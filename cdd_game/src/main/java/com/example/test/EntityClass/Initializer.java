package com.example.test.EntityClass;
public class Initializer{
    public BgmLibrary bgmLibrary;
    public Robot createRobot(String nickName,int picture){
        Robot temp=new Robot(nickName,picture);
        return temp;
    }
    public Bgm selectBgm(int number){
        return this.bgmLibrary.selectBgm(number);
    }
}