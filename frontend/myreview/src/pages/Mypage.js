import { StatusBar } from 'expo-status-bar';
import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Alert, Modal } from 'react-native';
import { AntDesign } from '@expo/vector-icons';

const Mypage = ({navigation}) => {
    const [modal, setModal] = useState(false);
    const [goal, setGoal] = useState(10);

    const handleGoalChange = (goalChange) => {
        if (goalChange==NaN || goalChange==0) {
            setGoal(10);
        }else{
            setGoal(goalChange);
        }
    }

    React.useLayoutEffect(()=>{
        navigation.setOptions({
            title:'',
            headerTintColor: '#E1D7C6',
            headerRight:()=>(
                <Pressable
                onPress={()=>navigation.navigate('profileEdit')}>
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
                    {/* 올해 목표 수정 모달 */}
                    <Modal
                        animationType="fade"
                        transparent={true}
                        visible={modal}
                        onRequestClose={()=>{setModal(false)}}>
                        <View style={styles.centeredView}>
                            <View style={styles.modalView}>
                                <Pressable
                                    style={styles.closeBtn}
                                    onPress={() => setModal(!modal)}>
                                    <AntDesign name="close" size={20} color="#E1D7C6" />
                                </Pressable>
                                <View style={styles.modalInput}>
                                    <Text style={styles.modalText}>목표수정</Text>
                                    <TextInput
                                        value={goal}
                                        style={styles.input}
                                        onChangeText={handleGoalChange}
                                        keyboardType='number-pad'
                                    />
                                </View>
                            </View>
                        </View>
                    </Modal>
                    <Pressable
                        onPress={()=>setModal(true)}>
                        <Text style={{fontSize: 25}}>{goal}</Text>
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
        width: 200,
        backgroundColor: 'white',
        borderRadius: 20,
        paddingHorizontal: 15,
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
        marginTop: 10,
        justifyContent: 'space-around',
        alignItems: 'center'
      },
      modalText: {
        fontSize: 18,
        marginRight: 20,
      },
      input: {
        width: 90,
        height: 35,
        // alignItems: 'center',
        paddingHorizontal: 15,
        backgroundColor: '#E1D7C6',
        borderRadius: 20,
    },
    closeBtn: {
        alignSelf: 'flex-end'
    }
});

export default Mypage;