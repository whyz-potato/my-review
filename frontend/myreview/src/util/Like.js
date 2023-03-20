import { Alert } from "react-native";
import URL from "../api/axios"

// 담기 book -> extra1: author, extra2: isbn / movie-> director, actors
export const Like = (category, userId, itemId, title, imgUrl, releaseDate, description, extra1, extra2) => {
    let image=imgUrl;
    if (imgUrl=="") image="https://i.postimg.cc/wBncwMHT/stacked-waves-haikei.png";

    console.log(userId+" "+itemId+" "+title);

    if (itemId === null) {
        if (category === "book") {
            URL.post(`/v1/review/book/new?id=${userId}`, {
                review: {
                    "status": "LIKE"
                },
                item: {
                    "itemId": null,
                    "title": title,
                    "image": image,
                    "releaseDate": releaseDate,
                    "description": description,
                    "author": extra1,
                    "isbn": extra2
                }
            })
                .then((res) => {
                    console.log(res.data);
                    Alert.alert('담기 성공!');
                })
                .catch((err) => {
                    console.log('like book null fail');
                    console.error(err);
                    console.error(err.response);
                })

        } else {
            URL.post(`/v1/review/movie/new?id=${userId}`, {
                "review": {
                    "status": "LIKE"
                },
                "item": {
                    "itemId": itemId,
                    "title": title,
                    "image": image,
                    "releaseDate": releaseDate,
                    "description": description,
                    "director": extra1,
                    "actors": extra2
                }
            })
                .then((res) => {
                    console.log(res.data);
                    Alert.alert('담기 성공!');
                })
                .catch((err) => {
                    console.log('like movie null fail');
                    console.error(err);
                })
        }
    }else {
        URL.post(`/v1/review/${category}/new?id=${userId}`, {
            "review": {
                "status": "LIKE"
            },
            "item": {
                "itemId": itemId
            }
        })
        .then((res)=>{
            console.log(res.data);
            Alert.alert('담기 성공!');
        })
        .catch((err)=>{
            console.log(`like ${category} fail`);
            console.error(err);
        })
    }
}