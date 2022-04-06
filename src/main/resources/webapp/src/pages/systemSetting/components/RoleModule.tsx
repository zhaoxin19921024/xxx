import {ExclamationCircleOutlined, PlusOutlined} from '@ant-design/icons';
//对话框
import {Button, Col, message, Modal, Row} from 'antd';
import React, {useEffect, useRef, useState} from 'react';
import {PageContainer} from '@ant-design/pro-layout';
import ProTable, {ActionType, ProColumns} from '@ant-design/pro-table';
import CreateRoleForm from './CreateRoleForm';
import {TableListItemRole} from './data.d';
import {getRoleList, delRoleInfo,getRoleFunc,commitRoleFunc,commitUserRoles} from './service';
import MenuSelect from './MenuSelect';
import './index.less';

const {confirm} = Modal;

interface CreateUserProps {
  value:any[];
  isUser:boolean;
  selectedUserId:string;
  funcList:any[];
}

const RoleModule: React.FC<CreateUserProps> = (props) => {
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  const [stepFormValues, setStepFormValues] = useState({});
  const actionRef = useRef<ActionType>();
  const [isEdit, setIsEdit] = useState(false);
  const [selectedKeys,setSelectedKeys] = useState([]);
  const [loading,setLoading] = useState(false);
  const [loadingRoleMenu,setLoadingRoleMenu] = useState(false);
  const [selectedRoleId,setSelectedRoleId] = useState(undefined);
  const [selectedRowRoleKeys,setSelectedRowRoleKeys] = useState([]);
  const [selectedRowsState, setSelectedRows] = useState<TableListItemRole[]>([]);

  useEffect(() => {
    if(props.value){
      setSelectedRowRoleKeys(props.value);
    }
  }, [props.value]);

  useEffect(() => {
    if(props.funcList){
      setSelectedKeys(props.funcList);
    }
  }, [props.funcList]);

  const columns: ProColumns<TableListItemRole>[] = [
    {
      title: '角色名称',
      dataIndex: 'jsmc',
      search: false,
    },
    {
      title: '角色说明',
      dataIndex: 'jssm',
      search: false,
      width: 200,
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
          <a key="del" onClick={() => showConfirm(record.jsdm)}>删除</a>];
      },
    }
  ];

  const showConfirm = (key: string) => {
    confirm({
      title: '删除角色',
      icon: <ExclamationCircleOutlined/>,
      content: "请您确认是否删除当前角色?",
      onOk() {
        delRoleInfo({jsdm:key}).then(response => {
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
    const { jsdm } = record;
    setSelectedRoleId(jsdm);
    setSelectedKeys([]);
    // 根据id 获取 role对应的菜单权限
    const params = { jsdm: jsdm };
    setLoadingRoleMenu(true);
    getRoleFunc(params).then(res => {
      setSelectedKeys(res.data);
    }).finally(() => setLoadingRoleMenu(false));
  };

  const handleSaveRoleMenu = () => {
    if(props.isUser){
      if(selectedRowsState.length === 0 || props.selectedUserId === undefined){
        //message.success('请选择一个用户或至少选择一个权限,再进行保存操作.');
        commitUserRoles({ jsdmlist:[],yhid:props.selectedUserId }).then(res => {
          message.success('保存成功');
        }).catch(error=>{
          return false;
        });
      }else {
        commitUserRoles({ jsdmlist:selectedRowsState.map((row) => row.jsdm),yhid:props.selectedUserId }).then(res => {
          message.success('保存成功');
        }).catch(error=>{
          return false;
        });
      }
    }else {
      if(selectedKeys && selectedKeys.length === 0){
        //message.success('请至少选择一个菜单,再进行保存操作.');
        setLoading(true)
        commitRoleFunc({ gnlist:[],jsdm:selectedRoleId }).then(res => {
          message.success('保存成功');
        }).finally(() => setLoading(false));
      }else {
        setLoading(true)
        commitRoleFunc({ gnlist:selectedKeys,jsdm:selectedRoleId }).then(res => {
          message.success('保存成功');
        }).finally(() => setLoading(false));
      }
    }
  };

  return (
    <PageContainer
      pageHeaderRender={false}
    >
      <Row>
        <Col span={16}>
          <ProTable<TableListItemRole>
            style={{marginTop:"100px"}}
            rowClassName={record => {
              if (record.jsdm === selectedRoleId) return 'role-table selected';
              return 'role-table';
            }}
            scroll={{y: 420}}
            headerTitle={'角色列表'}
            actionRef={actionRef}
            rowKey="jsdm"
            search={false}
            pagination={false}
            toolBarRender={() => {
              if(props.isUser){
                return [
                  <Button
                    type="primary"
                    key="buttonKey"
                    onClick={() => {
                      handleSaveRoleMenu();
                      return true;
                    }}>
                    <PlusOutlined/> 保存
                  </Button>,
                ]
              }else {
                return [
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
                  <Button
                    type="primary"
                    key="buttonKey"
                    onClick={() => {
                      handleSaveRoleMenu();
                      return true;
                    }}>
                    <PlusOutlined/> 保存
                  </Button>,
                ]
              }
            }}
            request={(params, sorter, filter) => {
              return getRoleList({}).then(response => {
                response.data.data = response.data;
                return response.data;
              });
            }
            }
            columns={columns}
            rowSelection={{
              selectedRowKeys:selectedRowRoleKeys,
              onChange: (_, selectedRows) => {setSelectedRows(selectedRows);setSelectedRowRoleKeys(selectedRows.map((row) => row.jsdm));} ,
            }}
            onRow={(record, index) => {
              return {
                onClick: () => handleRowClick(record, index),
              };
            }}
          />
          <CreateRoleForm
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
          </CreateRoleForm>
        </Col>
        <Col span={8}>
          <MenuSelect
            value={selectedKeys}
            onChange={selectedKeys => setSelectedKeys(selectedKeys)}
          />
        </Col>
      </Row>
    </PageContainer>
  );
};

export default RoleModule;
