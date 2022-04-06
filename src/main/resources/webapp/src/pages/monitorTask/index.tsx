//对话框
import {Button, Card, message, Modal, Row ,Col, Divider,Popconfirm} from 'antd';
import React, {useEffect, useRef, useState} from 'react';
import {PageContainer} from '@ant-design/pro-layout';
import { PlusOutlined, CaretLeftOutlined } from '@ant-design/icons';
import {DataNode, UpdateWlbsFl} from './data.d';
import {v4 as uuidV4} from 'uuid';
import ProTable from '@ant-design/pro-table';
import ProForm, { ProFormText, ProFormSelect, ProFormTextArea, ProFormCheckbox } from '@ant-design/pro-form';
import { CheckCard } from '@ant-design/pro-card';

import { getDictObjById, addJkrw, editJkrw, delJkrw, getJkrwList, startOrStopTask } from './service'



//定义组件对象
const MonitorTaskComp: React.FC<{}> = () => {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [rwIsModalVisible, setRwIsModalVisible] = useState(false);
  const actionRef = useRef();
  const formRef = useRef();
  const [zblxOptions, setZblxOptions] = useState([]);
  const [selectZbbs, setSelectZbbs] = useState([]);
  const [isEdit, setIsEdit] = useState(false);  //true 编辑状态  false 新增状态
  const [currentEditData, setCurrentEditData] = useState(null);
  const [targetList, setTargetList] = useState([]);


  useEffect(() => {
    getZblxDict();
  }, []);

  const getRwlxDict  = async () => {
    return getDictObjById({zdbs:"rwlx"}).then(res=>{
      if(res.code === 200){
        return res.data;
      }
    }).catch()
  }

  const getZblxDict = () => {
    getDictObjById({zdbs:"zblx"}).then(res=>{
      if(res.code === 200){
        setZblxOptions(res.data);
      }
    }).catch()
  }

  const columns:any = [
    {
      title: '序号',
      dataIndex: 'index',
      valueType: 'indexBorder',
      width: 60,
    },
    {
      title: '任务名称',
      dataIndex: 'rwmc',
      ellipsis: true,
      formItemProps: {
        rules: [
          {
            required: true,
            message: '此项为必填项',
          },
        ],
      },
    },
    {
      title: '任务类型',
      dataIndex: 'rwlx',
      valueType: 'select',
      request : getRwlxDict,
      params: {},
    },
    {
      title: '设备IP',
      dataIndex: 'sbip',
      search: false,
      width: 150
    },
    {
      title: '设备端口',
      dataIndex: 'sbdk',
      search: false,
      width: 150
    },
    {
      title: '任务描述',
      dataIndex: 'rwms',
      search: false
    },
    {
      title: '状态',
      dataIndex: 'rwzt',
      valueType: 'select',
      search: false,
      width: 150,
      valueEnum: {
        0: {
          text: '未启动',
          status: 'Error',
        },
        1: {
          text: '已启动',
          status: 'Success',
          disabled: true,
        }
      },
    },
    {
      title: '操作',
      valueType: 'option',
      render: (text, record, _, action) => {
        if(record.rwzt === 0){
          return(
            [
              <a
                key="editable"
                onClick={() => {
                  setIsModalVisible(true)
                  setIsEdit(true);
                  formRef?.current?.setFieldsValue(record);
                  setCurrentEditData(record);
                  record.zbList.map(item=>{
                    if(item.zbbs){
                      setSelectZbbs(old=>[...old,item?.zbbs])
                    }
                  })
                }}
              >
                编辑
              </a>,
              <Popconfirm
                key="popconfirm"
                title={`确认${record.rwmc}吗?`}
                okText="是"
                cancelText="否"
                onConfirm={() => {
                  delJkrw({rwbs:record?.rwbs}).then(res=>{
                    if(res.code === 200){
                      message.success("删除成功");
                      actionRef?.current.reload();
                    }
                  })
                }}>
                <a>删除</a>
              </Popconfirm>,
              <a
                key="start"
                onClick={() => {
                  startOrStopTask({taskList:[record?.rwbs],startOrStop:"start"}).then(res=>{
                    if( res.code === 200 ){
                      message.success("任务启动成功")
                      actionRef?.current.reload();
                    }else{
                      message.error("任务启动失败")
                    }
                  })
                }}
              >
                启动
              </a>,
              <a
                key="show"
                onClick={() => {
                  setRwIsModalVisible(true);
                  setTargetList(record?.zbList);
                }}
              >
                指标项
              </a>
            ]
          )
        }else {
          return (
            [
              <a
                key="stop"
                onClick={() => {
                  startOrStopTask({taskList:[record?.rwbs],startOrStop:"stop"}).then(res=>{
                    if( res.code === 200 ){
                      message.success("任务关闭成功")
                      actionRef?.current.reload();
                    }else{
                      message.error("任务关闭失败")
                    }
                  })
                }}
              >
                停止
              </a>,
              <a
                key="show"
                onClick={() => {
                  setRwIsModalVisible(true);
                  setTargetList(record?.zbList);
                }}
              >
                指标项
              </a>
            ]
          )
        }
      }
    },
  ];

  const handleOk = () => {
    formRef?.current?.validateFields().then(res=>{
      if(selectZbbs.length === 0){
        message.error("请选择监控指标")
        return false;
      }
      formRef?.current?.submit();
    })
  };

  const handleCancel = () => {
    formRef?.current.resetFields();
    setSelectZbbs([])
    setIsModalVisible(false);
  };



  return (
    <PageContainer
      pageHeaderRender={false}
    >
      <ProTable
        columns={columns}
        actionRef={actionRef}
        request={async (params = {}, sort, filter) => {
          return getJkrwList(params).then(res=>{
            if(res.code === 200){
              return {data:res.data.list};
            }
          })
        }}
        editable={{
          type: 'multiple',
        }}
        columnsState={{
          persistenceKey: 'pro-table-singe-demos',
          persistenceType: 'localStorage',
        }}
        rowKey="id"
        search={{
          labelWidth: 'auto',
        }}

        pagination={{
          pageSize: 10,
        }}
        dateFormatter="string"
        headerTitle="監控任务列表"
        toolBarRender={() => [
          <Button key="button" icon={<CaretLeftOutlined />} type="primary" onClick={()=>{

          }}>
            全部启动
          </Button>,
          <Button key="button" icon={<PlusOutlined />} type="primary" onClick={()=>{
            setIsModalVisible(true)
            setIsEdit(false);
          }}>
            新增任务
          </Button>
        ]}
      />
      <Modal title="编辑任务" visible={isModalVisible} onOk={handleOk}
             onCancel={handleCancel}
             width={1000} forceRender={true}>
            <ProForm
              formRef={formRef}
              onFinish={async (values) => {
                values.rwzbList = selectZbbs;
                if(isEdit){
                  values.rwbs = currentEditData?.rwbs;
                  editJkrw(values).then(res=>{
                    if( res.code === 200 ){
                      message.success("新增成功");
                      setIsModalVisible(false);
                      actionRef?.current.reload();
                    }
                  }).catch()
                }else{
                  values.rwbs = uuidV4().toString().replaceAll("-", "");
                  addJkrw(values).then(res=>{
                    if( res.code === 200 ){
                      message.success("新增成功");
                      setIsModalVisible(false);
                      actionRef?.current.reload();
                    }
                  }).catch()
                }
              }}
              {...{
                labelCol: { span: 6 },
                wrapperCol: { span: 16 },
              }}
              layout={'horizontal'}
              submitter={{
                // 配置按钮的属性
                resetButtonProps: {
                  style: {
                    // 隐藏重置按钮
                    display: 'none',
                  },
                },
                submitButtonProps: {
                  style: {
                    // 隐藏重置按钮
                    display: 'none',
                  },
                }
              }}
            >
              <Row gutter={16}>
                <Col span={12}>
                  <div style={{fontSize:16,marginBottom:20}}>
                    <Divider type="vertical" />基本信息
                  </div>

                  <ProFormText
                    width="md"
                    name="rwmc"
                    label="任务名称"
                    placeholder="请输入任务名称"
                    rules={[
                      {
                        required: true,
                        message: '请输入任务名称！',
                      },
                    ]}
                  />
                  <ProFormSelect
                    request={getRwlxDict}
                    params={{}}
                    width="md"
                    name="rwlx"
                    label="任务类型"
                    rules={[
                      {
                        required: true,
                        message: '请选择任务类型！',
                      },
                    ]}
                  />
                  <ProFormText
                    width="md"
                    name="sbip"
                    label="设备ip"
                    rules={[
                      {
                        required: true,
                        message: '请输入设备ip！',
                      },
                    ]}
                  />
                  <ProFormText
                    width="md"
                    name="sbdk"
                    label="设备端口"
                    rules={[
                      {
                        required: true,
                        message: '请输入设备端口！',
                      },
                    ]}
                  />
                  <ProFormTextArea
                    width="md"
                    name="rwms"
                    label="任务描述"

                  />

                </Col>

                <Col span={12}>
                  <div style={{fontSize:16,marginBottom:20}}>
                    <Divider type="vertical" />监控指标
                  </div>
                  <div style={{marginLeft:50}}>
                    <CheckCard.Group
                      multiple
                      onChange={(value) => {
                        setSelectZbbs(value);
                      }}
                      value={selectZbbs}
                    >
                      {
                        zblxOptions.map(item=>{
                        return (
                          <CheckCard
                            title={item?.label}
                            value={item?.value}
                          />
                        )
                      })}
                    </CheckCard.Group>
                  </div>

                </Col>
              </Row>
            </ProForm>
      </Modal>

      <Modal
         title="任务指标项"
         width = {350}
         footer = {null}
         bodyStyle = {{
           textAlign:'center'
         }}
         visible={rwIsModalVisible}
         onOk={()=>{

            setRwIsModalVisible(false)

         }}
         onCancel={()=>{
            setRwIsModalVisible(false)
         }}
      >
        {
          targetList.map(item=>{
            return(
              <Card size="small" style={{ width: 300,textAlign:'center',marginBottom:20 }}>
                {item?.zbmc}
              </Card>
            )
          })
        }
      </Modal>
    </PageContainer>
  );
};

export default MonitorTaskComp;
