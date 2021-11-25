package HeliP;

import java.awt.Graphics;
import java.awt.Color;

import HeliP.GameObject;

public class Bullet extends GameObject {
    public Bullet(int posX, int posY, int id, int size) {
        super(posX, posY, id, size, "/Users/inq/Desktop/JAVA/HeliP/bullet-2.png");

        speedX = 0;
        speedY = 2;
    }

    public void tick() {
        posY += speedY;
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, posX, posY, null);
    }
}
