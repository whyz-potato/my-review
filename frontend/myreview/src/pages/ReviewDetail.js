import { StatusBar } from 'expo-status-bar';
import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, Pressable, Alert, Image, ScrollView } from 'react-native';
import { AntDesign } from '@expo/vector-icons';
import URL from '../api/axios';

const ReviewDetail = ({route, navigation}) => {
    const category = route.params.category;
    const userId = route.params.user_id;
    const reviewId = route.params.review_id;
    const [starRate, setStarRate] = useState(1);
    const [detail, setDetail] = useState([]);   // review detail
    const [info, setInfo] = useState([]);   // contents info
    const [status, setStatus] = useState('');

    //refresh
    useEffect(()=>{
        const unsubscribe = navigation.addListener('focus', ()=>{
            getInfo();
        })
        return ()=>{unsubscribe};
    },[navigation])

    useEffect(()=>{
        getInfo();
    },[])

    const getInfo = ()=>{
        URL.get(`/v1/review/${category}/${userId}/${reviewId}`)
        .then((res)=>{
            console.log(res.data);
            setDetail(res.data.review);
            setInfo(res.data.item);
            setStarRate(res.data.review.rate);
        })
        .catch((err)=>{
            console.log('get review fail');
            console.error(err);
        })
    }

    useEffect(() =>{
        if (detail.status === "LIKE"){
            setStatus('보고싶은');
        }else if (detail.status === "WATCHING"){
            setStatus('보는중');
        }else{
            setStatus('완료');
        }
    }, [detail])

    const delReview = () => {
        URL.delete(`/v1/review/${userId}/${reviewId}`)
        .then((res)=>{
            console.log('delete success');
            Alert.alert('My Review', '정말 삭제하시겠습니까?', [
                {
                    text: '아니오',
                    onPress: () => console.log('cancel'),
                },
                {
                    text: '네',
                    onPress: () => { navigation.goBack() }
                }
            ]);
        })
        .catch((err)=>{
            console.log('delete fail');
            console.error(err);
        })
    }

    React.useLayoutEffect(()=>{
        navigation.setOptions({
            title:'',
            headerTintColor: '#E1D7C6',
            headerRight:()=>(
                <View style={{flexDirection: 'row'}}>
                    <Pressable
                        onPress={() => {navigation.navigate('reviewEdit', {category: category, review_id: detail.reviewId, user_id: userId})}}>
                        <Text style={styles.headerBtn}>수정</Text>
                    </Pressable>
                    <Pressable
                        style={{ marginLeft: 13 }}
                        onPress={() => { delReview() }}>
                        <Text style={styles.headerBtn}>삭제</Text>
                    </Pressable>
                </View>
            ),
        })
    })

    return(
        <View style={styles.container}>
            <Text style={styles.title}>{info.title}</Text>
            <View style={{marginHorizontal: 30, marginBottom: 30}}>
                <View style={{flexDirection: 'row', marginBottom: 20}}>
                    <Image
                        style={styles.image}
                        source={info.image===""? {uri:'https://i.postimg.cc/wBncwMHT/stacked-waves-haikei.png'}:{uri: info.image}} />
                    <View style={{justifyContent:'flex-end', width: '50%'}}>
                        <Text style={styles.size}>{category==='book'?info.author:info.director}</Text>
                        <Text style={styles.size}>{info.releaseDate}</Text>
                        <Text numberOfLines={1} ellipsizeMode='tail' style={styles.size}>{category==='book'?info.isbn:info.actors}</Text>
                        <Text style={styles.size}>{detail.reviewId===null?"":detail.viewDate}</Text>
                        <Text style={styles.status}>{status}</Text>
                    </View>
                </View>
                {detail.status !== "LIKE" &&
                    <View>
                        <Text style={{ fontSize: 20, fontWeight: 'bold', marginBottom: 10 }}>별점</Text>
                        <View style={[styles.row, { justifyContent: 'center', marginBottom: 20 }]}>
                            <AntDesign
                                name={starRate >= 1 ? "star" : "staro"}
                                size={50}
                                style={styles.star} />
                            <AntDesign
                                name={starRate >= 2 ? "star" : "staro"}
                                size={50}
                                style={styles.star} />
                            <AntDesign
                                name={starRate >= 3 ? "star" : "staro"}
                                size={50}
                                style={styles.star} />
                            <AntDesign
                                name={starRate >= 4 ? "star" : "staro"}
                                size={50}
                                style={styles.star} />
                            <AntDesign
                                name={starRate >= 5 ? "star" : "staro"}
                                size={50}
                                style={{ color: '#FFD56D' }} />
                        </View>
                        <View style={{maxHeight: 410}}>
                            <Text style={{ fontSize: 20, fontWeight: 'bold' }}>리뷰</Text>
                            <ScrollView contentContainerStyle={{ flexGrow: 1 }}>
                                <Text style={[styles.summary]}>{detail.content}</Text>
                            </ScrollView>
                        </View>
                    </View>
                }
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
    row: {
        flexDirection: 'row',
        alignItems: 'center',
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
        fontSize: 15,
        marginTop: 5,
    },
    status: {
        backgroundColor: '#77BDC3',
        borderRadius: 20,
        padding: 5,
        textAlign: 'center',
        fontSize: 15,
        marginTop: 5,
        marginLeft: -5,
        width: 80
    },
    comment: {
        borderWidth: 1,
        borderColor: '#E1D7C6',
        borderRadius: 7,
        paddingVertical: 15,
        paddingHorizontal: 20,
        fontSize: 18,
    },
    star: {
        color: '#FFD56D',
        marginRight: 8
    },
    title: {
        fontSize: 20,
        fontWeight: 'bold',
        marginTop: 20,
        marginBottom: 10,
        alignSelf: 'center',
        color: '#4E4637',
        marginHorizontal: 20,
        textAlign: 'center'
    },
    summary: {
        borderWidth: 1,
        borderColor: '#E1D7C6',
        borderRadius: 7,
        paddingVertical: 15,
        paddingHorizontal: 20,
        fontSize: 18,
        marginTop: 5,
        height: 300
    }
});

export default ReviewDetail;