package com.example.test.EntityClass;
import java.util.ArrayList;
public class BgmLibrary{
    public ArrayList<Bgm> bgmList;
    public Bgm selectBgm(int number){
        return this.bgmList.get(number);
    }
}