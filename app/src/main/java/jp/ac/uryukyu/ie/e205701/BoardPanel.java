package jp.ac.uryukyu.ie.e205701;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;

public class BoardPanel extends JPanel {
    static final int BLOCK_SIZE = 35;
    Board board;
    private int panelHeight = BLOCK_SIZE * 20;
    private int panelWidth = BLOCK_SIZE * 10;
    private int panelX = 0;
    private int panelY = 0;

    public BoardPanel(Board board) {
        this.board = board;
    }

    public void paint(Graphics g) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                Color blockColor = ColorSet.COLORS[board.boardProp[i][j]];
                g.setColor(blockColor);
                g.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);// ブロックの枠を描画
            }
        }

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

    public int getPanelHeight() {
        return panelHeight;
    }

    public int getPanelWidth() {
        return panelWidth;
    }

    public int getPanelX() {
        return panelX;
    }

    public int getPanelY() {
        return panelY;
    }
}