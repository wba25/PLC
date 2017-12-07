package Controler;


import Models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class Field extends JPanel implements ActionListener {

    private Timer timer;


    public static Cowboy cowboy;
    public static ArrayList<Demon> demons;
    public static ArrayList<Mummy> mummies;
    public static  ArrayList<Ogre> ogres;
    private boolean ingame;
    private final int ICRAFT_X = 180;
    private final int ICRAFT_Y = 120;
    private final int B_WIDTH = 410;
    private final int B_HEIGHT = 330;
    private final int DELAY = 15;

    public Field() {

        initField();
    }

    public void initField() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        //setBackground(new Color(147,177,38));


        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        cowboy = new Cowboy(ICRAFT_X, ICRAFT_Y);

        initEnemies();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void initEnemies() {
        demons = new ArrayList<>();
        mummies = new ArrayList<>();
        ogres = new ArrayList<>();
        generateDemons();
        generateMummies();
        generateOgres();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set background image
        Image background = Toolkit.getDefaultToolkit().getImage("/home/wellington/Desktop/PLC/Projeto/PLC/src/Assets/Game/Background.png");
        g.drawImage(background, 0, 0, this);

        if (ingame) {
            drawObjects(g);

        } else {

            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    public void drawObjects(Graphics g) {

        if (cowboy.isVisible()) {
            g.drawImage(cowboy.getImage(), cowboy.getX(), cowboy.getY(), this);
        }

        ArrayList<Bullet> ms = cowboy.getBullets();

        for (Bullet m : ms) {
            if (m.isVisible()) {
                g.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }
        }

        Iterator<Demon> interDemons = demons.iterator();

        while (interDemons.hasNext()) {
            Demon d = interDemons.next();

            if (d.isVisible())
                g.drawImage(d.getImage(), d.getX(), d.getY(), this);
        }

        /*
        for (Demon a : demons) {
            if (a.isVisible()) {
                g.drawImage(a.getImage(), a.getX(), a.getY(), this);
            }
        }
        */

        for (Mummy mm: mummies) {
            if (mm.isVisible()) {
                g.drawImage(mm.getImage(), mm.getX(), mm.getY(), this);
            }
        }

        try {
            for (Ogre o : ogres) {
                if (o.isVisible()) {
                    g.drawImage(o.getImage(), o.getX(), o.getY(), this);
                }
            }
        } catch (NullPointerException e){
            System.out.println("");
        }

        g.setColor(Color.WHITE);
        g.drawString("Bounty: " + cowboy.getBounty(), 5, 15);
    }

    public void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font big = new Font("Helvetica", Font.BOLD, 24);
        FontMetrics fm = getFontMetrics(big);

        g.setColor(Color.white);
        g.setFont(big);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, (B_HEIGHT / 2)-50);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        fm = getFontMetrics(small);
        g.setFont(small);
        String bounty = "Your bounty: "+cowboy.getBounty();
        g.drawString(bounty, (B_WIDTH - fm.stringWidth(bounty)) / 2, B_HEIGHT / 2);
    }

    // Acessado a cada Frame
    @Override
    public void actionPerformed(ActionEvent e) {
        inGame();

        updateCowboy();
        updateBullets();
        updateDemons();
        updateMummies();
        updateOgres();

        checkCollisions();

        repaint();
    }

    public void generateDemons() {
        new Wave(0, 120, 1, 2);
        new Wave(385, 120, 1, 0);
        new WaveHorizontal(180,0,1,2);
        new WaveHorizontal(180,285,1,0);
    }

    public void generateMummies() {
        new WaveMummy(0, 0, 1, 1);
        new WaveMummy(B_WIDTH-20, B_HEIGHT-20, 1, 2);
        new WaveMummy(B_WIDTH-20, 0, 1, 3);
        new WaveMummy(0, B_HEIGHT-20, 1, 4);
    }

    public void generateOgres() {
        new WaveOgres(0, 120, 1, 2);
        new WaveOgres(180,285,1,0);
    }

    public void inGame() {
        if (!ingame) {
            endGame();
            timer.stop();
        }
    }

    public void endGame() {
    }

    public void updateCowboy() {
        if (cowboy.isVisible()) {
            cowboy.move();
        }
    }

    public void updateBullets() {

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

    public void updateDemons() {
        // Para não consumir de coleção vazia
        if(demons.isEmpty()) return;

        Iterator<Demon> interdemons = demons.iterator();
        while (interdemons.hasNext()) {
            Demon d = interdemons.next();

            if (d.isVisible()) {
                d.move();
            } else {
                interdemons.remove();
            }
        }
    }

    public void updateMummies() {
        if(mummies.isEmpty()) return;

        Iterator<Mummy> interMummies = mummies.iterator();
        while (interMummies.hasNext()) {
            Mummy m = interMummies.next();

            if (m.isVisible()) {
                m.move();
            } else {
                interMummies.remove();
            }
        }
    }

    public void updateOgres() {
        if(ogres.isEmpty()) return;

        Iterator<Ogre> interOgres = ogres.iterator();
        while (interOgres.hasNext()) {
            Ogre o = interOgres.next();
            try {
                if (o.isVisible()) {
                    o.move();
                } else {
                    interOgres.remove();
                }
            } catch (NullPointerException e) {
                System.out.println("O Ogre ainda não morreu");
            }
        }
    }

    public void checkCollisions() {

        Rectangle r3 = cowboy.getBounds();

        // Verifica colisão entre cowboy e inimigo

        for (Demon demon : demons) {
            Rectangle r2 = demon.getBounds();

            if (r3.intersects(r2)) {
                cowboy.setVisible(false);
                demon.setVisible(false);
                ingame = false;
            }
        }

        for (Mummy mm : mummies) {
            Rectangle r2 = mm.getBounds();

            if (r3.intersects(r2)) {
                cowboy.setVisible(false);
                mm.setVisible(false);
                ingame = false;
            }
        }

        for (Ogre o : ogres) {
            try {
                Rectangle r2 = o.getBounds();

                if (r3.intersects(r2)) {
                    cowboy.setVisible(false);
                    o.setVisible(false);
                    ingame = false;
                }
            } catch (NullPointerException e) {
                System.out.println("O Ogre ainda não morreu");
            }
        }

        ArrayList<Bullet> bullets = cowboy.getBullets();

        // Verifica colisão entre bullet e demon
        for (Bullet b : bullets) {

            Rectangle r1 = b.getBounds();

            for (Demon demon : demons) {
                Rectangle r2 = demon.getBounds();

                if (r1.intersects(r2)) {
                    b.setVisible(false);
                    demon.setVisible(false);
                    cowboy.addBounty(10);
                }
            }

            for (Mummy mm : mummies) {
                Rectangle r2 = mm.getBounds();

                if (r1.intersects(r2)) {
                    b.setVisible(false);
                    mm.setVisible(false);
                    cowboy.addBounty(30);
                }
            }

            for (Ogre o : ogres) {
                try {
                    Rectangle r2 = o.getBounds();

                    if (r1.intersects(r2)) {
                        b.setVisible(false);
                        o.decrementLive();
                        if (o.getLive() <= 0) {
                            o.setVisible(false);
                            cowboy.addBounty(50);
                        }
                    }
                } catch (NullPointerException e) {
                    System.out.println("O Ogre ainda não morreu");
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