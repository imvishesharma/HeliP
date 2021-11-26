package HeliP;

import java.awt.Graphics;
import java.awt.Color;

//import HeliP.GameObject;

public class Player extends GameObject {
    public Player(int posX, int posY, int id, int size) {
        super(posX, posY, id, size, "/Users/inq/Desktop/JAVA/HeliP/run.png");

        speedX = 10;
        speedY = 0;
    }

    public void tick() {
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, posX, posY, null);
    }
}
