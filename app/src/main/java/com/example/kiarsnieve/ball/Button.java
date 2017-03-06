package com.example.kiarsnieve.ball;

/**
 * Created by kiarsnieve on 2017/02/20.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Button extends View{
    int x, y, radius;
    char color;
    Paint paint;

    public Button(Context context){
        super(context);
        radius = 80;
        x = y = 500;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
    }
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawCircle(x, y, radius, paint);
    }
}