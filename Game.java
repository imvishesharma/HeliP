package HeliP;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Color;

import HeliP.Window;
import HeliP.Player;
import HeliP.Enemy;
import HeliP.Handler;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 500, HEIGHT = 500;
    private Thread thread;
    private boolean running = false;

    private Handler handler;

    public Game() {

        handler = new Handler();
        Player p1 = new Player(WIDTH/2 - 16, 438, 123, 32);
        Enemy e1 = new Enemy(WIDTH/2 - 16, 50, 124, 32, handler);

        handler.addGameObject(p1);
        handler.addGameObject(e1);

        this.addKeyListener(new KeyInput(handler));

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
                handler.addGameObject(new Bullet(handler.getGameObject(1).getX(), handler.getGameObject(1).getY(), 124, 16));
                timer += 1000;
                System.out.println("FPS : " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        handler.tick();
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

        handler.render(g);

        g.dispose();
        bs.show();
    }
    public static void main(String[] args) {
        new Game();
    }
}
