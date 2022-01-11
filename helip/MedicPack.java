package helip;

import java.awt.Graphics;
import java.awt.Color;

public class MedicPack extends GameObject {
    public MedicPack(int posX, int posY, int size) {

        super(posX, posY, GameObject.GameObjectID.MEDIC, size, size);
        this.setImage(GameWindow.gameUtil.healthPack);

        speedX = 0;
        speedY = 2;
    }

    public void tick() {
        posY += speedY;

        Game.checkHealing(this);
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img, posX, posY, sizeX, sizeY, null);
    }
}
