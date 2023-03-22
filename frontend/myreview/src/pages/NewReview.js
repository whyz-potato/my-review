import { StatusBar } from 'expo-status-bar';
import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Alert, Image, ScrollView } from 'react-native';
import DateTimePickerModal from "react-native-modal-datetime-picker";
import Checkbox from 'expo-checkbox';
import { Entypo, AntDesign } from '@expo/vector-icons';
import URL from '../api/axios';
import { Like } from '../util/Like';

const NewReview = ({route, navigation}) => {
    let category = route.params.category;    // book? movie?
    let itemId = route.params.item_id;
    let userId = route.params.user_id;
    const [status, setStatus] = useState('');
    const [will, setWill] = useState(false);
    const [ing, setIng] = useState(false);
    const [done, setDone] = useState(false);
    const [comment, setComment] = useState('');
    const [date, setDate] = useState(new Date());
    const [dateModal, setDateModal] = useState(false);
    const [starRate, setStarRate] = useState(null);
    const [title, setTitle] = useState("");
    const [img, setImg] = useState('https://i.postimg.cc/wBncwMHT/stacked-waves-haikei.png');
    const [releaseDate, setReleaseDate] = useState("");
    const [description, setDescription] = useState("");
    const [author, setAuthor] = useState("");
    const [extra, setExtra] = useState("");

    useEffect(() => {
        if (itemId == null) {   // no item
            setTitle(route.params.title);
            setReleaseDate(route.params.releaseDate);
            setImg(route.params.img == "" ? 'https://i.postimg.cc/wBncwMHT/stacked-waves-haikei.png' : route.params.img);
            setDescription(category === "book" ? route.params.description : "");
            setAuthor(route.params.extra1);
            setExtra(route.params.extra2);
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
                })
                .catch((err) => {
                    console.log('get info fail');
                    console.error(err);
                })
        }
    }, [])

    const postReview = () =>{
        let viewDate = date.getFullYear().toString();
        let month="";
        let day=""
        if (date.getMonth()<9){
            month="0";
            month+=(date.getMonth()+1).toString();
        }else {
            month=(date.getMonth()+1).toString();
        }
        if (date.getDate()<10){
            day="0";
            day+=date.getDate().toString();
        }else {
            day=date.getDate().toString();
        }
        viewDate += month;
        viewDate += day;
        console.log(viewDate+" "+starRate);

        if (status === 'LIKE') {
            Like(category, userId, itemId, title, img, releaseDate, description, author, extra);
        } else {
            if (itemId === null) {
                if (category === "book") {
                    URL.post(`/v1/review/book/new?id=${userId}`, {
                        review: {
                            "status": status,
                            "rate": starRate,
                            "viewDate": viewDate,
                            "content": comment
                        },
                        item: {
                            "itemId": null,
                            "title": title,
                            "image": img,
                            "releaseDate": releaseDate,
                            "description": "",
                            "author": author,
                            "isbn": extra
                        }
                    })
                        .then((res) => {
                            console.log(res.data);
                            Alert.alert('My Review', '리뷰 등록 완료😃', [
                                {
                                    text: 'ok',
                                    onPress: () => { navigation.goBack(); }
                                }
                            ]);
                        })
                        .catch((err) => {
                            console.log('post book null fail');
                            console.error(err);
                            console.error(err.response);
                        })

                } else {
                    URL.post(`/v1/review/movie/new?id=${userId}`, {
                        "review": {
                            "status": status,
                            "rate": starRate,
                            "viewDate": viewDate,
                            "content": comment
                        },
                        "item": {
                            "itemId": itemId,
                            "title": title,
                            "image": img,
                            "releaseDate": releaseDate,
                            "description": description,
                            "director": author,
                            "actors": extra
                        }
                    })
                        .then((res) => {
                            console.log(res.data);
                            Alert.alert('My Review', '리뷰 등록 완료😃', [
                                {
                                    text: 'ok',
                                    onPress: () => { navigation.goBack(); }
                                }
                            ]);
                        })
                        .catch((err) => {
                            console.log('post movie null fail');
                            console.error(err);
                        })
                }
            } else {
                URL.post(`/v1/review/${category}/new?id=${userId}`, {
                    "review": {
                        "status": status,
                        "rate": starRate,
                        "viewDate": viewDate,
                        "content": comment
                    },
                    "item": {
                        "itemId": itemId
                    }
                })
                    .then((res) => {
                        console.log(res.data);
                        Alert.alert('My Review', '리뷰 등록 완료😃', [
                            {
                                text: 'ok',
                                onPress: () => { navigation.goBack(); }
                            }
                        ]);
                    })
                    .catch((err) => {
                        console.log(`post ${category} fail`);
                        console.error(err);
                    })
            }
        }
    }

    const showDatePicker = () => {
        setDateModal(true);
    };
    const hideDatePicker = () => {
        setDateModal(false);
    };
    const handleDate = (date) => {
        setDate(date);
        hideDatePicker();
    };

    const handleStatus = (select) => {
        if (select==='will') {
            setWill(true);
            setIng(false);
            setDone(false);
            setStatus('LIKE');
        } else if (select==='ing') {
            setWill(false);
            setIng(true);
            setDone(false);
            setStatus('WATCHING');
        } else {
            setWill(false);
            setIng(false);
            setDone(true);
            setStatus('DONE');
        }
    };

    React.useLayoutEffect(()=>{

        navigation.setOptions({
            title:'새로운 리뷰',
            headerTintColor: '#E1D7C6',
            headerTitleStyle: {fontWeight: 'bold'},
            headerRight:()=>(
                <Pressable
                    style={{marginRight: 6}}
                    onPress={() => { postReview() }}>
                    <Text style={styles.headerBtn}>저장</Text>
                </Pressable>
            ),
        })
    })

    return (
        <ScrollView contentContainerStyle={styles.container}>
            <Text style={styles.title}>{title}</Text>
            <View style={{marginHorizontal: 30, marginBottom: 20}}>
                <View style={{ flexDirection: 'row', marginBottom: 30 }}>
                    <Image
                        style={styles.image}
                        source={{ uri: img }} />
                    <View style={{ justifyContent: 'flex-end', width: '50%' }}>
                        <Text style={styles.size}>{author}</Text>
                        <Text style={styles.size}>{releaseDate}</Text>
                        <Text numberOfLines={3} ellipsizeMode='tail' style={styles.size}>{extra}</Text>
                    </View>
                </View>
                {/* 보기 상태 */}
                <View style={styles.statusContainer}>
                    <View style={styles.row}>
                        <Checkbox
                            style={styles.checkbox}
                            value={will}
                            onValueChange={()=>handleStatus('will')} 
                            color={will ? '#77BDC3' : undefined}/>
                        <Text style={{fontSize: 18, fontWeight: 'bold'}}>보고싶은</Text>
                    </View>
                    <View style={styles.row}>
                        <Checkbox
                            style={styles.checkbox}
                            value={ing}
                            onValueChange={()=>handleStatus('ing')} 
                            color={ing ? '#77BDC3' : undefined} />
                        <Text style={{fontSize: 18, fontWeight: 'bold'}}>보는중</Text>
                    </View>
                    <View style={styles.row}>
                        <Checkbox
                            style={styles.checkbox}
                            value={done}
                            onValueChange={()=>handleStatus('done')} 
                            color={done ? '#77BDC3' : undefined} />
                        <Text style={{fontSize: 18, fontWeight: 'bold'}}>완료</Text>
                    </View>
                </View>
                {/* 보고싶은 -> hide */}
                {!will && <View>
                    <View style={[styles.row, {marginBottom: 20}]}>
                        <Pressable 
                            style={{marginRight: 10}}
                            onPress={showDatePicker}>
                            <Entypo name="calendar" size={30} color="#77BDC3" />
                        </Pressable>
                        <DateTimePickerModal
                            isVisible={dateModal}
                            mode="date"
                            onConfirm={handleDate}
                            onCancel={hideDatePicker}
                        />
                        <Text style={{fontSize: 18}}>본 날짜 : {date.toLocaleDateString()}</Text>
                    </View>
                    {/* star rate */}
                    <Text style={{fontSize: 20, fontWeight: 'bold', marginBottom:10}}>별점</Text>
                    <View style={[styles.row, {justifyContent: 'center', marginBottom: 20}]}>
                        <Pressable onPress={()=>setStarRate(1)}>
                            <AntDesign 
                            name={starRate >= 1 ? "star":"staro"} 
                            size={50} 
                            style={styles.star} />
                        </Pressable>
                        <Pressable onPress={()=>setStarRate(2)}>
                            <AntDesign 
                            name={starRate >= 2 ? "star":"staro"} 
                            size={50} 
                            style={styles.star} />
                        </Pressable>
                        <Pressable onPress={()=>setStarRate(3)}>
                            <AntDesign 
                            name={starRate >= 3 ? "star":"staro"} 
                            size={50} 
                            style={styles.star} />
                        </Pressable>
                        <Pressable onPress={()=>setStarRate(4)}>
                            <AntDesign 
                            name={starRate >= 4 ? "star":"staro"} 
                            size={50} 
                            style={styles.star} />
                        </Pressable>
                        <Pressable onPress={()=>setStarRate(5)}>
                            <AntDesign 
                            name={starRate >= 5 ? "star":"staro"} 
                            size={50} 
                            style={{color: '#FFD56D'}} />
                        </Pressable>
                    </View>
                    <View>
                        <View>
                            <Text style={{ fontSize: 20, fontWeight: 'bold' }}>리뷰</Text>
                            <TextInput 
                                value={comment}
                                style={[{ fontSize: 15, textAlignVertical: 'top' }, styles.comment]}
                                multiline
                                onChangeText={setComment}
                            />
                        </View>
                    </View>
                </View>}
            </View>
            <StatusBar style='auto'/>
        </ScrollView>
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
    statusContainer: {
        marginBottom: 30,
        flexDirection:'row', 
        justifyContent:'space-evenly',
        backgroundColor: '#E1D7C6', 
        borderRadius: 30,
        paddingHorizontal: 10,
        paddingVertical: 15,
    },
    row: {
        flexDirection: 'row',
        alignItems: 'center',
    },
    comment: {
        borderWidth: 1,
        borderColor: '#E1D7C6',
        borderRadius: 7,
        padding: 15,
        marginTop: 10,
        height: 300,
        width: 350,
    },
    checkbox:{
        marginRight: 7,
    },
    star: {
        color: '#FFD56D',
        marginRight: 8
    },  
    title: {
        fontSize: 20,
        fontWeight: 'bold',
        marginTop: 20,
        marginBottom: 15,
        alignSelf: 'center',
        color: '#4E4637',
        marginHorizontal: 20,
        textAlign: 'center'
    }
});

export default NewReview;