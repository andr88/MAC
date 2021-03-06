package com.dora.app.mynetwork3a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;
    private TextView lott;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        lott = findViewById(R.id.lott);
    }


    public void test1(View view) {
        StringRequest request = new StringRequest(Request.Method.GET,
                "https://www.sakura.com.tw",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("brad", response);
                    }
                },
                null);

        queue.add(request);
    }


    public void test2(View view) {
        StringRequest request = new StringRequest(Request.Method.GET,
                "https://www.bradchao.com/v2/apptest/getTest1.php?account=brad&passwd=1234",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("brad", response);
                        parseJSON(response);
                    }
                },
                null);

        queue.add(request);
    }

    private void parseJSON(String json){
        try {
            JSONObject root = new JSONObject(json);
            String result = root.getString("result");
            String account = root.getString("account");
            String passwd = root.getString("passwd");
            String lotteryString = root.getString("lottery");
            lott.setText(lotteryString);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void test3(View view) {
        StringRequest request = new StringRequest(Request.Method.POST,
                "https://www.bradchao.com/v2/apptest/postTest1.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("brad", response);
                    }
                },
                null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("account", "brad");
                params.put("passwd", "4567");
                return params;
            }
        };

        queue.add(request);
    }


    public void test4(View view) {
        StringRequest request = new StringRequest(Request.Method.GET,
                "https://www1.sakura.com.tw/sakura/rstcom.nsf/org.xsp/per?u=987658",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.v("brad", response);
                        parseJSON2(response);
                    }
                },
                null){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String data = "brad:bradpass!";
                String data2 = Base64.encodeToString(data.getBytes(), Base64.NO_WRAP);
                HashMap<String,String> ret = new HashMap();
                ret.put("Authorization", "Basic " + data2);
                ret.put("Content-Type", "application/json; charset=UTF-8");
                return ret;
            }
        };

        queue.add(request);
    }

    private void parseJSON2(String json){
        try {
            JSONObject root = new JSONObject(json);
            String Auther = root.getString("Auther");
            String CreatorDepName0 = root.getString( "CreatorDepName0");
            Log.v("brad", Auther+" "+CreatorDepName0);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.v("brad", e.toString());
        }
    }



}
