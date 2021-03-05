package com.example.test.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.test.EntityClass.Register;
import com.example.test.R;

import java.io.File;


/*继承BaseActivity实现横屏*/
public class login extends BaseActivity {

    Button login;
    Button register;
    Intent l,r;
    EditText e;
    String message;
    int ed=9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Register r=new Register();
        login =(Button)findViewById(R.id.login);
        register =(Button)findViewById(R.id.register);
       login.setOnClickListener(new ButtonListener());//按钮监听
       register.setOnClickListener(new ButtonListener());
       e=(EditText)findViewById(R.id.edit_name);
    }
    //可以同时实现多个按钮的监听事件
    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            switch (v.getId()){
                //登录按钮
                case R.id.login:
                    message=e.getText().toString();
                    String path=login.this.getFilesDir().getPath()+ "/test1.txt";
                    Register.file=new File(path);
                    ed=Register.login(message);
                    AlertDialog.Builder alert3 = new AlertDialog.Builder(login.this);
                    if ((ed>=0 && ed<=8)&&message.trim().length()!=0) {
                        l=new Intent(login.this,main_Interface.class);//登录跳转到主界面
                        startActivity(l);
                    }
                    else
                    {
                        alert3.setTitle("登录");
                        alert3.setMessage("昵称错误，请重新输入");
                        alert3.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alert3.show();
                    }
                    break;
                    //注册按钮
                case R.id.register:
                    r=new Intent(login.this,register.class);//跳到注册界面
                    startActivity(r);
                    break;
            }
        }
    }
}
