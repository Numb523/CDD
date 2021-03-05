package com.example.test.EntityClass;
import java.util.ArrayList;
public abstract class CardType{
    CardType(){
        this.cards=new ArrayList<Card>();
    }
    public ArrayList<Card> cards;//用于存放牌（不论几张，用size()判断）
    @Override
    public String toString(){
        String temp=new String();
        for(Card c:cards)
            temp+=c.toString()+", ";
        return temp;
    }
}