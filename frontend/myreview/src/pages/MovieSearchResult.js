import { StatusBar } from 'expo-status-bar';
import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Alert, FlatList, Image } from 'react-native';
import { Entypo } from '@expo/vector-icons';
import URL from '../api/axios';

const MovieSearchResult=({navigation, route})=>{
    let query = route.params.query;
    let userId = route.params.user_id;
    const [search, setSearch] = useState(query);
    const [item, setItem] = useState([]);
    const [itemCnt, setItemCnt] = useState(0);
  
    // 검색 결과 조회
    const handleSearch = () => {
        if (search !== "") {
            console.log("query: " + search);
            URL.get(`/v1/content/movie/search?id=${userId}&q=${search}&start=1&display=15`)
                .then((res) => {
                    console.log(res.data);
                    setItem(res.data.items);
                    setItemCnt(res.data.total);
                })
                .catch((err) => {
                    console.log('search fail');
                    console.error(err);
                    console.log(err.response);
                })
        }
    }

    useEffect(()=>{
        handleSearch();
    }, [])

    const handleInput = (input) =>{
        if (input !== query) {  // 다른 검색어일 때
            if (input.length > 0) {
                handleSearch();
            } else {
                Alert.alert('제목을 입력해주세요!');
            }
        }
    } 

    React.useLayoutEffect(()=>{
        navigation.setOptions({
            title:'극장 검색',
            headerTintColor: '#E1D7C6',
        })
    })

    const itemView = ({item})=>{
        return (
            <View style={styles.content}>
                <Pressable 
                    style={{ marginRight: 25 }}
                    onPress={() => navigation.navigate('contentsDetail', 
                        {
                            userId: userId,
                            itemId: item.itemId,
                            category: 'movie',
                            title: item.title,
                            img: item.image,
                            releaseDate: item.releaseDate,
                            director: item.director,
                            actors: item.actors
                        })}>
                    <Image
                        style={styles.image}
                        source={item.image==""? {uri:'https://i.postimg.cc/wBncwMHT/stacked-waves-haikei.png'}:{uri: item.image}} />
                </Pressable>
                <View style={{width: '70%'}}>
                    <Text numberOfLines={1} ellipsizeMode="tail" style={{fontSize: 20, fontWeight: 'bold'}}>{(item.title).replace(/(<([^>]+)>)/ig,"")}</Text>
                    <Text style={{fontSize: 17, marginBottom: 8}}>{item.director}</Text>
                    <View style={{flexDirection:'row', marginLeft: -5}}>
                        <Pressable 
                            disabled={item.reviewId == null ? false : true}
                            style={[styles.contentBtn, item.reviewId==null?styles.ableBack:styles.disableBack, {marginRight: 7}]}
                            onPress={() => {
                                Like('movie', userId, item.itemId, item.title, item.image, item.releaseDate, "", item.director, item.actors);
                            }}>
                            <Text style={{color:'#fff'}}>담기</Text>
                        </Pressable>
                        <Pressable
                            disabled={item.reviewId == null ? false : true}
                            style={[item.reviewId==null?styles.ableBack:styles.disableBack, styles.contentBtn, { marginRight: 7 }]}
                            onPress={() => navigation.navigate('newReview', { category: 'movie' })}
                        >
                            <Text style={{color:'#fff'}}>리뷰 쓰기</Text>
                        </Pressable>
                    </View>
                </View>
            </View>
        );
    };

    return(
        <View style={styles.container}>
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
                onPress={()=>{handleInput(search)}}>
                    <Entypo name="magnifying-glass" size={38} color="#E1D7C6" />
                </Pressable>
            </View>
            {itemCnt > 0 &&
                <View style={{ marginTop: 20, marginLeft: 25, marginBottom: 120 }}>
                    <FlatList
                        data={item}
                        key='#'
                        keyExtractor={item => item.title}
                        renderItem={itemView}
                    />
                </View>}
            {itemCnt == 0 &&
                <View style={{alignSelf:'center', justifyContent:'center', height: '90%'}}>
                    <Text style={styles.emptyMsg}>검색 결과가 없습니다😢</Text>
                </View>}
            <StatusBar style='auto'/>
        </View>
    );

}

const styles = StyleSheet.create({
    container: {
      flexGrow: 1,
      backgroundColor: '#fff',
    },
    searchContainer: {
        flexDirection:'row',
        alignItems: 'center',
        justifyContent: 'center',
        marginTop: 20,
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
    content:{
        flexDirection: 'row',
        marginBottom: 20,
        alignItems: 'center',
        height: 100,
        width: '95%',
    },
    image: {
        width: 80,
        height: 100,
        borderRadius: 7,
    },
    contentBtn: {
        borderRadius: 20,
        paddingVertical: 3,
        paddingHorizontal: 10,
        textAlign:'center'
    },
    ableBack: {
        backgroundColor: '#77BDC3',
    },
    disableBack: {
        backgroundColor: 'gray',
    },
    emptyMsg: {
        fontSize: 20,
        fontWeight: 'bold',
        color: '#E1D7C6'
    },
});

export default MovieSearchResult;