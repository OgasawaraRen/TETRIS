package jp.ac.uryukyu.ie.e205701;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener {
    GameWindow gw;
    BoardPanel boardPanel;
    boolean isGameOver = false;
    int dropFrame = 60;
    Mino[] nextMinos = Mino.initMinos();
    int currentMinoNum = 0;

    public Game() {
        Board board = new Board();
        gw = new GameWindow();
        boardPanel = new BoardPanel(board);
        gw.add(boardPanel);
        gw.setVisible(true);
        gw.addKeyListener(this);
        gw.setFocusable(true);
        play(boardPanel);
    }

    public void play(BoardPanel boardPanel) {
        int frameCount = 0;
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                pressed_Up();
                break;

            case KeyEvent.VK_LEFT:
                pressed_Left();
                break;

            case KeyEvent.VK_RIGHT:
                pressed_Right();
                break;

            case KeyEvent.VK_DOWN:
                pressed_Down();
                break;

            case KeyEvent.VK_A:

                break;

            default:
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            dropFrame = 60;
        }
    }

    // left rotate
    void pressed_A() {
        int rotatedNum = boardPanel.board.dropMino.rotateNum - 1;
        if (rotatedNum < 0)
            rotatedNum = 3;
        if (boardPanel.board.canRotate(rotatedNum)) {
            boardPanel.board.dropMino.rotateNum = rotatedNum;
            boardPanel.repaint();
        }
    }

    // right rotate
    void pressed_D() {
        int rotatedNum = (boardPanel.board.dropMino.rotateNum + 1) % 4;
        if (boardPanel.board.canRotate(rotatedNum)) {
            boardPanel.board.dropMino.rotateNum = rotatedNum;
            boardPanel.repaint();
        }
    }

    // HOLD
    void pressed_W() {

    }

    void pressed_Right() {
        if (boardPanel.board.canSet(boardPanel.board.dropMino.x + 1, boardPanel.board.dropMino.y)) {
            boardPanel.board.dropMino.x += 1;
            boardPanel.repaint();
        }
    }

    void pressed_Left() {
        if (boardPanel.board.canSet(boardPanel.board.dropMino.x - 1, boardPanel.board.dropMino.y)) {
            boardPanel.board.dropMino.x -= 1;
            boardPanel.repaint();
        }
    }

    void pressed_Up() {
        boardPanel.board.hardDrop();
        boardPanel.repaint();
    }

    void pressed_Down() {
        dropFrame = 5;
    }
}
