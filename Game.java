package HeliP;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Color;

import java.util.LinkedList;

import HeliP.Window;
import HeliP.Player;
import HeliP.Enemy;
import HeliP.Handler;


public class Game extends Canvas implements Runnable {
    private static int counter = 0;
    public static final int WIDTH = 500, HEIGHT = 500;
    private Thread thread;
    private boolean running = false;

    public static Player player;
    private Level l;

    public Game() {
        player = new Player(WIDTH/2 - 16, 438, 123, 32);

        l = new Level(5, WIDTH, HEIGHT);

        this.addKeyListener(new KeyInput(player));

        new Window(WIDTH, HEIGHT, this);
    }


    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1) {
                tick();
                delta--;
            }

            if(running) {
                render();
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                counter++;
                l.update();
                timer += 1000;
                System.out.println("FPS : " + frames);
                frames = 0;
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

        player.render(g);

        l.render(g);

        g.dispose();
        bs.show();
    }
    public static void main(String[] args) {
        new Game();
    }
}
