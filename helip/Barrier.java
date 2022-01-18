package helip;

import java.awt.Graphics;
import java.awt.Color;

public class Barrier extends GameObject {
    private int health;

    public Barrier(int posX, int posY, int size) {
        super(posX, posY, GameObject.GameObjectID.BARRIER, size, 5);
        health = 20;
        speedX = 0;
        speedY = 0;
    }

    public int getHealth() {
        return health;
    }

    public void decHealth(int val) {
        health -= val;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(posX, posY, sizeX, sizeY);
    }
}
