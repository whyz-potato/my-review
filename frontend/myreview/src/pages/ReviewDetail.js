import { StatusBar } from 'expo-status-bar';
import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, Pressable, Alert, Image } from 'react-native';
import { AntDesign } from '@expo/vector-icons';

const ReviewDetail = ({route, navigation}) => {
    let category = route.params.category;
    const [starRate, setStarRate] = useState(1);

    React.useLayoutEffect(()=>{
        navigation.setOptions({
            title:'',
            headerTintColor: '#E1D7C6',
            headerRight:()=>(
                <View style={{flexDirection: 'row'}}>
                    <Pressable
                        onPress={() => {navigation.navigate('reviewEdit', {category: 'book'})}}>
                        <Text style={styles.headerBtn}>수정</Text>
                    </Pressable>
                    <Pressable
                        style={{ marginLeft: 13 }}
                        onPress={() => { Alert.alert('delete') }}>
                        <Text style={styles.headerBtn}>삭제</Text>
                    </Pressable>
                </View>
            ),
        })
    })

    return(
        <View style={styles.container}>
            <View style={{marginHorizontal: 40, marginVertical: 30}}>
                <View style={{flexDirection: 'row', marginBottom: 30}}>
                    <Image
                        style={styles.image}
                        source={{ uri: 'https://reactnative.dev/img/tiny_logo.png' }} />
                    <View style={{justifyContent:'flex-end'}}>
                        <Text style={styles.size}>제목</Text>
                        <Text style={styles.size}>작가</Text>
                        <Text style={styles.size}>발매일</Text>
                        <Text style={styles.size}>ISBN</Text>
                        <Text style={styles.size}>2023/03/04</Text>
                        <Text style={styles.status}>완료</Text>
                    </View>
                </View>
                {/* star rate */}
                <Text style={{fontSize: 20, fontWeight: 'bold', marginBottom:10}}>별점</Text>
                <View style={[styles.row, {justifyContent: 'center', marginBottom: 20}]}>
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
                <View>
                    <Text style={{fontSize: 20, fontWeight: 'bold'}}>리뷰</Text>
                    <Text style={[styles.size, styles.comment]}>
                    Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. 
                    It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.
                    </Text>
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
      alignItems: 'center',
    },
    row: {
        flexDirection: 'row',
        alignItems: 'center',
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
        fontSize: 15,
        marginTop: 5,
    },
    status: {
        backgroundColor: '#77BDC3',
        borderRadius: 20,
        padding: 5,
        textAlign: 'center',
        fontSize: 15,
        marginTop: 3,
        marginLeft: -5,
        width: 50,
    },
    comment: {
        borderWidth: 1,
        borderColor: '#E1D7C6',
        borderRadius: 7,
        paddingVertical: 15,
        paddingHorizontal: 20,
        marginTop: 10,
        height: 300,
        fontSize: 18
    },
    star: {
        color: '#FFD56D',
        marginRight: 8
    },
});

export default ReviewDetail;