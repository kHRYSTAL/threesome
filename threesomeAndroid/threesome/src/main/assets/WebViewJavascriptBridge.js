(function() {
    if (window.WebViewJavascriptBridge) {
        return;
    }

    var messagingIframe;
    var sendMessageQueue = [];
    var receiveMessageQueue = [];
    var messageHandlers = {};

    var CUSTOM_PROTOCOL_SCHEME = 'threesome';
    var QUEUE_HAS_MESSAGE = '__WVJB_QUEUE_MESSAGE__';

    var responseCallbacks = {};
    var uniqueId = 1;

    function _createQueueReadyIframe(doc) {
        messagingIframe = doc.createElement('iframe');
        messagingIframe.style.display = 'none';
        doc.documentElement.appendChild(messagingIframe);
    }

    //set default messageHandler
    function init(messageHandler) {
        if (WebViewJavascriptBridge._messageHandler) {
            window.console.log('WebViewJavascriptBridge.init called twice');
            return;
        }
        WebViewJavascriptBridge._messageHandler = messageHandler;
        var receivedMessages = receiveMessageQueue;
        receiveMessageQueue = null;
        for (var i = 0; i < receivedMessages.length; i++) {
            _dispatchMessageFromJava(receivedMessages[i]);
        }
    }

    function send(data, responseCallback) {
        _doSend({
            data: data
        }, responseCallback);
    }

    function registerHandler(handlerName, handler) {
        messageHandlers[handlerName] = handler;
    }

    function callHandler(handlerName, data, responseCallback) {
        _doSend({
            handlerName: handlerName,
            data: data
        }, responseCallback);
    }

    //sendMessage add message, android call sendMessage
    function _doSend(message, responseCallback) {

        if (responseCallback) {
            var callbackId = 'cb_' + (uniqueId++) + '_' + new Date().getTime();
            responseCallbacks[callbackId] = responseCallback;
            message.callbackId = callbackId;
        }

        sendMessageQueue.push(message);
        messagingIframe.src = CUSTOM_PROTOCOL_SCHEME + '://' + QUEUE_HAS_MESSAGE;
    }

    function _fetchQueue() {
        var messageQueueString = JSON.stringify(sendMessageQueue);
        sendMessageQueue = [];
        //android can't read directly the return data, so we can reload iframe src to communicate with java
        messagingIframe.src = CUSTOM_PROTOCOL_SCHEME + '://return/_fetchQueue/' + encodeURIComponent(messageQueueString);
    }

    //提供给native使用,
    function _dispatchMessageFromJava(messageJSON) {
        window.console.log("_dispatchMessageFromJava");
        setTimeout(function() {
            window.console.log("real dispatch");
            var message = JSON.parse(messageJSON);
            var responseCallback;
            //java call finished, now need to call js callback function
            if (message.responseId) {
                window.console.log("response id is:", message.responseId);
                responseCallback = responseCallbacks[message.responseId];
                if (!responseCallback) {
                    return;
                }
                responseCallback(message.responseData);
                delete responseCallbacks[message.responseId];
            } else {
                window.console.log("response id is null");
                // direct send
                if (message.callbackId) {
                    var callbackResponseId = message.callbackId;
                    responseCallback = function(responseData) {
                        _doSend({
                            responseId: callbackResponseId,
                            responseData: responseData
                        });
                    };
                }

                window.console.log(message.handlerName);
                var handler = WebViewJavascriptBridge._messageHandler;
                if (message.handlerName) {
                    handler = messageHandlers[message.handlerName];
                    if (typeof console !== 'undefined') {
                        console.log("handlerName: " + message.handlerName);
                     }
                }
                // find handler
                try {
                    if (typeof console !== 'undefined') {
                        console.log("start handle: " + message.data);
                        console.log(typeof responseCallback);
                    }
                   handler(message.data, responseCallback)
                } catch (exception) {
                    if (typeof console !== 'undefined') {
                        console.log("WebViewJavascriptBridge: WARNING: javascript handler threw.", message, exception);
                    }
                }
            }
        }, 0);
        window.console.log("_dispatchMessageFromJava end");
    }

    function _handleMessageFromJava(messageJSON) {
        console.log(messageJSON);
        if (receiveMessageQueue && receiveMessageQueue.length > 0) {
            console.log("receiveMessage");
            receiveMessageQueue.push(messageJSON);
        } else {
            console.log("dispatchMessage");
            _dispatchMessageFromJava(messageJSON);
        }
    }

    var WebViewJavascriptBridge = window.WebViewJavascriptBridge = {
        init: init,
        send: send,
        registerHandler: registerHandler,
        callHandler: callHandler,
        _fetchQueue: _fetchQueue,
        _handleMessageFromJava: _handleMessageFromJava
    };

    var doc = document;
    _createQueueReadyIframe(doc);
    setTimeout(_callWVJBCallbacks, 0);
    	function _callWVJBCallbacks() {
    		var callbacks = window.WVJBCallbacks;
    		delete window.WVJBCallbacks;
    		for (var i=0; i<callbacks.length; i++) {
    			callbacks[i](WebViewJavascriptBridge);
    		}
    	}
})();
