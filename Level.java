package HeliP;

import java.util.*;
import java.awt.Graphics;

public class Level {
    private int WIDTH, HEIGHT, SEED;
    private LinkedList<Enemy> enemies;
    private Random r;

    public Level(int seed, int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        SEED = seed;

        enemies = new LinkedList<Enemy>();

        r = new Random();

        HashMap<Integer, Integer> positions = new HashMap<Integer, Integer>();

        for(int i = 0; i < seed; i++) {
            positions.put(r.nextInt(WIDTH - 16), 30 + r.nextInt(100));
        }

        for(Map.Entry<Integer, Integer> p : positions.entrySet()) {
            enemies.add(new Enemy(p.getKey(), p.getValue(), 124, 16));
        }
    }

    public void update() {
        for(Enemy e : enemies) {
            if(r.nextInt(100) > SEED*5) {
                e.createBullet();
            }
        }
    }

    public void tick() {
        for(Enemy e : enemies) {
            e.tick();
        }
    }

    public void render(Graphics g) {
        g.drawString("Level " + SEED, 200, 20);
        for(Enemy e : enemies) {
            e.render(g);
        }
    }
}
