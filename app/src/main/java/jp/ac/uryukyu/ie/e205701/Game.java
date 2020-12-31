package jp.ac.uryukyu.ie.e205701;

import javax.swing.JFrame;

public class Game extends JFrame {

    public Game() {
        Board board = new Board();
        GameWindow gw = new GameWindow();
        BoardPanel boardPanel = new BoardPanel(board);
        gw.add(boardPanel);
        gw.setVisible(true);
        play(boardPanel);
    }

    public void play(BoardPanel boardPanel) {
        boolean isGameOver = false;
        int dropFrame = 60;
        int frameCount = 0;
        Mino[] nextMinos = Mino.initMinos();
        int currentMinoNum = 0;
        boardPanel.board.dropMino = nextMinos[currentMinoNum];
        boardPanel.repaint();
        while (!isGameOver) {
            sleep();
            frameCount++;
            if (frameCount == dropFrame) {
                frameCount = 0;
                if (boardPanel.board.canDrop()) {
                    boardPanel.board.drop();
                    boardPanel.repaint();
                } else {// これ以上下がれない場合
                    if (isGameOver = boardPanel.board.isGameOver(boardPanel.board.dropMino))
                        break;
                    boardPanel.board.setMino();
                    boardPanel.repaint();
                    currentMinoNum++;
                    if (currentMinoNum == 7) {
                        nextMinos = Mino.replenishMino(nextMinos);
                        currentMinoNum = 0;
                    }
                    boardPanel.board.dropMino = nextMinos[currentMinoNum];
                    isGameOver = !boardPanel.board.canSet(boardPanel.board.dropMino.x, -1);
                }
            }
        }
        System.out.print("GAME OVER");
    }

    void sleep() {
        final int SLEEP_TIME = 10;
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
