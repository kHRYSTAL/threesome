const hasConsole = typeof console !== 'undefined';
export function warn(msg) {
    if (hasConsole) {
        console.error('[Threesome warning]: ' + msg);
    }
}