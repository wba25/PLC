package GUI;

import Controler.Field;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    public Game() {

        initUI();
    }

    private void initUI() {

        Field f = new Field();

        setPreferredSize(new Dimension(410,330));

        add(f);

        //setResizable(false);

        pack();

        setTitle("PLC - Top Down Shooter");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game ex = new Game();
                //ex.getContentPane().setSize(1200, 1200);
                ex.setVisible(true);
            }
        });
    }
}