package jp.ac.uryukyu.ie.e205701;

public class Board {
    final static int BOARD_W = 10;
    final static int BOARD_H = 20;

    static int[][] makeBoardProp() {
        int[][] brd = new int[BOARD_H][BOARD_W];
        for (int i = 0; i < BOARD_H; i++) {
            for (int j = 0; i < BOARD_W; i++) {
                brd[i][j] = 0;
            }
        }
        return brd.clone();
    }

    int[][] boardProp = makeBoardProp();
    Mino dropMino;

    boolean canSet(int x, int y) {
        int rotateNum = dropMino.rotateNum;
        for (int i = 0; i < dropMino.shape[rotateNum].length; i++) {
            int blockY = y + i;
            for (int j = 0; j < dropMino.shape[rotateNum][0].length; j++) {
                int blockX = x + j;
                if (dropMino.shape[rotateNum][i][j] == 0)// ミノの空白部分は無視
                    continue;

                if (blockX >= BOARD_W || blockX < 0 || blockY >= BOARD_H) {// ボード外に出ていないか
                    return false;
                }

                if (blockY < 0)// 画面上外は無視
                    continue;

                if (boardProp[blockY][blockX] != 0) {// 既にブロックが置かれているか
                    return false;
                }
            }
        }
        return true;
    }

    void hardDrop() {
        int x = dropMino.x;
        int y = dropMino.y;

        while (canSet(x, y + 1)) {
            y++;
        }

        dropMino.y = y;
        setMino();
    }

    void setMino() {
        int rotateNum = dropMino.rotateNum;
        int y = dropMino.y;
        for (int i = 0; i < dropMino.shape[rotateNum].length; i++) {
            for (int j = 0; j < dropMino.shape[rotateNum][0].length; j++) {
                if (i + y < 0)
                    continue;
                if (dropMino.shape[rotateNum][i][j] != 0)
                    boardProp[dropMino.y + i][dropMino.x + j] = dropMino.COLOR_NUM;
            }
        }
    }

    boolean canDrop() {
        int y = dropMino.y + 1;
        return canSet(dropMino.x, y);
    }

    void drop() {
        dropMino.y += 1;
    }

    boolean canRotate(int rotateNum) {
        int beforeRotateNum = dropMino.rotateNum;
        dropMino.rotateNum = rotateNum;
        if (canSet(dropMino.x, dropMino.y)) {
            return true;
        } else {
            dropMino.rotateNum = beforeRotateNum;
            return false;
        }
    }

    void appearMino(Mino mino) {
        int x = BOARD_W / 2 - mino.shape[0][0].length / 2;

        for (int i = 1; i < mino.shape[0].length; i++) {
            for (int j = 0; j < mino.shape[0][0].length; j++) {
                if (mino.shape[0][i][j] != 0)
                    continue;
                boardProp[i - 1][j + x] = mino.COLOR_NUM;
            }
        }
    }

    boolean isGameOver(Mino mino) {
        int y = mino.y;
        for (int i = 0; i < mino.shape[mino.rotateNum].length; i++) {
            for (int j = 0; j < mino.shape[mino.rotateNum][0].length; j++) {
                if (mino.shape[mino.rotateNum][i][j] != 0 && y < 0)
                    return true;
            }
            y++;
        }
        return false;
    }

    boolean checkDeleteLine(int y) {
        for (int blockNum : boardProp[y]) {
            if (blockNum == 0) {
                return false;
            }
        }
        return true;
    }

    boolean deleteLine(int y) {
        if (checkDeleteLine(y)) {
            for (int i = 0; i < BOARD_W; i++) {
                boardProp[y][i] = -1;
            }
            return true;
        }
        return false;
    }

    void fallBlocks(int y) {
        for (int i = y; i > 0; i--) {
            for (int j = 0; j < BOARD_W; j++) {
                boardProp[i][j] = boardProp[i - 1][j];
            }
        }

        for (int i = 0; i < BOARD_W; i++) {// 最上段は必ず空白
            boardProp[0][i] = 0;
        }
    }

    int deleteLines(int top, int bottom) {
        int deleteCount = 0;
        for (int y = top; y <= bottom; y++) {// 揃っている段を全て消去
            if (deleteLine(y)) {
                deleteCount++;
            }
        }

        for (int y = BOARD_H - 1; y >= 0; y--) {
            if (boardProp[y][0] == -1) {// 消されている段なら上のブロック落下
                fallBlocks(y);
                y++;// 同じ位置からチェックしたいので+1する
            }
        }

        return deleteCount;
    }

    int deleteLines() {
        int deleteCount = 0;
        for (int y = 0; y < BOARD_H; y++) {// 揃っている段を全て消去
            if (deleteLine(y)) {
                deleteCount++;
            }
        }

        for (int y = BOARD_H - 1; y >= 0; y--) {
            if (boardProp[y][0] == -1) {// 消されている段なら上のブロック落下
                fallBlocks(y);
                y++;// 同じ位置からチェックしたいので+1する
            }
        }

        return deleteCount;
    }

}
