package com.team.battle;

import android.widget.Toast;

/**
 * This class checks if ships settled in this particular configuration can be applied on board.
 * If not it will show a proper message via Toast. If configuration is acceptable then this class will
 * save coordinates of this ship. To check the configuration this class is looking for another
 * black spots on board starting from the last marked spot.
 *
 */

public class Config extends Prepare {

    //Submarines
    static void jedyneczka(){
        Outline.komorki();
        Counters.singer();
        jeden.setText(context.getResources().getString(R.string.m1s)+": "+Integer.toString(Counters.sing)+"/3");
        Counters.count = 0;
        if (Counters.sing == 1){
            tabR[0] = c+1;
            tabK[0] = d+1;
        }
        else if(Counters.sing == 2){
            tabR[1] = c+1;
            tabK[1] = d+1;
        }
        else if(Counters.sing == 3){
            tabR[2] = c+1;
            tabK[2] = d+1;
        }

    }

    //Cruisers
    static void dwojeczka(){
        if((a-1>=0 && ivCells[a-1][b].getBackground()==drawCell[4]) ||
                (a+1<10 && ivCells[a+1][b].getBackground()==drawCell[4]) ||
                (b-1>=0 && ivCells[a][b-1].getBackground()==drawCell[4]) ||
                (b+1<10 && ivCells[a][b+1].getBackground()==drawCell[4])){
            Outline.komorki();
            Counters.doubler();
            dwu.setText(context.getResources().getString(R.string.m2s)+": "+Integer.toString(Counters.doub) + "/3");
            Counters.count = 0;

            if(Counters.doub==1){
                tabR[3] = a;
                tabK[3] = b;
                tabR[4] = e+1;
                tabK[4] = f+1;
            }

            else if(Counters.doub==2){
                tabR[5] = a;
                tabK[5] = b;
                tabR[6] = e+1;
                tabK[6] = f+1;
            }

            else if(Counters.doub==3){
                tabR[7] = a;
                tabK[7] = b;
                tabR[8] = e+1;
                tabK[8] = f+1;
            }
        }

        else {
            Toast.makeText(context, R.string.nope, Toast.LENGTH_SHORT).show();
        }
    }

    //Destroyers
    static void trojeczka(){
        finish = false;
        if((a-1>=0 && ivCells[a-1][b].getBackground()==drawCell[4]) ||
                (a+1<10 && ivCells[a+1][b].getBackground()==drawCell[4]) ||
                (b-1>=0 && ivCells[a][b-1].getBackground()==drawCell[4]) ||
                (b+1<10 && ivCells[a][b+1].getBackground()==drawCell[4])){
            final int c = a-1;
            final int d = b-1;
            for(int i =0; i<3; i++){
                for(int j=0;j<3;j++){
                    if (c + i < 10 && d + j < 10 && c + i >= 0 && d + j >= 0) {
                        if (finish == false) {
                            final int e = c + i;
                            final int f = d + j;
                            if (ivCells[e][f].getBackground() == drawCell[4] && ivCells[e][f] != ivCells[a][b]) {
                                if (((e - 1 >= 0 && ivCells[e - 1][f].getBackground() == drawCell[4]) && (ivCells[e - 1][f] != ivCells[a][b])) ||
                                        ((e + 1 < 10 && ivCells[e + 1][f].getBackground() == drawCell[4]) && (ivCells[e + 1][f] != ivCells[a][b])) ||
                                        ((f - 1 >= 0 && ivCells[e][f - 1].getBackground() == drawCell[4]) && (ivCells[e][f - 1] != ivCells[a][b])) ||
                                        ((f + 1 < 10 && ivCells[e][f + 1].getBackground() == drawCell[4]) && (ivCells[e][f + 1] != ivCells[a][b]))) {
                                    Outline.komorki();
                                    Counters.tripler();
                                    trzy.setText(context.getResources().getString(R.string.m3s)+": "+Integer.toString(Counters.trip) + "/2");
                                    Counters.count = 0;
                                    finish = true;

                                } else if (((a - 1 >= 0 && ivCells[a - 1][b].getBackground() == drawCell[4]) && (ivCells[a - 1][b] != ivCells[e][f])) ||
                                        ((a + 1 < 10 && ivCells[a + 1][b].getBackground() == drawCell[4]) && (ivCells[a + 1][b] != ivCells[e][f])) ||
                                        ((b - 1 >= 0 && ivCells[a][b - 1].getBackground() == drawCell[4]) && (ivCells[a][b - 1] != ivCells[e][f])) ||
                                        ((b + 1 < 10 && ivCells[a][b + 1].getBackground() == drawCell[4]) && (ivCells[a][b + 1] != ivCells[e][f]))) {
                                    Outline.komorki();
                                    Counters.tripler();
                                    trzy.setText(context.getResources().getString(R.string.m3s)+": "+Integer.toString(Counters.trip) + "/2");
                                    Counters.count = 0;
                                    finish = true;


                                } else {
                                    Toast.makeText(context, R.string.nope, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }
        }

        else{
            Toast.makeText(context, R.string.nope, Toast.LENGTH_SHORT).show();
        }

        if (Counters.trip == 1){
            tabR[9] = a;
            tabK[9] = b;
            tabR[10] = e+1;
            tabK[10] = f+1;
            tabR[11] = g+1;
            tabK[11] = h+1;
        }
        else if (Counters.trip == 2) {
            tabR[12] = a;
            tabK[12] = b;
            tabR[13] = e + 1;
            tabK[13] = f + 1;
            tabR[14] = g + 1;
            tabK[14] = h + 1;
        }
    }

    //Battleships
    static void czworeczka(){
        finish = false;
        if((a-1>=0 && ivCells[a-1][b].getBackground()==drawCell[4]) ||
                (a+1<10 && ivCells[a+1][b].getBackground()==drawCell[4]) ||
                (b-1>=0 && ivCells[a][b-1].getBackground()==drawCell[4]) ||
                (b+1<10 && ivCells[a][b+1].getBackground()==drawCell[4])){
            final int c = a-1;
            final int d = b-1;
            for(int i =0; i<3; i++){
                for(int j=0;j<3;j++){
                    if (c + i < 10 && d + j < 10 && c + i >= 0 && d + j >= 0) {
                        if (finish == false) {
                            final int e = c + i;
                            final int f = d + j;
                            if (ivCells[e][f].getBackground() == drawCell[4] && ivCells[e][f] != ivCells[a][b]) {
                                if (((e - 1 >= 0 && ivCells[e - 1][f].getBackground() == drawCell[4]) && (ivCells[e - 1][f] != ivCells[a][b])) ||
                                        ((e + 1 < 10 && ivCells[e + 1][f].getBackground() == drawCell[4]) && (ivCells[e + 1][f] != ivCells[a][b])) ||
                                        ((f - 1 >= 0 && ivCells[e][f - 1].getBackground() == drawCell[4]) && (ivCells[e][f - 1] != ivCells[a][b])) ||
                                        ((f + 1 < 10 && ivCells[e][f + 1].getBackground() == drawCell[4]) && (ivCells[e][f + 1] != ivCells[a][b]))) {
                                    final int e1 = e - 1;
                                    final int f1 = f - 1;
                                    for (int k = 0; k < 3; k++) {
                                        for (int l = 0; l < 3; l++) {
                                            if (e1 + k < 10 && f1 + l < 10 && e1 + k >= 0 && f1 + l >= 0) {
                                                if (finish == false) {
                                                    final int x = e1 + k;
                                                    final int y = f1 + l;
                                                    if (ivCells[x][y].getBackground() == drawCell[4] && (ivCells[x][y] != ivCells[a][b]) && (ivCells[x][y] != ivCells[e][f])) {
                                                        if (((x - 1 >= 0 && ivCells[x - 1][y].getBackground() == drawCell[4]) && (ivCells[x - 1][y] != ivCells[a][b]) && (ivCells[x - 1][y] != ivCells[e][f])) ||
                                                                ((x + 1 < 10 && ivCells[x + 1][y].getBackground() == drawCell[4]) && (ivCells[x + 1][y] != ivCells[a][b]) && (ivCells[x + 1][y] != ivCells[e][f])) ||
                                                                ((y - 1 >= 0 && ivCells[x][y - 1].getBackground() == drawCell[4]) && (ivCells[x][y - 1] != ivCells[a][b]) && (ivCells[x][y - 1] != ivCells[e][f])) ||
                                                                ((y + 1 < 10 && ivCells[x][y + 1].getBackground() == drawCell[4]) && (ivCells[x][y + 1] != ivCells[a][b]) && (ivCells[x][y + 1] != ivCells[e][f]))) {
                                                            Outline.komorki();
                                                            Counters.quader();
                                                            cztery.setText(context.getResources().getString(R.string.m4s)+": "+Integer.toString(Counters.quad)+"/1");
                                                            Counters.count = 0;
                                                            finish = true;


                                                        }

                                                        else if (((e - 1 >= 0 && ivCells[e - 1][f].getBackground() == drawCell[4]) && (ivCells[e - 1][f] != ivCells[a][b])&& (ivCells[a - 1][b] != ivCells[x][y])) ||
                                                                ((e + 1 < 10 && ivCells[e + 1][f].getBackground() == drawCell[4]) && (ivCells[e + 1][f] != ivCells[a][b])&& (ivCells[a - 1][b] != ivCells[x][y])) ||
                                                                ((f - 1 >= 0 && ivCells[e][f - 1].getBackground() == drawCell[4]) && (ivCells[e][f - 1] != ivCells[a][b])&& (ivCells[a - 1][b] != ivCells[x][y])) ||
                                                                ((f + 1 < 10 && ivCells[e][f + 1].getBackground() == drawCell[4]) && (ivCells[e][f + 1] != ivCells[a][b])&& (ivCells[a - 1][b] != ivCells[x][y]))) {

                                                            Outline.komorki();
                                                            Counters.quader();
                                                            cztery.setText(context.getResources().getString(R.string.m4s)+": "+Integer.toString(Counters.quad)+"/1");
                                                            Counters.count = 0;
                                                            finish = true;
                                                        }

                                                        else if (((a - 1 >= 0 && ivCells[a - 1][b].getBackground() == drawCell[4]) && (ivCells[a - 1][b] != ivCells[e][f])&& (ivCells[a - 1][b] != ivCells[x][y])) ||
                                                                ((a + 1 < 10 && ivCells[a + 1][b].getBackground() == drawCell[4]) && (ivCells[a + 1][b] != ivCells[e][f])&& (ivCells[a + 1][b] != ivCells[x][y])) ||
                                                                ((b - 1 >= 0 && ivCells[a][b - 1].getBackground() == drawCell[4]) && (ivCells[a][b - 1] != ivCells[e][f])&& (ivCells[a ][b - 1] != ivCells[x][y])) ||
                                                                ((b + 1 < 10 && ivCells[a][b + 1].getBackground() == drawCell[4]) && (ivCells[a][b + 1] != ivCells[e][f])&& (ivCells[a ][b + 1] != ivCells[x][y]))) {

                                                            Outline.komorki();
                                                            Counters.quader();
                                                            cztery.setText(context.getResources().getString(R.string.m4s)+": "+Integer.toString(Counters.quad)+"/1");
                                                            Counters.count = 0;
                                                            finish = true;
                                                        }

                                                        else {
                                                            Toast.makeText(context, R.string.nope, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                else if (((a - 1 >= 0 && ivCells[a - 1][b].getBackground() == drawCell[4]) && (ivCells[a - 1][b] != ivCells[e][f])) ||
                                        ((a + 1 < 10 && ivCells[a + 1][b].getBackground() == drawCell[4]) && (ivCells[a + 1][b] != ivCells[e][f])) ||
                                        ((b - 1 >= 0 && ivCells[a][b - 1].getBackground() == drawCell[4]) && (ivCells[a][b - 1] != ivCells[e][f])) ||
                                        ((b + 1 < 10 && ivCells[a][b + 1].getBackground() == drawCell[4]) && (ivCells[a][b + 1] != ivCells[e][f]))) {

                                    final int c1 = a - 1;
                                    final int d1 = b - 1;
                                    for (int k = 0; k < 3; k++) {
                                        for (int l = 0; l < 3; l++) {
                                            if (c1 + k < 10 && d1 + l < 10 && c1 + k >= 0 && d1 + l >= 0) {
                                                if (finish == false) {
                                                    final int x = c1 + k;
                                                    final int y = d1 + l;
                                                    if (ivCells[x][y].getBackground() == drawCell[4] && ivCells[x][y] != ivCells[a][b] && (ivCells[x][y] != ivCells[e][f])) {

                                                        if (((x - 1 >= 0 && ivCells[x - 1][y].getBackground() == drawCell[4]) && (ivCells[x - 1][y] != ivCells[a][b]) && (ivCells[x - 1][y] != ivCells[e][f])) ||
                                                                ((x + 1 < 10 && ivCells[x + 1][y].getBackground() == drawCell[4]) && (ivCells[x + 1][y] != ivCells[a][b]) && (ivCells[x + 1][y] != ivCells[e][f])) ||
                                                                ((y - 1 >= 0 && ivCells[x][y - 1].getBackground() == drawCell[4]) && (ivCells[x][y - 1] != ivCells[a][b]) && (ivCells[x][y - 1] != ivCells[e][f])) ||
                                                                ((y + 1 < 10 && ivCells[x][y + 1].getBackground() == drawCell[4]) && (ivCells[x][y + 1] != ivCells[a][b]) && (ivCells[x][y + 1] != ivCells[e][f]))) {

                                                            Outline.komorki();
                                                            Counters.quader();
                                                            cztery.setText(context.getResources().getString(R.string.m4s)+": "+Integer.toString(Counters.quad)+"/1");
                                                            Counters.count = 0;
                                                            finish = true;


                                                        }

                                                        else if (((a - 1 >= 0 && ivCells[a - 1][b].getBackground() == drawCell[4]) && (ivCells[a - 1][b] != ivCells[e][f])&& (ivCells[a - 1][b] != ivCells[x][y])) ||
                                                                ((a + 1 < 10 && ivCells[a + 1][b].getBackground() == drawCell[4]) && (ivCells[a + 1][b] != ivCells[e][f])&& (ivCells[a + 1][b] != ivCells[x][y])) ||
                                                                ((b - 1 >= 0 && ivCells[a][b - 1].getBackground() == drawCell[4]) && (ivCells[a][b - 1] != ivCells[e][f])&& (ivCells[a][b -1] != ivCells[x][y])) ||
                                                                ((b + 1 < 10 && ivCells[a][b + 1].getBackground() == drawCell[4]) && (ivCells[a][b + 1] != ivCells[e][f])&& (ivCells[a][b + 1] != ivCells[x][y]))) {

                                                            Outline.komorki();
                                                            Counters.quader();
                                                            cztery.setText(context.getResources().getString(R.string.m4s)+": "+Integer.toString(Counters.quad)+"/1");
                                                            Counters.count = 0;
                                                            finish = true;
                                                        }

                                                        else {
                                                            Toast.makeText(context, R.string.nope, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                else {
                                    Toast.makeText(context, R.string.nope, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }
        }
        else{
            Toast.makeText(context, R.string.nope, Toast.LENGTH_SHORT).show();
        }

        if (Counters.quad == 1){
            tabR[15] = a;
            tabK[15] = b;
            tabR[16] = e+1;
            tabK[16] = f+1;
            tabR[17] = g+1;
            tabK[17] = h+1;
            tabR[18] = o+1;
            tabK[18] = p+1;
        }
    }

}
