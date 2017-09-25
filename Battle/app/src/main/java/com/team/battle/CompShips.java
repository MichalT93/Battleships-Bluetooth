package com.team.battle;

import java.util.Random;

/**
 * This class creates a table of random coordinates for computer.
 */

public class CompShips {

    static int arrayC[][] = new int[19][2];


    static int rowsC[] = new int[19];
    static int colsC[] = new int[19];
    static Random rand = new Random();

    static int sing = 3, doub = 3, trip = 2, quad = 1;


    static boolean status, góra, dół, lewo, prawo;


    static int[][] array() {
        quadC();
        tripleC();
        doubleC();
        singleC();
        return arrayC;
    }

    static void quadC() {
        do {
            status = true;
            góra = true;
            dół = true;
            lewo = true;
            prawo = true;
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);

            int check = rand.nextInt(4);

            if (check == 0 && x - 1 >= 0) {
                if (x - 2 >= 0) {
                    if (x - 3 >= 0) {
                        arrayC[15][0] = x;
                        arrayC[15][1] = y;
                        arrayC[16][0] = x - 1;
                        arrayC[16][1] = y;
                        arrayC[17][0] = x - 2;
                        arrayC[17][1] = y;
                        arrayC[18][0] = x - 3;
                        arrayC[18][1] = y;
                    }
                }
            } else if (check == 1 && y + 1 < 10 && prawo) {
                if (y + 2 < 10) {
                    if (y + 3 < 10) {
                        arrayC[15][0] = x;
                        arrayC[15][1] = y;
                        arrayC[16][0] = x;
                        arrayC[16][1] = y + 1;
                        arrayC[17][0] = x;
                        arrayC[17][1] = y + 2;
                        arrayC[18][0] = x;
                        arrayC[18][1] = y + 3;
                        quad--;

                    }
                }

            } else if (check == 2 && x + 1 < 10) {
                if (x + 2 < 10) {
                    if (x - 2 >= 0) {
                        arrayC[15][0] = x;
                        arrayC[15][1] = y;
                        arrayC[16][0] = x + 1;
                        arrayC[16][1] = y;
                        arrayC[17][0] = x + 2;
                        arrayC[17][1] = y;
                        arrayC[18][0] = x + 3;
                        arrayC[18][1] = y;
                        quad--;
                    }
                }

            } else if (check == 3 && y - 1 >= 0) {
                if (y - 2 >= 0) {
                    if (y - 3 >= 0) {
                        arrayC[15][0] = x;
                        arrayC[15][1] = y;
                        arrayC[16][0] = x;
                        arrayC[16][1] = y - 1;
                        arrayC[17][0] = x;
                        arrayC[17][1] = y - 2;
                        arrayC[18][0] = x;
                        arrayC[18][1] = y - 3;
                        quad--;
                    }
                }
            }
        }while(quad>0);
    }

    static void tripleC() {
        do {
            if (trip == 2) {
                status = true;
                int x = rand.nextInt(10);
                int y = rand.nextInt(10);
                final int a = x - 1;
                final int b = y - 1;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (status) {
                            for (int k = 18; k > 14; k--) {
                                if (a + i == arrayC[k][0] && b + j == arrayC[k][1]) {     //SPRAWDZANIE OBRZEZA I SAMEGO POLA CZY JEST DOPUSZCALNE
                                    status = false;
                                }
                            }
                        }
                    }
                }
                if (status) {           //KIEDY POLOZENIE POCZATKOWE (X,Y) JEST DOPUSZCZALNE
                    status = false;
                    góra = true;
                    dół = true;
                    lewo = true;
                    prawo = true;
                    do {
                        int check = rand.nextInt(4);
                        if (check == 0 && x - 1 >= 0 && góra) {
                            int a1 = x - 2;
                            int b1 = y - 1;
                            for (int i = 0; i < 3; i++) {
                                for (int k = 18; k > 11; k--) {
                                    if (a1 == arrayC[k][0] && b1 + i == arrayC[k][1]) {
                                        góra = false;
                                    }
                                }
                            }
                            if (góra) {
                                status = true;
                            }
                        } else if (check == 1 && y + 1 < 10 && prawo) {
                            int a1 = x - 1;
                            int b1 = y + 2;
                            for (int i = 0; i < 3; i++) {
                                for (int k = 18; k > 11; k--) {
                                    if (a1 + i == arrayC[k][0] && b1 == arrayC[k][1]) {
                                        prawo = false;
                                    }
                                }
                            }
                            if (prawo) {
                                status = true;
                            }
                        } else if (check == 2 && x + 1 < 10 && dół) {
                            int a1 = x + 2;
                            int b1 = y - 1;
                            for (int i = 0; i < 3; i++) {
                                for (int k = 18; k > 11; k--) {
                                    if (a1 == arrayC[k][0] && b1 + i == arrayC[k][1]) {
                                        dół = false;
                                    }
                                }
                            }
                            if (dół) {
                                status = true;
                            }
                        } else if (check == 3 && y - 1 >= 0 && lewo) {
                            int a1 = x - 1;
                            int b1 = y - 2;
                            for (int i = 0; i < 3; i++) {
                                for (int k = 18; k > 11; k--) {
                                    if (a1 + i == arrayC[k][0] && b1 == arrayC[k][1]) {
                                        lewo = false;
                                    }
                                }
                            }
                            if (lewo) {
                                status = true;
                            }
                        }

                        if (status) {
                            if (check == 0 && góra && x - 2 >= 0) {
                                int a1 = x - 3;
                                int b1 = y - 1;
                                for (int i = 0; i < 3; i++) {
                                    for (int k = 18; k > 11; k--) {
                                        if (a1 == arrayC[k][0] && b1 + i == arrayC[k][1]) {
                                            góra = false;
                                        }
                                    }
                                }
                                if (góra) {
                                    arrayC[9][0] = x;
                                    arrayC[9][1] = y;
                                    arrayC[10][0] = x - 1;
                                    arrayC[10][1] = y;
                                    arrayC[11][0] = x - 2;
                                    arrayC[11][1] = y;
                                    trip--;
                                }
                            } else if (check == 1 && prawo && y + 2 < 10) {
                                int a1 = x - 1;
                                int b1 = y + 3;
                                for (int i = 0; i < 3; i++) {
                                    for (int k = 18; k > 11; k--) {
                                        if (a1 + i == arrayC[k][0] && b1 == arrayC[k][1]) {
                                            prawo = false;
                                        }
                                    }
                                }
                                if (prawo) {
                                    arrayC[9][0] = x;
                                    arrayC[9][1] = y;
                                    arrayC[10][0] = x;
                                    arrayC[10][1] = y + 1;
                                    arrayC[11][0] = x;
                                    arrayC[11][1] = y + 2;
                                    trip--;
                                }

                            } else if (check == 2 && dół && x + 2 < 10) {
                                int a1 = x + 3;
                                int b1 = y - 1;
                                for (int i = 0; i < 3; i++) {
                                    for (int k = 18; k > 11; k--) {
                                        if (a1 == arrayC[k][0] && b1 + i == arrayC[k][1]) {
                                            dół = false;
                                        }
                                    }
                                }
                                if (dół) {
                                    arrayC[9][0] = x;
                                    arrayC[9][1] = y;
                                    arrayC[10][0] = x + 1;
                                    arrayC[10][1] = y;
                                    arrayC[11][0] = x + 2;
                                    arrayC[11][1] = y;
                                    trip--;
                                }

                            } else if (check == 3 && lewo && y - 2 >= 0) {
                                int a1 = x - 1;
                                int b1 = y - 3;
                                for (int i = 0; i < 3; i++) {
                                    for (int k = 18; k > 11; k--) {
                                        if (a1 + i == arrayC[k][0] && b1 == arrayC[k][1]) {
                                            lewo = false;
                                        }
                                    }
                                }
                                if (lewo) {
                                    arrayC[9][0] = x;
                                    arrayC[9][1] = y;
                                    arrayC[10][0] = x;
                                    arrayC[10][1] = y - 1;
                                    arrayC[11][0] = x;
                                    arrayC[11][1] = y - 2;
                                    trip--;
                                }
                            }
                        } else if (!góra && !dół && !prawo && !lewo) {
                            break;
                        }
                    } while (trip > 1);
                }
            }else if (trip == 1) {
                        status = true;
                        int x = rand.nextInt(10);
                        int y = rand.nextInt(10);
                        final int a = x - 1;
                        final int b = y - 1;
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                if (status) {
                                    for (int k = 18; k > 14; k--) {
                                        if (a + i == arrayC[k][0] && b + j == arrayC[k][1]) {     //SPRAWDZANIE OBRZEZA I SAMEGO POLA CZY JEST DOPUSZCALNE
                                            status = false;
                                        }
                                    }
                                }
                            }
                        }
                        if(status) {
                            status = false;
                            góra = true;
                            dół = true;
                            lewo = true;
                            prawo = true;
                            do {
                                int check = rand.nextInt(4);
                                if (check == 0 && x - 1 >= 0 && góra) {
                                    int a1 = x - 2;
                                    int b1 = y - 1;
                                    for (int i = 0; i < 3; i++) {
                                        for (int k = 18; k > 14; k--) {
                                            if (a1 == arrayC[k][0] && b1 + i == arrayC[k][1]) {
                                                góra = false;
                                            }
                                        }
                                    }
                                    if (góra) {
                                        status = true;
                                    }
                                } else if (check == 1 && y + 1 < 10 && prawo) {
                                    int a1 = x - 1;
                                    int b1 = y + 2;
                                    for (int i = 0; i < 3; i++) {
                                        for (int k = 18; k > 14; k--) {
                                            if (a1 + i == arrayC[k][0] && b1 == arrayC[k][1]) {
                                                prawo = false;
                                            }
                                        }
                                    }
                                    if (prawo) {
                                        status = true;
                                    }
                                } else if (check == 2 && x + 1 < 10 && dół) {
                                    for (int i = 0; i < 3; i++) {
                                        for (int k = 18; k > 14; k--) {
                                            int a1 = x + 2;
                                            int b1 = y - 1;
                                            if (a1 == arrayC[k][0] && b1 + i == arrayC[k][1]) {
                                                dół = false;
                                            }
                                        }
                                        if (dół) {
                                            status = true;
                                        }
                                    }
                                } else if (check == 3 && y - 1 >= 0 && lewo) {
                                    int a1 = x - 1;
                                    int b1 = y - 2;
                                    for (int i = 0; i < 3; i++) {
                                        for (int k = 18; k > 14; k--) {
                                            if (a1 + i == arrayC[k][0] && b1 == arrayC[k][1]) {
                                                lewo = false;
                                            }
                                        }
                                    }
                                    if (lewo) {
                                        status = true;
                                    }
                                }
                                if (status) {   //JESLI ZNALAZL DRUGI DOPUSZCZALNY
                                    if (check == 0 && góra && x - 2 >= 0) {
                                        int a1 = x - 3;
                                        int b1 = y - 1;
                                        for (int i = 0; i < 3; i++) {
                                            for (int k = 18; k > 14; k--) {
                                                if (a1 == arrayC[k][0] && b1 + i == arrayC[k][1]) {
                                                    góra = false;
                                                }
                                            }
                                        }
                                        if (góra) {
                                            arrayC[12][0] = x;
                                            arrayC[12][1] = y;
                                            arrayC[13][0] = x - 1;
                                            arrayC[13][1] = y;
                                            arrayC[14][0] = x - 2;
                                            arrayC[14][1] = y;
                                            trip--;
                                        }
                                    } else if (check == 1 && prawo && y + 2 < 10) {
                                        int a1 = x - 1;
                                        int b1 = y + 3;
                                        for (int i = 0; i < 3; i++) {
                                            for (int k = 18; k > 14; k--) {
                                                if (a1 + i == arrayC[k][0] && b1 == arrayC[k][1]) {
                                                    prawo = false;
                                                }
                                            }
                                        }
                                        if (prawo) {
                                            arrayC[12][0] = x;
                                            arrayC[12][1] = y;
                                            arrayC[13][0] = x;
                                            arrayC[13][1] = y + 1;
                                            arrayC[14][0] = x;
                                            arrayC[14][1] = y + 2;
                                            trip--;
                                        }

                                    } else if (check == 2 && dół && x + 2 < 10) {
                                        int a1 = x + 3;
                                        int b1 = y - 1;
                                        for (int i = 0; i < 3; i++) {
                                            for (int k = 18; k > 14; k--) {
                                                if (a1 == arrayC[k][0] && b1 + i == arrayC[k][1]) {
                                                    dół = false;
                                                }
                                            }
                                        }
                                        if (dół) {
                                            arrayC[12][0] = x;
                                            arrayC[12][1] = y;
                                            arrayC[13][0] = x + 1;
                                            arrayC[13][1] = y;
                                            arrayC[14][0] = x + 2;
                                            arrayC[14][1] = y;
                                            trip--;
                                        }

                                    } else if (check == 3 && lewo && y - 2 >= 0) {
                                        int a1 = x - 1;
                                        int b1 = y - 3;
                                        for (int i = 0; i < 3; i++) {
                                            for (int k = 18; k > 14; k--) {
                                                if (a1 + i == arrayC[k][0] && b1 == arrayC[k][1]) {
                                                    lewo = false;
                                                }
                                            }
                                        }
                                        if (lewo) {
                                            arrayC[12][0] = x;
                                            arrayC[12][1] = y;
                                            arrayC[13][0] = x;
                                            arrayC[13][1] = y - 1;
                                            arrayC[14][0] = x;
                                            arrayC[14][1] = y - 2;
                                            trip--;
                                        }
                                    }
                                } else if (!góra && !dół && !prawo && !lewo) {
                                    break;
                                }
                            } while (trip > 0);
                        }
                    }
        } while (trip > 0);
    }

    static void doubleC() {
        do {
            if(doub == 3) {
                status = true;
                int x = rand.nextInt(10);
                int y = rand.nextInt(10);
                final int a = x - 1;
                final int b = y - 1;

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (status) {
                            for (int k = 18; k > 8; k--) {
                                if (a + i == arrayC[k][0] && b + j == arrayC[k][1]) {     //SPRAWDZANIE OBRZEZA I SAMEGO POLA CZY JEST DOPUSZCALNE
                                    status = false;
                                }
                            }
                        }
                    }
                }
                if(status) {
                    góra = true;
                    dół = true;
                    lewo = true;
                    prawo = true;
                    do {
                        int check = rand.nextInt(4);
                        if (check == 0 && x - 1 >= 0 && góra) {
                            int a1 = x - 2;
                            int b1 = y - 1;
                            for (int i = 0; i < 3; i++) {
                                for (int k = 18; k > 8; k--) {
                                    if (a1 == arrayC[k][0] && b1 + i == arrayC[k][1]) {
                                        góra = false;
                                    }
                                }
                            }
                            if (góra) {
                                arrayC[7][0] = x;
                                arrayC[7][1] = y;
                                arrayC[8][0] = x - 1;
                                arrayC[8][1] = y;
                                doub--;
                            }
                        } else if (check == 1 && y + 1 < 10 && prawo) {
                            int a1 = x - 1;
                            int b1 = y + 2;
                            for (int i = 0; i < 3; i++) {
                                for (int k = 18; k > 8; k--) {
                                    if (a1 + i == arrayC[k][0] && b1 == arrayC[k][1]) {
                                        prawo = false;
                                    }
                                }
                            }
                            if (prawo) {
                                arrayC[7][0] = x;
                                arrayC[7][1] = y;
                                arrayC[8][0] = x;
                                arrayC[8][1] = y + 1;
                                doub--;
                            }
                        } else if (check == 2 && x + 1 < 10 && dół) {
                            int a1 = x + 2;
                            int b1 = y - 1;
                            for (int i = 0; i < 3; i++) {
                                for (int k = 18; k > 8; k--) {
                                    if (a1 == arrayC[k][0] && b1 + i == arrayC[k][1]) {
                                        dół = false;
                                    }
                                }
                            }
                            if (dół) {
                                arrayC[7][0] = x;
                                arrayC[7][1] = y;
                                arrayC[8][0] = x + 1;
                                arrayC[8][1] = y;
                                doub--;
                            }
                        } else if (check == 3 && y - 1 >= 0 && lewo) {
                            int a1 = x - 1;
                            int b1 = y - 2;
                            for (int i = 0; i < 3; i++) {
                                for (int k = 18; k > 8; k--) {
                                    if (a1 + i == arrayC[k][0] && b1 == arrayC[k][1]) {
                                        lewo = false;
                                    }
                                }
                            }
                            if (lewo) {
                                arrayC[7][0] = x;
                                arrayC[7][1] = y;
                                arrayC[8][0] = x;
                                arrayC[8][1] = y - 1;
                                doub--;
                            }
                        }
                        else if(!góra && !prawo && !dół && !lewo){
                            break;
                        }
                    } while (doub > 2);
                }
            }

            else if (doub == 2) {
                status = true;
                int x = rand.nextInt(10);
                int y = rand.nextInt(10);
                final int a = x - 1;
                final int b = y - 1;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (status) {
                            for (int k = 18; k > 6; k--) {
                                if (a + i == arrayC[k][0] && b + j == arrayC[k][1]) {     //SPRAWDZANIE OBRZEZA I SAMEGO POLA CZY JEST DOPUSZCALNE
                                    status = false;
                                }
                            }
                        }
                    }
                }
                if(status) {
                    góra = true;
                    dół = true;
                    lewo = true;
                    prawo = true;
                    do {
                        int check = rand.nextInt(4);
                        if (check == 0 && x - 1 >= 0 && góra) {
                            int a1 = x - 2;
                            int b1 = y - 1;
                            for (int i = 0; i < 3; i++) {
                                for (int k = 18; k > 6; k--) {
                                    if (a1 == arrayC[k][0] && b1 + i == arrayC[k][1]) {
                                        góra = false;
                                    }
                                }
                            }
                            if (góra) {
                                arrayC[6][0] = x;
                                arrayC[6][1] = y;
                                arrayC[5][0] = x - 1;
                                arrayC[5][1] = y;
                                doub--;
                            }
                        } else if (check == 1 && y + 1 < 10 && prawo) {
                            int a1 = x - 1;
                            int b1 = y + 2;
                            for (int i = 0; i < 3; i++) {
                                for (int k = 18; k > 6; k--) {
                                    if (a1 + i == arrayC[k][0] && b1 == arrayC[k][1]) {
                                        prawo = false;
                                    }
                                }
                            }
                            if (prawo) {
                                arrayC[6][0] = x;
                                arrayC[6][1] = y;
                                arrayC[5][0] = x;
                                arrayC[5][1] = y + 1;
                                doub--;
                            }
                        } else if (check == 2 && x + 1 < 10 && dół) {
                            int a1 = x + 2;
                            int b1 = y - 1;
                            for (int i = 0; i < 3; i++) {
                                for (int k = 18; k > 6; k--) {
                                    if (a1 == arrayC[k][0] && b1 + i == arrayC[k][1]) {
                                        dół = false;
                                    }
                                }
                            }
                            if (dół) {
                                arrayC[6][0] = x;
                                arrayC[6][1] = y;
                                arrayC[5][0] = x + 1;
                                arrayC[5][1] = y;
                                doub--;
                            }
                        } else if (check == 3 && y - 1 >= 0 && lewo) {
                            int a1 = x - 1;
                            int b1 = y - 2;
                            for (int i = 0; i < 3; i++) {
                                for (int k = 18; k > 6; k--) {
                                    if (a1 + i == arrayC[k][0] && b1 == arrayC[k][1]) {
                                        lewo = false;
                                    }
                                }
                            }
                            if (lewo) {
                                arrayC[6][0] = x;
                                arrayC[6][1] = y;
                                arrayC[5][0] = x;
                                arrayC[5][1] = y - 1;
                                doub--;
                            }
                        }
                        else if(!góra && !prawo && !dół && !lewo){
                            break;
                        }
                    } while (doub > 1);
                }
            }

            else if (doub == 1) {
                status = true;
                int x = rand.nextInt(10);
                int y = rand.nextInt(10);
                final int a = x - 1;
                final int b = y - 1;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (status) {
                            for (int k = 18; k > 4; k--) {
                                if (a + i == arrayC[k][0] && b + j == arrayC[k][1]) {     //SPRAWDZANIE OBRZEZA I SAMEGO POLA CZY JEST DOPUSZCALNE
                                    status = false;
                                }
                            }
                        }
                    }
                }

                if(status){
                    góra = true;
                    dół = true;
                    lewo = true;
                    prawo = true;
                    do {
                        int check = rand.nextInt(4);
                        if (check == 0 && x - 1 >= 0 && góra) {
                            int a1 = x - 2;
                            int b1 = y - 1;
                            for (int i = 0; i < 3; i++) {
                                for (int k = 18; k > 4; k--) {
                                    if (a1 == arrayC[k][0] && b1 + i == arrayC[k][1]) {
                                        góra = false;
                                    }
                                }
                            }
                            if (góra) {
                                arrayC[4][0] = x;
                                arrayC[4][1] = y;
                                arrayC[3][0] = x - 1;
                                arrayC[3][1] = y;
                                doub--;
                            }
                        } else if (check == 1 && y + 1 < 10 && prawo) {
                            int a1 = x - 1;
                            int b1 = y + 2;
                            for (int i = 0; i < 3; i++) {
                                for (int k = 18; k > 4; k--) {
                                    if (a1 + i == arrayC[k][0] && b1 == arrayC[k][1]) {
                                        prawo = false;
                                    }
                                }
                            }
                            if (prawo) {
                                arrayC[4][0] = x;
                                arrayC[4][1] = y;
                                arrayC[3][0] = x;
                                arrayC[3][1] = y + 1;
                                doub--;
                            }
                        } else if (check == 2 && x + 1 < 10 && dół) {
                            int a1 = x + 2;
                            int b1 = y - 1;
                            for (int i = 0; i < 3; i++) {
                                for (int k = 18; k > 4; k--) {
                                    if (a1 == arrayC[k][0] && b1 + i == arrayC[k][1]) {
                                        dół = false;
                                    }
                                }
                            }
                            if (dół) {
                                arrayC[4][0] = x;
                                arrayC[4][1] = y;
                                arrayC[3][0] = x + 1;
                                arrayC[3][1] = y;
                                doub--;
                            }
                        } else if (check == 3 && y - 1 >= 0 && lewo) {
                            int a1 = x - 1;
                            int b1 = y - 2;
                            for (int i = 0; i < 3; i++) {
                                for (int k = 18; k > 4; k--) {
                                    if (a1 + i == arrayC[k][0] && b1 == arrayC[k][1]) {
                                        lewo = false;
                                    }
                                }
                            }
                            if (lewo) {
                                arrayC[4][0] = x;
                                arrayC[4][1] = y;
                                arrayC[3][0] = x;
                                arrayC[3][1] = y - 1;
                                doub--;
                            }
                        }
                        else if(!góra && !prawo && !dół && !lewo){
                            break;
                        }
                    } while (doub > 0);
                }
            }
        } while (doub > 0);
    }


    static void singleC() {
        do {
            if (sing == 1) {
                status = true;
                int x = rand.nextInt(10);
                int y = rand.nextInt(10);
                final int a = x - 1;
                final int b = y - 1;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        for (int k = 18; k > 0; k--) {
                            if (a + i == arrayC[k][0] && b + j == arrayC[k][1]) {
                                status = false;
                            }
                        }
                    }
                }

                if(status){
                    arrayC[0][0] = x;
                    arrayC[0][1] = y;
                    sing--;
                }
            } else if (sing == 2) {
                status = true;
                int x = rand.nextInt(10);
                int y = rand.nextInt(10);
                final int a = x - 1;
                final int b = y - 1;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        for (int k = 18; k > 1; k--) {
                            if (a + i == arrayC[k][0] && b + j == arrayC[k][1]) {
                                status = false;
                            }
                        }
                    }
                }
                if(status){
                    arrayC[1][0] = x;
                    arrayC[1][1] = y;
                    sing--;
                }
            } else if (sing == 3) {
                status = true;
                int x = rand.nextInt(10);
                int y = rand.nextInt(10);
                final int a = x - 1;
                final int b = y - 1;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        for (int k = 18; k > 2; k--) {
                            if (a + i == arrayC[k][0] && b + j == arrayC[k][1]) {
                                status = false;
                            }
                        }
                    }
                }
                if(status){
                    arrayC[2][0] = x;
                    arrayC[2][1] = y;
                    sing--;
                }
            }

        } while (sing > 0);
    }

    static int[] row() {
        for (int i = 0; i < 19; i++) {
            rowsC[i] = arrayC[i][0];
        }
        return rowsC;
    }

    static int[] col() {
        for (int i = 0; i < 19; i++) {
            colsC[i] = arrayC[i][1];
        }
        return colsC;
    }

}
