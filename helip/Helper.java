package helip;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;
import java.util.LinkedList;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Helper extends GameObject {
    private boolean faceEast;

    public Helper(int posX, int posY, int size, int initDirec) {
        super(posX, posY, GameObject.GameObjectID.HELPER, size, size);

        if(initDirec == 0) {
            faceEast = true;
            this.setImage(GameWindow.gameUtil.helperHeliFR);
        }
        else {
            faceEast = false;
            this.setImage(GameWindow.gameUtil.helperHeliFL);
        }

        speedX = 2;
        speedY = 0;
    }

    public void tick() {
        if(faceEast) {
            posX += speedX;
        }
        else {
            posX -= speedX;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, posX, posY, sizeX, sizeY, null);
    }
}
