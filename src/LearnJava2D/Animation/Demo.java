package LearnJava2D.Animation;

import javax.swing.*;
import java.awt.*;

public class Demo extends JFrame {
    public Demo(){
        initUI();
    }

    private void initUI() {
        add(new BoardThreadAnimation());

        setResizable(false);
        pack();

        setTitle("Star");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame ex = new Demo();
                ex.setVisible(true);
            }
        });
    }
}
