import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import {createNativeStackNavigator} from "@react-navigation/native-stack";
import Login from "./src/pages/Login";
import Signup from "./src/pages/Signup";

export default function App() {
    const Stack = createNativeStackNavigator();

    return (
        <NavigationContainer>
            <Stack.Navigator>
                <Stack.Screen name="login" component={Login} options={{headerShown: false}}/>
                <Stack.Screen name="signup" component={Signup} options={{headerShown: false}}/>
            </Stack.Navigator>
        </NavigationContainer>
    );
}