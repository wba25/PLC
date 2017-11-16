package LearnJava2D;

import javax.swing.*;
import java.awt.*;

public class Exemplo1 extends JFrame {
    public Exemplo1() {
        initUI();
    }

    private void initUI() {
        add(new Surface());

        setTitle("Ultra Game");
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Exemplo1 ex = new Exemplo1();
                ex.setVisible(true);
            }
        });
    }
}
