package Controler;

import Models.Demon;
import Models.Sprite;

// Thread para gerar uma Wave de inimigos
public class Wave extends Thread {
    private int x;
    private int y;
    private int waves;
    private int lines;

    public Wave(int x, int y, int waves, int lines) {
        this.x = x;
        this.y = y;
        this.waves = waves;
        this.lines = lines;
    }

    @Override
    public void run() {
        int x = 0;
        int y = 100;
        int wave = 4;
        int line = 5;

        for(int i = 0; i<wave; i++) {
            for(int j = 0; j < line; j++) {
                Field.demons.add(new Demon(x, y, Field.cowboy));
                y += 20;
            }
            y = 100;
            x += 20;
        }
    }
}
