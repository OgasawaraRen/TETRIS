package jp.ac.uryukyu.ie.e205701;

import java.awt.Graphics;
import java.awt.Font;

import javax.swing.*;

/**
 * プレイ中のスコアを管理、表示するクラス。
 */
public class ScorePanel extends JPanel {
    private final int panelHeight = 20;
    private final int panelWidth = 185;
    private int score = 0;

    /**
     * スコアを描画する。
     */
    @Override
    public void paint(Graphics g) {
        Font font = new Font("ＭＳ Ｐゴシック", Font.PLAIN, 20);
        g.setColor(ColorSet.BLACK);
        g.setFont(font);
        g.drawString(String.valueOf(score), 20, 20);
    }

    /**
     * スコアを引数の値分だけ加算し、画面を更新する。
     * 
     * @param value スコアに加算するポイント
     */
    public void addScore(int value) {
        score += value;
        repaint();
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
     * 現在のスコアを返す。
     * 
     * @return 現在のスコア
     */
    public int getScore() {
        return score;
    }
}
