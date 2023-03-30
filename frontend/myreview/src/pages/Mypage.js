import { StatusBar } from 'expo-status-bar';
import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Modal, FlatList } from 'react-native';
import { AntDesign } from '@expo/vector-icons';
import URL from '../api/axios';

const Mypage = ({navigation, route}) => {
    let userId = route.params.user_id;
    const [modal, setModal] = useState(false);
    const [goal, setGoal] = useState(10);
    const [goalChange, setGoalChange] = useState(10);
    const [year, setYear] = useState(2023);
    const [cnt, setCnt] = useState(0);
    const [history, setHistory] = useState([]);

    const changeGoal = () => {
        console.log('goal to: '+goalChange);
        URL.put(
            `/v1/goal/${userId}`, {
            "target": goalChange,
        })
            .then((res) => {
                console.log(res.data);
                setGoal(goalChange);
                setModal(false);
            })
            .catch((err) => {
                console.log('change goal fail');
                console.error(err);
                console.log(err.response);
            })
        
    }

    const handleGoalChange = (input) => {
        if (input===NaN || input===0) {
            setGoalChange(10);
        }else{
            setGoalChange(input);
        }
    }

    React.useLayoutEffect(()=>{
        navigation.setOptions({
            title:'',
            headerTintColor: '#E1D7C6',
            headerRight:()=>(
                <Pressable
                onPress={()=>navigation.navigate('profileEdit', {user_id: userId})}>
                    <Text style={{color: '#E1D7C6', fontWeight:'bold', fontSize:16}}>프로필 편집</Text>
                </Pressable>
            ),
        })
    })

    // 올해 목표 가져오기
    useEffect(()=>{
        URL.get(`/v1/goal/${userId}`)
        .then((res)=>{
            console.log(res.data);
            setGoal(res.data.target);
            setYear(res.data.year);
            setCnt(res.data.cnt);
        })
        .catch((err)=>{
            console.log("get goal fail");
            console.error(err);
        })
    },[goal])
 
    // 기록 가져오기
    useEffect(()=> {
        URL.get(`/v1/goal/history/${userId}`)
        .then((res)=>{
            console.log(res.data);
            setHistory(res.data);
        })
    },[])

    const itemView = ({item})=>{
        return(
            <View style={styles.rowBetween}>
                <Text style={styles.txt}>{item.year}</Text>
                <Text style={styles.txt}>{item.cnt}/{item.target}</Text>
            </View>
        )
    }
    
    return(
        <View style={styles.container}>
            <View style={{marginHorizontal: 50}}>
                <Text style={styles.title}>내 기록</Text>
                <View style={styles.rowBetween}>
                    <Text style={{fontSize: 25}}>{year} 목표</Text>
                    {/* 올해 목표 수정 모달 */}
                    <Modal
                        animationType="fade"
                        transparent={true}
                        visible={modal}
                        onRequestClose={()=>{setModal(false)}}>
                        <View style={styles.centeredView}>
                            <View style={styles.modalView}>
                                <Pressable
                                    style={styles.endBtn}
                                    onPress={() => setModal(!modal)}>
                                    <AntDesign name="close" size={20} color="#E1D7C6" />
                                </Pressable>
                                <View style={styles.modalInput}>
                                    <Text style={styles.modalText}>새 목표</Text>
                                    <TextInput
                                        value={goal}
                                        style={styles.input}
                                        onChangeText={handleGoalChange}
                                        keyboardType='number-pad'
                                    />
                                </View>
                                <Pressable
                                    style={styles.endBtn}
                                    onPress={()=>changeGoal()}>
                                    <Text style={styles.editBtn}>수정</Text>
                                </Pressable>
                            </View>
                        </View>
                    </Modal>
                    <Pressable
                        onPress={()=>setModal(true)}>
                        <Text style={{fontSize: 25}}>{goal}</Text>
                    </Pressable>
                </View>
                <View style={styles.rowBetween}>
                    <Text style={styles.txt}>{year} 본 개수</Text>
                    <Text style={styles.txt}>{cnt}</Text>
                </View>
                <View style={{marginTop: 60}}>
                    <Text style={{ fontSize: 25 }}>과거 기록</Text>
                    <FlatList
                        data={history}
                        key='#'
                        keyExtractor={item => item.year}
                        renderItem={itemView}
                    />
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
        color: '#77BDC3',
    },
    rowBetween: {
        flexDirection:'row', 
        justifyContent:'space-between',
        alignItems:'center',
        marginTop: 20,
    },
    txt: {
        fontSize: 16,
    },
    centeredView: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
      },
    modalView: {
        width: 250,
        backgroundColor: 'white',
        borderRadius: 20,
        paddingHorizontal: 20,
        paddingBottom: 20,
        paddingTop: 10,
        shadowColor: '#000',
        shadowOffset: {
          width: 0,
          height: 2,
        },
        shadowOpacity: 0.25,
        shadowRadius: 4,
        elevation: 5,
      },
      modalInput: {
        flexDirection: 'row',
        marginVertical: 15,
        justifyContent: 'space-around',
        alignItems: 'center'
      },
      modalText: {
        fontSize: 18,
        marginRight: 30,
      },
      input: {
        width: 120,
        height: 35,
        // alignItems: 'center',
        paddingHorizontal: 15,
        backgroundColor: '#E1D7C6',
        borderRadius: 20,
    },
    endBtn: {
        alignSelf: 'flex-end'
    },
    editBtn: {
        color: '#E1D7C6',
        fontWeight: 'bold',
        fontSize: 15,
    },
});

export default Mypage;