import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import {createNativeStackNavigator} from "@react-navigation/native-stack";

import BottomTabNav from "./BottomTabNav";
import Mypage from "../pages/Mypage";
import ProfileEdit from "../pages/ProfileEdit";
import BookSearchResult from "../pages/BookSearchResult";
import MovieSearchResult from "../pages/MovieSearchResult";
import NewReview from "../pages/NewReview";
import ContentsDetail from "../pages/ContentsDetail";
import ReviewDetail from "../pages/ReviewDetail";
import ReviewEdit from "../pages/ReviewEdit";

export default function AuthStack() {
    const Stack = createNativeStackNavigator();

    return(
        <Stack.Navigator>
            <Stack.Screen name="main" component={BottomTabNav} options={{ headerShown: false }} />
            <Stack.Screen name="mypage" component={Mypage} options={{}} />
            <Stack.Screen name="profileEdit" component={ProfileEdit} options={{}} />
            <Stack.Screen name="bookSearchResult" component={BookSearchResult} options={{}} />
            <Stack.Screen name="movieSearchResult" component={MovieSearchResult} options={{}} />
            <Stack.Screen name="newReview" component={NewReview} options={{}} />
            <Stack.Screen name="contentsDetail" component={ContentsDetail} options={{}} />
            <Stack.Screen name="reviewDetail" component={ReviewDetail} options={{}} />
            <Stack.Screen name="reviewEdit" component={ReviewEdit} options={{}} />
        </Stack.Navigator>
    );
}