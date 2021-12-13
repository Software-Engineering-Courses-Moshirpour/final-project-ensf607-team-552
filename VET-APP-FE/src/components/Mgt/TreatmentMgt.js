import { Row,Col,Space, Button,Popconfirm,message,Form,Select,Modal,Input   } from 'antd'
import React from 'react'
import Transition from '../Static/Transition'
import { Table } from 'antd';
import { useHistory } from "react-router-dom";
import { useEffect, useState } from 'react';
import axios from '../Api/request';
import moment from 'moment';
import { PENDING,ROLE_ADMIN,ROLE_STUDENT,ROLE_TEACHINGTECH,statusData } from '../DummyData/dummy';
import { useForm } from 'antd/lib/form/Form';


const TreatmentMgt = () => {
      const { Option } = Select;
      const [animalData, setAnimalData] = useState([]);
      const [userData, setUserData] = useState([]);
      const [reqData, setReqData] = useState([]);
      const [isModalVisible, setIsModalVisible] = useState(false);
      let history = useHistory();
      const [commentForm]=useForm();

      const [data, setData] = useState([]);
      const [animalId, setanimalId] = useState(0);

      useEffect(() => {
        if(userData.length==0 && animalData.length==0){
          loadAnimal();
          loadUser();
        }
      }, [])

      useEffect(() => {
        const temp = animalData.map(ad => (
          {...ad,
          key:ad.id,
          dob: moment(new Date(ad.dob)).format('YYYY-MM-DD'),
          requestTo:null
          }
          ))
          setData(temp);
      }, [animalData])

      function editAnimal(key) {
        history.push(`/animalMgt/${key}/edit`)
     };


     function viewAnimal(key) {
        // history.push(`/animalMgt/${key}/edit`)
      };

      function commentAnimal(key) {
        // history.push(`/animalMgt/${key}/edit`)
        setanimalId(key);
        setIsModalVisible(true);
      };

      
      function viewComments(key) {
        history.push(`/animalProfile/${key}/view`)
      };

      function viewAnimalProfile(key) {
        history.push(`/animalProfile/${key}/view`)
      };


      const handleOk = () => {
        setIsModalVisible(false);
      };
    
      const handleCancel = () => {
        setIsModalVisible(false);
      };

   
     function reqAnimal(animalstatus,animalID,userID) {
      let now = moment().format('YYYY-MM-DD');
        setUnavailableStatus(animalID);
        axios.post("api/request/addRequest", {adminstatus:PENDING, reqDate:now,
          returnDate:now, returnedUser:localStorage.getItem("userName"),techstatus:PENDING, animalid: animalID, userid:userID, instructId: localStorage.getItem("userId")})
       .then(res=>{
         console.log(res.data.message);  
         message.success("req animal successfully");
       })
    }
    //console.log(data);
    function setUnavailableStatus(key){
      axios.get("api/animal/setUnavailableStatus?id="+key)
      .then(res=>{
        console.log(res.data.message);  
      })
      .then(res=>{
        loadAnimal();
      })
    }
    const handleChange = (value,v1,record) => {
      record.requestTo = value
      //setuid(value);  
      let dataTemp = data
      let rowIndex = dataTemp.find((item) => item.id = record.id)
      dataTemp.splice(rowIndex, 1, record);
      setData(dataTemp);
    };
    const handleRequest = (record) => {
      reqAnimal(record.adminstatus,record.id,record.requestTo);
    };

    function loadAnimal(){
      if(localStorage.getItem("role")==ROLE_ADMIN){
        axios.get("api/animal/getAllAnimal")
      .then(res=>{
        setAnimalData(res.data.data);  
      })
      }else{
        axios.get("api/animal/getAllAnimal")
      .then(res=>{
        setAnimalData(res.data.data);  
      })
      }  
    }
    function loadRequest(){
      axios.get("api/request/getAllRequestbyUserID")
      .then(res=>{
        setReqData(res.data.data);  
      })
    }

    function loadUser(){
      axios.get("api/user/getAllUserByRole")
      .then(res=>{
        setUserData(res.data.data);  
      })
      
    }

    const onFinish = (values) => {
      axios.post("api/comment/addComment",{...values, description:values.description, animalId:animalId,userId:localStorage.getItem("userId")})
      .then(res=>{  
        message.success(res.data.message);
        setIsModalVisible(false);
        commentForm.resetFields();
      })
    };
  
    const onFinishFailed = (errorInfo) => {
      
    };
    const columns = [
        {
          title: 'ID',
          dataIndex: 'id',
          
        },
        {
          title: 'Image',
          dataIndex: 'url',
          render:(text,record) => (
           <img src={record.url} style={{width:"100px"}}/>
        )
          
        },
          {
            title: 'Age',
            dataIndex: 'age',
            sorter: (a, b) => a.age - b.age,
          },
        {
          title: 'Breed',
          dataIndex: 'breed',
        },
        {
          title: 'Status',
          dataIndex: 'status',
          sorter: (a, b) =>  a.status.localeCompare(b.status),
        }, 
        {
          title: 'Request to',
          dataIndex: 'userid',
          render:(text,record) => (
            localStorage.getItem("role")==ROLE_TEACHINGTECH?
              (<Select
              placeholder="Select a health technician"
              onChange={(e)=>handleChange(e, text, record)}
              allowClear
              >
              {userData.map(rl =>(
                  <Option key={rl.id} value={rl.id}>{rl.id}</Option>
              ))}
              </Select>):""
          )
        },
        {
          title: 'Action',
          key: 'action',
          render: (text, record) => (
            <Space size="middle">
              {localStorage.getItem("role")==ROLE_TEACHINGTECH &&  <Button onClick={() => handleRequest(record)}>Request</Button>}
              <Button onClick={() => editAnimal(record.key)}>Edit</Button>
              <Button onClick={() => viewAnimal(record.key)}>View</Button>
              {localStorage.getItem("role")==ROLE_STUDENT && <Button onClick={() => commentAnimal(record.key)}>Comment</Button>}
              {localStorage.getItem("role")==ROLE_ADMIN  && <Button onClick={() => viewComments(record.key)}>View Comments</Button>} 
              {localStorage.getItem("role")==ROLE_STUDENT && <Button onClick={() => viewAnimalProfile(record.key)}>View Profile</Button>}
              <Modal title="Basic Modal" visible={isModalVisible} onOk={handleOk} onCancel={handleCancel}>
              <Form 
                  form={commentForm}
                  onFinish={onFinish}
                  onFinishFailed={onFinishFailed}
                  labelCol={{ span: 6 }}
                  wrapperCol={{ span: 16 }}
                  name="dynamic_rule">


                      <Form.Item
                          name="description"
                          label="description"
                          rules={[
                          {
                              required: true,
                              message: 'Please input your description',
                          },
                          ]}
                      >
                          <Input placeholder="Please input your description" />
                      </Form.Item>
                      <Form.Item  wrapperCol={{ offset: 6, span: 16 }}>
                          <Button type="primary" htmlType="submit">
                          Submit
                          </Button>
                      </Form.Item>
                      
                  </Form>
              </Modal>
            </Space>
          ),
        },
      ];



    return (
        <React.Fragment>
        <Row>
        <Col span={4} style={{marginTop:"-80px"}}>
        <Transition></Transition>
        </Col>
        <Col span={16} style={{marginTop:"20px"}}>
           <h1>Treatment Management</h1>
           <Table bordered columns={columns} dataSource={data} />
           {/* <h1>Your Request</h1>
           <Table bordered columns={req_columns} dataSource={data} /> */}
        </Col>
        </Row>
        </React.Fragment>
    )
}


export default TreatmentMgt;
