package HeliP;

import java.awt.Graphics;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public abstract class GameObject {
    protected int posX, posY, id, speedX = 1, speedY = 1, size;
    protected int lower_boundX = 0, upper_boundX = 500, lower_boundY = 0, upper_boundY = 500;
    protected BufferedImage img;

    protected GameObject(int posX, int posY, int id, int size, String url) {
        this.posX = posX;
        this.posY = posY;
        this.id = id;
        this.size = size;
        try {
            img = ImageIO.read(new File(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void tick();
    public abstract void render(Graphics g);

    public void setX(int x) {
        if(x >= lower_boundX && x + size <= Game.WIDTH) {
            this.posX = x;
        }
    }
    public int getX() {return posX;}

    public void setY(int y) {
        if(y >= lower_boundY && y + size <= Game.HEIGHT) {
            this.posY = y;
        }
    }
    public int getY() {return posY;}

    public void setID(int id) {this.id = id;}
    public int getID() {return id;}

    public void setSpeedX(int spdX) {this.speedX = spdX;}
    public int getSpeedX() {return speedX;}

    public void setSpeedY(int spdY) {this.speedY = spdY;}
    public int getSpeedY() {return speedY;}

    public void setImage(BufferedImage img) {
        this.img = img;
    }
}
