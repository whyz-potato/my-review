import { StatusBar } from 'expo-status-bar';
import { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Image, FlatList } from 'react-native';
import { Entypo, MaterialIcons, AntDesign } from '@expo/vector-icons';
import URL from '../api/axios';
import { replaceTxt } from '../util/replaceTxt';

const Cinema = ({navigation}) => {
    const [search, setSearch] = useState(''); 
    const [userId, setUserId] = useState(0);
    const [review, setReview] = useState(0);

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

    // get movie
    useEffect(()=>{
        URL.get(`/v1/review/movie/search?id=${userId}`)
        .then((res)=>{
            console.log(res.data);
            setReview(res.data.reviews.item);
        })
        .catch((err)=>{
            console.log('get movie fail');
            console.error(err);
        })
    },[])
    
    const itemView = ({item})=>{
        return (
            <View style={{marginBottom: 5, marginRight: 10}}>
                <Pressable onPress={()=>navigation.navigate('reviewDetail', {category: 'movie'})}>
                    <Image
                        style={styles.image}
                        source={{ uri: item.image }}
                    />
                </Pressable>                
                <Text style={{fontSize: 15, textAlign:'center'}}>{replaceTxt(item.title)}</Text>
            </View>
        );
    };
    
    return(
        <View style={styles.container}>
            <View style={styles.header}>
                <View style={styles.headerBtn}>
                    <Pressable style={{marginRight: 20}}
                    onPress={()=>navigation.navigate('library')}>
                        <Text style={styles.unfocusTxt}>서재</Text>
                    </Pressable>
                    <Pressable onPress={()=>navigation.navigate('cinema')}>
                        <Text style={styles.focusTxt}>극장</Text>
                    </Pressable>
                </View>
                <Pressable style={{ justifyContent: 'center'}} onPress={()=>navigation.navigate('mypage', {user_id:userId})}>
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
                onPressIn={()=>{console.log(search)}}>
                    <Entypo name="magnifying-glass" size={38} color="#E1D7C6" />
                </Pressable>
            </View>
            <View style={styles.contentsArr}>
                <View>
                    <Text style={styles.contentsTitle}>작성한 리뷰</Text>
                    <View style={styles.contentBox}>
                        <FlatList
                            data={review}
                            key={'#'}
                            keyExtractor={item => item.reviewId}
                            renderItem={itemView}
                            numColumns={4}
                        />
                    </View>
                </View>
            </View>
            <Pressable
            style={styles.floatingBtn}
            onPress={()=>navigation.navigate('movieSearchResult')}>
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

    },
    contentsTitle: {
        fontSize: 23,
        fontWeight: 'bold',
    },
    contentBox:{
        flexDirection: 'row',
        marginTop: 15,
        marginBottom: 480,
    },
    floatingBtn: {
        position: 'absolute',
        right: 30,
        top: 655,   //25
    },    
    image: {
        width: 80,
        height: 110,
        borderRadius: 7,
    },
});

export default Cinema;