package com.example.test.EntityClass;
import java.util.ArrayList;
public class PictureLibrary{
    PictureLibrary(){
        pictureList=new ArrayList<Integer>();
    }
    public ArrayList<Integer> pictureList;
    public int selectPicture(int number){
        return this.pictureList.get(number);
    }
    public void givingPicutre(int pictureNumber){
        this.pictureList.add(pictureNumber);
    }//向头像编号列表添加头像
    public int selectPicutreNumber(int number){return this.pictureList.get(number);}//根据编号获取头像编号
}