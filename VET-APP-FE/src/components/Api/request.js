import axios from 'axios'


//axios.defaults.baseURL = process.env.BASE_URL;


axios.defaults.baseURL =  "http://localhost:8085";


axios.interceptors.request.use(
    config =>{
        const token = sessionStorage.getItem("token");
        config.data = JSON.stringify(config.data);
        config.headers ={
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Methods': 'GET, POST, PATCH, PUT, DELETE, OPTIONS',
            'Access-Control-Allow-Headers': 'Origin, Content-Type, X-Auth-Token'
        };
        if(token)
        {
            config.headers.Authorization =token;
        }
        return config;
    },
    err =>{
        return Promise.reject(err);
    }
)



axios.interceptors.response.use(
    response => {
        if(response.status == 401) {
            //if not authorized redirect back to login
            window.location.href="/"
        }
        return response;
    },
    error => {
        return Promise.reject(error)
});





export default axios;

