package helip;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;
import java.util.LinkedList;


import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Enemy extends GameObject {
    private boolean faceEast;
    private BufferedImage i1, i2;

    private Bullet bullet;

    public Enemy(int posX, int posY, int id, int size, int initDirec) {
        super(posX, posY, id, size, "/Images/hc.png");

        try {
            i1 = ImageIO.read(new File(Game.gameCurrentPath + "/Images/hc.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            i2 = ImageIO.read(new File(Game.gameCurrentPath + "/Images/hc1.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(initDirec == 0) {
            faceEast = true;
            this.setImage(i1);
        }
        else {
            faceEast = false;
            this.setImage(i2);
        }

        speedX = 2;
        speedY = 0;
    }

    public void createBullet(int vel) {
        Bullet b = new Bullet(posX, posY, 124, 16);
        b.setSpeedY(vel);
        bullet=b;
    }

    public void tick() {
            if(faceEast)
                posX += speedX;
            else
                posX-=speedX;
            bullet.tick();
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, posX, posY, null);

        bullet.render(g);
    }
}
