import { StatusBar } from 'expo-status-bar';
import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Alert, Image, ScrollView } from 'react-native';
import DateTimePickerModal from "react-native-modal-datetime-picker";
import Checkbox from 'expo-checkbox';
import { Entypo, AntDesign } from '@expo/vector-icons';

const ReviewEdit = ({route, navigation}) => {
    let category = route.params.category;    // book? movie?
    const [will, setWill] = useState(false);
    const [ing, setIng] = useState(false);
    const [done, setDone] = useState(false);
    const [comment, setComment] = useState('');
    const [date, setDate] = useState(new Date());
    const [dateModal, setDateModal] = useState(false);
    const [starRate, setStarRate] = useState(1);

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
        } else if (select==='ing') {
            setWill(false);
            setIng(true);
            setDone(false);
        } else {
            setWill(false);
            setIng(false);
            setDone(true);
        }
    };

    React.useLayoutEffect(()=>{

        navigation.setOptions({
            title:'리뷰 수정',
            headerTintColor: '#E1D7C6',
            headerTitleStyle: {fontWeight: 'bold'},
            headerRight:()=>(
                <Pressable
                    onPress={() => { Alert.alert('save') }}>
                    <Text style={styles.headerBtn}>저장</Text>
                </Pressable>
            ),
        })
    })

    return (
        <ScrollView contentContainerStyle={styles.container}>
            <View style={{marginHorizontal: 30, marginVertical: 30}}>
                <View style={{ flexDirection: 'row', marginBottom: 30 }}>
                    <Image
                        style={styles.image}
                        source={{ uri: 'https://reactnative.dev/img/tiny_logo.png' }} />
                    <View style={{ justifyContent: 'flex-end' }}>
                        <Text style={styles.size}>제목</Text>
                        <Text style={styles.size}>작가</Text>
                        <Text style={styles.size}>발매일</Text>
                        <Text style={styles.size}>ISBN</Text>
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
        fontSize: 16,
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
});

export default ReviewEdit;