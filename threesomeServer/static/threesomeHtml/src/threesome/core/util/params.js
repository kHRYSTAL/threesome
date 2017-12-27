import {clone} from '../../../lib/clone'


export function _formatParam(oldParam){
    var param = clone(oldParam);

    if(typeof param === 'object' ){
        for(var key in param){
            param[key] = _formatParam(param[key]);
        }
        // param = JSON.stringify(param);
    }
    else if(typeof param === 'string'){
        param = encodeURI(param);
    }
    else if(typeof param !== 'function'){
        param = param.toString();
    }

    return param;
}

export function _decodeString(param){
    // return decodeURI(param);
    return decodeURIComponent(param)
}

export function _createEventName(txt){
    txt = encodeURI(txt).replace(/%/g,"");
    return '_'+txt+'_Callback';
}
