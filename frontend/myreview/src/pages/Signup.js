import { StatusBar } from 'expo-status-bar';
import { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Alert, ScrollView } from 'react-native';
import Checkbox from 'expo-checkbox';
import { emailValidation, pwdValidation } from '../util/Validation';

const Signup = () => {
    const [email, setEmail] = useState('');
    const [emailError, setEmailError] = useState('');
    const [validEmail, setValidEmail] = useState(true);
    const [name, setName] = useState('');
    const [password, setPwd] = useState('');
    const [pwdError, setPwdError] = useState('');
    const [validPwd, setValidPwd] = useState(false);
    const [passwordCheck, setPwdCheck] = useState('');
    const [pwdCheckError, setPwdCheckError] = useState('');
    const [validPwdCheck, setValidPwdCheck] = useState(false);
    const [isSelected, setSelect] = useState(false);

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
        if (pwdValidation(password)) {
            setPwdError("");
            setValidPwd(true);
        } else {
            setPwdError("영문 소문자 또는 대문자, 숫자, 특수문자를 포함해 8-15자 내외로 설정해주세요.");
            setValidPwd(false);
        }
    }, [password])

    useEffect(()=> {
        if (password===passwordCheck) {
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

            <View style={{ marginTop: 30}}>
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
                            onPress={()=>{Alert.alert('사용 가능')}}>
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
                            onPress={()=>{isSelected ? Alert.alert('success'):Alert.alert('agree first')}}>
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
        alignItems: 'center',
        justifyContent: 'center',
    },
    title: {
        marginTop: 40,
        color: '#6DAFB5',
        fontSize: 35,
        fontWeight: 'bold'
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
        width: 250,
        height: 50,
        padding: 10,
        marginBottom: 10,
        marginLeft: 15,
        borderWidth: 1,
        borderColor: '#E1D7C6',
        borderRadius: 7,
    },
    errorMsg: {
        justifyContent: 'flex-end',
        color: 'red',
    },
    emailBtn:{
        alignItems: 'flex-end',
        marginBottom: 5,
    },
    emailTxt: {
        backgroundColor: '#6DAFB5',
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
        backgroundColor: '#6DAFB5',
        borderRadius: 25,
        color: '#ffffff',
        paddingVertical: 10,
        paddingHorizontal: 20,
        fontSize: 20,
        textAlign: 'center'
    },
});

export default Signup;