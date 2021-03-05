package com.example.test.EntityClass;
import java.util.ArrayList;
import java.util.Collections;

public class Hands{
    Hands(){
        this.cards=new ArrayList<Card>();
        this.selectedCards=new ArrayList<Card>();
    }
    public ArrayList<Card> cards;
    public ArrayList<Card> selectedCards;
    public ArrayList<Card> getCards() {
        return cards;
    }
    //选牌，传入的参数是牌的序号
    public void selectCards(int...number){
        for(int i:number)
            if(i<13&&!this.selectedCards.contains(this.cards.get(i)))
                this.selectedCards.add(this.cards.get(i));
    }
    public void selectCards(){
        this.selectedCards.clear();
        for(Card c:this.cards)
            if(c.isSelected)
                this.selectedCards.add(c);
    }
    //出牌
    public void showCards(){
        this.cards.removeAll(selectedCards);//从手牌中移除已选择的牌，相当于出牌了
        this.selectedCards.clear();//清空已选择的牌
    }
    //重新选牌
    public void clearSelected(){
        this.selectedCards.clear();
    }
    public String toString(){
        String temp=new String();
        for(int i=0;i<this.cards.size();i++)
            temp+=(" "+i+this.cards.get(i).toString()+",");
        return temp;
    }
    public void Arrange(){
        Collections.sort(this.cards);
    }//整理手牌
}