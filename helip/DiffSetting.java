package helip;

import java.io.*;
import java.util.Scanner;

public class DiffSetting {
    public enum DIFFICULTY {EASY, MEDIUM, HARD};

    private DIFFICULTY currDiff;
    private String commonPath;

    private int[] settingVal;

    public DiffSetting(DIFFICULTY currDiff) {
        this.currDiff = currDiff;

        commonPath = new String("Data/Settings/");
        settingVal = new int[7];

        this.setDiff(currDiff);
    }

    public DIFFICULTY getDiff() {
        return currDiff;
    }

    public void setDiff(DIFFICULTY currDiff) {
        this.currDiff = currDiff;
        String path;
        if(currDiff == DIFFICULTY.EASY) {
            path = new String("Easy.txt");
        }
        else if(currDiff == DIFFICULTY.MEDIUM) {
            path = new String("Medium.txt");
        }
        else {//if(currDiff == DIFFICULTY.HARD) {
            path = new String("Hard.txt");
        }

        int i = 0;
        try {
            File file = new File(commonPath + path);
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()) {
                String[] tmp = scanner.nextLine().split(" ", 0);
                settingVal[i] = Integer.parseInt(tmp[1]);
                i++;
            }

            scanner.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getSeed() {
        return settingVal[0];
    }
    public int getNEnemy() {
        return settingVal[1];
    }
    public int getNBarrier() {
        return settingVal[2];
    }
    public int getBulletVel() {
        return settingVal[3];
    }
    public int getBulletProb() {
        return settingVal[4];
    }
    public int getHelperProb() {
        return settingVal[5];
    }
    public int getUpdateTime() {
        return settingVal[6];
    }
}
