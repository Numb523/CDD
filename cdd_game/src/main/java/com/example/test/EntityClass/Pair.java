package com.example.test.EntityClass;
import java.util.ArrayList;
import java.util.Collections;
public class Pair extends CardType implements Comparable<Pair> {
    Pair(ArrayList<Card> cards) {
        if(isLegal(cards)) {//判断是否合法
            this.cards=new ArrayList<Card>(cards);
        }
    }
    Pair(){
        super();
    }
    //判断是否合法，给外面用的
    public static boolean isLegal(ArrayList<Card> cards){
        if(cards.size()!=2)
            return false;
        if(cards.get(0).getFigure()==cards.get(1).getFigure())
            return true;
        else
            return false;
    }

    //public ArrayList<Card> cards;//用于存放对牌
    //对子有可能会出现同点数，需要先排序，然后取较大的一张进行比较
    @Override
    public int compareTo(Pair pair) {
        Collections.sort(this.cards);
        Collections.sort(pair.cards);
        return this.cards.get(1).compareTo(pair.cards.get(1));
    }
    public String toString(){
        String temp=new String();
        temp+="Pair: ";
        for(Card c:cards)
            temp+=c.toString();
        return temp;
    }
}



