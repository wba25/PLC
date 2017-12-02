package Models;

public class Bullet extends Sprite {

    private final int BOARD_WIDTH = 390;
    private final int BOARD_HEIGHT = 290;
    private final int speed = 5;
    private int vertical_direction;
    private int horizontal_direction;

    public Bullet(int x, int y, int vertical, int horizontal) {
        super(x, y);

        this.vertical_direction = vertical;
        this.horizontal_direction = horizontal;
        initBullet();
    }

    private void initBullet() {

        loadImage("/home/wellington/Desktop/PLC/Projeto/PLC/src/Assets/Game/Bullet.png");
        getImageDimensions();
    }

    public void move() {

        x += speed * horizontal_direction;

        y += speed * vertical_direction;

        if ((x > BOARD_WIDTH) || (x < 0))
            vis = false;

        if ((y > BOARD_HEIGHT) || (y < 0))
            vis = false;
    }
}