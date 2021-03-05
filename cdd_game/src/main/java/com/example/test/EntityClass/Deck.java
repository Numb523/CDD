package com.example.test.EntityClass;
import java.util.ArrayList;
import java.util.Random;


public class Deck{
    public Deck(){
        this.cards=new ArrayList<Card>();
        this.cards.add(new Card(Pattern.Spade,Figure.Ace));
        this.cards.add(new Card(Pattern.Spade,Figure.Two));
        this.cards.add(new Card(Pattern.Spade,Figure.Three));
        this.cards.add(new Card(Pattern.Spade,Figure.Four));
        this.cards.add(new Card(Pattern.Spade,Figure.Five));
        this.cards.add(new Card(Pattern.Spade,Figure.Six));
        this.cards.add(new Card(Pattern.Spade,Figure.Seven));
        this.cards.add(new Card(Pattern.Spade,Figure.Eight));
        this.cards.add(new Card(Pattern.Spade,Figure.Nine));
        this.cards.add(new Card(Pattern.Spade,Figure.Ten));
        this.cards.add(new Card(Pattern.Spade,Figure.Jack));
        this.cards.add(new Card(Pattern.Spade,Figure.Queen));
        this.cards.add(new Card(Pattern.Spade,Figure.King));

        this.cards.add(new Card(Pattern.Heart,Figure.Ace));
        this.cards.add(new Card(Pattern.Heart,Figure.Two));
        this.cards.add(new Card(Pattern.Heart,Figure.Three));
        this.cards.add(new Card(Pattern.Heart,Figure.Four));
        this.cards.add(new Card(Pattern.Heart,Figure.Five));
        this.cards.add(new Card(Pattern.Heart,Figure.Six));
        this.cards.add(new Card(Pattern.Heart,Figure.Seven));
        this.cards.add(new Card(Pattern.Heart,Figure.Eight));
        this.cards.add(new Card(Pattern.Heart,Figure.Nine));
        this.cards.add(new Card(Pattern.Heart,Figure.Ten));
        this.cards.add(new Card(Pattern.Heart,Figure.Jack));
        this.cards.add(new Card(Pattern.Heart,Figure.Queen));
        this.cards.add(new Card(Pattern.Heart,Figure.King));

        this.cards.add(new Card(Pattern.Club,Figure.Ace));
        this.cards.add(new Card(Pattern.Club,Figure.Two));
        this.cards.add(new Card(Pattern.Club,Figure.Three));
        this.cards.add(new Card(Pattern.Club,Figure.Four));
        this.cards.add(new Card(Pattern.Club,Figure.Five));
        this.cards.add(new Card(Pattern.Club,Figure.Six));
        this.cards.add(new Card(Pattern.Club,Figure.Seven));
        this.cards.add(new Card(Pattern.Club,Figure.Eight));
        this.cards.add(new Card(Pattern.Club,Figure.Nine));
        this.cards.add(new Card(Pattern.Club,Figure.Ten));
        this.cards.add(new Card(Pattern.Club,Figure.Jack));
        this.cards.add(new Card(Pattern.Club,Figure.Queen));
        this.cards.add(new Card(Pattern.Club,Figure.King));

        this.cards.add(new Card(Pattern.Diamond,Figure.Ace));
        this.cards.add(new Card(Pattern.Diamond,Figure.Two));
        this.cards.add(new Card(Pattern.Diamond,Figure.Three));
        this.cards.add(new Card(Pattern.Diamond,Figure.Four));
        this.cards.add(new Card(Pattern.Diamond,Figure.Five));
        this.cards.add(new Card(Pattern.Diamond,Figure.Six));
        this.cards.add(new Card(Pattern.Diamond,Figure.Seven));
        this.cards.add(new Card(Pattern.Diamond,Figure.Eight));
        this.cards.add(new Card(Pattern.Diamond,Figure.Nine));
        this.cards.add(new Card(Pattern.Diamond,Figure.Ten));
        this.cards.add(new Card(Pattern.Diamond,Figure.Jack));
        this.cards.add(new Card(Pattern.Diamond,Figure.Queen));
        this.cards.add(new Card(Pattern.Diamond,Figure.King));
    }
    public ArrayList<Card> cards;
    public boolean isEmpty(ArrayList<Card> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }
    public ArrayList<Card> Shuffle(ArrayList<Card> sourceList,int seed){
        if (isEmpty(sourceList)) {
            return sourceList;
        }
        ArrayList<Card> randomList = new ArrayList<Card>( sourceList.size( ) );
        do{
            int randomIndex = Math.abs( new Random(seed).nextInt( sourceList.size() ) );
            randomList.add( sourceList.remove( randomIndex ) );
        }while( sourceList.size( ) > 0 );
        return randomList;
    }
    public ArrayList<Card> Shuffle(ArrayList<Card> sourceList){
        if (isEmpty(sourceList)) {
            return sourceList;
        }
        ArrayList<Card> randomList = new ArrayList<Card>( sourceList.size( ) );
        do{
            int randomIndex = Math.abs( new Random().nextInt( sourceList.size() ) );
            randomList.add( sourceList.remove( randomIndex ) );
        }while( sourceList.size( ) > 0 );
        return randomList;
    }
    public void DistributeCard(People mainPlayer,People secondPlayer,People thirdPlayer,People forthPlayer,int seed){
        this.cards=this.Shuffle(this.cards,seed);
        do{
            //int randomIndex = Math.abs( new Random().nextInt( this.cards.size() ) );
            mainPlayer.hands.cards.add(this.cards.remove(0));
            //randomIndex = Math.abs( new Random().nextInt( this.cards.size() ) );
            secondPlayer.hands.cards.add(this.cards.remove(0));
            //randomIndex = Math.abs( new Random().nextInt( this.cards.size() ) );
            thirdPlayer.hands.cards.add(this.cards.remove(0));
            //randomIndex = Math.abs( new Random().nextInt( this.cards.size() ) );
            forthPlayer.hands.cards.add(this.cards.remove(0));
        }while (this.cards.size( ) > 0);
    }
    public void DistributeCard(People mainPlayer,People secondPlayer,People thirdPlayer,People forthPlayer){
        this.cards=this.Shuffle(this.cards);
        do{
            //int randomIndex = Math.abs( new Random().nextInt( this.cards.size() ) );
            mainPlayer.hands.cards.add(this.cards.remove(0));
            //randomIndex = Math.abs( new Random().nextInt( this.cards.size() ) );
            secondPlayer.hands.cards.add(this.cards.remove(0));
            //randomIndex = Math.abs( new Random().nextInt( this.cards.size() ) );
            thirdPlayer.hands.cards.add(this.cards.remove(0));
            //randomIndex = Math.abs( new Random().nextInt( this.cards.size() ) );
            forthPlayer.hands.cards.add(this.cards.remove(0));
        }while (this.cards.size( ) > 0);
    }
}

