package helip;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    public enum GameObjectID {ENEMY, HELPER, PLAYER, BULLET, MEDIC, BARRIER};

    protected int posX, posY, speedX, speedY, sizeX, sizeY;
    protected int lower_boundX = 0, upper_boundX = GameWindow.WIDTH, lower_boundY = 0, upper_boundY = GameWindow.HEIGHT;
    protected GameObjectID id;
    protected BufferedImage img;

    protected GameObject(int posX, int posY, GameObjectID id, int sizeX, int sizeY) {
        this.posX = posX;
        this.posY = posY;
        this.id = id;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.img = img;
        this.speedX = 1;
        this.speedY = 0;
    }

    public abstract void tick();
    public abstract void render(Graphics g);

    public void setX(int x) {
        this.posX = x;
    }
    public int getX() {return posX;}

    public void setY(int y) {
        this.posY = y;
    }
    public int getY() {return posY;}

    public void setID(GameObjectID id) {this.id = id;}
    public GameObjectID getID() {return id;}

    public void setSpeedX(int spdX) {this.speedX = spdX;}
    public int getSpeedX() {return speedX;}

    public void setSpeedY(int spdY) {this.speedY = spdY;}
    public int getSpeedY() {return speedY;}

    public void setImage(BufferedImage img) {
        this.img = img;
    }

    public int getSizeX() {
        return sizeX;
    }
    public int getSizeY() {
        return sizeY;
    }
}
