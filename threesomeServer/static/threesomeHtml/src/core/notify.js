/**
 * Created by kHRYSTAL on 17/12/14.
 */
import {_registerHandler, _callHandler} from './handler';

var obMap = {};
export function _registerNotify(key, event) {
    obMap[key] = event;
}

function _notifyCenter() {
    _registerHandler('ThreesomeNativeNotification', function (res) {
        var callbacks = obMap[res.type];
        var success = 'success' in callbacks ? callbacks.success : function(){};
        var fail = 'fail' in callbacks? callbacks.fail : function(){};
        var complete = 'complete' in callbacks? callbacks.complete : function(){};

        switch (res.code) {
            case 200:
            case 501:
                success(res.type);
                break;
            case 500:
                fail(res.type);
                break;
        }
        complete(res.type);
    });
}

_notifyCenter();