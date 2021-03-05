package com.example.test.EntityClass;
import java.util.ArrayList;
public class Player extends People{
    public Player(){
        this.hands=new Hands();
        this.score=new Score();

    }
    //选牌，要与控制类结合，控制类要把选中牌的序号传入该方法
    @Override
    public void selectCards(int...number){
        this.hands.selectCards(number);
    }
    //出牌
    @Override
    public void showCards(){
        this.hands.showCards();
    }
    public void clearSelected(){
        this.hands.clearSelected();
    }
}