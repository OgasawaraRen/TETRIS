package jp.ac.uryukyu.ie.e205701;

import java.awt.Color;
import javax.swing.JFrame;

/**
 * ゲームウィンドウクラス。ウィンドウに関する設定を行う。
 */
public class GameWindow extends JFrame {

    /**
     * コンストラクタ。ウィンドウに関する設定を行う。
     */
    public GameWindow() {
        super("TETRIS");
        setSize(500, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
