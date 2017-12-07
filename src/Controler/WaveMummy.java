package Controler;

import Models.Mummy;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wellington on 07/12/17.
 */
public class WaveMummy extends Wave {
    public WaveMummy(int x, int y, int lines, int begin) {
        this.interval = 10000;
        this.delay = 5000;
        this.waveNumber = 0;
        this.timer = new Timer();
        this.x = x;
        this.y = y;
        this.lines = lines;
        this.begin = begin;
        this.limit = 10;

        verifyLimit();

        timerWaves();
    }

    @Override
    public void timerWaves() {
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (waveNumber >= begin) {
                    if (waveNumber % 10 == 0) {
                        updateLines(1);
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
                if (Field.mummies.size() < limit) {
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
                addEnemy((new Mummy(tempX, tempY, Field.cowboy)));
                tempY += 20;
            }
            tempY = y;
            tempX += 20;
        }
    }

    synchronized public void addEnemy(Mummy m) {
        if (Field.mummies.size() == limit) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Field.mummies.add(m);
    }
}
