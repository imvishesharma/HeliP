package helip;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Canvas;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import helip.Menu;
import helip.GameUtil;
import helip.Game;
import helip.KeyInput;
import helip.Level;
import helip.Player;
import helip.Enemy;
import helip.Bullet;
import helip.Helper;
import helip.MedicPack;
import helip.Barrier;

import helip.DiffSetting;

public class GameWindow
{
    public enum STATE {MENU, GAME, SCORES, SETTING};

    static int COUNTER = 1;

    static final Color BACKGROUND_COLOR = Color.white;;
    public static final int WIDTH = 500, HEIGHT = 600;

    static JFrame gameFrame;
    static Canvas gameCanvas;

    static Menu menu;
    public static Game game = null;

    public static GameUtil gameUtil;
    public static STATE currState = STATE.MENU;

    static long prevUpdateTime, currUpdateTime, prevTime1000, currTime1000;
    private final int MS64 = 64, MS1000 = 1000;

    public static int updateTime = 1000;

    static long lastTime, nowTime;
    double nTicks = 64;
    double delta = 0;
    double ns = 1000000000 / nTicks;

    public static long levelTimer, TIME_PER_LEVEL = 15;

    public GameWindow() {
        gameFrame = new JFrame("HeliP");
        gameFrame.setSize(WIDTH, HEIGHT);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameCanvas = new Canvas();
        gameCanvas.setSize(WIDTH, HEIGHT);

        gameFrame.add(gameCanvas);

        gameFrame.setVisible(true);

        gameUtil = new GameUtil();

        menu = new Menu();

        gameCanvas.addMouseListener(menu);
        gameCanvas.addKeyListener(new KeyInput());
    }

    public void run() {
        gameCanvas.createBufferStrategy(3);
        BufferStrategy gameBuffer = gameCanvas.getBufferStrategy();

        prevUpdateTime = System.currentTimeMillis();
        prevTime1000 = System.currentTimeMillis();

        lastTime = System.nanoTime();
        levelTimer = 1;

        while(true) {
            if(levelTimer > TIME_PER_LEVEL) {
                if(game.currLevel() == 20) {
                    JOptionPane.showMessageDialog(gameFrame, "Hooray You Passed All level with Score = " +  game.getScore());
                    menu.closeGame();
                    levelTimer = 1;
                    continue;
                }

                JOptionPane.showMessageDialog(gameFrame, "Continue to Next Level");
                game.genNextLevel();
                levelTimer = 1;
            }

            if(currState == STATE.GAME && !game.player.isAlive()) {
                JOptionPane.showMessageDialog(gameFrame, "You Died! Score = " +  game.getScore());
                menu.closeGame();
            }

            currUpdateTime = System.currentTimeMillis();
            currTime1000 = System.currentTimeMillis();

            nowTime = System.nanoTime();
            delta += (nowTime - lastTime) / ns;
            lastTime = nowTime;

            while(delta >= 1) {
                tick();
                delta--;
            }

            if(currState == STATE.GAME && game.isRunning()) {
                if(currTime1000 - prevTime1000 >= MS1000) {
                    prevTime1000 = currTime1000;
                    levelTimer++;
                }
                if(currUpdateTime - prevUpdateTime >= updateTime) {
                    prevUpdateTime = currUpdateTime;
                    game.update();
                    //System.out.println("run() : levelTimer = " + levelTimer);
                }
            }

            do {
                Graphics g = gameBuffer.getDrawGraphics();
                render(g);
                g.dispose();
            }
            while(gameBuffer.contentsLost());

            gameBuffer.show();
        }
    }

    private void tick() {
        if(currState == STATE.MENU  || currState == STATE.SETTING) {
            menu.tick();
        }
        else if(currState == STATE.GAME) {
            game.tick();
        }
        else if(currState == STATE.SCORES) {

        }
    }

    private void render(Graphics g) {
        if(currState == STATE.MENU || currState == STATE.SETTING) {
            menu.render(g);
        }
        else if(currState == STATE.GAME) {
            game.render(g);
        }
        else if(currState == STATE.SCORES) {

        }
    }

    public static void main(String[] args) {
        GameWindow g = new GameWindow();
        g.run();
    }
}
