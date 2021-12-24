package helip;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Color;
import java.io.File;

import java.util.LinkedList;

import javax.swing.*;

import helip.Window;
import helip.GameObject;
import helip.KeyInput;
import helip.Player;
import helip.Enemy;
import helip.Bullet;
import helip.Level;
import helip.Helper;



public class Game extends Canvas implements Runnable {
    private static int SCORE = 1, LEVEL = 0;
    public static final int WIDTH = 500, HEIGHT = 600;

    public static int currEnemy = 5;

    private Thread thread;
    public static Player player;
    private static boolean running = true;
    public static boolean isPaused = false;
    public static int START_SEED;
    public static String gameCurrentPath;
    private static Helper hp; 

    private Level l;

    public Game() {
        try {
            gameCurrentPath = new java.io.File(".").getCanonicalPath();
            //gameCurrentPath += "/helip";
            System.out.println(gameCurrentPath);

        }  catch (Exception e) {
            e.printStackTrace();
        }

        player = new Player(WIDTH/2 - 16, HEIGHT - 22, 123, 32);
        START_SEED = 5;
        this.addKeyListener(new KeyInput());

        new Window(WIDTH, HEIGHT, this);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        long timer2 = System.currentTimeMillis();
        int frames = 0;
        int seed = START_SEED;
        int gap2 = 1000;
        int userOption;

        while(running) {
            lastTime = System.nanoTime();
            timer = System.currentTimeMillis();
            timer2 = System.currentTimeMillis();
            //gap2 = Math.min(50*seed, 500);
            ++LEVEL;

            l = new Level(seed, currEnemy, WIDTH, HEIGHT);
            currEnemy = l.enemyCount();

            player.incHealth(20);
            player.setX(WIDTH/2 - 16);
            player.setY(HEIGHT - 62);

            int levelTimer = 1;

            while(player.isAlive() && levelTimer <= 10) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                hp = new Helper();

                while(delta >= 1) {
                    tick();
                    delta--;
                }

                render();
                frames++;

                if(System.currentTimeMillis() - timer2 > gap2) {
                    if(!isPaused) {
                        //System.out.println("Level " + LEVEL + ", Bullets " + l.bulletCount());

                        int x = l.update();
                        System.out.print(x + ", ");
                    }
                    timer2 += gap2;
                }
                if(System.currentTimeMillis() - timer > 1000) {
                    if(!isPaused) {
                        SCORE++;
                        levelTimer++;
                        //System.out.println("FPS : " + frames);
                    }

                    timer += 1000;
                    //System.out.println(isPaused == false ? "FALSE" : "TRUE");
                    frames = 0;
                }
            }

            tick();
            render();

            if(!player.isAlive()) {
                l = null;
                userOption = JOptionPane.showConfirmDialog(this, "Want to replay?");
                if(userOption != JOptionPane.YES_OPTION) {
                    //stop();
                    System.exit(0);
                }
                seed = START_SEED;
                LEVEL = 0;
                SCORE = 1;
                player.incHealth(100);
            } else {
                JOptionPane.showMessageDialog(this, "Go to next level");
                seed++;
                System.out.println();
            }
        }
        //stop();
    }

    private void tick() {
        if(!isPaused) {
            l.tick();
            if(hp){
                hp.tick();
                if(hp.getX()<0||hp.getX()>WIDTH)
                        hp=NULL;

            }
        }
    }

    private void render() {
        if(!isPaused) {
            BufferStrategy bs =  this.getBufferStrategy();

            if(bs == null) {
                this.createBufferStrategy(3);
                return;
            }
            Graphics g = bs.getDrawGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, WIDTH, HEIGHT);

            g.setColor(Color.red);
            g.drawString("Level " + LEVEL, WIDTH/2 - 20, 25);

            g.setColor(Color.black);
            g.drawString("Score : " + SCORE, WIDTH - 100, 20);

            player.render(g);

            l.render(g);
            if(hp)
                hp.render(g);

            g.dispose();
            bs.show();
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
