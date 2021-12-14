import { Row,Col,Space, Button,Popconfirm,message, Spin   } from 'antd'
import React from 'react'
import Transition from '../Static/Transition'
import { Table } from 'antd';
import { useHistory } from "react-router-dom";
import { useEffect, useState } from 'react';
import axios from '../Api/request';
import moment from 'moment';
import { PRESCRIBED,PROCESSING, DECLINED, ROLE_ADMIN, ROLE_ANIMALHTTECH, ROLE_TEACHINGTECH, ROLE_ANIMALCAREAT } from '../DummyData/dummy';


const DailyReportMgt = () => {
      const [loading, setloading] = useState(true);
      const [requestData, setrequestData] = useState([]);

      let history = useHistory();

      const data = [];

      useEffect(() => {
        refreshPage();
      }, [])

      requestData.map(ud=>{
        data.push({
            ...ud,
            key:ud.id,
            created: moment(new Date(ud.created)).format('YYYY-MM-DD'),
            animalID: ud.animalID,

          })
      });


      function confirm(key) {
        let params = { id: key}
        axios.delete("api/prescription/deletePrescription",{params})
        .then(res=>{
          message.success("Prescription deleted successfully");
        })
        .then(res2=>{
          refreshPage();
        })
      }
      
      function cancel(e) {
        message.error('Click on No');
      }








      function refreshPage(){
        //console.log(localStorage.getItem("userId"));
        if(localStorage.getItem("role")==ROLE_ADMIN || localStorage.getItem("role")==ROLE_ANIMALHTTECH){
          axios.get("api/dailyReport/getallReports")
          .then(res=>{
            setrequestData(res.data.data);  
            setloading(false);
          })
        }
        else if(localStorage.getItem("role")==ROLE_ANIMALCAREAT){
          axios.get("api/dailyReport/getReportsById?userId="+localStorage.getItem("userId"))
          .then(res=>{
            setrequestData(res.data.data);  
            console.log(res.data.data);
            setloading(false);
          })
        }
      }
    const columns = [
        {
            title: 'Animal ID',
            dataIndex: 'animalId',
        },
        {
            title: 'Created At',
            dataIndex: 'created',
        },
        {
            title: 'Daily Status',
            dataIndex: 'status',
            
        },
        {
            title: 'Location',
            dataIndex: 'location',
        },
        {
            title: 'Action',
            key: 'action',
            render: (text, record) => (
                
            
              <Space size="middle">
                 <Popconfirm
                        title="Are you sure to delete this record?"
                        onConfirm={() => confirm(record.key)}
                        onCancel={cancel}
                        okText="Yes"
                        cancelText="No"
                    >      
                 {localStorage.getItem("role")==ROLE_ANIMALHTTECH &&<Button type="danger">Delete</Button>}
                 {localStorage.getItem("role")==ROLE_ANIMALCAREAT &&<Button type="danger">Delete</Button>}
                 {localStorage.getItem("role")==ROLE_ADMIN &&<Button type="danger">Delete</Button>}
                </Popconfirm>
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
           <h1>Daily Report Management</h1>
           {loading && <Spin tip="Loading..."/>}                       
           {!loading && <Table bordered columns={columns} dataSource={data} />}
        </Col>

       
        </Row>
        </React.Fragment>
    )
}


export default DailyReportMgt;
