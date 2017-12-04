package Controler;

import Models.Demon;

public class WaveHorizontal extends Wave {
    public WaveHorizontal(int x, int y, int waves, int lines) {
        super(x, y, waves, lines);
    }

    @Override
    public void run() {
        int startY = y;
        int startX = x;
        for(int j = 0; j < waves; j++) {
            for(int i = 0; i<lines; i++) {
                Field.demons.add(new Demon(x, y, Field.cowboy));
                x += 20;
            }
            x = startX;
            y += 20;
        }
    }
}
