package com.example.test.EntityClass;
import java.util.ArrayList;
public class Single extends CardType implements Comparable<Single> {
    Single(ArrayList<Card> cards){
        if(isLegal(cards)){
            this.cards=new ArrayList<Card>(cards);
        }
    }
    Single(){
        super();
    }
    //public ArrayList<Card> cards;//用于存放单张
    public static boolean isLegal(ArrayList<Card> cards){
        if(cards.size()!=1)
            return false;
        else
            return true;
    }
    public int compareTo(Single single) {
        return this.cards.get(0).compareTo(single.cards.get(0));
    }
    public String toString(){
        String temp=new String();
        temp+="Single: ";
        for(Card c:cards)
            temp+=c.toString();
        return temp;
    }
}