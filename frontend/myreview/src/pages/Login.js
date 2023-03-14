import { StatusBar } from 'expo-status-bar';
import { useEffect, useState } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Alert } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import URL from '../api/axios';

const Login = ({ navigation }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = () => {
    console.log('login ' + email + " " + password);

    if (email === "" || password === "") {
      Alert.alert('빈 칸을 입력해주세요.');
    } else {
      URL.post(
        "/login", {
        "email": email,
        "password": password,
      }
      )
        .then(async (res) => {
          console.log(res.data);

          try {
            await AsyncStorage.multiSet([
              ['accessToken', res.data.body.accessToken],
              ['refreshToken', res.data.body.accessToken],
              ['userId', JSON.stringify(res.data.body.userId)]]);
          } catch (error) {
            console.log(error);
          }
          navigation.navigate('auth');
        })
        .catch((err) => {
          console.log('login error');
          console.log(err);
          Alert.alert('이메일 또는 비밀번호를 확인하세요.');
        })
    }
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>로그인</Text>
      <View style={{ marginTop: 20 }}>
        <View>
          <Text style={styles.inputArea}>이메일</Text>
          <TextInput
            value={email}
            onChangeText={setEmail}
            style={styles.input}
          />
        </View>
        <View>
          <Text style={styles.inputArea}>비밀번호</Text>
          <TextInput
            value={password}
            onChangeText={setPassword}
            style={styles.input}
            secureTextEntry={true}
          />
        </View>
        <Pressable
          style={styles.signupBtn}
          onPress={() => navigation.navigate('signup')}>
          <Text style={{ color: '#B4AA99' }}>회원가입</Text>
        </Pressable>
        <Pressable
          onPress={() => { handleSubmit() }}
          style={styles.loginBtn}>
          <Text style={styles.loginTxt}>로그인</Text>
        </Pressable>
      </View>
      <View style={{ marginTop: 70 }}>
        <Text style={{ color: '#B4AA99' }}>또는</Text>
      </View>
      <Pressable
        onPress={() => Alert.alert('to kakao login')}
        style={{ marginTop: 20 }}>
        <Text style={styles.kakaoTxt}>카카오로 로그인</Text>
      </Pressable>
      <StatusBar style="auto" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
  title: {
    color: '#77BDC3',
    fontSize: 40,
    fontWeight: 'bold',
    marginBottom: 5,
  },
  inputArea: {
    fontSize: 18,
    marginTop: 15,
    color: '#E1D7C6',
  },
  input: {
    width: 300,
    height: 50,
    padding: 10,
    marginBottom: 10,
    borderWidth: 1,
    borderBottomColor: '#E1D7C6',
    borderTopColor: '#ffffff',
    borderLeftColor: '#ffffff',
    borderRightColor: '#ffffff',
  },
  signupBtn: {
    alignItems: 'flex-end',
    marginTop: 10,
  },
  loginBtn: {
    marginTop: 20,
  },
  loginTxt: {
    color: '#ffffff',
    backgroundColor: '#77BDC3',
    borderRadius: 25,
    paddingVertical: 10,
    paddingHorizontal: 15,
    fontSize: 20,
    textAlign: 'center'
  },
  kakaoTxt: {
    color: '#ffffff',
    backgroundColor: '#F3D500',
    borderRadius: 25,
    paddingVertical: 10,
    paddingHorizontal: 15,
    fontSize: 20,
    textAlign: 'center',
    width: 300
  }
});

export default Login;