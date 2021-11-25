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

    private static LinkedList<Enemy> enemies = new LinkedList<Enemy>();
    public static Player player;

    public Game() {
        //handler = new Handler();
        player = new Player(WIDTH/2 - 16, 438, 123, 32);
        enemies.add(new Enemy(WIDTH/2 - 16, 50, 124, 32));
        enemies.add(new Enemy(100, 80, 124, 32));
        //handler.addGameObject(p1);
        //handler.addGameObject(e1);

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

            if(System.currentTimeMillis() - timer > 2000) {
                counter++;
                for(int i = 0; i < enemies.size(); i++) {
                    enemies.get(i).createBullet();
                }
                timer += 2000;
                System.out.println("FPS : " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        for(int i = 0; i < enemies.size(); i++) {
            GameObject tmpObj = enemies.get(i);

            tmpObj.tick();
        }
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

        //handler.render(g);
        player.render(g);

        for(int i = 0; i < enemies.size(); i++) {
            GameObject tmpObj = enemies.get(i);

            tmpObj.render(g);
        }

        g.dispose();
        bs.show();
    }
    public static void main(String[] args) {
        new Game();
    }
}
