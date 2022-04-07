//对话框
import {
  Row,
  Col,
  Divider,
  Button,
  Tabs
} from 'antd';
import React, {useRef, useState} from 'react';
import ProForm, {ProFormSelect, ProFormText} from '@ant-design/pro-form';

const { TabPane } = Tabs;
//定义组件对象
const NetworkConfig: React.FC<{}> = () => {
  const formRef = useRef();

  const [activeKey,setActiveKey] = useState("1")

  const formValueChange = (target:any) =>{
    let key = Object.keys(target)[0];
    console.log(key);
  }

  const onFormLayoutChange =(changedValues:any, allValues:any) =>{
    console.log(changedValues, allValues)
    formValueChange(changedValues);
  }

  return (
    <>
      <Tabs defaultActiveKey={activeKey} onChange={(value)=>{
        setActiveKey(value);
      }}>
        <TabPane tab="路由器" key="1">
        </TabPane>
        <TabPane tab="防火墙" key="2">
        </TabPane>
      </Tabs>
      <Row gutter={6}>
        <Col span={12}>
          <ProForm
            {...{
              labelCol: { span: 10 },
              wrapperCol: { span: 12 },
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
              },
            }}
            onFinish = {async (values) => {
              console.log("提交参数打印",values);
            }}
            formRef={formRef}
            onValuesChange={onFormLayoutChange}
          >
            <Row gutter={16}>
              <Col span={8}>
                <ProFormText
                  width="md"
                  name="lyqmc"
                  label="路由器名称"
                  placeholder="请输入名称"
                  allowClear={false}
                  rules={[
                    {
                      required: true,
                      message: '请输入探针名称',
                    },
                  ]}/>
              </Col>
              <Col span={8}>
                <ProFormText
                  width="md"
                  name="asmc"
                  label="AS名称"
                  allowClear={false}
                />
              </Col>
              <Col span={8}>
                <ProFormText width="md" name="lyqs" label="路由器数" />
              </Col>
              <Col span={8}>
                <ProFormText
                  width="md"
                  name="glip"
                  label="管理IP"
                />
              </Col>
              <Col span={8}>
                <ProFormText
                  width="md"
                  name="asxx"
                  label="AS信息"
                />
              </Col>
              <Col span={8}>
                <ProFormText
                  width="md"
                  name="zzxts"
                  label="自治系统数"
                />
              </Col>
              <Col span={8}>
                <ProFormSelect
                  width="md"
                  request={async () => {
                    return [
                      {value:"bgp",label:"bgp"},
                      {value:"isis",label:"isis"},
                      {value:"ospf",label:"ospf"},
                      {value:"ospf6",label:"ospf6"},
                      {value:"rip",label:"rip"},
                      {value:"ripng",label:"ripng"},
                      {value:"zebra",label:"zebra"}
                    ];
                  }}
                  name="lyxy"
                  label="路由协议"
                />
              </Col>
              <Col span={8}>
                {activeKey !== "1" ? <ProFormText
                  width="md"
                  name="csll"
                  label="测试流量"
                /> : null}
              </Col>
              <Col span={8}>
                <Button type={"primary"} onClick={()=>{
                  console.log("提交参数打印",
                    formRef?.current.getFieldsValue([
                      "lyqmc",
                      "asmc",
                      "glip",
                      "asxx",
                      "zzxts",
                      "lyxy",
                      "csll"
                    ]))
                }}>开始</Button>
              </Col>
            </Row>
            {activeKey === "1" ?
              <Row gutter={16}>
                <Divider orientation="left">连通性测试结果</Divider>
                <Col span={12}>
                  <ProFormText width="md" name="ltl" label="连通率(%)" />
                </Col>
                <Col span={12}>
                  <Button type="primary" onClick={()=>{
                    console.log("连接测试");
                  }}>连接测试</Button>
                </Col>
                <Col span={24}>
                  <ProFormText width="lg" name="ltjd" label="连通节点" />
                </Col>
                <Col span={24}>
                  <ProFormText width="lg" name="bltjd" label="不连通节点" />
                </Col>
                <Divider />
                <Col span={24}>
                  <ProFormText width="lg" name="lj" label="被测路径" />
                </Col>
                <Col span={12}>
                  <Button type="primary" onClick={()=>{
                    console.log("路径测试");
                  }}>路径测试</Button>
                </Col>
              </Row>
              :
              <Row gutter={16}>
                <Divider orientation="left">性能测试</Divider>
                <Col span={12}>
                  <ProFormText width="md" name="ltl" label="连通率(%)" />
                </Col>
                <Col span={12}>
                  <ProFormText width="md" name="ttl" label="吞吐量(Mbps)" />
                </Col>
                <Col span={12}>
                  <ProFormText width="md" name="tcpsy" label="时延(ms)" />
                </Col>
                <Col span={12}>
                  <ProFormText width="md" name="tcpdbl" label="丢包率(%)" />
                </Col>
                <Col span={16}>
                  <ProFormText width="md" name="lj" label="路径" />
                </Col>
              </Row>
            }
          </ProForm>
        </Col>
      </Row>
    </>
  );
};

export default NetworkConfig;
