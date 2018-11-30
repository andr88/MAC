package com.dora.app.listviewtest;

import android.content.res.Resources;
import android.security.keystore.StrongBoxUnavailableException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity {
    private ListView listview;
    private SimpleAdapter simAdp;
    private int[] to = {R.id.item_title, R.id.item_date};
    private String[] from = {"brad", "sakura"};
    private LinkedList<HashMap<String,String>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = findViewById(R.id.listview);
        initlistview();
    }

    private  void  initlistview(){
        data = new LinkedList<>();

        for (int i=0; i<100; i++){
            HashMap<String, String> row = new HashMap<>();
            row.put(from[0], "事件"+i);
            row.put(from[1], "2018-11-02");
            data.add(row);
        }

        simAdp = new SimpleAdapter(this, data, R.layout.item, from, to);
        listview.setAdapter(simAdp);

        //短按編輯或新增
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                doItem(position);
            }
        });

        //長按刪除
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "item"+position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }

    private  void doItem(int i){
        Toast.makeText(this, data.get(i).get(from[0]), Toast.LENGTH_SHORT).show();

    }

    public void addOnClick(View view) {
        HashMap<String,String> newdata = new HashMap<>();
        newdata.put(from[0], "新事件");
        newdata.put(from[1], "2018-11-02");
        data.add(0, newdata);
        simAdp.notifyDataSetChanged();
    }

    public void delOnClick(View view) {
        data.remove(0);
        simAdp.notifyDataSetChanged();
    }
}
