import { StatusBar } from 'expo-status-bar';
import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Alert, Modal } from 'react-native';
import { pwdValidation } from '../util/Validation';
import { logout, resign } from '../util/User';
import URL from '../api/axios';

const ProfileEdit = ({navigation, route}) => {
    let userId = route.params.user_id;
    const userEmail='myreview@naver.com';
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [pwdCheck, setPwdCheck] = useState('');
    const [pwdError, setPwdError] = useState('');
    const [validPwd, setValidPwd] = useState(true);
    const [pwdCheckError, setPwdCheckError] = useState('');
    const [validPwdCheck, setValidPwdCheck] = useState(false);
    const [modal, setModal] = useState(false);
    const [email, setEmail] = useState('');

    useEffect(()=> {
        if(password==''){
            setPwdError("");
            setValidPwd(true);
        }else if (pwdValidation(password)) {
            setPwdError("");
            setValidPwd(true);
        } else {
            setPwdError("영문 소문자 또는 대문자, 숫자, 특수문자를 포함해 8-15자 내외로 설정해주세요.");
            setValidPwd(false);
        }
    }, [password])

    useEffect(()=> {
        if(pwdCheck==''){
            setPwdCheckError("");
            setValidPwdCheck(true);
        }else if (password===pwdCheck) {
            setPwdCheckError("");
            setValidPwdCheck(true);
        }else {
            setPwdCheckError("비밀번호가 동일하지 않습니다.");
            setValidPwdCheck(false);
        }
    }, [pwdCheck])

    // const changeProfile = () =>{
    //     if (name !== "" && password !== "") {
    //         if (validPwd && validPwdCheck) {
    //             URL.put(
    //                 `/users/${userId}`, {
    //                 "name": name,
    //                 "password": password
    //             })
    //                 .then((res) => {
    //                     console.log('edit success');
    //                     console.log(res);
    //                 })
    //                 .catch((err) => {
    //                     console.log('fail');
    //                     console.log(err);
    //                 })
    //         }else {
    //             Alert.alert('비밀번호를 확인해주세요.');
    //         }
    //     }else if (name ==="" || password!==""){
    //         if (validPwd && validPwdCheck) {
    //             URL.put(
    //                 `/users/${userId}`, {
    //                 "password": password,
    //             })
    //                 .then((res) => {
    //                     console.log('pwd edit success');
    //                     console.log(res);
    //                 })
    //                 .catch((err) => {
    //                     console.log('fail');
    //                     console.log(err);
    //                 })
    //         } else {
    //             Alert.alert('비밀번호를 확인해주세요.');
    //         }
    //     }else if (name !=="" || password===""){
    //         URL.put(
    //             `/users/${userId}`, {
    //             "name": name,
    //         })
    //             .then((res) => {
    //                 console.log('name edit success');
    //                 console.log(res);
    //             })
    //             .catch((err) => {
    //                 console.log('fail');
    //                 console.log(err);
    //             })
    //     }else {
    //         Alert.alert('이름 또는 비밀번호를 입력해주세요.')
    //     }
    // }

    React.useLayoutEffect(()=>{
        navigation.setOptions({
            title:'',
            headerTintColor: '#E1D7C6',
        })
    })

    return (
        <View style={styles.container}>
            <View style={{marginHorizontal: 40, marginVertical: 30}}>
                <Text style={styles.title}>프로필 편집</Text>
                <View style={{marginLeft: 10, marginTop: 30}}>
                    <View style={styles.rowBetween}>
                        <Text style={styles.inputArea}>이메일</Text>
                        <Text style={{fontSize:15}}>{userEmail}</Text>
                    </View>
                    <View style={styles.rowBetween}>
                        <Text style={styles.inputArea}>이름</Text>
                        <TextInput
                        style={styles.input}
                        value={name}
                        onChangeText={setName}
                        />
                    </View>
                    <View style={styles.rowBetween}>
                        <Text style={styles.inputArea}>비밀번호</Text>
                        <TextInput
                        style={styles.input}
                        value={password}
                        onChangeText={setPassword}
                        secureTextEntry={true}
                        />
                    </View>
                    {!validPwd && <Text style={styles.errorMsg}>{pwdError}</Text>}

                    <View style={styles.rowBetween}>
                        <Text style={styles.inputArea}>비밀번호 확인</Text>
                        <TextInput
                        style={styles.input}
                        value={pwdCheck}
                        onChangeText={setPwdCheck}
                        secureTextEntry={true}
                        />
                    </View>
                    {!validPwdCheck && <Text style={styles.errorMsg}>{pwdCheckError}</Text>}

                    <View style={{alignSelf:'flex-end'}}>
                        <Pressable
                            onPress={() => {changeProfile}}>
                            <Text style={styles.editBtn}>변경</Text>
                        </Pressable>
                    </View>

                    <View style={{flexDirection: 'row', alignSelf: 'center', marginTop: 100}}>
                        <Pressable
                        style={{marginRight: 20}}
                        onPressIn={()=>{Alert.alert('로그아웃', '정말 로그아웃 하시겠습니까?', [
                            {
                                text: '취소',
                                onPress: () => console.log('cancel'),
                            },
                            {
                                text: '네',
                                onPress: () => { logout({navigation}) }
                            }
                        ]);
                        }}
                        >
                            <Text style={styles.memberBtn}>로그아웃</Text>
                        </Pressable>

                        <Modal
                            animationType="fade"
                            transparent={true}
                            visible={modal}
                            onRequestClose={() => { setModal(false) }}>
                            <View style={styles.centeredView}>
                                <View style={styles.modalView}>
                                    <View style={styles.modalInputBox}>
                                        <Text style={{fontSize:22, marginBottom:20, fontWeight:'bold'}}>회원 탈퇴</Text>
                                        <Text style={styles.modalText}>이메일을 입력해주세요.</Text>
                                        <TextInput
                                            value={email}
                                            style={styles.modalInput}
                                            onChangeText={setEmail}
                                        />
                                    </View>
                                    <View style={styles.check}>
                                        <Text style={{color:'#872200', marginBottom: 8}}>정말 탈퇴하시겠습니까?</Text>
                                        <View style={{flexDirection: 'row', justifyContent:'flex-end'}}>
                                            <Pressable
                                                style={{marginRight: 20}}
                                                onPress={() => { }}>
                                                <Text style={{ fontSize: 18, color:'#872200', fontWeight:'bold' }}>네</Text>
                                            </Pressable>
                                            <Pressable
                                            style={{}}
                                            onPress={() => {setModal(false)}}>
                                            <Text style={{fontSize:18, fontWeight:'bold'}}>아니오</Text>
                                        </Pressable>
                                        </View>
                                    </View>
                                </View>
                            </View>
                        </Modal>
                        <Pressable
                        onPressIn={()=>{setModal(true)}}>
                            <Text style={[styles.memberBtn, {backgroundColor: '#872200'}]}>회원탈퇴</Text>
                        </Pressable>
                    </View>
                </View>
            </View>
            <StatusBar style="auto" />
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: '#fff',
    },
    title: {
        fontSize: 38,
        fontWeight: 'bold',
        marginBottom: 15,
        color: '#77BDC3',
    },
    rowBetween: {
        flexDirection:'row', 
        justifyContent:'space-between',
        alignItems:'center',
        marginBottom: 25,
    },
    inputArea: {
        fontSize: 18,
        fontWeight: '750'
    },
    input: {
        borderWidth: 1,
        borderColor: '#E1D7C6',
        width: 200,
        height: 40,
        borderRadius: 7,
        padding: 10,
        fontSize: 15,
    },
    errorMsg: {
        justifyContent: 'flex-end',
        color: 'red',
    },
    memberBtn: {
        backgroundColor: '#77BDC3',
        borderRadius: 25,
        color: '#ffffff',
        paddingVertical: 8,
        paddingHorizontal: 15,
        fontSize: 20,
        textAlign: 'center'
    },
    centeredView: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
      },
    modalView: {
        width: 330,
        backgroundColor: 'white',
        borderRadius: 20,
        paddingHorizontal: 25,
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
      modalInputBox: {
        marginTop: 15,
      },
      modalText: {
        fontSize: 16,
        marginBottom: 10,
      },
      modalInput: {
        width: 280,
        height: 45,
        paddingHorizontal: 15,
        backgroundColor: '#E1D7C6',
        borderRadius: 20,
    },
    check: {
        marginTop: 30,
        alignSelf: 'flex-end'
    },
    editBtn: {
        backgroundColor: '#77BDC3',
        borderRadius: 25,
        color: '#ffffff',
        paddingVertical: 6,
        fontSize: 19,
        textAlign: 'center',
        width: 65,
        marginTop: -10
    }
})

export default ProfileEdit;