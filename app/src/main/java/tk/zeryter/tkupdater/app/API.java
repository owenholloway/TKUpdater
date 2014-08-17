package tk.zeryter.tkupdater.app;

import android.util.Log;
import android.webkit.*;
import org.apache.http.util.EncodingUtils;

/*
* Owen Holloway, Zeryt
* */

public class API {

    private static WebView webViewer;

    public static void setWebView(WebView viewToSet) {
        webViewer = viewToSet;

        //Remove all previous cookies
        CookieSyncManager.createInstance(webViewer.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
        Log.i("cookies", "removed all cookies");

        WebSettings webSettings = webViewer.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //setup the webViewer
        webViewer.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                //webViewer.loadUrl("javascript:window.INTERFACE.setDocument(document.documentElement.innerHTML);");
            }
        });

        webViewer.addJavascriptInterface(new WebViewInterface(), "INTERFACE");
    }

    public static void testLogin(final String user, final String pass) {
        //The main website url
        webViewer.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                webViewer.loadUrl("javascript:function set() {document.getElementById('fldemail').value=\""+user+"\";document.getElementById('fldpassword').value=\""+pass+"\";}; set(); document.Login.submit();");
            }
        });
        webViewer.loadUrl("http://my.dot.tk");


        //Log.d("utasconnect", webViewer.getUrl());
    }

    public static class WebViewInterface {

        public static String html;

        @JavascriptInterface
        public void setDocument(String document) {
            html = document;
            Log.i("Document", document);
        }

    }
}