package Models;


public class Demon extends Sprite {

    private final int INITIAL_X = 400;
    private final int speed = 1;
    private Sprite target;
    private boolean isAxis;

    public Demon(int x, int y, Sprite target) {
        super(x, y);

        this.isAxis = true;
        this.target = target;
        initAlien();
    }

    private void initAlien() {

        loadImage("/home/wellington/Desktop/PLC/Projeto/PLC/src/Assets/Game/Demon.png");
        getImageDimensions();
    }

    public void move() {

        if (x < 0) {
            x = INITIAL_X;
        }

        // Se o alvo estar
        // Primeiro quadrante
        // Segundo quadrante
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