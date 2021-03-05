package com.example.test.EntityClass;
public class Score{
    Score(){
        point=0;
    }
    public int point;

    public void addScore(int point) {
        this.point += point;
    }
}