package edu.byu.yc.tests;

public class Liveness {

    public void lva() {
        int a = 3;
        int b = 5;
        int d = 4;
        int x = 100;
        if (a > b) {
            x = d;
        }
    }

}
