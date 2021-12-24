package heliP;

import java.awt.Graphics;
import java.awt.Color;

//import HeliP.GameObject;

public class Barrier extends GameObject {
    int health;
    public Barrier(int posX, int posY, int id, int size) {
        super(posX, posY, id, size, Game.gameCurrentPath + "/Images/Barrier_img.png");
        health =100;
        speedX = 0;
        speedY = 0;
    }
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, posX, posY, null);
    }
}
