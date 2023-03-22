import { StatusBar } from 'expo-status-bar';
import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, Pressable, Alert, Image, ScrollView } from 'react-native';
import { Like } from '../util/Like';
import URL from '../api/axios';

const ContentsDetail = ({route, navigation}) => {
    const category = route.params.category;    // book? movie?
    const userId = route.params.user_id;
    const itemId = route.params.item_id;
    const [title, setTitle] = useState("");
    const [img, setImg] = useState('https://i.postimg.cc/wBncwMHT/stacked-waves-haikei.png');
    const [releaseDate, setReleaseDate] = useState("");
    const [description, setDescription] = useState("");
    const [author, setAuthor] = useState("");
    const [extra, setExtra] = useState("");
    const [reviewId, setReviewId] = useState(null);
    
    //refresh
    useEffect(()=>{
        const unsubscribe = navigation.addListener('focus', ()=>{
            getInfo();
        })
        return ()=>{unsubscribe};
    },[navigation])

    useEffect(()=>{
        getInfo();
    }, [])
    
    const getInfo = () => {
        if (itemId == null) {
            setTitle(route.params.title);
            setReleaseDate(route.params.releaseDate);
            setImg(route.params.img == "" ? 'https://i.postimg.cc/wBncwMHT/stacked-waves-haikei.png' : route.params.img);
            setDescription(category === "book" ? route.params.description : "");
            setAuthor(category === "book" ? route.params.author : route.params.director);
            setExtra(category === "book" ? route.params.isbn : route.params.actors);
        } else {
            URL.get(`/v1/content/${category}/${userId}/${itemId}`)
                .then((res) => {
                    console.log(res.data);
                    setTitle(res.data.title);
                    setReleaseDate(res.data.releaseDate);
                    setImg(res.data.image == "" ? 'https://i.postimg.cc/wBncwMHT/stacked-waves-haikei.png' : res.data.image);
                    setDescription(category === "book" ? res.data.description : "");
                    setAuthor(category === "book" ? res.data.author : res.data.director);
                    setExtra(category === "book" ? res.data.isbn : res.data.actors);
                    setReviewId(res.data.reviewId);
                })
                .catch((err) => {
                    console.log('get info fail');
                    console.error(err);
                })
        }
    }

    const handleLike = () =>{
        if (reviewId==null){
            Like(category, userId, itemId, title, img, releaseDate, description, author, extra);
        }else {
            Alert.alert('이미 담겨있습니다!');
        }
    }

    const handlePost = () =>{
        if (reviewId==null){
            navigation.navigate('newReview', {
                user_id: userId,
                item_id: itemId,
                category: category,
                title: title,
                img: img,
                releaseDate: releaseDate,
                extra1: author,
                extra2: extra
            })
        }else {
            Alert.alert('이미 저장한 리뷰입니다!');
        }
    }

    React.useLayoutEffect(()=>{
        navigation.setOptions({
            title:'상세 정보',
            headerTintColor: '#E1D7C6',
            headerTitleStyle:{fontWeight:'bold'},
            headerRight:()=>(
                <View style={{flexDirection: 'row'}}>
                    <Pressable
                        onPress={() => { handleLike() }}>
                        <Text style={styles.headerBtn}>담기</Text>
                    </Pressable>
                    <Pressable
                        style={{ marginLeft: 13 }}
                        onPress={() => { handlePost() }}>
                        <Text style={styles.headerBtn}>저장</Text>
                    </Pressable>
                </View>
            ),
        })
    })

    return(
        <View style={styles.container}>
            <Text style={styles.title}>{title}</Text>
            <View style={{marginHorizontal: 35, marginTop: 20}}>
                <View style={{flexDirection: 'row', marginBottom: 40}}>
                    <Image
                        style={styles.image}
                        source={{ uri: img }} />
                    <View style={{justifyContent:'flex-end', width: '50%'}}>
                        <Text style={styles.size}>{author}</Text>
                        <Text style={styles.size}>{releaseDate}</Text>
                        <Text numberOfLines={3} ellipsizeMode='tail' style={styles.size}>{extra}</Text>
                    </View>
                </View>
                <View style={{maxHeight: 410}}>
                    <Text style={{fontSize: 20, fontWeight: 'bold', marginBottom:5}}>줄거리</Text>
                    <ScrollView contentContainerStyle={{flexGrow:1}}>
                        <Text style={[styles.size, styles.summary]}>{description}</Text>
                    </ScrollView>
                </View>
            </View>
            <StatusBar style='auto'/>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
      flexGrow: 1,
      backgroundColor: '#fff',
    },
    headerBtn: {
        color: '#E1D7C6', 
        fontWeight: 'bold',
        fontSize: 18,
    },
    image: {
        width: 150,
        height: 190,
        borderRadius: 7,
        marginRight: 30,
    },
    size: {
        fontSize: 18,
        marginTop: 5,
    },
    summary: {
        // borderWidth: 1,
        // borderColor: '#E1D7C6',
        backgroundColor: '#E1D7C6',
        borderRadius: 7,
        paddingVertical: 15,
        paddingHorizontal: 20,
    },
    title: {
        fontSize: 20,
        fontWeight: 'bold',
        marginTop: 25,
        alignSelf: 'center',
        color: '#4E4637',
        marginHorizontal: 20,
        textAlign: 'center'
    }
});

export default ContentsDetail;