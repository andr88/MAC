package com.dora.app.myactivitytest;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private long lastTime = 0;
    private Button test3, test4;
    private MainApp mainApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test3 = findViewById(R.id.test3btn);
        test4 = findViewById(R.id.test4btn);

        test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("brad", "SetOnClick");
            }
        });
        mainApp = (MainApp) getApplication();
        mainApp.lottery = (int)(Math.random()*48+1);
        mainApp.username = "mary";
    }

    public void test1(View view) {
        Intent intent = new Intent(this, Page2Activity.class);
        intent.putExtra("name","Brad");
        intent.putExtra("sound", false);
        intent.putExtra("stage", 4);
        //startActivity(intent);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("brad", "OnActResult");

        switch(requestCode){   //整數或字串, byte short int, string
            case 1: case 5: case 6:
                afterPage2(requestCode, data);
                break;
            case 2:
                afterPage3(requestCode, data);
                break;

        }
    }

    private  void afterPage2(int rcode, Intent intent){
        int k1 = intent.getIntExtra("k1", -1);
        int k2 = intent.getIntExtra("k2", -1);
        Log.v("brad", rcode + ": k1:" + k1 + " k2:" + k2);
    }

    private void afterPage3(int rcode, Intent intent){
        int m1 = intent.getIntExtra("m1", -1);
        int m2 = intent.getIntExtra("m2", -1);
        Log.v("brad", rcode + ": m1:" + m1 + " m2:" + m2);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void finish() {
        if (System.currentTimeMillis()-lastTime > 3*1000) {
            lastTime = System.currentTimeMillis();
            Toast.makeText(this, "Press back one more to exit!",
                    Toast.LENGTH_SHORT).show();
        } else {
            super.finish();
        }
    }

    public void test34(View view) {
        Log.v("brad", "click_34");
        if (view == test3){
            Log.v("brad", "is btn3");
        } else if (view == test4){
            Log.v("brad", "is btn4");
        }
    }

    public void GoP3(View view) {
        Intent intent = new Intent(this, page3.class);
        //startActivity(intent);
        startActivityForResult(intent, 1);
    }
}
