import URL from "../api/axios";
import AsyncStorage from '@react-native-async-storage/async-storage';

export const logout = async({navigation}) =>{
    console.log('logout');
    try {
        AsyncStorage.clear();
        navigation.navigate('login');
    } catch (error) {
        console.log(error);
    }
}

export const resign = () =>{

}