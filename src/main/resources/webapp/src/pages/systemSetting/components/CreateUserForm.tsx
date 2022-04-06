import React, {useEffect} from 'react';
import {Button, Card, Input, Form, Select, Modal, message, DatePicker} from 'antd';
import {addUserInfo,updateUserInfo} from "./service";
import {TableListItem} from "./data";
// @ts-ignore
import {v4 as uuidV4} from 'uuid';
import moment from 'moment';

const FormItem = Form.Item;

interface CreateFormProps {
  modalVisible: boolean;
  onCancel: () => void;
  onOk: () => void;
  data:{};
  isEdit:boolean;
}

const CreateUserForm: React.FC<CreateFormProps> = (props) => {
  const { modalVisible, onCancel } = props;
  const [form] = Form.useForm();

  useEffect(() => {
    form.resetFields();
  }, [props.modalVisible]);

  useEffect(() => {
    if(props.data){
      let tempData = JSON.parse(JSON.stringify(props.data));
      tempData.csny = moment(tempData.csny,'YYYY-MM-DD');
      form.setFieldsValue({
        ...tempData,
      });
    }
  }, [props.data]);

  const formItemLayout = {
    labelCol: {
      xs: { span: 24 },
      sm: { span: 7 },
    },
    wrapperCol: {
      xs: { span: 24 },
      sm: { span: 12 },
      md: { span: 10 },
    },
  };

  const submitFormLayout = {
    wrapperCol: {
      xs: { span: 24, offset: 0 },
      sm: { span: 10, offset: 7 },
    },
  };

  const onFinish = (values: TableListItem) => {
    if(props.isEdit){
      const hide = message.loading('正在修改');
      updateUserInfo({...values}).then(response => {
        props.onOk();
        hide();
        message.success('修改成功');
        return true;
      }).catch(error=>{
        hide();
        message.error('修改失败请重试！');
        return false;
      });
    }else {
      const hide = message.loading('正在添加');
      values.yhid = uuidV4().toString().replaceAll("-", "");
      addUserInfo({...values}).then(response => {
        props.onOk();
        hide();
        message.success('添加成功');
        return true;
      }).catch(error=>{
        hide();
        message.error('添加失败请重试！');
        return false;
      });
    }

  };

  const onFinishFailed = (errorInfo: any) => {
    // eslint-disable-next-line no-console
    console.log('Failed:', errorInfo);
  };

  return (
    <>
        <Modal
          width={800}
          destroyOnClose
          title="用户信息"
          visible={modalVisible}
          onCancel={() => onCancel()}
          footer={null}
        >
          <Card bordered={false}>
            <Form
              form={form}
              onFinish={onFinish}
              onFinishFailed={onFinishFailed}
            >
              <FormItem
                {...formItemLayout}
                hidden={true}
                name="yhid"
              >
                <Input />
              </FormItem>
              <FormItem
                {...formItemLayout}
                label="用户姓名"
                name="yhxm"
                rules={[
                  {
                    required: true,
                    message: "用户姓名不能为空!",
                  },
                ]}
              >
                <Input placeholder="请输入用户姓名" />
              </FormItem>
              <FormItem
                {...formItemLayout}
                label="登录名"
                name="dlm"
                rules={[
                  {
                    required: true,
                    message: "登录名不能为空!",
                  },
                ]}
              >
                <Input placeholder="请输入登录名" />
              </FormItem>
              {!props.isEdit?(
                <FormItem
                  {...formItemLayout}
                  label="登录密码"
                  name="dlmm"
                  rules={[
                    {
                      required: true,
                      message: "登录密码不能为空!",
                    },
                  ]}
                >
                  <Input placeholder="请输入登录密码" />
                </FormItem>
              ):null}
              <FormItem
                {...formItemLayout}
                label="身份证号"
                name="sfz"
              >
                <Input />
              </FormItem>
              <FormItem
                {...formItemLayout}
                label="出生年月"
                name="csny"
              >
                <DatePicker />
              </FormItem>
              <FormItem
                {...formItemLayout}
                label="性别"
                name="xb"
              >
                <Select
                  placeholder="请选择"
                  options={[{label:"男",value:"01"},{label:"女",value:"02"}]}
                />
              </FormItem>
              <FormItem
                {...formItemLayout}
                label="职务"
                name="zw"
              >
                <Input />
              </FormItem>
              <FormItem
                {...formItemLayout}
                label="联系电话"
                name="lxdh"
              >
                <Input />
              </FormItem>
              <FormItem {...submitFormLayout} style={{ marginTop: 32 }}>
                <Button onClick={()=>form.resetFields()}>
                  重置
                </Button>
                <Button type="primary" htmlType="submit" style={{ marginLeft: 8 }}>
                  保存
                </Button>
              </FormItem>
            </Form>
          </Card>
        </Modal>
    </>
  );
};

export default CreateUserForm;
