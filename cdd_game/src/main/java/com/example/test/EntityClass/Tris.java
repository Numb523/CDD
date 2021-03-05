package com.example.test.EntityClass;
import java.util.ArrayList;
public class Tris extends CardType implements Comparable<Tris> {
    Tris(ArrayList<Card> cards){
        if(isLegal(cards)) {//判断是否合法
            this.cards=new ArrayList<Card>(cards);
        }
    }
    Tris(){
        super();
    }
    public static boolean isLegal(ArrayList<Card> cards){
        if(cards.size()!=3)
            return false;
        if(cards.get(0).getFigure()==cards.get(1).getFigure()&&cards.get(1).getFigure()==cards.get(2).getFigure())
            return true;
        else
            return false;
    }
    //public ArrayList<Card> cards;//用于存放三张
    //三张的比较不存在同点数的可能，不需要排序
    public int compareTo(Tris tris) {
        return this.cards.get(0).compareTo(tris.cards.get(0));
    }
    public String toString(){
        String temp=new String();
        temp+="Tris: ";
        for(Card c:cards)
            temp+=c.toString();
        return temp;
    }
}