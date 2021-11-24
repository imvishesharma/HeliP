package HeliP;

import javax.swing.JFrame;
import java.awt.Canvas;

public class Window extends Canvas {
    public Window(final int width, final int height, Game game) {
        JFrame frame = new JFrame("HeliP");

        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setVisible(true);

        game.start();
    }
}
