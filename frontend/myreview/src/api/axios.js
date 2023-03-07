import axios from "axios";

const URL = axios.create({
    baseURL: "https://p3q4govhwg.execute-api.ap-northeast-2.amazonaws.com/",
    headers: {
        "Content-Type": "application/json",
    }
});

export default URL;