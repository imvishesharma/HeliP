package helip;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;


public abstract class GameObject {
    protected int posX, posY, id, speedX, speedY, size;
    protected int lower_boundX = 0, upper_boundX = Game.WIDTH, lower_boundY = 0, upper_boundY = Game.HEIGHT;
    protected BufferedImage img;

    protected GameObject(int posX, int posY, int id, int size, String url) {
        this.posX = posX;
        this.posY = posY;
        this.id = id;
        this.size = size;
        try {
            img = ImageIO.read(new File(Game.gameCurrentPath + url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void tick();
    public abstract void render(Graphics g);

    public void setX(int x) {
        if(x >= lower_boundX && x + size <= upper_boundX) {
            this.posX = x;
        }
    }
    public int getX() {return posX;}

    public void setY(int y) {
        if(y >= lower_boundY && y + size <= upper_boundY) {
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
