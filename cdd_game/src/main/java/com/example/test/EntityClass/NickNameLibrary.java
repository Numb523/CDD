package com.example.test.EntityClass;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class NickNameLibrary{
    NickNameLibrary(){
        nickNameList=new ArrayList<String>();
    }
    //这两个列表的编号是一一对应的，昵称列表第一个对应头像编号列表第一个
    public ArrayList<String> nickNameList;//昵称列表

    public void givingNickName(String nickName){
        this.nickNameList.add(nickName);
    }//向昵称列表添加昵称

    public String selectNickName(int number){
        return this.nickNameList.get(number);
    }//根据编号获取昵称


    //打印函数方便检查，可以改
    public String toString(){
        String temp=new String();
        for(String s:nickNameList){
            temp+=s;
        }
        return temp;
    }
}