package Models;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Cowboy extends Sprite {

    private int dx;
    private int dy;
    private final int speed = 2;
    private ArrayList<Bullet> bullets;

    public Cowboy(int x, int y) {
        super(x, y);
        initCraft();
    }

    private void initCraft() {

        bullets = new ArrayList<>();
        loadImage("/home/wellington/Desktop/PLC/Projeto/PLC/src/Assets/Game/Cowboy.png");
        getImageDimensions();
    }

    public void move() {

        x += dx;
        y += dy;

        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }
    }

    public ArrayList getBullets() {
        return bullets;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            fire(0, -1);
        }

        if (key == KeyEvent.VK_RIGHT) {
            fire(0, 1);
        }

        if (key == KeyEvent.VK_UP) {
            fire(-1, 0);
        }

        if (key == KeyEvent.VK_DOWN) {
            fire(1, 0);
        }

        if (key == KeyEvent.VK_A) {
            dx = -speed;
        }

        if (key == KeyEvent.VK_D) {
            dx = speed;
        }

        if (key == KeyEvent.VK_W) {
            dy = -speed;
        }

        if (key == KeyEvent.VK_S) {
            dy = speed;
        }
    }

    public void fire(int vertical, int horizontal) {
        bullets.add(new Bullet(x + width, y + height / 2, vertical, horizontal));
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            dx = 0;
        }

        if (key == KeyEvent.VK_D) {
            dx = 0;
        }

        if (key == KeyEvent.VK_W) {
            dy = 0;
        }

        if (key == KeyEvent.VK_S) {
            dy = 0;
        }
    }
}