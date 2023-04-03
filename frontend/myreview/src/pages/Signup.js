import { StatusBar } from 'expo-status-bar';
import { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Alert, ScrollView } from 'react-native';
import Checkbox from 'expo-checkbox';
import { emailValidation, pwdValidation } from '../util/Validation';
import URL from '../api/axios';
import axios from 'axios';

const Signup = ({navigation}) => {
    const [email, setEmail] = useState('');
    const [emailError, setEmailError] = useState('');
    const [validEmail, setValidEmail] = useState(true);
    const [checkedEmail, setCheckedEmail] = useState(false);
    const [name, setName] = useState('');
    const [password, setPwd] = useState('');
    const [pwdError, setPwdError] = useState('');
    const [validPwd, setValidPwd] = useState(false);
    const [passwordCheck, setPwdCheck] = useState('');
    const [pwdCheckError, setPwdCheckError] = useState('');
    const [validPwdCheck, setValidPwdCheck] = useState(false);
    const [isSelected, setSelect] = useState(false);

    const submit = () => {
        if (!checkedEmail) {
            Alert.alert('이메일 중복 체크 먼저 해주세요!');
        }else if(!validPwd || !validPwdCheck) {
            Alert.alert('비밀번호를 확인해주세요.');
        }else if (email==="" || name==="" || password ==="" || passwordCheck==="") {
            Alert.alert('빈 칸을 입력해주세요.');
        }else {
            console.log('signup '+ email+" "+name+" "+password);
             URL.post(
                 "/v1/signup", {
                     email: email,
                     password: password,
                     name: name,
                 },
                 {withCredentials: true}
             )
             .then((res)=>{
                 console.log(res.data);
                 Alert.alert('회원가입 완료','반가워요!', [{
                     text: 'ok',
                     onPress: () => { navigation.navigate('login') }
                 }])
             })
             .catch((err)=>{
                 console.log('signup fail');
                 console.log(err);
             })

//            fetch("http://192.168.0.13:8080/v1/signup", {
//                method: 'POST',
//                headers: {
//                    "Access-Control-Allow-Origin": "*",
//                    "Access-Control-Allow-Method": "*",
//                    "Access-Control-Allow-Headers": "Content-Type, Accept",
//                    'Content-Type': 'application/json',
//                    Accept: 'application/json',
//                },
//                body: JSON.stringify({
//                    email: email,
//                    password: password,
//                    name: name,
//                })
//            })
//            .then(res=>{
//                console.log(res);
//                Alert.alert('회원가입 완료','반가워요!', [{
//                    text: 'ok',
//                    onPress: () => { navigation.navigate('login') }
//                }])
//            })
//            .catch(err=>{
//                console.log('signup fail');
//                console.error(err);
//            })
        }
    }

    // email 중복 확인
    const emailCheck = () => {
        console.log('email check '+email);

        if (!validEmail) Alert.alert('유효하지 않은 이메일 형식입니다.');
        else {
            URL.post(
                "/v1/emChk", {
                "email": email
            })            
                .then((res) => {
                    console.log(res.data);
                    if (res.data === 0) {
                        Alert.alert('사용 가능한 이메일입니다.');
                        setCheckedEmail(true);
                    }else {
                        Alert.alert('이미 존재하는 이메일입니다.');
                        setCheckedEmail(false);
                    }
                })
                .catch((err) => {
                    console.log(err.response);
                    setCheckedEmail(false);
                })
        }
    }

    const handleEmailChange = (onchangeEmail) => {
        setEmail(onchangeEmail);
        if (emailValidation(email)) {
            setEmailError("");
            setValidEmail(true);
        }else {
            setEmailError("유효하지 않은 이메일 형식입니다.");
            setValidEmail(false);
        }
    };

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
        if(passwordCheck==''){
            setPwdCheckError("");
            setValidPwdCheck(true);
        }else if (password===passwordCheck) {
            setPwdCheckError("");
            setValidPwdCheck(true);
        }else {
            setPwdCheckError("비밀번호가 동일하지 않습니다.");
            setValidPwdCheck(false);
        }
    }, [passwordCheck])


    return (
        <ScrollView contentContainerStyle={styles.container}>
            <Text style={styles.title}>회원가입</Text>

            <View style={{ marginTop: 30 , marginHorizontal: 35}}>
                <View style={styles.row}>
                    <Text style={styles.inputArea}>이메일</Text>
                    <TextInput
                        value={email}
                        style={styles.input}
                        onChangeText={handleEmailChange}
                    />
                </View>
                {!validEmail && <Text style={styles.errorMsg}>{emailError}</Text>}
                <Pressable style={styles.emailBtn}
                            onPress={()=>{emailCheck()}}>
                    <Text style={styles.emailTxt}>중복 확인</Text>
                </Pressable>
                <View style={styles.row}>
                    <Text style={styles.inputArea}>이름</Text>
                    <TextInput
                        value={name}
                        style={styles.input}
                        onChangeText={setName}
                    />
                </View>
                <View style={styles.row}>
                    <Text style={styles.inputArea}>비밀번호</Text>
                    <TextInput
                        value={password}
                        style={styles.input}
                        onChangeText={setPwd}
                        secureTextEntry={true}
                    />
                </View>
                {!validPwd && <Text style={styles.errorMsg}>{pwdError}</Text>}
                <View style={styles.row}>
                    <Text style={styles.inputArea}>비밀번호 확인</Text>
                    <TextInput
                        value={passwordCheck}
                        style={styles.input}
                        onChangeText={setPwdCheck}
                        secureTextEntry={true}
                    />
                </View>
                {!validPwdCheck && <Text style={styles.errorMsg}>{pwdCheckError}</Text>}
                <View style={{marginTop: 10}}>
                    <Text style={styles.inputArea}>개인정보 활용 동의서</Text>
                    <Text style={styles.agreement}>{'\n'}개인정보 수집 및 이용 동의서{'\n'}{'\n'}
                        <Text>
                            - 수집하는 개인정보 항목: 성명, 이메일{'\n'}
                            - 개인정보의 이용 목적: 회원 식별 및 서비스 이용{'\n'}
                            - 개인정보의 보유 및 이용 기간: 회원 탈퇴 시 파기{'\n'}
                        </Text>
                    </Text>
                    <View style={styles.checkboxContainer}>
                        <Checkbox
                            value={isSelected}
                            onValueChange={setSelect}
                            style={styles.checkbox}
                        />
                        <Text style={{marginLeft: 5}}>동의합니다</Text>
                    </View>
                </View>
                <Pressable style={styles.signupBtn}
                            onPress={()=>{isSelected ? submit():Alert.alert('개인정보 활용 동의를 체크해주세요.')}}>
                    <Text style={styles.signupTxt}>회원 가입</Text>
                </Pressable>
            </View>
            <StatusBar style="auto" />
        </ScrollView>
    );
}

const styles = StyleSheet.create({
    container: {
        flexGrow: 1,
        backgroundColor: '#fff',
        justifyContent: 'center',
    },
    title: {
        marginTop: 30,
        color: '#77BDC3',
        fontSize: 35,
        fontWeight: 'bold',
        alignSelf: 'center'
    },
    row: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        marginTop: 10,
    },
    inputArea: {
        fontSize: 18,
        color: '#E1D7C6',
    },
    input: {
        width: 200,
        height: 45,
        padding: 10,
        marginBottom: 10,
        marginLeft: 15,
        borderWidth: 1,
        borderColor: '#E1D7C6',
        borderRadius: 7,
    },
    errorMsg: {
        alignSelf: 'flex-end',
        color: 'red',
        marginBottom: 7,
    },
    emailBtn:{
        alignItems: 'flex-end',
        marginBottom: 5,
    },
    emailTxt: {
        backgroundColor: '#77BDC3',
        borderRadius: 20,
        color: '#ffffff',
        paddingVertical: 5,
        paddingHorizontal: 10,
        fontSize: 15,
    },
    agreement:{
        borderWidth: 1,
        borderRadius: 7,
        borderColor: '#E1D7C6',
        marginVertical: 10,
        textAlign: 'center',
        fontSize: 15
    },
    checkboxContainer:{
        flexDirection: 'row', 
        justifyContent: 'flex-end',
    },
    checkbox:{

    },
    signupBtn: {
        // alignItems: 'center',
        marginTop: 30
    },
    signupTxt: {
        backgroundColor: '#77BDC3',
        borderRadius: 25,
        color: '#ffffff',
        paddingVertical: 10,
        paddingHorizontal: 20,
        fontSize: 20,
        textAlign: 'center'
    },
});

export default Signup;