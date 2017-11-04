import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 02/11/2017.
 */
public class ThreadExample extends JFrame {

    public ThreadExample() {

        initUI();
    }

    private void initUI() {

        add(new WindowThread());

        setResizable(false);
        pack();

        setTitle("Thread example");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame ex = new ThreadExample();
                ex.setVisible(true);
            }
        });
    }
}
