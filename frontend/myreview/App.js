import React, { useEffect, useState } from "react";
import { NavigationContainer } from "@react-navigation/native";
import {createNativeStackNavigator} from "@react-navigation/native-stack";
import AsyncStorage from '@react-native-async-storage/async-storage';
import AuthStack from "./src/navigation/AuthStack";
import Login from "./src/pages/Login";
import Signup from "./src/pages/Signup";

export default function App() {
    const Stack = createNativeStackNavigator();
    const [isLogin, setIslogin] = useState(false);

    useEffect(() => {
        async () => {
            if (await AsyncStorage.getItem('userId') !== null) {
                setIslogin(true);
            }
        }
    });

    return (
        <NavigationContainer>
            <Stack.Navigator>
                {isLogin?(
                    <>
                    <Stack.Screen name="auth" component={AuthStack} options={{ headerShown: false }} />
                    </>
                ):(
                    <>
                    <Stack.Screen name="login" component={Login} options={{ headerShown: false }} />
                    <Stack.Screen name="signup" component={Signup} options={{headerShown: false}}/>
                    <Stack.Screen name="auth" component={AuthStack} options={{ headerShown: false }} />
                    </>
                )} 
            </Stack.Navigator>
        </NavigationContainer>
    );
}