package helip;

import java.awt.Graphics;
import java.util.LinkedList;

public class Enemy extends GameObject {
    private boolean faceRight;
    private LinkedList<Bullet> bullets;

    public Enemy(int posX, int posY, int size, int faceRight) {
        super(posX, posY, GameObject.GameObjectID.ENEMY, size, size);

        if(faceRight == 1) {
            this.faceRight = true;
            this.setImage(GameWindow.gameUtil.enemyHeliFR);
        }
        else {
            this.faceRight = false;
            this.setImage(GameWindow.gameUtil.enemyHeliFL);
        }

        bullets = new LinkedList<Bullet>();
    }

    public void createBullet(int vel) {
        Bullet b = new Bullet(posX, posY, 16);
        b.setSpeedY(vel);
        bullets.add(b);
    }

    public int countBullet() {
        return bullets.size();
    }

    public void tick() {
        // System.out.println("Enemy tick()");

        if(faceRight) {
            if(posX + sizeX > GameWindow.WIDTH) {
                faceRight = false;
                this.setImage(GameWindow.gameUtil.enemyHeliFL);
                return;
            }
            posX += speedX;
        }
        else {
            if(posX < 0) {
                faceRight = true;
                this.setImage(GameWindow.gameUtil.enemyHeliFR);
                return;
            }
            posX -= speedX;
        }

        for(int i = 0; i < bullets.size(); i++) {
            bullets.get(i).tick();
            if(bullets.get(i).getY() >= GameWindow.HEIGHT - 30) {
                bullets.remove(i);
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(this.img, posX, posY, sizeX, sizeY, null);

        for(GameObject go : bullets) {
            go.render(g);
        }
    }
}
