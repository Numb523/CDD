package com.example.test.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class PlayingActivity extends AppCompatActivity {

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static double SCALE_VERTICAL;
    public static double SCALE_HORIAONTAL;
    public static Handler handler;
    public static gameView gv;
    public final static int PASUE = 0;
    private Intent intent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Playing","Start");

        //横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //获取屏幕大小
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if (SCREEN_HEIGHT > SCREEN_WIDTH) {
            int temp = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = temp;
        }
        System.out.println(SCREEN_HEIGHT + "X" + SCREEN_WIDTH);
        SCALE_VERTICAL = SCREEN_HEIGHT / 320.0;
        SCALE_HORIAONTAL = SCREEN_WIDTH / 480.0;
        System.out.println(SCALE_VERTICAL + "X" + SCALE_HORIAONTAL);

        gv = new gameView(this.getApplicationContext());
        setContentView(gv);
        //背景音乐
        /*
        intent = new Intent(PlayingActivity.this,MusicService.class);
        String action = MusicService.ACTION_MUSIC;
        intent.setAction(action);
        startService(intent);
*/
        handler = new Handler(){

            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case 0:
                        Intent intent = new Intent(PlayingActivity.this, PauseActivity.class);
                        startActivity(intent);

                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(intent != null){
            stopService(intent);
        }
    }
}
