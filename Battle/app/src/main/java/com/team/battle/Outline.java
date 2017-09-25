package com.team.battle;

/**
 * This class creates an outline of miss squares to prevent setting ships in invalid position later.
 */

public class Outline extends Prepare {


    public static void komorki() {
        //Submarines
        if (Counters.count == 1) {
            c = a - 1;
            d = b - 1;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (c + i < 10 && d + j < 10 && c + i >= 0 && d + j >= 0) {
                        if (ivCells[c + i][d + j] != ivCells[a][b]) {
                            if (ivCells[c + i][d + j].getBackground() == drawCell[3] || ivCells[c + i][d + j].getBackground() == drawCell[0]) {
                                ivCells[c + i][d + j].setBackground(drawCell[0]);
                            }
                        }
                    }
                }
            }
        }
        //Cruisers
        else if (Counters.count == 2) {
            c = a - 1;
            d = b - 1;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (c + i < 10 && d + j < 10 && c + i >= 0 && d + j >= 0) {
                        if (ivCells[c + i][d + j] != ivCells[a][b]) {
                            if (ivCells[c + i][d + j].getBackground() == drawCell[3] || ivCells[c + i][d + j].getBackground() == drawCell[0]) {
                                ivCells[c + i][d + j].setBackground(drawCell[0]);
                            } else if (ivCells[c + i][d + j].getBackground() == drawCell[4]) {

                                e = (c + i) - 1;
                                f = (d + j) - 1;
                                for (int k = 0; k < 3; k++) {
                                    for (int l = 0; l < 3; l++) {
                                        if (e + k < 10 && f + l < 10 && e + k >= 0 && f + l >= 0) {
                                            if (ivCells[e + k][f + l] != ivCells[c + i][d + j]) {
                                                if (ivCells[e + k][f + l].getBackground() == drawCell[3] || ivCells[e + k][f + l].getBackground() == drawCell[0]) {
                                                    ivCells[e + k][f + l].setBackground(drawCell[0]);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //Destroyers
        else if (Counters.count == 3) {
            c = a - 1;
            d = b - 1;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (c + i < 10 && d + j < 10 && c + i >= 0 && d + j >= 0) {
                        if (ivCells[c + i][d + j] != ivCells[a][b]) {
                            if (ivCells[c + i][d + j].getBackground() == drawCell[3] || ivCells[c + i][d + j].getBackground() == drawCell[0]) {
                                ivCells[c + i][d + j].setBackground(drawCell[0]);
                            } else if (ivCells[c + i][d + j].getBackground() == drawCell[4]) {
                                e = (c + i) - 1;
                                f = (d + j) - 1;
                                for (int k = 0; k < 3; k++) {
                                    for (int l = 0; l < 3; l++) {
                                        if (e + k < 10 && f + l < 10 && e + k >= 0 && f + l >= 0) {
                                            if (ivCells[e + k][f + l] != ivCells[c + i][d + j] && ivCells[e + k][f + l] != ivCells[a][b]) {
                                                if (ivCells[e + k][f + l].getBackground() == drawCell[3] || ivCells[e + k][f + l].getBackground() == drawCell[0]) {
                                                    ivCells[e + k][f + l].setBackground(drawCell[0]);
                                                } else if (ivCells[e + k][f + l].getBackground() == drawCell[4]) {
                                                    g = (e + k) - 1;
                                                    h = (f + l) - 1;
                                                    for (int m = 0; m < 3; m++) {
                                                        for (int n = 0; n < 3; n++) {
                                                            if (g + m < 10 && h + n < 10 && g + m >= 0 && h + n >= 0) {
                                                                if (ivCells[g + m][h + n] != ivCells[e + k][f + l]) {
                                                                    if (ivCells[g + m][h + n].getBackground() == drawCell[3] || ivCells[g + m][h + n].getBackground() == drawCell[0]) {
                                                                        ivCells[g + m][h + n].setBackground(drawCell[0]);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //Battleships
        else if (Counters.count == 4){
            c = a - 1;
            d = b - 1;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (c + i < 10 && d + j < 10 && c + i >= 0 && d + j >= 0) {
                        if (ivCells[c + i][d + j] != ivCells[a][b]) {
                            if (ivCells[c + i][d + j].getBackground() == drawCell[3] || ivCells[c + i][d + j].getBackground() == drawCell[0]) {
                                ivCells[c + i][d + j].setBackground(drawCell[0]);
                            } else if (ivCells[c + i][d + j].getBackground() == drawCell[4]) {
                                e = (c + i) - 1;
                                f = (d + j) - 1;
                                for (int k = 0; k < 3; k++) {
                                    for (int l = 0; l < 3; l++) {
                                        if (e + k < 10 && f + l < 10 && e + k >= 0 && f + l >= 0) {
                                            if (ivCells[e + k][f + l] != ivCells[c + i][d + j] && ivCells[e+k][f+l] != ivCells[a][b]) {
                                                if (ivCells[e + k][f + l].getBackground() == drawCell[3] || ivCells[e + k][f + l].getBackground() == drawCell[0]) {
                                                    ivCells[e + k][f + l].setBackground(drawCell[0]);
                                                } else if (ivCells[e + k][f + l].getBackground() == drawCell[4]) {
                                                    g = (e + k) - 1;
                                                    h = (f + l) - 1;
                                                    for (int m = 0; m < 3; m++) {
                                                        for (int n = 0; n < 3; n++) {
                                                            if (g + m < 10 && h + n < 10 && g + m >= 0 && h + n >= 0) {
                                                                if (ivCells[g + m][h + n] != ivCells[e + k][f + l] && ivCells[g + m][h + n] != ivCells[a][b] && ivCells[g + m][h + n] != ivCells[c+i][d+j] ) {
                                                                    if (ivCells[g + m][h + n].getBackground() == drawCell[3] || ivCells[g + m][h + n].getBackground() == drawCell[0]) {
                                                                        ivCells[g + m][h + n].setBackground(drawCell[0]);
                                                                    }
                                                                    else if (ivCells[g + m][h + n].getBackground() == drawCell[4]) {
                                                                        o = (g + m) - 1;
                                                                        p = (h + n) - 1;
                                                                        for (int q = 0; q < 3; q++) {
                                                                            for (int z = 0; z < 3; z++) {
                                                                                if (o + q < 10 && p + z < 10 && o + q >= 0 && p + z >= 0) {
                                                                                    if (ivCells[o + q][p + z] != ivCells[g + m][h + n]) {
                                                                                        if (ivCells[o + q][p + z].getBackground() == drawCell[3] || ivCells[o + q][p + z].getBackground() == drawCell[0]) {
                                                                                            ivCells[o + q][p + z].setBackground(drawCell[0]);
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
