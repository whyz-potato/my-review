import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Library from '../pages/Library';
import Cinema from '../pages/Cinema';

const ReviewScreen = () => {
    const ReviewStack = createNativeStackNavigator();

    return(
        <ReviewStack.Navigator screenOptions={{headerShown:false, animation: 'none'}}>
            <ReviewStack.Screen name='library' component={Library} />
            <ReviewStack.Screen name='cinema' component={Cinema} />
        </ReviewStack.Navigator>
    );
}

export default ReviewScreen;