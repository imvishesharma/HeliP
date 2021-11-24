package HeliP;

import java.util.LinkedList;
import java.awt.Graphics;

public class Handler {
    LinkedList<GameObject> objs = new LinkedList<GameObject>();

    public void tick() {
        for(int i = 0; i < objs.size(); i++) {
            GameObject tmpObj = objs.get(i);

            tmpObj.tick();
        }
    }

    public void render(Graphics g) {
        for(int i = 0; i < objs.size(); i++) {
            GameObject tmpObj = objs.get(i);

            tmpObj.render(g);
        }
    }

    public void addGameObject(GameObject obj) {
        this.objs.add(obj);
    }

    public void removeGameObject(GameObject obj) {
        this.objs.remove(obj);
    }
}
