import { StatusBar } from 'expo-status-bar';
import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, ScrollView, FlatList, Image } from 'react-native';
import { Entypo, MaterialIcons } from '@expo/vector-icons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import URL from '../api/axios';

const ExploreLib = ({navigation}) => {
    const [search, setSearch] = useState('');    
    const [newContent, setNewContent] = useState([]);
    const [top, setTop] = useState([]);
    const [myContent, setMyContent] = useState([]);
    const [userId, setUserId] = useState(0);
    const [myContentCnt, setMyContentCnt] = useState(0);
    const [topCnt, setTopCnt] = useState(0);
    const [newContentCnt, setNewContentCnt] = useState(0);

    //refresh
    useEffect(()=>{
        const unsubscribe = navigation.addListener('focus', ()=>{
            console.log(userId);
            if (userId !== 0) {
                URL.get(`/v1/content/book/${userId}`)
                    .then((res) => {
                        console.log(res.data);
                        setMyContent(res.data.myContent.items);
                        setNewContent(res.data.newContent.items);
                        setTop(res.data.top10.items);
    
                        setMyContentCnt(res.data.myContent.count);
                        setNewContentCnt(res.data.newContent.count);
                        setTopCnt(res.data.top10.count);
                    })
                    .catch((err) => {
                        console.log('refresh fail');
                        console.error(err);
                    })
            }
        })
        return ()=>{unsubscribe};
    },[navigation])

    const getId = async () => {
        try {
            const val = await AsyncStorage.getItem('userId');
            if (val !== null) {
                setUserId(val);
            }
        } catch (error) {
            console.log("get id fail");
            console.log(error);
        }
    }

    useEffect(() => {
        getId();
    }, []);

    // 신작, 담은, 탑10
    useEffect(()=>{
        if (userId !== 0) {
            URL.get(`/v1/content/book/${userId}`)
                .then((res) => {
                    console.log(res.data);
                    setMyContent(res.data.myContent.items);
                    setNewContent(res.data.newContent.items);
                    setTop(res.data.top10.items);

                    setMyContentCnt(res.data.myContent.count);
                    setNewContentCnt(res.data.newContent.count);
                    setTopCnt(res.data.top10.count);
                })
                .catch((err) => {
                    console.log('get lib fail');
                    console.error(err);
                })
        }
    }, [userId])

    const itemView = ({item})=>{
        return (
            <View style={{marginBottom: 5, marginRight: 20, width: 80}}>
                <Pressable onPress={()=>navigation.navigate('contentsDetail', 
                    {
                        category: 'book',
                        item_id: item.itemId,
                        user_id: userId
                    })}>
                    <Image
                        style={styles.image}
                        source={item.image==""? {uri:'https://i.postimg.cc/wBncwMHT/stacked-waves-haikei.png'}:{uri: item.image}}
                    />
                </Pressable>             
                <Text numberOfLines={1} ellipsizeMode="tail" style={styles.itemTitle}>{(item.title).replace(/(<([^>]+)>)/ig,"")}</Text>
            </View>
        );
    };

    return(
        <ScrollView contentContainerStyle={styles.container}>
            <View style={styles.header}>
                <View style={styles.headerBtn}>
                    <Pressable style={{ marginRight: 20 }}
                        onPress={() => navigation.navigate('exploreLib')}>
                        <Text style={styles.focusTxt}>서재</Text>
                    </Pressable>
                    <Pressable onPress={() => navigation.navigate('exploreCinema')}>
                        <Text style={styles.unfocusTxt}>극장</Text>
                    </Pressable>
                </View>
                <Pressable style={{ justifyContent: 'center' }} onPress={() => navigation.navigate('mypage', {user_id: userId})}>
                    <MaterialIcons name="face" size={40} color="#E1D7C6" />
                </Pressable>
            </View>
            <View style={styles.searchContainer}>
                <TextInput 
                placeholder='검색어를 입력하세요'
                placeholderTextColor={'white'}
                style={styles.searchInput}
                value={search}
                onChangeText={setSearch}
                />
                <Pressable
                style={styles.searchBtn}
                onPress={()=>navigation.navigate('bookSearchResult', {query: search, user_id: userId})}>
                    <Entypo name="magnifying-glass" size={38} color="#E1D7C6" />
                </Pressable>
            </View>
            <View style={styles.contentsArr}>
                <View>
                    <Text style={styles.contentsTitle}>담은 도서</Text>
                    <View style={styles.contentsBox}>
                        {myContentCnt !== 0 &&
                            <FlatList
                                data={myContent}
                                key="#"
                                keyExtractor={item => item.itemId}
                                renderItem={itemView}
                                horizontal={true}
                            />}
                        {myContentCnt === 0 &&
                            <View>
                                <Text style={styles.emptyMsg}>담은 도서가 없습니다😶</Text>
                            </View>}
                    </View>
                </View>
                <View>
                    <Text style={styles.contentsTitle}>이달의 신작</Text>
                    <View style={styles.contentsBox}>
                        {newContentCnt !== 0 &&
                            <FlatList
                                data={newContent}
                                key="#"
                                keyExtractor={item => item.itemId}
                                renderItem={itemView}
                                horizontal={true}
                            />}
                        {newContentCnt === 0 &&
                            <View>
                                <Text style={styles.emptyMsg}>이달의 신작이 없습니다😶</Text>
                            </View>}
                    </View>
                </View>
                <View>
                    <Text style={styles.contentsTitle}>인기 도서</Text>
                    <View style={styles.contentsBox}>
                    {topCnt !== 0 &&
                            <FlatList
                                data={top}
                                key="#"
                                keyExtractor={item=>item.itemId}
                                renderItem={itemView}
                                horizontal={true}
                            />}
                        {topCnt === 0 &&
                            <View>
                                <Text style={styles.emptyMsg}>인기 도서가 없습니다😶</Text>
                            </View>}                        
                    </View>
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
        marginLeft:30,
        marginTop: 20,
        width: '85%',
    },
    contentsTitle: {
        fontSize: 23,
        // color: '#77BDC3',
        fontWeight: 'bold',
    },
    contentsBox: {
        height: 150,
        marginVertical: 15,
    },
    image: {
        width: 90,
        height: 120,
        borderRadius: 7,
    },
    itemTitle: {
        fontSize: 15, 
        textAlign:'center',
        marginTop: 8,
    },
    emptyMsg: {
        fontSize: 20,
        fontWeight: 'bold',
        color: '#E1D7C6',
        alignSelf: 'center',
        paddingVertical: 50
    },
});

export default ExploreLib;