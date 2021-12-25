package helip;

import java.awt.Graphics;
import java.awt.Color;

public class Barrier extends GameObject {
    private int health;

    public Barrier(int posX, int posY, int id, int size) {
        super(posX, posY, id, size, "/Images/barrier3.png");
        health = 20;
        speedX = 0;
        speedY = 0;
    }

    public int getHealth() {
        return health;
    }

    public void decHealth() {
        health -= 5;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, posX, posY, null);
    }
}
