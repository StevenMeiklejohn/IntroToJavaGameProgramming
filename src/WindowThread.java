import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class WindowThread extends JPanel implements Runnable {

    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int DELAY = 50;

    private PlayerShip playerShip;
    private Thread animator;
    private ArrayList aliens;
    private boolean inGame;
    KeyboardInput keyboard = new KeyboardInput();
    private final int[][] pos = {
            {2380, 29}, {2500, 59}, {1380, 89},
            {780, 109}, {580, 139}, {680, 239},
            {790, 259}, {760, 50}, {790, 150},
            {980, 209}, {560, 45}, {510, 70},
            {930, 159}, {590, 80}, {530, 60},
            {940, 59}, {990, 30}, {920, 200},
            {900, 259}, {660, 50}, {540, 90},
            {810, 220}, {860, 20}, {740, 180},
            {820, 128}, {490, 170}, {700, 30}
    };

    public WindowThread() {

        initWindow();
    }


    private void initWindow() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);
        addKeyListener(keyboard);
        setFocusable(true);
        inGame = true;
        initAliens();
        playerShip = new PlayerShip(100, 300);
    }

    public void initAliens() {
        aliens = new ArrayList<>();

        for (int[] p : pos) {
            aliens.add(new Alien(p[0], p[1]));
        }
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
        if (inGame) {

            doDrawing(g);

        } else {

            drawGameOver(g);
        }
    }



    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        if(playerShip.isVisible()) {
            g2d.drawImage(playerShip.getImage(), playerShip.getX(), playerShip.getY(), this);
        }

        ArrayList ms = playerShip.getMissiles();

        for (Object m1 : ms) {
            Missile m = (Missile) m1;
            g2d.drawImage(m.getImage(), m.getX(),
                    m.getY(), this);
        }

        for (Object a1 : aliens) {
            Alien a = (Alien) a1;
            g2d.drawImage(a.getImage(), a.getX(),
                    a.getY(), this);
        }

//        for (Alien a : aliens) {
//            if (a.isVisible()) {
//                g.drawImage(a.getImage(), a.getX(), a.getY(), this);
//            }
//        }

        g.setColor(Color.WHITE);
        g.drawString("Aliens left: " + aliens.size(), 5, 15);
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }




    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (inGame) {
            updatePlayerShip();
            updateMissiles();
            updateAliens();

            checkCollisions();
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

    private void updateMissiles() {

        ArrayList ms = playerShip.getMissiles();

        for (int i = 0; i < ms.size(); i++) {

            Missile m = (Missile) ms.get(i);

            if (m.isVisible()) {

                m.move();
            } else {

                ms.remove(i);
            }
        }
    }

    private void updatePlayerShip() {

        if (playerShip.isVisible()) {
            playerShip.move();
        }
    }

    private void updateAliens() {

        if (aliens.isEmpty()) {

            inGame = false;
            return;
        }

        for (int i = 0; i < aliens.size(); i++) {
            Alien a = (Alien) aliens.get(i);

            if (a.isVisible()) {
                a.move();
            } else {
                aliens.remove(i);
            }
        }
    }

    public void checkCollisions() {


        Rectangle r3 = playerShip.getBounds();

        for (int i = 0; i < aliens.size(); i++) {
            Alien a = (Alien) aliens.get(i);
            Rectangle r2 = a.getBounds();

            if (r3.intersects(r2)) {
                playerShip.setVisible(false);
                a.setVisible(false);
                inGame = false;
            }
        }


        ArrayList<Missile> ms = playerShip.getMissiles();

        for (Missile m : ms) {

            Rectangle r1 = m.getBounds();

            for (int i = 0; i < aliens.size(); i++) {
                Alien a = (Alien) aliens.get(i);
                Rectangle r2 = a.getBounds();
                if (r1.intersects(r2)) {
                    m.setVisible(false);
                    a.setVisible(false);
                }
            }
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

        // If moving right
        if (keyboard.keyDown(KeyEvent.VK_RIGHT)) {
            playerShip.setDx(5);
        }

        if (keyboard.keyDown(KeyEvent.VK_SPACE)){
            playerShip.fire();
        }



        if( playerShip.getX() < 0 )
            playerShip.setX(0);


        if( playerShip.getX() >= (B_WIDTH - playerShip.getImageWidth()))
            playerShip.setX(B_WIDTH - playerShip.getImageWidth());

        if( playerShip.getY() < 0 )
            playerShip.setY(0);

        if( playerShip.getY() >= (B_HEIGHT - playerShip.getImageHeight()))
            playerShip.setY(B_HEIGHT - playerShip.getImageHeight());

    }
}