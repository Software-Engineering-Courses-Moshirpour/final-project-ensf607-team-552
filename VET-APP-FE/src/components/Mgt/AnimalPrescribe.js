import React, { useState } from 'react'
import Transition from '../Static/Transition'
import { useParams,useHistory } from "react-router-dom";
import { Form, Input, Button,DatePicker,Row,Col,Select, Upload, message } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import { useForm } from 'antd/lib/form/Form';
import { useEffect } from 'react';
import { treatmentMethod } from '../DummyData/dummy';
import axios from '../Api/request';

const AnimalPrescribe = () => {
    let history = useHistory();
    const { Option } = Select;
    const [userForm] = useForm();
    const [pageloading, setPageloading] = useState(true)
    let { id } = useParams();
    //console.log(id);
    const data = [];
    
    useEffect(() => {
        loadPage();
     }, [data])
 

    function loadPage(){
        axios.get("api/treatmentReq/getRequestByID?id="+id)
        .then(res=>{
          //console.log(res.data.data);
          data.push({
            ...res.data.data,
            treatmentId: res.data.data.id,
            animalId:res.data.data.animalId,
            techId:res.data.data.userId,
            careAttnId: res.data.data.careAttnId
          });
          //console.log(data);
          userForm.setFieldsValue(data[0]); 
          setPageloading(true)
        });
    }


    const onFinish = (values) => {
        console.log(data[0].treatmentId+" "+values.type +" "+ values.prescription + " "+ data[0].animalId + " "+localStorage.getItem("userId"))
        axios.post("/api/prescription/addPrescription", {...values, type:values.type, prescription:values.prescription, animalId: data[0].animalId, careAttnId:data[0].careAttnId, treatmentReqId:data[0].treatmentId, userId:localStorage.getItem("userId")})
        //{id: id, url:values.imageUrl, status: values.status}
            .then(
              res => { 
                message.success(res.data.message); 
                history.goBack();
              }
            )
            .catch(
                res1=>{
                message.error(res1?.data?.message);  
                }
            )
            ;
          // convert moment back to string
        //   console.log(moment(values.dob).format('YYYY/MM/DD'));
      };
    
      const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
      };
        
    
    return pageloading &&(
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
                name="type"
                label="type"
                rules={[
                {
                    required: true,
                    message: 'Please select a type',
                },
                ]}
            >
                <Select
                placeholder="Select types"
                allowClear
                >
                {treatmentMethod.map(rl =>(
                    <Option key={rl.id} value={rl.stsName}>{rl.stsName}</Option>
                ))}
                </Select>
            </Form.Item>



            <Form.Item
                name="prescription"
                label="prescription"
                rules={[
                {
                    required: true,
                    message: 'Please input prescription',
                },
                ]}
            >
                <Input placeholder="Please input prescription" />
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

export default AnimalPrescribe;
