package com.dora.app.mylocalfiles;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor sedt;
    //private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("config", MODE_PRIVATE);
        sedt = sp.edit();

        int counter = sp.getInt("counter", 0);
        sedt.putInt("counter", counter+1);
        sedt.commit();
    }

    public void test1(View view) {
        sedt.putString("username", "Brad");
        sedt.putString("password", "123456");
        sedt.putBoolean("sound", false);
        sedt.commit();
        Toast.makeText(this, "Save OK!", Toast.LENGTH_SHORT).show();
    }

    public void test2(View view) {
        boolean sound = sp.getBoolean("sound", true);
        String username = sp.getString("username", "guest");
        String password = sp.getString("password", "123");
        int counter = sp.getInt("counter", 0);
        Toast.makeText(this, counter+":"+username+":"+password+":"+sound, Toast.LENGTH_SHORT).show();
    }

    public void test3(View view) {
        sedt.clear();
        sedt.commit();
    }

    public void test4(View view) {
        try {
            FileOutputStream fout = openFileOutput("data.txt", MODE_APPEND); //PRIVATE=rewrite
            fout.write("Hello world\n".getBytes());
            fout.write("Hello world2\n".getBytes());
            fout.write("zzz\n".getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this, "Save OK!", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            Log.v("brad", e.toString());
        }

    }

    public void test5(View view) {
        try {
            FileInputStream fin = openFileInput("data.txt");
            InputStreamReader ir = new InputStreamReader(fin);
            BufferedReader reader = new BufferedReader(ir);
            String line = null;
            while ( (line = reader.readLine()) != null){
                Log.v("brad", line);
            }

            int data;
            while ((data = fin.read()) != -1){
                Log.v("brad", String.valueOf((char)data));
            }
            fin.close();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            Log.v("brad", e.toString());
        }
    }



}
