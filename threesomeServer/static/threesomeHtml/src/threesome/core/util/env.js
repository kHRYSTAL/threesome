
export const inBrowser =
    typeof window !== 'undefined' &&
    Object.prototype.toString.call(window) !== '[object Object]'

export const UA = inBrowser && window.navigator.userAgent.toLowerCase()
export const isAndroid = UA && UA.indexOf('android') > 0
export const isIOS = UA && /iphone|ipad|ipod|ios/.test(UA)
export const isThreesome = UA && UA.indexOf('threesome') > 0
export const AppVersion = isThreesome && UA.match(/threesome\/([\d\.]*)/)[1]

export function isNative (Ctor) {
  return /native code/.test(Ctor.toString())
}


const MAJOR_REG = /^(\d+)\.$/
const MINOR_REG = /\.(\d+)\./
const PATCH_REG = /\.(\d+)$/


function matchVersion (version, reg) {
    return Number(version.match(reg)[1])
}

//与当前app版本比较是否更新
export function compareAppVersion (tagetVersion) {
    if(!AppVersion){
        return
    }

    let aArray = AppVersion.split('.')
    let bArray = tagetVersion.split('.')

    for (var i = 0; i < 3; i++) {
        let aNum = Number(aArray[i])
        let bNum = Number(bArray[i])

        if(aNum === bNum){
            continue
        }else{
            return aNum > bNum
        }
    }

    return true
}
