// judge js run env
export const inBrowser =
    typeof window !== 'undefined' && Object.prototype.toString.call(window) !== '[object Object]'

export const UA = inBrowser && window.navigator.userAgent.toLowerCase();
export const isAndroid = UA && UA.indexOf('android') > 0;
export const  isIOS = UA && /iphone|ipad|ipod|ios/.test(UA);
export const isThreesome = UA && UA.indexOf('threesome') > 0;
// can set version in app client with threesome, use this value can make different logic in different app version
export const AppVersion = isThreesome && UA.match(/threesome\/([\d\.]*)/)[1];


/**
 * @param targetVersion current server static js version
 * @returns {boolean} if return false, means appVersion is old
 */
export function compareAppVersion(targetVersion) {
    if (!AppVersion) {
        return;
    }
    let aArr = AppVersion.split('.');
    let bArr = targetVersion.split('.');
    for (var i=0; i < 3; i++) {
        let aNum = Number(aArr[i]);
        let bNum = Number(bArr[i]);

        if (aNum === bNum) {
            continue;
        } else {
            return aNum > bNum;
        }
        return true;
    }
}