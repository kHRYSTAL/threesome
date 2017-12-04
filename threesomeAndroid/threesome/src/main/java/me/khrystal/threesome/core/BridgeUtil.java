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
    final static String OVERRIDE_LOAD = OVERRIDE_SCHEMA + "__BRIDGE_LOADED__";
    //threesome://return/{{function}}/{{content}}
    final static String RETURN_DATA = OVERRIDE_SCHEMA + "return/";
    final static String FETCH_QUEUE = RETURN_DATA + "_fetchQueue/";
    final static String EMPTY_STR = "";
    final static String UNDELINE_STR = "_";
    final static String SPLIT_MARK = "/";

    final static String CALLBACK_ID_FORMAT = "JAVA_CB_%s";
    // use loadUrl execute html function
    final static String JS_HANDLE_MESSAGE_FROM_JAVA = "javascript:WebViewJavascriptBridge._handleMessageFromJava('%s');";
    final static String JS_FETCH_QUEUE_FRAM_JAVA = "javascript:WebViewJavascriptBridge._fetchQueue();";
    public final static String JAVASCRIPT_STR = "javascript:";

    public static String parseFunctionName(String jsUrl) {
        return jsUrl.replace("javascript:WebViewJavascriptBridge.", EMPTY_STR).replaceAll("\\(.*\\);", EMPTY_STR);
    }

    public static String getDataFromReturnUrl(String url) {
        if (url.startsWith(FETCH_QUEUE)) {
            return url.replace(FETCH_QUEUE, EMPTY_STR);
        }

        String temp = url.replace(RETURN_DATA, EMPTY_STR);
        // notice: "/" of json contain, would be split
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

    public static String getFunctionFromReturnUrl(String url) {
        String temp = url.replace(RETURN_DATA, EMPTY_STR);
        String[] functionAndData = temp.split(SPLIT_MARK);
        if (functionAndData.length >= 1) {
            return functionAndData[0];
        }
        return null;
    }

    public static void webViewLoadLocalJs(WebView view, String path){
        String jsContent = assetFile2Str(view.getContext(), path);
        view.loadUrl(JAVASCRIPT_STR + jsContent);
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
