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
import { render } from '@testing-library/react';
import { SearchOutlined } from '@ant-design/icons';
import Highlighter from 'react-highlight-words';

const AniamlMgt = () => {
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
        axios.get("api/animal/getAvailableAnimal")
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
  

    class App extends React.Component {
      state = {
        searchText: '',
        searchedColumn: '',
      };
      getColumnSearchProps = dataIndex => ({
        filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters }) => (
          <div style={{ padding: 8 }}>
            <Input
              ref={node => {
                this.searchInput = node;
              }}
              placeholder={`Search ${dataIndex}`}
              value={selectedKeys[0]}
              onChange={e => setSelectedKeys(e.target.value ? [e.target.value] : [])}
              onPressEnter={() => this.handleSearch(selectedKeys, confirm, dataIndex)}
              style={{ marginBottom: 8, display: 'block' }}
            />
            <Space>
              <Button
                type="primary"
                onClick={() => this.handleSearch(selectedKeys, confirm, dataIndex)}
                icon={<SearchOutlined />}
                size="small"
                style={{ width: 90 }}
              >
                Search
              </Button>
              <Button onClick={() => this.handleReset(clearFilters)} size="small" style={{ width: 90 }}>
                Reset
              </Button>
              <Button
                type="link"
                size="small"
                onClick={() => {
                  confirm({ closeDropdown: false });
                  this.setState({
                    searchText: selectedKeys[0],
                    searchedColumn: dataIndex,
                  });
                }}
              >
                Filter
              </Button>
            </Space>
          </div>
        ),
        filterIcon: filtered => <SearchOutlined style={{ color: filtered ? '#1890ff' : undefined }} />,
        onFilter: (value, record) =>
          record[dataIndex]
            ? record[dataIndex].toString().toLowerCase().includes(value.toLowerCase())
            : '',
        onFilterDropdownVisibleChange: visible => {
          if (visible) {
            setTimeout(() => this.searchInput.select(), 100);
          }
        },
        render: text =>
          this.state.searchedColumn === dataIndex ? (
            <Highlighter
              highlightStyle={{ backgroundColor: '#ffc069', padding: 0 }}
              searchWords={[this.state.searchText]}
              autoEscape
              textToHighlight={text ? text.toString() : ''}
            />
          ) : (
            text
          ),
      });
    
      handleSearch = (selectedKeys, confirm, dataIndex) => {
        confirm();
        this.setState({
          searchText: selectedKeys[0],
          searchedColumn: dataIndex,
        });
      };
    
      handleReset = clearFilters => {
        clearFilters();
        this.setState({ searchText: '' });
      };
      
      render(){
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
              ...this.getColumnSearchProps('age'),
              sorter: (a, b) => a.age - b.age,
            },
          {
            title: 'Breed',
            dataIndex: 'breed',
            ...this.getColumnSearchProps('breed'),
          },
          {
            title: 'Status',
            dataIndex: 'status',
            ...this.getColumnSearchProps('status'),
            sorter: (a, b) =>  a.status.localeCompare(b.status),
          }, 
          {
            title: 'Request to',
            dataIndex: 'userid',
            render:(text,record) => (
              localStorage.getItem("role")==ROLE_TEACHINGTECH?
                (<Select
                placeholder="Select a technician"
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
            <h1>Animal Management</h1>
            <Table bordered columns={columns} dataSource={data} />
            {/* <h1>Your Request</h1>
            <Table bordered columns={req_columns} dataSource={data} /> */}
          </Col>
          </Row>
          </React.Fragment>
      )
}
}
};




export default AniamlMgt;
