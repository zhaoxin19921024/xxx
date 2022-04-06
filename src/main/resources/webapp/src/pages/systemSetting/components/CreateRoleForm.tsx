import React, {useEffect} from 'react';
import {Button, Card, Input, Form, Modal, message} from 'antd';
import {addRoleInfo,updateRoleInfo} from "./service";
import {TableListItemRole} from "./data";
// @ts-ignore
import {v4 as uuidV4} from 'uuid';
import moment from 'moment';

const FormItem = Form.Item;
const { TextArea } = Input;

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

  const onFinish = (values: TableListItemRole) => {
    if(props.isEdit){
      const hide = message.loading('正在修改');
      updateRoleInfo({...values}).then(response => {
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
      values.jsdm = uuidV4().toString().replaceAll("-", "");
      addRoleInfo({...values}).then(response => {
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
                name="jsdm"
              >
                <Input />
              </FormItem>
              <FormItem
                {...formItemLayout}
                label="角色名称"
                name="jsmc"
                rules={[
                  {
                    required: true,
                    message: "角色名称不能为空!",
                  },
                ]}
              >
                <Input placeholder="请输入角色名称" />
              </FormItem>
              <FormItem
                {...formItemLayout}
                label="显示顺序"
                name="xssx"
              >
                <Input />
              </FormItem>
              <FormItem
                {...formItemLayout}
                label="角色说明"
                name="jssm"
              >
                <TextArea
                  style={{ minHeight: 32 }}
                  rows={4}
                />
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
