package com.example.test.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.test.Control.GameControl;
import com.example.test.R;
import com.example.test.wificonnet.Cilent;
import com.example.test.wificonnet.Server;
import com.example.test.wificonnet.Tag;

public class gameView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    boolean threadFlag = true;
    Context context;
    SurfaceHolder holder;
    Canvas canvas;
    Bitmap backgroundBitmap;
    GameControl gc ;


    Thread gameThread = new Thread(){
        @SuppressLint("WrongCall")
        @Override
        public void run() {
            holder = getHolder();
            try {
                canvas = holder.lockCanvas();
                onDraw(canvas);
            } finally {
                holder.unlockCanvasAndPost(canvas);
            }

            if(Cilent.myCilent.flag)
            {
                //Cilent.myCilent.send(Tag.nickName+" "+Cilent.myCilent.number+" "+GameControl.players[0].nickName);
                //Cilent.myCilent.send(Tag.picture+" "+ Cilent.myCilent.number+" "+ GameControl.players[0].picture);

                while (!Cilent.myCilent.start)
                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(Server.myServer.flag)
            {
                while (Server.socketList.size()<3)
                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Server.myServer.sendall(Tag.start+"");

            }


            while (threadFlag) {
                gc.gameLogic();
                try {
                    canvas = holder.lockCanvas();
                    onDraw(canvas);
                } finally {
                    holder.unlockCanvasAndPost(canvas);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public gameView(Context context){
        super(context);
        this.context = context;
        gc = new GameControl(context);
        this.getHolder().addCallback(this);
        this.setOnTouchListener(this);
        backgroundBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.playing_bg);
    }

    @Override

    protected void onDraw(Canvas canvas) {
        //绘制背景
        Rect src = new Rect();
        Rect des = new Rect();
        src.set(0, 0, backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
        des.set(0, 0, PlayingActivity.SCREEN_WIDTH, PlayingActivity.SCREEN_HEIGHT);
        canvas.drawBitmap(backgroundBitmap, src, des, null);
        //绘制牌
        gc.controlPaint(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        threadFlag = true;
        gameThread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        threadFlag = false;
        boolean retry = true;
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    public boolean onTouch(View v, MotionEvent event){
        if (event.getAction() != MotionEvent.ACTION_UP) {
            return true;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        System.out.println("ACTION_UP -- " + (event.getAction() == MotionEvent.ACTION_UP));
        gc.onTuch(x, y);
        // threadFlag=!threadFlag;
        return true;
    }



}
