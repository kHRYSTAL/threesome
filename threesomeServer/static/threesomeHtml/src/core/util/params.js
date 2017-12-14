/**
 * Created by kHRYSTAL on 17/12/14.
 */
import  {clone} from '../../lib/clone'

// handle send message to format need encode to support Android and iOS
export function _formatParam(oldParam) {
    var param = clone(oldParam);
    if (typeof param === 'object') {
        for (var key in param) {
            param[key] = _formatParam(param[key]);
        }
        // param = JSON.stringify(param);
    } else if (typeof param === 'string') {
        param = encodeURI(param);
    } else if (typeof param !== 'function') {
        param = param.toString();
    }
    return param;
}

// handle receive message need decode
export function _decodeString(param) {
    return decodeURIComponent(param);
}

// create event function name
export function _createEventName(txt) {
    let txt = encodeURI(txt).replace(/%/g,""); // need encode because text maybe chinese
    return '_'+txt+'_Callback';
}