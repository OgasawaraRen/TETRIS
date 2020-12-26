package jp.ac.uryukyu.ie.e205701;

import javax.swing.*;

public class Board {
    static int[][] makeBoardProp() {
        int[][] brd = new int[20][10];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; i < 10; i++) {
                brd[i][j] = 0;
            }
        }
        return brd.clone();
    }

    int[][] boardProp = makeBoardProp();

}
