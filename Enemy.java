package HeliP;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

import HeliP.Bullet;

public class Enemy extends GameObject {
    private boolean faceWest = true;
    public Enemy(int posX, int posY, int id, int size, Handler handler) {
        super(posX, posY, id, size, "/Users/inq/Desktop/JAVA/HeliP/hc.png");

        speedX = 2;
        speedY = 0;

        handler.addGameObject(new Bullet(posX, posY, 124, 16));
        /*
        long timer = System.currentTimeMillis();
        while(true) {
            long now = System.nanoTime();
            if(now - timer > 1000) {
                handler.addGameObject(new Bullet(posX, posY, 124, 16));
                timer = now;
            }
        }
*/
    }

    public void tick() {
        if(faceWest) {
            if(posX + size > Game.WIDTH) {
                faceWest = false;
                return;
            }
            posX += speedX;
        }
        else {
            if(posX < 0) {
                faceWest = true;
                return;
            }
            posX -= speedX;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, posX, posY, null);
    }
}
