package com.example.test.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.test.EntityClass.Register;
import com.example.test.R;

import java.io.File;


public class register extends Activity {
    ImageButton p[]=new ImageButton[9];//头像
    Button ensure;//注册确定按钮
    EditText reg_name;
    String message_Name;
    boolean choose_p=false;
    boolean edit=false;
    Intent en_r;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);



        p[0]=(ImageButton)findViewById(R.id.p1);
        p[1]=(ImageButton)findViewById(R.id.p2);
        p[2]=(ImageButton)findViewById(R.id.p3);
        p[3]=(ImageButton)findViewById(R.id.p4);
        p[4]=(ImageButton)findViewById(R.id.p5);
        p[5]=(ImageButton)findViewById(R.id.p6);
        p[6]=(ImageButton)findViewById(R.id.p7);
        p[7]=(ImageButton)findViewById(R.id.p8);
        p[8]=(ImageButton)findViewById(R.id.p9);
        p[0].setOnClickListener(new select());//按钮监听
        p[1].setOnClickListener(new select());//按钮监听
        p[2].setOnClickListener(new select());//按钮监听
        p[3].setOnClickListener(new select());//按钮监听
        p[4].setOnClickListener(new select());//按钮监听
        p[5].setOnClickListener(new select());//按钮监听
        p[6].setOnClickListener(new select());//按钮监听
        p[7].setOnClickListener(new select());//按钮监听
        p[8].setOnClickListener(new select());//按钮监听
        reg_name=(EditText)findViewById(R.id.register_name);
        ensure=(Button)findViewById(R.id.ensure_reg);
        ensure.setOnClickListener(new ensure_r());

    }
    private class select implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.p1:
                    after_choose(p[0]);
                    setI(0);
                    break;
                case R.id.p2:
                    after_choose(p[1]);
                    setI(1);
                    break;
                case R.id.p3:
                    after_choose(p[2]);
                    setI(2);
                    break;
                case R.id.p4:
                    after_choose(p[3]);
                    setI(3);
                    break;
                case R.id.p5:
                    after_choose(p[4]);
                    setI(4);
                    break;
                case R.id.p6:
                    after_choose(p[5]);
                    setI(5);
                    break;
                case R.id.p7:
                    after_choose(p[6]);
                    setI(6);
                    break;
                case R.id.p8:
                    after_choose(p[7]);
                    setI(7);
                    break;
                case R.id.p9:
                    after_choose(p[8]);
                    setI(8);
                    break;

            }
        }
    }
    private void after_choose(ImageButton im){
        AlertDialog.Builder alert1=new AlertDialog.Builder(register.this);
        alert1.setTitle("头像选择");
        alert1.setMessage("确定选择这个头像？");
        //确定
        alert1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choose_p=true;
            }
        });
        //取消
        alert1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert1.show();
    }
    private class ensure_r implements View.OnClickListener {
        @Override
        public void onClick(View v) {
                    AlertDialog.Builder alert2=new AlertDialog.Builder(register.this);
                    alert2.setTitle("注册");
                    alert2.setMessage("确定注册？");
                    //确定
            alert2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    message_Name=reg_name.getText().toString();
                    String path=register.this.getFilesDir().getPath()+ "/test1.txt";
                    Register.file=new File(path);
                    edit=Register.regist(message_Name,i);
                    AlertDialog.Builder alert3 = new AlertDialog.Builder(register.this);
                    if (choose_p == true && edit == true&&message_Name.trim().length()!=0) {
                        en_r = new Intent(register.this, login.class);
                        startActivity(en_r);
                    }
                    else
                    {
                        alert3.setTitle("注册");
                        alert3.setMessage("昵称或头像未选择/账号已存在");
                        alert3.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alert3.show();
                    }

                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
                    //取消
            alert2.show();
        }
    }
    public void setI(int n){i=n;}
}
