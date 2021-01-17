package jp.ac.uryukyu.ie.e205701;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * ゲーム管理クラス。このクラスのインスタンスを生成することでゲームが開始する。
 */
public class Game implements KeyListener {
    GameWindow gw;// ウィンドウ
    BoardPanel boardPanel;
    NextMinoPanel nextMinoPanel;
    HoldPanel holdPanel;
    ScorePanel scorePanel;
    ResultPanel resultPanel;
    boolean isGameOver = false;
    int dropFrame = 60;// 60フレーム毎にミノが落下
    Mino[] nextMinos = Mino.initMinos();
    int currentMinoNum = 0;
    int holdCount = 0;// ホールド回数、ミノ設置毎にリセット
    boolean isPlay = true;

    /**
     * コンストラクタ。呼び出されることでゲームが開始される。
     */
    public Game() {
        while (true) {
            resetFields();
            initPanels();
            initGW();
            play();
            result();
            stop();
            resultPanel.isVisible();
        }
    }

    /**
     * ミノの落下を開始する。
     */
    public void play() {
        int frameCount = 0;
        boardPanel.board.dropMino = nextMinos[currentMinoNum];
        boardPanel.repaint();
        while (!isGameOver) {
            sleep();
            frameCount++;
            if (frameCount >= dropFrame) {
                frameCount = 0;
                if (boardPanel.board.canDrop()) {// １マス下に下がれる場合
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

    /**
     * 10ミリ秒だけ処理を一時停止させる。
     */
    public void sleep() {
        final int SLEEP_TIME = 10;
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gameクラスのフィールドを初期状態にする。
     */
    public void resetFields() {
        isGameOver = false;
        dropFrame = 60;
        nextMinos = Mino.initMinos();
        currentMinoNum = 0;
        holdCount = 0;
        isPlay = true;
    }

    /**
     * boardPanelのパネルの大きさと座標を設定する。
     */
    public void initBoardPanel() {
        boardPanel = new BoardPanel();
        int width = boardPanel.getPanelWidth();
        int height = boardPanel.getPanelHeight();
        int x = 0;
        int y = 0;
        boardPanel.setBounds(x, y, width, height);
    }

    /**
     * nextMinoPanelのパネルの大きさと座標、表示する3つ先までのミノの設定をする。
     */
    public void initNextMinoPanel() {
        Mino[] printNextMinos = new Mino[3];
        for (int i = 0; i < 3; i++) {
            printNextMinos[i] = nextMinos[i + 1];
        }
        nextMinoPanel = new NextMinoPanel(printNextMinos);
        int width = nextMinoPanel.getPanelWidth();
        int height = nextMinoPanel.getPanelHeight();
        int x = boardPanel.getPanelWidth() + 5;
        int y = 20;
        nextMinoPanel.setBounds(x, y, width, height);
    }

    /**
     * holdPanelのパネルの大きさと座標を設定する。
     */
    public void initHoldPanel() {
        holdPanel = new HoldPanel();
        int width = holdPanel.getPanelWidth();
        int height = holdPanel.getPanelHeight();
        int x = boardPanel.getPanelWidth() + 5;
        int y = nextMinoPanel.getPanelHeight() + 15;
        holdPanel.setBounds(x, y, width, height);
    }

    /**
     * scorePanelのパネルの大きさと座標を設定する。
     */
    public void initScorePanel() {
        scorePanel = new ScorePanel();
        int width = scorePanel.getPanelWidth();
        int height = scorePanel.getPanelHeight();
        int x = boardPanel.getPanelWidth() + 5;
        int y = boardPanel.getPanelHeight() - height - 10;
        scorePanel.setBounds(x, y, width, height);
    }

    /**
     * resultPanelのパネルの大きさと座標を設定する。
     */
    public void initResultPanel() {
        resultPanel = new ResultPanel();
        int width = resultPanel.getPanelWidth();
        int height = resultPanel.getPanelHeight();
        int x = 0;
        int y = (boardPanel.getPanelHeight() - resultPanel.getPanelHeight()) / 2;
        resultPanel.setBounds(x, y, width, height);
        resultPanel.setVisible(false);
    }

    /**
     * 各パネルの初期設定を行うメソッドを呼び出す。
     */
    public void initPanels() {
        initBoardPanel();
        initNextMinoPanel();
        initHoldPanel();
        initScorePanel();
        initResultPanel();
    }

    /**
     * gwに各パネルを追加し、それぞれのパネルに再描画の指示を出す。
     */
    public void initGW() {
        gw = new GameWindow();
        gw.setLayout(null);
        gw.add(resultPanel);
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

    /**
     * 消去した列数から点数を算出する。
     * 
     * @param deleteCount 消去した列数
     * @return 加算するスコア
     */
    public int calcScore(int deleteCount) {
        int addScore = 0;
        for (int i = 0; i < deleteCount; i++) {
            addScore += 100 * i;
        }
        return addScore;
    }

    /**
     * resultPanelを表示する。ゲームオーバーになった際に呼び出す。
     */
    public void result() {
        resultPanel.setScore(scorePanel.getScore());
        resultPanel.repaint();
        resultPanel.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * キーが押下されているときに呼び出される。
     */
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

    /**
     * キーが離されたときに呼び出される。
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            dropFrame = 60;
        }
    }

    /**
     * Aキーを押した際の処理。回転可能ならばミノを左回転させる。
     */
    public void pressed_A() {
        int rotatedNum = boardPanel.board.dropMino.rotateNum - 1;
        if (rotatedNum < 0)
            rotatedNum = 3;
        if (boardPanel.board.canRotate(rotatedNum)) {
            boardPanel.board.dropMino.rotateNum = rotatedNum;
            boardPanel.repaint();
        }
    }

    /**
     * Dキーを押した際の処理。回転可能ならばミノを右回転させる。
     */
    public void pressed_D() {
        int rotatedNum = (boardPanel.board.dropMino.rotateNum + 1) % 4;
        if (boardPanel.board.canRotate(rotatedNum)) {
            boardPanel.board.dropMino.rotateNum = rotatedNum;
            boardPanel.repaint();
        }
    }

    /**
     * Wキーを押した際の処理。ホールド可能ならば落下中のミノをホールドし、既にホールドしているミノがあった場合はそのミノと入れ替える。
     */
    public void pressed_W() {
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

    /**
     * 右矢印キーを押した際の処理。移動可能ならばミノを右に1マス移動する。
     */
    public void pressed_Right() {
        if (boardPanel.board.canSet(boardPanel.board.dropMino.x + 1, boardPanel.board.dropMino.y)) {
            boardPanel.board.dropMino.x += 1;
            boardPanel.repaint();
        }
    }

    /**
     * 左矢印キーを押した際の処理。移動可能ならばミノを左に1マス移動する。
     */
    public void pressed_Left() {
        if (boardPanel.board.canSet(boardPanel.board.dropMino.x - 1, boardPanel.board.dropMino.y)) {
            boardPanel.board.dropMino.x -= 1;
            boardPanel.repaint();
        }
    }

    /**
     * 上矢印キーを押した際の処理。のミノを即座に可能な限り落下、設置する。
     */
    public void pressed_Up() {
        boardPanel.board.hardDrop();
        boardPanel.repaint();
    }

    /**
     * 下矢印キーを押した際の処理。dropFrameを5に変更する。
     */
    public void pressed_Down() {
        dropFrame = 60;
        if (boardPanel.board.canDrop()) {
            dropFrame = 5;
        }
    }

    /**
     * isPlayがfalseである限りゲームを停止させる。
     */
    public void stop() {
        while (!isPlay) {
            sleep();
        }
    }
}
