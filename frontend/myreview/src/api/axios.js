import axios from "axios";
import * as Network from 'expo-network';

// let hostIP = "192.168.0.13";
// let hostIP = "";

// async () => {
//     try {
//         hostIP = await Network.getIpAddressAsync();
//     } catch (error) {
//         console.log(error);
//     }
// }


const URL = axios.create({
    baseURL: "http://192.168.0.13:8080/v1",
    headers: {
        "Content-Type": "application/json",
        "Accept": "application/json",
    },
});

export default URL;