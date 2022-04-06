import React, { useEffect, useState } from 'react';
import { Col, Descriptions, Divider, Modal, Row } from 'antd';
import { StatisticCard } from '@ant-design/pro-card';
import moment from 'moment';
import RcResizeObserver from 'rc-resize-observer';
import DeviceLine from './charts/Line';
import Field from '@ant-design/pro-field';

const { Statistic } = StatisticCard;

/**
 * 设备信息类
 */
interface deviceData {
  serverId:string;
  deviceName:string;
  type:number;
  timestamp:number;
  online:number;
  load:{
    load1:number,
    load5:number,
    load15:number,
  };
  cpu:[{
    name:string,
    cpuRatio:number,
  }];
  mem:[{
    name:string,
    userRatio:number,
  }];
  disk:[{
    name:string,
    diskRatio:number,
  }];
  diskInOut:[{
    name:string,
    read:number,
    write:number,
  }];
  intfs:[{
    name:string,
    in:number,
    out:number,
  }];
}

/**
 * 定义从父组件传过来的参数
 */
interface CreateFormProps {
  visible: boolean;
  onCancel: () => void;
  current: Partial<deviceData>;
}

const ModalDeviceInfo: React.FC<CreateFormProps> = (props) => {
  const { visible, onCancel, current } = props;
  const [responsive, setResponsive] = useState(false);
  const [intfsData, setIntfsData] = useState<any>([]);
  const [loadInfoData, setLoadInfoData] = useState<any>([]);
  const [cpuData, setCpuData] = useState<any>([]);
  const [memData, setMemData] = useState<any>([]);
  const [diskData, setDiskData] = useState<any>([]);
  const [diskInOutData, setDiskInOutData] = useState<any>([]);

  /**
   * 监听设备数据变化，实时更新折线图
   */
  useEffect(() => {
    if (current) {
      let tempTData = current?.timestamp || 0;
      let tempTime = moment(tempTData*1000).format('YYYY-MM-DD hh:mm:ss');
      /**
       * 设备负载曲线数据
       */
      let tempShiftArrLoad = loadInfoData;
      if (tempShiftArrLoad.length >= 60) {
        tempShiftArrLoad.shift();
        tempShiftArrLoad.shift();
        tempShiftArrLoad.shift();
        setLoadInfoData(tempShiftArrLoad);
      }
      let tempLloadInfoData: any[] = [];
      tempLloadInfoData.push({
        year: current.timestamp ? tempTime : null,
        value: current.load?.load1,
        category: '1分钟负载',
      });
      tempLloadInfoData.push({
        year: current.timestamp ? tempTime : null,
        value: current.load?.load5,
        category: '5分钟负载',
      });
      tempLloadInfoData.push({
        year: current.timestamp ? tempTime : null,
        value: current.load?.load15,
        category: '15分钟负载',
      });
      setLoadInfoData([...loadInfoData, ...tempLloadInfoData]);

      /**
       * 接口曲线数据
       */
       let tempShiftArr = intfsData;
       if (tempShiftArr.length >= 80) {
         tempShiftArr.shift();
         tempShiftArr.shift();
         tempShiftArr.shift();
         tempShiftArr.shift();
         setIntfsData(tempShiftArr);
       }
       let tempIntfs:any = [];
       current.intfs?.map(intfs => {
         tempIntfs.push({
           year: current.timestamp ? tempTime : null,
           value: intfs.in,
           category: intfs.name + '(接收)',
         });
         tempIntfs.push({
           year: current.timestamp ? tempTime : null,
           value: intfs.out,
           category: intfs.name + '(发送)',
         });
       });
       setIntfsData([...intfsData, ...tempIntfs]);

       /**
       * CPU曲线数据
       */
      let tempShiftArrCpu = cpuData;
      if (tempShiftArrCpu.length >= 20) {
        tempShiftArrCpu.shift();
        setCpuData(tempShiftArrCpu);
      }
      let tempCpuData:any = [];
      current.cpu?.map(cpus => {
        tempCpuData.push({
          year: current.timestamp ? tempTime : null,
          value: cpus.cpuRatio,
          category: cpus.name,
        });
      });
      setCpuData([...cpuData, ...tempCpuData]);

      /**
       * 内存曲线数据
       */
       let tempShiftArrMem = memData;
       if (tempShiftArrMem.length >= 20) {
         tempShiftArrMem.shift();
         setMemData(tempShiftArrMem);
       }
       let tempMemData:any = [];
       current.mem?.map(mems => {
        tempMemData.push({
           year: current.timestamp ? tempTime : null,
           value: mems.userRatio,
           category: mems.name,
         });
       });
       setMemData([...memData, ...tempMemData]);

       /**
       * 磁盘曲线数据
       */
        let tempShiftArrDisk = diskData;
        if (tempShiftArrDisk.length >= 20) {
          tempShiftArrDisk.shift();
          setDiskData(tempShiftArrDisk);
        }
        let tempDiskData:any = [];
        current.mem?.map(disks => {
          tempDiskData.push({
            year: current.timestamp ? tempTime : null,
            value: disks.userRatio,
            category: disks.name,
          });
        });
        setDiskData([...diskData, ...tempDiskData]);

        /**
       * 磁盘读取速率曲线数据
       */
         let tempShiftArrDiskInOut = diskInOutData;
         if (tempShiftArrDiskInOut.length >= 40) {
           tempShiftArrDiskInOut.shift();
           tempShiftArrDiskInOut.shift();
           setDiskInOutData(tempShiftArrDiskInOut);
         }
         let tempDiskInOutData:any = [];
         current.diskInOut?.map(diskInOuts => {
           tempDiskInOutData.push({
             year: current.timestamp ? tempTime : null,
             value: diskInOuts.read,
             category: diskInOuts.name + '(读取)',
           });
           tempDiskInOutData.push({
            year: current.timestamp ? tempTime : null,
            value: diskInOuts.write,
            category: diskInOuts.name + '(写入)',
          });
         });
         setDiskInOutData([...diskInOutData, ...tempDiskInOutData]);
    }
  }, [props.current]);


  return (
    <>
      <Modal
        width={'100vw'}
        style={{ top: "10px" }}
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
            <StatisticCard>
              {/* <Statistic value={current.deviceName ? current.deviceName : " "} /> */}
              <Descriptions column={2} size="small">
                <Descriptions.Item label="设备名称" contentStyle={{paddingRight:"10px"}}>
                    {/* <MiniProgress percent={iteml.load1?iteml.load1/100:0}/> */}
                    <Field text={current.deviceName ? current.deviceName : " "} valueType="text" mode="read" />
                </Descriptions.Item>
              </Descriptions>
            </StatisticCard>
            <Divider type={responsive ? 'horizontal' : 'vertical'} />
            <StatisticCard>
              {/* <Statistic value={current.type === 1 ? "物理服务器" : current.type === 2 ? "容器" : current.type === 3 ? "虚拟机" : " "} /> */}
              <Descriptions column={2} size="small">
                <Descriptions.Item label="节点类型" contentStyle={{paddingRight:"10px"}}>
                    {/* <MiniProgress percent={iteml.load1?iteml.load1/100:0}/> */}
                    <Field text={current.type === 1 ? "物理服务器" : current.type === 2 ? "容器" : current.type === 3 ? "虚拟机" : " "} valueType="text" mode="read" />
                </Descriptions.Item>
              </Descriptions>
            </StatisticCard>
            <Divider type={responsive ? 'horizontal' : 'vertical'} />
            <StatisticCard>
              {/* <Statistic value={current.online === 1 ? "在线" : "离线"} status={current.online === 1 ? "success" : "default"} /> */}
              <Descriptions column={2} size="small">
                <Descriptions.Item label="设备状态" contentStyle={{paddingRight:"10px"}}>
                    {/* <MiniProgress percent={iteml.load1?iteml.load1/100:0}/> */}
                    <Field text={current.online === 1 ? "在线" : "离线"} valueType="text" mode="read" />
                </Descriptions.Item>
              </Descriptions>
            </StatisticCard>
          </StatisticCard.Group>
        </RcResizeObserver>
        <Row gutter={8}>
          <Col span={12}>
            <RcResizeObserver
              key="resize-observer2"
              onResize={(offset) => {
                setResponsive(offset.width < 640);
              }}
            >
              <StatisticCard title="设备负载" chart={<DeviceLine data={loadInfoData} height={200} autoFit={true} />} style={{ marginTop: "15px" }} bordered />
            </RcResizeObserver>
          </Col>
          <Col span={12}>
            <RcResizeObserver
              key="resize-observer4"
              onResize={(offset) => {
                setResponsive(offset.width < 640);
              }}
            >
              <StatisticCard title="端口上下行速率" chart={<DeviceLine data={intfsData} height={200} autoFit={true} />} style={{ marginTop: "15px" }} bordered />
            </RcResizeObserver>
          </Col>
        </Row>
        <Row gutter={8}>
          <Col span={12}>
            <RcResizeObserver
              key="resize-observer5"
              onResize={(offset) => {
                setResponsive(offset.width < 640);
              }}
            >
              <StatisticCard title="CPU占比" chart={<DeviceLine data={cpuData} height={200} autoFit={true} />} style={{ marginTop: "15px" }} bordered />
            </RcResizeObserver>
          </Col>
          <Col span={12}>
            <RcResizeObserver
              key="resize-observer6"
              onResize={(offset) => {
                setResponsive(offset.width < 640);
              }}
            >
              <StatisticCard title="内存占比" chart={<DeviceLine data={memData} height={200} autoFit={true} />} style={{ marginTop: "15px" }} bordered />
            </RcResizeObserver>
          </Col>
        </Row>
        <Row gutter={8}>
          <Col span={12}>
            <RcResizeObserver
              key="resize-observer6"
              onResize={(offset) => {
                setResponsive(offset.width < 640);
              }}
            >
              <StatisticCard title="磁盘占比" chart={<DeviceLine data={diskData} height={200} autoFit={true} />} style={{ marginTop: "15px" }} bordered />
            </RcResizeObserver>
          </Col>
          <Col span={12}>
            <RcResizeObserver
              key="resize-observer6"
              onResize={(offset) => {
                setResponsive(offset.width < 640);
              }}
            >
              <StatisticCard title="磁盘读写速率" chart={<DeviceLine data={diskInOutData} height={200} autoFit={true} />} style={{ marginTop: "15px" }} bordered />
            </RcResizeObserver>
          </Col>
        </Row>
      </Modal>
    </>
  );
};

export default ModalDeviceInfo;
