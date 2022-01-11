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

    private int buttonPX = 30, buttonPY = GameWindow.HEIGHT - 80;
    private int buttonSX = GameWindow.WIDTH/2 - 75, buttonSY = GameWindow.HEIGHT - 200;
    private int bL = 130, bH = 40;

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
        g.fillRect(buttonPX, buttonPY, bL, bH);
        g.fillRect(buttonPX + 160, buttonPY, bL, bH);
        g.fillRect(buttonPX + 310, buttonPY, bL, bH);

        g.setColor(Color.black);
        g.setFont(g.getFont().deriveFont(20f));
        g.drawString("Play", buttonPX + 50, buttonPY + 25);
        g.drawString("Scorecard", buttonPX + 180, buttonPY + 25);
        g.drawString("Exit", buttonPX + 350, buttonPY + 25);

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
        g.fillRect(buttonSX, buttonSY - 200, bL, bH);
        g.fillRect(buttonSX, buttonSY - 150 , bL, bH);
        g.fillRect(buttonSX, buttonSY - 100, bL, bH);

        g.setColor(Color.black);
        g.setFont(g.getFont().deriveFont(20f));
        g.drawString("Easy", buttonSX + 40, buttonSY - 175);
        g.drawString("Medium", buttonSX + 25, buttonSY - 125);
        g.drawString("Hard", buttonSX + 40, buttonSY - 75);
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
            if(mouseOver(e.getX(), e.getY(), buttonPX, buttonPY, bL, bH)) {
                if(GameWindow.game == null) {
                    GameWindow.game = new Game(currDiffSetting);
                    GameWindow.updateTime = currDiffSetting.getUpdateTime();
                    GameWindow.currState = GameWindow.STATE.GAME;
                }
                System.out.println("mousePressed() : Play Button Clicked");
            }

            else if(mouseOver(e.getX(), e.getY(), buttonPX + 160, buttonPY, bL, bH)) {
                GameWindow.currState = GameWindow.STATE.SCORES;
                System.out.println("mousePressed() : Scorecard Button Clicked");
            }

            else if(mouseOver(e.getX(), e.getY(), buttonPX + 310, buttonPY, bL, bH)) {
                System.out.println("mousePressed() : Exit Button Clicked");
                System.exit(0);
            }

            else if(mouseOver(e.getX(), e.getY(), 450, 30, 32, 32)) {
                System.out.println("mousePressed() : Setting Button Clicked");
                GameWindow.currState = GameWindow.STATE.SETTING;
            }
        }
        else if(GameWindow.currState == GameWindow.STATE.SETTING) {
            if(mouseOver(e.getX(), e.getY(), buttonSX, buttonSY - 200, bL, bH)) {
                currDiffSetting.setDiff(DiffSetting.DIFFICULTY.EASY);
                GameWindow.currState = GameWindow.STATE.MENU;

                System.out.println("mousePressed() : Easy Button Clicked");
            }
            else if(mouseOver(e.getX(), e.getY(), buttonSX, buttonSY - 150, bL, bH)) {
                currDiffSetting.setDiff(DiffSetting.DIFFICULTY.MEDIUM);
                GameWindow.currState = GameWindow.STATE.MENU;

                System.out.println("mousePressed() : Medium Button Clicked");
            }
            else if(mouseOver(e.getX(), e.getY(), buttonSX, buttonSY - 100, bL, bH)) {
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
            fr.write("Level-" + Long.toString(GameWindow.game.currLevel()) + " " + Long.toString(GameWindow.game.getScore()) + "\n");
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        GameWindow.currState = GameWindow.STATE.MENU;
        GameWindow.game = null;
        GameWindow.levelTimer = 0;
    }

    public void tick() {
        //if(GameWindow.currState == GameWindow.STATE.MENU) {
            for(GameObject e : obj) {
                e.tick();
            }
        //}
    }

    public void render(Graphics g) {
        g.clearRect(0, 0, GameWindow.WIDTH, GameWindow.HEIGHT);

        if(GameWindow.currState == GameWindow.STATE.MENU) {
            g.setColor(Color.black);
            g.setFont(g.getFont().deriveFont(15f));
            int linePosY = 30;

            for(String s : startStory) {
                g.drawString(s, 5, linePosY);
                linePosY += 16;
            }

            for(GameObject e : obj) {
                e.render(g);
            }

            drawPlayButton(g);
        }
        else if(GameWindow.currState == GameWindow.STATE.SETTING) {
            for(GameObject e : obj) {
                e.render(g);
            }

            drawSettingButton(g);
        }
    }
}
