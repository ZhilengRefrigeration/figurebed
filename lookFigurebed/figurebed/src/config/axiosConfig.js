import axios from 'axios';
import { serverUrl } from '/public/static/config.json';
// 创建 axios 请求实例
const serviceAxios = axios.create({
    baseURL: serverUrl, // 基础请求地址
    timeout: 10000, // 请求超时设置
    headers: {
        "Content-Type": "application/json",
        "token": localStorage.getItem('token')
    }
});
export default serviceAxios;