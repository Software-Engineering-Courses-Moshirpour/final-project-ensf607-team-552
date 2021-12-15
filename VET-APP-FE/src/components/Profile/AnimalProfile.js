import { Row,Col,Card,Comment, Tooltip, Avatar,Form, Input,Spin, Table, Button, Space} from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import React,{ Fragment, useEffect, useState }  from 'react';
import Transition from '../Static/Transition'
import axios from '../Api/request';
import { useParams } from "react-router-dom";
import moment from 'moment';
import { useForm } from 'antd/lib/form/Form';
import { ROLE_ADMIN } from '../DummyData/dummy';


const AnimalProfile = () => {
    let { id } = useParams();
    const [src, setsrc] = useState('');
    const [commentData, setcommentData] = useState([]);
    const [loading, setloading] = useState(true);
    const [animalForm] = useForm();
    const data = [];

    useEffect(() => {
      axios.get("api/animal/getAnimalById?id="+id)
      .then(res=>{    
          setcommentData(res.data.data.comments)
          setsrc(res.data.data.url)
          data.push({
            ...res.data.data,
            breed:res.data.data.breed
          })
      })
      .then(res1=>{
        animalForm.setFieldsValue(data[0]);
        setloading(false);
      })
      
  }, [data])
    
    
    return (
        <React.Fragment>
        <Row>
        <Col span={4} style={{marginTop:"-80px"}}>
        <Transition></Transition>
        </Col>
        <Col span={3} style={{marginTop:"20px"}}>
           <h1>Animal Profile</h1>
           {localStorage.getItem("role")==ROLE_ADMIN && loading && 
              <Spin />
           }

           {localStorage.getItem("role")==ROLE_ADMIN && !loading && 
           <Card
              hoverable
              style={{ width: 240 }}
              cover={<img alt="example" src={src}/>}
            >
              
             <h2>Comments</h2>

              {commentData.map(cd =>(
                    
                    <Comment
                    key={cd.id}
                    author={<a>{cd.user}</a>}
                    avatar={<Avatar src="https://joeschmoe.io/api/v1/random" alt="Han Solo" />}
                    content={
                      <p>
                        {cd.description}
                      </p>
                    }
                    datetime={
                      <Tooltip title={moment().format('YYYY-MM-DD')}>
                        <span>{moment(new Date(cd.created)).format('YYYY-MM-DD')}</span>
                      </Tooltip>
                    }
                  />
                ))}
                
            </Card>
          }


          {localStorage.getItem("role")!=ROLE_ADMIN && loading &&
                      
                      <Spin/>
                    }  

         {localStorage.getItem("role")!=ROLE_ADMIN && !loading &&
            <Card
              hoverable
              style={{ width: 240 }}
              cover={<img alt="example" src={src}/>}
            >    
            </Card> }         
        </Col>
        <Col span={6} style={{marginTop:"60px", marginLeft: "60px"}}>
        <Form 
        form={animalForm}
        labelCol={{ span: 6 }}
        wrapperCol={{ span: 16 }}
        name="dynamic_rule">

            <Form.Item
                name="breed"
                label="breed"
            >
                <Input disabled />
            </Form.Item>

            <Form.Item
                name="color"
                label="color"
            >
                <Input disabled />
            </Form.Item>

            <Form.Item
                name="region"
                label="region"
            >
                <Input disabled />
            </Form.Item>

            <Form.Item
                name="sex"
                label="sex"
            >
                <Input disabled />
            </Form.Item>

            <Form.Item
                name="type"
                label="type"
            >
                <Input disabled />
            </Form.Item>

        </Form>        
        </Col>
        </Row>
        </React.Fragment>
    )
}


export default AnimalProfile;
