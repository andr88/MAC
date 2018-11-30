package com.dora.app.mynetwork2;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MyService2 extends Service {
    public static final int CMD_GOTO_Sakura = 1;
    public static final int CMD_GET_SakuraImg = 2;

    //@androidx.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int cmd = intent.getIntExtra("cmd", -1);
        switch (cmd){
            case CMD_GOTO_Sakura:
                gotoSakura();
            case CMD_GET_SakuraImg:
                getSakuraImg();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void gotoSakura(){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("https://www.sakura.com.tw");
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.connect();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream())
                    );
                    String line;
                    while ( (line = reader.readLine()) != null ){
                        Log.v("brad", line);
                    }
                    reader.close();
                } catch (Exception e){
                    Log.v("brad", e.toString());
                }
                super.run();
            }
        }.start();
    }


    private void getSakuraImg(){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("https://www.sakura.com.tw/Uploads/Post/contents/1IMG_4727_20180307105939_1200x897_20180307105939.jpg");
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.connect();

                    InputStream in = conn.getInputStream();
                    //Bitmap bmp = BitmapFactory.decodeStream(in);
                    ManApp.bmp = BitmapFactory.decodeStream(in);

                    Intent intent = new Intent("GotImg");
                    //intent.putExtra("img", bmp);
                    sendBroadcast(intent);

                } catch (Exception e){
                    Log.v("brad", e.toString());
                }
                super.run();
            }
        }.start();
    }

}
