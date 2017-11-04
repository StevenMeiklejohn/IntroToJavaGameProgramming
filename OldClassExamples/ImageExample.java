import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 01/11/2017.
 */
public class ImageExample extends JFrame {

    public ImageExample() {

        initUI();
    }

    private void initUI() {

        add(new Window());

        pack();

        setTitle("playerSprite");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                ImageExample ex = new ImageExample();
                ex.setVisible(true);
            }
        });
    }
}
