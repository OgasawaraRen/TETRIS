package jp.ac.uryukyu.ie.e205701;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

import javax.swing.*;

public class ResultPanel extends JPanel {
    private int score = 0;
    private final int panelHeight = 140;
    private final int panelWidth = 520;

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, panelWidth, panelHeight);
        g.setColor(Color.WHITE);
        g.drawLine(0, 1, panelWidth, 1);
        g.drawLine(0, panelHeight - 1, panelWidth, panelHeight - 1);
        Font font = new Font("ＭＳ Ｐゴシック", Font.PLAIN, 40);
        g.setFont(font);
        g.drawString("GAME OVER", 80, 60);
        font = new Font("ＭＳ Ｐゴシック", Font.PLAIN, 20);
        g.setFont(font);
        String str = "SCORE:" + String.valueOf(score);
        g.drawString(str, 80, 90);
        g.drawString("RETRY:Enter", 80, 115);
    }

    ResultPanel(int score) {
        this.score = score;
        repaint();
    }

    public int getPanelHeight() {
        return panelHeight;
    }

    public int getPanelWidth() {
        return panelWidth;
    }

}
