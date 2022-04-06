import React, {FC, useEffect} from 'react';
import {Button, Form, Input,Select,Row,Col, Card} from 'antd';
import {formItemData} from "./data"

const FormItem = Form.Item;
const {TextArea} = Input;

interface CreateFormProps {
  //取消
  onCancel: () => void;
  //完成
  onFinish: (data: object) => void;
  //表单结构数据
  formData: Partial<formItemData>[];
  //当前的数据
  selectData: {};
  //TODO:是否为编辑状态
  isEdit: boolean;
  //TODO:是否显示
  visible: boolean;
}

/**
 * 自动创建表单
 * @param props
 * @param ref
 * @constructor
 */
const CreatForm: FC<CreateFormProps> = (props, ref) => {
  const [form] = Form.useForm();

  useEffect(() => {
    form.resetFields();
  }, [props.visible]);

  useEffect(() => {
    console.log("aaaa",props.selectData);
    form.resetFields();
    if(JSON.stringify(props.selectData) !== "{}"){
      form.setFieldsValue({
        ...props.selectData
      })
    }
  },[props.selectData])

  /**
   * 样式
   */
  const formItemLayout = {
    labelCol: {
      xs: {span: 24},
      sm: {span: 8},
    },
    wrapperCol: {
      xs: {span: 24},
      sm: {span: 16},
    },
  };

  const submitFormLayout = {
    wrapperCol: {
      xs: { span: 24, offset: 0 },
      sm: { span: 10, offset: 7 },
    },
  };

  return (
    <Card title="资产信息" bordered={false} hidden={props.visible}>
      <Form
        {...formItemLayout}
        form={form}
        onFinish={props.onFinish}
      >
        <Row gutter={12}>
        {
          props.formData.map((data, index) => {
            switch (data.type) {
              case "TextArea":
                return (
                  <Col span={data.span} hidden={data.isShow}>
                    <FormItem
                      label={data.label}
                      name={data.name}
                      key={index}
                    >
                      <TextArea
                        style={data.style?data.style:{}}
                        rows={data.rows?data.rows:3}
                      />
                    </FormItem>
                  </Col>
                );
              case "Input":
                return (
                  <Col span={data.span} hidden={data.isShow}>
                    <FormItem
                      label={data.label}
                      name={data.name}
                      key={index}
                    >
                      <Input/>
                    </FormItem>
                  </Col>
                );
              case "Select":
                return (
                  <Col span={data.span} hidden={data.isShow}>
                    <FormItem
                      label={data.label}
                      name={data.name}
                      key={index}
                    >
                      <Select
                        placeholder="请选择"
                        options={data.selectData}
                      />
                    </FormItem>
                  </Col>
                );
              case "Button":
                return (
                  <Col span={24}>
                    <FormItem {...submitFormLayout} style={{ marginTop: 32 }}>
                      <Button onClick={()=>form.resetFields()}>
                        重置
                      </Button>
                      <Button type="primary" htmlType="submit" style={{ marginLeft: 8 }}>
                        提交
                      </Button>
                    </FormItem>
                  </Col>
                );
              default:
                return "";
            }
          })
        }
        </Row>
      </Form>
    </Card>
  );
};

export default CreatForm;
