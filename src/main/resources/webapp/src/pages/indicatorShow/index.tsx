//对话框
import {Col, Divider, Button, Row, Modal, Card, message,PageHeader,Drawer, Space, Badge} from 'antd';
import React, {useEffect, useRef, useState} from 'react';
import {PageContainer} from '@ant-design/pro-layout';
import ProForm, { ProFormText,ProFormRadio } from '@ant-design/pro-form';
import ProTable from '@ant-design/pro-table';
import ProList from '@ant-design/pro-list';
import './index.less'
import {v4 as uuidV4} from 'uuid';
import { addJkgczb, getJkgczbList, addJkzbtx, getGcxxTxList, getGcTxDataFromServer, delGcTxData, delJkgczb } from './service'
import { DashBoard as DashBoardComp } from './components/DashBoard';
import { PlusOutlined,CloseCircleOutlined, CloseOutlined } from '@ant-design/icons';
import { getDictObjById, getJkrwList } from "@/pages/monitorTask/service";
import { CheckCard } from '@ant-design/pro-card';
import SockJS from "sockjs-client";
import Stomp from "stompjs";


const { confirm } = Modal;

let stompClient:any = null;
let wsId: any = null;

//定义组件对象
const IndicatorShowComp = () => {
  const [lineData, setLineData] = useState(lineDataTemp);
  const [cardActionProps, setCardActionProps] = useState<'actions' | 'extra'>('actions');
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [gcList, setGcList] = useState([]);
  const [rwTabData, setRwTabData] = useState([]);

  const formRef = useRef();

  const drawFormRef = useRef();

  const [dataBoardVisible,setDataBoardVisible] = useState(false);
  const [drawerVisible,setDrawerVisible] = useState(false);
  const [currentSelectGc,setCurrentSelectGc] = useState("");
  const [filterFormData,setFilterFormData] = useState(null);
  const [selectedRowKeys,setSelectedRowKeys] = useState([]);


  const [gcStatus,setGcStatus] = useState(0); //工程状态 0 浏览 1 编辑 2 运行中


  const [gcxxtxData,setGcxxtxData] = useState({txList:[],txData: {}});
  useEffect(() => {
    getGcList();
    //订阅后台数据
    let ws = new SockJS('stomp');
    stompClient = Stomp.over(ws);
    stompClient.heartbeat.outgoing = 200;
    stompClient.heartbeat.incoming = 0;
    //stompClient.debug = null;
    stompClient.connect({}, function () {
      console.log("-----连接成功", "--------------");
      // wsId = stompClient.subscribe("/vnmGxtxServer/"+gcbs, function (res: any) {
      //   console.log("-----当前订阅收到场景监视信息", res.body);
      //   let tempData = JSON.parse(res.body);
      //   console.log(tempData);
      // });

    })
  }, []);

  const getRwlxDict  = async () => {
    return getDictObjById({zdbs:"rwlx"}).then(res=>{
      if(res.code === 200){
        let arr = [];
        res.data.map(item=>{
          item.value = Number(item.value)
          arr.push(item);
        });
        return arr;
      }
    }).catch()
  }

  const getZblxDict  = async () => {
    return getDictObjById({zdbs:"zblx"}).then(res=>{
      if(res.code === 200){
        let arr = [];
        res.data.map(item=>{
          item.value = Number(item.value)
          arr.push(item);
        });
        return arr;
      }
    }).catch()
  }

  const columns = [
    {
      title: '任务名称',
      key: 'rwmc',
      dataIndex: 'rwmc',
      search: false
    },
    {
      title: '任务类型',
      key: 'rwlx',
      dataIndex: 'rwlx',
      valueType: 'radio',
      initialValue: 'all',
      request : getRwlxDict,
      params: {},
      search: false
    }
  ]

  const getGcList = () =>{
    getJkgczbList({}).then(res=>{
      if(res.code === 200){
          setGcList(res.data);
          let gcbs = res.data.length > 0 ? res.data[0].gcbs : ""
          setCurrentSelectGc(gcbs)
          getGcxxTxList({gcbs:gcbs}).then(res=>{
            if(res.code === 200){
              console.log(res.data)
              setGcxxtxData({txList:res.data.list,txData: res.data.tempMap});
            }
          }).catch(res=>{})
      }
    })
  }

  //画板编辑事件
  const editDataEvent = (param) =>{
    setDrawerVisible(true);
    drawFormRef?.current?.setFieldsValue(param);
    drawFormRef?.current?.submit();
    setSelectedRowKeys(param?.rwbsList);
  }

  const delDataEvent = (param) =>{
    delGcTxData({txbs:param.txbs}).then(res=>{
      if(res.code === 200){
        message.success("删除成功")
        getGcxxTxList({gcbs:param?.gcbs}).then(res=>{
          if(res.code === 200){
            console.log(res.data)
            setGcxxtxData({txList:res.data.list,txData: res.data.tempMap});
          }
        }).catch(res=>{})
      }
    })
  }


  //新建工程
  const addEngineer = () =>{
    setIsModalVisible(true)
  }

  return (
    <PageContainer
      pageHeaderRender={false}
    >
      <Row gutter={20}>
        <Col className="gutter-row" span={3}
             // style={{backgroundColor:'#000'}}
        >
          <div>
            <span style={{fontSize:18}}>工程列表</span>
            <Button type="primary" style={{float:"right"}} onClick={addEngineer}>新建工程</Button>
          </div>
          <Divider />
          <CheckCard.Group
            className={'checkGroup'}
            onChange={(value) => {
              if(value){
                setCurrentSelectGc(value);
                getGcxxTxList({gcbs:value}).then(res=> {
                  if (res.code === 200) {
                    setGcxxtxData({txList: res.data.list, txData: res.data.tempMap})
                  }
                })
              }else{
                setCurrentSelectGc("")
                setGcxxtxData({txList: [], txData: {}})
              }
            }}
            value={currentSelectGc}
            size={'small'}
          >
            {gcList.map(item=>(
              <Badge offset={[-10, 10]}
                     count={
                       <CloseOutlined style={{color:"#1890ff"}} onClick={()=>{
                         confirm({
                           title: '确认删除此工程吗',
                           onOk() {
                             delJkgczb({gcbs:item?.gcbs}).then(res=>{
                               if(res.code === 200){
                                  message.success("删除成功");
                                  getGcList();
                               }
                             })
                           },
                           onCancel() {
                             console.log('Cancel');
                           },
                         });
                       }}
                       />
                     }
              >
                <CheckCard
                  title={item?.gcmc}
                  description={item?.cjsj}
                  value={item?.gcbs}
                />
              </Badge>
            ))}
          </CheckCard.Group>
        </Col>
        <Col className="gutter-row" span={21} >
            <Row gutter={16}>
              <Col span={24}>
                <span style={{fontSize: 20}}>指标项展示</span>
                <Button type="primary" style={{float:"right"}} disabled={currentSelectGc===""} onClick={()=>{
                  getGcxxTxList({gcbs:currentSelectGc}).then(ress=>{
                    if(ress.code === 200){
                      setDataBoardVisible(true)
                      setGcStatus(2)
                      getGcTxDataFromServer({gcbs:currentSelectGc,startOrStop:"stop"}).then(res=>{
                        if(res.code === 200){
                          getGcTxDataFromServer({gcbs:currentSelectGc,startOrStop:"start"}).then(res=>{
                            if(res.code === 200){
                              message.success("获取成功")
                              wsId = stompClient.subscribe("/vnmGctxServer/"+currentSelectGc, function (re: any) {
                                console.log("-----当前订阅收到场景监视信息", re.body);
                                let tempData = JSON.parse(re.body);
                                setGcxxtxData({txList:ress.data.list,txData: tempData})
                              });
                            }
                          })
                        }
                      })
                    }
                  }).catch(res=>{})
                }}>运行</Button>
                <Button type="primary" style={{float:"right",marginRight:10}} disabled={currentSelectGc===""} onClick={()=>{
                  getGcxxTxList({gcbs:currentSelectGc}).then(res=>{
                    if(res.code === 200){
                      setGcxxtxData({txList:res.data.list,txData: res.data.tempMap})
                      setDataBoardVisible(true)
                      setGcStatus(1)
                    }
                  }).catch(res=>{})
                }}>编辑画板</Button>
              </Col>
            </Row>
            <Divider />
            <Row gutter={16} >
              <Col span={24} >
                <DashBoardComp {...gcxxtxData} gcStatus={gcStatus}/>
              </Col>
            </Row>
        </Col>
      </Row>
      <Modal title="工程编辑"
             visible={isModalVisible}
             onOk={()=>{
               formRef?.current?.validateFields().then(res=>{
                 formRef?.current?.submit();
                 setIsModalVisible(false)
               })
             }}
             onCancel={()=>setIsModalVisible(false)}
             width={380}
             // forceRender={true}
      >
        <ProForm
          formRef={formRef}
          layout={'horizontal'}
          style={{height:30}}
          onFinish={async (values) => {
            values.gcbs = uuidV4().toString().replaceAll("-", "");
            addJkgczb(values).then(res=>{
              if(res.code === 200){
                message.success("新增成功")
                getGcList();
                setIsModalVisible(false)
              }
            })
          }}
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
          <ProFormText
            width="md"
            name="gcmc"
            label="工程名称"
            placeholder="请输入名称"
            rules={[
              {
                required: true,
                message: '请输入名称！',
              },
            ]}
          />
        </ProForm>
      </Modal>
      <Modal
        title={gcStatus !== 2 ? "编辑画板":"场景运行"}
        visible={dataBoardVisible}
        onOk={()=>setDataBoardVisible(false)}
        onCancel={()=>{
          setDataBoardVisible(false);
          if(gcStatus === 2){
            getGcTxDataFromServer({gcbs:currentSelectGc,startOrStop:"stop"}).then(res=>{
              if(res.code === 200){
                message.success("关闭成功")
              }
            })
          }
          setGcStatus(0)
        }}
        width='calc(100vw)'
        style={{
          margin: 0,
          maxWidth:'calc(100vw)',
          position:'relative',
          top:0
        }}
        bodyStyle={{
          height:'calc(100vh - 80px)',
          backgroundColor:'grey',
          padding: 12
        }}
        footer={null}
      >
        {
          gcStatus === 2 ? null :
            <PageHeader
              style={{backgroundColor:'#fff',padding:'5px 25px',marginBottom:20}}
              extra={[
                <a className="example-link" onClick={()=>{

                }}>
                  <PlusOutlined />
                  背景样式
                </a>,
                <a className="example-link" onClick={()=>{
                  setDrawerVisible(true)
                  drawFormRef?.current?.resetFields();
                }}>
                  <PlusOutlined />
                  新建图像
                </a>,
                <a className="example-link" onClick={()=>{

                }}>
                  <PlusOutlined />
                  保存图像
                </a>
              ]}
            />
        }
        <DashBoardComp {...gcxxtxData} editDataEvent={editDataEvent} delDataEvent={delDataEvent} gcStatus={gcStatus}/>
      </Modal>
      <Drawer
          title="属性编辑"
          placement="right"
          onClose={()=>{
            setDrawerVisible(false)

          }}
          zIndex={10000}
          forceRender={true}
          visible={drawerVisible} size={'large'}
          // extra={
          //   <Space>
          //     <Button type="primary" onClick={()=>{
          //
          //     }}>
          //       保存
          //     </Button>
          //   </Space>
          // }
      >
        <ProForm
          formRef={drawFormRef}
          onFinish={ (values) => {
            getJkrwList(values).then(res=>{
              if(res.code === 200 ){
                console.log(res.data)
                setFilterFormData(values);
                setRwTabData(res.data.list)
              }
            })
          }}
          submitter={{
            searchConfig: {
              resetText: '重置',
              submitText: '查询指标',
            },
          }}
        >
          <ProFormText
            name="txmc"
            label="图形名称"
            rules={[{ required: true, message: '这是必填项' }]}
          />

          <ProFormRadio.Group
            name="rwlx"
            label="任务类型"
            request={getRwlxDict}
            params={{}}
            rules={[{ required: true, message: '这是必填项' }]}
          />
          <ProFormRadio.Group
            name="zbbs"
            label="指标项"
            request={getZblxDict}
            params={{}}
            rules={[{ required: true, message: '这是必填项' }]}
          />
        </ProForm>
        <ProTable
          style={{marginTop:20}}
          pagination={{
            pageSize:100
          }}
          rowSelection={{
            // selectedRowKeys
          }}
          tableAlertRender={({ selectedRowKeys, selectedRows, onCleanSelected }) => (
            <Space size={24}>
              <span>
                已选 {selectedRowKeys.length} 项
                <a style={{ marginLeft: 8 }} onClick={onCleanSelected}>
                  取消选择
                </a>
              </span>
            </Space>
          )}
          tableAlertOptionRender={({selectedRowKeys}) => {
            return (
              <Space size={16}>
                <a onClick={()=>{
                  let obj = {
                    txbs: uuidV4().toString().replaceAll("-", ""),
                    gcbs: currentSelectGc,
                    rwlx: filterFormData?.rwlx,
                    zbbs: filterFormData?.zbbs,
                    txmc: filterFormData?.txmc,
                    rwbsList:selectedRowKeys
                  }
                  addJkzbtx(obj).then(res=>{
                    if(res.code === 200){
                      message.success("新增成功")
                      setDrawerVisible(false);
                      let arr = gcxxtxData?.txList;
                      arr.push(obj);
                      setGcxxtxData({txList:arr,txData: {}})
                    }
                  })
                }}>确认选择</a>
              </Space>
            );
          }}
          columns={columns}
          toolBarRender={false}
          rowKey="rwbs"
          dataSource={rwTabData}
          size={'small'}
          search={false}
        />
      </Drawer>
    </PageContainer>
  );
};

export default IndicatorShowComp;






const lineDataTemp = [
    {
      "year": "1850",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1850",
      "value": 54,
      "category": "Solid fuel"
    },
    {
      "year": "1850",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1850",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1850",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1851",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1851",
      "value": 54,
      "category": "Solid fuel"
    },
    {
      "year": "1851",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1851",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1851",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1852",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1852",
      "value": 57,
      "category": "Solid fuel"
    },
    {
      "year": "1852",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1852",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1852",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1853",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1853",
      "value": 59,
      "category": "Solid fuel"
    },
    {
      "year": "1853",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1853",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1853",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1854",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1854",
      "value": 69,
      "category": "Solid fuel"
    },
    {
      "year": "1854",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1854",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1854",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1855",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1855",
      "value": 71,
      "category": "Solid fuel"
    },
    {
      "year": "1855",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1855",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1855",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1856",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1856",
      "value": 76,
      "category": "Solid fuel"
    },
    {
      "year": "1856",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1856",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1856",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1857",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1857",
      "value": 77,
      "category": "Solid fuel"
    },
    {
      "year": "1857",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1857",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1857",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1858",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1858",
      "value": 78,
      "category": "Solid fuel"
    },
    {
      "year": "1858",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1858",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1858",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1859",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1859",
      "value": 83,
      "category": "Solid fuel"
    },
    {
      "year": "1859",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1859",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1859",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1860",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1860",
      "value": 91,
      "category": "Solid fuel"
    },
    {
      "year": "1860",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1860",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1860",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1861",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1861",
      "value": 95,
      "category": "Solid fuel"
    },
    {
      "year": "1861",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1861",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1861",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1862",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1862",
      "value": 96,
      "category": "Solid fuel"
    },
    {
      "year": "1862",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1862",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1862",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1863",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1863",
      "value": 103,
      "category": "Solid fuel"
    },
    {
      "year": "1863",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1863",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1863",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1864",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1864",
      "value": 112,
      "category": "Solid fuel"
    },
    {
      "year": "1864",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1864",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1864",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1865",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1865",
      "value": 119,
      "category": "Solid fuel"
    },
    {
      "year": "1865",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1865",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1865",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1866",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1866",
      "value": 122,
      "category": "Solid fuel"
    },
    {
      "year": "1866",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1866",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1866",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1867",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1867",
      "value": 130,
      "category": "Solid fuel"
    },
    {
      "year": "1867",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1867",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1867",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1868",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1868",
      "value": 134,
      "category": "Solid fuel"
    },
    {
      "year": "1868",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1868",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1868",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1869",
      "value": 0,
      "category": "Liquid fuel"
    },
    {
      "year": "1869",
      "value": 142,
      "category": "Solid fuel"
    },
    {
      "year": "1869",
      "value": 0,
      "category": "Gas fuel"
    },
    {
      "year": "1869",
      "value": 0,
      "category": "Cement production"
    },
    {
      "year": "1869",
      "value": 0,
      "category": "Gas flarinl"
    },
    {
      "year": "1870",
      "value": 1,
      "category": "Liquid fuel"
    },
    {
      "year": "1870",
      "value": 146,
      "category": "Solid fuel"
    },
    {
      "year": "1870",
      "value": 0,
      "category": "Gas fuel"
    }
]



