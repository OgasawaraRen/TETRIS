package jp.ac.uryukyu.ie.e205701;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
    public GameWindow() {
        super("TETRIS");
        setSize(500, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
