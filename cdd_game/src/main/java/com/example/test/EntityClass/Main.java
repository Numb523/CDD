package com.example.test.EntityClass;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


public class Main {
    public static void main(String args[]) {


        //Register register=new Register();
        //register.clearInfoForFile("test1");
        //register.recordInfo("xiaoming",1);
        //register.recordInfo("xiaohong",2);
        //register.recordInfo("xiaogang",0);
        //System.out.println(register.readFile("test1"));
        //register.updateInfo();
        //System.out.println(register.toString());
        /*
        ArrayList<Integer> temp=Main.accept();
        Collections.sort(temp);
        for(int i:temp)
            System.out.println(i);

         */




        Player player=new Player();
        Robot robot1=new Robot();
        Robot robot2=new Robot();
        Robot robot3=new Robot();
        Deck deck=new Deck();
        deck.DistributeCard(player,robot1,robot2,robot3);//发牌
        //检查所有人的手牌
        System.out.println("player before: "+player.hands.toString());//整牌前
        player.hands.Arrange();
        System.out.println("player after: "+player.hands.toString());//整牌后
        robot1.hands.Arrange();
        System.out.println("robot1: "+robot1.hands.toString());
        robot2.hands.Arrange();
        System.out.println("robot2: "+robot2.hands.toString());
        robot3.hands.Arrange();
        System.out.println("robot3: "+robot3.hands.toString());
        //创建对局
        GameRule gameRule=new GameRule(player,robot1,robot2,robot3);
        AI ai=new AI(gameRule);
        int i=gameRule.tempOne;
        boolean TakenTurn=false;
        ArrayList<Integer> temp=new ArrayList<>();
        while (!gameRule.isOver()){
            if(i!=0)
                ai.Strategy1(i);
            else {
                while (!TakenTurn){
                    temp=Main.accept();
                    if(temp.get(0)==0){
                        for(int j=1;j<temp.size();j++)
                            gameRule.peopleList.get(0).selectCards(temp.get(j));
                        TakenTurn=gameRule.takeTurn(0,Choice.Show);
                    }
                    else{
                        TakenTurn=gameRule.takeTurn(0,Choice.Pass);
                    }
                    if(!TakenTurn)
                        System.out.println("your choice is illegal");
                    gameRule.peopleList.get(0).hands.clearSelected();
                }
                TakenTurn=false;
            }
            if(i<3)
                i++;
            else
                i=0;
        }


        /*
        robot1.selectCards(0);
        Turn(gameRule,1,Choice.Show);
        robot2.selectCards(0);
        Turn(gameRule,2,Choice.Show);
        robot3.selectCards(1);
        Turn(gameRule,3,Choice.Show);
        Turn(gameRule,0,Choice.Pass);
        Turn(gameRule,1,Choice.Pass);
        Turn(gameRule,2,Choice.Pass);
        robot3.selectCards(0,1);
        Turn(gameRule,3,Choice.Show);*/

        /*
        //0号想出个顺子
        player.selectCards(0,1,2,3,4);
        if(gameRule.takeTurn(0,Choice.Show))
            System.out.println("0success "+Choice.Show);
        else
            System.out.println("0fail "+Choice.Show);
        System.out.println("player: "+player.hands.toString());
        //1号出了个顺子
        robot1.selectCards(0,1,2,3,4);
        if(gameRule.takeTurn(1,Choice.Show))
            System.out.println("1success "+Choice.Show);
        else
            System.out.println("1fail "+Choice.Show);
        System.out.println(gameRule.toString());
        System.out.println("robot1: "+robot1.hands.toString());
        //0号又想出个顺子
        if(gameRule.takeTurn(0,Choice.Show))
            System.out.println("0success "+Choice.Show);
        else
            System.out.println("0fail "+Choice.Show);
        System.out.println("player: "+player.hands.toString());
        //1号还想出个顺子
        robot1.selectCards(0,1,2,3,4);
        if(gameRule.takeTurn(1,Choice.Show))
            System.out.println("1success "+Choice.Show);
        else
            System.out.println("1fail "+Choice.Show);
        System.out.println(gameRule.toString());
        System.out.println("robot1: "+robot1.hands.toString());
        //2号也打了个顺子
        robot2.selectCards(1,2,3,4,5);
        if(gameRule.takeTurn(2,Choice.Show))
            System.out.println("2success "+Choice.Show);
        else
            System.out.println("2fail "+Choice.Show);
        System.out.println("robot2: "+robot2.hands.toString());
        System.out.println(gameRule.toString());
        //3号空过
        if(gameRule.takeTurn(3,Choice.Pass))
            System.out.println("3success "+Choice.Pass);
        else
            System.out.println("3fail "+Choice.Pass);
        System.out.println("robot3: "+robot3.hands.toString());
        //0号空过
        if(gameRule.takeTurn(0,Choice.Pass))
            System.out.println("0success "+Choice.Pass);
        else
            System.out.println("0fail "+Choice.Pass);
        //1号空过
        if(gameRule.takeTurn(1,Choice.Pass))
            System.out.println("1success "+Choice.Pass);
        else
            System.out.println("1fail "+Choice.Pass);
        //2号打了个单牌
        robot2.selectCards(0);
        if(gameRule.takeTurn(2,Choice.Show))
            System.out.println("2success "+Choice.Show);
        else
            System.out.println("2fail "+Choice.Show);
        System.out.println("robot2: "+robot2.hands.toString());
        System.out.println(gameRule.toString());*/
    }
    public static void Turn(GameRule gameRule,int number,Choice choice){
        if(choice==Choice.Show){
            if(gameRule.takeTurn(number,choice))
                System.out.println(number+"success "+Choice.Show);
            else
                System.out.println(number+"fail "+Choice.Show);
        }
        else {
            if(gameRule.takeTurn(number,Choice.Pass))
                System.out.println(number+"success "+Choice.Pass);
            else
                System.out.println(number+"fail "+Choice.Pass);
        }
        System.out.println(number+gameRule.peopleList.get(number).hands.toString());
        System.out.println(gameRule.toString());
    }
    public static ArrayList<Integer> accept(){
        ArrayList<Integer> temp=new ArrayList<>();
        Scanner input = new Scanner(System.in);
        /*
        int a = input.nextInt();
        int b=input.nextInt();
        temp.add(a);
        while (b>0){
            temp.add(b%10);
            b/=10;
        }*/
        String[] str = input.nextLine().split(" "); //字符串以空格分割
        for(int i=0;i<str.length;i++)
            temp.add(Integer.parseInt(String.valueOf(str[i])));//将字符串的数字保存到整型数组里
        System.out.println("your choice has been taken");
        return temp;
    }
}
