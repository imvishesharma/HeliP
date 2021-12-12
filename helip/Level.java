package helip;

import java.util.*;
import java.awt.Graphics;

public class Level {
    private int WIDTH, HEIGHT, SEED;
    private LinkedList<Enemy> enemies;
    private Random r;

    public Level(int seed, int prevEnemy, int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        SEED = seed;

        enemies = new LinkedList<Enemy>();

        r = new Random();

        HashMap<Integer, Integer> positions = new HashMap<Integer, Integer>();

        for(int i = 0; i < prevEnemy + 1 + r.nextInt(seed); i++) {
            positions.put(r.nextInt(WIDTH - 16), 30 + r.nextInt(100));
        }

        for(Map.Entry<Integer, Integer> p : positions.entrySet()) {
            enemies.add(new Enemy(p.getKey(), p.getValue(), 124, 16, r.nextInt(2)));
        }
    }

    public int update() {
        int bulletsCountPerUpdate = 0;
        for(Enemy e : enemies) {
            int x = r.nextInt(2000);
            if(x > 500) {
                //e.createBullet(Math.max(3, 10 - (int)SEED/5));
                bulletsCountPerUpdate++;
                e.createBullet(3);
            }
        }

        return bulletsCountPerUpdate;
        //System.out.println("update() : Seed = " + SEED + ", Bullets = " + bulletsCountPerUpdate);
    }

    public int bulletCount() {
        int total = 0;
        for(Enemy e : enemies) {
            total += e.countBullet();
        }
        return total;
    }

    public int enemyCount() {
        return enemies.size();
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
