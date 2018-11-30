package tw.brad.mytimertask;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    private Bitmap ball;
    private Resources res;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        res = context.getResources();
        ball = BitmapFactory.decodeResource(res, R.drawable.ball);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(ball, 100, 100, null);

    }
}
