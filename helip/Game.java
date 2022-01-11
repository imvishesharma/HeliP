package helip;

import java.awt.Graphics;

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
        level.update();
    }

    public int currLevel() {
        return level.currLevel();
    }
    public void genNextLevel() {
        level.genNextLevel();
    }

    public static void checkCollision(Bullet bullet) {
        int X = player.getX();
        int Y = player.getY();

        if(X <= bullet.getX() && bullet.getX() <= X + bullet.getSizeX() && Y <= bullet.getY() && bullet.getY() <= Y + bullet.getSizeY()) {
            bullet.setX(GameWindow.WIDTH + 50);
            bullet.setY(GameWindow.HEIGHT + 50);
            player.decHealth(10);

            //System.out.println("checkCollision() : Bullets hits Player");
        }

        for(Barrier b : level.getBarriers()) {
            X = b.getX();
            Y = b.getY();
            if(X <= bullet.getX() && bullet.getX() <= X + b.getSizeX() && Y <= bullet.getY() && bullet.getY() <= Y + b.getSizeY()) {
                b.decHealth();
                bullet.setX(GameWindow.WIDTH + 50);
                bullet.setY(GameWindow.HEIGHT + 50);

                //System.out.println("checkCollision() : Bullets hits Barrier");
            }
        }
    }
    public static void checkHealing(MedicPack medic) {
        int X = player.getX();
        int Y = player.getY();

        if(X <= medic.getX() && medic.getX() <= X + medic.getSizeX() && Y <= medic.getY() && medic.getY() <= Y + medic.getSizeY()) {
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
            score++;
            player.tick();
            level.tick();
        }
    }

    public void render(Graphics g) {
        if(!isPaused) {
            g.clearRect(0, 0, GameWindow.WIDTH, GameWindow.HEIGHT);

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
