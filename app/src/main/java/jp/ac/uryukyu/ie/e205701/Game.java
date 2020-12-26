package jp.ac.uryukyu.ie.e205701;

import javax.swing.JFrame;

public class Game extends JFrame {
    public Game() {
        Board board = new Board();
        GameWindow gw = new GameWindow();
        gw.add(new BoardPanel(board));
        gw.setVisible(true);
    }
}
