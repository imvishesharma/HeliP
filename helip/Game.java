package helip;

import java.awt.Graphics;
import java.awt.Color;

public class Game {
    public static Player player;
    private static Level level;

    public static boolean isPaused;

    private static long score;
    private DiffSetting ds;

    public Game(DiffSetting ds) {
        this.ds = ds;

        player = new Player(32);
        level = new Level(ds.getSeed(), ds.getNEnemy(), ds.getNBarrier(), ds.getBulletVel(), ds.getBulletProb(), ds.getHelperProb(), 10);
        score = 0;
        isPaused = false;
    }

    public void update() {
        score += level.update();
    }

    public int currLevel() {
        return level.currLevel();
    }
    public void genNextLevel() {
        level.genNextLevel();
    }

    public DiffSetting getDiffSett() {
        return ds;
    }

    public static void checkCollision(Bullet bullet) {
        int Xp = player.getX();
        int Yp = player.getY();

        int Xb = bullet.getX();
        int Yb = bullet.getY();


        boolean collided = false;
        if(Xp > Xb && Xp - Xb < bullet.getSizeX()) {
            if(Yp > Yb && Yp - Yb < bullet.getSizeY()) {
                collided = true;
            }
            else if(Yp < Yb && Yb - Yp < player.getSizeY()) {
                collided = true;
            }
        }
        else if(Xp < Xb && Xb - Xp < player.getSizeX()) {
            if(Yp > Yb && Yp - Yb < bullet.getSizeY()) {
                collided = true;
            }
            else if(Yp < Yb && Yb - Yp < player.getSizeY()) {
                collided = true;
            }
        }

        if(collided) {
            bullet.setX(GameWindow.WIDTH + 50);
            bullet.setY(GameWindow.HEIGHT + 50);
            player.decHealth(10);

            System.out.println("checkCollision() : Bullets hits Player");
        }

        for(Barrier b : level.getBarriers()) {
            int X = b.getX();
            int Y = b.getY();

            collided = false;
            if(X > Xb && X - Xb < bullet.getSizeX()) {
                if(Y > Yb && Y - Yb < bullet.getSizeY()) {
                    collided = true;
                }
                else if(Y < Yb && Yb - Y < b.getSizeY()) {
                    collided = true;
                }
            }
            else if(X < Xb && Xb - X < b.getSizeX()) {
                if(Y > Yb && Y - Yb < bullet.getSizeY()) {
                    collided = true;
                }
                else if(Y < Yb && Yb - Y < b.getSizeY()) {
                    collided = true;
                }
            }

            if(collided) {
                bullet.setX(GameWindow.WIDTH + 50);
                bullet.setY(GameWindow.HEIGHT + 50);
                b.decHealth(5);

                System.out.println("checkCollision() : Bullets hits Barrier");
            }
        }
    }
    public static void checkHealing(MedicPack medic) {
        int Xp = player.getX();
        int Yp = player.getY();

        int Xm = medic.getX();
        int Ym = medic.getY();

        boolean collided = false;
        if(Xp > Xm && Xp - Xm < medic.getSizeX()) {
            if(Yp > Ym && Yp - Ym < medic.getSizeY()) {
                collided = true;
            }
            else if(Yp < Ym && Ym - Yp < player.getSizeY()) {
                collided = true;
            }
        }
        else if(Xp < Xm && Xm - Xp < player.getSizeX()) {
            if(Yp > Ym && Yp - Ym < medic.getSizeY()) {
                collided = true;
            }
            else if(Yp < Ym && Ym - Yp < player.getSizeY()) {
                collided = true;
            }
        }

        if(collided) {
            medic.setX(GameWindow.WIDTH + 50);
            medic.setY(GameWindow.HEIGHT + 50);
            player.incHealth(20);

            System.out.println("checkHealing() : MedicPack heals Player");
        }
    }

    public boolean isRunning() {
        return !isPaused;
    }

    public long getScore() {
        return score;
    }
    public void tick() {
        if(!isPaused) {
            player.tick();
            level.tick();
        }
    }

    public void render(Graphics g) {
        if(!isPaused) {
            //g.clearRect(0, 0, GameWindow.WIDTH, GameWindow.HEIGHT);
            g.setColor(Color.white);
            g.fillRect(0, 0, GameWindow.WIDTH, GameWindow.HEIGHT);

            g.setColor(Color.black);

            if(ds.getDiff() == DiffSetting.DIFFICULTY.EASY) {
                g.drawString("Difficulty : Easy", 10, 45);
            }
            else if(ds.getDiff() == DiffSetting.DIFFICULTY.MEDIUM) {
                g.drawString("Difficulty : Medium", 10, 45);
            }
            else if(ds.getDiff() == DiffSetting.DIFFICULTY.HARD) {
                g.drawString("Difficulty : Hard", 10, 45);
            }
            g.drawString("levelTimer : " + GameWindow.levelTimer, GameWindow.WIDTH/2 - 25, 45);

            g.drawImage(GameWindow.gameUtil.close, GameWindow.WIDTH - 30, 15, 16, 16, null);

            g.setFont(g.getFont().deriveFont(15f));

            g.drawString("Score : " + score, GameWindow.WIDTH - 130, 35);

            player.render(g);
            level.render(g);
        }
    }
}
