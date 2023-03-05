import { StatusBar } from 'expo-status-bar';
import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, Pressable, Alert, Image } from 'react-native';

const ContentsDetail = ({route, navigation}) => {
    const {category} = route.params;    // book? movie?

    React.useLayoutEffect(()=>{
        navigation.setOptions({
            title:'상세 정보',
            headerTintColor: '#E1D7C6',
            headerTitleStyle:{fontWeight:'bold'},
            headerRight:()=>(
                <View style={{flexDirection: 'row'}}>
                    <Pressable
                        onPress={() => { Alert.alert('담겼습니다') }}>
                        <Text style={{ color: '#E1D7C6', fontWeight: 'bold' }}>담기</Text>
                    </Pressable>
                    <Pressable
                        style={{ marginLeft: 13 }}
                        onPress={() => { navigation.navigate('newReview', {category: 'book'}) }}>
                        <Text style={{ color: '#E1D7C6', fontWeight: 'bold' }}>저장</Text>
                    </Pressable>
                </View>
            ),
        })
    })

    return(
        <View style={styles.container}>
            <View style={{marginHorizontal: 40, marginTop: 30}}>
                <View style={{flexDirection: 'row', marginBottom: 40}}>
                    <Image
                        style={styles.image}
                        source={{ uri: 'https://reactnative.dev/img/tiny_logo.png' }} />
                    <View style={{justifyContent:'flex-end'}}>
                        <Text style={styles.size}>제목</Text>
                        <Text style={styles.size}>작가</Text>
                        <Text style={styles.size}>발매일</Text>
                        <Text style={styles.size}>ISBN</Text>
                    </View>
                </View>
                <View>
                    <Text style={{fontSize: 20, fontWeight: 'bold'}}>줄거리</Text>
                    <Text style={[styles.size, styles.summary]}>
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
    summary: {
        // borderWidth: 1,
        // borderColor: '#E1D7C6',
        backgroundColor: '#E1D7C6',
        borderRadius: 7,
        paddingVertical: 15,
        paddingHorizontal: 20,
        marginTop: 10
    }
});

export default ContentsDetail;