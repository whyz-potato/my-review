import { StatusBar } from 'expo-status-bar';
import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Alert, FlatList, Image } from 'react-native';
import { Entypo } from '@expo/vector-icons';

const MovieSearchResult=({navigation, route})=>{
    let query = route.params.query;
    let userId = route.params.user_id;
    const [search, setSearch] = useState(query);  
    const Data = [
        { id: '1', title: '므레모사', author: '김초엽', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '2', title: 'B', author: 'Abc', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '3', title: 'C', author: 'Abc', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '4', title: 'D', author: 'Abc', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '5', title: 'E', author: 'Abc', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '6', title: 'F', author: 'Abc', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '7', title: 'G', author: 'Abc', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '8', title: 'H', author: 'Abc', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '9', title: 'I', author: 'Abc', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '10', title: 'J', author: 'Abc', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '11', title: 'k', author: 'Abc', img: 'https://reactnative.dev/img/tiny_logo.png' },
      ];

      // search
    // useEffect(()=>{
    //     URL.get(`/content/movie/search/${userId}/${search}`)
    //     .then((res)=>{
    //         console.log(res.data);
    //         setItem(res.data.body.items);
    //     })
    //     .catch((err)=>{
    //         console.log('search fail');
    //         console.log(err);
    //     })
    // }, [query])

    const handleInput = (input) =>{
        if (input!==""){
            query=input;
        }else{
            Alert.alert('제목을 입력해주세요!');
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
                <Pressable onPress={()=>navigation.navigate('contentsDetail', {category: 'movie'})}>
                    <Image
                        style={styles.image}
                        source={{ uri: item.img }} />
                </Pressable>
                <View>
                    <Text style={{fontSize: 20, fontWeight: 'bold'}}>{item.title}</Text>
                    <Text style={{fontSize: 17, marginBottom: 8}}>{item.author}</Text>
                    <View style={{flexDirection:'row', marginLeft: -5}}>
                        <Pressable style={{marginRight: 7}}
                        onPress={()=>Alert.alert('담겼습니다!')}>
                            <Text style={styles.contentBtn}>담기</Text>
                        </Pressable>
                        <Pressable
                        onPress={()=>navigation.navigate('newReview', {category: 'movie'})}
                        >
                            <Text style={styles.contentBtn}>리뷰 쓰기</Text>
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
            <View style={{marginTop: 20, marginLeft: 45, marginBottom: 100}}>
                <FlatList
                    data={Data}
                    keyExtractor={item => item.id}
                    renderItem={itemView}
                />
            </View>
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
    content:{
        flexDirection: 'row',
        marginBottom: 20,
        alignItems: 'center',
        height: 100,
    },
    image: {
        width: 80,
        height: 100,
        borderRadius: 7,
        marginRight: 30,
    },
    contentBtn: {
        backgroundColor: '#77BDC3',
        borderRadius: 20,
        color: '#ffffff',
        paddingVertical: 3,
        paddingHorizontal: 10,
    },
});

export default MovieSearchResult;