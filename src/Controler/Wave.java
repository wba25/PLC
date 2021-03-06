package Controler;

import Models.Demon;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Wave {
    protected int x;
    protected int y;
    protected final int waves = 1;
    protected int lines;
    protected Timer timer;
    protected int interval;
    protected int delay;
    protected int waveNumber;
    protected int begin;
    protected int limit = 50;

    public Wave(int x, int y, int lines, int begin) {
        this.interval = 5000;
        this.delay = 5000;
        this.waveNumber = 0;
        this.timer = new Timer();
        this.x = x;
        this.y = y;
        this.lines = lines;
        this.begin = begin;

        generateWave();

        verifyLimit();

        timerWaves();
    }

    public Wave() {
    }

    public void timerWaves() {
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (waveNumber >= begin) {
                    if (waveNumber % 5 == 0) {
                        updateLines(2);
                    }
                    generateWave();
                }
                waveNumber++;
            }
        }, delay, interval);
    }

    public void verifyLimit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Field.demons.size() < limit) {
                    try {
                        notifyAll();
                    } catch (IllegalMonitorStateException e) {
                        System.out.println("Não há nenhuma thread em wait");
                    }
                }
            }
        }).start();
    }

    public void generateWave() {
        int tempY = y;
        int tempX = x;
        for (int i = 0; i < waves; i++) {
            for (int j = 0; j < lines; j++) {
                addEnemy((new Demon(tempX, tempY, Field.cowboy)));
                tempY += 20;
            }
            tempY = y;
            tempX += 20;
        }
    }

    synchronized public void addEnemy(Demon d) {
        if (Field.demons.size() == limit) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Field.demons.add(d);
    }

    public void updateLines(int linePlus) {
        lines += linePlus;
    }

}