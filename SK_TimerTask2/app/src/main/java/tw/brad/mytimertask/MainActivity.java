package tw.brad.mytimertask;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Timer timer;
    private int i;
    private MyTask myTask;
    private TextView counter;
    private UIHandler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uiHandler = new UIHandler();
        counter = findViewById(R.id.counter);

        timer = new Timer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (timer != null){
            timer.cancel();
            timer.purge();
            timer = null;
        }


    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            i++;
            Log.v("brad", "i = " + i);
            //counter.setText("i = " + i);
            Message message = new Message();
            Bundle data = new Bundle();
            data.putInt("i", i);
            message.setData(data);
            uiHandler.sendMessage(message);
        }
    }

    public void test1(View view) {
        myTask = new MyTask();
        timer.schedule(myTask, 1000, 1*10);
    }

    public void test2(View view) {
        if (myTask != null){
            myTask.cancel();
            myTask = null;
        }
    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int i = msg.getData().getInt("i");
            counter.setText("i = " + i);

        }
    }

}
