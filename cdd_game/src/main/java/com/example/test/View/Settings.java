package com.example.test.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.EntityClass.Register;
import com.example.test.R;

import java.util.ArrayList;
import java.util.List;

public class Settings extends BaseActivity {
    SeekBar bar1;//游戏音量滑动条
    SeekBar bar2;//游戏音效滑动条
    TextView text1;//游戏音量大小
    TextView text2;//游戏音效大小
    ImageButton return_main;//返回按钮
    Spinner name_spinner;//昵称下拉框
    Intent r;//跳转意图
    static ArrayList<String>name_list;//下拉框内容
    ArrayAdapter<String>arr_Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //游戏音量，滑动条1
        bar1 = (SeekBar) findViewById(R.id.music_bar);
        text1 = (TextView) findViewById(R.id.music_process);
        bar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                text1.setText( Integer.toString(progress));//滑动条当前值传递给文本进行显示
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                Log.e("------------", "开始滑动！");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                Log.e("------------", "停止滑动！");
            }
        });
        //游戏音效，滑动条2
        bar2 = (SeekBar) findViewById(R.id.sound_effect_bar);
        text2 = (TextView) findViewById(R.id.sound_prrocess);
        bar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                //滑动条当前值传递给文本进行显示，不过目前没有实现滑动条当前值的保存，所以跳转界面后滑动条当前值会恢复到默认值
                text2.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                Log.e("------------", "开始滑动！");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                Log.e("------------", "停止滑动！");
            }
        });
        //返回按钮
        return_main=(ImageButton)findViewById(R.id.return_main);
        return_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r=new Intent(Settings.this,main_Interface.class);
                startActivity(r);
            }
        });
        name_spinner=(Spinner)findViewById(R.id.name_select);
        name_list=Register.getNickNameList();
        arr_Adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,name_list);
        arr_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        name_spinner.setAdapter(arr_Adapter);
        name_spinner.setSelection(0);
    }
}
