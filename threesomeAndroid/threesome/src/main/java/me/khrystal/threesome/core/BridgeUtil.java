package me.khrystal.threesome.core;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * usage: bridge util
 * author: kHRYSTAL
 * create time: 17/12/4
 * update time:
 * email: 723526676@qq.com
 */

public class BridgeUtil {
    final static String OVERRIDE_SCHEMA = "threesome://";
    final static String OVERRIDE_LOADED = OVERRIDE_SCHEMA + "__BRIDGE_LOADED__";
    // 格式为 yy://return/{function}/returncontent return 后跟函数名 再后跟返回的内容
    final static String RETURN_DATA = OVERRIDE_SCHEMA + "return/";
    final static String FETCH_QUEUE = RETURN_DATA + "_fetchQueue/";
    final static String EMPTY_STR = "";
    final static String UNDERLINE_STR = "_";
    final static String SPLIT_MARK = "/";

    final static String CALLBACK_ID_FORMAT = "JAVA_CB_%s";
    // js 处理 java内部方法
    final static String JS_HANDLE_MESSAGE_FROM_JAVA = "javascript:WebViewJavascriptBridge._handleMessageFromJava('%s');";
    // js 获取 java的内部message队列
    final static String JS_FETCH_QUEUE_FROM_JAVA = "javascript:WebViewJavascriptBridge._fetchQueue();";
    public final static String JAVASCRIPT_STR = "javascript:";

    /**
     * 获取js function名称
     *
     * @param jsUrl
     * @return
     */
    public static String parseFunctionName(String jsUrl) {
        return jsUrl.replace("javascript:WebViewJavascriptBridge.", "").replaceAll("\\(.*\\);", "");
    }

    /**
     * 从 js 返回的uri中获取数据
     *
     * @param url
     * @return
     */
    public static String getDataFromReturnUrl(String url) {
        if (url.startsWith(FETCH_QUEUE)) {
            return url.replace(FETCH_QUEUE, EMPTY_STR);
        }

        String temp = url.replace(RETURN_DATA, EMPTY_STR);
        // 默认contentdata中是没有 '/' 的, 如果有'/'会被去除
        String[] functionAndData = temp.split(SPLIT_MARK);

        if (functionAndData.length >= 2) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < functionAndData.length; i++) {
                sb.append(functionAndData[i]);
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 从 js 返回的uri中获取函数名称
     *
     * @param url
     * @return
     */
    public static String getFunctionFromReturnUrl(String url) {
        String temp = url.replace(RETURN_DATA, EMPTY_STR);
        String[] functionAndData = temp.split(SPLIT_MARK);
        if (functionAndData.length >= 1) {
            return functionAndData[0];
        }
        return null;
    }

    /**
     * js 文件将注入为第一个script引用
     * @param view
     * @param url
     */
    public static void webViewLoadJs(WebView view, String url){
        String js = "var newscript = document.createElement(\"script\");";
        js += "newscript.src=\"" + url + "\";";
        js += "document.scripts[0].parentNode.insertBefore(newscript,document.scripts[0]);";
        view.loadUrl("javascript:" + js);
    }

    public static void webViewLoadLocalJs(WebView view, String path){
        String jsContent = assetFile2Str(view.getContext(), path);
        Log.e("LoadLocal", jsContent);
        view.loadUrl("javascript:" + jsContent);
    }

    public static String assetFile2Str(Context c, String urlStr){
        InputStream in = null;
        try{
            in = c.getAssets().open(urlStr);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            StringBuilder sb = new StringBuilder();
            do {
                line = bufferedReader.readLine();
                if (line != null && !line.matches("^\\s*\\/\\/.*")) {
                    sb.append(line);
                }
            } while (line != null);

            bufferedReader.close();
            in.close();

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }
}
