package jp.ac.uryukyu.ie.e205701;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;

/**
 * これから落下するミノを管理、表示するクラス。
 */
public class NextMinoPanel extends JPanel {
    Mino[] nextMinos;

    final int BLOCK_SIZE = 35;

    private final int panelHeight = BLOCK_SIZE * 12;
    private final int panelWidth = BLOCK_SIZE * 5;

    /**
     * コンストラクタ。引数で受け取った配列をnextMinosに設定する。
     * 
     * @param nextMinos nextMinosに設定する配列。要素数は3を想定。
     */
    public NextMinoPanel(Mino[] nextMinos) {
        this.nextMinos = nextMinos;
        this.setVisible(true);
    }

    /**
     * nextMinosの2,3番目の要素をそれぞれ1,2番目に移動させ、3番目には引数で受け取ったミノを代入する。
     * 
     * @param mino nextMinosの3番目に代入するミノ
     */
    public void reloadMino(Mino mino) {
        for (int i = 0; i < nextMinos.length - 1; i++) {
            nextMinos[i] = nextMinos[i + 1];
        }
        nextMinos[nextMinos.length - 1] = mino;
        repaint();
    }

    /**
     * nextMinoを描画する。
     */
    public void paint(Graphics g) {
        int loop = 0;
        for (Mino nextMino : nextMinos) {
            int gap = BLOCK_SIZE / 2;
            if (nextMino.shape[0].length % 4 == 0) {
                gap = 0;
            } else if (nextMino.shape[0].length % 2 == 0) {
                gap = BLOCK_SIZE;
            }
            Color blockColor = ColorSet.COLORS[nextMino.COLOR_NUM];
            for (int i = 0; i < nextMino.shape[0].length; i++) {
                for (int j = 0; j < nextMino.shape[0][0].length; j++) {
                    if (nextMino.shape[0][i][j] == 0)
                        continue;

                    g.setColor(blockColor);
                    g.fillRect(j * BLOCK_SIZE + gap, i * BLOCK_SIZE + loop * BLOCK_SIZE * 4, BLOCK_SIZE, BLOCK_SIZE);
                    g.setColor(ColorSet.BLACK);
                    g.drawRect(j * BLOCK_SIZE + gap, i * BLOCK_SIZE + loop * BLOCK_SIZE * 4, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
            loop++;
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
}
