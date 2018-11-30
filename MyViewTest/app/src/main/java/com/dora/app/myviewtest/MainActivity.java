package com.dora.app.myviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.security.PublicKey;

public class MainActivity extends AppCompatActivity {
    private MyView myView;
    private SeekBar seekBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView = findViewById(R.id.myView);
        textView = findViewById(R.id.lineNumber);

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(16);
        seekBar.setProgress(8);
        myView.chlineWidth(8);

        final TextView seekBarValue = findViewById(R.id.seekBarValue);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue.setText(""+progress);
                myView.chlineWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void clear(View view) {
        myView.clear();
    }
    public void undo(View view) {
        myView.undo();
    }
    public void redo(View view) {
        myView.redo();
    }

    public void showLineNum(int sz){
        textView.setText(""+sz);
    }
}
