//对话框
import React, { useEffect, useState } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import ProCard, { StatisticCard } from "@ant-design/pro-card";
import RcResizeObserver from 'rc-resize-observer';
import './index.less';
import { Col, Row, Select, Space } from "antd";
import TreeMapChart from "../components/charts/TreeMapChart";

import Stomp from "stompjs"
import SockJS from 'sockjs-client';
import MiniProgress from './charts/MiniProgress';

const { Statistic } = StatisticCard;

let stompClient: any = null;
let isInit: any = true;
let appWsId: any = null;

//定义组件对象
const DeviceMonitor: React.FC<{}> = () => {
  const [responsive, setResponsive] = useState(false);
  const [deviceDiskData, setDeviceDiskData] = useState([]);
  const [deviceMemData, setDeviceMemData] = useState([]);
  const [deviceCpuData, setDeviceCpuData] = useState([]);
  const [appDiskData, setAppDiskData] = useState([]);
  const [appMemData, setAppMemData] = useState([]);
  const [appCpuData, setAppCpuData] = useState([]);
  const [showAppData, setShowAppData] = useState({});
  const [showDeviceData, setShowDeviceData] = useState({});
  const [showTotalData, setShowTotalData] = useState({});

  useEffect(() => {
    stompClick();
    return () => {
      console.log(`组件销毁回调`, appWsId);
      if (appWsId) {
        stompClient.unsubscribe(appWsId.id);
      }
    }
  }, []);

  // const data = [
  //   {children:[{name:"bd4b914ed9084c1f8c0b318d36e03c67",value:10}],name:"192.168.1.3",value:15},
  //   {children:[{name:"bd4b914ed9084c1f8c0b318d36e03c67",value:20}],name:"192.168.1.2",value:25}
  // ];

  const stompClick = () => {
    console.log(`object`, appWsId);
    let ws = new SockJS('stomp');
    stompClient = Stomp.over(ws);
    stompClient.heartbeat.outgoing = 200;
    stompClient.heartbeat.incoming = 0;
    stompClient.debug = null;

    stompClient.connect({}, function () {
      //订阅列表数据
      appWsId = stompClient.subscribe("/app/getResponse", function (res: any) {
        console.log("-----当前订阅收到应用监视信息", res.body);
        let tempAppData = JSON.parse(res.body);
        setDeviceDiskData(tempAppData.device.disk);
        setDeviceMemData(tempAppData.device.mem);
        setDeviceCpuData(tempAppData.device.cpu);
        setAppDiskData(tempAppData.app.disk);
        setAppMemData(tempAppData.app.mem);
        setAppCpuData(tempAppData.app.cpu);
        console.log(`tempAppData.total  `, tempAppData.total);
        setShowTotalData(tempAppData.total);
        if(isInit){
          isInit = false;
          let tempData = {
            title:"CPU占比",
            data: tempAppData.device.cpu,
          }
          setShowDeviceData(tempData);
          let tempData1 = {
            title:"CPU占比",
            data: tempAppData.app.cpu,
          }
          setShowAppData(tempData1);
        }
      })
    })
  }

  const handleChangeDevice = (value: string) => {
    if(value === "001"){
      let tempData = {
        title:"CPU占比",
        data: deviceCpuData,
      }
      setShowDeviceData(tempData);
    }else if(value === "002"){
      let tempData = {
        title:"内存占比",
        data: deviceMemData,
      }
      setShowDeviceData(tempData);
    }else if(value === "003"){
      let tempData = {
        title:"磁盘使用率",
        data: deviceDiskData,
      }
      setShowDeviceData(tempData);
    }
  }

  const handleChangeApp = (value: string) => {
    if(value === "001"){
      let tempData = {
        title:"CPU占比",
        data: appCpuData,
      }
      setShowAppData(tempData);
    }else if(value === "002"){
      let tempData = {
        title:"内存占比",
        data: appMemData,
      }
      setShowAppData(tempData);
    }else if(value === "003"){
      let tempData = {
        title:"磁盘使用率",
        data: appDiskData,
      }
      setShowAppData(tempData);
    }
  }

  return (
    <PageContainer
      pageHeaderRender={false}
    >
      <ProCard gutter={8} split="vertical" ghost>
        <ProCard title="服务器视图" colSpan={{ md: 12, sm: 24 }} bordered style={{ scrollPaddingBlock: "auto" }} extra={(
          <Select defaultValue="001" style={{width:"160px"}}  options={[{label:"CPU",value:"001"},{label:"内存",value:"002"},{label:"磁盘",value:"003"}]} onChange={handleChangeDevice} />
        )}>
          <RcResizeObserver
            key="resize-observer2"
            onResize={(offset) => {
              setResponsive(offset.width < 640);
            }}
          >
            <TreeMapChart data={showDeviceData} />
          </RcResizeObserver>
        </ProCard>
        <ProCard title="应用视图" colSpan={{ md: 12, sm: 24 }} bordered style={{ scrollPaddingBlock: "auto" }} extra={(
          <Select defaultValue="001" style={{width:"160px"}} options={[{label:"CPU",value:"001"},{label:"内存",value:"002"},{label:"磁盘",value:"003"}]} onChange={handleChangeApp} />
        )}>
          <RcResizeObserver
            key="resize-observer2"
            onResize={(offset) => {
              setResponsive(offset.width < 640);
            }}
          >
            <TreeMapChart data={showAppData} />
          </RcResizeObserver>
        </ProCard>
      </ProCard>
      <ProCard gutter={8} split="vertical" ghost style={{paddingTop:"10px"}}>
        <ProCard title="CPU" colSpan={{ md: 8, sm: 24 }} bordered split="horizontal">
          {
            showTotalData.cpu?.map((cpuObj:any)=>(
              <StatisticCard
                //title={cpuObj.name}
                statistic={{
                  value:cpuObj.name,
                  description: (
                    <Space>
                      <Statistic title="当前使用率" value={cpuObj.value} />
                      <Statistic title="总数" value={cpuObj.total} />
                    </Space>
                  ),
                }}
                chart={
                  <>
                    <MiniProgress percent={cpuObj.total > 0?cpuObj.value/cpuObj.total:0} />
                  </>
                }
              />
            ))
          }
        </ProCard>
        <ProCard title="内存" colSpan={{ md: 8, sm: 24 }} bordered split="horizontal">
         {
            showTotalData.mem?.map((memObj:any)=>(
              <StatisticCard
                //title={cpuObj.name}
                statistic={{
                  value:memObj.name,
                  description: (
                    <Space>
                      <Statistic title="当前占用" value={memObj.value} />
                      <Statistic title="总数" value={memObj.total} />
                    </Space>
                  ),
                }}
                chart={
                  <>
                    <MiniProgress percent={memObj.total > 0?memObj.value/memObj.total:0} />
                  </>
                }
              />
            ))
          }
        </ProCard>
        <ProCard title="磁盘" colSpan={{ md: 8, sm: 24 }} bordered split="horizontal">
         {
            showTotalData.disk?.map((diskObj:any)=>(
              <StatisticCard
                //title={cpuObj.name}
                statistic={{
                  value:diskObj.name,
                  description: (
                    <Space>
                      <Statistic title="当前占用" value={diskObj.value} />
                      <Statistic title="总数" value={diskObj.total} />
                    </Space>
                  ),
                }}
                chart={
                  <>
                    <MiniProgress percent={diskObj.total > 0?diskObj.value/diskObj.total:0} />
                  </>
                }
              />
            ))
          }
        </ProCard>
      </ProCard>
    </PageContainer>
  );
};

export default DeviceMonitor;
