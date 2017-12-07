package Controler;

import Models.Ogre;

import java.util.Timer;
import java.util.TimerTask;

public class WaveOgres extends Wave{
    public WaveOgres(int x, int y, int lines, int begin) {
        this.interval = 15070;
        this.delay = 10000;
        this.waveNumber = 0;
        this.timer = new Timer();
        this.x = x;
        this.y = y;
        this.lines = lines;
        this.begin = begin;
        this.limit = 5;

        verifyLimit();

        timerWaves();
    }

    @Override
    public void timerWaves() {
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                generateWave();
            }
        }, delay, interval);
    }

    public void verifyLimit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Field.ogres.size() < limit) {
                    try {
                        notifyAll();
                    } catch (IllegalMonitorStateException e) {
                        System.out.println("Não há nenhuma thread em wait");
                    }
                }
            }
        }).start();
    }

    @Override
    public void generateWave() {
        int tempY = y;
        int tempX = x;
        for (int i = 0; i < waves; i++) {
            for (int j = 0; j < lines; j++) {
                addEnemy((new Ogre(tempX, tempY, Field.cowboy)));
                tempY += 20;
            }
            tempY = y;
            tempX += 20;
        }
    }

    synchronized public void addEnemy(Ogre o) {
        if (Field.ogres.size() == limit) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Field.ogres.add(o);
    }
}
