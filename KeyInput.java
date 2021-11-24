package HeliP;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    private Handler handler;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for(int i = 0; i < handler.objs.size(); i++) {
            GameObject obj = handler.objs.get(i);
            if(obj.getID() == 123) {
                if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                    obj.setX(obj.getX() - obj.getSpeedX());
                }
                else if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                    obj.setX(obj.getX() + obj.getSpeedX());
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {

    }
}
