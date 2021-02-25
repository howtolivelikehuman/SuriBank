import axios from 'axios'

const { token } = window.localStorage;

const api = axios.create({
    baseURL: 'http://localhost:8081/api/',
    headers:{
        'Content-Type': 'application/json',
        TOKEN : token && token.length > 0 ? token : ''
    }
})

const set_token = (token) => {
    api.defaults.headers['TOKEN'] = token;
}



export default api
