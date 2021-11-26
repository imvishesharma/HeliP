package HeliP;

import java.util.*;
import java.awt.Graphics;

public class Level {
    private int WIDTH, HEIGHT;
    private LinkedList<Enemy> enemies;
    private Random r;

    public Level(int seed, int width, int height) {
        WIDTH = width;
        HEIGHT = height;

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

    }

    public void tick() {
        for(Enemy e : enemies) {
            e.tick();
        }
    }

    public void render(Graphics g) {
        for(Enemy e : enemies) {
            e.render(g);
        }
    }
}
