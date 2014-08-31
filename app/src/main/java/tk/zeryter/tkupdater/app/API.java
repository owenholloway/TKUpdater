package tk.zeryter.tkupdater.app;

import android.util.Log;
import android.webkit.*;

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

        webViewer.addJavascriptInterface(new MyJavascriptInterface(), "INTERFACE");
    }

    public static void testLogin(final String user, final String pass) {
        //The main website url
        webViewer.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                webViewer.addJavascriptInterface(new MyJavascriptInterface(), "INTERFACE");
                webViewer.loadUrl("javascript:function set() {document.getElementById('username').value=\""+user+"\";document.getElementById('password').value=\""+pass+"\";} set();$('.form-stacked').submit();INTERFACE.setDocument('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                Log.d("WebViewerTitle",webViewer.getTitle());
           }
        });
        webViewer.loadUrl("https://partners.freenom.com/clientarea.php");
    }

    public static void  updateDNS(String url) {
        //webViewer.loadUrl();
    }

    public static class MyJavascriptInterface {

        public static String html;

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void setDocument(String document) {
            html = document;
            Log.i("Document", document);
        }

    }
}
