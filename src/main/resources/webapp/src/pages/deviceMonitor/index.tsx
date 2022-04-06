//对话框
import React, {useEffect, useState} from 'react';
import {PageContainer} from '@ant-design/pro-layout';
import ProCard, {StatisticCard} from "@ant-design/pro-card";
import MiniProgress from "@/pages/deviceMonitor/charts/MiniProgress";
import RcResizeObserver from 'rc-resize-observer';
import ChartTable from "@/pages/deviceMonitor/charts/ChartTable";
import {searchByKeyWords} from "./service";
import './index.less';
import {Col, Descriptions, Row} from "antd";
import DeviceLine from './charts/Line';
import ModalDeviceInfo from "@/pages/deviceMonitor/ModalDeviceInfo";

import Stomp from "stompjs"
import SockJS from 'sockjs-client';
import MiniRingProgress from './charts/MiniRing';
import Field from '@ant-design/pro-field';
import Avatar from 'antd/lib/avatar/avatar';



const {Statistic, Divider} = StatisticCard;

let stompClient:any = null;
let wsId: any = null;
let listWsId: any = null;

//定义组件对象
const DeviceMonitor: React.FC<{}> = () => {
  const [serverListData, setServerListData] = useState([]);
  const [responsive, setResponsive] = useState(false);
  const [modalDeviceInfoVisible, setModalDeviceInfoVisible] = useState(false);
  const [deviceInfoData, setDeviceInfoData] = useState({});
  const [deviceListInfoData,setDeviceListInfoData] = useState({
    serverTotals:[],
    runResTotals:{
      cpuRateTotal:0,
      memRateTotal:0,
      diskRateTotal:0,
      deviceTotal:0,
      onlineDeviceTotal:0,
      offlineDeviceTotal:0,
      alarmDeviceTotal:0,
    },
    runSceneTotals:{
      sceneTotal:0,
      onlineSceneTotal:0,
      offlineSceneTotal:0,
      alarmSceneTotal:0,
    },
    runContainerTotals:{
      containerTotal:0,
      onlineContainerTotal:0,
      offlineContainerTotal:0,
      alarmContainerTotal:0,
    },
    alarmTotals:{
      riskTotal:0,
      highRiskTotal:0,
      midRiskTotal:0,
      lowerRiskTotal:0,
    },
  });

  useEffect(() => {
    searchByKeyWords({current: 1, pageSize: 1000}).then(res => {
      setServerListData(res.data.list);
    }).catch(err => {
    })
    stompClick();
    return () => {
      console.log(`组件销毁回调`,listWsId);
      if(listWsId){
        stompClient.unsubscribe(listWsId.id);
      }
    }
  }, []);

  const stompClick = () => {
    let ws = new SockJS('stomp');
    stompClient = Stomp.over(ws);
    stompClient.heartbeat.outgoing = 200;
    stompClient.heartbeat.incoming = 0;
    stompClient.debug = null;

    stompClient.connect({}, function () {
      //订阅列表数据
      listWsId = stompClient.subscribe("/list/getResponse", function (res: any) {
        console.log("-----当前订阅收到列表信息", res.body);
        setDeviceListInfoData(JSON.parse(res.body));
      })
    })
  }

  const onDeviceInfoClose = () => {
    if(wsId){
      stompClient.unsubscribe(wsId.id);
    }
    setModalDeviceInfoVisible(false);
  }

  const showDeviceInfoPage = (sId: string,sName: string) => {
    if(wsId){
      stompClient.unsubscribe(wsId.id);
    }
    //订阅单条数据
    wsId = stompClient.subscribe("/node/"+sId, function (res: any) {
      console.log("-----当前订阅收到设备信息", res.body);
      let tempData = JSON.parse(res.body);
      tempData.deviceName = sName;
      setDeviceInfoData(tempData);
    })
    setModalDeviceInfoVisible(true);
  }

  return (
    <PageContainer
      pageHeaderRender={false}
    >
      <ProCard gutter={15} split="vertical" ghost>
        <ProCard title="设备列表" colSpan={{md: 12, sm: 24}} style={{overflowY:"auto",height:"90vh"}}>
          <Row gutter={4}>
            {
              serverListData.map((item:{id:string,name:string,sbList:[]}, index) =>
                (
                  <Col key={item.id} span={12} style={{paddingBottom: "5px"}}>
                    <ProCard key={item.id} title={item.name} colSpan={{md: 24, sm: 24}} headerBordered bordered split="vertical">
                      <ProCard key={item.id} split="vertical" style={{padding: "10px"}}>
                        <Row key={item.id} gutter={4}>
                          {
                            item.sbList?.map((items:{id:string,name:string}, index) =>
                            (
                              deviceListInfoData?.serverTotals?.map((iteml:{serverId:string,load1:number,appTotal:number,containerTotal:number},index) =>
                                items.id === iteml.serverId?(
                                  <Col key={items.id} span={24} style={{paddingBottom: "5px"}}>
                                    <StatisticCard
                                      key={items.id}
                                      onClick={()=>showDeviceInfoPage(items.id,items.name)}
                                      bordered
                                      statistic={{
                                        style: {fontSize: "16px"},
                                        value: items.name,
                                      }}
                                      split="horizontal"
                                      style={{padding: "10px"}}
                                      chart={
                                        <Descriptions column={2} size="small">
                                          <Descriptions.Item label="1分钟负载" contentStyle={{paddingRight:"10px"}}>
                                              {/* <MiniProgress percent={iteml.load1?iteml.load1/100:0}/> */}
                                              <Field text={iteml.load1?iteml.load1.toFixed(2):0} valueType="progress" mode="read" />
                                          </Descriptions.Item>
                                        </Descriptions>
                                      }
                                    >
                                      <ProCard split="vertical" gutter={10}>
                                          <ProCard 
                                            bordered 
                                            layout="center"
                                            split="vertical"
                                            style={{backgroundColor:'#F7FBFF',padding: "10px"}}
                                          >
                                            <Statistic description={<h5>应用数量</h5>} value=" " title={<Avatar size="large" style={{color:'#ffffff',backgroundColor:'#6395F9'}}>{iteml.appTotal?iteml.appTotal:0}</Avatar>}/>
                                          </ProCard>
                                          <ProCard 
                                            bordered 
                                            layout="center"
                                            split="vertical"
                                            style={{backgroundColor:'#F7FBFF',padding: "10px"}}
                                          >
                                            <Statistic description={<h5>容器数量</h5>} value=" " title={<Avatar size="large" style={{color:'#ffffff',backgroundColor:'#6395F9'}}>{iteml.containerTotal?iteml.containerTotal:0}</Avatar>}/>
                                          </ProCard>
                                        </ProCard>
                                    </StatisticCard>
                                  </Col>
                                ):null
                              )
                            ))
                          }
                        </Row>
                      </ProCard>
                    </ProCard>
                  </Col>
                )
              )
            }
          </Row>
        </ProCard>
        <ProCard title="资源信息" colSpan={{md: 12, sm: 24}} style={{overflowY:"auto",height:"90vh"}}>
          <RcResizeObserver
            key="resize-observer1"
            onResize={(offset: { width: number; }) => {
              setResponsive(offset.width < 640);
            }}
          >
            <StatisticCard
              title="运行资源信息"
              direction={responsive ? 'column' : undefined}
              bordered
              headerBordered
              footer={
                <ChartTable data={[
                  {
                    key: '1',
                    name: 'CPU占用率',
                    percent: deviceListInfoData.runResTotals?.cpuRateTotal?deviceListInfoData.runResTotals.cpuRateTotal/100:0,
                    flow: 2335,
                  },
                  {
                    key: '2',
                    name: '内存占用率',
                    percent: deviceListInfoData.runResTotals?.memRateTotal?deviceListInfoData.runResTotals.memRateTotal/100:0,
                    flow: 3245,
                  },
                  {
                    key: '3',
                    name: '磁盘占用率',
                    percent: deviceListInfoData.runResTotals?.diskRateTotal?deviceListInfoData.runResTotals.diskRateTotal/100:0,
                    flow: 3445,
                  },
                ]}/>
              }
            >
              <StatisticCard.Group>
                <StatisticCard
                  statistic={{
                    title: '设备总数',
                    value: deviceListInfoData.runResTotals?.deviceTotal?deviceListInfoData.runResTotals.deviceTotal:0,
                    suffix: '台',
                  }}
                />
                <Divider type={responsive ? 'horizontal' : 'vertical'}/>
                <StatisticCard
                  statistic={{
                    title: '在线设备',
                    value: deviceListInfoData.runResTotals?.onlineDeviceTotal?deviceListInfoData.runResTotals.onlineDeviceTotal:0,
                    suffix: '台',
                  }}
                  chart={
                    <MiniRingProgress
                      percent={(deviceListInfoData.runResTotals?.deviceTotal !== 0 && deviceListInfoData.runResTotals?.onlineDeviceTotal !== 0)?deviceListInfoData.runResTotals?.onlineDeviceTotal/deviceListInfoData.runResTotals?.deviceTotal:0}
                      bgColor="#30BF78"
                      ringProps={{
                        statistic: {
                          content: false,
                        },
                        innerRadius: 0.7,
                      }}
                    />
                  }
                  chartPlacement="left"
                />
                {/* <Divider type={responsive ? 'horizontal' : 'vertical'}/> */}
                <StatisticCard
                  statistic={{
                    title: '离线设备',
                    value: deviceListInfoData.runResTotals?.offlineDeviceTotal?deviceListInfoData.runResTotals.offlineDeviceTotal:0,
                    suffix: '台',
                  }}
                  chart={
                    <MiniRingProgress
                      percent={(deviceListInfoData.runResTotals?.deviceTotal !== 0 && deviceListInfoData.runResTotals?.offlineDeviceTotal !== 0)?deviceListInfoData.runResTotals?.offlineDeviceTotal/deviceListInfoData.runResTotals?.deviceTotal:0}
                      bgColor="#7277B"
                      ringProps={{
                        statistic: {
                          content: false,
                        },
                        innerRadius: 0.7,
                      }}
                    />
                  }
                  chartPlacement="left"
                />
                {/* <Divider type={responsive ? 'horizontal' : 'vertical'}/> */}
                <StatisticCard
                  statistic={{
                    title: '告警设备',
                    value: deviceListInfoData.runResTotals?.alarmDeviceTotal?deviceListInfoData.runResTotals.alarmDeviceTotal:0,
                    suffix: '台',
                  }}
                  chart={
                    <MiniRingProgress
                      percent={(deviceListInfoData.runResTotals?.deviceTotal !== 0 && deviceListInfoData.runResTotals?.alarmDeviceTotal !== 0)?deviceListInfoData.runResTotals?.alarmDeviceTotal/deviceListInfoData.runResTotals?.deviceTotal:0}
                      bgColor="#EF4136"
                      ringProps={{
                        statistic: {
                          content: false,
                        },
                        innerRadius: 0.7,
                      }}
                    />
                  }
                  chartPlacement="left"
                />
              </StatisticCard.Group>
            </StatisticCard>
          </RcResizeObserver>
          <RcResizeObserver
            key="resize-observer2"
            onResize={(offset) => {
              setResponsive(offset.width < 640);
            }}
          >
            <ProCard
              title="运行场景信息"
              style={{marginTop: "15px"}}
              bordered
              headerBordered
            >
              <StatisticCard.Group
                title="场景统计"
                direction={responsive ? 'column' : undefined}
              >
                <StatisticCard
                  layout="center"
                  statistic={{
                    title: <Avatar size="large" style={{color:'#ffffff',backgroundColor:'#6395F9'}}>{deviceListInfoData.runSceneTotals?.sceneTotal?deviceListInfoData.runSceneTotals.sceneTotal:0}</Avatar>,
                    value: " ",
                    description: <h5>场景总数</h5>,
                  }}
                />
                <Divider type={responsive ? 'horizontal' : 'vertical'}/>
                <StatisticCard
                  layout="center"
                  statistic={{
                    title: <Avatar size="large" style={{color:'#ffffff',backgroundColor:'#6395F9'}}>{deviceListInfoData.runSceneTotals?.onlineSceneTotal?deviceListInfoData.runSceneTotals.onlineSceneTotal:0}</Avatar>,
                    value: " ",
                    description: <h5>在线场景</h5>,
                  }}
                />
                <Divider type={responsive ? 'horizontal' : 'vertical'}/>
                <StatisticCard
                  layout="center"
                  statistic={{
                    title: <Avatar size="large" style={{color:'#ffffff',backgroundColor:'#6395F9'}}>{deviceListInfoData.runSceneTotals?.offlineSceneTotal?deviceListInfoData.runSceneTotals.offlineSceneTotal:0}</Avatar>,
                    value: " ",
                    description: <h5>在线场景</h5>,
                  }}
                />
                <Divider type={responsive ? 'horizontal' : 'vertical'}/>
                <StatisticCard
                  layout="center"
                  statistic={{
                    title: <Avatar size="large" style={{color:'#ffffff',backgroundColor:'#6395F9'}}>{deviceListInfoData.runSceneTotals?.alarmSceneTotal?deviceListInfoData.runSceneTotals.alarmSceneTotal:0}</Avatar>,
                    value: " ",
                    description: <h5>告警场景</h5>,
                  }}
                />
              </StatisticCard.Group>
              <StatisticCard.Group
                title="容器统计"
                direction={responsive ? 'column' : undefined}
              >
                <StatisticCard
                  layout="center"
                  statistic={{
                    title: <Avatar size="large" style={{color:'#ffffff',backgroundColor:'#6395F9'}}>{deviceListInfoData.runContainerTotals?.containerTotal?deviceListInfoData.runContainerTotals.containerTotal:0}</Avatar>,
                    value: " ",
                    description: <h5>容器总数</h5>,
                  }}
                />
                <Divider type={responsive ? 'horizontal' : 'vertical'}/>
                <StatisticCard
                  layout="center"
                  statistic={{
                    title: <Avatar size="large" style={{color:'#ffffff',backgroundColor:'#6395F9'}}>{deviceListInfoData.runContainerTotals?.onlineContainerTotal?deviceListInfoData.runContainerTotals.onlineContainerTotal:0}</Avatar>,
                    value: " ",
                    description: <h5>在线容器</h5>,
                  }}
                />
                <Divider type={responsive ? 'horizontal' : 'vertical'}/>
                <StatisticCard
                  layout="center"
                  statistic={{
                    title: <Avatar size="large" style={{color:'#ffffff',backgroundColor:'#6395F9'}}>{deviceListInfoData.runContainerTotals?.offlineContainerTotal?deviceListInfoData.runContainerTotals.offlineContainerTotal:0}</Avatar>,
                    value: " ",
                    description: <h5>离线容器</h5>,
                  }}
                />
                <Divider type={responsive ? 'horizontal' : 'vertical'}/>
                <StatisticCard
                  layout="center"
                  statistic={{
                    title: <Avatar size="large" style={{color:'#ffffff',backgroundColor:'#6395F9'}}>{deviceListInfoData.runContainerTotals?.alarmContainerTotal?deviceListInfoData.runContainerTotals.alarmContainerTotal:0}</Avatar>,
                    value: " ",
                    description: <h5>告警容器</h5>,
                  }}
                />
              </StatisticCard.Group>
            </ProCard>
          </RcResizeObserver>
          <RcResizeObserver
            key="resize-observer3"
            onResize={(offset) => {
              setResponsive(offset.width < 640);
            }}
          >
            <StatisticCard
              style={{marginTop: "15px"}}
              title="告警信息"
              direction={responsive ? 'column' : undefined}
              bordered
              headerBordered
              footer={
                <>
                  <StatisticCard title="告警趋势图" chart={<DeviceLine  data={[]} height={250} autoFit={true}/>}/>
                </>

              }
            >
              <StatisticCard.Group>
                <StatisticCard
                  layout="center"
                  statistic={{
                    title: <Avatar size="large" style={{color:'#ffffff',backgroundColor:'#6395F9'}}>{deviceListInfoData.alarmTotals?.riskTotal?deviceListInfoData.alarmTotals.riskTotal:0}</Avatar>,
                    value: " ",
                    description: <h5>失陷</h5>,
                  }}
                />
                <Divider type={responsive ? 'horizontal' : 'vertical'}/>
                <StatisticCard
                  layout="center"
                  statistic={{
                    title: <Avatar size="large" style={{color:'#ffffff',backgroundColor:'#6395F9'}}>{deviceListInfoData.alarmTotals?.highRiskTotal?deviceListInfoData.alarmTotals.highRiskTotal:0}</Avatar>,
                    value: " ",
                    description: <h5>高危</h5>,
                  }}
                />
                <Divider type={responsive ? 'horizontal' : 'vertical'}/>
                <StatisticCard
                  layout="center"
                  statistic={{
                    title: <Avatar size="large" style={{color:'#ffffff',backgroundColor:'#6395F9'}}>{deviceListInfoData.alarmTotals?.midRiskTotal?deviceListInfoData.alarmTotals.midRiskTotal:0}</Avatar>,
                    value: " ",
                    description: <h5>中危</h5>,
                  }}
                />
                <Divider type={responsive ? 'horizontal' : 'vertical'}/>
                <StatisticCard
                  layout="center"
                  statistic={{
                    title: <Avatar size="large" style={{color:'#ffffff',backgroundColor:'#6395F9'}}>{deviceListInfoData.alarmTotals?.lowerRiskTotal?deviceListInfoData.alarmTotals.lowerRiskTotal:0}</Avatar>,
                    value: " ",
                    description: <h5>低危</h5>,
                  }}
                />
              </StatisticCard.Group>
            </StatisticCard>
          </RcResizeObserver>
        </ProCard>
        <ModalDeviceInfo visible={modalDeviceInfoVisible} current={deviceInfoData} onCancel={onDeviceInfoClose}/>
      </ProCard>
    </PageContainer>
  );
};

export default DeviceMonitor;
