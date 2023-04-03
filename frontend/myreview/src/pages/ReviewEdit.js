import { StatusBar } from 'expo-status-bar';
import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Alert, Image, ScrollView } from 'react-native';
import DateTimePickerModal from "react-native-modal-datetime-picker";
import Checkbox from 'expo-checkbox';
import { Entypo, AntDesign } from '@expo/vector-icons';
import URL from '../api/axios';

const ReviewEdit = ({route, navigation}) => {
    let category = route.params.category;    // book? movie?
    let userId = route.params.user_id;
    let reviewId = route.params.review_id;
    const [will, setWill] = useState(false);
    const [ing, setIng] = useState(false);
    const [done, setDone] = useState(false);
    const [comment, setComment] = useState('');
    const [date, setDate] = useState(null);
    const [dateModal, setDateModal] = useState(false);
    const [starRate, setStarRate] = useState(null);
    const [detail, setDetail] = useState([]);   // review detail
    const [info, setInfo] = useState([]);   // contents info
    const [status, setStatus] = useState('');
    const [dateChanged, setDateChanged] = useState(false);

    useEffect(()=>{
        URL.get(`/v1/review/${category}/${userId}/${reviewId}`)
        .then((res)=>{
            console.log(res.data);
            setDetail(res.data.review);
            setInfo(res.data.item);
            setStarRate(res.data.review.rate);
            setStatus(res.data.review.status);
            setComment(res.data.review.content);
            initStatus(res.data.review.status);
        })
        .catch((err)=>{
            console.log('get review fail');
            console.error(err);
        })
    }, [])

    const initStatus = (st) =>{
        console.log(st);
        if (st === "LIKE"){
            setWill(true);
            setIng(false);
            setDone(false);
        }else if (st === "WATCHING"){
            setWill(false);
            setIng(true);
            setDone(false);
            
        }else{
            setWill(false);
            setIng(false);
            setDone(true);
        }
    }

    const putReview = () => {
        if (detail.viewDate===null){
            Alert.alert('날짜를 선택해주세요.');
        }else {
            let viewDate="";
            if (dateChanged) {
                viewDate = date.getFullYear().toString();
                let month = "";
                let day = ""
                if (date.getMonth() < 9) {
                    month = "0";
                    month += (date.getMonth() + 1).toString();
                } else {
                    month = (date.getMonth() + 1).toString();
                }
                if (date.getDate() < 10) {
                    day = "0";
                    day += date.getDate().toString();
                } else {
                    day = date.getDate().toString();
                }
                viewDate += month;
                viewDate += day;
            }else {
                viewDate = detail.viewDate;
            }
            console.log(viewDate);
            
            URL.put(`/v1/review/${category}/${userId}/${reviewId}`, {
                "status": status,
                "rate": starRate,
                "viewDate": viewDate,
                "content": comment
            })
            .then((res) => {
                console.log(res.data);
                Alert.alert('My Review', '리뷰 수정 완료😃', [
                    {
                        text: 'ok',
                        onPress: () => { navigation.goBack(); }
                    }
                ]);
            })
            .catch((err) => {
                console.log('put fail');
                console.error(err);
                console.error(err.response);
            })
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
        setDateChanged(true);
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
            title:'리뷰 수정',
            headerTintColor: '#E1D7C6',
            headerTitleStyle: {fontWeight: 'bold'},
            headerRight:()=>(
                <Pressable
                    onPress={() => { putReview() }}>
                    <Text style={styles.headerBtn}>저장</Text>
                </Pressable>
            ),
        })
    })

    return (
        <ScrollView contentContainerStyle={styles.container}>
            <Text style={styles.title}>{info.title}</Text>
            <View style={{marginHorizontal: 30, marginBottom: 30}}>
                <View style={{ flexDirection: 'row', marginBottom: 30 }}>
                    <Image
                        style={styles.image}
                        source={info.image===""? {uri:'https://i.postimg.cc/wBncwMHT/stacked-waves-haikei.png'}:{uri: info.image}} />
                    <View style={{ justifyContent: 'flex-end', width: '50%' }}>
                        <Text style={styles.size}>{category==='book'?info.author:info.director}</Text>
                        <Text style={styles.size}>{info.releaseDate}</Text>
                        <Text numberOfLines={1} ellipsizeMode='tail' style={styles.size}>{category==='book'?info.isbn:info.actors}</Text>
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
                        <Text style={{fontSize: 18}}>본 날짜 : {dateChanged?date.toLocaleDateString():detail.viewDate}</Text>
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
        marginBottom: 10,
        alignSelf: 'center',
        color: '#4E4637',
        marginHorizontal: 20,
        textAlign: 'center'
    }
});

export default ReviewEdit;