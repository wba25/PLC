package Models;


public class Demon extends Sprite {
    private final int speed = 1;
    private Sprite target;
    private boolean isAxis;
    private boolean inHorizontal;

    public Demon(int x, int y, Sprite target) {
        super(x, y);

        this.isAxis = true;
        this.inHorizontal = false;
        this.target = target;
        initDemon();
    }

    public void initDemon() {
        loadImage("src/Assets/Game/Demon.png");
        getImageDimensions();
    }

    public void move() {
        if(inHorizontal) {
            if(isAxis) {
                if(y == target.getY())
                    isAxis = false;
                if (y > target.getY())
                    y -= speed;
                if (y < target.getY())
                    y += speed;
            }
            else {
                if(x == target.getX())
                    isAxis = true;
                else if(x > target.getX())
                    x -= speed;
                else if(x < target.getX())
                    x += speed;
            }
        } else {
            if(isAxis) {
                if(x == target.getX())
                    isAxis = false;
                else if(x > target.getX())
                    x -= speed;
                else if(x < target.getX())
                    x += speed;
            }
            else {
                if(y == target.getY())
                    isAxis = true;
                if (y > target.getY())
                    y -= speed;
                if (y < target.getY())
                    y += speed;
            }
        }
    }

    public void setInHorizontal(boolean inHorizontal) {
        this.inHorizontal = inHorizontal;
    }
}