import { StatusBar } from 'expo-status-bar';
import { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Image, FlatList } from 'react-native';
import { Entypo, MaterialIcons, AntDesign } from '@expo/vector-icons';
import AsyncStorage from '@react-native-async-storage/async-storage';

const Library = ({navigation}) => {
    const [search, setSearch] = useState(''); 
    const Data = [
        { id: '1', title: '므레모사', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '2', title: 'B', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '3', title: 'C', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '4', title: 'D', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '5', title: 'E', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '6', title: 'F', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '7', title: 'G', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '8', title: 'H', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '9', title: 'I', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '10', title: '므레모사', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '11', title: 'B', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '12', title: 'C', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '13', title: 'D', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '14', title: 'E', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '15', title: 'F', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '16', title: 'G', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '17', title: 'H', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '18', title: 'I', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '19', title: 'B', img: 'https://reactnative.dev/img/tiny_logo.png' },
        { id: '20', title: 'C', img: 'https://reactnative.dev/img/tiny_logo.png' },
      ];
      let userId=0;

    async() => {
        try {
            userId=await AsyncStorage.getItem('userId');
            if (userId!=null) userId = JSON.parse(userId);
        } catch (error) {
            console.log(error);
        }
    } 
    
    const itemView = ({item})=>{
        return (
            <View style={{marginBottom: 5, marginRight: 10}}>
                <Pressable onPress={()=>navigation.navigate('reviewDetail', {category: 'book'})}>
                    <Image
                        style={styles.image}
                        source={{ uri: item.img }}
                    />
                </Pressable>                
                <Text style={{fontSize: 15, textAlign:'center'}}>{item.title}</Text>
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
                            data={Data}
                            key={'#'}
                            keyExtractor={item => item.id}
                            renderItem={itemView}
                            numColumns={4}
                        />
                    </View>
                </View>
            </View>
            <Pressable
            style={styles.floatingBtn}
            onPress={()=>navigation.navigate('bookSearchResult')}>
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

export default Library;