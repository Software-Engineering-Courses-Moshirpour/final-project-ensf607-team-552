import { Row,Col,Space, Button,Popconfirm,message   } from 'antd'
import React from 'react'
import Transition from '../../Static/Transition'
import { Table } from 'antd';
import { useHistory } from "react-router-dom";
import { useEffect, useState } from 'react';
import axios from '../../Api/request';
import moment from 'moment';
import { APPROVED, DECLINED, ROLE_ADMIN, ROLE_ANIMALHTTECH, ROLE_TEACHINGTECH } from '../../DummyData/dummy';


const ReqMgt = () => {

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
            reqdate: moment(new Date(ud.reqDate)).format('YYYY-MM-DD'),
            returnDate:moment(new Date(ud.returnDate)).format('YYYY-MM-DD')
          })
      });


      function confirm(key) {
        let params = { id: key}
        axios.delete("api/request/deleteRequest",{params})
        .then(res=>{
          message.success("req deleted successfully");
        })
        .then(res2=>{
          refreshPage();
        })
      }
      
      function cancel(e) {
        message.error('Click on No');
      }


      function approveReq(requestId){
        if(localStorage.getItem("role")==ROLE_ANIMALHTTECH){
          let params = { reqId: requestId,status: APPROVED,type: ROLE_ANIMALHTTECH }
           axios.get("api/request/updateRequestById",{params})
            .then(res=>{
              message.success("tech status updated");
              console.log(res.data.data);
            })
            .then(res2=>{
              refreshPage();
            })
         }
        else if(localStorage.getItem("role")==ROLE_ADMIN){
          let params = { reqId: requestId,status: APPROVED,type: ROLE_ADMIN }
          axios.get("api/request/updateRequestById",{params})
          .then(res=>{
            message.success("admin status updated");
            // console.log(res.data.data.message);
          })
          .then(res2=>{
            refreshPage();
          })
        }
      }



      function declineReq(requestId){
        if(localStorage.getItem("role")==ROLE_ANIMALHTTECH){
          let params = { reqId: requestId,status: DECLINED,type: ROLE_ANIMALHTTECH }
           axios.get("api/request/updateRequestById",{params})
            .then(res=>{
              message.success("tech status updated");
              console.log(res.data.data);
            })
            .then(res2=>{
              refreshPage();
            })
         }
        else if(localStorage.getItem("role")==ROLE_ADMIN){
          let params = { reqId: requestId,status: DECLINED,type: ROLE_ADMIN }
           axios.get("api/request/updateRequestById",{params})
          .then(res=>{
            console.log(res.data);
            message.success("admin status updated");
          })
          .then(res2=>{
            refreshPage();
          })
        }
      }


      function refreshPage(){
        if(localStorage.getItem("role")==ROLE_ANIMALHTTECH){
        axios.get("api/request/getallrequests?userid="+localStorage.getItem("userId"))
        .then(res=>{
          setrequestData(res.data.data);  
        })}
        else if(localStorage.getItem("role")==ROLE_ADMIN){
          axios.get("api/request/getallrequestsForAdmin")
          .then(res=>{
            setrequestData(res.data.data);  
          })
        }
        else if(localStorage.getItem("role")==ROLE_TEACHINGTECH){
          axios.get("api/request/getallrequestsByInstruct?instrucId="+localStorage.getItem("userId"))
          .then(res=>{
            setrequestData(res.data.data);  
          })
        }
      }



    const columns = [
        {
          title: 'adminstatus',
          dataIndex: 'adminstatus',
        },
          {
            title: 'techstatus',
            dataIndex: 'techstatus',
          },
        {
          title: 'reqdate',
          dataIndex: 'reqdate',
        },
        {
          title: 'returnDate',
          dataIndex: 'returnDate',
        },
        {
          title: 'returnedUser',
          dataIndex: 'returnedUser',
        },
        {
            title: 'Action',
            key: 'action',
            render: (text, record) => (
              <Space size="middle">
                {localStorage.getItem("role")==ROLE_ANIMALHTTECH && record.techstatus=="PENDING" && <Button type="primary" onClick={() => approveReq(record.key)}>Approve</Button>}
                {localStorage.getItem("role")==ROLE_ADMIN && record.adminstatus=="PENDING" && <Button type="primary" onClick={() => approveReq(record.key)}>Approve</Button>}
              
                 {localStorage.getItem("role")==ROLE_ANIMALHTTECH && record.techstatus=="PENDING" && <Button type="danger" onClick={() => declineReq(record.key)}>Decline</Button>}
                 {localStorage.getItem("role")==ROLE_ADMIN && record.adminstatus=="PENDING" && <Button type="danger" onClick={() => declineReq(record.key)}>Decline</Button>}
                 <Popconfirm
                        title="Are you sure to delete this record?"
                        onConfirm={() => confirm(record.key)}
                        onCancel={cancel}
                        okText="Yes"
                        cancelText="No"
                    >      
                 {localStorage.getItem("role")==ROLE_TEACHINGTECH &&  record.adminstatus!="DECLINED" && <Button type="danger">Delete</Button>}
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
           <h1>Request Management</h1>
                            
           <Table bordered columns={columns} dataSource={data} />
        </Col>

       
        </Row>
        </React.Fragment>
    )
}


export default ReqMgt;
