/**!
 * threesome-sdk.js v0.0.1
 * (c) 2017-2017
 * author kHRYSTAL
 */
(function(){'use strict';var f,g=[];function l(a){g.push(a);1==g.length&&f()}function m(){for(;g.length;)g[0](),g.shift()}f=function(){setTimeout(m)};function n(a){this.a=p;this.b=void 0;this.f=[];var b=this;try{a(function(a){q(b,a)},function(a){r(b,a)})}catch(c){r(b,c)}}var p=2;function t(a){return new n(function(b,c){c(a)})}function u(a){return new n(function(b){b(a)})}function q(a,b){if(a.a==p){if(b==a)throw new TypeError;var c=!1;try{var d=b&&b.then;if(null!=b&&"object"==typeof b&&"function"==typeof d){d.call(b,function(b){c||q(a,b);c=!0},function(b){c||r(a,b);c=!0});return}}catch(e){c||r(a,e);return}a.a=0;a.b=b;v(a)}}
function r(a,b){if(a.a==p){if(b==a)throw new TypeError;a.a=1;a.b=b;v(a)}}function v(a){l(function(){if(a.a!=p)for(;a.f.length;){var b=a.f.shift(),c=b[0],d=b[1],e=b[2],b=b[3];try{0==a.a?"function"==typeof c?e(c.call(void 0,a.b)):e(a.b):1==a.a&&("function"==typeof d?e(d.call(void 0,a.b)):b(a.b))}catch(h){b(h)}}})}n.prototype.g=function(a){return this.c(void 0,a)};n.prototype.c=function(a,b){var c=this;return new n(function(d,e){c.f.push([a,b,d,e]);v(c)})};
function w(a){return new n(function(b,c){function d(c){return function(d){h[c]=d;e+=1;e==a.length&&b(h)}}var e=0,h=[];0==a.length&&b(h);for(var k=0;k<a.length;k+=1)u(a[k]).c(d(k),c)})}function x(a){return new n(function(b,c){for(var d=0;d<a.length;d+=1)u(a[d]).c(b,c)})};window.Promise||(window.Promise=n,window.Promise.resolve=u,window.Promise.reject=t,window.Promise.race=x,window.Promise.all=w,window.Promise.prototype.then=n.prototype.c,window.Promise.prototype["catch"]=n.prototype.g);}());
(function (global, factory) {
	typeof exports === 'object' && typeof module !== 'undefined' ? module.exports = factory() :
	typeof define === 'function' && define.amd ? define(factory) :
	(global.threesome = factory());
}(this, (function () { 'use strict';

// judge js run env
var inBrowser = typeof window !== 'undefined' && Object.prototype.toString.call(window) !== '[object Object]';

var UA = inBrowser && window.navigator.userAgent.toLowerCase();
var isAndroid = UA && UA.indexOf('android') > 0;
var isIOS = UA && /iphone|ipad|ipod|ios/.test(UA);
var isThreesome = UA && UA.indexOf('threesome') > 0;
// can set version in app client with threesome, use this value can make different logic in different app version
var AppVersion = isThreesome && UA.match(/threesome\/([\d\.]*)/)[1];

/**
 * @param targetVersion current server static js version
 * @returns {boolean} if return false, means appVersion is old
 */

var hasConsole = typeof console !== 'undefined';
function warn(msg) {
    if (hasConsole) {
        console.error('[Threesome warning]: ' + msg);
    }
}

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) {
  return typeof obj;
} : function (obj) {
  return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj;
};





















var _extends = Object.assign || function (target) {
  for (var i = 1; i < arguments.length; i++) {
    var source = arguments[i];

    for (var key in source) {
      if (Object.prototype.hasOwnProperty.call(source, key)) {
        target[key] = source[key];
      }
    }
  }

  return target;
};

function _instanceof(obj, type) {
  return type != null && obj instanceof type;
}

var nativeMap;
try {
  nativeMap = Map;
} catch (_) {
  // maybe a reference error because no `Map`. Give it a dummy value that no
  // value will ever be an instanceof.
  nativeMap = function nativeMap() {};
}

var nativeSet;
try {
  nativeSet = Set;
} catch (_) {
  nativeSet = function nativeSet() {};
}

var nativePromise;
try {
  nativePromise = Promise;
} catch (_) {
  nativePromise = function nativePromise() {};
}

/**
 * Clones (copies) an Object using deep copying.
 *
 * This function supports circular references by default, but if you are certain
 * there are no circular references in your object, you can save some CPU time
 * by calling clone(obj, false).
 *
 * Caution: if `circular` is false and `parent` contains circular references,
 * your program may enter an infinite loop and crash.
 *
 * @param `parent` - the object to be cloned
 * @param `circular` - set to true if the object to be cloned may contain
 *    circular references. (optional - true by default)
 * @param `depth` - set to a number if the object is only to be cloned to
 *    a particular depth. (optional - defaults to Infinity)
 * @param `prototype` - sets the prototype to be used when cloning an object.
 *    (optional - defaults to parent prototype).
 * @param `includeNonEnumerable` - set to true if the non-enumerable properties
 *    should be cloned as well. Non-enumerable properties on the prototype
 *    chain will be ignored. (optional - false by default)
*/
function clone(parent, circular, depth, prototype, includeNonEnumerable) {
  if ((typeof circular === 'undefined' ? 'undefined' : _typeof(circular)) === 'object') {
    depth = circular.depth;
    prototype = circular.prototype;
    includeNonEnumerable = circular.includeNonEnumerable;
    circular = circular.circular;
  }
  // maintain two arrays for circular references, where corresponding parents
  // and children have the same index
  var allParents = [];
  var allChildren = [];

  var useBuffer = typeof Buffer != 'undefined';

  if (typeof circular == 'undefined') circular = true;

  if (typeof depth == 'undefined') depth = Infinity;

  // recurse this function so we don't reset allParents and allChildren
  function _clone(parent, depth) {
    // cloning null always returns null
    if (parent === null) return null;

    if (depth === 0) return parent;

    var child;
    var proto;
    if ((typeof parent === 'undefined' ? 'undefined' : _typeof(parent)) != 'object') {
      return parent;
    }

    if (_instanceof(parent, nativeMap)) {
      child = new nativeMap();
    } else if (_instanceof(parent, nativeSet)) {
      child = new nativeSet();
    } else if (_instanceof(parent, nativePromise)) {
      child = new nativePromise(function (resolve, reject) {
        parent.then(function (value) {
          resolve(_clone(value, depth - 1));
        }, function (err) {
          reject(_clone(err, depth - 1));
        });
      });
    } else if (clone.__isArray(parent)) {
      child = [];
    } else if (clone.__isRegExp(parent)) {
      child = new RegExp(parent.source, __getRegExpFlags(parent));
      if (parent.lastIndex) child.lastIndex = parent.lastIndex;
    } else if (clone.__isDate(parent)) {
      child = new Date(parent.getTime());
    } else if (useBuffer && Buffer.isBuffer(parent)) {
      child = new Buffer(parent.length);
      parent.copy(child);
      return child;
    } else if (_instanceof(parent, Error)) {
      child = Object.create(parent);
    } else {
      if (typeof prototype == 'undefined') {
        proto = Object.getPrototypeOf(parent);
        child = Object.create(proto);
      } else {
        child = Object.create(prototype);
        proto = prototype;
      }
    }

    if (circular) {
      var index = allParents.indexOf(parent);

      if (index != -1) {
        return allChildren[index];
      }
      allParents.push(parent);
      allChildren.push(child);
    }

    if (_instanceof(parent, nativeMap)) {
      parent.forEach(function (value, key) {
        var keyChild = _clone(key, depth - 1);
        var valueChild = _clone(value, depth - 1);
        child.set(keyChild, valueChild);
      });
    }
    if (_instanceof(parent, nativeSet)) {
      parent.forEach(function (value) {
        var entryChild = _clone(value, depth - 1);
        child.add(entryChild);
      });
    }

    for (var i in parent) {
      var attrs;
      if (proto) {
        attrs = Object.getOwnPropertyDescriptor(proto, i);
      }

      if (attrs && attrs.set == null) {
        continue;
      }
      child[i] = _clone(parent[i], depth - 1);
    }

    if (Object.getOwnPropertySymbols) {
      var symbols = Object.getOwnPropertySymbols(parent);
      for (var i = 0; i < symbols.length; i++) {
        // Don't need to worry about cloning a symbol because it is a primitive,
        // like a number or string.
        var symbol = symbols[i];
        var descriptor = Object.getOwnPropertyDescriptor(parent, symbol);
        if (descriptor && !descriptor.enumerable && !includeNonEnumerable) {
          continue;
        }
        child[symbol] = _clone(parent[symbol], depth - 1);
        if (!descriptor.enumerable) {
          Object.defineProperty(child, symbol, {
            enumerable: false
          });
        }
      }
    }

    if (includeNonEnumerable) {
      var allPropertyNames = Object.getOwnPropertyNames(parent);
      for (var i = 0; i < allPropertyNames.length; i++) {
        var propertyName = allPropertyNames[i];
        var descriptor = Object.getOwnPropertyDescriptor(parent, propertyName);
        if (descriptor && descriptor.enumerable) {
          continue;
        }
        child[propertyName] = _clone(parent[propertyName], depth - 1);
        Object.defineProperty(child, propertyName, {
          enumerable: false
        });
      }
    }

    return child;
  }

  return _clone(parent, depth);
}

/**
 * Simple flat clone using prototype, accepts only objects, usefull for property
 * override on FLAT configuration object (no nested props).
 *
 * USE WITH CAUTION! This may not behave as you wish if you do not know how this
 * works.
 */
clone.clonePrototype = function clonePrototype(parent) {
  if (parent === null) return null;

  var c = function c() {};
  c.prototype = parent;
  return new c();
};

// private utility functions

function __objToStr(o) {
  return Object.prototype.toString.call(o);
}
clone.__objToStr = __objToStr;

function __isDate(o) {
  return (typeof o === 'undefined' ? 'undefined' : _typeof(o)) === 'object' && __objToStr(o) === '[object Date]';
}
clone.__isDate = __isDate;

function __isArray(o) {
  return (typeof o === 'undefined' ? 'undefined' : _typeof(o)) === 'object' && __objToStr(o) === '[object Array]';
}
clone.__isArray = __isArray;

function __isRegExp(o) {
  return (typeof o === 'undefined' ? 'undefined' : _typeof(o)) === 'object' && __objToStr(o) === '[object RegExp]';
}
clone.__isRegExp = __isRegExp;

function __getRegExpFlags(re) {
  var flags = '';
  if (re.global) flags += 'g';
  if (re.ignoreCase) flags += 'i';
  if (re.multiline) flags += 'm';
  return flags;
}
clone.__getRegExpFlags = __getRegExpFlags;

// handle send message to format need encode to support Android and iOS
function _formatParam(oldParam) {
    var param = clone(oldParam);
    if ((typeof param === 'undefined' ? 'undefined' : _typeof(param)) === 'object') {
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
function _decodeString(param) {
    return decodeURIComponent(param);
}

// create event function name

var callQueue = [];
var registerQueue = [];

function createBridge() {
    var fakeBridge = {
        callHandler: function callHandler() {
            for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
                args[_key] = arguments[_key];
            }

            callQueue.push(function () {
                window.WebViewJavascriptBridge.callHandler.apply(window.WebViewJavascriptBridge, args);
            });
        },
        registerHandler: function registerHandler() {
            for (var _len2 = arguments.length, args = Array(_len2), _key2 = 0; _key2 < _len2; _key2++) {
                args[_key2] = arguments[_key2];
            }

            registerQueue.push(function () {
                window.WebViewJavascriptBridge.registerHandler.apply(window.WebViewJavascriptBridge, args);
            });
        }
    };
    var callback = function callback(realBridge) {
        var bridge = realBridge;
        while (registerQueue.length > 0) {
            var fn = registerQueue.shift();
            fn();
        }
        while (callQueue.length > 0) {
            var fn = callQueue.shift();
            fn();
        }
    };
    window.WVJBCallbacks = [callback];
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

var bridge = window.WebViewJavascriptBridge ? window.WebViewJavascriptBridge : createBridge();

// receive app client event
function _registerHandler(name, event) {
    bridge.registerHandler(name, function (res) {
        res = _decodeString(res);
        try {
            res = JSON.parse(res);
        } catch (e) {
            warn(e);
        } finally {
            event(res);
        }
    });
}

// call app client event
function _callHandler(taskName, param) {
    return new Promise(function (resolve, reject) {
        if (!(taskName && typeof taskName === "string")) {
            return reject(taskName + " lost taskName");
        }

        var config = {
            taskId: taskName
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
        });
    });
}

var obMap = {};


function _notifyCenter() {
    _registerHandler('ThreesomeNativeNotification', function (res) {
        var callbacks = obMap[res.type];
        var success = 'success' in callbacks ? callbacks.success : function () {};
        var fail = 'fail' in callbacks ? callbacks.fail : function () {};
        var complete = 'complete' in callbacks ? callbacks.complete : function () {};

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

/**
 * open a new page
 * @param config
 */
function navigateTo(config) {
  _callHandler('threesome/navigator/openGroup', config);
}

/**
 * redirect to current page
 * @param config
 */
function redirectTo(config) {
  _callHandler('threesome/navigator/openPage', config);
}

/**
 * close current page
 */
function navigateBack() {
  _callHandler('threesome/navigator/closeCurGroup');
}

/**
 * redirect back current page
 */
function redirectBack() {
  _callHandler('threesome/navigator/closeCurPage');
}



var api = Object.freeze({
	navigateTo: navigateTo,
	redirectTo: redirectTo,
	navigateBack: navigateBack,
	redirectBack: redirectBack
});

var Threesome$1 = _extends({}, api);

return Threesome$1;

})));
