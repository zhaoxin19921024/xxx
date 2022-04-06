//对话框
import {
  Tree,
  Row,
  Col,
  Drawer,
  message,
  Divider,
  Empty,
  Tabs,
  Button,
  Menu,
  Modal,
  Upload,
  MessageArgsProps
} from 'antd';
import React, {useEffect, useRef, useState} from 'react';
import {PageContainer} from '@ant-design/pro-layout';
import { PlusOutlined, CaretLeftOutlined } from '@ant-design/icons';
import {DataNode, UpdateWlbsFl} from './data.d';
import {v4 as uuidV4} from 'uuid';
import ProTable, { ActionType } from '@ant-design/pro-table';
import ProForm, { ProFormText, ProFormSelect, ProFormRadio, ProFormTextArea, ProFormCheckbox } from '@ant-design/pro-form';


const { confirm } = Modal;

//定义组件对象
const TrafficManager: React.FC<{}> = () => {
  // const { visible,onCancel } = props;
  const [rightClickNodeTreeItem, setRightClickNodeTreeItem] = useState(null);
  const [formRadio, setFormRadio] = useState("");
  const [isAdd, setIsAdd] = useState(true)
  const formRef = useRef();
  const [useCscxyArr, setUseCscxyArr] = useState([]);

  const [psDisabled,setPsDisabled] = useState(false);
  const [idtDisabled,setIdtDisabled] = useState(false);
  const [sllxDisabled,setSllxDisabled] = useState(false);
  const [paramDisabled,setParamDisabled] = useState(false);

  const [vnfValue, setVnfValue] = useState("tgm");

  const formValueChange = (target) =>{
    let arr = ["1","3","5"];//概率模型数据
    let key = Object.keys(target)[0];
    switch (key){
      case "psmx":
        setPsDisabled(arr.includes(target["psmx"]))
        break;
      case "idtmx":
        setIdtDisabled(arr.includes(target["idtmx"]))
        break;
      case "sllx":
        setSllxDisabled(target["sllx"])
        break;
      case "zxfs":
        setFormRadio(target["zxfs"]);
        break;
      case "yycxy":
        let data = [
          { value:"1",label:"tcp" },
          { value:"2",label:"udp" },
        ]
        let arr_1 = data[0];
        let arr_2 = data[1];
        if(["3","4","5","6"].includes(target["yycxy"])){
          setUseCscxyArr([arr_1]);
        }else if (["1"].includes(target["yycxy"])){
          setUseCscxyArr([arr_2]);
        }else{
          setUseCscxyArr(data);
        }
        setParamDisabled(target["yycxy"]?true:false);
        break;
      case "vnf":
        setVnfValue(target["vnf"]);
        if(target["vnf"] == "ditg"){
          setParamDisabled(true);
        }else if(target["vnf"] == "tcpreplay"){
          setParamDisabled(true);
        }else if(target["vnf"] == "tgm"){
          setParamDisabled(false);
        }
        break;
    }
  }

  const onFormLayoutChange =(changedValues, allValues) =>{
    console.log(changedValues, allValues)
    formValueChange(changedValues);
  }

  return (
    <>
        <Row gutter={6}>
          <Col span={12}>
            {isAdd ?
              <ProForm
                {...{
                  labelCol: { span: 8 },
                  wrapperCol: { span: 12 },
                }}
                // layout = {'horizontal'}
                onFinish = {async (values) => {
                  console.log("提交参数打印",values);


                  // values.cjid = props.cjid;
                  // const sourceData = [...treeData];
                  // if(isAdd){
                  //   addTgrwcs(values).then(res=>{
                  //     if(res.code == 200){
                  //       message.success('新增成功');
                  //       setTreeData([]);
                  //       getTgTreeFun("");
                  //       // //手动更新树
                  //       // getTgTree({key: ""}).then(response => {
                  //       //   setTreeData(updateTreeData(sourceData, "", response.data));
                  //       //   setLoadedKeys([]);
                  //       // });
                  //     }
                  //   }).catch(res=>{})
                  // }else{
                  //   values.rwid = treeNode.key;
                  //   editTgrwcs(values).then(res=>{
                  //     if(res.code == 200){
                  //       message.success('编辑成功');
                  //     }
                  //   }).catch(res=>{})
                  // }
                }}
                formRef={formRef}
                onValuesChange={onFormLayoutChange}
              >
                <Divider orientation="left">基本信息</Divider>
                <Row gutter={16}>
                  <Col span={12}>
                    <ProFormText
                      width="md"
                      name="rwmc"
                      label="任务名称"
                      placeholder="请输入名称"
                      allowClear={false}
                      rules={[
                        {
                          required: true,
                          message: '请输入任务名称',
                        },
                      ]}/>
                  </Col>
                  <Col span={12}>
                    <ProFormSelect
                      width="md"
                      allowClear={isAdd}
                      request={async () => {
                        // return getSelectListByNodeType({id: props.cjid,nodeType:17}).then(response => {
                        //   return response.data;
                        // })
                        return [];
                      }}
                      name="vmb"
                      label="VMB"
                      // rules={[
                      //   {
                      //     required: true,
                      //     message: '请选择VMB',
                      //   },
                      // ]}
                    />
                  </Col>
                  <Col span={12}>
                    <ProFormSelect
                      width="md"
                      request={async () => [
                        { label: '关闭', value: 0 },
                        { label: '开启', value: 1 }
                      ]}
                      name="dz"
                      label="动作"
                      rules={[
                        {
                          required: true,
                          message: '请选择动作',
                        },
                      ]}
                    />
                  </Col>
                  <Col span={12}>
                    <ProFormSelect
                      width="md"
                      allowClear={false}
                      initialValue={"tgm"}
                      request={async () => {
                        // return getDictObjById({zdbs: "tgvnf"}).then(response => {
                        //   return response.data;
                        // });
                        return [
                          {value:"tgm",label:"背景流量"},
                          {value:"ditg",label:"D-ITG"},
                          {value:"tcpreplay",label:"tcpreplay"}
                        ]
                      }}
                      name="vnf"
                      label="VNF"
                      rules={[
                        {
                          required: true,
                          message: '请选择VNF',
                        },
                      ]}
                    />
                  </Col>
                </Row>
                <Divider orientation="left">触发策略</Divider>
                <Row gutter={16}>
                  <Col span={24}>
                    <ProFormRadio.Group
                      label="执行方式"
                      name="zxfs"
                      width="md"
                      options={[{value:0,label:'在某时间'}, {value:1,label:'在某任务之后'}, {value:2,label:'在某任务启动后'}]}
                    />
                  </Col>
                  <Col span={12}>
                    <ProFormText width="md" disabled={formRadio !== 0} name="zxsj" label="时间" placeholder="请输入时间" />
                  </Col>
                  <Col span={12}>
                    <ProFormSelect
                      width="md"
                      disabled={formRadio === 0 }
                      request={async () => {
                        // return getTgrwByCjid({cjid: props.cjid}).then(response => {
                        //   return response.data;
                        // });
                        return [];
                      }}
                      name="zxrw"
                      label="任务"
                    />
                  </Col>
                  <Col span={12}>
                    <ProFormSelect
                      width="sm"
                      request={async () => [
                        { label: '停止任务', value: 0 },
                        { label: '开启任务', value: 1 },
                        { label: '停止所有任务', value: 2 }
                      ]}
                      name="ccf"
                      label="当出错"
                    />
                  </Col>
                  <Col span={12}>
                    <ProFormText width="sm" name="company" label="参数" placeholder="" allowClear={false}/>
                  </Col>
                </Row>
                <Divider orientation="left">执行参数</Divider>
                {
                  vnfValue === "tgm" ?
                    <Row gutter={16}>
                      <Col span={8}>
                        <ProFormSelect
                          width="md"
                          request={async () => {
                            // return getSelectListByNodeType({id: props.cjid,nodeType:17}).then(response => {
                            //   return response.data;
                            // })
                            return [];
                          }}
                          name="mb"
                          label="目标"
                          rules={[
                            {
                              required: true,
                              message: '请选择目标',
                            },
                          ]}
                        />
                      </Col>
                      <Col span={8}>
                        <ProFormSelect
                          width="md"
                          request={async () => {
                            // return getDictObjById({zdbs: "yycxy"}).then(response => {
                            //   return response.data;
                            // });
                            return [
                              { value:"1",label:"Telnet" },
                              { value:"2",label:"DNS" },
                              { value:"3",label:"xxx" },
                              { value:"4",label:"xxx" },
                              { value:"5",label:"xxx" },
                              { value:"6",label:"xxx" },
                            ]
                          }}
                          name="yycxy"
                          label="应用层协议"
                        />
                      </Col>
                      <Col span={8}>
                        <ProFormSelect
                          width="md"
                          options={useCscxyArr}
                          name="cscxy"
                          label="传输层协议"
                          rules={[
                            {
                              required: true,
                              message: '请选择协议',
                            },
                          ]}
                        />
                      </Col>
                      <Col span={16}>
                        <ProFormRadio.Group
                          label="选择类型"
                          width="md"
                          radioType="button"
                          name="sllx"
                          options={['包速率', '流速率','持续时长']}
                        />
                      </Col>
                      <Col span={8} >
                        {sllxDisabled === "包速率" ?
                          <>
                            <ProFormText width="md" name="bsl" label="包速率"  allowClear={false}/>
                          </>
                          : null}
                        {sllxDisabled === "流速率" ?
                          <>
                            <ProFormText width="md" name="lsl" label="流速率"  allowClear={false}/>
                          </>
                          : null}
                        {sllxDisabled === "持续时长" ?
                          <>
                            <ProFormText width="md  " name="cxsc" label="持续时长"  allowClear={false}/>
                          </>
                          : null}
                      </Col>
                      <Col span={12}></Col>
                    </Row> : null
                }
                {
                  vnfValue === "ditg" ?
                    <Row gutter={16}>
                      <Col span={12}>
                        <ProFormSelect
                          width="md"
                          request={async () => {
                            return [];
                          }}
                          name="fsd"
                          label="发送端"
                        />
                      </Col>
                      <Col span={12}>
                        <ProFormSelect
                          width="md"
                          request={async () => {
                            return [];
                          }}
                          name="jsd"
                          label="接收端"
                        />
                      </Col>
                      <Col span={8}>
                        <ProFormSelect
                          width="xs"
                          request={async () => {
                            return [
                              { value:"1",label:"tcp" },
                              { value:"2",label:"udp" }
                            ]
                          }}
                          name="xy"
                          label="协议"
                        />
                      </Col>
                      <Col span={8}>
                        <ProFormText width="xs" name="sl" label="速率"  allowClear={false}/>
                      </Col>
                      <Col span={8}>
                        <ProFormSelect
                          width="sm"
                          request={async () => {
                            return [
                              {value:"1",label:"常数"},
                              {value:"2",label:"均匀分布"},
                              {value:"3",label:"指数分布"},
                              {value:"4",label:"正态分布"},
                              {value:"5",label:"泊松分布"},
                              {value:"6",label:"帕累托分布"},
                            ]
                          }}
                          name="mx"
                          label="模型"
                        />
                      </Col>
                    </Row> : null
                }
                {
                  vnfValue === "tcpreplay" ?
                    <Row gutter={16}>
                      <Col span={12}>
                        <ProFormSelect
                          width="md"
                          request={async () => {
                            return [];
                          }}
                          name="bhsb"
                          label="捕获设备"
                        />
                      </Col>
                      <Col span={12}>
                        <ProFormSelect
                          width="md"
                          request={async () => {
                            return [];
                          }}
                          name="bhdk"
                          label="捕获端口"
                        />
                      </Col>
                      <Col span={24}>
                        <ProFormText width="md" name="glbds" label="过滤表达式"  allowClear={false}/>
                      </Col>
                    </Row> : null
                }
                {
                  !paramDisabled ?
                    <>
                      <Row>
                        <Col span={8}>
                          <ProFormSelect
                            width="sm"
                            request={async () => {
                              return [
                                {value:"1",label:"常数"},
                                {value:"2",label:"均匀分布"},
                                {value:"3",label:"指数分布"},
                                {value:"4",label:"正态分布"},
                                {value:"5",label:"泊松分布"},
                                {value:"6",label:"帕累托分布"},
                              ]
                            }}
                            name="psmx"
                            label="PS模型"
                          />
                        </Col>
                        <Col span={8}>
                          <ProFormText width="sm" name="pscs1" label="PS参数1"  allowClear={false} initialValue={512}/>
                        </Col>
                        <Col span={8}>
                          {!psDisabled?<ProFormText width="sm" name="pscs2" label="PS参数2" allowClear={false}/>:null}
                        </Col>
                      </Row>
                      <Row>
                        <Col span={8}>
                          <ProFormSelect
                            width="sm"
                            request={async () => {
                              return [
                                {value:"1",label:"常数"},
                                {value:"2",label:"均匀分布"},
                                {value:"3",label:"指数分布"},
                                {value:"4",label:"正态分布"},
                                {value:"5",label:"泊松分布"},
                                {value:"6",label:"帕累托分布"},
                              ]
                            }}
                            name="idtmx"
                            label="IDT模型"
                          />
                        </Col>
                        <Col span={8}>
                          <ProFormText width="sm" name="idtcs1" label="IDT参数1"  allowClear={false} initialValue={1024}/>
                        </Col>
                        <Col span={8}>
                          {!idtDisabled?<ProFormText width="sm" name="idtcs2" label="IDT参数2" allowClear={false}/>:null}
                        </Col>
                      </Row>
                    </>
                    : null
                }
              </ProForm>
              :
              <div style={{marginTop:300}}><Empty description={false} /></div>
            }
          </Col>
        </Row>
    </>
  );
};

export default TrafficManager;

function updateTreeData(list: any[], key: React.Key, children: any[]): any[] {
  return list.map(node => {
    if (node.key === key) {
      return {
        ...node,
        children,
      };
    }
    if (node.children) {
      return {
        ...node,
        children: updateTreeData(node.children, key, children),
      };
    }
    return node;
  });
}

const tempDelTreeData = (sourceData: DataNode[] = [], key: string) => {
  sourceData.map((node, index) => {
    if (node.key === key) {
      sourceData.splice(index, 1);
    } else if (node.children) {
      tempDelTreeData(node.children, key);
    }
    return node;
  });
  return sourceData;
}
