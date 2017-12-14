/**
 * Created by kHRYSTAL on 17/12/14.
 */
// judge js run env
export const inBrowser =
    typeof window !== 'undefined' && Object.prototype.toString.call(window) !== '[object Object]'

export const UA = inBrowser && window.navigator.userAgent.toLowerCase();
export const isAndroid = UA && UA.indexOf('android') > 0;
export const  isIOS = UA && /iphone|ipad|ipod|ios/.test(UA);
export const isThreesome = UA && UA.indexOf('threesome') > 0;
// can set version in app client with threesome, use this value can make different logic in different app version
export const AppVersion = isThreesome && UA.match(/threesome\/([\d\.]*)/)[1];