package helip;

import java.awt.*;
import javax.swing.*;

public class Player extends GameObject {
    private int health = 100;

    public Player(int size) {
        super(GameWindow.WIDTH/2 - size/2, GameWindow.HEIGHT - size - 30, GameObject.GameObjectID.PLAYER, size, size);
        this.setImage(GameWindow.gameUtil.playerFL);

        speedX = 10;
        speedY = 0;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.drawImage(img, posX, posY, sizeX, sizeY, null);

        g2d.setStroke(new BasicStroke(2.0f));
        g2d.drawRect(10, 10, 100, 20);

        g2d.setColor(Color.red);
        g2d.fillRect(11, 11, Math.max(0, health - 2), 18);

        g2d.drawString(String.valueOf(health), 120, 25);
    }

    public int getHealth() {
        return health;
    }

    public void decHealth(int dec) {
        this.health = Math.max(health - dec, 0);
    }

    public void incHealth(int inc) {
        health = Math.min(health + inc, 100);
    }

    public boolean isAlive() {
        return (health > 0);
    }
}
