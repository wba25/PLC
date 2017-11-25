package LearnJava2D.Donut;

import javax.swing.*;
import java.awt.*;

public class Demo extends JFrame {

    public Demo() {
        initUI();
    }

    private void initUI() {
        add(new Donut());
        setSize(550,500);

        setTitle("Donut");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Demo d = new Demo();
                d.setVisible(true);
            }
        });
    }


}
