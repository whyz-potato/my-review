import { createNativeStackNavigator } from '@react-navigation/native-stack';
import ExploreLib from '../pages/ExploreLib';
import ExploreCinema from '../pages/ExploreCinema';

const ExploreScreen = () => {
    const ExploreStack = createNativeStackNavigator();

    return(
        <ExploreStack.Navigator screenOptions={{headerShown:false, animation: 'none'}}>
            <ExploreStack.Screen name='exploreLib' component={ExploreLib} />
            <ExploreStack.Screen name='exploreCinema' component={ExploreCinema} />
        </ExploreStack.Navigator>
    );
}

export default ExploreScreen;