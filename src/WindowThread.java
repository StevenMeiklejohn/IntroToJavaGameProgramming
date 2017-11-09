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
    private ArrayList explosions;
    private boolean inGame;
    private boolean inTitle;
    private boolean gameOver;
    private static Background bg1, bg2;
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
        inTitle = true;
        inGame = false;
        initAliens();
        initExplosions();
        bg1 = new Background(0, 0);
        bg2 = new Background(800, 0);
        playerShip = new PlayerShip(100, 300);
    }

    public void initAliens() {
        aliens = new ArrayList<>();

        for (int[] p : pos) {
            aliens.add(new Alien(p[0], p[1]));
        }
    }

    public void initExplosions(){
        explosions = new ArrayList<Explosion>();
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
        }
        if(inTitle){
            drawGameTitle(g);
        }
        if(gameOver){
            drawGameOver(g);
        }
//        if(!inGame && !inTitle){
//            drawGameOver(g);
//        }

    }



    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;



        if(playerShip.isVisible()) {
            g.drawImage(bg1.getBackgroundImage(), bg1.getX(), bg1.getY(), this);
            g.drawImage(bg2.getBackgroundImage(), bg2.getX(), bg2.getY(), this);
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

        for (Object ex : explosions) {
            Explosion explosion = (Explosion) ex;
            g2d.drawImage(explosion.getImage(), explosion.getX(),
                    explosion.getY(), this);
        }

        g.setColor(Color.WHITE);
        g.drawString("Aliens left: " + aliens.size(), 5, 15);
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawGameOver(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        GameOver gameOver = new GameOver(0, 0);
        g2d.drawImage(gameOver.getImage(), 0, 0, this);
    }

    private void drawGameTitle(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        SplashScreen title = new SplashScreen(0, 0);
        g2d.drawImage(title.getImage(), 0, 0, this);
    }




    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while(inTitle){
            keyboard.poll();
            processInput();
        }

        while(!inGame && !inTitle){
            keyboard.poll();
            processInput();
        }

        while (inGame) {
            bg1.update();
            bg2.update();
            updatePlayerShip();
            updateMissiles();
            updateAliens();
            updateExplosions();

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
        } else {
            inGame = false;
        }
    }

    private void updateAliens() {

        if (aliens.isEmpty()) {

            inGame = false;
            gameOver = true;
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

    private void updateExplosions(){
        for (int i = 0; i < explosions.size(); i++) {
            Explosion ex = (Explosion) explosions.get(i);

            if (ex.checkForRemoval()) {
                explosions.remove(i);
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
                explosions.add(new Explosion(a.getX(), a.getY()));
                explosions.add(new Explosion(playerShip.getX(), playerShip.getY()));
                gameOver = true;
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
                    explosions.add(new Explosion(a.getX(), a.getY()));
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

        if (keyboard.keyDown(KeyEvent.VK_P)){
            inTitle = false;
            inGame = true;
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