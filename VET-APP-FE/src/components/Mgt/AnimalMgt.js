import { Row,Col,Space, Button,Popconfirm,message,Form,Select   } from 'antd'
import React from 'react'
import Transition from '../Static/Transition'
import { Table } from 'antd';
import { useHistory } from "react-router-dom";
import { useEffect, useState } from 'react';
import axios from '../Api/request';
import moment from 'moment';
import { PENDING,statusData } from '../DummyData/dummy';


const AniamlMgt = () => {
      const { Option } = Select;
      const [animalData, setAnimalData] = useState([]);
      const [userData, setUserData] = useState([]);
      const [reqData, setReqData] = useState([]);
      let history = useHistory();

      //const data = [];
      const [data, setData] = useState([]);

      useEffect(() => {
        loadAnimal();
        loadUser();
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
      setData(dataTemp)
      //console.log(value);
      //console.log(v1);
      //console.log(record.key);
      //console.log(value);
      //console.log(uid);
    };
    const handleRequest = (record) => {
      reqAnimal(record.adminstatus,record.id,record.requestTo);
    };

    function loadAnimal(){
      axios.get("api/animal/getAvailableAnimal")
      .then(res=>{
        setAnimalData(res.data.data);  
      })
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
        //console.log(res.data.data);
      })
      
    }
    const columns = [
        {
          title: 'ID',
          dataIndex: 'id',
          
        },
          {
            title: 'Age',
            dataIndex: 'age',
          },
        {
          title: 'Breed',
          dataIndex: 'breed',
        },
        {
          title: 'Status',
          dataIndex: 'status',
        }, 
        {
          title: 'Request to',
          dataIndex: 'userid',
          render:(text,record) => (
              <Select
              placeholder="Select a technician"
              //value={record.requestTo}
              //onChange = {handleChange}
              onChange={(e)=>handleChange(e, text, record)}
              allowClear
              >
              {userData.map(rl =>(
                  <Option key={rl.id} value={rl.id}>{rl.id}</Option>
              ))}
              </Select>
          )
        },
        {
          title: 'Action',
          key: 'action',
          render: (text, record) => (
            <Space size="middle">
              <Button onClick={() => handleRequest(record)}>Request</Button>
            </Space>
          ),
        },
      ];

      const req_columns = [
        {
          title: 'ID',
          dataIndex: 'id',
          
        },
          {
            title: 'Admin Status',
            dataIndex: 'age',
          },
        {
          title: 'Request Date',
          dataIndex: 'breed',
        },
        {
          title: 'Return Date',
          dataIndex: 'status',
        }, 
        {
          title: 'Return By',
          dataIndex: 'userid',
        },        
        {
          title: 'Tech Status',
          dataIndex: 'userid',
        },
        {
          title: 'Animal ID',
          dataIndex: 'userid',
        },
        {
          title: 'Tech ID',
          dataIndex: 'userid',
        },
        {
          title: 'Submitted By',
          dataIndex: 'userid',
        },
        {
          title: 'Action',
          key: 'action',
          render: (text, record) => (
            <Space size="middle">              
              <Button onClick={() => handleRequest(record)}>Cancle</Button>
            </Space>
          ),
        },
      ];
 
// <Button onClick={() => reqAnimal(record.key)}>Request</Button>

    return (
        <React.Fragment>
        <Row>
        <Col span={4} style={{marginTop:"-80px"}}>
        <Transition></Transition>
        </Col>
        <Col span={16} style={{marginTop:"20px"}}>
           <h1>Animal Management</h1>
           <Table bordered columns={columns} dataSource={data} />
           {/* <h1>Your Request</h1>
           <Table bordered columns={req_columns} dataSource={data} /> */}
        </Col>
        </Row>
        </React.Fragment>
    )
}


export default AniamlMgt;
