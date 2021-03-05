package com.example.test.EntityClass;
import java.util.ArrayList;
import java.util.Collections;

import static com.example.test.EntityClass.Figure.Ace;

enum CombinationType{Sequence,Flush,ThreeWithPair,FourWithOne,FlushSequence}//从小到大依次为杂顺、同花、三带二、四带一、同花顺
public class CombinedCards extends CardType implements Comparable<CombinedCards>  {
    CombinedCards(ArrayList<Card> cards){
        //if(isLegal(cards)){//判断是否合法，并且决定当前组合的类型
            this.cards=new ArrayList<Card>(cards);//改变指针
            if(this.judgeType())
                this.correction();//决定当前组合的代表单牌
        //}
    }
    CombinedCards(){
        super();
    }
    //public ArrayList<Card> cards;//用于存放组合
    private CombinationType combinationType;//组合类型
    public Card representative;//牌型相同时用于比较的代表单牌
    public CombinationType getCombinationType() {
        return combinationType;
    }

    public void setCombinationType(CombinationType combinationType) {
        this.combinationType = combinationType;
    }
    //判断类型，自己用的
    public boolean judgeType(){
        Collections.sort(cards);
        //判断是否为同花或同花顺
        if(cards.get(0).getPattern()==cards.get(1).getPattern()&&cards.get(1).getPattern()==cards.get(2).getPattern()&&cards.get(2).getPattern()==cards.get(3).getPattern()
                && cards.get(3).getPattern()==cards.get(4).getPattern()){
            //先把包含A或者2的但是不符合排序的顺子找出来
            // JQKA2是不合法的
            if(cards.get(4).getFigure()==Figure.Two&&cards.get(3).getFigure()== Ace&&cards.get(2).getFigure()==Figure.King&&cards.get(1).getFigure()==Figure.Queen&&cards.get(0).getFigure()==Figure.Jack)
                return false;
            //A2345是合法的
            else if(cards.get(4).getFigure()==Figure.Two&&cards.get(3).getFigure()== Ace&&cards.get(2).getFigure()==Figure.Five&&cards.get(1).getFigure()==Figure.Four&&cards.get(0).getFigure()==Figure.Three){
                this.setCombinationType(CombinationType.FlushSequence);
                return true; }
            //23456是合法的
            else if(cards.get(4).getFigure()==Figure.Two&&cards.get(3).getFigure()==Figure.Six&&cards.get(2).getFigure()==Figure.Five&&cards.get(1).getFigure()==Figure.Four&&cards.get(0).getFigure()==Figure.Three){
                this.setCombinationType(CombinationType.FlushSequence);
                return true; }
            //如果点数相连则是同花顺，包括10JQKA，不相连就是同花
            else{
                if(cards.get(0).getFigure().ordinal()==cards.get(1).getFigure().ordinal()-1&&cards.get(1).getFigure().ordinal()==cards.get(2).getFigure().ordinal()-1&&cards.get(2).getFigure().ordinal()==cards.get(3).getFigure().ordinal()-1
                        && cards.get(3).getFigure().ordinal()==cards.get(4).getFigure().ordinal()-1){
                    this.setCombinationType(CombinationType.FlushSequence);
                    return true;
                }
                else{
                    this.setCombinationType(CombinationType.Flush);
                    return true;
                }
            }
        }
        //非同花的情况
        else{
            //如果前三张牌或后三张牌点数相同，并且余下的两张牌点数相同，则为三带二
            if((cards.get(0).getFigure()==cards.get(1).getFigure()&&cards.get(1).getFigure()==cards.get(2).getFigure()&&cards.get(3).getFigure()==cards.get(4).getFigure())
                    ||(cards.get(2).getFigure()==cards.get(3).getFigure()&&cards.get(3).getFigure()==cards.get(4).getFigure()&&cards.get(0).getFigure()==cards.get(1).getFigure())){
                this.setCombinationType(CombinationType.ThreeWithPair);
                return true;
            }
            //如果前四张牌或后四张牌点数相同，则为四带一
            else if(cards.get(0).getFigure()==cards.get(1).getFigure()&&cards.get(1).getFigure()==cards.get(2).getFigure()&&cards.get(2).getFigure()==cards.get(3).getFigure()){
                this.setCombinationType(CombinationType.FourWithOne);
                return true;
            }
            //再判断是否是普通顺子
            else{
                //先把包含A或者2的但是不符合排序的顺子找出来
                // JQKA2是不合法的
                if(cards.get(4).getFigure()==Figure.Two&&cards.get(3).getFigure()== Ace&&cards.get(2).getFigure()==Figure.King&&cards.get(1).getFigure()==Figure.Queen&&cards.get(0).getFigure()==Figure.Jack)
                    return false;
                    //A2345是合法的
                else if(cards.get(4).getFigure()==Figure.Two&&cards.get(3).getFigure()== Ace&&cards.get(2).getFigure()==Figure.Five&&cards.get(1).getFigure()==Figure.Four&&cards.get(0).getFigure()==Figure.Three){
                    this.setCombinationType(CombinationType.Sequence);
                    return true; }
                //23456是合法的
                else if(cards.get(4).getFigure()==Figure.Two&&cards.get(3).getFigure()==Figure.Six&&cards.get(2).getFigure()==Figure.Five&&cards.get(1).getFigure()==Figure.Four&&cards.get(0).getFigure()==Figure.Three){
                    this.setCombinationType(CombinationType.Sequence);
                    return true; }
                //如果点数相连则是顺子，包括10JQKA，不相连就是非法的，
                else{
                    if(cards.get(0).getFigure().ordinal()==cards.get(1).getFigure().ordinal()-1&&cards.get(1).getFigure().ordinal()==cards.get(2).getFigure().ordinal()-1&&cards.get(2).getFigure().ordinal()==cards.get(3).getFigure().ordinal()-1
                            && cards.get(3).getFigure().ordinal()==cards.get(4).getFigure().ordinal()-1){
                        this.setCombinationType(CombinationType.Sequence);
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }
        }
    }
    //判断是否合法，给外面用的
    public static boolean isLegal(ArrayList<Card> cards){
        if(cards.size()!=5){return false;}//如果不是5张牌一定是不合法的
        Collections.sort(cards);
        //判断是否为同花或同花顺
        if(cards.get(0).getPattern()==cards.get(1).getPattern()&&cards.get(1).getPattern()==cards.get(2).getPattern()&&cards.get(2).getPattern()==cards.get(3).getPattern()
                && cards.get(3).getPattern()==cards.get(4).getPattern()){
            //先把包含A或者2的但是不符合排序的顺子找出来
            // JQKA2是不合法的
            if(cards.get(4).getFigure()==Figure.Two&&cards.get(3).getFigure()== Ace&&cards.get(2).getFigure()==Figure.King&&cards.get(1).getFigure()==Figure.Queen&&cards.get(0).getFigure()==Figure.Jack)
                return false;
                //A2345是合法的
            else if(cards.get(4).getFigure()==Figure.Two&&cards.get(3).getFigure()== Ace&&cards.get(2).getFigure()==Figure.Five&&cards.get(1).getFigure()==Figure.Four&&cards.get(0).getFigure()==Figure.Three)
                return true;
            //23456是合法的
            else if(cards.get(4).getFigure()==Figure.Two&&cards.get(3).getFigure()==Figure.Six&&cards.get(2).getFigure()==Figure.Five&&cards.get(1).getFigure()==Figure.Four&&cards.get(0).getFigure()==Figure.Three)
                return true;
            //如果点数相连则是同花顺，包括10JQKA，不相连就是同花
            else{
                if(cards.get(0).getFigure().ordinal()==cards.get(1).getFigure().ordinal()-1&&cards.get(1).getFigure().ordinal()==cards.get(2).getFigure().ordinal()-1&&cards.get(2).getFigure().ordinal()==cards.get(3).getFigure().ordinal()-1
                        && cards.get(3).getFigure().ordinal()==cards.get(4).getFigure().ordinal()-1)
                    return true;
                else
                    return true;
            }
        }
        //非同花的情况
        else{
            //如果前三张牌或后三张牌点数相同，并且余下的两张牌点数相同，则为三带二
            if((cards.get(0).getFigure()==cards.get(1).getFigure()&&cards.get(1).getFigure()==cards.get(2).getFigure()&&cards.get(3).getFigure()==cards.get(4).getFigure())
                    ||(cards.get(2).getFigure()==cards.get(3).getFigure()&&cards.get(3).getFigure()==cards.get(4).getFigure()&&cards.get(0).getFigure()==cards.get(1).getFigure()))
                return true;
            //如果前四张牌或后四张牌点数相同，则为四带一
            else if(cards.get(0).getFigure()==cards.get(1).getFigure()&&cards.get(1).getFigure()==cards.get(2).getFigure()&&cards.get(2).getFigure()==cards.get(3).getFigure())
                return true;
            //再判断是否是普通顺子
            else{
                //先把包含A或者2的但是不符合排序的顺子找出来
                // JQKA2是不合法的
                if(cards.get(4).getFigure()==Figure.Two&&cards.get(3).getFigure()== Ace&&cards.get(2).getFigure()==Figure.King&&cards.get(1).getFigure()==Figure.Queen&&cards.get(0).getFigure()==Figure.Jack)
                    return false;
                    //A2345是合法的
                else if(cards.get(4).getFigure()==Figure.Two&&cards.get(3).getFigure()== Ace&&cards.get(2).getFigure()==Figure.Five&&cards.get(1).getFigure()==Figure.Four&&cards.get(0).getFigure()==Figure.Three)
                    return true;
                //23456是合法的
                else if(cards.get(4).getFigure()==Figure.Two&&cards.get(3).getFigure()==Figure.Six&&cards.get(2).getFigure()==Figure.Five&&cards.get(1).getFigure()==Figure.Four&&cards.get(0).getFigure()==Figure.Three)
                    return true;
                //如果点数相连则是顺子，包括10JQKA，不相连就是非法的，
                else{
                    if(cards.get(0).getFigure().ordinal()==cards.get(1).getFigure().ordinal()-1&&cards.get(1).getFigure().ordinal()==cards.get(2).getFigure().ordinal()-1&&cards.get(2).getFigure().ordinal()==cards.get(3).getFigure().ordinal()-1
                            && cards.get(3).getFigure().ordinal()==cards.get(4).getFigure().ordinal()-1)
                        return true;
                    else
                        return false;
                }
            }
        }
    }
    //找出代表的单牌
    public void correction(){
        Collections.sort(this.cards);//先按照花色、点数排序
        switch (this.combinationType){
        case Sequence://顺子取最大的单牌作为比较对象
            if(this.cards.get(4).getFigure()!=Figure.Two)//顺子中没有2的情况下，最大的单牌就是最后一张,包括10JQKA
                this.representative=cards.get(4);
            else{//顺子中有2要再分情况
                if(this.cards.get(3).getFigure()!=Figure.Ace)//没有A的话说明顺子是23456,取6为最大的单牌
                    this.representative=cards.get(3);
                else//有2又有A说明是A2345，取5为最大的单牌
                    this.representative=cards.get(2);
            }
            break;
        case Flush://同花取最大的单牌作为比较对象
            this.representative=cards.get(4);
            break;
        case ThreeWithPair://三带一对取三个中最大的单牌作为比较对象
            if(cards.get(0).getFigure()==cards.get(2).getFigure())//如果第一张和第三张相同点数，说明三个在前面
                this.representative=cards.get(2);
            else//否则一对在前面
                this.representative=cards.get(4);
            break;
        case FourWithOne://四带一取四个中最大的单牌作为比较对象
            if(cards.get(0).getFigure()==cards.get(1).getFigure())//如果第一张和第二张相同点数，说明四个在前面
                this.representative=cards.get(3);
            else//否则单张在前面
                this.representative=cards.get(4);
            break;
        case FlushSequence://同花顺取最大的单牌作为比较对象
            if(cards.get(4).getFigure()!=Figure.Two)//顺子中没有2的情况下，最大的单牌就是最后一张,包括10JQKA
            {
                this.representative=cards.get(4);
            } else {//顺子中有2要再分情况
                if(this.cards.get(3).getFigure()!=Figure.Ace)//没有A的话说明顺子是23456,取6为最大的单牌
                    this.representative=cards.get(3);
                else//有2又有A说明是A2345，取5为最大的单牌
                    this.representative=cards.get(2);
            }
            break;
        default:
            break;
        }
    }
    public int compareTo(CombinedCards combinedCards) {//比较接口，先比较牌型，牌型相同再比较代表的单牌
        if(this.getCombinationType().ordinal() < combinedCards.getCombinationType().ordinal())
            return -1;
        else if(this.getCombinationType().ordinal() > combinedCards.getCombinationType().ordinal())
            return 1;
        else{
            return (this.representative.compareTo(combinedCards.representative));
        }
    }
    public String toString(){
        String temp=new String();
        temp+="CombinedCards, ";
        temp+=this.combinationType;
        temp+=": ";
        for(Card c:cards)
            temp+=c.toString();
        return temp;
    }
}