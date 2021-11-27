package HeliP;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Color;

import java.util.LinkedList;

import javax.swing.*;

import HeliP.Window;
import HeliP.Player;
import HeliP.Enemy;
import HeliP.Handler;
import HeliP.Bullet;
import HeliP.Level;
import HeliP.KeyInput;
import HeliP.GameObject;

public class Game extends Canvas implements Runnable {
    private static int SCORE = 0, LEVEL = 0;
    public static final int WIDTH = 500, HEIGHT = 500;

    private Thread thread;
    public static Player player;
    public static boolean running = true;

    private Level l;

    public Game() {
        player = new Player(WIDTH/2 - 16, 438, 123, 32);

        this.addKeyListener(new KeyInput(player));

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
        int frames = 0;
        int seed = 3;

        while(running) {
            ++LEVEL;
            l = new Level(seed, WIDTH, HEIGHT);
            player.incHealth(100);
            player.setX(WIDTH/2 - 16);
            player.setY(438);

            int levelTimer = 0;

            while(player.isAlive() && levelTimer < 10) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;

                while(delta >= 1) {
                    tick();
                    delta--;
                }

                render();

                frames++;

                if(System.currentTimeMillis() - timer > 1000) {
                    SCORE++;
                    levelTimer++;
                    l.update();
                    timer += 1000;
                    System.out.println("FPS : " + frames);
                    frames = 0;
                }
            }

            tick();
            render();

            if(!player.isAlive()) {
                l = null;
                int userOption = JOptionPane.showConfirmDialog(this, "Want to replay?");
                if(userOption != JOptionPane.YES_OPTION) {
                    stop();
                }
                seed = 3;
                LEVEL = 0;
                SCORE = 0;
            } else {
                seed++;
            }
        }
        stop();
    }

    private void tick() {
        l.tick();
    }

    private void render() {
        BufferStrategy bs =  this.getBufferStrategy();

        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.red);
        g.drawString("Level " + LEVEL, 200, 20);

        g.setColor(Color.black);
        g.drawString("Score : " + SCORE, 400, 20);

        player.render(g);

        l.render(g);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Game();
    }
}
