import { StatusBar } from 'expo-status-bar';
import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Alert } from 'react-native';
import { pwdValidation } from '../util/Validation';

const ProfileEdit = ({navigation}) => {
    const email='myreview@naver.com';
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [pwdCheck, setPwdCheck] = useState('');
    const [pwdError, setPwdError] = useState('');
    const [validPwd, setValidPwd] = useState(true);
    const [pwdCheckError, setPwdCheckError] = useState('');
    const [validPwdCheck, setValidPwdCheck] = useState(false);

    React.useLayoutEffect(()=>{
        navigation.setOptions({
            title:'',
            headerTintColor: '#E1D7C6',
        })
    })

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

    return (
        <View style={styles.container}>
            <View style={{marginHorizontal: 40, marginVertical: 30}}>
                <Text style={styles.title}>프로필 편집</Text>
                <View style={{marginLeft: 15, marginTop: 30}}>
                    <View style={styles.rowBetween}>
                        <Text style={styles.inputArea}>이메일</Text>
                        <Text style={{fontSize:15}}>{email}</Text>
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

                    <View style={{flexDirection: 'row', alignSelf: 'center', marginTop: 70}}>
                        <Pressable
                        style={{marginRight: 25}}
                        onPressIn={()=>{Alert.alert('로그아웃', '정말 로그아웃 하시겠습니까?', [
                            {
                                text: '취소',
                                onPress: () => console.log('cancel'),
                            },
                            {
                                text: '네',
                                onPress: () => { navigation.navigate('login') }
                            }
                        ]);
                        }}
                        >
                            <Text style={styles.memberBtn}>로그아웃</Text>
                        </Pressable>
                        <Pressable
                        onPressIn={()=>{Alert.alert('회원 탈퇴', '정말 탈퇴 하시겠습니까?', [
                            {
                                text: '취소',
                                onPress: () => console.log('cancel'),
                            },
                            {
                                text: '네',
                                onPress: () => { navigation.navigate('login') }
                            }
                        ]);
                        }}
                        >
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
})

export default ProfileEdit;