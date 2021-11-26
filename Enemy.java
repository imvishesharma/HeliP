package HeliP;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;
import java.util.LinkedList;

//import HeliP.Bullet;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Enemy extends GameObject {
    private boolean faceEast = true;
    private BufferedImage i1, i2;

    private LinkedList<Bullet> bullets = new LinkedList<Bullet>();

    public Enemy(int posX, int posY, int id, int size) {
        super(posX, posY, id, size, "/Users/inq/Desktop/JAVA/HeliP/hc.png");

        try {
            i1 = ImageIO.read(new File("/Users/inq/Desktop/JAVA/HeliP/hc.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            i2 = ImageIO.read(new File("/Users/inq/Desktop/JAVA/HeliP/hc1.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        speedX = 2;
        speedY = 0;
    }

    public void createBullet() {
        bullets.add(new Bullet(posX, posY, 124, 16));
    }

    public void tick() {
        if(faceEast) {
            if(posX + size > Game.WIDTH) {
                faceEast = false;
                this.setImage(i2);
                return;
            }
            posX += speedX;
        }
        else {
            if(posX < 0) {
                faceEast = true;
                this.setImage(i1);
                return;
            }
            posX -= speedX;
        }

        for(int i = 0; i < bullets.size(); i++) {
            bullets.get(i).tick();
            if(bullets.get(i).posY > Game.HEIGHT - 30) {
                bullets.remove(i);
            }
        }

    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, posX, posY, null);

        for(int i = 0; i < bullets.size(); i++) {
            bullets.get(i).render(g);
        }
    }
}
