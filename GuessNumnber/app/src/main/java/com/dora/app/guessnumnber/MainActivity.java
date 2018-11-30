package com.dora.app.guessnumnber;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private LinkedList<Integer> answer = new LinkedList<>(); //有順序性
    private int[] inputRes =
            {R.id.main_input1,R.id.main_input2,R.id.main_input3,R.id.main_input4};
    private TextView[] input = new TextView[4];

    private int[] numberRes = {R.id.main_btn_0,R.id.main_btn_1,R.id.main_btn_2,
            R.id.main_btn_3,R.id.main_btn_4,R.id.main_btn_5,R.id.main_btn_6,
            R.id.main_btn_7,R.id.main_btn_8,R.id.main_btn_9};
    private View[] btnNumber = new View[10];

    private int inputPoint;
    private LinkedList<Integer> inputValue = new LinkedList<>(); // 輸入數值陣列

    private ListView listView;
    private SimpleAdapter adapter;
    private String[] from = {"order", "guess", "result"};
    private int[] to = {R.id.item_order, R.id.item_guess, R.id.item_result};
    private LinkedList<HashMap<String,String>> hist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initGame();
        initListView();
    }


    private void initView(){
        for (int i=0; i<inputRes.length; i++){
            input[i] = findViewById(inputRes[i]);
        }
        for (int i=0; i<numberRes.length; i++){
            btnNumber[i] = findViewById(numberRes[i]);
        }
    }

    private void initGame(){
        answer = createAnswer();
        clear(null);
    }

    private void initListView(){
        listView = findViewById(R.id.main_listview);
        hist = new LinkedList<>();
        adapter = new SimpleAdapter(this, hist, R.layout.item_round, from, to);
        listView.setAdapter(adapter);
    }

    private LinkedList<Integer> createAnswer(){
        LinkedList<Integer> ret = new LinkedList<>();
        HashSet<Integer> nums = new HashSet<>();
        while (nums.size()<4){
            nums.add((int)(Math.random()*10));
        }
        for (Integer i : nums){
            ret.add(i);
        }
        Collections.shuffle(ret);
        Log.d("brad", ret.toString());
        return ret;
    }


    public void inputNumber(View view) {
        if (inputPoint == 4) return;    // 此時只能 send or back or clear
        // 比對輸入鍵
        for (int i=0; i<btnNumber.length; i++){
            if (view == btnNumber[i]){
                // 輸入 i 鍵
                inputValue.set(inputPoint,i);
                input[inputPoint].setText("" + i);
                inputPoint++;
                btnNumber[i].setEnabled(false);
                break;
            }
        }
    }

    public void back(View view) {
        if (inputPoint == 0) return;

        inputPoint--;
        btnNumber[inputValue.get(inputPoint)].setEnabled(true);
        inputValue.set(inputPoint, -1);
        input[inputPoint].setText("");
    }

    public void clear(View view) {
        inputPoint = 0;
        inputValue.clear();
        for (int i=0; i<4; i++){
            inputValue.add(-1);
        }
        for (int i = 0; i < input.length; i++) {
            input[i].setText("");
        }
        for(int i = 0; i<btnNumber.length; i++){
            btnNumber[i].setEnabled(true);
        }
    }

    public void send(View view) {
        if (inputPoint != 4) return;

        int a, b; a = b = 0; String guess = "";
        for (int i=0; i<inputValue.size(); i++){
            guess += inputValue.get(i);
            if (inputValue.get(i).equals(answer.get(i))){
                a++;
            }else if (answer.contains(inputValue.get(i))){
                b++;
            }
        }
        Log.d("brad", a + "A" + b + "B");
        clear(null);

        //Toast.makeText(this, a + "A" + b + "B", Toast.LENGTH_SHORT).show();

        HashMap<String,String> row = new HashMap<>();
        row.put(from[0], "" + (hist.size()+1));
        row.put(from[1], guess);
        row.put(from[2], a + "A" + b + "B");
        hist.addFirst(row);
        //hist.add(row);
        adapter.notifyDataSetChanged();
        //listView.smoothScrollToPosition(hist.size()-1);
        if (a == 4){
            // winner
            displayResult(true);
        }else if(hist.size() == 10){
            // loser
            displayResult(false);
        }
    }

    private void displayResult(boolean isWinner){
        AlertDialog alertDialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("遊戲結果");

        StringBuffer ansString = new StringBuffer();
        for (int i=0; i<answer.size();i++) ansString.append(answer.get(i));

        builder.setMessage(isWinner?"完全正確":"挑戰失敗\n" + "答案:" + ansString);
        builder.setPositiveButton("開新局", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                replay(null);
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }


    public void replay(View view) {
        initGame();
        hist.clear();
        adapter.notifyDataSetChanged();
    }
}
