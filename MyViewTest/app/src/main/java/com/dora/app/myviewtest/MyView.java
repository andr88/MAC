package com.dora.app.myviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;

public class MyView extends View {
    private LinkedList<LinkedList<HashMap<String, Float>>> lines, recycler;
    private View rootView;
    private int lineWidth;
    private MainActivity mainActivity;

    private TextView seekBarValue;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mainActivity = (MainActivity)context;

        setBackgroundColor(Color.rgb(200,200,200));
        lines = new LinkedList<>();
        recycler = new LinkedList<>();

        //rootView = inflate((context, R.layout.activity_main, null);
        //seekBarValue = rootView.findViewById(R.id.seekBarValue);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
//        int sz = 5;
//        if(seekBarValue!=null){
//            sz = Integer.parseInt(seekBarValue.getText().toString());
//            Log.v("brad", seekBarValue.getText().toString());
//        }
        paint.setStrokeWidth( lineWidth );

        for (LinkedList<HashMap<String, Float>> line :lines){
            for (int k=1; k<line.size(); k++){
                if (k==1){
                    paint.setStrokeWidth( line.get(k-1).get("w") );
                } else {
                    HashMap<String, Float> p0 = line.get(k-1);
                    HashMap<String, Float> p1 = line.get(k);
                    canvas.drawLine(p0.get("x"), p0.get("y"),
                            p1.get("x"), p1.get("y"), paint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float ex = event.getX();
        float ey = event.getY();
        //Log.v("brad", ex + " x " + ey);

//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                addNewLine();
//            case MotionEvent.ACTION_MOVE:
//                addNewPoint(ex, ey);
//                break;
//        }

        if (event.getAction()==MotionEvent.ACTION_DOWN){
            addNewLine();
        }
        if (event.getAction()==MotionEvent.ACTION_MOVE || event.getAction()==MotionEvent.ACTION_DOWN){
            addNewPoint(ex, ey);
        }
        if (event.getAction()==MotionEvent.ACTION_UP){
            mainActivity.showLineNum(lines.size());
        }

        invalidate(); //repaint => onDraw;

        return true; //super.onTouchEvent(event);
    }

    private void addNewLine(){
        LinkedList<HashMap<String, Float>> line = new LinkedList<>();
        HashMap<String, Float> data = new HashMap<>();
        data.put("w", (float)lineWidth);
        line.add(data);
        lines.add(line);
        recycler.clear();
    }

    private void addNewPoint(float x, float y){
        HashMap<String, Float> point = new HashMap<>();
        point.put("x", x);
        point.put("y", y);
        lines.getLast().add(point);
    }

    public void clear(){
        lines.clear();
        invalidate();
    }

    public void undo(){
        if (lines.size()>0){
        recycler.add(lines.removeLast());
        invalidate();
        }
    }

    public void redo(){
        if (recycler.size()>0){
            lines.add(recycler.removeLast());
            invalidate();
        }
    }

    public void chlineWidth(int newWidth){
        lineWidth = newWidth;
        invalidate();
    }

}
