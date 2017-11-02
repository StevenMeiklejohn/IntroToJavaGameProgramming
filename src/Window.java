import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Window extends JPanel implements Runnable {

    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int INITIAL_X = 40;
    private final int INITIAL_Y = 40;
    private final int DELAY = 100;

    private Image player;
    private Thread animator;
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

    }

    @Override
    public void addNotify() {
        super.addNotify();

        animator = new Thread(this);
        animator.start();
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



    public void cycle() {
       x += 1;
       y += 1;

       if (y > B_HEIGHT) {
           y = INITIAL_Y;
           x = INITIAL_X;
       }
       if (x > B_WIDTH) {
           y = INITIAL_Y;
           x = INITIAL_X;
       }

            repaint();
        }


    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {

            cycle();
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }

            beforeTime = System.currentTimeMillis();
        }
    }


}