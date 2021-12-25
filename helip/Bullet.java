package helip;

import java.awt.Graphics;
import java.awt.Color;

public class Bullet extends GameObject {
    public Bullet(int posX, int posY, int id, int size) {

        super(posX, posY, id, size, "/Images/bullet-2.png");

        speedX = 0;
        speedY = 2;
    }

    public void tick() {
        posY += speedY;

        // Checking if Bullet hits player
        int X = Game.player.getX();
        int Y = Game.player.getY();

        if(X <= this.posX && this.posX <= X + 24 && Y <= this.posY && this.posY <= Y + 32) {
            this.posX = Game.WIDTH + 50;
            this.posY = Game.HEIGHT + 50;
            Game.player.decHealth(10);
        }

        // Checking if Bullet hits Barrier
        for(int i = 0; i < Game.l.getSizeBarrier(); i++) {
            X = Game.l.getIBarrier(i).getX();
            Y = Game.l.getIBarrier(i).getY();
            if(X <= this.posX && this.posX <= X + 32 && Y <= this.posY + 8 && this.posY <= Y + 16) {
                this.posX = Game.WIDTH + 50;
                this.posY = Game.HEIGHT + 50;
                Game.l.getIBarrier(i).decHealth();
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, posX, posY, null);
    }
}
