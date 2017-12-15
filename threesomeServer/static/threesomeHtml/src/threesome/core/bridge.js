/**
 * Created by kHRYSTAL on 17/12/14.
 */
var callQueue = [];
var registerQueue = [];

function createBridge() {
    var fakeBridge = {
        callHandler: function (...args) {
            callQueue.push(function () {
               window.WebViewJavascriptBridge.callHandler.apply(window.WebViewJavascriptBridge, args);
            });
        },
        registerHandler: function (...args) {
            registerQueue.push(function(){
                window.WebViewJavascriptBridge.registerHandler.apply(window.WebViewJavascriptBridge, args);
            });
        }
    };
    var callback = function (realBridge) {
        let bridge = realBridge;
        while(registerQueue.length > 0) {
            var fn = registerQueue.shift();
            fn();
        }
        while(callQueue.length > 0) {
            var fn = callQueue.shift();
            fn();
        }
    }
    window.WVJBCallbacks = [callback]
    setTimeout(function () {
        var WVJBIframe = document.createElement('iframe');
        WVJBIframe.style.display = 'none';
        WVJBIframe.src = 'threesome://__BRIDGE_LOADED__';
        document.documentElement.appendChild(WVJBIframe);
        setTimeout(function () {
            document.documentElement.removeChild(WVJBIframe);
        }, 0);
    }, 0);

    return fakeBridge;
}

var bridge = window.WebViewJavascriptBridge ? window.WebViewJavascriptBridge : createBridge()

export {bridge}
