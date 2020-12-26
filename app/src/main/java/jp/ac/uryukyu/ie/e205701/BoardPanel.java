package jp.ac.uryukyu.ie.e205701;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;

public class BoardPanel extends JPanel {
    Board board;
    static final Color BLACK = Color.BLACK;
    static final Color SKYBLUE = Color.getHSBColor(0.54f, 1, 1);
    static final Color YELLOW = Color.getHSBColor(0.16f, 0.94f, 0.94f);
    static final Color PURPLE = Color.getHSBColor(0.8f, 1, 0.71f);
    static final Color RED = Color.getHSBColor(0, 1, 1);
    static final Color GREEN = Color.getHSBColor(0.34f, 0.88f, 0.88f);
    static final Color BULE = Color.getHSBColor(0.66f, 1, 1);
    static final Color ORANGE = Color.getHSBColor(0.09f, 1, 1);
    static final Color[] COLORS = { BLACK, SKYBLUE, YELLOW, PURPLE, RED, GREEN, BULE, ORANGE };
    static final int BLOCK_SIZE = 35;

    public BoardPanel(Board board) {
        this.board = board;
    }

    public void paint(Graphics g) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                Color blockColor = COLORS[board.boardProp[i][j]];
                g.setColor(blockColor);
                g.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);// ブロックの枠を描画
            }
        }
    }
}