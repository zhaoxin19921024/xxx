import React, {useEffect, useState} from 'react';
import {Divider, Modal} from 'antd';
import { StatisticCard } from '@ant-design/pro-card';
import moment from 'moment';
import RcResizeObserver from 'rc-resize-observer';
import DeviceLine from './charts/Line';

const { Statistic } = StatisticCard;

interface CreateFormProps {
  visible: boolean;
  onCancel: () => void;
  current: {};
}

const ModalDeviceInfo: React.FC<CreateFormProps> = (props) => {
  const {visible, onCancel} = props;
  const [responsive, setResponsive] = useState(false);
  const [intfsData,setIntfsData] = useState([]);

  useEffect(() => {
  }, [props.visible]);

  useEffect(() => {
    if(props.current){
      let tempShiftArr = intfsData;
      if(tempShiftArr.length >= 80){
        tempShiftArr.shift();
        tempShiftArr.shift();
        tempShiftArr.shift();
        tempShiftArr.shift();
        setIntfsData(tempShiftArr);
      }
      let tempIntfs = [];
      let tempTime = moment(props.current.timestamp).format('YYYY-MM-DD hh:mm:ss');
      console.log("tempTime === ",tempTime);
      props.current.net?.intfs?.map(item => {
        tempIntfs.push({
          year:props.current.timestamp?tempTime:null,
          value:item.in,
          category:item.name+'(接收)',
        });
        tempIntfs.push({
          year:props.current.timestamp?tempTime:null,
          value:item.out,
          category:item.name+'(发送)',
        });
      });
      setIntfsData([...intfsData,...tempIntfs]);
    }
  }, [props.current]);


  return (
    <>
      <Modal
        width={'100vw'}
        style={{top:"10px"}}
        destroyOnClose
        title="设备详情"
        visible={visible}
        onCancel={() => onCancel()}
        footer={null}
      >
        <RcResizeObserver
          key="resize-observer1"
          onResize={(offset) => {
            setResponsive(offset.width < 640);
          }}
        >
          <StatisticCard.Group direction={responsive ? 'column' : undefined} bordered>
            <StatisticCard title="设备名称">
              <Statistic value={props.current.deviceName?props.current.deviceName:" "} />
            </StatisticCard>
            <Divider type={responsive ? 'horizontal' : 'vertical'}/>
            <StatisticCard title="节点类型">
              <Statistic value={props.current.type === 1?"物理服务器":props.current.type === 2?"容器":props.current.type === 3?"虚拟机":" "} />
            </StatisticCard>
            <Divider type={responsive ? 'horizontal' : 'vertical'}/>
            <StatisticCard title="设备状态">
              <Statistic value={props.current.online === 1?"在线":"离线"} status={props.current.online === 1?"success":"default"}/>
            </StatisticCard>
          </StatisticCard.Group>
        </RcResizeObserver>
        <RcResizeObserver
          key="resize-observer2"
          onResize={(offset) => {
            setResponsive(offset.width < 640);
          }}
        >
          <StatisticCard.Group direction={responsive ? 'column' : undefined} bordered style={{marginTop:"15px"}}>
            <StatisticCard title="设备负载" chart={<DeviceLine data={[]} height={200} autoFit={true} />} style={{marginTop:"15px"}} bordered />
            {/*<StatisticCard
              bordered
              statistic={{
                style: {fontSize: "16px"},
                value: " ",
                description: <Statistic title="1分钟负载" value=" "/>
              }}
              split="horizontal"
              style={{padding: "10px"}}
              chart={<MiniProgress percent={0}/>}
            />
            <StatisticCard
              bordered
              statistic={{
                style: {fontSize: "16px"},
                value: " ",
                description: <Statistic title="5分钟负载" value=" "/>
              }}
              split="horizontal"
              style={{padding: "10px"}}
              chart={<MiniProgress percent={0}/>}
            />
            <StatisticCard
              bordered
              statistic={{
                style: {fontSize: "16px"},
                value: " ",
                description: <Statistic title="15分钟负载" value=" "/>
              }}
              split="horizontal"
              style={{padding: "10px"}}
              chart={<MiniProgress percent={0}/>}
            />*/}
          </StatisticCard.Group>
        </RcResizeObserver>
        {/*<RcResizeObserver
          key="resize-observer3"
          onResize={(offset) => {
            setResponsive(offset.width < 640);
          }}
        >
          <StatisticCard title="端口状态" bordered style={{marginTop:"15px"}}>
            <ChartTablePort />
          </StatisticCard>
        </RcResizeObserver>*/}
        <RcResizeObserver
          key="resize-observer4"
          onResize={(offset) => {
            setResponsive(offset.width < 640);
          }}
        >
          <StatisticCard title="端口上下行速率" chart={<DeviceLine data={intfsData} height={200} autoFit={true} />} style={{marginTop:"15px"}} bordered />
        </RcResizeObserver>
        <RcResizeObserver
          key="resize-observer5"
          onResize={(offset) => {
            setResponsive(offset.width < 640);
          }}
        >
          <StatisticCard title="带宽利用率" chart={<DeviceLine data={[]} height={200} autoFit={true} />} style={{marginTop:"15px"}} bordered />
        </RcResizeObserver>
        <RcResizeObserver
          key="resize-observer6"
          onResize={(offset) => {
            setResponsive(offset.width < 640);
          }}
        >
          <StatisticCard title="性能监控" chart={<DeviceLine  data={[]} height={200} autoFit={true} />} style={{marginTop:"15px"}} bordered />
        </RcResizeObserver>
      </Modal>
    </>
  );
};

export default ModalDeviceInfo;
