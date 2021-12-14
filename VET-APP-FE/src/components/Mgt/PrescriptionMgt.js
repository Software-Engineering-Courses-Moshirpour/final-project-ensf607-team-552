import { Row,Col,Space, Button,Popconfirm,message, Spin   } from 'antd'
import React from 'react'
import Transition from '../Static/Transition'
import { Table } from 'antd';
import { useHistory } from "react-router-dom";
import { useEffect, useState } from 'react';
import axios from '../Api/request';
import moment from 'moment';
import { PRESCRIBED,PROCESSING, DECLINED, ROLE_ADMIN, ROLE_ANIMALHTTECH, ROLE_TEACHINGTECH, ROLE_ANIMALCAREAT } from '../DummyData/dummy';


const PrescriptionMgt = () => {
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
        axios.delete("api/treatmentReq/deleteRequest",{params})
        .then(res=>{
          message.success("treatmentReq deleted successfully");
        })
        .then(res2=>{
          refreshPage();
        })
      }
      
      function cancel(e) {
        message.error('Click on No');
      }

      function test(id){
        console.log("test");
        axios.get("api/treatmentReq/getRequestByID?id="+id)
        .then(res=>{
          console.log(res.data.data);
    
          //userForm.setFieldsValue(data[0]); 
        });
      }
    
      function prescribeReq(record){
        history.push(`/treatmentMgt/${record.key}/edit`)
        //requestId
        //record.key
        //history.push(`/animalMgt/${key}/edit`)
        //console.log("record: " + record.key);
        //console.log("record: " + record.careAttnId);
        //console.log(record);
        /*
        if(localStorage.getItem("role")==ROLE_ANIMALHTTECH){
          let params = { reqId: record.key,status: PRESCRIBED,type: ROLE_ANIMALHTTECH }
           axios.get("api/treatmentReq/updateRequestById",{params})
            .then(res=>{
              message.success("prescribeReq updated");
              console.log(res.data.data);
            })
            .then(res2=>{
              refreshPage();
            })
         }*/
         
      }




      function declineReq(requestId){
        if(localStorage.getItem("role")==ROLE_ANIMALHTTECH){
          let params = { reqId: requestId,status: DECLINED,type: ROLE_ANIMALHTTECH }
           axios.get("api/treatmentReq/updateRequestById",{params})
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
           axios.get("api/treatmentReq/updateRequestById",{params})
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
        //console.log(localStorage.getItem("userId"));
        if(localStorage.getItem("role")==ROLE_ANIMALHTTECH){
            //console.log(localStorage.getItem("userId"));
        axios.get("api/prescription/getallPreByID?techId="+localStorage.getItem("userId"))
        .then(res=>{
          setrequestData(res.data.data); 
          console.log(res.data.data);
          setloading(false);
        })}
        else if(localStorage.getItem("role")==ROLE_ADMIN){
          axios.get("api/prescription/getallrequestsForAdmin")
          .then(res=>{
            setrequestData(res.data.data);  
            setloading(false);
          })
        }
        else if(localStorage.getItem("role")==ROLE_ANIMALCAREAT){
          axios.get("api/prescription/getallReqByCareAttnID?careAttnId="+localStorage.getItem("userId"))
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
            title: 'From Care Attn',
            dataIndex: 'careAttnUser',
            
        },
        {
            title: 'Type',
            dataIndex: 'type',
        },
        {
            title: 'Prescription',
            dataIndex: 'prescription',
        },
        {
          title: 'Created at',
          dataIndex: 'created',
        },

        {
            title: 'Action',
            key: 'action',
            render: (text, record) => (
                
            
              <Space size="middle">
                {localStorage.getItem("role")==ROLE_ANIMALHTTECH && record.techstatus=="PROCESSING" && <Button type="primary" onClick={() => prescribeReq(record)}>Prescribe</Button>}
              
                 {localStorage.getItem("role")==ROLE_ANIMALHTTECH && record.techstatus=="PROCESSING" && <Button type="danger" onClick={() => declineReq(record.key)}>Decline</Button>}
                 {localStorage.getItem("role")==ROLE_ADMIN && record.adminstatus=="PROCESSING" && <Button type="danger" onClick={() => declineReq(record.key)}>Decline</Button>}
                 <Popconfirm
                        title="Are you sure to delete this record?"
                        onConfirm={() => confirm(record.key)}
                        onCancel={cancel}
                        okText="Yes"
                        cancelText="No"
                    >      
                 {localStorage.getItem("role")==ROLE_ANIMALCAREAT && record.techstatus=="PROCESSING" && <Button type="danger">Delete</Button>}
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
           <h1>Prescription Management</h1>
           {loading && <Spin tip="Loading..."/>}                       
           {!loading && <Table bordered columns={columns} dataSource={data} />}
        </Col>

       
        </Row>
        </React.Fragment>
    )
}


export default PrescriptionMgt;
