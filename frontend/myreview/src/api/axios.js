import axios from "axios";
import AsyncStorage from '@react-native-async-storage/async-storage';

let token="";

const getToken = async () => {
    try {
      const val = await AsyncStorage.getItem('accessToken');
      if (val !== null) token = val;
    } catch (error) {
        console.log("get token fail");
      console.log(error);
    }

    return token;
}

const URL = axios.create({
    baseURL: "http://10.0.2.2:8080",
    // timeout: 5000,
    withCredentials: true,
    headers: {
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Method": "*",
        "Access-Control-Allow-Headers": "Content-Type, Accept",
        "Content-Type": "application/json",
        Accept: "application/json",
        'Access-Token': getToken(),
    },
});

export default URL;