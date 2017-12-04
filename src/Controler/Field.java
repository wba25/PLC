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
    public static Cowboy cowboy;
    public static ArrayList<Demon> demons;
    private ArrayList<Wave> waves;
    private boolean ingame;
    private final int ICRAFT_X = 180;
    private final int ICRAFT_Y = 120;
    private final int B_WIDTH = 400;
    private final int B_HEIGHT = 300;
    private final int DELAY = 15;

    public Field() {

        initField();
    }

    private void initField() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(new Color(147,177,38));
        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        cowboy = new Cowboy(ICRAFT_X, ICRAFT_Y);

        initEnemies();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    /*
    *
    * TODO:
    *
    * Threads para gerar ondas de inimigos
    *
    * */

    public void initEnemies() {
        demons = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int x = 0;
                int y = 100;
                int wave = 4;
                int line = 5;
                //demons
                //cowboy

                for(int i = 0; i<wave; i++) {
                    for(int j = 0; j < line; j++) {
                        demons.add(new Demon(x, y, cowboy));
                        y += 20;
                    }
                    y = 100;
                    x += 20;
                }
            }
        }).start();
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
            g.drawImage(cowboy.getImage(), cowboy.getX(), cowboy.getY(), this);
        }

        ArrayList<Bullet> ms = cowboy.getBullets();

        for (Bullet m : ms) {
            if (m.isVisible()) {
                g.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }
        }

        // Thread para gerar e mover inimigos
        for (Demon a : demons) {
            if (a.isVisible()) {
                g.drawImage(a.getImage(), a.getX(), a.getY(), this);
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Bounty: " + cowboy.getBounty(), 5, 15);
    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        updateCowboy();
        updateBullets();
        updateDemons();

        checkCollisions();

        repaint();
    }

    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

    private void updateCowboy() {

        if (cowboy.isVisible()) {
            cowboy.move();
        }
    }

    private void updateBullets() {

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

    private void updateDemons() {

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

        // Verifica colisão entre cowboy e demon
        for (Demon demon : demons) {
            Rectangle r2 = demon.getBounds();

            if (r3.intersects(r2)) {
                cowboy.setVisible(false);
                demon.setVisible(false);
                ingame = false;
            }
        }

        ArrayList<Bullet> ms = cowboy.getBullets();

        // Verifica colisão entre bullet e demon
        for (Bullet m : ms) {

            Rectangle r1 = m.getBounds();

            for (Demon demon : demons) {

                Rectangle r2 = demon.getBounds();

                if (r1.intersects(r2)) {
                    m.setVisible(false);
                    demon.setVisible(false);
                    cowboy.addBounty(10);
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