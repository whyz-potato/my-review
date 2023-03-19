import { StatusBar } from 'expo-status-bar';
import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, Pressable, Alert, Image, ScrollView } from 'react-native';
import { replaceTxt } from '../util/replaceTxt';

const ContentsDetail = ({route, navigation}) => {
    const {category} = route.params.category;    // book? movie?
    const title = route.params.title;
    const img = route.params.img;
    const releaseDate = route.params.releaseDate;
    const description = category == 'book' ? route.params.description : "";
    const author = category == 'book' ? route.params.author : route.params.director;
    const extraInfo = category == 'book' ? route.params.isbn : route.params.actors;

    console.log(route.params);

    React.useLayoutEffect(()=>{
        navigation.setOptions({
            title:'상세 정보',
            headerTintColor: '#E1D7C6',
            headerTitleStyle:{fontWeight:'bold'},
            headerRight:()=>(
                <View style={{flexDirection: 'row'}}>
                    <Pressable
                        onPress={() => { Alert.alert('담겼습니다') }}>
                        <Text style={styles.headerBtn}>담기</Text>
                    </Pressable>
                    <Pressable
                        style={{ marginLeft: 13 }}
                        onPress={() => { navigation.navigate('newReview', {category: category}) }}>
                        <Text style={styles.headerBtn}>저장</Text>
                    </Pressable>
                </View>
            ),
        })
    })

    return(
        <View style={styles.container}>
            <Text style={styles.title}>{replaceTxt(title)}</Text>
            <View style={{marginHorizontal: 40, marginTop: 20}}>
                <View style={{flexDirection: 'row', marginBottom: 40}}>
                    <Image
                        style={styles.image}
                        source={{ uri: img }} />
                    <View style={{justifyContent:'flex-end', width: '55%'}}>
                        <Text style={styles.size}>{author}</Text>
                        <Text style={styles.size}>{releaseDate}</Text>
                        <Text style={styles.size}>{extraInfo}</Text>
                    </View>
                </View>
                <View style={{maxHeight: 420}}>
                    <Text style={{fontSize: 20, fontWeight: 'bold', marginBottom:5}}>줄거리</Text>
                    <ScrollView contentContainerStyle={{flexGrow:1}}>
                        <Text style={[styles.size, styles.summary]}>{replaceTxt(description)}</Text>
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
    },
    title: {
        fontSize: 20,
        fontWeight: 'bold',
        marginTop: 25,
        alignSelf: 'center',
        color: '#4E4637',
        marginHorizontal: 20,
    }
});

export default ContentsDetail;