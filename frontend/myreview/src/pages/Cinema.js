import { StatusBar } from 'expo-status-bar';
import { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Alert, ScrollView } from 'react-native';
import { Entypo, MaterialIcons } from '@expo/vector-icons';

const Cinema = ({navigation}) => {
    const [search, setSearch] = useState('');  
    
    return(
        <ScrollView contentContainerStyle={styles.container}>
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
                <Pressable style={{ justifyContent: 'center'}} onPress={()=>navigation.navigate('mypage')}>
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
                    <ScrollView horizontal={true} style={styles.contentsBox}>
                        <View>

                        </View>
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
        color: '#6DAFB5',
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
        // color: '#6DAFB5',
        fontWeight: 'bold',
    },
    contentsBox: {
        maxHeight:200
    }
});

export default Cinema;