package com.dora.app.mynetwork1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private TextView source;
    private StringBuffer sb;
    private UIhandler uIhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        source = findViewById(R.id.source);
        uIhandler = new UIhandler();
    }

    public void test1(View view) {
        sb = new StringBuffer();

        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("https://www.sakura.com.tw");
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.connect();
                    conn.getInputStream();
                    BufferedReader reader =
                            new BufferedReader(
                                    new InputStreamReader(
                                            conn.getInputStream()));
                    String line = null; int i=1;
                    while ( (line = reader.readLine()) != null){
                        Log.v("brad", i + ":" + line);
                        sb.append(line + "\n");
                        i++;
                    }
                    reader.close();
                    uIhandler.sendEmptyMessage(0);
                    //source.setText(sb);

                } catch (Exception e) {
                    Log.v("brad", e.toString());
                }
            }
        }.start();
        Log.v("brad", "main");
    }


    public void test2(View view) {
        sb = new StringBuffer();

        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvTravelFood.aspx");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    conn.getInputStream();
                    BufferedReader reader =
                            new BufferedReader(
                                    new InputStreamReader(
                                            conn.getInputStream()));
                    String line = null; int i=1;
                    while ( (line = reader.readLine()) != null){
                        sb.append(line);
                        i++;
                    }
                    reader.close();
                    parseJSON(sb.toString());

                } catch (Exception e) {
                    Log.v("brad", e.toString());
                }
            }
        }.start();
    }

    private void parseJSON(String json){
        try {
            JSONArray root = new JSONArray(json);
            for (int i=0; i<root.length(); i++){
                JSONObject row = root.getJSONObject(i);
                String name = row.getString("Name");
                String tel = row.getString("Tel");
                Log.v("brad", name + ":" + tel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.v("brad", e.toString());
        }
    }

    public void test3(View view) {
    }


    private class UIhandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            source.setText(sb);
        }
    }

}
