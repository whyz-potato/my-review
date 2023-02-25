import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import {createNativeStackNavigator} from "@react-navigation/native-stack";
import Login from "./src/pages/Login";
import Signup from "./src/pages/Signup";
import Mypage from "./src/pages/Mypage";
import BottomTabNav from "./src/components/BottomTabNav";

export default function App() {
    const Stack = createNativeStackNavigator();

    return (
        <NavigationContainer>
            <Stack.Navigator>
                <Stack.Screen name="login" component={Login} options={{headerShown: false}}/>
                <Stack.Screen name="signup" component={Signup} options={{headerShown: false}}/>
                <Stack.Screen name="mypage" component={Mypage} options={{}}/>
                <Stack.Screen name="main" component={BottomTabNav} options={{headerShown: false}}/>
            </Stack.Navigator>
        </NavigationContainer>
    );
}