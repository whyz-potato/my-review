import { StatusBar } from 'expo-status-bar';
import { useState, useEffect } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { AntDesign } from '@expo/vector-icons';
import Svg, {Path} from 'react-native-svg';
import URL from '../api/axios';

const Home = ({ navigation, route }) => {
  const [goal, setGoal] = useState(10);
  const [cnt, setCnt] = useState(0);
  const [achievement, setAchievement] = useState(0);
  const [userId, setUserId] = useState(0);
  // const [userId, setUserId] = useState(route.params.user_id);

  const getId = async () => {
    try {
      const val = await AsyncStorage.getItem('userId');
      if (val !== null) setUserId(val);
    } catch (error) {
        console.log("get id fail");
        console.log(error);
    }
  }

  useEffect(() => {
    getId();
  }, []);

  // get goal
  useEffect(() => {
    console.log(userId);
    if (userId !== 0) {
      URL.get(`/v1/goal/${userId}`)
        .then((res) => {
          console.log(res.data);
          setGoal(res.data.target);
          setCnt(res.data.cnt);
          setAchievement(res.data.attainmentRate);
          // setAchievement(100);   // fortest
        })
        .catch((err) => {
          console.log('get goal fail');
          console.error(err);
        })
    }
  }, [userId])

  return (
    <View style={styles.container}>
      <Svg viewBox="0 0 412 787" width="412" height="787" style={styles.svg}>
        <Path d="M0 322L13.7 332.5C27.3 343 54.7 364 82.2 362.3C109.7 360.7 137.3 336.3 164.8 330.8C192.3 325.3 219.7 338.7 247.2 346.2C274.7 353.7 302.3 355.3 329.8 354.7C357.3 354 384.7 351 398.3 349.5L412 348L412 788L398.3 788C384.7 788 357.3 788 329.8 788C302.3 788 274.7 788 247.2 788C219.7 788 192.3 788 164.8 788C137.3 788 109.7 788 82.2 788C54.7 788 27.3 788 13.7 788L0 788Z" fill="#e6f4f1"></Path>
        <Path d="M0 482L13.7 482.5C27.3 483 54.7 484 82.2 484C109.7 484 137.3 483 164.8 474.7C192.3 466.3 219.7 450.7 247.2 441.2C274.7 431.7 302.3 428.3 329.8 426.3C357.3 424.3 384.7 423.7 398.3 423.3L412 423L412 788L398.3 788C384.7 788 357.3 788 329.8 788C302.3 788 274.7 788 247.2 788C219.7 788 192.3 788 164.8 788C137.3 788 109.7 788 82.2 788C54.7 788 27.3 788 13.7 788L0 788Z" fill="#c2e2df"></Path>
        <Path d="M0 560L13.7 568C27.3 576 54.7 592 82.2 598.3C109.7 604.7 137.3 601.3 164.8 593.2C192.3 585 219.7 572 247.2 564.5C274.7 557 302.3 555 329.8 563C357.3 571 384.7 589 398.3 598L412 607L412 788L398.3 788C384.7 788 357.3 788 329.8 788C302.3 788 274.7 788 247.2 788C219.7 788 192.3 788 164.8 788C137.3 788 109.7 788 82.2 788C54.7 788 27.3 788 13.7 788L0 788Z" fill="#9ed0d0"></Path>
        <Path d="M0 635L13.7 638.2C27.3 641.3 54.7 647.7 82.2 652.2C109.7 656.7 137.3 659.3 164.8 661.3C192.3 663.3 219.7 664.7 247.2 665C274.7 665.3 302.3 664.7 329.8 665.3C357.3 666 384.7 668 398.3 669L412 670L412 788L398.3 788C384.7 788 357.3 788 329.8 788C302.3 788 274.7 788 247.2 788C219.7 788 192.3 788 164.8 788C137.3 788 109.7 788 82.2 788C54.7 788 27.3 788 13.7 788L0 788Z" fill="#77bdc3"></Path>
      </Svg>
      {/* whale */}
      <AntDesign name="smile-circle" size={100} style={[styles.whale, {bottom: (achievement==0)?10:(achievement>=100)?420:(achievement*3.5)}]} color="black" />
      
      <View style={styles.goalBox}>
        <Text style={{ fontSize: 50, fontWeight: 'bold', color: '#77BDC3' }}>{achievement}%</Text>
        <View>
          <View style={styles.goalDetail}>
            <Text style={{ fontSize: 18 }}>올해 목표</Text>
            <Text style={{ fontSize: 18 }}>{goal}</Text>
          </View>
          <View style={styles.goalDetail}>
            <Text style={{ fontSize: 18 }}>본 개수</Text>
            <Text style={{ fontSize: 18 }}>{cnt}</Text>
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
  },
  bgImage: {
    flex: 1,
  },
  goalBox: {
    alignItems: 'center',
    marginTop: 65
  },
  goalDetail: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginTop: 5,
    width: 150,
  },
  svg:{
    position: 'absolute',
    bottom:0
  },
   whale: {
    position: 'absolute',
    alignSelf: 'center',
   },
});

export default Home;