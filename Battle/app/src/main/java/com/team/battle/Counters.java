package com.team.battle;



public class Counters {

    static int count = 0, sing = 0, doub = 0, trip = 0, quad = 0;

    static int singer() {
        sing++;
        return sing;
    }

    static int doubler() {
        doub++;
        return doub;
    }

    static int tripler(){
        trip++;
        return trip;
    }

    static int quader(){
        quad++;
        return quad;
    }

    static int counter(){
        count++;
        return count;
    }

    static int discounter(){
        count--;
        return count;
    }
    
}
