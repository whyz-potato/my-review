import axios from "axios";
import * as Network from 'expo-network';

// let hostIP = "";

// async () => {
//     try {
//         hostIP = await Network.getIpAddressAsync();
//     } catch (error) {
//         console.log(error);
//     }
// }


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
    },
});

export default URL;