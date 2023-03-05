import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import {createNativeStackNavigator} from "@react-navigation/native-stack";
import BottomTabNav from "./src/navigation/BottomTabNav";
import Login from "./src/pages/Login";
import Signup from "./src/pages/Signup";
import Mypage from "./src/pages/Mypage";
import BookSearchResult from "./src/pages/BookSearchResult";
import MovieSearchResult from "./src/pages/MovieSearchResult";
import ProfileEdit from "./src/pages/ProfileEdit";
import NewReview from "./src/pages/NewReview";
import ContentsDetail from "./src/pages/ContentsDetail";
import ReviewDetail from "./src/pages/ReviewDetail";
import ReviewEdit from "./src/pages/ReviewEdit";

export default function App() {
    const Stack = createNativeStackNavigator();

    return (
        <NavigationContainer>
            <Stack.Navigator>
                <Stack.Screen name="login" component={Login} options={{headerShown: false}}/>
                <Stack.Screen name="signup" component={Signup} options={{headerShown: false}}/>
                <Stack.Screen name="mypage" component={Mypage} options={{}}/>
                <Stack.Screen name="profileEdit" component={ProfileEdit} options={{}}/>
                <Stack.Screen name="bookSearchResult" component={BookSearchResult} options={{}}/>
                <Stack.Screen name="movieSearchResult" component={MovieSearchResult} options={{}}/>
                <Stack.Screen name="newReview" component={NewReview} options={{}}/>
                <Stack.Screen name="contentsDetail" component={ContentsDetail} options={{}}/>
                <Stack.Screen name="reviewDetail" component={ReviewDetail} options={{}}/>
                <Stack.Screen name="reviewEdit" component={ReviewEdit} options={{}}/>
                <Stack.Screen name="main" component={BottomTabNav} options={{headerShown: false}}/>
            </Stack.Navigator>
        </NavigationContainer>
    );
}