package com.dora.app.mywebview;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    public static final String BASIC_USERNAME = "brad";
    public static final String BASIC_PASSWORD = "bradpass!";
    private LocationManager lmgr;
    private MyListener myListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        initWebvew();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            init();
        } else {
            finish();
        }
    }

    private void init(){
        lmgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        myListener = new MyListener();
        lmgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myListener);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                    },
                    123);
        } else {
            init();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        lmgr.removeUpdates(myListener);
    }

    private class MyListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            Log.v("brad", lat +" , "+lng);
            webView.loadUrl("javascript:gotoKD(" + lat + "," + lng + ")");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    private void initWebvew(){
        //webView.loadUrl("https://www.sakura.com.tw");
        webView.setWebViewClient(new WebViewClient ());
        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/mymap.html");

        //webView.setWebViewClient(new MyWebViewClient ());
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
//                handler.proceed("brad", "bradpass!");
//                //super.onReceivedHttpAuthRequest(view, handler, host, realm);
//            }
//
//            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                String data = "brad:bradpass!";
//                String data2 = Base64.encodeToString(data.getBytes(), Base64.NO_WRAP);
//                HashMap<String,String> bauth = new HashMap();
//                bauth.put("Authorization", "Basic " + data2);
//                view.loadUrl(request.getUrl().toString(), bauth);
//                return true;
//            }
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url){
//                String data = "brad:bradpass!";
//                String data2 = Base64.encodeToString(data.getBytes(), Base64.NO_WRAP);
//                HashMap<String,String> bauth = new HashMap();
//                bauth.put("Authorization", "Basic " + data2);
//                view.loadUrl(url, bauth);
//                return true;
//            }
//
//
////            @Override
////            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
////                return getNewResponse(url);
////            }
////
////            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
////            @Override
////            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
////                String url = request.getUrl().toString();
////                return getNewResponse(url);
////            }
////
////            private WebResourceResponse getNewResponse(String url) {
////
////                try {
////                    OkHttpClient httpClient = new OkHttpClient();
////
////                    Request request = new Request.Builder()
////                            .url(url.trim())
////                            .addHeader("Authorization", "YOU_AUTH_KEY") // Example header
////                            .addHeader("api-key", "YOUR_API_KEY") // Example header
////                            .build();
////
////                    Response response = httpClient.newCall(request).execute();
////
////                    return new WebResourceResponse(
////                            null,
////                            response.header("content-encoding", "utf-8"),
////                            response.body().byteStream()
////                    );
////
////                } catch (Exception e) {
////                    return null;
////                }
////
////            }
//
//
//
//        });

//        WebSettings set = webView.getSettings();
//        set.setJavaScriptEnabled(true);
//        set.setUserAgentString("myApp");
//
//        String data = "brad:bradpass!";
//        String data2 = Base64.encodeToString(data.getBytes(), Base64.NO_WRAP);
//        HashMap<String,String> bauth = new HashMap();
//        bauth.put("Authorization", "Basic " + data2);
//        //bauth.put("httpMethod", "POST");
//
//        //webView.loadUrl("http://www1.sakura.com.tw/sakura/asklev2.nsf/vwMyAu.xsp", bauth);
//        //webView.loadUrl("xxxhttps://sakura5.sakura.com.tw", bauth);
//        //webView.loadUrl("https://sakura5.sakura.com.tw/names.nsf?login", bauth);
//        webView.loadUrl("https://sakura5.sakura.com.tw/traveler", bauth);
//        webView.loadUrl("https://www1.sakura.com.tw/sakura/asklev2.nsf/vwMyAu.xsp", bauth);

    }

    public void test1(View view) {
        webView.loadUrl("javascript:createLottery()");
    }

    public void test2(View view) {
        double lat = 24.001420;
        double lng = 121.606043;
        webView.loadUrl("javascript:gotoKD(" + lat + "," + lng + ")");

        //webView.loadUrl("https://www1.sakura.com.tw/names.nsf?logout");
    }

    private static class BasicAuth {
        final String host;
        final String realm;
        final String username;
        final String password;
        public BasicAuth(String host, String realm, String username, String password) {
            this.host = host;
            this.realm = realm;
            this.username = username;
            this.password = password;
        } }

//    private class MyWebViewClient extends WebViewClient {
//        private BasicAuth mAuthedData = null;
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            mAuthedData = null;
//            super.onPageStarted(view, url, favicon);
//        }
//
//        @Override
//        public void onReceivedHttpAuthRequest(WebView view,
//                                              HttpAuthHandler handler, String host, String realm) {
//            //handler.proceed("brad", "bradpass!");
//            String[] usernamePassword = view.getHttpAuthUsernamePassword( host, realm);
//            if (usernamePassword == null) {
//                if (mAuthedData == null) {  //最初の認証。認証をトライする
//                    mAuthedData = new BasicAuth(host, realm, BASIC_USERNAME, BASIC_PASSWORD);
//                    handler.proceed(mAuthedData.username, mAuthedData.password);
//                } else {
//                    handler.cancel();
//                }
//            } else {
//                if (mAuthedData == null) { //一度認証に成功 すでに保存済みのユーザー名/パスワードがあるため、それを利用する(自動認証)。
//                    String username = usernamePassword[0]; //一度目の自動認証試行。
//                    String password = usernamePassword[1];
//                    handler.proceed(username, password);
//                    mAuthedData = new BasicAuth(host, realm, username, password);
//                } else { // 自動認証失敗時。
//                    WebViewDatabase.getInstance(getApplicationContext()).clearHttpAuthUsernamePassword();
//                    mAuthedData = null;
//                    onReceivedHttpAuthRequest(view, handler, host, realm);
//                }
//            }
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            if (mAuthedData != null) {
//                view.setHttpAuthUsernamePassword(mAuthedData.host,
//                        mAuthedData.realm,
//                        mAuthedData.username,
//                        mAuthedData.password);
//                mAuthedData = null;
//            }
//            //super.onPageFinished(view, url);
//        }
//    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
