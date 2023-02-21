import { StatusBar } from 'expo-status-bar';
import { useState } from 'react';
import { StyleSheet, Text, View, TextInput, Pressable, Alert } from 'react-native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

const Login = ({ navigation }) => {
  const [email, password] = useState('');
  return (
    <View style={styles.container}>
      <Text style={styles.title}>로그인</Text>
      <View style={{ marginTop: 20 }}>
        <View>
          <Text style={styles.inputArea}>이메일</Text>
          <TextInput
            value={email}
            style={styles.input}
          />
        </View>
        <View>
          <Text style={styles.inputArea}>비밀번호</Text>
          <TextInput
            value={password}
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
          onPress={() => Alert.alert('Login success')}
          style={styles.loginBtn}>
          <Text style={styles.loginTxt}>로그인</Text>
        </Pressable>
        <StatusBar style="auto" />
      </View>
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
    color: '#6DAFB5',
    fontSize: 40,
    fontWeight: 'bold',
    marginBottom: 5,
  },
  inputArea: {
    fontSize: 18,
    marginTop: 20,
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
    backgroundColor: '#6DAFB5',
    borderRadius: 25,
    paddingVertical: 10,
    paddingHorizontal: 15,
    fontSize: 20,
    textAlign: 'center'
  }
});

export default Login;