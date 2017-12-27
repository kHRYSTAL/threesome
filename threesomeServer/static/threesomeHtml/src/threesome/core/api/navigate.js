import {_registerHandler, _callHandler} from '../interface/output'

/**
 * open a new page
 * @param config
 */
export function navigateTo(config) {
    _callHandler('threesome/navigator/openGroup', config)
}

/**
 * redirect to current page
 * @param config
 */
export function redirectTo (config){
    _callHandler('threesome/navigator/openPage', config);
}

/**
 * close current page
 */
export function navigateBack (){
    _callHandler('threesome/navigator/closeCurGroup');
}

/**
 * redirect back current page
 */
export function redirectBack (){
    _callHandler('threesome/navigator/closeCurPage');
}

