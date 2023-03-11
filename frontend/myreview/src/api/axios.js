import axios from "axios";
import AsyncStorage from '@react-native-async-storage/async-storage';

let accessToken="";
async() => {
    try {
       accessToken = await AsyncStorage.getItem('accessToken');
    } catch (error) {
        console.log(error);
    }
}

const URL = axios.create({
    baseURL: "https://p3q4govhwg.execute-api.ap-northeast-2.amazonaws.com/v1/",    //for test
    headers: {
        "Content-Type": "application/json",
        "Access-Token": accessToken,
    }
});

export default URL;