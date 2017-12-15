/**
 * Created by kHRYSTAL on 17/12/14.
 */
import {warn, _formatParam, _decodeString} from '../util/output';
import {bridge} from './bridge';

// receive app client event
export function _registerHandler(name, event) {
    bridge.registerHandler(name, function (res) {
        res = _decodeString(res);
        try {
            res = JSON.parse(res)
        } catch (e) {
            warn(e);
        } finally {
            event(res);
        }
    });
}

// call app client event
export function _callHandler(taskName, param) {
    return new Promise(function (resolve, reject) {
        if (!(taskName && typeof taskName === "string")) {
            return reject(taskName + " lost taskName");
        }

        let config = {
            taskId : taskName
        };

        if (!!param) {
            config["param"] = _formatParam(param);
        }

        bridge.callHandler("NativeThreesome", config, function (res) {
            res = _decodeString(res);
            try {
                res = JSON.parse(res);
            } catch (e) {
                warn(e);
            } finally {
                return resolve(res);
            }
        })
    })
}
