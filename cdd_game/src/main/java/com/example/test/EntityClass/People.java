package com.example.test.EntityClass;
public class People implements Comparable<People> {
    public Hands hands;
    public Score score;
    public String nickName;
    public int picture;
    public CardType cardType;//用于记录最后出的牌
    public void selectCards(int...number){
        this.hands.selectCards(number);
    }
    public void showCards(){
        this.hands.showCards();
    }
    public int compareTo(People people) {//通过分数进行比较
        return this.score.point-people.score.point;
    }
}