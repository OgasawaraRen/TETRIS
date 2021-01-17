package jp.ac.uryukyu.ie.e205701;

/**
 * ボードクラス。盤面、落下中のミノを管理する。
 */
public class Board {
    final static int BOARD_W = 10;// 盤面の横幅
    final static int BOARD_H = 20;// 盤面の縦幅
    int[][] boardProp = makeBoardProp();// 盤面の状態
    Mino dropMino;// 落下中のミノ

    /**
     * BoardクラスのboardPropの初期化に使用する。初期状態(全ての値が0)の盤面を返す。
     * 
     * @return 全て0のint型２次元配列を返す
     */
    public static int[][] makeBoardProp() {
        int[][] brd = new int[BOARD_H][BOARD_W];
        for (int i = 0; i < BOARD_H; i++) {
            for (int j = 0; i < BOARD_W; i++) {
                brd[i][j] = 0;
            }
        }
        return brd.clone();
    }

    /**
     * 盤面にミノが設置可能かどうかを判定し、設置可能ならtrueを、設置不可能ならfalseを返す。
     * 
     * @param x 設置するミノのx座標
     * @param y 設置するミノのy座標
     * @return 設置可能な場合true
     */
    public boolean canSet(int x, int y) {
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

    /**
     * 落下中のミノを設置可能な最も下の位置に設置する
     */
    public void hardDrop() {
        int x = dropMino.x;
        int y = dropMino.y;

        while (canSet(x, y + 1)) {
            y++;
        }

        dropMino.y = y;
        setMino();
    }

    /**
     * 落下中のミノを盤面に設置する。ミノが画面上外に出ている場合は、画面内にあるブロックのみ設置される。
     */
    public void setMino() {
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

    /**
     * ミノが落下可能か(1マス下に移動可能か)を判定する。落下可能な場合はtrueを、落下不可能ならfalseを返す。
     * 
     * @return 落下可能な場合ture
     */
    public boolean canDrop() {
        int y = dropMino.y + 1;
        return canSet(dropMino.x, y);
    }

    /**
     * 落下中のミノのy座標を1マス下にする。
     */
    public void drop() {
        dropMino.y += 1;
    }

    /**
     * 落下中のミノが回転可能かを判定する。回転可能な場合はtrueを、回転不可能ならfalseを返す。
     * 
     * @param rotateNum 回転後の番号
     * @return 回転可能な場合true
     */
    public boolean canRotate(int rotateNum) {
        int beforeRotateNum = dropMino.rotateNum;
        dropMino.rotateNum = rotateNum;
        if (canSet(dropMino.x, dropMino.y)) {
            return true;
        } else {
            dropMino.rotateNum = beforeRotateNum;
            return false;
        }
    }

    /**
     * 引数で受け取ったミノを盤面に出現させる。
     * 
     * @param mino 出現させるミノ
     */
    public void appearMino(Mino mino) {
        int x = BOARD_W / 2 - mino.shape[0][0].length / 2;

        for (int i = 1; i < mino.shape[0].length; i++) {
            for (int j = 0; j < mino.shape[0][0].length; j++) {
                if (mino.shape[0][i][j] != 0)
                    continue;
                boardProp[i - 1][j + x] = mino.COLOR_NUM;
            }
        }
    }

    /**
     * ゲームオーバーかどうかを判定する。引数で受け取ったミノを盤面に設置した際に、画面上外に出ている部分が存在しているかどうかを調べて判定する。
     * 
     * @param mino 設置するミノ
     * @return 引数のミノを設置した時、画面上外に出ている部分が存在している場合はtrue
     */
    public boolean isGameOver(Mino mino) {
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

    /**
     * 引数で受け取ったy座標の列を調べ、消去可能であるかどうかを判定する。
     * 
     * @param y 調べる列のy座標
     * @return 消去可能な場合true
     */
    public boolean checkDeleteLine(int y) {
        for (int blockNum : boardProp[y]) {
            if (blockNum == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 引数で指定したy座標の列が消去可能だった場合、ブロックを全て消去状態(-1)に変更する。消去可能な場合はtrueを返し、不可能な場合はfalseを返す。
     * 
     * @param y 消去する列のy座標
     * @return 消去可能な場合true
     */
    public boolean deleteLine(int y) {
        if (checkDeleteLine(y)) {
            for (int i = 0; i < BOARD_W; i++) {
                boardProp[y][i] = -1;
            }
            return true;
        }
        return false;
    }

    /**
     * 引数で指定したy座標から最下段までの列を調べ、消去状態の列だった場合その列を取り除く。
     * 
     * @param y 調べる最上段のy座標
     */
    public void fallBlocks(int y) {
        for (int i = y; i > 0; i--) {
            for (int j = 0; j < BOARD_W; j++) {
                boardProp[i][j] = boardProp[i - 1][j];
            }
        }

        for (int i = 0; i < BOARD_W; i++) {// 最上段は必ず空白
            boardProp[0][i] = 0;
        }
    }

    /**
     * 引数で指定した範囲を調べ、消去可能な列を消去する。消去した列数を返す。
     * 
     * @param top    調べる最上段のy座標
     * @param bottom 調べる最下段のy座標
     * @return 消去した列数
     */
    public int deleteLines(int top, int bottom) {
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

    /**
     * 盤面を全て調べ、消去可能な列を消去する。消去した列数を返す。
     * 
     * @return 消去した列数
     */
    public int deleteLines() {
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
