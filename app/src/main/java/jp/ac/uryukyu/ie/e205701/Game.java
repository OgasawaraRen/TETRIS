package jp.ac.uryukyu.ie.e205701;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener {
    GameWindow gw;
    BoardPanel boardPanel;
    NextMinoPanel nextMinoPanel;
    HoldPanel holdPanel;
    ScorePanel scorePanel;
    ResultPanel resultPanel;
    boolean isGameOver = false;
    int dropFrame = 60;
    Mino[] nextMinos = Mino.initMinos();
    int currentMinoNum = 0;
    int holdCount = 0;
    boolean isPlay = true;

    public Game() {
        while (true) {
            resetFields();
            Board board = new Board();
            initBoardPanel(board);
            initNextMinoPanel();
            initHoldPanel();
            initScorePanel();
            initGW(boardPanel, nextMinoPanel, scorePanel);
            play(boardPanel);
            result();
            stop();
            resultPanel.isVisible();
        }
    }

    public void play(BoardPanel boardPanel) {
        int frameCount = 0;
        boardPanel.board.dropMino = nextMinos[currentMinoNum];
        boardPanel.repaint();
        while (!isGameOver) {
            sleep();
            frameCount++;
            if (frameCount >= dropFrame) {
                frameCount = 0;
                if (boardPanel.board.canDrop()) {
                    boardPanel.board.drop();
                    boardPanel.repaint();
                } else {// これ以上下がれない場合
                    if (isGameOver = boardPanel.board.isGameOver(boardPanel.board.dropMino))
                        break;
                    boardPanel.board.setMino();
                    int deleteCount = boardPanel.board.deleteLines();
                    scorePanel.addScore(calcScore(deleteCount));
                    boardPanel.repaint();
                    currentMinoNum++;
                    if (currentMinoNum == 7) {
                        nextMinos = Mino.replenishMino(nextMinos);
                        currentMinoNum = 0;
                    }
                    nextMinoPanel.reloadMino(nextMinos[currentMinoNum + 3]);
                    boardPanel.board.dropMino = nextMinos[currentMinoNum];
                    isGameOver = !boardPanel.board.canSet(boardPanel.board.dropMino.x, -1);
                    holdCount = 0;
                }
            }
        }
        isPlay = false;
    }

    void sleep() {
        final int SLEEP_TIME = 10;
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void resetFields() {
        isGameOver = false;
        dropFrame = 60;
        nextMinos = Mino.initMinos();
        currentMinoNum = 0;
        holdCount = 0;
        isPlay = true;
    }

    void initBoardPanel(Board board) {
        boardPanel = new BoardPanel(board);
        int width = boardPanel.getPanelWidth();
        int height = boardPanel.getPanelHeight();
        boardPanel.setBounds(0, 0, width, height);
    }

    void initNextMinoPanel() {
        Mino[] printNextMinos = new Mino[3];
        for (int i = 0; i < 3; i++) {
            printNextMinos[i] = nextMinos[i + 1];
        }
        nextMinoPanel = new NextMinoPanel(printNextMinos);
        int width = nextMinoPanel.getPanelWidth();
        int height = nextMinoPanel.getPanelHeight();
        nextMinoPanel.setBounds(boardPanel.getPanelWidth() + 5, 20, width, height);
    }

    void initHoldPanel() {
        holdPanel = new HoldPanel();
        int width = holdPanel.getPanelWidth();
        int height = holdPanel.getPanelHeight();
        holdPanel.setBounds(boardPanel.getPanelWidth() + 5, nextMinoPanel.getPanelHeight() + 15, width, height);
    }

    void initScorePanel() {
        scorePanel = new ScorePanel();
        int width = scorePanel.getPanelWidth();
        int height = scorePanel.getPanelHeight();
        scorePanel.setBounds(boardPanel.getPanelWidth() + 5, boardPanel.getPanelHeight() - height - 10, width, height);
    }

    void initGW(BoardPanel boardPanel, NextMinoPanel nextMinoPanel, ScorePanel scorePanel) {
        gw = new GameWindow();
        gw.setLayout(null);
        gw.add(boardPanel);
        gw.add(nextMinoPanel);
        gw.add(holdPanel);
        gw.add(scorePanel);
        gw.addKeyListener(this);
        boardPanel.repaint();
        nextMinoPanel.repaint();
        holdPanel.repaint();
        scorePanel.repaint();
    }

    int calcScore(int deleteCount) {
        int addScore = 0;
        for (int i = 0; i < deleteCount; i++) {
            addScore += 100 * i;
        }
        return addScore;
    }

    void result() {
        resultPanel = new ResultPanel(scorePanel.getScore());
        int width = resultPanel.getPanelWidth();
        int height = resultPanel.getPanelHeight();
        int y = (boardPanel.getPanelHeight() - resultPanel.getPanelHeight()) / 2;
        resultPanel.setBounds(0, y, width, height);
        gw.add(resultPanel);
        resultPanel.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isGameOver) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                isPlay = true;
            return;
        }
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
                pressed_A();
                break;

            case KeyEvent.VK_D:
                pressed_D();
                break;

            case KeyEvent.VK_W:
                pressed_W();
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
        if (holdCount > 0) {
            return;
        }
        holdCount++;
        boardPanel.board.dropMino = holdPanel.tradeMino(boardPanel.board.dropMino);
        if (boardPanel.board.dropMino == null) {
            currentMinoNum++;
            if (currentMinoNum == 7) {
                nextMinos = Mino.replenishMino(nextMinos);
                currentMinoNum = 0;
            }
            nextMinoPanel.reloadMino(nextMinos[currentMinoNum + 3]);
            boardPanel.board.dropMino = nextMinos[currentMinoNum];
        }
        boardPanel.repaint();
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
        dropFrame = 60;
        if (boardPanel.board.canDrop()) {
            dropFrame = 5;
        }
    }

    void stop() {
        while (!isPlay) {
            sleep();
        }
    }
}
