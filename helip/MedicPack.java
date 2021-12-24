package helip;

import java.awt.Graphics;
import java.awt.Color;

public class MedicPack extends GameObject {
    public MedicPack(int posX, int posY, int id, int size) {

        super(posX, posY, id, size, "/Images/mp.png");

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
