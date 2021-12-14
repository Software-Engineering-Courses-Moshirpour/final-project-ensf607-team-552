import { Row,Col,Space, Button,Popconfirm,message,Form,Select,Modal,Input,Spin  } from 'antd'
import React from 'react'
import Transition from '../Static/Transition'
import { Table } from 'antd';
import { useHistory } from "react-router-dom";
import { useEffect, useState } from 'react';
import axios from '../Api/request';
import moment from 'moment';
import { TREATMENT,PROCESSING,PENDING,ROLE_ADMIN,ROLE_STUDENT,ROLE_TEACHINGTECH,ROLE_ANIMALCAREAT,statusData } from '../DummyData/dummy';
import { useForm } from 'antd/lib/form/Form';


const AniamlMgt = () => {
      const { Option } = Select;
      const [animalData, setAnimalData] = useState([]);
      const [userData, setUserData] = useState([]);
      const [reqData, setReqData] = useState([]);
      const [isModalVisible, setIsModalVisible] = useState(false);
      const [isTRVisible, setIsTRVisible] = useState(false);
      const [loading, setloading] = useState(true);
      const [pageNum, setpageNum] = useState(1);
      const [elementTotal, setelementTotal] = useState(0);
      let history = useHistory();
      const [commentForm]=useForm();
      const [treatmentCommentForm]=useForm();
      const [data, setData] = useState([]);
      const [animalId, setanimalId] = useState(0);
      const [techId, settechId] = useState(0);
      useEffect(() => {
        if(userData.length==0 && animalData.length==0){
          loadAnimal(1);
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
      const handleTROk = () => {
        setIsTRVisible(false);
      };
    
      const handleTRCancel = () => {
        setIsTRVisible(false);
      };
      const handleAnimalRequest = (record) => {
        reqAnimal(record.adminstatus,record.id,record.requestTo);
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
    const handleTreatmentRequest = (record) => {
      setanimalId(record.id);
      settechId(record.requestTo);
      setIsTRVisible(true);
      //reqTreatment(,, record.description);
    };
/*
    function reqTreatment(animalID,userID, description) {
      setIsTRVisible(true);

    }*/
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
    function setTreatmentStatus(key){
      axios.get("api/animal/setTreatmentStatus?id="+key)
      .then(res=>{
        console.log(res.data.message);  
      })
      .then(res=>{
        loadAnimal();
      })
    }
    const onFinishTreatmentReq = (record) => {
      let now = moment().format('YYYY-MM-DD');
      axios.post("api/treatmentReq/addRequest", { reqDate:now,
        techStatus:PROCESSING, animalId: animalId, userId:techId, careAttnId: localStorage.getItem("userId"), description:record.description})
     .then(res=>{
       console.log(res.data.message);  
       message.success("req treatment successfully");
       setTreatmentStatus(animalId);
       setIsTRVisible(false);
       treatmentCommentForm.resetFields();
     })

/*
      axios.post("api/comment/addComment",{...values, description:values.description, animalId:animalId,userId:localStorage.getItem("userId")})
      .then(res=>{  
        message.success(res.data.message);
        setIsTRVisible(false);
        treatmentCommentForm.resetFields();
      })*/
    };
  
    const onFinishFailedTreatmentReq = (errorInfo) => {
      
    };
    const handleChange = (value,v1,record) => {
      record.requestTo = value
      //setuid(value);  
      let dataTemp = data
      let rowIndex = dataTemp.find((item) => item.id = record.id)
      dataTemp.splice(rowIndex, 1, record);
      setData(dataTemp);
    };


    function loadAnimal(pageNum){
      setloading(true);
      setpageNum(pageNum);
      if(localStorage.getItem("role")==ROLE_ADMIN||localStorage.getItem("role")==ROLE_ANIMALCAREAT){
        axios.get('api/animal/getAllAnimal?pageNum='+pageNum).then((res) => {
        setelementTotal(res.data.pageTotal);
        setAnimalData(res.data.data); 
        setloading(false);
      })
      }
      else{
        axios.get("api/animal/getAvailableAnimal")
      .then(res=>{
        setAnimalData(res.data.data);  
        setloading(false);
      })
      }  
    }

    function loadUser(){
      axios.get("api/user/getAllUserByRole")
      .then(res=>{
        setUserData(res.data.data);  
      })
      
    }

    const onFinish = (values) => {
      axios.post("api/comment/addComment",{...values, description:values.description, animalId:animalId, userId:localStorage.getItem("userId")})
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
            <img src={record.url?record.url:"https://607607newnewbk123.s3.us-east-2.amazonaws.com/default20211213.png"} style={{ width: '100px' }} />
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
            localStorage.getItem("role")==ROLE_TEACHINGTECH ||  localStorage.getItem("role")==ROLE_ANIMALCAREAT?
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
              {localStorage.getItem("role")==ROLE_TEACHINGTECH &&  <Button onClick={() => handleAnimalRequest(record)}>Request Animal</Button>}
              {localStorage.getItem("role")==ROLE_ANIMALCAREAT && record.status !="Treatment" && <Button onClick={() => handleTreatmentRequest(record)}>Request Treatment</Button>}
              <Button onClick={() => editAnimal(record.key)}>Edit</Button>
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
              <Modal title="Treatment Desc" visible={isTRVisible} onOk={handleTROk} onCancel={handleTRCancel}>
              <Form 
                  form={treatmentCommentForm}
                  onFinish={onFinishTreatmentReq}
                  onFinishFailed={onFinishFailedTreatmentReq}
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
           <h1>Animal Management</h1>
           {loading && <Spin tip="Loading..."/>}
           {!loading &&  <Table 
            pagination={{ 
              pageSize: 5,
              total:elementTotal,
              onChange(current, pageSize){
                loadAnimal(current);
              },
              current: pageNum,
              showTotal: function () { 
                return 'total of ' + elementTotal + ' data'; 
            }
            }}
           
           bordered columns={columns} dataSource={data} />}
        </Col>
        </Row>
        </React.Fragment>
    )
}


export default AniamlMgt;
