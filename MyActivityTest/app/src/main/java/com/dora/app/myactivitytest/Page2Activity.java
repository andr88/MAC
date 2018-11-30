package com.dora.app.myactivitytest;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Page2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        Intent intent = getIntent();
        int stage = intent.getIntExtra("stage", -1);
        String name = intent.getStringExtra("name");
        boolean sound = intent.getBooleanExtra("sound", true);
        Log.v("brad", "name="+name);

    }

    @Override
    public void onBackPressed() {
        Log.v("brad", "B4OnBackPressed");
        //super.onBackPressed();
        //Log.v("brad", "OnBackPressed");
    }

    @Override
    public void finish() {
        Intent intent2 = new Intent();
        intent2.putExtra("k1", 11);
        intent2.putExtra("k2", 22);
        setResult(123, intent2);
        Log.v("brad", "finish");
        super.finish();
        Log.v("brad", "finish");
    }

    public void test2(View view) {
        AlertDialog alertDialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("訊息");
        builder.setMessage("確定離開?");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("否", null);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
    }
}
