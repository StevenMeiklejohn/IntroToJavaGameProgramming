import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 01/11/2017.
 */
public class ThreadAnimationExample extends JFrame {

    public ThreadAnimationExample() {

        initUI();
    }

    private void initUI() {

        add(new Window());

        setResizable(false);
        pack();

        setTitle("Moving Ship");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame ex = new ThreadAnimationExample();
                ex.setVisible(true);
            }
        });
    }
}
