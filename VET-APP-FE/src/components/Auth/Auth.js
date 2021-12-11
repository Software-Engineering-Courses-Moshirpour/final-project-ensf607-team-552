import { useDispatch } from 'react-redux';
import { Button, Alert,Form, Input,Row, Col } from 'antd';
import { useState } from 'react';
import { useForm } from 'antd/lib/form/Form';
import { NavLink } from 'react-router-dom';
import axios from '../Api/request';

import classes from './Auth.module.css';
import { authActions } from '../../store/auth';

const Auth = () => {
  const dispatch = useDispatch();
  const [pwderror, setpwderror] = useState(false);
  const [errorMsg, seterrorMsg] = useState("");
  const [loginform] = useForm();

  const onFinish = (values) => {
    var pwd = values.password;
    var cofpwd = values.confirmpwd;
    if(pwd!=cofpwd){
      setpwderror(true);
      seterrorMsg("password doesn not match");
      return;
    }

    axios.post("/api/auth/signin",{username: values.username, password: values.password})
      .then(res=> {
          console.log(res.data);
          localStorage.setItem("userId", res.data.id);
          localStorage.setItem("userName", res.data.username);
          localStorage.setItem("role", res.data.roles[0]);
          sessionStorage.setItem("token", res.data.accessToken);
          dispatch(authActions.login());
      }).catch(function (error) {
        setpwderror(true);
        seterrorMsg("credential check failed");
    });
    
    
  };

  const onFinishFailed = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };

  const onReset = () => {
    loginform.resetFields();
  };



  return (
    <main className={classes.auth}>
      
      {pwderror && <Alert style={{marginBottom:"5px"}} message={errorMsg} type="error" showIcon closable />}
      
      <section>
            <h1 style={{marginBottom:'20px'}}>Login To Vet App</h1>
            <Form
              form={loginform}
              name="basic"
              labelCol={{ span: 6 }}
              wrapperCol={{ span: 16 }}
              initialValues={{ remember: true }}
              onFinish={onFinish}
              onFinishFailed={onFinishFailed}
              autoComplete="off"
            >
              <Form.Item
                label="User Name"
                name="username"
                rules={[{ required: true, message: 'Please input username!'}]}
              >
                <Input />
              </Form.Item>

              <Form.Item
                label="Pwd"
                name="password"
                rules={[{ required: true, message: 'Please input your password!' }]}
              >
                <Input.Password />
              </Form.Item>

              <Form.Item
                label="Confirm Pwd"
                name="confirmpwd"
                rules={[{ required: true, message: 'Please confirm your password!' }]}
              >
                <Input.Password />
              </Form.Item>




              <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                <Row>
                  <Col span={6}>
                  <Button type="primary" htmlType="submit">
                    Submit
                  </Button>
                  </Col>
                  <Col span={3}>
                  <Button htmlType="button" onClick={onReset}>
                    Reset
                  </Button>
                  </Col>
                </Row>
              </Form.Item>
            </Form>
      </section>
      <p>Forgot Password? Please <a href="http://localhost:8085/pwd/forgot_password"> Click </a> here to reset</p>

    </main>
  );
};

export default Auth;
