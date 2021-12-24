package helip;

import java.util.*;
import java.awt.Graphics;

public class Level {
    private int WIDTH, HEIGHT, SEED;
    private LinkedList<Enemy> enemies;
    private LinkedList<Helper> helpers;
    private LinkedList<MedicPack> medipacks;

    private Random r;

    public Level(int seed, int prevEnemy, int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        SEED = seed;

        enemies = new LinkedList<Enemy>();
        helpers = new LinkedList<Helper>();
        medipacks = new LinkedList<MedicPack>();

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

    public void addHelper() {
        if(r.nextInt(1000) < 100) {
            Helper h = new Helper(16 + r.nextInt()%(WIDTH - 15), 150 + r.nextInt()%100, 167, 16, r.nextInt()%2);
            helpers.add(h);
        }
    }

    public void genMediPack() {
        for(Helper h : helpers) {
            MedicPack b = new MedicPack(h.getX(), h.getY(), 111, 32);
            b.setSpeedY(2);
            medipacks.add(b);
        }
    }

    public void tick() {
        for(Enemy e : enemies) {
            e.tick();
        }

        for(int i = 0; i < helpers.size(); i++) {
            helpers.get(i).tick();
            if(helpers.get(i).posX < 0 || helpers.get(i).posX >= WIDTH) {
                helpers.remove(i);
            }
        }

        for(int i = 0; i < medipacks.size(); i++) {
            medipacks.get(i).tick();
            if(medipacks.get(i).getY() > HEIGHT) {
                medipacks.remove(i);
            }
        }
    }

    public void render(Graphics g) {
        //g.drawString("Level " + SEED, 200, 20);
        for(Enemy e : enemies) {
            e.render(g);
        }

        for(Helper h : helpers) {
            h.render(g);
        }

        for(MedicPack m : medipacks) {
            m.render(g);
        }
    }
}
