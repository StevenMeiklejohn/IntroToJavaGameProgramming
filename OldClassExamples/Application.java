import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 01/11/2017.
 */
public class Application extends JFrame {


    public Application() {

        initUI();
    }

    private void initUI() {

        add(new Window());

        setSize(800, 500);

        setTitle("Codeclan Game3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Application ex = new Application();
                ex.setVisible(true);
            }
        });
    }
}
