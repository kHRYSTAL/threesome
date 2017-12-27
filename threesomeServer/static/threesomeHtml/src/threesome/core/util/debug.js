

const hasConsole = typeof console !== 'undefined'

export function warn(msg){
    console.error("[Threesome warn]: "+msg);
}
