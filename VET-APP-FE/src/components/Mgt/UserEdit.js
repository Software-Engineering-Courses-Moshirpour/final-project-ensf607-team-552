import React from 'react'
import Transition from '../Static/Transition'
import { useParams } from "react-router-dom";
import { Form, Input, Button,DatePicker,Row,Col,Select } from 'antd';
import { useForm } from 'antd/lib/form/Form';
import { useEffect } from 'react';
import { roledata,statusData } from '../DummyData/dummy';
import axios from '../Api/request';
import moment from 'moment';


export default function UserEdit() {
    const { Option } = Select;
    const [userForm] = useForm();
    let { id } = useParams();
    const data = [];


    useEffect(() => {
        axios.get("api/user/getuserById?id="+id)
        .then(res=>{
          data.push({
            ...res.data.data,
            key:res.data.data.id,
            dob: moment(new Date(res.data.data.dob)),
            createdAt:moment(new Date(res.data.data.createdAt)),
            role:res.data.data.roles[0].roleName,
            roleId:res.data.data.roles[0].id,
            password:res.data.data.password,
            status:res.data.data.status
          });
          userForm.setFieldsValue(data[0]); 
        })
    }, [data])

    
  const onFinish = (values) => {
        
    axios.post("/api/user/updateUser", {...values, id: id, roles:[{id:values.roleId, roleName:values.role, status:values.status.stsName}]})
        .then(
          res => {
            console.log(res.data.message);
            
          }
        );
      // convert moment back to string
      console.log(moment(values.dob).format('YYYY/MM/DD'));
  };

  const onFinishFailed = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };
    
    
    return (
        <React.Fragment>
        <Row>
        <Col span={4} style={{marginTop:"-80px"}}>
        <Transition></Transition>
        </Col>
        <Col span={16} style={{marginTop:"20px"}}>
        <Form 
        form={userForm}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        labelCol={{ span: 6 }}
        wrapperCol={{ span: 16 }}
        name="dynamic_rule">

            <Form.Item
                name="username"
                label="User Name"
                rules={[
                {
                    required: true,
                    message: 'Please input your user name',
                },
                ]}
            >
                <Input placeholder="Please input your user name" />
            </Form.Item>
            <Form.Item
                name="phone"
                label="Phone"
                rules={[
                {
                    required: true,
                    message: 'Please input your phone',
                },
                ]}
            >
                <Input placeholder="Please input your phone" />
            </Form.Item>
            <Form.Item
                name="dob"
                label="D.O.B"
                rules={[
                {
                    required: true,
                    message: 'Please select your date birth',
                },
                ]}
            >
                <DatePicker  format="YYYY-MM-DD" />
            </Form.Item>
            <Form.Item
                name="role"
                label="Role"
                rules={[
                {
                    required: true,
                    message: 'Please select your role',
                },
                ]}
            >
                <Select
                placeholder="Select a role"
                allowClear
                >
                {roledata.map(rl =>(
                    <Option key={rl.id} value={rl.roleName}>{rl.roleName}</Option>
                ))}
                </Select>
            </Form.Item>
            <Form.Item
                name="status"
                label="Status"
                rules={[
                {
                    required: false,
                    message: 'Please select your status',
                },
                ]}
            >
                <Select
                placeholder="Select a status"
                allowClear
                >
                {statusData.map(rl =>(
                    <Option key={rl.id} value={rl.stsName}>{rl.stsName}</Option>
                ))}
                </Select>
            </Form.Item>
            <Form.Item
                hidden
                name="roleId"
                label="roleId"
                rules={[
                {
                    required: true,
                    message: 'Please select your role',
                },
                ]}
            >
                <Input  placeholder="Please input your role id" />
            </Form.Item>
            <Form.Item
                name="email"
                label="Email"
                rules={[
                {
                    required: true,
                    message: 'Please input valid email!',
                    type: 'string', 
                    pattern: /^([a-z0-9_.-]+)@([\da-z.-]+).([a-z.]{2,6})$/
                },
                ]}
            >
                <Input placeholder="Please input your email" />
            </Form.Item>
            <Form.Item
                name="password"
                label="password"
                rules={[
                    {
                        required: true,
                        message: 'Please input your password',
                    },
                    ]}
            >
                <Input placeholder="Please input your new password" />
            </Form.Item>
            <Form.Item  wrapperCol={{ offset: 6, span: 16 }}>
                <Button type="primary" htmlType="submit">
                Submit
                </Button>
            </Form.Item>
        </Form>
        </Col>
        </Row>
        </React.Fragment>
    )
}
