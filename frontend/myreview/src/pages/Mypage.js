import { StatusBar } from 'expo-status-bar';
import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Alert, ScrollView } from 'react-native';

const Mypage = ({navigation}) => {

    React.useLayoutEffect(()=>{
        navigation.setOptions({
            title:'',
            headerTintColor: '#E1D7C6',
            headerRight:()=>(
                <Pressable>
                    <Text style={{color: '#E1D7C6', fontWeight:'bold'}}>프로필 편집</Text>
                </Pressable>
            ),
        })
    })
    
    return(
        <View style={styles.container}>
            <View style={{marginHorizontal: 50}}>
                <Text style={styles.title}>내 기록</Text>
                <View style={styles.rowBetween}>
                    <Text style={{fontSize: 25}}>2023 목표</Text>
                    <Pressable>
                        <Text style={{fontSize: 25}}>10</Text>
                    </Pressable>
                </View>
                <View style={styles.rowBetween}>
                    <Text style={styles.txt}>2023 본 개수</Text>
                    <Text style={styles.txt}>5</Text>
                </View>
                <View style={{marginTop: 60}}>
                    <Text style={{ fontSize: 25 }}>과거 기록</Text>
                    <View style={styles.rowBetween}>
                        <Text style={styles.txt}>2022</Text>
                        <Text style={styles.txt}>5/8</Text>
                    </View>
                    <View style={styles.rowBetween}>
                        <Text style={styles.txt}>2021</Text>
                        <Text style={styles.txt}>5/8</Text>
                    </View>
                </View>
            </View>
            <StatusBar style="auto" />
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: '#fff',
      justifyContent: 'space-between',
    },
    title: {
        fontSize: 38,
        fontWeight: 'bold',
        marginTop: 30,
        marginBottom: 15,
        color: '#6DAFB5',
    },
    rowBetween: {
        flexDirection:'row', 
        justifyContent:'space-between',
        marginTop: 20,
    },
    txt: {
        fontSize: 16,
    }
});

export default Mypage;