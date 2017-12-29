/**
 * Created by kHRYSTAL on 17/12/29.
 */
import {_registerHandler, _callHandler} from '../interface/output'
import {_createEventName} from '../util/output'

/**
 * @export
 * @param {String} navBtnType enum [‘navBack’， ‘navBackWhite’]  default navBack(black)
 * @returns
 */
export function setBackButtonStyleType(navBtnType) {
    return _callHandler("threesome/titleBar/leftbutton", {navBtnType: navBtnType})
}

/**
 * @export
 * @param {String} color  0x000000 or #000000
 * @returns
 */
export function setCloseTextFontColor(color) {
    return _callHandler("threesome/titleBar/leftclosebutton/color", {color: color})
}


/**
 * createMenuButton
 */
function createRightButton (config, handlerFunc){
    var buttonName = config.txt ? config.txt :
                        config.navBtnType ? config.navBtnType : '';
    config['handlerName'] = _createEventName(buttonName);
    _registerHandler(config.handlerName, handlerFunc); // register callback

    return _callHandler('threesome/titleBar/button', config); // return promise
}

export function showTxtRightButton (params, handlerFunc){
    var config = (typeof params == 'string') ? {txt: params} : params
    return createRightButton (config, handlerFunc)
}

export function showGraphicalButton(navBtnType, handlerFunc){
    return createRightButton ({navBtnType: navBtnType}, handlerFunc)
}

export function setNavigationBar (config){
    var pArr = [];
    if(config.backgroundColor){
        pArr.push(setNavigationBarBackgroundColor(config.backgroundColor))
    }
    if(config.title){
        pArr.push(setNavigationBarTitle(config.title))
    }
    if(config.fontSize){
        pArr.push(setNavigationBarFontSize(config.fontSize))
    }
    if(config.color){
        pArr.push(setNavigationBarFontColor(config.color))
    }
    if(config.backgroundImage){
        pArr.push(setNavigationBarBackgroundImage(config.backgroundImage))
    }
    if(config.statusBarStyletype){
        pArr.push(setStatusBarStyletype(config.statusBarStyletype))
    }
    return pArr.length > 0 ? Promise.all(pArr) : undefined;
}


/**
 * setNavigationBarTitle
 * @param {String}   text     
 * @param {Function} callback 
 */
export function setNavigationBarTitle (title){
    return _callHandler('threesome/titleBar/title/text', {content: title});
}


/**
 * setNavigationBarColor
 * @param {String}   color    ‘0xFFFFFF’ or '#FFFFFF'
 * @param {Function} callback 
 */
export function setNavigationBarFontColor (color){
    return _callHandler('threesome/titleBar/title/textColor', {color: color});
}


/**
 * setNavigationBarFontSize
 * @param {Number}   fontSize 
 * @param {Function} callback 
 */
export function setNavigationBarFontSize (size){
    return _callHandler('threesome/titleBar/title/fontSize', {size: size});
}


/**
 * setNavigationBarBackgroundColor
 * @param {String}   backgroundColor‘0xFFFFFF’ or '#FFFFFF'
 * @param {Function} callback
 */
export function setNavigationBarBackgroundColor (color){
    return _callHandler('threesome/titleBar/background/color', {color: color});
}

/**
 * setNavigationBarBackgroundImage
 * @param {String}   imageType 
 * @param {Function} callback
 */
export function setNavigationBarBackgroundImage (imageType){
    return _callHandler('threesome/titleBar/background/image', {imageType: imageType});
}

/**
 * setStatusBar
 * @param {String}   style  statusbar text color 0: black 1:white
 * @param {Function} callback
 */
export function setStatusBarStyletype (style){
    return _callHandler('threesome/statusbar/style', {style: style});
}