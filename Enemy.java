package HeliP;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

public class Enemy extends GameObject {
    private boolean faceWest = true;
    public Enemy(int posX, int posY, int id, int size) {
        super(posX, posY, id, size, "/Users/inq/Desktop/JAVA/HeliP/hc.png");

        speedX = 5;
        speedY = 0;
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
