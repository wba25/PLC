package Controler;

import Models.Demon;
import Models.Sprite;

// Thread para gerar uma Wave de inimigos
public class Wave extends Thread {
    protected int x;
    protected int y;
    protected int waves;
    protected int lines;

    public Wave(int x, int y, int waves, int lines) {
        this.x = x;
        this.y = y;
        this.waves = waves;
        this.lines = lines;
    }

    @Override
    public void run() {
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
}
