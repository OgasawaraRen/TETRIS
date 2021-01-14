package jp.ac.uryukyu.ie.e205701;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

import javax.swing.*;

public class HoldPanel extends JPanel {
    Mino holdMino = null;
    private final int BLOCK_SIZE = 35;
    private final int HOLD_BLOCK_SIZE = 25;
    private final int FRAME_RECT_SIZE = 140;

    private final int panelHeight = BLOCK_SIZE * 6;
    private final int panelWidth = BLOCK_SIZE * 5;

    @Override
    public void paint(Graphics g) {
        Font font = new Font("ＭＳ Ｐゴシック", Font.PLAIN, 20);
        g.setColor(ColorSet.BLACK);
        g.setFont(font);
        g.drawString("HOLD", 20, 20);
        g.drawRect(0, 30, FRAME_RECT_SIZE, FRAME_RECT_SIZE);
        if (holdMino == null)
            return;
        Color blockColor = ColorSet.COLORS[holdMino.COLOR_NUM];
        int gap = ((FRAME_RECT_SIZE - HOLD_BLOCK_SIZE * holdMino.shape[0].length) / 2);
        int vertGap = HOLD_BLOCK_SIZE / 2;
        if (holdMino.shape[0].length == 2) {
            vertGap = 0;
        }

        for (int i = 0; i < holdMino.shape[0].length; i++) {
            for (int j = 0; j < holdMino.shape[0][0].length; j++) {
                if (holdMino.shape[0][i][j] == 0)
                    continue;

                g.setColor(blockColor);
                g.fillRect(j * HOLD_BLOCK_SIZE + gap, (i + 1) * HOLD_BLOCK_SIZE + gap + vertGap, HOLD_BLOCK_SIZE,
                        HOLD_BLOCK_SIZE);
                g.setColor(ColorSet.BLACK);
                g.drawRect(j * HOLD_BLOCK_SIZE + gap, (i + 1) * HOLD_BLOCK_SIZE + gap + vertGap, HOLD_BLOCK_SIZE,
                        HOLD_BLOCK_SIZE);
            }
        }
    }

    Mino tradeMino(Mino setMino) {
        Mino returnMino = holdMino;
        holdMino = setMino;

        if (returnMino != null) {
            returnMino.rotateNum = 0;
            returnMino.x = returnMino.DEFAULT_X;
            returnMino.y = returnMino.DEFAULT_Y;
        }
        repaint();
        return returnMino;
    }

    public int getPanelHeight() {
        return panelHeight;
    }

    public int getPanelWidth() {
        return panelWidth;
    }
}
