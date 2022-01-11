package helip;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

import java.util.Random;

public class GameUtil {
    public String iconPath;
    public String pathEnmyHeliFL, pathEnmyHeliFR;
    public String pathHlprHeliFL, pathHlprHeliFR;
    public String pathPlyrFL, pathPlyrFR;
    public String pathBlltFD;
    public String pathHlthPack;

    public static BufferedImage enemyHeliFL, enemyHeliFR;
    public static BufferedImage helperHeliFL, helperHeliFR;
    public static BufferedImage playerFL, playerFR;
    public static BufferedImage bulletFD, healthPack;
    public static BufferedImage setting, close;

    private static Random r;

    public GameUtil() {
        iconPath = new String("Data/Icons/");
        pathEnmyHeliFL = new String("enemy-helicopter256-fl.png");
        pathEnmyHeliFR = new String("enemy-helicopter256-fr.png");

        pathHlprHeliFL = new String("helper-helicopter256-fl.png");
        pathHlprHeliFR = new String("helper-helicopter256-fr.png");

        pathPlyrFL = new String("player256-fl.png");
        pathPlyrFR = new String("player256-fr.png");

        pathBlltFD = new String("bullet256-fd.png");

        pathHlthPack = new String("pharmacy256.png");

        try {
            enemyHeliFL = ImageIO.read(new File(iconPath + pathEnmyHeliFL));
            enemyHeliFR = ImageIO.read(new File(iconPath + pathEnmyHeliFR));

            helperHeliFL = ImageIO.read(new File(iconPath + pathHlprHeliFL));
            helperHeliFR = ImageIO.read(new File(iconPath + pathHlprHeliFR));

            playerFL = ImageIO.read(new File(iconPath + pathPlyrFL));
            playerFR = ImageIO.read(new File(iconPath + pathPlyrFR));

            bulletFD = ImageIO.read(new File(iconPath + pathBlltFD));
            healthPack = ImageIO.read(new File(iconPath + pathHlthPack));

            setting = ImageIO.read(new File(iconPath + "gear256.png"));
            close = ImageIO.read(new File(iconPath + "close256.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        r = new Random();
    }

    public static int getRandom(int l, int h) {
        return l + r.nextInt(h - l + 1);
    }
}
