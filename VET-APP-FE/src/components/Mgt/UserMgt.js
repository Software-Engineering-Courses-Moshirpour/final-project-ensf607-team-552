import { Row,Col,Space, Button,Popconfirm,message   } from 'antd'
import React from 'react'
import Transition from '../Static/Transition'
import { Table } from 'antd';
import { useHistory } from "react-router-dom";
import { useEffect, useState } from 'react';
import axios from '../Api/request';
import moment from 'moment';


const UserMgt = () => {

      const [userData, setuserData] = useState([]);

      let history = useHistory();

      const data = [];

      useEffect(() => {
        axios.get("api/user/getalluser")
        .then(res=>{
          setuserData(res.data.data);  
        })
      }, [])

      userData.map(ud=>{
        console.log(ud);
        data.push({
            ...ud,
            key:ud.id,
            dob: moment(new Date(ud.dob)).format('YYYY-MM-DD'),
            createdAt:moment(new Date(ud.createdAt)).format('YYYY-MM-DD'),
            role:ud.roles?ud.roles[0].roleName:"ROLE_ADMIN"
          })
      });


      const addUser = () => {
        history.push(`/userMgt/addUser`)
      };

      function confirm(key) {
        axios.delete("api/user/deleteById?id="+key)
        .then(res=>{
          console.log(res.data.message);  
        })
      }
      
      function cancel(e) {
        message.error('Click on No');
      }


      function editUser(key) {
         history.push(`/userMgt/${key}/edit`)
      };



    const columns = [
        {
          title: 'username',
          dataIndex: 'username',
        },
          {
            title: 'DOB',
            dataIndex: 'dob',
          },
        {
          title: 'Role',
          dataIndex: 'role',
        },
        {
          title: 'Created_At',
          dataIndex: 'createdAt',
        },
        {
            title: 'Action',
            key: 'action',
            render: (text, record) => (
              <Space size="middle">
                <Button onClick={() => editUser(record.key)}>Edit</Button>
                <Popconfirm
                        title="Are you sure to delete this record?"
                        onConfirm={() => confirm(record.key)}
                        onCancel={cancel}
                        okText="Yes"
                        cancelText="No"
                    >
                   <Button>Delete</Button>
                </Popconfirm>
                <Button>Block</Button>
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
           <h1>User Management</h1>
                            
           <Table bordered columns={columns} dataSource={data} />
        </Col>
        <Col span={2} style={{marginTop:"20px", marginLeft:"-90px"}}>
           <Button type="primary" onClick={addUser}>Add User</Button>        
        </Col>
       
        </Row>
        </React.Fragment>
    )
}


export default UserMgt;
