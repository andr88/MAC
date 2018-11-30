package com.dora.app.layouttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
    private String answer;
    private EditText input;
    private TextView hist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);

        answer = createAnswer();
        input = findViewById(R.id.main_input);
        hist = findViewById(R.id.main_hist);
    }

    private String createAnswer(){
        //TODO
        TreeSet<Integer> set = new TreeSet<>(); //TreeSet=>不重複的資料結構
        while (set.size()<3){
            set.add( (int)(Math.random()*10) );
        }
        String ans = "";
        for(Integer s : set) {
             ans = ans + "" + s;
        }
        Log.v("brad:",ans);
        return ans;
    }

    private  String checkAB(String guess){
        int A, B; A = B = 0;
        for (int i=0 ; i<answer.length(); i++){
            if (guess.charAt(i) == answer.charAt(i)){
                A++;
            } else if (answer.indexOf(guess.charAt(i)) !=- 1){
                B++;
            }
        }
        return  A + "A" + B + "B";
    }

    public void guess(View view) {
        String inputText = input.getText().toString();
        hist.append("\n"+checkAB(inputText));
        Log.v("brad:",inputText);
    }
}
