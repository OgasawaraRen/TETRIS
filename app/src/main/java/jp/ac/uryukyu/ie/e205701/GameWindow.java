package jp.ac.uryukyu.ie.e205701;

import java.awt.Color;
import javax.swing.JFrame;

public class GameWindow extends JFrame {

    public GameWindow() {
        super("TETRIS");
        setSize(500, 720);
        // setBounds(0, 0, 500, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }
}
