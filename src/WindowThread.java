import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class WindowThread extends JPanel implements Runnable {

    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int INITIAL_X = 40;
    private final int INITIAL_Y = 40;
    private final int DELAY = 50;

    private PlayerShip playerShip;
    private Thread animator;
    KeyboardInput keyboard = new KeyboardInput();
    private int x, y;

    public WindowThread() {

        initWindow();
    }


    private void initWindow() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);
        addKeyListener(keyboard);
        setFocusable(true);
//        setVisible(true);
//        canvas.addKeyListener( keyboard );
        playerShip = new PlayerShip();

//        x = INITIAL_X;
//        y = INITIAL_Y;

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

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(playerShip.getImage(), playerShip.getX(), playerShip.getY(), this);
        Toolkit.getDefaultToolkit().sync();
    }


    public void cycle() {
//       x += 1;
//       y += 1;
//
//       if (y > B_HEIGHT) {
//           y = INITIAL_Y;
//           x = INITIAL_X;
//       }
//       if (x > B_WIDTH) {
//           y = INITIAL_Y;
//           x = INITIAL_X;
//       }


//        keyboard.poll();
//        processInput();
//        repaint();
    }


    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {
            playerShip.move();
            keyboard.poll();
            processInput();
            repaint();

            // Should we exit?
            if (keyboard.keyDownOnce(KeyEvent.VK_ESCAPE))
                break;

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

    protected void processInput() {
        // If moving down
        if (keyboard.keyDown(KeyEvent.VK_DOWN)) {
            playerShip.setDy(5);
        }
        if (!keyboard.keyDown(KeyEvent.VK_DOWN)){
            playerShip.setDy(0);
        }

        // If moving up
        if (keyboard.keyDown(KeyEvent.VK_UP)) {
            playerShip.setDy(-5);
        }

        // If moving left
        if (keyboard.keyDown(KeyEvent.VK_LEFT)) {
            playerShip.setDx(-5);
        }
        if (!keyboard.keyDown(KeyEvent.VK_LEFT)){
            playerShip.setDx(0);
        }

            // Check collision with left
//            if( bob.x < 0 )
//                bob.x = 0;

        // If moving right
        if (keyboard.keyDown(KeyEvent.VK_RIGHT)) {
            playerShip.setDx(5);
        }

//        if (keyboard.keyReleased(KeyEvent.VK_RIGHT){
//            playerShip.setDx(0);
//        }






//        // Add random circle if space bar is pressed
//        if( keyboard.keyDownOnce( KeyEvent.VK_SPACE ) ) {
//            int x = rand.nextInt( WIDTH );
//            int y = rand.nextInt( HEIGHT );
//            circles.add( new Point( x, y ) );
//        }
        // Clear circles if they press C
//        if( keyboard.keyDownOnce( KeyEvent.VK_C ) ) {
//            circles.clear();
//        }
    }
}