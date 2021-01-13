package jp.ac.uryukyu.ie.e205701;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;

public class NextMinoPanel extends JPanel {
    Mino[] nextMinos;

    final int BLOCK_SIZE = 35;

    private final int panelHeight = BLOCK_SIZE * 13;
    private final int panelWidth = BLOCK_SIZE * 5;

    public NextMinoPanel(Mino[] nextMinos) {
        this.nextMinos = nextMinos;
        this.setVisible(true);
    }

    public void reloadMino(Mino mino) {
        for (int i = 0; i < nextMinos.length - 1; i++) {
            nextMinos[i] = nextMinos[i + 1];
        }
        nextMinos[nextMinos.length - 1] = mino;
        repaint();
    }

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

    public int getPanelHeight() {
        return panelHeight;
    }

    public int getPanelWidth() {
        return panelWidth;
    }
}
