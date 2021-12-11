package helip;

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
            enemies.add(new Enemy(p.getKey(), p.getValue(), 124, 16, r.nextInt(2)));
        }
    }

    public void update() {
        for(Enemy e : enemies) {
            int x = r.nextInt(2000);
            if(x > 30*SEED) {
                //System.out.println("X = " + x);
                e.createBullet(Math.max(1, 6 - (int)SEED/10));
            }
        }
    }

    public void tick() {
        for(Enemy e : enemies) {
            e.tick();
        }
    }

    public void render(Graphics g) {
        //g.drawString("Level " + SEED, 200, 20);
        for(Enemy e : enemies) {
            e.render(g);
        }
    }
}
