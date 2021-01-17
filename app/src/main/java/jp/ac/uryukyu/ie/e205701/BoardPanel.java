package jp.ac.uryukyu.ie.e205701;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;

/**
 * ボードの表示クラス。
 */
public class BoardPanel extends JPanel {
    static final int BLOCK_SIZE = 35; // ブロック1マスの大きさ
    Board board;// ゲームの盤面
    private int panelHeight = BLOCK_SIZE * 20; // パネルの縦幅
    private int panelWidth = BLOCK_SIZE * 10; // パネルの横幅
    private int panelX = 0; // パネルを配置するx座標
    private int panelY = 0; // パネルを配置するy座標

    private boolean gridVisible = true;// gridを表示するか

    /**
     * コンストラクタ。boardフィールドの初期化を行う。
     */
    public BoardPanel() {
        this.board = new Board();
    }

    /**
     * 盤面と落下中のミノを描画する。
     */
    @Override
    public void paint(Graphics g) {
        // 落下中のミノ以外を描画
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                Color blockColor = ColorSet.COLORS[board.boardProp[i][j]];
                g.setColor(blockColor);
                g.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.setColor(Color.BLACK);
                if (gridVisible && blockColor == ColorSet.BLACK) {
                    g.setColor(ColorSet.GRID_GRAY);
                }
                g.drawRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);// ブロックの枠を描画
            }
        }

        // 落下中のミノを描画
        if (board.dropMino != null) {
            for (int i = 0; i < board.dropMino.shape[0].length; i++) {
                for (int j = 0; j < board.dropMino.shape[0][0].length; j++) {
                    if (board.dropMino.shape[board.dropMino.rotateNum][i][j] == 0)
                        continue;
                    Color blockColor = ColorSet.COLORS[board.dropMino.COLOR_NUM];
                    g.setColor(blockColor);
                    g.fillRect((j + board.dropMino.x) * BLOCK_SIZE, (i + board.dropMino.y) * BLOCK_SIZE, BLOCK_SIZE,
                            BLOCK_SIZE);
                    g.setColor(Color.BLACK);
                    g.drawRect((j + board.dropMino.x) * BLOCK_SIZE, (i + board.dropMino.y) * BLOCK_SIZE, BLOCK_SIZE,
                            BLOCK_SIZE);// ブロックの枠を描画
                }
            }
        }
    }

    /**
     * パネルの縦の長さを返す。
     * 
     * @return パネルの縦幅
     */
    public int getPanelHeight() {
        return panelHeight;
    }

    /**
     * パネルの横の長さを返す。
     * 
     * @return パネルの横幅
     */
    public int getPanelWidth() {
        return panelWidth;
    }

    /**
     * パネルを配置するx座標を返す
     * 
     * @return パネルのx座標
     */
    public int getPanelX() {
        return panelX;
    }

    /**
     * パネルを配置するy座標を返す
     * 
     * @return パネルのy座標
     */
    public int getPanelY() {
        return panelY;
    }

    /**
     * グリッドの表示を切り替える。引数にtrueを渡すことでグリッド表示、falseを渡すことでグリッド非表示。
     * 
     * @param gridVisible グリッドを表示する場合true
     */
    public void setGridVisible(boolean gridVisible) {
        this.gridVisible = gridVisible;
    }
}