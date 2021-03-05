package com.example.test.Control;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.test.View.CardImage;
import com.example.test.EntityClass.*;
import com.example.test.View.PlayingActivity;
import com.example.test.R;
import com.example.test.wificonnet.Cilent;
import com.example.test.wificonnet.Server;
import com.example.test.wificonnet.Tag;

public class GameControl {
    private  int  op = -1;//3,还未开始,-1:重新开始 0:游戏中 1:本局结束
    public boolean ifClickChupai = false;
    public  GameRule gr;
    private Player player;
    private Robot robot1, robot2, robot3;
    private Deck deck;
    private int timeLimite = 300;
    private AIStrategy[] aiStrategy=new AIStrategy[3];
    //private AI ai;
    //private minFirstStrategy minFirstStrategy;
    //private mixStrategy mixStrategy;

    public static Player []players=new Player[4];
    public ArrayList<Integer> MeCards=new ArrayList<Integer>();
    public static boolean chupai=false;
    public static ArrayList<Integer> OtherCards=new ArrayList<Integer>();
    public static int localnum =0;
    public static boolean pass=false;



    private boolean canDrawLatestCards = false;
    public Context context;
    Bitmap chuPaiImage;
    Bitmap redoImage;
    Bitmap passImage;
    boolean one = false;

    private boolean[] canPass = new boolean[4];
    private int[][] timeLimitePosition = {{130, 190}, {360, 80}, {180,20},{80, 80} };
    private int[][] passPosition = {{110, 200}, {300, 100},{180,80},{70, 110}};
    private int[][] playerLatestCardsPosition = {{130, 160},{330, 80},{180,60},{80, 80}};
    private int[][] playerCardsPosition = {{120, 230},{415, 160},{300,50},{20, 160} };
    private int[][] scorePosition = {{70, 290}, {70, 30}, {340, 30}};
    private int[][] iconPosition = {{50, 240}, {410, 70}, {240, 10},{15,70}};
    private int buttonPosition_X = 240;
    private int buttonPosition_Y = 180;
    private Bitmap[] cornImage = new Bitmap[9];
    private Bitmap pasue;
    private Bitmap score;

    public GameControl(Context context){
        this.context = context;
        redoImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_redo);
        passImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_pass);
        chuPaiImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_chupai);
        pasue = BitmapFactory.decodeResource(context.getResources(), R.drawable.pasue);
        score = BitmapFactory.decodeResource(context.getResources(), R.drawable.score);
        /*
        this.player = new Player();
        this.robot1 = new Robot();
        this.robot2 = new Robot();
        this.robot3 = new Robot();
        */
        this.player = new Player();
        this.robot1 = new Robot();
        this.robot2 = new Robot();
        this.robot3 = new Robot();

        cornImage[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_1);
        cornImage[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_2);
        cornImage[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_3);
        cornImage[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_4);
        cornImage[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_5);
        cornImage[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_6);
        cornImage[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_7);
        cornImage[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_8);
        cornImage[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_9);

        if(Server.myServer.flag || Cilent.myCilent.flag)
            op=3;

        MeCards.clear();

    }


    public static int netToLocal(int netnum)
    {
        int num=0;
        if(Cilent.myCilent.flag)
            num=Cilent.myCilent.number;
        return (netnum- num+4)%4;
    }

    public void gameLogic(){
        switch (op){
            case 3:
                init_net();
                op=4;
                break;
            case 4:
                playingGame_net();
                one=true;
                break;
            case -1:
                init();
                op = 0 ;
                break;
            case 0:
                playingGame();
                one = true;
                break;
            case 1:
                if(one){
                    gr.calculateScore();
                    one = !one;
                    for(int i = 0 ; i < 4 ; i++ ){
                        if(gr.peopleList.get(i).cardType!=null || gr.peopleList.get(i).hands.cards != null){
                            gr.peopleList.get(i).cardType = null;
                            gr.peopleList.get(i).hands.cards.clear();
                        }
                    }
                }
                break;
            case 2:
                PlayingActivity.handler.sendEmptyMessage(PlayingActivity.PASUE);
                break;
        }
    }


    public void controlPaint(Canvas canvas) {
        switch (op) {
            case -1 :
                break;
            case 0 :
                //画出游戏时画面
                paintGaming(canvas);
                break;
            case 1 :
                //画出游戏结束画面
                paintResult(canvas);
                break;


        }
    }

    private void paintResult(Canvas canvas) {
        paintIconAndScore(canvas);
        for(int i = 0 ; i < 4 ; i++){
            int score = gr.peopleList.get(i).score.point;
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize((int) (16 * PlayingActivity.SCALE_HORIAONTAL));
            canvas.drawText("分数：" + score, (int) (passPosition[i][0] * PlayingActivity.SCALE_HORIAONTAL),
                    (int) (passPosition[i][1] * PlayingActivity.SCALE_VERTICAL), paint);

        }
    }

    //玩游戏逻辑
    private void playingGame(){
            if(gr.isOver()){
            op = 1;
        }
        //机器人
        if(gr.tempOne != 0){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            aiStrategy[gr.tempOne-1].Strategy(gr.tempOne);
            timeLimite = 300;
            canPass[gr.tempOne] = true;
        }
        //玩家
        else{
            if (timeLimite <= 300 && timeLimite >= 0) {
                if (ifClickChupai == true) {

                    Choice choice = Choice.Show;
                    gr.takeTurn(0,choice);
                    }
                    ifClickChupai = false;
                }

            //超时
            else {
                //除第一轮外均为不出
                if (!gr.firstTurn) {
                    Choice choice = Choice.Pass;
                    gr.takeTurn(0,choice);
                }
                //第一轮则采用智能出牌
                else {
                    //智能出牌
                    //
                }

            }
            timeLimite = 300;

        }
        timeLimite -= 2;
        canDrawLatestCards = true;

    }

    //玩游戏逻辑
    private void playingGame_net(){
        if(gr.isOver()){
            op = 1;
        }
        //别的玩家出牌
        if(gr.tempOne != 0){

            while (!chupai || !pass)
            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(chupai)
            {

                players[localnum].hands.clearSelected();
                int []temp=new int[OtherCards.size()];
                players[localnum].hands.selectCards(temp);

                gr.takeTurn(localnum,Choice.Show);
                chupai=false;
            }
            else if(pass)
            {
                gr.takeTurn(localnum,Choice.Pass);
                canPass[gr.tempOne] = true;
            }

        }
        //玩家
        else{
            if (timeLimite <= 300 && timeLimite >= 0) {
                if (ifClickChupai == true) {

                    String cards=Tag.card+" "+Server.myServer.number;
                    for(int i: MeCards)
                        cards=cards+" "+i;

                    if(Server.myServer.flag)
                    {
                        Server.myServer.sendall(cards);
                    }
                    else if(Cilent.myCilent.flag)
                    {
                        Cilent.myCilent.send(cards);
                    }

                    Choice choice = Choice.Show;
                    gr.takeTurn(0,choice);
                }
                ifClickChupai = false;
            }

            timeLimite = 300;

        }
        //timeLimite -= 2;
        canDrawLatestCards = true;

    }


    //初始化
    private void init(){

        //洗牌,发牌
        deck = new Deck();

        if(!deck.isEmpty(deck.cards)){
            deck.DistributeCard(player,robot1,robot2,robot3);
            player.hands.Arrange();
            robot1.hands.Arrange();
            robot2.hands.Arrange();
            robot3.hands.Arrange();
        }
        gr = new GameRule(player,robot1,robot2,robot3);
        aiStrategy[0]=new minFirstStrategy(gr);
        aiStrategy[1]=new mixStrategy(gr);
        aiStrategy[2]=new minFirstStrategy(gr);
        gr.firstTurn = true;

        for(int i = 0 ; i < 4; i++){
            canPass[i]=false;
        }
    }

    public void init_net()
    {
        //洗牌,发牌
        deck = new Deck();

        if(!deck.isEmpty(deck.cards)){
            deck.DistributeCard(players[netToLocal(0)],players[netToLocal(1)],players[netToLocal(2)],players[netToLocal(3)]);
            players[0].hands.Arrange();
            players[1].hands.Arrange();
            players[2].hands.Arrange();
            players[3].hands.Arrange();
        }
        gr = new GameRule(players[0],players[1],players[2],players[3]);
        //this.ai = new AI(gr);
        //this.mixStrategy = new mixStrategy(gr);
        gr.firstTurn = true;

        for(int i = 0 ; i < 4; i++){
            canPass[i]=false;
        }
    }



    private void paintGaming(Canvas canvas){
        paint(canvas,gr.peopleList);
        //paintTimeLimite(canvas);
        paintIconAndScore(canvas);
        paintPasue(canvas);
        paintScore(canvas);


        if (gr.tempOne == 0){
            Rect src = new Rect();
            Rect dst = new Rect();
            //绘制出牌
            src.set(0, 0, chuPaiImage.getWidth(), chuPaiImage.getHeight());
            dst.set((int) (buttonPosition_X * PlayingActivity.SCALE_HORIAONTAL),
                    (int) (buttonPosition_Y * PlayingActivity.SCALE_VERTICAL),
                    (int) ((buttonPosition_X + 80) * PlayingActivity.SCALE_HORIAONTAL),
                    (int) ((buttonPosition_Y + 40) * PlayingActivity.SCALE_VERTICAL));
            canvas.drawBitmap(chuPaiImage, src, dst, null);
            //绘制不出
            if (!gr.firstTurn) {
                src.set(0, 0, passImage.getWidth(), passImage.getHeight());
                dst.set((int) ((buttonPosition_X - 80) * PlayingActivity.SCALE_HORIAONTAL),
                        (int) (buttonPosition_Y * PlayingActivity.SCALE_VERTICAL),
                        (int) ((buttonPosition_X) * PlayingActivity.SCALE_HORIAONTAL),
                        (int) ((buttonPosition_Y + 40) * PlayingActivity.SCALE_VERTICAL));
                canvas.drawBitmap(passImage, src, dst, null);
            }
            //绘制重选
            src.set(0, 0, redoImage.getWidth(), redoImage.getHeight());
            dst.set((int) ((buttonPosition_X + 80) * PlayingActivity.SCALE_HORIAONTAL),
                    (int) ((buttonPosition_Y) * PlayingActivity.SCALE_VERTICAL),
                    (int) ((buttonPosition_X + 160) * PlayingActivity.SCALE_HORIAONTAL),
                    (int) ((buttonPosition_Y + 40) * PlayingActivity.SCALE_VERTICAL));
            canvas.drawBitmap(redoImage, src, dst, null);
        }

        for (int i = 0; i < 4; i++) {
            if (gr.tempOne != i && gr.peopleList.get(i).cardType != null && canDrawLatestCards == true) {
                paintCard(canvas, playerLatestCardsPosition[i][0],
                        playerLatestCardsPosition[i][1], gr.peopleList.get(i).cardType);
            }
            if (gr.tempOne != i && gr.peopleList.get(i).cardType == null && canPass[i] == true) {
                paintPass(canvas, i);
            }
        }


    }

    private void paintScore(Canvas canvas) {
        Rect src = new Rect();
        Rect des = new Rect();
        src.set(0, 0, score.getWidth(), score.getHeight());
        des.set((int)(360*PlayingActivity.SCALE_HORIAONTAL), 0, (int)(480*PlayingActivity.SCALE_HORIAONTAL),(int) (40 * PlayingActivity.SCALE_VERTICAL));
        canvas.drawBitmap(score, src, des, null);

        int score = gr.peopleList.get(0).score.point;
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize((int) (20 * PlayingActivity.SCALE_HORIAONTAL));
        canvas.drawText(" " + score, (int) (440 * PlayingActivity.SCALE_HORIAONTAL),
                (int) (25 * PlayingActivity.SCALE_VERTICAL), paint);



    }
/*
    private void paintPasue(Canvas canvas){
        Bitmap backgroundBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pasue_bagr);
        Bitmap pasueRestart = BitmapFactory.decodeResource(context.getResources(), R.drawable.pasue_restart);
        Bitmap pasueExit = BitmapFactory.decodeResource(context.getResources(), R.drawable.pasue_exit);
        Bitmap pasueRank = BitmapFactory.decodeResource(context.getResources(), R.drawable.pasue_rank);
        Bitmap pasueSetting = BitmapFactory.decodeResource(context.getResources(), R.drawable.pasue_setting);



        Rect src = new Rect();
        Rect des = new Rect();
        src.set(0, 0, backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
        des.set(0, 0, PlayingActivity.SCREEN_WIDTH, PlayingActivity.SCREEN_HEIGHT);
        canvas.drawBitmap(backgroundBitmap, src, des, null);

        src.set(0, 0, pasueRestart.getWidth(), chuPaiImage.getHeight());
        des.set((int) (buttonPosition_X * PlayingActivity.SCALE_HORIAONTAL),
                (int) (buttonPosition_Y * PlayingActivity.SCALE_VERTICAL),
                (int) ((buttonPosition_X + 100) * PlayingActivity.SCALE_HORIAONTAL),
                (int) ((buttonPosition_Y + 20) * PlayingActivity.SCALE_VERTICAL));
        canvas.drawBitmap(chuPaiImage, src, des, null);



    }

*/

    private void paintPasue(Canvas canvas){

        Rect src = new Rect();
        Rect des = new Rect();
        src.set(0, 0, pasue.getWidth(), pasue.getHeight());
        des.set(0, 0, 200,200);
        canvas.drawBitmap(pasue, src, des, null);
    }

    public void paint(Canvas canvas,ArrayList<People> peopleList){
        Rect src = new Rect();
        Rect des = new Rect();

        int row = -1;
        int col = -1;

        //绘制机器人的牌
        for(int i = 1; i <= 3 ; i++ ){
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(1);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            Bitmap backImage = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.card_bg);

            //画一张牌
            src.set(0, 0, backImage.getWidth(), backImage.getHeight());
            des.set((int) ((playerCardsPosition[i][0] + 22) * PlayingActivity.SCALE_HORIAONTAL),
                    (int) ((playerCardsPosition[i][1] - 20) * PlayingActivity.SCALE_VERTICAL),
                    (int) (((playerCardsPosition[i][0] + 22) + 20) * PlayingActivity.SCALE_HORIAONTAL),
                    (int) ((playerCardsPosition[i][1] + 10 ) * PlayingActivity.SCALE_VERTICAL));
            RectF rectF = new RectF(des);
            canvas.drawRoundRect(rectF, 5, 5, paint);
            canvas.drawBitmap(backImage, src, des, paint);
            //画出剩余牌数
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            paint.setTextSize((int) (20 * PlayingActivity.SCALE_HORIAONTAL));
            canvas.drawText("" + peopleList.get(i).hands.cards.size(), (int) (playerCardsPosition[i][0] * PlayingActivity.SCALE_HORIAONTAL),
                    (int) ((playerCardsPosition[i][1]) * PlayingActivity.SCALE_VERTICAL), paint);
        }

        People player = peopleList.get(0);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        for (int i = 0; i < player.hands.cards.size(); i++) {
            Card card = player.hands.cards.get(i);
            Figure fingure = card.getFigure();
            switch (fingure){
                case Ace:
                    col = 0;
                    break;
                case Two:
                    col = 1;
                    break;
                case Three:
                    col = 2;
                    break;
                case Four:
                    col = 3;
                    break;
                case Five:
                    col = 4;
                    break;
                case Six:
                    col = 5;
                    break;
                case Seven:
                    col = 6;
                    break;
                case Eight:
                    col = 7;
                    break;
                case Nine:
                    col = 8;
                    break;
                case Ten:
                    col = 9;
                    break;
                case Jack:
                    col = 10;
                    break;
                case Queen:
                    col = 11;
                    break;
                case King:
                    col = 12;
                    break;
            }
            card.getPattern();
            Pattern pattern = card.getPattern();
            switch (pattern){
                case Diamond:
                    row = 0;
                    break;
                case Club:
                    row = 1;
                    break;
                case Heart:
                    row = 2;
                    break;
                case Spade:
                    row = 3;
                    break;
            }
            Bitmap cardImage;
            cardImage = BitmapFactory.decodeResource(context.getResources(),
                    CardImage.card[row][col]);
            int select = 0;
            if (player.hands.selectedCards.contains(card)) {
                select = 10;
            }
            src.set(0, 0, cardImage.getWidth(), cardImage.getHeight());
            des.set((int) ((playerCardsPosition[0][0] + i * 20) * PlayingActivity.SCALE_HORIAONTAL),
                    (int) ((playerCardsPosition[0][1] - select) * PlayingActivity.SCALE_VERTICAL),
                    (int) ((playerCardsPosition[0][0] + 40 + i * 20) * PlayingActivity.SCALE_HORIAONTAL),
                    (int) ((playerCardsPosition[0][1] - select + 60) * PlayingActivity.SCALE_VERTICAL));
            RectF rectF = new RectF(des);
            canvas.drawRoundRect(rectF, 5, 5, paint);
            canvas.drawBitmap(cardImage, src, des, paint);

        }

    }

    private void paintTimeLimite(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setTextSize((int) (16 * PlayingActivity.SCALE_HORIAONTAL));
        for (int i = 0; i < 3; i++) {
            if (i == gr.tempOne) {
                canvas.drawText("" + (timeLimite / 10),
                        (int) (timeLimitePosition[i][0] * PlayingActivity.SCALE_HORIAONTAL),
                        (int) (timeLimitePosition[i][1] * PlayingActivity.SCALE_VERTICAL), paint);
            }
        }
    }

    //绘制牌
    public void paintCard(Canvas canvas, int left, int top, CardType cardType) {

        Rect src = new Rect();
        Rect des = new Rect();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        for (int i = 0; i < cardType.cards.size(); i++) {
            Card card = cardType.cards.get(i);
            int row = -1;
            int col = -1;
            Bitmap cardImage;
            Figure fingure = card.getFigure();
            switch (fingure){
                case Ace:
                    col = 0;
                    break;
                case Two:
                    col = 1;
                    break;
                case Three:
                    col = 2;
                    break;
                case Four:
                    col = 3;
                    break;
                case Five:
                    col = 4;
                    break;
                case Six:
                    col = 5;
                    break;
                case Seven:
                    col = 6;
                    break;
                case Eight:
                    col = 7;
                    break;
                case Nine:
                    col = 8;
                    break;
                case Ten:
                    col = 9;
                    break;
                case Jack:
                    col = 10;
                    break;
                case Queen:
                    col = 11;
                    break;
                case King:
                    col = 12;
                    break;
            }
            card.getPattern();
            Pattern pattern = card.getPattern();
            switch (pattern){
                case Diamond:
                    row = 0;
                    break;
                case Club:
                    row = 1;
                    break;
                case Heart:
                    row = 2;
                    break;
                case Spade:
                    row = 3;
                    break;
            }

            cardImage = BitmapFactory.decodeResource(context.getResources(),
                    CardImage.card[row][col]);
                src.set(0, 0, cardImage.getWidth(), cardImage.getHeight());
                des.set((int) ((left + i * 20) * PlayingActivity.SCALE_HORIAONTAL),
                        (int) (top * PlayingActivity.SCALE_VERTICAL),
                        (int) ((left + 40 + i * 20) * PlayingActivity.SCALE_HORIAONTAL),
                        (int) ((top + 60) * PlayingActivity.SCALE_VERTICAL));
            RectF rectF = new RectF(des);
            canvas.drawRoundRect(rectF, 5, 5, paint);
            canvas.drawBitmap(cardImage, src, des, paint);

        }

    }

    private void paintPass(Canvas canvas, int id) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize((int) (16 * PlayingActivity.SCALE_HORIAONTAL));
        canvas.drawText("不要", (int) (passPosition[id][0] * PlayingActivity.SCALE_HORIAONTAL),
                (int) (passPosition[id][1] * PlayingActivity.SCALE_VERTICAL), paint);

    }


    public void onTuch(int x, int y) {

        if (op == 1) {
            op = -1;
            gr.peopleList.get(0).hands.cards.clear();
            gr.peopleList.get(1).hands.cards.clear();
            gr.peopleList.get(2).hands.cards.clear();
            gr.peopleList.get(3).hands.cards.clear();
        }
        onTuchCard(x, y, player);

        if(inRect(x, y, 0, 0, 200,200)){
            System.out.println("出牌");
            op = 2 ;

        }




        if (gr.tempOne == 0) {

            if (inRect(x, y, (int) (buttonPosition_X * PlayingActivity.SCALE_HORIAONTAL),
                    (int) (buttonPosition_Y * PlayingActivity.SCALE_VERTICAL),
                    (int) (80 * PlayingActivity.SCALE_HORIAONTAL),
                    (int) (40 * PlayingActivity.SCALE_VERTICAL))) {
                System.out.println("出牌");
                ifClickChupai = true;

            }
            if (!gr.firstTurn) {
                if (inRect(x, y,
                        (int) ((buttonPosition_X - 80) * PlayingActivity.SCALE_HORIAONTAL),
                        (int) (buttonPosition_Y * PlayingActivity.SCALE_VERTICAL),
                        (int) (80 * PlayingActivity.SCALE_HORIAONTAL),
                        (int) (40 * PlayingActivity.SCALE_VERTICAL))) {

                    if(Server.myServer.flag)
                    {
                        Server.myServer.sendall(Tag.Pass+" "+Server.myServer.number);
                    }
                    else if(Cilent.myCilent.flag)
                    {
                        Cilent.myCilent.send(Tag.Pass+" "+Cilent.myCilent.number);
                    }

                    System.out.println("不要");
                    Choice choice = Choice.Pass;
                    canPass[gr.tempOne] = true;
                    gr.takeTurn(0,choice);

                }
            }
            if (inRect(x, y,
                    (int) ((buttonPosition_X + 80) * PlayingActivity.SCALE_HORIAONTAL),
                    (int) (buttonPosition_Y * PlayingActivity.SCALE_VERTICAL),
                    (int) (80 * PlayingActivity.SCALE_HORIAONTAL),
                    (int) (40 * PlayingActivity.SCALE_VERTICAL))) {
                System.out.println("重置");
                player.hands.selectedCards.clear();
            }

        }


    }


    // 点选牌
    public void onTuchCard(int x, int y ,Player player) {

        for (int i = 0; i < player.hands.cards.size(); i++) {
            if (i != player.hands.cards.size() - 1) {
                if (inRect(x, y,
                        (int) ((playerCardsPosition[0][0] + i * 20) * PlayingActivity.SCALE_HORIAONTAL),
                        (int) ((playerCardsPosition[0][1] - (player.hands.selectedCards.contains(player.hands.cards.get(i)) ? 10 : 0)) * PlayingActivity.SCALE_VERTICAL),
                        (int) (20 * PlayingActivity.SCALE_HORIAONTAL),
                        (int) (60 * PlayingActivity.SCALE_VERTICAL)) ) {
                    if(!player.hands.selectedCards.contains(player.hands.cards.get(i))) {
                        player.hands.selectedCards.add(player.hands.cards.get(i));
                        MeCards.add(new Integer(i));
                        break;
                    }
                    else
                    {
                        player.hands.selectedCards.remove(player.hands.cards.get(i));
                        MeCards.remove(new Integer(i));
                        break;
                    }
                }
            }
            else {
                if (inRect(x, y,
                        (int) ((playerCardsPosition[0][0] + i * 20) * PlayingActivity.SCALE_HORIAONTAL),
                        (int) ((playerCardsPosition[0][1] - (player.hands.selectedCards.contains(player.hands.cards.get(i)) ? 10 : 0)) * PlayingActivity.SCALE_VERTICAL),
                        (int) (40 * PlayingActivity.SCALE_HORIAONTAL),
                        (int) (60 * PlayingActivity.SCALE_VERTICAL))) {
                    if(!player.hands.selectedCards.contains(player.hands.cards.get(i))) {
                        player.hands.selectedCards.add(player.hands.cards.get(i));
                        MeCards.add(new Integer(i));
                        break;
                    }
                    else
                    {
                        player.hands.selectedCards.remove(player.hands.cards.get(i));
                        MeCards.remove(new Integer(i));
                        break;
                    }
                }
            }

        }
    }

    private void paintIconAndScore(Canvas canvas) {

        Paint paint = new Paint();
        paint.setTextSize((int) (16 * PlayingActivity.SCALE_VERTICAL));
        Rect src = new Rect();
        Rect dst = new Rect();
        for (int i = 0; i < 4; i++) {

                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                src.set(0, 0, cornImage[i].getWidth(), cornImage[i].getHeight());
                dst.set((int) (iconPosition[i][0] * PlayingActivity.SCALE_HORIAONTAL),
                        (int) (iconPosition[i][1] * PlayingActivity.SCALE_VERTICAL),
                        (int) ((iconPosition[i][0] + 50) * PlayingActivity.SCALE_HORIAONTAL),
                        (int) ((iconPosition[i][1] + 50) * PlayingActivity.SCALE_VERTICAL));
                RectF rectF = new RectF(dst);
                canvas.drawRoundRect(rectF, 5, 5, paint);
                canvas.drawBitmap(cornImage[i], src, dst, paint);
        }
    }


    public  boolean inRect(int x, int y, int rectX, int rectY, int rectW, int rectH) {
        if (x <= rectX || x >= rectX + rectW || y <= rectY || y >= rectY + rectH) {
            return false;
        }
        return true;
    }

}
