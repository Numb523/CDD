package com.example.test.EntityClass;


public class Card implements Comparable<Card> {
    Card(){
        this.setPattern(Pattern.Diamond);
        this.setFigure(Figure.Three);
        isSelected=false;
    }
    Card(Pattern pattern,Figure figure){
        this.setPattern(pattern);
        this.setFigure(figure);
        isSelected=false;
    }
    private Pattern pattern;//花色
    private Figure figure;//点数
    public Boolean isSelected;
    public Figure getFigure() {
        return figure;
    }
    public Pattern getPattern() {
        return pattern;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
    public int compareTo(Card card) {//比较接口，先比较点数，点数相同再比较花色，不考虑同牌比较
        if(this.getFigure().ordinal() < card.getFigure().ordinal())
            return -1;
        else if(this.getFigure().ordinal() > card.getFigure().ordinal())
            return 1;
        else{
            if(this.getPattern().ordinal() < card.getPattern().ordinal())
                return -1;
            else
                return 1;
        }
    }
    public String toString(){
        return this.pattern.toString()+" "+this.figure.toString();
    }
}