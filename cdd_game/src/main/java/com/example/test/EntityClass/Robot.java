package com.example.test.EntityClass;
import java.util.ArrayList;
public class Robot extends People{
    Robot(String nickName,int picture){
        this.nickName=nickName;
        this.picture=picture;
        this.hands=new Hands();
    }
    public Robot(){
        this.hands=new Hands();
        this.score=new Score();

    }
    //出牌
    @Override
    public void showCards(){
        this.hands.showCards();
    }
    //选牌
    @Override
    public void selectCards(int...number){
        this.hands.selectCards(number);
    }
}