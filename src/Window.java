import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Window extends JPanel {

    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int INITIAL_X = 40;
    private final int INITIAL_Y = 40;
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 25;

    private Image player;
    private Timer timer;
    private int x, y;

    public Window() {

        initWindow();
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/player1.png");
        player = ii.getImage();
    }

    private void initWindow() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);

        loadImage();

        x = INITIAL_X;
        y = INITIAL_Y;

        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawPlayer(g);
    }

    private void drawPlayer(Graphics g) {

        g.drawImage(player, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
            x += 1;
            y += 1;

            if (y > B_HEIGHT) {
                y = INITIAL_Y;
                x = INITIAL_X;
            }

            repaint();
        }
    }


}