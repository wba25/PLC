package Models;

public class Mummy extends Sprite {
    private final int speed = 1;
    private Sprite target;

    public Mummy(int x, int y, Sprite target) {
        super(x, y);
        this.target = target;
        initMummy();
    }

    private void initMummy() {
        loadImage("../Assets/Game/Mummy.png");
        getImageDimensions();
    }

    public void move() {
        if (y > target.getY())
            y -= speed;
        if (y < target.getY())
            y += speed;
        if(x > target.getX())
            x -= speed;
        if(x < target.getX())
            x += speed;
    }
}
