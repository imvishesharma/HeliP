package helip;

import java.awt.Graphics;
import java.awt.Color;

public class Bullet extends GameObject {
    public Bullet(int posX, int posY, int size) {

        super(posX, posY, GameObject.GameObjectID.BULLET, size, size);
        this.setImage(GameWindow.gameUtil.bulletFD);

        speedX = 0;
        speedY = 1;
    }

    public void tick() {
        posY += speedY;

        Game.checkCollision(this);
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, posX, posY, sizeX, sizeY, null);
    }
}
