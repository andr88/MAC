package com.dora.app.myactivitytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class page3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
    }

    @Override
    public void finish() {
        Intent intent3 = new Intent();
        intent3.putExtra("m1", 33);
        intent3.putExtra("m2", 44);
        setResult(223, intent3);

        super.finish();
    }

}
