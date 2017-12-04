package Controler;

import Models.Demon;
import Models.Sprite;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

// Thread para gerar uma Wave de inimigos
public class Wave extends Thread {
    protected int x;
    protected int y;
    protected int waves;
    protected int lines;
    protected Timer timer;
    protected int interval;
    protected int delay;
    protected int waveNumber;
    protected int begin;

    public Wave(int x, int y, int waves, int lines, int begin) {
        this.interval = 5000;
        this.delay = 5000;
        this.waveNumber = 0;
        this.timer = new Timer();
        this.x = x;
        this.y = y;
        this.waves = waves;
        this.lines = lines;
        this.begin = begin;

        generateWave();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(waveNumber>=begin) {
                    if (waveNumber % 5 == 0) {
                        updateWaves(2);
                    }
                    if (waveNumber % 20 == 0) {
                        updateLines(0);
                    }
                    generateWave();
                    waveNumber++;
                }
            }
        }, delay, interval);
    }

    @Override
    public void run() {

    }

    public void generateWave() {
        int startY = y;
        int startX = x;
        for(int i = 0; i<waves; i++) {
            for(int j = 0; j < lines; j++) {
                Field.demons.add(new Demon(x, y, Field.cowboy));
                y += 20;
            }
            y = startY;
            x += 20;
        }
    }

    public void updateWaves(int wavePlus) {
        waves += wavePlus;
    }

    public void updateLines(int linePlus) {
        lines += linePlus;
    }

    public void startWhen(int num) {
        begin = num;
    }

}
