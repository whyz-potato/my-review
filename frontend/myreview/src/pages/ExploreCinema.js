import { StatusBar } from 'expo-status-bar';
import { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, ScrollView, Keyboard } from 'react-native';
import { Entypo, MaterialIcons } from '@expo/vector-icons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import URL from '../api/axios';

const ExploreCinema = ({navigation}) => {
    const [search, setSearch] = useState('');     
    const [newContent, setNewContent] = useState([]);
    const [top, setTop] = useState([]);
    const [myContent, setMyContent] = useState([]);
    let userId=0;

    async() => {
        try {
            userId=await AsyncStorage.getItem('userId');
            if (userId!=null) userId = JSON.parse(userId);
        } catch (error) {
            console.log(error);
        }
    }    

    // 신작, 담은, 탑10
    /*
    useEffect(()=>{
        URL.get(`/content/movie/${userId}`)
        .then((res)=>{
            console.log(res.data);
            setMyContent(res.data.body.myContent);
            setNewContent(res.data.body.newContent);
            setTop(res.data.body.top10);
        })
        .catch((err)=>{
            console.log('get movie fail');
            console.log(err);
        })
    },[])
    */

    return(
        <ScrollView contentContainerStyle={styles.container}>
            <View style={styles.header}>
                <View style={styles.headerBtn}>
                    <Pressable style={{marginRight: 20}}
                    onPress={()=>navigation.navigate('exploreLib')}>
                        <Text style={styles.unfocusTxt}>서재</Text>
                    </Pressable>
                    <Pressable onPress={()=>navigation.navigate('exploreCinema')}>
                        <Text style={styles.focusTxt}>극장</Text>
                    </Pressable>
                </View>
                <Pressable style={{ justifyContent: 'center'}} onPress={()=>navigation.navigate('mypage', {user_id: userId})}>
                    <MaterialIcons name="face" size={40} color="#E1D7C6" />
                </Pressable>
            </View>
            <View style={styles.searchContainer}>
                <TextInput 
                placeholder='제목으로 검색하기'
                placeholderTextColor={'white'}
                style={styles.searchInput}
                value={search}
                onChangeText={setSearch}
                />
                <Pressable
                style={styles.searchBtn}
                onPressIn={()=>{console.log(search); navigation.navigate('movieSearchResult', {query: search, user_id: userId})}}>
                    <Entypo name="magnifying-glass" size={38} color="#E1D7C6" />
                </Pressable>
            </View>
            <View style={styles.contentsArr}>
                <View>
                    <Text style={styles.contentsTitle}>담은 영화</Text>
                    <ScrollView horizontal={true} style={styles.contentsBox}>
                        <View>

                        </View>
                    </ScrollView>
                </View>
                <View>
                    <Text style={styles.contentsTitle}>이달의 신작</Text>
                    <ScrollView horizontal={true} style={styles.contentsBox}>
                        
                    </ScrollView>
                </View>
                <View>
                    <Text style={styles.contentsTitle}>인기 영화</Text>
                    <ScrollView horizontal={true} style={styles.contentsBox}>
                        
                    </ScrollView>
                </View>
            </View>
            <StatusBar style="auto" />
        </ScrollView>
    );
}

const styles = StyleSheet.create({
    container: {
      flexGrow: 1,
      backgroundColor: '#fff',
      alignItems: 'center',
    },
    header: {
        flexDirection: 'row',
        alignSelf: 'flex-start',
        marginTop:50,
        marginHorizontal: 30,
        width: '85%',
        justifyContent: 'space-between'
    },
    headerBtn:{
        flexDirection: 'row',
        alignItems:'center'
    },
    focusTxt:{
        fontSize: 40,
        fontWeight: 'bold',
        color: '#77BDC3',
    },
    unfocusTxt:{
        fontSize:30,
        color: '#E1D7C6',
        fontWeight: 'bold',
    },
    searchContainer: {
        flexDirection:'row',
        alignItems: 'center',
        justifyContent: 'center',
        marginTop: 15,
    },
    searchInput: {
        backgroundColor:'#E1D7C6',
        borderRadius: 20,
        width: 300,
        height: 40,
        paddingHorizontal: 15,
        fontSize: 18
    },
    searchBtn: {
        marginLeft: 15,
    },
    contentsArr: {
        alignSelf: 'flex-start',
        marginHorizontal:30,
        marginTop: 20,

    },
    contentsTitle: {
        fontSize: 23,
        // color: '#77BDC3',
        fontWeight: 'bold',
    },
    contentsBox: {
        maxHeight:200
    }
});

export default ExploreCinema;