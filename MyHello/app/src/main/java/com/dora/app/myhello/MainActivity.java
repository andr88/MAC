package com.dora.app.myhello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.TreeSet;

/**
 * JavaDoc
 */

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.mesg);
    }

    public void test1(View view) {
        TreeSet<Integer> set = new TreeSet<>(); //TreeSet=>不重複的資料結構
        while (set.size()<6){
            set.add( (int)(Math.random()*49+1) );
        }
        //int lo = (int)(Math.random()*49+1);
        //textView.setText("" + lo);
        textView.setText( set.toString() );
    }
}
