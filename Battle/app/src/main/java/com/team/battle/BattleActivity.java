package com.team.battle;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;
/**
 * This activity will be launched if we choose to play with computer. In the beginning, computer randomly chooses a number from 0 to 1.
 * If it is 0 then a Player starts, if 1 then a Opponent starts (computer). As a player we can choose only one spot on opponents board.
 * If we want to check other spot the we need to click on black spot again and click on another white spot. If there's a hit then we have another move.
 * If there isn't then computer starts.
 * Computer randomly chooses a coordinates to attack. Computer also stores used coordinates to prevent attacking the same spot more than once.
 * If there is a hit, computer has another move. If there isn't then we start.
 */

public class BattleActivity extends BasicActivity {

    Random rand = new Random();
    int Player = 0;
    int Opponent = 0;
    int countP = 0;
    int countO = 0;
    int c = 0;
    int[] tryX = new int[100];
    int[] tryY = new int[100];
    int move = 0;


    final static int maxN = 10;
    public  ImageView[][] opCells = new ImageView[maxN][maxN];
    public  ImageView[][] myCells = new ImageView[maxN][maxN];
    public  Drawable[] drawCell = new Drawable[5];

    Context context;
    int rows[];
    int cols[];
    TextView statki1;
    TextView statki2;

    boolean multi;
    boolean status = false;
    boolean same = false;

    Button atackButton, btButton;

    static int setX;
    static int setY;
    static int xC = 0;
    static int yC = 0;

    //Debugging
    private final static String TAG = "BattleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_battle);

        Bundle ships = getIntent().getExtras();
        rows = ships.getIntArray("rzedy");
        cols = ships.getIntArray("kolumny");

        context = this;

        CompShips.array();
        CompShips.row();
        CompShips.col();
        loadResources();
        designMyBoardGame();
        designOpBoardGame();

        Game();

        statki1 = (TextView) findViewById(R.id.statki1);
        statki1.setText(R.string.Ty);


        statki2 = (TextView) findViewById(R.id.statki2);
        statki2.setText(R.string.comp);

        atackButton = (Button) findViewById(R.id.atackButton);
        atackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttackP();
            }
        });

        //Prevent locking screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

    }


    //***************************************************
    // GRA

    public int Choose(){

        return  rand.nextInt(2);

    }

    public void Game(){
        if(!multi){
            c = Choose();
            if(c == 0){
                Player = 1;
            }

            else{
                Opponent = 1;
                AttackC();
            }
        }
    }

    public void AttackP() {

        status = false;
        for (int i = 0; i < 19; i++) {
            if (setX == CompShips.rowsC[i] && setY == CompShips.colsC[i]) {
                opCells[setX][setY].setBackground(drawCell[1]);
                status = true;
                Player = 1;
                countP++;
            }

        }
        if (status) {
            checkOp();
        }

        if (!status) {
            opCells[setX][setY].setBackground(drawCell[0]);
            Player = 0;
            Opponent = 1;
            AttackC();
        }

        if (countP == 19) {
            gameOver();
        }
    }

    public void AttackC(){
        do {
            status = false;
            same = false;
            xC = rand.nextInt(10);
            yC = rand.nextInt(10);
            for (int i = 0; i < 19; i++){
                if ((xC == rows[i] && yC == cols[i])) {
                    if (move == 0) {
                        myCells[xC][yC].setBackground(drawCell[1]);
                        status = true;
                        Opponent = 1;
                        tryX[move] = xC;
                        tryY[move] = yC;
                        move++;
                        countO++;
                        break;
                    }
                    else {
                        for (int k = 0; k <= move; k++) {
                            if (xC != tryX[k] && yC != tryY[k]) {
                                same = false;
                            } else if (xC == tryX[k] && yC == tryY[k]) {
                                same = true;
                                break;
                            }

                        }
                        if (!same) {
                            myCells[xC][yC].setBackground(drawCell[1]);
                            status = true;
                            Opponent = 1;
                            tryX[move] = xC;
                            tryY[move] = yC;
                            move++;
                            countO++;
                            break;
                        }
                    }
                }
            }

            if(status){
                checkMe();
            }

            if (!status) {
                if(move == 0){
                    myCells[xC][yC].setBackground(drawCell[0]);
                    Player = 1;
                    Opponent = 0;
                    tryX[move] = xC;
                    tryY[move] = yC;
                    move++;
                }
                else{
                    for(int k = 0; k <=move; k++){
                        if(xC != tryX[k]  && yC != tryY[k]){
                            same = false;
                        }
                        else if(xC == tryX[k]  && yC == tryY[k]){
                            same = true;
                            break;
                        }
                    }

                    if(!same){
                        myCells[xC][yC].setBackground(drawCell[0]);
                        Player = 1;
                        Opponent = 0;
                        tryX[move] = xC;
                        tryY[move] = yC;
                        move++;
                    }
                }
            }
        }while(Opponent == 1);

        if(countO == 19){
            gameOver();
        }
    }

    public void gameOver(){
        AlertDialog.Builder a_build = new AlertDialog.Builder(BattleActivity.this);
        final Intent i = new Intent(this, StartActivity.class);
        if (status) {
            a_build.setMessage(R.string.wygrana)
                    .setCancelable(false)
                    .setPositiveButton(R.string.tak, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(i);
                        }
                    })
                    .setNegativeButton(R.string.nie, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            AlertDialog alert = a_build.create();
            alert.setTitle(R.string.koniec);
            alert.show();
        } else {
            a_build.setMessage(R.string.przegrana)
                    .setCancelable(false)
                    .setPositiveButton(R.string.tak, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(i);
                        }
                    })
                    .setNegativeButton(R.string.nie, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            AlertDialog alert = a_build.create();
            alert.setTitle(R.string.koniec);
            alert.show();

        }
    }

    public void checkMe(){
        if(myCells[rows[0]][cols[0]].getBackground() == drawCell[1]){
            myCells[rows[0]][cols[0]].setBackground(drawCell[2]);
        }
        else if(myCells[rows[1]][cols[1]].getBackground() == drawCell[1]){
            myCells[rows[1]][cols[1]].setBackground(drawCell[2]);
        }

        else if(myCells[rows[2]][cols[2]].getBackground() == drawCell[1]){
            myCells[rows[2]][cols[2]].setBackground(drawCell[2]);
        }

        else if(myCells[rows[3]][cols[3]].getBackground() == drawCell[1] && myCells[rows[4]][cols[4]].getBackground() == drawCell[1] ){
            myCells[rows[3]][cols[3]].setBackground(drawCell[2]);
            myCells[rows[4]][cols[4]].setBackground(drawCell[2]);
        }

        else if(myCells[rows[5]][cols[5]].getBackground() == drawCell[1] && myCells[rows[6]][cols[6]].getBackground() == drawCell[1] ){
            myCells[rows[5]][cols[5]].setBackground(drawCell[2]);
            myCells[rows[6]][cols[6]].setBackground(drawCell[2]);
        }

        else if(myCells[rows[7]][cols[7]].getBackground() == drawCell[1] && myCells[rows[8]][cols[8]].getBackground() == drawCell[1] ){
            myCells[rows[7]][cols[7]].setBackground(drawCell[2]);
            myCells[rows[8]][cols[8]].setBackground(drawCell[2]);
        }

        else if(myCells[rows[9]][cols[9]].getBackground() == drawCell[1] && myCells[rows[10]][cols[10]].getBackground() == drawCell[1]
                && myCells[rows[11]][cols[11]].getBackground() == drawCell[1]){
            myCells[rows[9]][cols[9]].setBackground(drawCell[2]);
            myCells[rows[10]][cols[10]].setBackground(drawCell[2]);
            myCells[rows[11]][cols[11]].setBackground(drawCell[2]);
        }
        else if(myCells[rows[12]][cols[12]].getBackground() == drawCell[1] && myCells[rows[13]][cols[13]].getBackground() == drawCell[1]
                && myCells[rows[14]][cols[14]].getBackground() == drawCell[1]){
            myCells[rows[12]][cols[12]].setBackground(drawCell[2]);
            myCells[rows[13]][cols[13]].setBackground(drawCell[2]);
            myCells[rows[14]][cols[14]].setBackground(drawCell[2]);
        }
        else if(myCells[rows[15]][cols[15]].getBackground() == drawCell[1] && myCells[rows[16]][cols[16]].getBackground() == drawCell[1]
                && myCells[rows[17]][cols[17]].getBackground() == drawCell[1] && myCells[rows[18]][cols[18]].getBackground() == drawCell[1]){
            myCells[rows[15]][cols[15]].setBackground(drawCell[2]);
            myCells[rows[16]][cols[16]].setBackground(drawCell[2]);
            myCells[rows[17]][cols[17]].setBackground(drawCell[2]);
            myCells[rows[18]][cols[18]].setBackground(drawCell[2]);
        }

    }

    public void checkOp(){
        if(opCells[CompShips.rowsC[0]][CompShips.colsC[0]].getBackground() == drawCell[1]){
            opCells[CompShips.rowsC[0]][CompShips.colsC[0]].setBackground(drawCell[2]);
        }
        else if(opCells[CompShips.rowsC[1]][CompShips.colsC[1]].getBackground() == drawCell[1]){
            opCells[CompShips.rowsC[1]][CompShips.colsC[1]].setBackground(drawCell[2]);
        }
        else if(opCells[CompShips.rowsC[2]][CompShips.colsC[2]].getBackground() == drawCell[1]){
            opCells[CompShips.rowsC[2]][CompShips.colsC[2]].setBackground(drawCell[2]);
        }
        else if(opCells[CompShips.rowsC[3]][CompShips.colsC[3]].getBackground() == drawCell[1] && opCells[CompShips.rowsC[4]][CompShips.colsC[4]].getBackground() == drawCell[1]){
            opCells[CompShips.rowsC[3]][CompShips.colsC[3]].setBackground(drawCell[2]);
            opCells[CompShips.rowsC[4]][CompShips.colsC[4]].setBackground(drawCell[2]);
        }
        else if(opCells[CompShips.rowsC[5]][CompShips.colsC[5]].getBackground() == drawCell[1] && opCells[CompShips.rowsC[6]][CompShips.colsC[6]].getBackground() == drawCell[1]){
            opCells[CompShips.rowsC[5]][CompShips.colsC[5]].setBackground(drawCell[2]);
            opCells[CompShips.rowsC[6]][CompShips.colsC[6]].setBackground(drawCell[2]);
        }
        else if(opCells[CompShips.rowsC[7]][CompShips.colsC[7]].getBackground() == drawCell[1] && opCells[CompShips.rowsC[8]][CompShips.colsC[8]].getBackground() == drawCell[1]){
            opCells[CompShips.rowsC[7]][CompShips.colsC[7]].setBackground(drawCell[2]);
            opCells[CompShips.rowsC[8]][CompShips.colsC[8]].setBackground(drawCell[2]);
        }
        else if(opCells[CompShips.rowsC[9]][CompShips.colsC[9]].getBackground() == drawCell[1] && opCells[CompShips.rowsC[10]][CompShips.colsC[10]].getBackground() == drawCell[1]
                && opCells[CompShips.rowsC[11]][CompShips.colsC[11]].getBackground() == drawCell[1]){
            opCells[CompShips.rowsC[9]][CompShips.colsC[9]].setBackground(drawCell[2]);
            opCells[CompShips.rowsC[10]][CompShips.colsC[10]].setBackground(drawCell[2]);
            opCells[CompShips.rowsC[11]][CompShips.colsC[11]].setBackground(drawCell[2]);
        }
        else if(opCells[CompShips.rowsC[12]][CompShips.colsC[12]].getBackground() == drawCell[1] && opCells[CompShips.rowsC[13]][CompShips.colsC[13]].getBackground() == drawCell[1]
                && opCells[CompShips.rowsC[14]][CompShips.colsC[14]].getBackground() == drawCell[1]){
            opCells[CompShips.rowsC[12]][CompShips.colsC[12]].setBackground(drawCell[2]);
            opCells[CompShips.rowsC[13]][CompShips.colsC[13]].setBackground(drawCell[2]);
            opCells[CompShips.rowsC[14]][CompShips.colsC[14]].setBackground(drawCell[2]);
        }
        else if(opCells[CompShips.rowsC[15]][CompShips.colsC[15]].getBackground() == drawCell[1] && opCells[CompShips.rowsC[16]][CompShips.colsC[16]].getBackground() == drawCell[1]
                && opCells[CompShips.rowsC[17]][CompShips.colsC[17]].getBackground() == drawCell[1] && opCells[CompShips.rowsC[18]][CompShips.colsC[18]].getBackground() == drawCell[1]){
            opCells[CompShips.rowsC[15]][CompShips.colsC[15]].setBackground(drawCell[2]);
            opCells[CompShips.rowsC[16]][CompShips.colsC[16]].setBackground(drawCell[2]);
            opCells[CompShips.rowsC[17]][CompShips.colsC[17]].setBackground(drawCell[2]);
            opCells[CompShips.rowsC[18]][CompShips.colsC[18]].setBackground(drawCell[2]);
        }



    }




    //****************************************************************************
    //

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy()");
        gameOver();
    }

    @Override
    public void onResume() {
        super.onResume();


        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart()");
    }


    //******************************************************
    //PLANSZE


    private void designOpBoardGame()  {

        int SizeOfCells = Math.round(ScreenWidth()/2/maxN);
        LinearLayout.LayoutParams lpRow = new LinearLayout.LayoutParams(SizeOfCells*maxN, SizeOfCells);
        LinearLayout.LayoutParams lpCell = new LinearLayout.LayoutParams(SizeOfCells, SizeOfCells);

        LinearLayout tablica = (LinearLayout) findViewById(R.id.tablica2);

        for(int i=0; i<maxN; i++){
            LinearLayout linRow = new LinearLayout(context);
            for(int j=0; j<maxN; j++) {
                opCells[i][j] = new ImageView(context);
                opCells[i][j].setBackground(drawCell[3]);
                linRow.addView(opCells[i][j], lpCell);
                final int x = i;
                final int y = j;
                opCells[x][y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (opCells[x][y].getBackground() == drawCell[3] && Player == 1) {
                            opCells[x][y].setBackground(drawCell[4]);
                            Player = 0;
                            setX = x;
                            setY = y;

                        } else if (opCells[x][y].getBackground() == drawCell[4] && Player == 0) {
                            opCells[x][y].setBackground(drawCell[3]);
                            Player = 1;
                        }
                    }
                });
            }
            tablica.addView(linRow,lpRow);
        }
    }


    private void designMyBoardGame()  {

        int SizeOfCells = Math.round(ScreenWidth()/2/maxN);
        LinearLayout.LayoutParams lpRow = new LinearLayout.LayoutParams(SizeOfCells*maxN, SizeOfCells);
        LinearLayout.LayoutParams lpCell = new LinearLayout.LayoutParams(SizeOfCells, SizeOfCells);

        LinearLayout tablica = (LinearLayout) findViewById(R.id.tablica1);

        for(int i=0; i<maxN; i++){
            LinearLayout linRow = new LinearLayout(context);
            for(int j=0; j<maxN; j++) {
                myCells[i][j] = new ImageView(context);
                myCells[i][j].setBackground(drawCell[3]);
                linRow.addView(myCells[i][j], lpCell);

            }
            tablica.addView(linRow,lpRow);
        }
        for (int k = 0; k < 19; k++) {
            myCells[rows[k]][cols[k]].setBackground(drawCell[4]);
        }

    }

    //METODY DO STWORZENIA PLANSZY
    //KOLORKI KOMOREK
    public void loadResources() {
        drawCell[4] = context.getResources().getDrawable(R.drawable.statek);
        drawCell[3] = context.getResources().getDrawable(R.drawable.bloczek);
        drawCell[2] = context.getResources().getDrawable(R.drawable.zatopiony);
        drawCell[1] = context.getResources().getDrawable(R.drawable.trafiony);
        drawCell[0] = context.getResources().getDrawable(R.drawable.pudlo);
    }

    //OBLICZANIE SZEROKOSCI EKRANU
    public float ScreenWidth() {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();

        int a = (int) Math.round(0.9*dm.widthPixels);

        return a;
    }
}