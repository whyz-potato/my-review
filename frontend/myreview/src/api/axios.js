import axios from "axios";
import AsyncStorage from '@react-native-async-storage/async-storage';

const URL = axios.create({
    baseURL: "http://10.0.2.2:8080",
    // timeout: 5000,
    withCredentials: true,
});

URL.interceptors.request.use(
  async (config) => {
    const token = await AsyncStorage.getItem('accessToken')
    config.headers["Content-Type"] = "application/json"
    config.headers.Accept = "application/json"
    config.headers["X-AUTH-TOKEN"] = token
    return config;
  },
  err => {
    return Promise.reject(err);
  }
)

export default URL;