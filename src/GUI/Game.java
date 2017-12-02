package GUI;

import Controler.Field;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    public Game() {

        initUI();
    }

    private void initUI() {

        add(new Field());

        setResizable(false);
        pack();

        setTitle("Shoot me bitch");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game ex = new Game();
                ex.setVisible(true);
            }
        });
    }
}