import React from 'react'
import { Button, Alert,Form, Input,Row, Col } from 'antd';
import { useForm } from 'antd/lib/form/Form';
import classes from './ResetPwd.module.css';
export default function ResetPwd() {


    const [emailform] = useForm();

    const onFinish = (values) => {
        console.log(values.email);
    };
  
    const onFinishFailed = (errorInfo) => {
      console.log('Failed:', errorInfo);
    };
    
    
    const onReset = () => {
        emailform.resetFields();
    };
    
    
    return (
        <main className={classes.auth}>
        <div className={classes.reminder}>
           You Forgot Your password? we will send you a link to reset it ...
        </div>
        <section>
              <Form
                form={emailform}
                name="basic"
                labelCol={{ span: 4 }}
                wrapperCol={{ span: 16 }}
                initialValues={{ remember: true }}
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
                autoComplete="off"
              >
                <Form.Item
                  label="Email"
                  name="email"
                  rules={[{ required: true, message: 'Please input valid email!',type: 'string', pattern: /^([a-z0-9_.-]+)@([\da-z.-]+).([a-z.]{2,6})$/ }]}
                >
                  <Input />
                </Form.Item>
  
  
  
  
                <Form.Item wrapperCol={{ offset: 6, span: 16 }}>
                  <Row>
                    <Col span={8}>
                    <Button type="primary" htmlType="submit">
                     Next
                    </Button>
                    </Col>
                    <Button htmlType="button" onClick={onReset}>
                    Reset
                    </Button>
                  </Row>
                </Form.Item>
              </Form>
        </section>
  
      </main>
    )
}
