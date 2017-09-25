package com.team.battle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * In this activity we are creating our board and marking spots where we want to set our ships. We need to set ships one by one.
 * When our ship is in wanted position then we use Apply button. After that programme checks if configuration of this ship is acceptable
 * and then, if it is acceptable of course, will create outline with miss squares. That will prevent from setting a ship in invalid positions later.
 * Also applied ships and outline aren't clickable on the board. To reset our board we use Reset button.
 *
 * Submarines - 1 spot
 * Cruisers - 2 spots
 * Destroyers - 3 spots
 * Battleships - 4 spots
 *
 */
public class Prepare extends BasicActivity {

    //Debugging
    final static String TAG = "Prepare";

    final static int maxN=10;
    public static ImageView[][] ivCells =  new ImageView[maxN][maxN];

    public static Context context;
    public static Drawable[] drawCell = new Drawable[5];

    public static String nickname="";

    static int a, b, c, d, e, f, g, h, o, p;



    public static boolean finish = false;
    public boolean status = false;

    static int[] tabR = new int[19]; //table that stores rows coordinates of ships
    static int[] tabK = new int [19]; //table that stores columns coordinates of ships

    Button but, apply, res;
    static TextView jeden, dwu, trzy, cztery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_prepare);

        Intent myIntent = getIntent(); // gets the previously created intent
        nickname = myIntent.getStringExtra(Constants.NICK);
        final Boolean multiplayer = myIntent.getBooleanExtra("multi", false);

        context = this;

        loadResources();
        designBoardGame();

        jeden = (TextView) findViewById(R.id.jeden);
        dwu = (TextView) findViewById(R.id.dwu);
        trzy = (TextView) findViewById(R.id.trzy);
        cztery = (TextView) findViewById(R.id.cztery);
        jeden.setText(R.string.jedno);
        dwu.setText(R.string.dwu);
        trzy.setText(R.string.troj);
        cztery.setText(R.string.cztero);

        but = (Button) findViewById(R.id.butto);
        View.OnClickListener lb = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Counters.sing == 3 && Counters.doub == 3 && Counters.trip == 2 && Counters.quad == 1 && Counters.count == 0){
                    Log.d(TAG, "All done! onClick(), " + nickname);
                    reakcja1(nickname, multiplayer);
                }
                else{
                    Toast.makeText(context, R.string.niewszystko, Toast.LENGTH_SHORT).show();
                }
            }
        };
        but.setOnClickListener(lb);

        res = (Button) findViewById(R.id.reset);
        View.OnClickListener rst = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i =0; i<19; i++){
                    tabR[i] = 0;
                    tabK[i] = 0;
                }

                for(int j=0;j<maxN;j++){
                    for(int k=0;k<maxN;k++){
                        ivCells[j][k].setBackground(drawCell[3]);
                    }
                }
                Counters.sing =0;
                Counters.doub =0;
                Counters.trip =0;
                Counters.quad =0;
                Counters.count =0;
                jeden.setText(R.string.jedno);
                dwu.setText(R.string.dwu);
                trzy.setText(R.string.troj);
                cztery.setText(R.string.cztero);
            }
        };
        res.setOnClickListener(rst);


        apply = (Button) findViewById(R.id.apply);
        View.OnClickListener ap = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Counters.count == 0){
                    Toast.makeText(context, R.string.najpierw,Toast.LENGTH_SHORT).show();
                }

                else if (Counters.count == 1 && Counters.sing <= 2)
                {
                    finish = false;
                    status = true;
                    if(ivCells[a][b].getBackground() == drawCell[4]){
                        Config.jedyneczka();
                    }
                    else if(ivCells[a][b].getBackground() == drawCell[3]){
                        for(int i=0; i<maxN;i++){
                            for(int j=0; j<maxN; j++){
                                if(finish == false){
                                    status = true;
                                    if(ivCells[i][j].getBackground() == drawCell[4]){
                                        for(int k =0; k<19; k++) {
                                            if (i == tabR[k] && j == tabK[k]) {
                                                status = false;
                                            }
                                        }
                                        if(status){
                                            a = i;
                                            b = j;
                                            Config.jedyneczka();
                                            finish = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                else if (Counters.count == 2 && Counters.doub <= 2)
                {
                    finish = false;
                    status = true;
                    if(ivCells[a][b].getBackground() == drawCell[4]){
                        Config.dwojeczka();
                    }
                    else if(ivCells[a][b].getBackground() == drawCell[3]){
                        for(int i=0; i<maxN;i++){
                            for(int j=0; j<maxN; j++){
                                if(finish == false){
                                    status = true;
                                    if(ivCells[i][j].getBackground() == drawCell[4]){
                                        for(int k =0; k<19; k++) {
                                            if (i == tabR[k] && j == tabK[k]) {
                                                status = false;
                                            }
                                        }
                                        if(status){
                                            a = i;
                                            b = j;
                                            Config.dwojeczka();
                                            finish = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else if (Counters.count == 3 && Counters.trip <= 1)
                {
                    finish = false;
                    if(ivCells[a][b].getBackground() == drawCell[4]){
                        Config.trojeczka();
                    }
                    else if(ivCells[a][b].getBackground() == drawCell[3]){
                        for(int i=0; i<maxN;i++){
                            for(int j=0; j<maxN; j++){
                                if(finish == false){
                                    status = true;
                                    if(ivCells[i][j].getBackground() == drawCell[4]){
                                        for(int k =0; k<19; k++) {
                                            if (i == tabR[k] && j == tabK[k]) {
                                                status = false;
                                            }
                                        }
                                        if(status){
                                            a = i;
                                            b = j;
                                            Config.trojeczka();
                                            finish = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else if (Counters.count == 4 && Counters.quad == 0)
                {
                    finish = false;
                    if(ivCells[a][b].getBackground() == drawCell[4]){
                        Config.czworeczka();
                    }
                    else if(ivCells[a][b].getBackground() == drawCell[3]){
                        for(int i=0; i<maxN;i++){
                            for(int j=0; j<maxN; j++){
                                if(finish == false){
                                    status = true;
                                    if(ivCells[i][j].getBackground() == drawCell[4]){
                                        for(int k =0; k<19; k++) {
                                            if (i == tabR[k] && j == tabK[k]) {
                                                status = false;
                                            }
                                        }
                                        if(status){
                                            a = i;
                                            b = j;
                                            Config.czworeczka();
                                            finish = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                else if (Counters.count == 1 && Counters.sing > 2){
                    Toast.makeText(context, R.string.zajedno, Toast.LENGTH_SHORT).show();
                }
                else if (Counters.count == 2 && Counters.doub > 2){
                    Toast.makeText(context, R.string.zadwu, Toast.LENGTH_SHORT).show();
                }
                else if (Counters.count == 3 && Counters.trip > 1){
                    Toast.makeText(context, R.string.zatroj, Toast.LENGTH_SHORT).show();
                }
                else if (Counters.count == 4 && Counters.quad > 0){
                    Toast.makeText(context, R.string.zacztero, Toast.LENGTH_SHORT).show();
                }
            }
        };
        apply.setOnClickListener(ap);
    }

    //Methods used to create a game board
    //cells colours
    public void loadResources() {
        drawCell[4] = context.getResources().getDrawable(R.drawable.ship);
        drawCell[3] = context.getResources().getDrawable(R.drawable.empty);
        drawCell[2] = context.getResources().getDrawable(R.drawable.sunken);
        drawCell[1] = context.getResources().getDrawable(R.drawable.hit);
        drawCell[0] = context.getResources().getDrawable(R.drawable.miss);
    }

    //Calculating screen width
    public float ScreenWidth() {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();

        int a = (int) Math.round(0.9*dm.widthPixels);

        return a;
    }

    //Creating board
    @SuppressLint("NewApi")
    public void designBoardGame()  {

        int SizeOfCells = Math.round(ScreenWidth()/2/maxN);
        LinearLayout.LayoutParams lpRow = new LinearLayout.LayoutParams(SizeOfCells*maxN, SizeOfCells);
        LinearLayout.LayoutParams lpCell = new LinearLayout.LayoutParams(SizeOfCells, SizeOfCells);

        LinearLayout tablica = (LinearLayout) findViewById(R.id.tablica);

        for(int i=0; i<maxN; i++){
           LinearLayout linRow = new LinearLayout(context);
            for(int j=0; j<maxN; j++) {
                ivCells[i][j] = new ImageView(context);
                ivCells[i][j].setBackground(drawCell[3]);
                linRow.addView(ivCells[i][j], lpCell);
                final int x = i;
                final int y = j;
                ivCells[x][y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int k = 0; k < 19; k++) {
                            if (ivCells[x][y] != ivCells[tabR[k]][tabK[k]]) {
                                ivCells[x][y].getBackground();
                                if (ivCells[x][y].getBackground() == drawCell[3]) {
                                    ivCells[x][y].setBackground(drawCell[4]);
                                    Counters.counter();
                                    a = x;
                                    b = y;
                                } else if (ivCells[x][y].getBackground() == drawCell[4]) {
                                    ivCells[x][y].setBackground(drawCell[3]);
                                    Counters.discounter();
                                }
                            }
                        }
                    }
                });
                }
            tablica.addView(linRow,lpRow);
        }
    }


    //ALL DONE!!!!!!!!!!!!!
    //&
    //Transmission coordinates to next activity
    public void reakcja1(String nick, Boolean multiplayer) {
        Intent i;
        if(multiplayer){i = new Intent(this, BattleMultiActivity.class); }
        else {i = new Intent(this, BattleActivity.class); }
        i.putExtra(Constants.NICK, nick);
        i.putExtra("multi", multiplayer);
        i.putExtra("rzedy", tabR);
        i.putExtra("kolumny", tabK);
        startActivity(i);
        this.finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        Counters.count =0;
        Counters.sing = 0;
        Counters.doub = 0;
        Counters.trip = 0;
        Counters.quad = 0;
    }
}
