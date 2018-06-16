package com.imaginecode.imaginecode;

/**
 * Created by shabadsobti on 6/16/18.
 */

import android.app.Activity;
import android.content.DialogInterface;
import android.webkit.WebView;
import android.app.AlertDialog.Builder;
import android.app.AlertDialog;

/**
 * Class to handle all calls from JS & from Java too
 **/
public class JsHandler {

    Activity activity;
    String TAG = "JsHandler";
    WebView webView;


    public JsHandler(Activity _contxt,WebView _webView) {
        activity = _contxt;
        webView = _webView;
    }


    /**
     * This function handles call from Android-Java
     */
    public void javaFnCall(String code) {

        final String webUrl = "javascript:" + code;
        // Add this to avoid android.view.windowmanager$badtokenexception unable to add window
        if(!activity.isFinishing())
            // loadurl on UI main thread
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    webView.loadUrl(webUrl);
                }
            });
    }


}
