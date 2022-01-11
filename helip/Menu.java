package helip;

import java.util.LinkedList;
import java.awt.Graphics;
import java.awt.Color;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.*;
import java.util.Scanner;

import java.awt.event.*;

public class Menu extends MouseAdapter {
    private LinkedList<String> startStory;
    private LinkedList<GameObject> obj;

    private int buttonX = GameWindow.WIDTH/2 - 75, buttonY = GameWindow.HEIGHT - 200;
    private int bL = 150, bH = 40;

    private static DiffSetting currDiffSetting;

    public Menu() {
        startStory = new LinkedList<String>();
        obj = new LinkedList<GameObject>();

        try {
            File file = new File("Data/startStory.txt");
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                startStory.add(line);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        obj.add(new Enemy(5, 200, 48, 1));
        obj.get(0).setSpeedX(2);
        obj.add(new Enemy(GameWindow.WIDTH - 48, 250, 48, 0));
        obj.get(1).setSpeedX(3);

        currDiffSetting = new DiffSetting(DiffSetting.DIFFICULTY.EASY);
    }

    private void drawPlayButton(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(buttonX, buttonY, bL, bH);
        g.fillRect(buttonX, buttonY + 50, bL, bH);
        g.fillRect(buttonX, buttonY + 100, bL, bH);

        g.setColor(Color.black);
        g.setFont(g.getFont().deriveFont(20f));
        g.drawString("Play", buttonX + 50, buttonY + 25);
        g.drawString("Scorecard", buttonX + 25, buttonY + 75);
        g.drawString("Exit", buttonX + 50, buttonY + 125);

        g.drawImage(GameWindow.gameUtil.setting, 450, 30, 32, 32, null);

        g.setFont(g.getFont().deriveFont(10f));
        if(currDiffSetting.getDiff() == DiffSetting.DIFFICULTY.EASY) {
            g.drawString("Difficulty : Easy", 420, 75);
        }
        else if(currDiffSetting.getDiff() == DiffSetting.DIFFICULTY.MEDIUM) {
            g.drawString("Difficulty : Medium", 405, 75);
        }
        else if(currDiffSetting.getDiff() == DiffSetting.DIFFICULTY.HARD) {
            g.drawString("Difficulty : Hard", 420, 75);
        }
    }

    private void drawSettingButton(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(buttonX, buttonY - 200, bL, bH);
        g.fillRect(buttonX, buttonY - 150 , bL, bH);
        g.fillRect(buttonX, buttonY - 100, bL, bH);

        g.setColor(Color.black);
        g.setFont(g.getFont().deriveFont(20f));
        g.drawString("Easy", buttonX + 50, buttonY - 175);
        g.drawString("Medium", buttonX + 35, buttonY - 125);
        g.drawString("Hard", buttonX + 50, buttonY - 75);
    }

    public boolean mouseOver(int mx, int my, int x, int y, int w, int h) {
        if(mx >= x && mx <= x + w) {
            if(my >= y && my <= y + h) {
                return true;
            }
        }

        return false;
    }

    public void mousePressed(MouseEvent e) {
        if(GameWindow.currState == GameWindow.STATE.MENU) {
            if(mouseOver(e.getX(), e.getY(), buttonX, buttonY, bL, bH)) {
                if(GameWindow.game == null) {
                    GameWindow.game = new Game(currDiffSetting);
                    GameWindow.currState = GameWindow.STATE.GAME;
                }
                System.out.println("mousePressed() : Play Button Clicked");
            }

            else if(mouseOver(e.getX(), e.getY(), buttonX, buttonY + 50, bL, bH)) {
                GameWindow.currState = GameWindow.STATE.SCORES;
                System.out.println("mousePressed() : Scorecard Button Clicked");
            }

            else if(mouseOver(e.getX(), e.getY(), buttonX, buttonY + 100, bL, bH)) {
                System.out.println("mousePressed() : Exit Button Clicked");
                System.exit(0);
            }

            else if(mouseOver(e.getX(), e.getY(), 450, 30, 32, 32)) {
                System.out.println("mousePressed() : Setting Button Clicked");
                GameWindow.currState = GameWindow.STATE.SETTING;
            }
        }
        else if(GameWindow.currState == GameWindow.STATE.SETTING) {
            if(mouseOver(e.getX(), e.getY(), buttonX, buttonY - 200, bL, bH)) {
                currDiffSetting.setDiff(DiffSetting.DIFFICULTY.EASY);
                GameWindow.currState = GameWindow.STATE.MENU;

                System.out.println("mousePressed() : Easy Button Clicked");
            }
            else if(mouseOver(e.getX(), e.getY(), buttonX, buttonY - 150, bL, bH)) {
                currDiffSetting.setDiff(DiffSetting.DIFFICULTY.MEDIUM);
                GameWindow.currState = GameWindow.STATE.MENU;

                System.out.println("mousePressed() : Medium Button Clicked");
            }
            else if(mouseOver(e.getX(), e.getY(), buttonX, buttonY - 100, bL, bH)) {
                currDiffSetting.setDiff(DiffSetting.DIFFICULTY.HARD);
                GameWindow.currState = GameWindow.STATE.MENU;

                System.out.println("mousePressed() : Hard Button Clicked");
            }
        }
        else if(GameWindow.currState == GameWindow.STATE.GAME) {
            if(mouseOver(e.getX(), e.getY(), GameWindow.WIDTH - 30, 15, 16, 16)) {
                closeGame();
                System.out.println("mousePressed() : Close Button Clicked");
            }
        }
    }

    public void closeGame() {
        String scorePath = new String("Data/");
        if(currDiffSetting.getDiff() == DiffSetting.DIFFICULTY.EASY) {
            scorePath += "EasyScore.txt";
        }
        else if(currDiffSetting.getDiff() == DiffSetting.DIFFICULTY.MEDIUM) {
            scorePath += "MediumScore.txt";
        }
        else if(currDiffSetting.getDiff() == DiffSetting.DIFFICULTY.HARD) {
            scorePath += "HardScore.txt";
        }

        try {
            File file = new File(scorePath);
            FileWriter fr = new FileWriter(file, true);
            fr.write(Long.toString(GameWindow.game.getScore()));
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        GameWindow.currState = GameWindow.STATE.MENU;
        GameWindow.game = null;
        GameWindow.levelTimer = 0;
    }

    public void tick() {
        if(GameWindow.currState == GameWindow.STATE.MENU) {
            for(GameObject e : obj) {
                e.tick();
            }
        }
    }

    public void render(Graphics g) {
        g.clearRect(0, 0, GameWindow.WIDTH, GameWindow.HEIGHT);

        if(GameWindow.currState == GameWindow.STATE.MENU) {
            g.setColor(Color.black);
            g.setFont(g.getFont().deriveFont(15f));
            int linePosY = 30;

            for(String s : startStory) {
                g.drawString(s, 5, linePosY);
                linePosY += 25;
            }

            for(GameObject e : obj) {
                e.render(g);
            }

            drawPlayButton(g);
        }
        else if(GameWindow.currState == GameWindow.STATE.SETTING) {
            drawSettingButton(g);
        }
    }
}
