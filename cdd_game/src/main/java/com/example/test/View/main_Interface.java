package com.example.test.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.test.R;
import com.example.test.wificonnet.Cilent;
import com.example.test.wificonnet.Server;
import com.example.test.wificonnet.Tag;

/**
 * Created by HP on 2020/6/21.
 */
public class main_Interface extends BaseActivity {
    Intent str,set,help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interface);
    }

        public void Click(View v) {
            switch (v.getId()){
                //开始游戏按钮
                case R.id.start:
                    str=new Intent(main_Interface.this,PlayingActivity.class);//开始游戏按钮可跳转到游戏界面
                    startActivity(str);
                    break;
                    //游戏设置按钮
                case R.id.setting:
                    set=new Intent(main_Interface.this,Settings.class);//游戏设置按钮可跳转到游戏设置界面
                    startActivity(set);
                    break;
                    //帮助按钮
                case R.id.help:
                    help=new Intent(main_Interface.this,help.class);//游戏设置按钮可跳转到游戏设置界面
                    startActivity(help);
                    break;
                    //创建房间按钮
                case R.id.create:
                    Server.myServer.startServer();
                    Server.myServer.flag=true;

                    str=new Intent(main_Interface.this,PlayingActivity.class);//开始游戏按钮可跳转到游戏界面
                    startActivity(str);

                    break;
                    //加入房间按钮
                case R.id.join:
                    Cilent.myCilent.connet();
                    Cilent.myCilent.flag=true;
                    Cilent.myCilent.send(Tag.Ready+"");

                    str=new Intent(main_Interface.this,PlayingActivity.class);//开始游戏按钮可跳转到游戏界面
                    startActivity(str);

                    break;
            }
        }
}
