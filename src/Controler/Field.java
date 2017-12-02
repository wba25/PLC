package Controler;


import Models.Demon;
import Models.Cowboy;
import Models.Bullet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Field extends JPanel implements ActionListener {

    private Timer timer;
    private Cowboy cowboy;
    private ArrayList<Demon> demons;
    private boolean ingame;
    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int B_WIDTH = 400;
    private final int B_HEIGHT = 300;
    private final int DELAY = 15;

    private final int[][] pos = {
            {2380, 29}, {2500, 59}, {1380, 89},
            {780, 109}, {580, 139}, {680, 239},
            {790, 259}, {760, 50}, {790, 150},
            {980, 209}, {560, 45}, {510, 70},
            {930, 159}, {590, 80}, {530, 60},
            {940, 59}, {990, 30}, {920, 200},
            {900, 259}, {660, 50}, {540, 90},
            {810, 220}, {860, 20}, {740, 180},
            {820, 128}, {490, 170}, {700, 30}
    };

    public Field() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(new Color(147,177,38));
        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        cowboy = new Cowboy(ICRAFT_X, ICRAFT_Y);

        initAliens();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void initAliens() {
        demons = new ArrayList<>();

        for (int[] p : pos) {
            demons.add(new Demon(p[0], p[1], cowboy));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (ingame) {

            drawObjects(g);

        } else {

            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {

        if (cowboy.isVisible()) {
            g.drawImage(cowboy.getImage(), cowboy.getX(), cowboy.getY(),
                    this);
        }

        ArrayList<Bullet> ms = cowboy.getBullets();

        for (Bullet m : ms) {
            if (m.isVisible()) {
                g.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }
        }

        for (Demon a : demons) {
            if (a.isVisible()) {
                g.drawImage(a.getImage(), a.getX(), a.getY(), this);
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Aliens left: " + demons.size(), 5, 15);
    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        updateCraft();
        updateMissiles();
        updateAliens();

        checkCollisions();

        repaint();
    }

    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

    private void updateCraft() {

        if (cowboy.isVisible()) {
            cowboy.move();
        }
    }

    private void updateMissiles() {

        ArrayList<Bullet> ms = cowboy.getBullets();

        for (int i = 0; i < ms.size(); i++) {

            Bullet m = ms.get(i);

            if (m.isVisible()) {
                m.move();
            } else {
                ms.remove(i);
            }
        }
    }

    private void updateAliens() {

        if (demons.isEmpty()) {

            ingame = false;
            return;
        }

        for (int i = 0; i < demons.size(); i++) {

            Demon a = demons.get(i);
            if (a.isVisible()) {
                a.move();
            } else {
                demons.remove(i);
            }
        }
    }

    public void checkCollisions() {

        Rectangle r3 = cowboy.getBounds();

        for (Demon demon : demons) {
            Rectangle r2 = demon.getBounds();

            if (r3.intersects(r2)) {
                cowboy.setVisible(false);
                demon.setVisible(false);
                ingame = false;
            }
        }

        ArrayList<Bullet> ms = cowboy.getBullets();

        for (Bullet m : ms) {

            Rectangle r1 = m.getBounds();

            for (Demon demon : demons) {

                Rectangle r2 = demon.getBounds();

                if (r1.intersects(r2)) {
                    m.setVisible(false);
                    demon.setVisible(false);
                }
            }
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            cowboy.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            cowboy.keyPressed(e);
        }
    }
}