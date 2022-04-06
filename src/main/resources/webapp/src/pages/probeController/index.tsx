//对话框
import {
  Row,
  Col,
  Divider,
  Button
} from 'antd';
import React, { useRef} from 'react';
import ProForm, { ProFormText } from '@ant-design/pro-form';


//定义组件对象
const ProbeController: React.FC<{}> = () => {
  const formRef = useRef();

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
              <Divider orientation="left">基本信息</Divider>
              <Row gutter={16}>
                <Col span={8}>
                  <ProFormText
                    width="md"
                    name="tzmc"
                    label="源探针名称"
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
                    name="mdtzmc"
                    label="目的探针名称"
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
                  <ProFormText width="md" name="tzsl" label="探针数量" />
                </Col>
                <Col span={8}>
                  <ProFormText
                    width="md"
                    name="ytzip"
                    label="源探针IP"
                    placeholder="请输入名称"
                    allowClear={false}
                    rules={[
                      {
                        required: true,
                        message: '请输入探针IP',
                      },
                    ]}/>
                </Col>
                <Col span={8}>
                  <ProFormText
                    width="md"
                    name="mdtzip"
                    label="目的探针IP"
                    placeholder="请输入名称"
                    allowClear={false}
                    rules={[
                      {
                        required: true,
                        message: '请输入探针IP',
                      },
                    ]}/>
                </Col>
                <Col span={8}>
                  <Button type={"primary"} onClick={()=>{
                    console.log("提交参数打印",formRef?.current.getFieldsValue(["tzmc","mdtzmc","tzsl","ytzip","mdtzip"]))
                  }}>开始</Button>
                </Col>
              </Row>
              <Divider orientation="left">测量性能</Divider>
              <Row gutter={16}>
                <Col span={8}>
                  <ProFormText width="md" name="ltl" label="连通率" />
                </Col>
                <Col span={16}>
                  <ProFormText width="md" name="lj" label="路径" />
                </Col>
                <Col span={8}>
                  <ProFormText width="md" name="tcpttl" label="TCP性能：吞吐量" />
                </Col>
                <Col span={8}>
                  <ProFormText width="md" name="tcpsy" label="时延(ms)" />
                </Col>
                <Col span={8}>
                  <ProFormText width="md" name="tcpdbl" label="丢包率(%)" />
                </Col>
                <Col span={8}>
                  <ProFormText width="md" name="udpttl" label="UDP性能：吞吐量" />
                </Col>
                <Col span={8}>
                  <ProFormText width="md" name="udpys" label="延时(ms)" />
                </Col>
                <Col span={8}>
                  <ProFormText width="md" name="udpdbl" label="丢包率(%)" />
                </Col>
                <Col span={8}>
                  <ProFormText width="md" name="pjlyl" label="平均CPU利用率(%)" />
                </Col>
                <Col span={8}>
                  <ProFormText width="md" name="shlyl" label="瞬时CPU利用率(%)" />
                </Col>
                <Col span={8}>
                  <ProFormText width="md" name="nclyl" label="内存利用率(%)" />
                </Col>
              </Row>
            </ProForm>
          </Col>
        </Row>
    </>
  );
};

export default ProbeController;
