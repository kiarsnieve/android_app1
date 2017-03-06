package com.example.kiarsnieve.ball;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.view.MotionEvent;


public class MainActivity extends Activity implements Runnable {
    Ball ball;
    Button button;
    //Block block;
    Handler handler;
    RelativeLayout relativeLayout;
    int width, height;
    int dx = 10, dy = 20, time = 16;
    int i, iy, step=0;
    float touch_x;
    float touch_y;
    float touch_time;
    int color_black = 1;
    int click_flg = 0;
    int click_action;


    //ブロックパラメータ
    int dan = 3; //ブロックの段数
    int block_group_margin_top = 100; //上の隙間


    Block block[][]=new Block[dan][10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d("" + "aaaaaaaaaaaaaa" , "");
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        relativeLayout = new RelativeLayout(this);
        relativeLayout.setBackgroundColor(Color.GREEN);
        setContentView(relativeLayout);

        handler = new Handler();
        handler.postDelayed(this, time);

        WindowManager windowManager =
                (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        width = point.x;
        height = point.y;

        //ボール生成
        ball = new Ball(this);
        ball.x = width / 2;
        ball.y = height / 2;
        relativeLayout.addView(ball);

        //ボタン生成
        button = new Button(this);
//        ball.x = width / 2;
//        ball.y = height / 2;
        relativeLayout.addView(button);

        //ブロック生成
        for(iy=0;iy<dan;iy++) {
            for (i = 0; i < 10; i++) {
                block[iy][i] = new Block(this);
                block[iy][i].x = i * 105 + 10;
                block[iy][i].width = (i + 1) * (105);
                block[iy][i].life = 1;
                relativeLayout.addView(block[iy][i]);
            }
        }
    }


    public boolean onTouchEvent(MotionEvent event) {
        Log.d("TouchEvent", "X:" + event.getX() + ",time:" + event.getAction());
        touch_x = event.getX();
        touch_y = event.getY();
        touch_time = event.getEventTime();
        click_flg = 1;
        click_action = event.getAction();
//        if(color_black == 1){
//            color_black =1;
//        }else{
//            color_black =0;
//        }
        return true;
    }


    @Override
    public void run() {

        step++;

        //推進
        ball.x += dx;
        ball.y += dy;

        //タッチ処理
        if((touch_x - button.x) * (touch_x - button.x) + (touch_y - button.y) * (touch_y - button.y) < button.radius * button.radius){
            if(click_flg == 1){
                //Log.d("boke", "ahoaho");
                relativeLayout.removeView(button);
                if(click_action == 0 || click_action == 2){
                    button.paint.setColor(Color.RED);
                }else if(click_action == 1){
                    button.paint.setColor(Color.BLUE);
                }
                button.paint.setStyle(Paint.Style.FILL);
                relativeLayout.addView(button);
                //touch_x = 0;
                //touch_y = 0;
                click_flg = 0;
            }

        }else{
            if(click_flg == 1){
                //Log.d("saru", "sarusaru");
                relativeLayout.removeView(button);
                button.paint.setColor(Color.BLUE);
                button.paint.setStyle(Paint.Style.FILL);
                relativeLayout.addView(button);
                touch_x = 0;
                touch_y = 0;
                click_flg = 0;
            }
        }


        //ブロック接触判定
        for(iy=0;iy<dan;iy++) {
            for (i = 0; i < 10; i++) {
                block[iy][i].y = iy * 100 + 5 + (iy * 5);
                block[iy][i].height = (iy + 1) * 100 + 5;
                if (block[iy][i].life >= 1) {

                    //ブロックアンダー判定
                    if (ball.y - ball.radius <= block[iy][i].height && ball.y - ball.radius >= block[iy][i].height - 40 ) {
                        if (block[iy][i].x < ball.x && block[iy][i].width > ball.x) {
                            if ((step - block[iy][i].lasttouch) > 3) {
                                block[iy][i].lasttouch = step;
                                dy = -dy;
                                block[iy][i].life--;
                                if (block[iy][i].life == 0) {
                                    relativeLayout.removeView(block[iy][i]);
                                }
                            }
                            break;
                        }
                    }

                    //ブロックレフト判定
                    if (ball.y <= block[iy][i].height && ball.y >= block[iy][i].height - 100 ) {
                        if (block[iy][i].x < ball.x - ball.radius && block[iy][i].width - 50 > ball.x) {
                            if ((step - block[iy][i].lasttouch) > 3) {
                                block[iy][i].lasttouch = step;
                                dx = -dx;
                                block[iy][i].life--;
                                if (block[iy][i].life == 0) {
                                    relativeLayout.removeView(block[iy][i]);
                                }
                            }
                            break;
                        }
                    }




                }
            }
        }

        //左右壁反転
        if (ball.x <= ball.radius) {
            ball.x = ball.radius;
            dx = -dx;
        } else if (ball.x >= width - ball.radius) {
            ball.x = width - ball.radius;
            dx = -dx;
        }

        //上下壁反転
        if (ball.y <= ball.radius) {
            ball.y = ball.radius;
            dy = -dy;
        } else if (ball.y >= height - ball.radius) {
            ball.y = height - ball.radius;
            dy = -dy;
        }

        ball.invalidate();
        handler.postDelayed(this, time);
    }

    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(this);
    }
}