import {ExclamationCircleOutlined, PlusOutlined} from '@ant-design/icons';
//对话框
import {Button, Col, Drawer, message, Modal, Row, Space} from 'antd';
import React, {useRef, useState} from 'react';
import {PageContainer} from '@ant-design/pro-layout';
import ProTable, {ActionType, ProColumns} from '@ant-design/pro-table';
import ProDescriptions from '@ant-design/pro-descriptions';
import CreateUserForm from './CreateUserForm';
import {TableListItem} from './data.d';
import {
  delUserInfoById, getUserRoleList,
  queryRule,
  removeRule,
} from './service';
import {FormInstance} from "antd/lib/form/hooks/useForm";
import RoleModule from "@/pages/systemSetting/components/RoleModule";
import './index.less';

const {confirm} = Modal;

/**
 *  删除节点
 * @param selectedRows
 */
const handleRemove = async (selectedRows: TableListItem[]) => {
  const hide = message.loading('正在删除');
  if (!selectedRows) return true;
  try {
    await removeRule({
      ids: selectedRows.map((row) => row.yhid),
    });
    hide();
    message.success('删除成功，即将刷新');
    return true;
  } catch (error) {
    hide();
    message.error('删除失败，请重试');
    return false;
  }
};

const UserModule: React.FC<{}> = () => {
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  const [stepFormValues, setStepFormValues] = useState({});
  const actionRef = useRef<ActionType>();
  const formRef = useRef<FormInstance>();
  const [row, setRow] = useState<TableListItem>();
  const [selectedRowsState, setSelectedRows] = useState<TableListItem[]>([]);
  const [isEdit, setIsEdit] = useState(false);
  const [selectedUserId,setSelectedUserId] = useState(undefined);
  const [selectedKeys,setSelectedKeys] = useState([]);
  const [funcList,setFuncList] = useState([]);
  const columns: ProColumns<TableListItem>[] = [
    {
      title: '用户姓名',
      dataIndex: 'yhxm',
      search: false,
      width:90   // 20210330 zx修改  针对Bug编号 157
    },
    /*{
      title: '出生年月',
      dataIndex: 'csny',
      search: false,
      width: 200,
    },
    {
      title: '性别',
      dataIndex: 'xb',
      search: false,
    },*/
    {
      title: '职务',
      dataIndex: 'zw',
      search: false
    },
    {
      title: '身份证号',
      dataIndex: 'sfz',
      search: false,
    },
    {
      title: '联系电话',
      dataIndex: 'lxdh',
      search: false,
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => {
        return [
          <a key="edit" onClick={() => {
            setIsEdit(true);
            setStepFormValues(record);
            handleModalVisible(true)
          }}>编辑</a>,
          <a key="del" onClick={() => showConfirm(record.yhid)}>删除</a>];
      },
    },
    {
      title: '用户姓名',
      dataIndex: 'yhxm',
      hideInTable: true,//表格中隐藏，用于输入框查询条件
    }, {
      title: '身份证号',
      dataIndex: 'sfz',
      hideInTable: true,//表格中隐藏，用于输入框查询条件
    }
  ];

  const showConfirm = (key: string) => {
    confirm({
      title: '删除角色',
      icon: <ExclamationCircleOutlined/>,
      content: "请您确认是否删除当前角色?",
      onOk() {
        delUserInfoById({id:key}).then(response => {
          message.success('删除成功');
          if (actionRef.current) {
            actionRef.current.reload();
          }
          return true;
        }).catch(error=>{
          return false;
        });
      },
      onCancel() {

      }
    });
  };

  const handleRowClick = (record) => {
    const { yhid } = record;
    setSelectedUserId(yhid);
    setSelectedKeys([]);
    // 根据id 获取 role对应的菜单权限
    const params = { yhid: yhid };
    getUserRoleList(params).then(res => {
      setSelectedKeys(res.data.roleList);
      setFuncList(res.data.funcList);
    }).catch(error=>{
      return false;
    });
  };

  return (
    <PageContainer
      pageHeaderRender={false}
    >
      <Row>
        <Col span={12}>
          <ProTable<TableListItem>
            rowClassName={record => {
              if (record.yhid === selectedUserId) return 'role-table selected';
              return 'role-table';
            }}
            scroll={{y: 420}}
            headerTitle={'用户列表'}
            actionRef={actionRef}
            formRef={formRef}
            rowKey="yhid"
            search={{
              labelWidth: 120,
              defaultCollapsed: false,//搜索框默认展开
            }}
            tableAlertOptionRender={() => {
              return (
                <Space size={16}>
                  <a
                    onClick={async () => {
                      await handleRemove(selectedRowsState);
                      setSelectedRows([]);
                      actionRef.current?.reloadAndRest?.();
                    }}
                  >
                    批量删除
                  </a>
                </Space>
              );
            }}
            toolBarRender={() => [
              <Button
                type="primary"
                key="buttonKey"
                onClick={() => {
                  setIsEdit(false);
                  handleModalVisible(true)
                  return true;
                }}>
                <PlusOutlined/> 添加
              </Button>,
            ]}
            request={(params, sorter, filter) => {
              return queryRule({...params, sorter, filter}).then(response => {
                response.data.data = response.data.list;
                return response.data;
              });
            }
            }
            columns={columns}
            rowSelection={{
              onChange: (_, selectedRows) => setSelectedRows(selectedRows),
            }}
            onRow={(record, index) => {
              return {
                onClick: () => handleRowClick(record, index),
              };
            }}
          />
          <CreateUserForm
            onCancel={() => {setStepFormValues({});handleModalVisible(false);}}
            modalVisible={createModalVisible}
            data={stepFormValues}
            isEdit={isEdit}
            onOk={() => {
              handleModalVisible(false);
              if (actionRef.current) {
                actionRef.current.reload();
              }
            }}
          >
          </CreateUserForm>
          <Drawer
            width={600}
            visible={!!row}
            onClose={() => {
              setRow(undefined);
            }}
            closable={false}
          >
            {row?.yhxm && (
              <ProDescriptions<TableListItem>
                column={2}
                title={row?.yhxm}
                request={async () => ({
                  data: row || {},
                })}
                params={{
                  id: row?.yhxm,
                }}
                columns={columns}
              />
            )}
          </Drawer>
        </Col>
        <Col span={12}>
          <RoleModule value={selectedKeys} isUser={true} selectedUserId={selectedUserId} funcList={funcList} />
        </Col>
      </Row>
    </PageContainer>
  );
};

export default UserModule;
