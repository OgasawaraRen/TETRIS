package jp.ac.uryukyu.ie.e205701;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

import javax.swing.*;

/**
 * ホールドパネルクラス。ホールドしたミノの管理と表示を行う。 Mino holdMino//ホールドしているミノ。何もホールドしていない時はnull。
 */
public class HoldPanel extends JPanel {
    Mino holdMino = null;// ホールド中のミノ
    private final int BLOCK_SIZE = 35;
    private final int HOLD_BLOCK_SIZE = 25;// 表示するホールドミノの１マスの大きさ
    private final int FRAME_RECT_SIZE = 140;// 枠の大きさ

    private final int panelHeight = BLOCK_SIZE * 6;
    private final int panelWidth = BLOCK_SIZE * 5;

    /**
     * ホールドしているミノと枠線、HOLDの文字の描画を行う。
     */
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

    /**
     * 引数で受け取ったミノをholdMinoにセットし、戻り値にセットする前のholdMinoを返す。
     * 
     * @param setMino ホールドするミノ
     * @return メソッドを呼び出す前のholdMino
     */
    public Mino tradeMino(Mino setMino) {
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
