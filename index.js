
import { NativeModules,NativeEventEmitter } from 'react-native';
const {RNHDIdCard}=NativeModules;
export default {
/**
 * 导出模块设置监听方法回调
 */
show:function(){
    console.log(RNHDIdCard,'RNHDIdCard');
    // RNHDIDCard.show('Awesome',RNHDIDCard.SHORT)
    // return RNHDModule.show('Awesome',RNHDModule.SHORT);
}

};