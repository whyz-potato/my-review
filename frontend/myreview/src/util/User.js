import URL from "../api/axios";
import AsyncStorage from '@react-native-async-storage/async-storage';

export const logout = async({navigation}) => {
    console.log('logout');
    try {
        AsyncStorage.clear();
        navigation.navigate('login');
    } catch (error) {
        console.log(error);
    }
}

export const resign = async({navigation}, userId) => {
    console.log('resign');
    URL.post(`/v1/users/resign/${userId}`)
    .then((res)=>{
        console.log(res.data);
        try {
            AsyncStorage.clear();
            navigation.navigate('login');
        } catch (error) {
            console.log(error);
        }
    })
    .catch((err)=>{
        console.log('resign fail');
        console.error(err);
        console.log(err.response);
    })
}