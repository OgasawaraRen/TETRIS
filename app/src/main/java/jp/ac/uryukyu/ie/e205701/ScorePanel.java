package jp.ac.uryukyu.ie.e205701;

import java.awt.Graphics;
import java.awt.Font;

import javax.swing.*;

public class ScorePanel extends JPanel {
    private final int panelHeight = 20;
    private final int panelWidth = 185;
    private int score = 0;

    @Override
    public void paint(Graphics g) {
        Font font = new Font("ＭＳ Ｐゴシック", Font.PLAIN, 20);
        g.setColor(ColorSet.BLACK);
        g.setFont(font);
        g.drawString(String.valueOf(score), 20, 20);
    }

    public void addScore(int value) {
        score += value;
        repaint();
    }

    public int getPanelHeight() {
        return panelHeight;
    }

    public int getPanelWidth() {
        return panelWidth;
    }

    public int getScore() {
        return score;
    }
}
