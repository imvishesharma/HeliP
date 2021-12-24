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

        // Checking if MedicPack hits player
        int X = Game.player.getX();
        int Y = Game.player.getY();
        if(X <= this.posX && posX <= X + 24 && Y <= this.posY && posY <= Y + 32) {
            this.posX = Game.WIDTH + 50;
            this.posY = Game.HEIGHT + 50;
            Game.player.incHealth(20);
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, posX, posY, null);
    }
}
