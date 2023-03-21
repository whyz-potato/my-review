import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { FontAwesome, MaterialIcons, Ionicons } from '@expo/vector-icons';
import ExploreScreen from './ExploreStack';
import ReviewScreen from './ReviewStack';
import Home from '../pages/Home';

const BottomTabNav = () => {
    const BottomTab = createBottomTabNavigator();

    return(
        <BottomTab.Navigator initialRouteName='home' 
        screenOptions={{tabBarActiveTintColor:'#6DAFB5', tabBarInactiveTintColor:'#E1D7C6',tabBarStyle:{height:70}, tabBarShowLabel:false, headerShown:false, unmountOnBlur:true}}>
            <BottomTab.Screen name="explore" component={ExploreScreen} 
            options={{tabBarIcon: ({color})=>(<MaterialIcons name="explore" size={46} color={color} />)}} />
            <BottomTab.Screen name='home' component={Home} 
            options={{tabBarIcon: ({color})=>(<FontAwesome name="home" size={46} color={color} />)}} />
            <BottomTab.Screen name="review" component={ReviewScreen} 
            options={{tabBarIcon: ({color})=>(<Ionicons name="library" size={43} color={color} />)}} />
        </BottomTab.Navigator>
    );
}

export default BottomTabNav;