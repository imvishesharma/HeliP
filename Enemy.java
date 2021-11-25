package HeliP;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

import HeliP.Bullet;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Enemy extends GameObject {
    private boolean faceEast = true;
    private BufferedImage i1, i2;

    public Enemy(int posX, int posY, int id, int size, Handler handler) {
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
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, posX, posY, null);
    }
}
