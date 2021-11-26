package HeliP;

import java.awt.*;
import javax.swing.*;

//import HeliP.GameObject;

public class Player extends GameObject {
    private int health = 100;

    public Player(int posX, int posY, int id, int size) {
        super(posX, posY, id, size, "/Users/inq/Desktop/JAVA/HeliP/run.png");

        speedX = 10;
        speedY = 0;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.drawImage(img, posX, posY, null);

        g2d.setStroke(new BasicStroke(2.0f));
        g2d.drawRect(10, 10, 100, 20);

        g2d.setColor(Color.red);
        g2d.fillRect(11, 11, health - 2, 18);
    }

    public int getHealth() {
        return health;
    }

    public void decHealth(int dec) {
        health = Math.max(health - dec, 0);
    }

    public void incHealth(int inc) {
        health = Math.min(health + inc, 100);
    }
}
