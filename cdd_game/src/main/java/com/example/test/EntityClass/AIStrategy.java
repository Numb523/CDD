package com.example.test.EntityClass;
import java.util.*;

public abstract class AIStrategy {
    //private People currentPeople;//当前轮到的玩家，目前只有Robot玩家需要使用到AI。后续可以添加提示功能。
    public GameRule theGame = new GameRule();//用于判断回合等基本游戏信息

    public AIStrategy() {
        theGame=new GameRule();
    }
    public AIStrategy(GameRule gameRule){
        theGame=gameRule;
    }

    ;//构造函数
    public boolean searchSingle(int number){//寻找单牌
        People currentPeople = theGame.peopleList.get(number);//接收传参：当前玩家  根据序号获取对应的人
            ArrayList<Card> minCards = new ArrayList<Card>();
            for (int i = 0; i < currentPeople.hands.cards.size(); i++) {
                Card minCard = currentPeople.hands.cards.get(i);
                minCards.add(minCard);
                if (theGame.isLegal(minCards))//如果当次找到的是比上一个玩家大的牌，则可以出牌
                {
                    currentPeople.hands.selectedCards.add(minCard);//添加到出牌序列
                    theGame.takeTurn(number, Choice.Show);
                    return true;
                }
                minCards.clear();
            }
            return false;
    }

    public boolean searchSingle_dec(int number) {//寻找单牌_降序
        People currentPeople = theGame.peopleList.get(number);//接收传参：当前玩家  根据序号获取对应的人
        ArrayList<Card> maxCards = new ArrayList<Card>();
        Card maxCard = currentPeople.hands.cards.get(currentPeople.hands.cards.size()-1);//无需循环，如果手中最大的单排小于上一家的牌，没有寻找下一张牌的意义
        maxCards.add(maxCard);
        if (theGame.isLegal(maxCards)){//如果当次找到的是比上一个玩家大的牌，则可以出牌
            currentPeople.hands.selectedCards.add(maxCard);//添加到出牌序列
            theGame.takeTurn(number, Choice.Show);
            return true;
        }
        maxCards.clear();
        return false;
    }

    public boolean searchPair(int number){
        People currentPeople = theGame.peopleList.get(number);//接收传参：当前玩家  根据序号获取对应的人
            ArrayList<Card> minCards = new ArrayList<Card>();//遍历手牌数组，找出最小的对子（不一定能找到）
            Card currentCard;//当前考虑的是否有对子的牌
            boolean flag = false;//标志位，用于记录是否找到对子

            for (int i = 0; i < currentPeople.hands.cards.size() - 2; i++)//依次枚举
            {
                currentCard = currentPeople.hands.cards.get(i);
                if (currentPeople.hands.cards.get(i + 1).getFigure() == currentPeople.hands.cards.get(i).getFigure())//如果有对子，因为已经进行排序，那么必然在相邻的位置上，故可以省去一次循环
                {
                    minCards.add(currentPeople.hands.cards.get(i));
                    minCards.add(currentPeople.hands.cards.get(i + 1));
                    if (theGame.isLegal(minCards)) {
                        flag = true;
                        break;//找到对子，跳出循环}
                    }
                    minCards.clear();//不符合要求的话就清空重新找
                }
            }
            if (flag) {
                for (int i = 0; i < 2; i++) {
                    currentPeople.hands.selectedCards.add(minCards.get(i));//添加到出牌序列
                }
                theGame.takeTurn(number, Choice.Show);//执行出牌动作
                return true;
            } else
                return false;//执行跳过操作
    }

    public boolean searchPair_dec(int number) {//寻找对子_降序
        People currentPeople = theGame.peopleList.get(number);//接收传参：当前玩家  根据序号获取对应的人
        ArrayList<Card> maxCards = new ArrayList<Card>();//遍历手牌数组，找出最大的对子（不一定能找到）
        Card currentCard;//当前考虑的是否有对子的牌
        boolean flag = false;//标志位，用于记录是否找到对子

        for (int i = currentPeople.hands.cards.size()-1; i > 0; i--)//依次枚举
        {
            currentCard = currentPeople.hands.cards.get(i);
            if (currentPeople.hands.cards.get(i - 1).getFigure() == currentPeople.hands.cards.get(i).getFigure())//如果有对子，因为已经进行排序，那么必然在相邻的位置上，故可以省去一次循环
            {
                maxCards.add(currentPeople.hands.cards.get(i - 1));
                maxCards.add(currentPeople.hands.cards.get(i));
                if (theGame.isLegal(maxCards)) {
                    flag = true;
                    break;//找到对子，跳出循环}
                }
                maxCards.clear();//不符合要求的话就清空重新找
            }
        }
        if (flag) {
            for (int i = 0; i < 2; i++) {
                currentPeople.hands.selectedCards.add(maxCards.get(i));//添加到出牌序列
            }
            theGame.takeTurn(number, Choice.Show);//执行出牌动作
            return true;
        } else
            return false;//执行跳过操作
    }

    public boolean searchTris(int number){
        People currentPeople = theGame.peopleList.get(number);//接收传参：当前玩家  根据序号获取对应的人
            ArrayList<Card> minCards = new ArrayList<Card>();
            Card currentCard;
            boolean flag = false;
            for (int i = 0; i < currentPeople.hands.cards.size() - 2; i++)//依次枚举
            {
                currentCard = currentPeople.hands.cards.get(i);
                if (currentPeople.hands.cards.get(i + 1).getFigure() == currentPeople.hands.cards.get(i).getFigure() && currentPeople.hands.cards.get(i + 2).getFigure() == currentPeople.hands.cards.get(i + 1).getFigure()) {
                    minCards.add(currentPeople.hands.cards.get(i));
                    minCards.add(currentPeople.hands.cards.get(i + 1));
                    minCards.add(currentPeople.hands.cards.get(i + 2));
                    if (theGame.isLegal(minCards)) {
                        flag = true;
                        break;//找到三个，跳出循环}
                    }
                    minCards.clear();//不符合要求的话就清空重新找
                }
            }
            if (flag) {
                for (int i = 0; i < 3; i++) {
                    currentPeople.hands.selectedCards.add(minCards.get(i));//添加到出牌序列
                }
                theGame.takeTurn(number, Choice.Show);//执行出牌动作
                return true;
            } else
                return false;//执行跳过操作
    }
    public boolean searchCombinedCards(int number){
            People currentPeople = theGame.peopleList.get(number);//接收传参：当前玩家  根据序号获取对应的人
            ArrayList<Card> minCards=new ArrayList<Card>();
            boolean flag=false;//用于标记是否找到五张牌，不需要【并且区分大小，杂顺=1，同花=2，三带二=3，四带一=4，同花顺=5】
            //判断杂顺
            ArrayList<Card> sequence=new ArrayList<Card>();//储存长顺子
            for(int i=0;i<currentPeople.hands.cards.size()-4;i++)
            {
                if(currentPeople.hands.cards.get(i+1).getFigure()!=currentPeople.hands.cards.get(i).getFigure()||
                        currentPeople.hands.cards.get(i+1).getFigure().ordinal()!=currentPeople.hands.cards.get(i).getFigure().ordinal()+1)//下一张牌要么点数相同要么点数加一，否则这轮循环就不需要执行
                    continue;
                sequence.add(currentPeople.hands.cards.get(i));
                for(int j=i+1;j<currentPeople.hands.cards.size();j++){//从i开始往后找
                    if(currentPeople.hands.cards.get(j).getFigure().ordinal()==sequence.get(sequence.size()-1).getFigure().ordinal()+1);//如果找到的牌比最后添加进去的牌点数大1点，就加入长顺子
                    sequence.add(currentPeople.hands.cards.get(j));
                }
                if(sequence.size()>=5){//如果找出的长顺子包含五张或以上，就对长顺子每次取五张来检查合法性
                    for(int k=0;k<sequence.size()-4;k++){
                        for(int n=k;n<k+5;n++)//从k开始的五张牌
                            minCards.add(sequence.get(n));
                        if(theGame.isLegal(minCards)){
                            flag=true;
                            break;
                        }
                        minCards.clear();
                    }
                }
                if(flag)
                    break;
                sequence.clear();
            }
            if (flag)
            {
                for (int i = 0; i < 5; i++)
                {
                    currentPeople.hands.selectedCards.add(minCards.get(i));//添加到出牌序列
                }
                theGame.takeTurn(number, Choice.Show);//执行出牌动作//执行出牌动作
                return true;//退出该函数，即，结束本次AI出牌动作
            }
            minCards.clear();

            //判断同花
            ArrayList<Card> diamond=new ArrayList<Card>();
            ArrayList<Card> heart=new ArrayList<Card>();
            ArrayList<Card> spade=new ArrayList<Card>();
            ArrayList<Card> club=new ArrayList<Card>();
            for(int i=0;i<currentPeople.hands.cards.size();i++)//为什么这里会提示unreachable statement，return函数我明明框在括号里了，而且后面也没有提示这个错误。【奇怪，加了这行注释又没问题了】，因为你前面的if else无论如何都会执行return，自然下面的代码都没用了
            {
                switch (currentPeople.hands.cards.get(i).getPattern()){
                    case Spade:
                        spade.add(currentPeople.hands.cards.get(i));
                        break;
                    case Heart:
                        heart.add(currentPeople.hands.cards.get(i));
                        break;
                    case Club:
                        club.add(currentPeople.hands.cards.get(i));
                        break;
                    case Diamond:
                        diamond.add(currentPeople.hands.cards.get(i));
                        break;
                    default:
                        break;
                }
            }
            //依次对花色从小打到进行查找
            if(diamond.size()>=5){
                for(int i=0;i<diamond.size()-4;i++){
                    for(int j=i;j<i+5;j++)
                        minCards.add(diamond.get(j));
                    if(theGame.isLegal(minCards)){
                        flag=true;
                        break;
                    }
                    if(flag)
                        break;
                    minCards.clear();
                }
            }
            if(!flag&&club.size()>=5){
                for(int i=0;i<club.size()-4;i++){
                    for(int j=i;j<i+5;j++)
                        minCards.add(club.get(j));
                    if(theGame.isLegal(minCards)){
                        flag=true;
                        break;
                    }
                    if(flag)
                        break;
                    minCards.clear();
                }
            }
            if(!flag&&heart.size()>=5){
                for(int i=0;i<heart.size()-4;i++){
                    for(int j=i;j<i+5;j++)
                        minCards.add(heart.get(j));
                    if(theGame.isLegal(minCards)){
                        flag=true;
                        break;
                    }
                    if(flag)
                        break;
                    minCards.clear();
                }
            }
            if(!flag&&spade.size()>=5){
                for(int i=0;i<spade.size()-4;i++){
                    for(int j=i;j<i+5;j++)
                        minCards.add(spade.get(j));
                    if(theGame.isLegal(minCards)){
                        flag=true;
                        break;
                    }
                    if(flag)
                        break;
                    minCards.clear();
                }
            }
            if (flag)
            {
                for (int i = 0; i < 5; i++)
                {
                    currentPeople.hands.selectedCards.add(minCards.get(i));//添加到出牌序列
                }
                theGame.takeTurn(number, Choice.Show);//执行出牌动作
                return true;//退出该函数，即，结束本次AI出牌动作
            }
            minCards.clear();

            //判断三带二
            for (int i = 0; i < currentPeople.hands.cards.size() - 2; i++)//先找三张相同的
            {
                if (currentPeople.hands.cards.get(i + 1).getFigure() == currentPeople.hands.cards.get(i).getFigure()
                        && currentPeople.hands.cards.get(i + 2).getFigure() == currentPeople.hands.cards.get(i).getFigure()) {
                    minCards.add(currentPeople.hands.cards.get(i));
                    minCards.add(currentPeople.hands.cards.get(i + 1));
                    minCards.add(currentPeople.hands.cards.get(i + 2));
                    for (int j = 0; j < currentPeople.hands.cards.size() - 1; j++)//再找两张相同的
                    {
                        if (currentPeople.hands.cards.get(j + 1).getFigure() == currentPeople.hands.cards.get(j).getFigure()
                                && currentPeople.hands.cards.get(j).getFigure() != currentPeople.hands.cards.get(i).getFigure())
                        {
                            minCards.add(currentPeople.hands.cards.get(j));
                            minCards.add(currentPeople.hands.cards.get(j + 1));
                            if(theGame.isLegal(minCards))
                            {
                                flag = true;
                                break;
                            }
                            minCards.remove(4);
                            minCards.remove(3);
                        }
                    }
                    if(flag)
                        break;
                    minCards.clear();
                }
            }
            if (flag)
            {
                for (int i = 0; i < 5; i++)
                {
                    currentPeople.hands.selectedCards.add(minCards.get(i));//添加到出牌序列
                }
                theGame.takeTurn(number, Choice.Show);//执行出牌动作
                return true;//退出该函数，即，结束本次AI出牌动作
            }
            minCards.clear();


            //判断四带一
            for(int i=0;i<currentPeople.hands.cards.size()-3;i++)
            {
                if(currentPeople.hands.cards.get(i+1).getFigure()==currentPeople.hands.cards.get(i).getFigure()
                        &&currentPeople.hands.cards.get(i+2).getFigure()==currentPeople.hands.cards.get(i).getFigure()
                        &&currentPeople.hands.cards.get(i+3).getFigure()==currentPeople.hands.cards.get(i).getFigure())
                {
                    minCards.add(currentPeople.hands.cards.get(i));
                    minCards.add(currentPeople.hands.cards.get(i+1));
                    minCards.add(currentPeople.hands.cards.get(i+2));
                    minCards.add(currentPeople.hands.cards.get(i+3));
                    for(int j=0;j<currentPeople.hands.cards.size();j++)
                    {
                        if(currentPeople.hands.cards.get(j).getFigure()!=currentPeople.hands.cards.get(i).getFigure())
                        {
                            minCards.add(currentPeople.hands.cards.get(j));
                            if(theGame.isLegal(minCards))
                            {
                                flag = true;
                                break;
                            }
                            minCards.remove(4);
                        }
                    }
                    if(flag)
                        break;
                    minCards.clear();
                }

            }
            if (flag)
            {
                for (int i = 0; i < 5; i++)
                {
                    currentPeople.hands.selectedCards.add(minCards.get(i));//添加到出牌序列
                }
                theGame.takeTurn(number, Choice.Show);;//执行出牌动作
                return true;//退出该函数，即，结束本次AI出牌动作
            }
            minCards.clear();
            //判断同花顺
            diamond.clear();
            heart.clear();
            spade.clear();
            club.clear();
            for(int i=0;i<currentPeople.hands.cards.size();i++){
                switch (currentPeople.hands.cards.get(i).getPattern()){
                    case Spade:
                        spade.add(currentPeople.hands.cards.get(i));
                        break;
                    case Heart:
                        heart.add(currentPeople.hands.cards.get(i));
                        break;
                    case Club:
                        club.add(currentPeople.hands.cards.get(i));
                        break;
                    case Diamond:
                        diamond.add(currentPeople.hands.cards.get(i));
                        break;
                    default:
                        break;
                }
            }
            //因为同花色不存在同点数的情况，而且是已经排好序的情况，直接判断大小就行
            //依次对花色从小打到进行查找
            if(diamond.size()>=5){
                for(int i=0;i<diamond.size()-4;i++){
                    if(diamond.get(i).getFigure().ordinal()+1==diamond.get(i+1).getFigure().ordinal()&&
                            diamond.get(i+1).getFigure().ordinal()+1==diamond.get(i+2).getFigure().ordinal()&&
                            diamond.get(i+2).getFigure().ordinal()+1==diamond.get(i+3).getFigure().ordinal()&&
                            diamond.get(i+3).getFigure().ordinal()+1==diamond.get(i+4).getFigure().ordinal()){
                        for(int j=i;j<i+5;j++){
                            minCards.add(diamond.get(j));
                        }
                    }
                    if(theGame.isLegal(minCards)){
                        flag=true;
                        break;
                    }
                    if(flag)
                        break;
                    minCards.clear();
                }
            }
            if(!flag&&club.size()>=5){
                for(int i=0;i<club.size()-4;i++){
                    if(club.get(i).getFigure().ordinal()+1==club.get(i+1).getFigure().ordinal()&&
                            club.get(i+1).getFigure().ordinal()+1==club.get(i+2).getFigure().ordinal()&&
                            club.get(i+2).getFigure().ordinal()+1==club.get(i+3).getFigure().ordinal()&&
                            club.get(i+3).getFigure().ordinal()+1==club.get(i+4).getFigure().ordinal()){
                        for(int j=i;j<i+5;j++){
                            minCards.add(club.get(j));
                        }
                    }
                    if(theGame.isLegal(minCards)){
                        flag=true;
                        break;
                    }
                    if(flag)
                        break;
                    minCards.clear();
                }
            }
            if(!flag&&heart.size()>=5){
                for(int i=0;i<heart.size()-4;i++){
                    if(heart.get(i).getFigure().ordinal()+1==heart.get(i+1).getFigure().ordinal()&&
                            heart.get(i+1).getFigure().ordinal()+1==heart.get(i+2).getFigure().ordinal()&&
                            heart.get(i+2).getFigure().ordinal()+1==heart.get(i+3).getFigure().ordinal()&&
                            heart.get(i+3).getFigure().ordinal()+1==heart.get(i+4).getFigure().ordinal()){
                        for(int j=i;j<i+5;j++){
                            minCards.add(heart.get(j));
                        }
                    }
                    if(theGame.isLegal(minCards)){
                        flag=true;
                        break;
                    }
                    if(flag)
                        break;
                    minCards.clear();
                }
            }
            if(!flag&&spade.size()>=5){
                for(int i=0;i<spade.size()-4;i++){
                    if(spade.get(i).getFigure().ordinal()+1==spade.get(i+1).getFigure().ordinal()&&
                            spade.get(i+1).getFigure().ordinal()+1==spade.get(i+2).getFigure().ordinal()&&
                            spade.get(i+2).getFigure().ordinal()+1==spade.get(i+3).getFigure().ordinal()&&
                            spade.get(i+3).getFigure().ordinal()+1==spade.get(i+4).getFigure().ordinal()){
                        for(int j=i;j<i+5;j++){
                            minCards.add(spade.get(j));
                        }
                    }
                    if(theGame.isLegal(minCards)){
                        flag=true;
                        break;
                    }
                    if(flag)
                        break;
                    minCards.clear();
                }
            }
            if (flag)
            {
                for (int i = 0; i < 5; i++)
                {
                    currentPeople.hands.selectedCards.add(minCards.get(i));//添加到出牌序列
                }
                theGame.takeTurn(number, Choice.Show);//执行出牌动作
                return true;//退出该函数，即，结束本次AI出牌动作
            }
            minCards.clear();
            return false;//执行跳过操作
    }
    public abstract void Strategy(int number);
}