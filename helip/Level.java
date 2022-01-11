package helip;

import java.util.*;
import java.awt.Graphics;

public class Level {
    private int WIDTH, HEIGHT, SEED;
    private LinkedList<Enemy> enemies;
    private LinkedList<Barrier> barriers;
    private LinkedList<Helper> helpers;
    private LinkedList<MedicPack> medics;

    private Random r;

    private static int currLevel;
    private static int nEnemies, nBarriers, vBullet, dmgBullet;
    private static int bulletProb, helperProb;

    public Level(int seed, int initialEnemies, int initialBarriers, int bulletVel, int bulletProb, int helperProb, int hitDamage) {
        WIDTH = GameWindow.WIDTH;
        HEIGHT = GameWindow.HEIGHT;
        SEED = seed;

        currLevel = 1;
        nEnemies = initialEnemies;
        nBarriers = initialBarriers;
        vBullet = bulletVel;
        dmgBullet = hitDamage;

        this.bulletProb = bulletProb;
        this.helperProb = helperProb;

        r = new Random();

        enemies = genEnemies();
        barriers = genBarriers();

        helpers = new LinkedList<Helper>();
        medics = new LinkedList<MedicPack>();
    }

    public int currLevel() {
        return currLevel;
    }
    public void genNextLevel() {
        currLevel++;
        nEnemies += GameUtil.getRandom(1, SEED);
        nBarriers = Math.max(0, nBarriers - GameUtil.getRandom(1, SEED));

        enemies = genEnemies();
        barriers = genBarriers();
    }

    public LinkedList<Enemy> genEnemies() {
        enemies = new LinkedList<Enemy>();

        HashMap<Integer, Integer> positions = new HashMap<Integer, Integer>();

        for(int i = 0; i < nEnemies; i++) {
            positions.put(r.nextInt(WIDTH - 16), 50 + r.nextInt(150));
        }

        for(Map.Entry<Integer, Integer> p : positions.entrySet()) {
            enemies.add(new Enemy(p.getKey(), p.getValue(), 40, r.nextInt(2)));
        }

        return enemies;
    }

    public LinkedList<Barrier> genBarriers() {
        barriers = new LinkedList<Barrier>();

        for(int i = 0; i < nBarriers; i++) {
            barriers.add(new Barrier(GameUtil.getRandom(0, WIDTH - 32), GameUtil.getRandom(400, 500), 32));
        }

        return barriers;
    }

    public int update() {
        if(r.nextInt(2) == 1) {
            if(helperProb > r.nextInt(2000)) {
                helpers.add(new Helper(GameUtil.getRandom(32, WIDTH - 32), GameUtil.getRandom(100, 150), 40, 0));
                medics.add(new MedicPack(helpers.get(helpers.size() - 1).getX(), helpers.get(helpers.size() - 1).getY(), 25));
            }
        }
        else {
            if(helperProb > r.nextInt(2000)) {
                helpers.add(new Helper(GameUtil.getRandom(32, WIDTH - 32), GameUtil.getRandom(100, 150), 40, 1));
                medics.add(new MedicPack(helpers.get(helpers.size() - 1).getX(), helpers.get(helpers.size() - 1).getY(), 25));
            }
        }

        int bulletsCountPerUpdate = 0;
        for(Enemy e : enemies) {
            if(bulletProb > r.nextInt(2000)) {
                bulletsCountPerUpdate++;
                e.createBullet(vBullet);
            }
        }

        return bulletsCountPerUpdate;
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

    public LinkedList<Barrier> getBarriers() {
        return barriers;
    }

    public void tick() {
        for(Enemy e : enemies) {
            e.tick();
        }

        for(int i = 0; i < barriers.size(); i++) {
            barriers.get(i).tick();
            if(barriers.get(i).getHealth() <= 0) {
                barriers.remove(i);
            }
        }

        for(int i = 0; i < helpers.size(); i++) {
            helpers.get(i).tick();
            if(helpers.get(i).getX() < 0 || helpers.get(i).getX() > WIDTH) {
                helpers.remove(i);
            }
        }

        for(int i = 0; i < medics.size(); i++) {
            medics.get(i).tick();
            if(medics.get(i).getY() >= GameWindow.HEIGHT - 30) {
                medics.remove(i);
            }
        }
    }

    public void render(Graphics g) {
        g.drawString("Level " + currLevel, WIDTH/2 - 10, 25);

        for(Enemy e : enemies) {
            e.render(g);
        }

        for(Barrier b : barriers) {
            b.render(g);
        }

        for(Helper h : helpers) {
            h.render(g);
        }

        for(MedicPack mp : medics) {
            mp.render(g);
        }
    }
}
