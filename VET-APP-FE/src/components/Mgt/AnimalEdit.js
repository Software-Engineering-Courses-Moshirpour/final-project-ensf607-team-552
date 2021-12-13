import React from 'react'
import Transition from '../Static/Transition'
import { useParams,useHistory } from "react-router-dom";
import { Form, Input, Button,DatePicker,Row,Col,Select, Upload, message } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import { useForm } from 'antd/lib/form/Form';
import { useEffect } from 'react';
import { animalStatus } from '../DummyData/dummy';
import axios from '../Api/request';


export default function AnimalEdit() {
    let history = useHistory();
    const { Option } = Select;
    const [userForm] = useForm();
    let { id } = useParams();
    console.log(id);
    const data = [];


    useEffect(() => {
       loadPage();
    }, [data])


    function loadPage(){
        axios.get("api/animal/getanimalById?id="+id)
        .then(res=>{
          data.push({
            ...res.data.data,
            key:res.data.data.id,
            status:res.data.data.status,
            imageUrl: res.data.data.url
          });
          userForm.setFieldsValue(data[0]); 
        });
    }


    const props = {
        name: 'file',
        action:  "http://localhost:8085/api/animal/imageupload?id="+id,
        headers: {
          authorization: sessionStorage.getItem("token"),
        },
        onChange(info) {
          if (info.file.status !== 'uploading') {
           
          }
          if (info.file.status === 'done') {
            console.log(info.file.response.url);
            message.success(`${info.file.name} file uploaded successfully`);
            // refresh page
            history.go(0);
          } else if (info.file.status === 'error') {
            message.error(`${info.file.name} file upload failed.`);
          }
        },
      };

    
  const onFinish = (values) => {
        
    axios.post("/api/animal/updateAnimalStatusImage", {id: id, url:values.imageUrl, status: values.status})
        .then(
          res => { 
            message.success(res.data.message); 
            history.goBack();
          }
        )
        .catch(
            res1=>{
            message.error(res1.data.message);  
            }
        )
        ;
      // convert moment back to string
    //   console.log(moment(values.dob).format('YYYY/MM/DD'));
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
                name="status"
                label="status"
                rules={[
                {
                    required: true,
                    message: 'Please input your status',
                },
                ]}
            >
                <Select
                placeholder="Select status"
                allowClear
                >
                {animalStatus.map(rl =>(
                    <Option key={rl.id} value={rl.stsName}>{rl.stsName}</Option>
                ))}
                </Select>
            </Form.Item>


            <Form.Item
                name="imageUrl"
                label="imageUrl"
                rules={[
                {
                    required: true,
                    message: 'Please input your imageUrl',
                },
                ]}
            >
                <Input placeholder="Please input your imageUrl" />
            </Form.Item>
            <Form.Item  wrapperCol={{ offset: 6, span: 16 }}>
                <Button type="primary" htmlType="submit">
                Submit
                </Button>
            </Form.Item>
            
        </Form>
        <span style={{marginLeft:"250px"}}>
            <Upload {...props}>
                <Button icon={<UploadOutlined />}>Click to Upload</Button>
            </Upload>
        </span>
        </Col>
        </Row>

        </React.Fragment>
    )
}
