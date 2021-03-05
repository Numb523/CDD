package com.example.test.EntityClass;

public class mixStrategy extends AIStrategy {
    public mixStrategy(){super();}
    public mixStrategy(GameRule gameRule){super(gameRule);}
    @Override
    public void Strategy(int number) {//策略2：大小混合出牌       //修改为只传序号
        People currentPeople = theGame.peopleList.get(number);//接收传参：当前玩家   //修改为根据序号获取对应的人
        currentPeople.hands.Arrange();//对手牌进行排序，大小>花色
        //判断传进来的序号是不是第一个出牌的人，还要确定是不是第一回合,或者轮完一轮都没人要
        if (theGame.tempOne == number && theGame.firstTurn || (theGame.lastOne == theGame.tempOne)) {
            if (!this.searchPair_dec(number))
                if (!this.searchCombinedCards(number))
                    if (!this.searchTris(number))
                        if (!this.searchSingle_dec(number)) {
                        }
        } else//不是第一回合，不能决定牌型
        {
            if (theGame.cardType.cards.size() == 1)//判断上一个玩家的牌型，第一种情况：单牌
            {
                if (!this.searchSingle(number))
                    theGame.takeTurn(number, Choice.Pass);//循环结束也没找到就跳过回合
            } else if (theGame.cardType.cards.size() == 2)//判断牌型，第二种情况：对子
            {
                if (!this.searchPair_dec(number))
                    theGame.takeTurn(number, Choice.Pass);//循环结束也没找到就跳过回合
            } else if (theGame.cardType.cards.size() == 3)//判断牌型，第三种情况：三张
            {
                if (!this.searchTris(number))
                    theGame.takeTurn(number, Choice.Pass);//循环结束也没找到就跳过回合
            } else if (theGame.cardType.cards.size() == 5)//判断牌型，第四种情况：五张
            {
                if (!this.searchCombinedCards(number))
                    theGame.takeTurn(number, Choice.Pass);//循环结束也没找到就跳过回合
            }/*关于五张牌，这里没有判断同花顺，虽然说期望是AI每次出手上最小的牌，多加一组判断即可实现*/
        }
    }
}
