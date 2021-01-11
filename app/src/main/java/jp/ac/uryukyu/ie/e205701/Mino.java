package jp.ac.uryukyu.ie.e205701;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class Mino {
    int[][][] shape;
    int rotateNum;
    int x, y;// 左上,shape基準
    final int COLOR_NUM;

    Mino(int[][] defShape, int colorNum) {
        this.shape = makeShapes(defShape);
        this.x = Board.BOARD_W / 2 - (this.shape[0][0].length + 1) / 2;

        this.y = -2;
        this.COLOR_NUM = colorNum;
        rotateNum = 0;
    }

    int[][][] makeShapes(int[][] defShape) {
        int[][][] shapeBPs = new int[4][defShape.length][defShape[0].length];
        shapeBPs[0] = defShape;
        for (int i = 1; i < 4; i++) {
            shapeBPs[i] = Matrix.rotate(shapeBPs[i - 1]);
        }
        return shapeBPs;
    }

    static Mino[] makeMinos() {
        Mino[] minos = new Mino[7];

        int[][][] minosShape = { { { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } }, // Iミノ
                { { 1, 1 }, { 1, 1 } }, // Oミノ
                { { 0, 1, 0 }, { 1, 1, 1 }, { 0, 0, 0 } }, // Tミノ
                { { 1, 1, 0 }, { 0, 1, 1 }, { 0, 0, 0 } }, // Zミノ
                { { 0, 1, 1 }, { 1, 1, 0 }, { 0, 0, 0 } }, // Sミノ
                { { 1, 0, 0 }, { 1, 1, 1 }, { 0, 0, 0 } }, // Jミノ
                { { 0, 0, 1 }, { 1, 1, 1 }, { 0, 0, 0 } } };// Lミノ

        for (int i = 0; i < minos.length; i++) {
            minos[i] = new Mino(minosShape[i], i + 1);
        }

        return minos;
    }

    static Mino[] initMinos() {
        ArrayList<Mino> m1 = new ArrayList<Mino>(Arrays.asList(makeMinos()));
        ArrayList<Mino> m2 = new ArrayList<Mino>(Arrays.asList(makeMinos()));

        Collections.shuffle(m1);
        Collections.shuffle(m2);
        m1.addAll(m2);
        return m1.toArray(new Mino[m1.size()]);
    }

    static Mino[] replenishMino(Mino[] minos) {
        for (int i = 0; i < 7; i++) {
            minos[i] = minos[i + 7];
        }
        ArrayList<Mino> addMinosList = new ArrayList<Mino>(Arrays.asList(makeMinos()));
        Collections.shuffle(addMinosList);
        Mino[] addMinos = addMinosList.toArray(new Mino[7]);
        for (int i = 0; i < 7; i++) {
            minos[i + 7] = addMinos[i];
        }

        return minos;
    }
}
