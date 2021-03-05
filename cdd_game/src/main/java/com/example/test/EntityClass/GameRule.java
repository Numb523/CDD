package com.example.test.EntityClass;
import java.util.ArrayList;
import java.util.Collections;

public class GameRule{
    GameRule(){
        lastOne=tempOne=0;
        firstTurn=true;
        peopleList=new ArrayList<People>();
    }
    public GameRule(Player player,Robot robot1,Robot robot2,Robot robot3){
        lastOne=0;
        firstTurn=true;
        peopleList=new ArrayList<People>();
        peopleList.add(player);
        peopleList.add(robot1);
        peopleList.add(robot2);
        peopleList.add(robot3);
        tempOne=fistOne();
    }

    public GameRule(Player player1,Player player2,Player player3,Player player4){
        lastOne=0;
        firstTurn=true;
        peopleList=new ArrayList<People>();
        peopleList.add(player1);
        peopleList.add(player2);
        peopleList.add(player3);
        peopleList.add(player4);
        tempOne=fistOne();
    }

    public ArrayList<People> peopleList;//记录一个对局里面的玩家和机器人
    public CardType cardType;//用于记录出牌堆最上面的牌
    public int lastOne;//出了当前出牌堆最上面牌的人的序号
    public int tempOne;//当前回合的人的序号
    public boolean firstTurn;//标志是否为第一回合
    //找出第一个出牌的人
    public int fistOne(){
        for(int i=0;i<13;i++){
            if(peopleList.get(0).hands.cards.get(i).getFigure()==Figure.Three)
                if(peopleList.get(0).hands.cards.get(i).getPattern()==Pattern.Diamond)
                    return 0;
        }
        for(int i=0;i<13;i++){
            if(peopleList.get(1).hands.cards.get(i).getFigure()==Figure.Three)
                if(peopleList.get(1).hands.cards.get(i).getPattern()==Pattern.Diamond)
                    return 1;
        }
        for(int i=0;i<13;i++){
            if(peopleList.get(2).hands.cards.get(i).getFigure()==Figure.Three)
                if(peopleList.get(2).hands.cards.get(i).getPattern()==Pattern.Diamond)
                    return 2;
        }
        for(int i=0;i<13;i++){
            if(peopleList.get(3).hands.cards.get(i).getFigure()==Figure.Three)
                if(peopleList.get(3).hands.cards.get(i).getPattern()==Pattern.Diamond)
                    return 3;
        }
        return 0;
    }
    //进行回合，要接收管理类的信号，控制类接上此方法
    public boolean takeTurn(int number,Choice choice){
        if(number==tempOne){//判断是否是当前回合的人采取行动
            if(choice==Choice.Show && this.peopleList.get(tempOne).hands.selectedCards.size()!=0){//选择出牌，并且出牌不为空
                if(isLegal(this.peopleList.get(tempOne).hands.selectedCards)){//出牌符合要求
                    if(firstTurn)
                        firstTurn=false;
                    this.cardType=createCardType(this.peopleList.get(tempOne).hands.selectedCards);
                    this.peopleList.get(number).cardType=this.cardType;
                    this.peopleList.get(tempOne).hands.showCards();
                    lastOne=tempOne;
                    this.toNext();
                    System.out.println("LastCards"+this.toString());
                    System.out.println(number+this.peopleList.get(number).hands.toString());
                    return true;
                }
                else
                    return false;//出牌不符合要求
            }
            else if(choice==Choice.Pass){//跳过回合，进入下一家的回合
                this.toNext();
                this.peopleList.get(number).cardType=null;
                System.out.println(number+" Pass");
                return true;
            }
            else
                return false;//出牌为空
        }
        else
            return false;//如果不是当前回合的人的行动无效
    }

    public void toNext(){//轮到下一个玩家
        if(tempOne<3)
            tempOne++;
        else
            tempOne=0;
    }
    public boolean isLegal(ArrayList<Card> cards){//用来比较上一家出的牌
        if(firstTurn) {//第一回合出牌无限制
            if (Single.isLegal(cards) || Pair.isLegal(cards) || Tris.isLegal(cards) || CombinedCards.isLegal(cards))
                return true;
            else
                return false;
        }
        if(lastOne==tempOne){//轮完一圈都没人能要，可以无限制出牌
            if (Single.isLegal(cards) || Pair.isLegal(cards) || Tris.isLegal(cards) || CombinedCards.isLegal(cards))
                return true;
            else
                return false;}
        if(cards.size()!=this.cardType.cards.size())//牌的数量与牌堆最上面的牌不同就肯定不合法
            return false;
        //需要定义对象所以不用switch了
        if(Single.isLegal(cards)){
            Single temp=new Single(cards);
            return (temp.compareTo((Single) this.cardType) > 0);
        }
        else if(Pair.isLegal(cards)){
            Pair temp=new Pair(cards);
            return (temp.compareTo((Pair) this.cardType)>0);
        }
        else if(Tris.isLegal(cards)){
            Tris temp=new Tris(cards);
            return (temp.compareTo((Tris) this.cardType)>0);
        }
        else if(CombinedCards.isLegal(cards)){
            CombinedCards temp=new CombinedCards(cards);
            return (temp.compareTo((CombinedCards)this.cardType)>0);
        }
        else
            return false;
        /*
        switch (cards.size()){
            case 1:
                if(Single.isLegal(cards)&&this.cardType.compareTo(new Single(cards))==1)
                    return true;
                else
                    return false;
            case 2:
                if(Pair.isLegal(cards)&&this.cardType.compareTo(new Pair(cards))==1)
                    return true;
                else
                    return false;
            case 3:
                if(Tris.isLegal(cards)&&this.cardType.compareTo(new Tris(cards))==1)
                    return true;
                else
                    return false;
            case 5:
                if(CombinedCards.isLegal(cards)&&this.cardType.compareTo(new CombinedCards(cards))==1)
                    return true;
                else
                    return false;
            default:
                return false;
        }
        */
    }
    public CardType createCardType(ArrayList<Card> cards){//对传入的牌进行包装，创建对应的对象
        if(cards.size()==1&&Single.isLegal(cards))
            return new Single(cards);
        else if(cards.size()==2&&Pair.isLegal(cards))
            return new Pair(cards);
        else if(cards.size()==3&&Tris.isLegal(cards))
            return new Tris(cards);
        else if(cards.size()==5&&CombinedCards.isLegal(cards))
            return new CombinedCards(cards);
        else
            return null;
    }

    public boolean isOver(){//判断是否有人出完牌了
        for(int i=0;i<4;i++)
            if(this.peopleList.get(i).hands.cards.size()==0)
                return true;
        return false;
    }
    public void calculateScore(){//计算得分
        if(isOver()){
            ArrayList<Integer> cardRemain=new ArrayList<Integer>();
            for(int i=0;i<4;i++)
                cardRemain.add(this.peopleList.get(i).hands.cards.size());
            ArrayList<Integer> cardScore=new ArrayList<Integer>();
            for(int n:cardRemain){
                if(n<8)
                    cardScore.add(n);
                else if(n<10)
                    cardScore.add(2*n);
                else if(n<13)
                    cardScore.add(3*n);
                else if(n==13)
                    cardScore.add(4*n);
            }
            int temp=cardScore.get(0)+cardScore.get(1)+cardScore.get(2)+cardScore.get(3);
            for(int i=0;i<4;i++)
                this.peopleList.get(i).score.addScore(temp-4*cardScore.get(i));
        }
    }
    public ArrayList<People> calculateRank(){//计算排名
        ArrayList<People> temp = new ArrayList<People>(this.peopleList);
        Collections.sort(temp);
        return temp;
    }
    public String toString(){
        if(this.cardType!=null)
            return this.cardType.toString();
        else
            return "nothing";
    }
}