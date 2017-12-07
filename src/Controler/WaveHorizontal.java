package Controler;

import Models.Demon;

public class WaveHorizontal extends Wave {
    public WaveHorizontal(int x, int y, int lines, int begin) {
        super(x, y, lines, begin);
    }

    @Override
    public void generateWave() {
        int tempY = y;
        int tempX = x;
        for(int j = 0; j < waves; j++) {
            for(int i = 0; i<lines; i++) {
                Demon d = new Demon(tempX, tempY, Field.cowboy);
                d.setInHorizontal(true);
                addEnemy(d);
                tempX += 20;
            }
            tempX = x;
            tempY += 20;
        }
    }
}
