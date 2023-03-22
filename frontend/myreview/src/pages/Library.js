import { StatusBar } from 'expo-status-bar';
import { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Image, FlatList } from 'react-native';
import { Entypo, MaterialIcons, AntDesign } from '@expo/vector-icons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import URL from '../api/axios';

const Library = ({navigation}) => {
    const [search, setSearch] = useState(''); 
    const [userId, setUserId] = useState(0);
    const [review, setReview] = useState([]);
    const [reviewCnt, setReviewCnt] = useState(0);

    //refresh
    useEffect(()=>{
        const unsubscribe = navigation.addListener('focus', ()=>{
            console.log(userId+" "+search);
            if (userId !== 0) {
                URL.get(`/v1/review/book/search?id=${userId}&q=${search}&display=40`)
                    .then((res) => {
                        console.log(res.data);
                        setReview(res.data.reviews);
                        setReviewCnt(res.data.total);
                    })
                    .catch((err) => {
                        console.log('get book review fail');
                        console.error(err);
                    })
            }
        })
        return ()=>{unsubscribe};
    },[navigation])

    const getId = async () => {
        try {
            const val = await AsyncStorage.getItem('userId');
            if (val !== null) setUserId(val);
        } catch (error) {
            console.log("get id fail");
            console.log(error);
        }
    }

    useEffect(() => {
        getId();
    }, []);

    // get review
    useEffect(()=>{
        if (userId !== 0) {
            URL.get(`/v1/review/book/search?id=${userId}&display=40`)
                .then((res) => {
                    console.log(res.data);
                    setReview(res.data.reviews);
                    setReviewCnt(res.data.total);
                    setSearch('');
                })
                .catch((err) => {
                    console.log('get book review fail');
                    console.error(err);
                })
        }
    },[userId])

    const filter = () => {
        console.log(search);
        URL.get(`/v1/review/book/search?id=${userId}&q=${search}&display=12`)
            .then((res) => {
                console.log(res.data);
                setReview(res.data.reviews);
                setReviewCnt(res.data.total);
            })
            .catch((err) => {
                console.log('get movie fail');
                console.error(err);
            })
    }
    
    const itemView = ({item})=>{
        return (
            <View style={{marginBottom: 5, marginRight: 10, width: 80}}>
                <Pressable onPress={()=>navigation.navigate('reviewDetail', {category: 'book', user_id: userId, review_id: item.reviewId})}>
                    <Image
                        style={styles.image}
                        source={item.item.image==""? {uri:'https://i.postimg.cc/wBncwMHT/stacked-waves-haikei.png'}:{uri: item.item.image}}
                    />
                </Pressable>                
                <Text numberOfLines={1} ellipsizeMode='tail' style={{fontSize: 15, textAlign:'center'}}>{item.item.title}</Text>
            </View>
        );
    };
    
    return(
        <View style={styles.container}>
            <View style={styles.header}>
                <View style={styles.headerBtn}>
                    <Pressable style={{marginRight: 20}}
                    onPress={()=>navigation.navigate('library')}>
                        <Text style={styles.focusTxt}>서재</Text>
                    </Pressable>
                    <Pressable onPress={()=>navigation.navigate('cinema')}>
                        <Text style={styles.unfocusTxt}>극장</Text>
                    </Pressable>
                </View>
                <Pressable style={{ justifyContent: 'center'}} onPress={()=>navigation.navigate('mypage', {user_id: userId})}>
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
                onPressIn={()=>{filter()}}>
                    <Entypo name="magnifying-glass" size={38} color="#E1D7C6" />
                </Pressable>
            </View>
            {reviewCnt == 0 &&
                <View style={{ alignSelf: 'center', justifyContent: 'center', height: '75%' }}>
                    <Text style={styles.emptyMsg}>작성한 리뷰가 없습니다.{'\n'}새로운 리뷰를 작성해주세요😃</Text>
                </View>}
            {reviewCnt > 0 &&
                <View style={styles.contentsArr}>
                    <View>
                        <Text style={styles.contentsTitle}>작성한 리뷰</Text>
                        <View style={styles.contentBox}>
                            <FlatList
                                data={review}
                                key='#'
                                keyExtractor={item => item.reviewId}
                                renderItem={itemView}
                                numColumns={4}
                            />
                        </View>
                    </View>
                </View>}
            <Pressable
                style={styles.floatingBtn}
                onPress={() => navigation.navigate('bookSearchResult', {query: "", user_id: userId})}>
                <AntDesign name="pluscircle" size={60} color="#E1D7C6"/>
            </Pressable>
            <StatusBar style="auto" />
        </View>
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
        marginLeft:30,
        marginTop: 20,
        maxHeight: 600,
    },
    contentsTitle: {
        fontSize: 23,
        fontWeight: 'bold',
    },
    contentBox:{
        flexDirection: 'row',
        marginTop: 15,
        marginBottom: 50,
    },
    floatingBtn: {
        position: 'absolute',
        right: 30,
        // top: 655,
        bottom: 25
    },    
    image: {
        width: 80,
        height: 110,
        borderRadius: 7,
    },
    emptyMsg: {
        fontSize: 20,
        color: '#E1D7C6',
        textAlign: 'center',
    },
});

export default Library;