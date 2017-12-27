import {warn, _formatParam, _decodeString} from '../util/output'
import {bridge} from '../../bridge/bridge'


export function _registerHandler(name, event){
    bridge.registerHandler(name, function(res){

        res = _decodeString(res)
        try {
            res = JSON.parse(res)
        } catch (e) {} finally {
            event(res)
        }
    })
}


export function _callHandler(taskName, param){

    return new Promise(function(resolve, reject){
        if(!(taskName && typeof taskName === "string")){
            reject(taskName + " lost argument");
            return;
        }

        var config = {
            taskId : taskName,
        };

        if(!!param){
            config["param"] =_formatParam(param);
        }

        bridge.callHandler('NativeThreesome', config, function(res){

            res = _decodeString(res)
            try {
                res =  JSON.parse(res)
            } catch (e) {}
            finally {
                resolve(res)
            }
        });
    });
}
