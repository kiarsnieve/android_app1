package com.example.kiarsnieve.ball;

/**
 * Created by kiarsnieve on 2017/02/20.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Block extends View{
    int x, y, width, height, life, lasttouch;
    Paint paint;

    public Block(Context context){
        super(context);
        x = 5;
        y = 5;
        width = 105;
        height = 105;
        life = 1;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
    }
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawRect(x, y, width, height, paint);
    }
}