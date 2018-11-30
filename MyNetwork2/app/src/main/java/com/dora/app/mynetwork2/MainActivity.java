package com.dora.app.mynetwork2;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.ImageView;

import java.text.BreakIterator;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    private ConnectivityManager cmgr;
    private MyReceiver myReceiver;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cmgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        String uu = "iT \u90A6 \u5e6b \u5fd9";
        Log.v("brad", "network = "+isConnectNetwork() + uu);

        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction("GotImg");
        registerReceiver(myReceiver, filter);

        img = findViewById(R.id.img);
    }

    private boolean isConnectNetwork(){
        NetworkInfo info = cmgr.getActiveNetworkInfo();
        boolean IsConnected = info != null && info.isConnectedOrConnecting();
        return IsConnected;

    }

    @Override
    protected void onStart() { //使用中才會接收
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myReceiver, filter);
    }

    @Override
    protected void onStop() { //非使用中不接收
        super.onStop();
        unregisterReceiver(myReceiver);
    }

    @Override
    public void finish() {  //不使用 onStart onStop, 使用 finish 只要活著就一直在偵測
        super.finish();
        unregisterReceiver(myReceiver);
    }


    private  class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case ConnectivityManager.CONNECTIVITY_ACTION:
                    Log.v("brad", "network = "+isConnectNetwork() );
                    break;
                case "GotImg":
                    showImage(intent);
                    Log.v("brad", "Got Image" );
                    break;
            }
        }
    }

    public void test1(View view) {
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("key", (int)Math.random()*40+1);
        startService(intent);
    }

    public void test2(View view) {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }


    public void test3(View view) {
        Intent intent = new Intent(this, MyService2.class);
        intent.putExtra("cmd", MyService2.CMD_GOTO_Sakura);
        startService(intent);
    }

    public void test4(View view) {
        Intent intent = new Intent(this, MyService2.class);
        intent.putExtra("cmd", MyService2.CMD_GET_SakuraImg);
        startService(intent);
    }

    private void showImage(Intent intent){
        //Bitmap bmp = BitmapFactory.decodeStream(?);
        //Bitmap bmp = (Bitmap) intent.getParcelableExtra("img");
        //img.setImageBitmap(bmp);

        img.setImageBitmap(ManApp.bmp);
    }


}
