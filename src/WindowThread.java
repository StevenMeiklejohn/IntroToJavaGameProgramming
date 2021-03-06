import com.sun.media.jfxmedia.MediaPlayer;
import javafx.scene.media.Media;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class WindowThread extends JPanel implements Runnable {

    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int DELAY = 50;
    private PlayerShip playerShip;
    private Thread animator;
    private ArrayList aliens;
    private ArrayList explosions;
    private ArrayList planets;
    private Shield shieldGreen;
    private Shield shieldYellow;
    private Shield shieldRed;
    private Sound music;
    private Sound explosion;
    private Sound critical_damage;
    private Sound game_over;
    private Sound player_shot;
    public static final long FIRE_RATE = 200000000L;
    public static final long CRITICAL_DAMAGE_RATE = 5000000000L;

    public long lastShot;
    public long lastCriticalDamage;



    private boolean inGame;
    private boolean inTitle;
    private boolean gameOver;
//    private static Planet planet;
    private static Background bg1, bg2;
    KeyboardInput keyboard = new KeyboardInput();
    private final int[][] wave1 = {
            {500, 400}, {2500, 59}, {1380, 89},
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
        initAliens(10);
        initExplosions();
        initPlanets();
        bg1 = new Background(0, 0);
        bg2 = new Background(800, 0);
        playerShip = new PlayerShip(75, 275);
        shieldGreen = new Shield(40, 240, "green");
        shieldYellow = new Shield(40, 240, "yellow");
        shieldRed = new Shield(40, 240, "red");
        music = new Sound("music");
        explosion = new Sound("explosion");
        critical_damage = new Sound("critical_damage");
        player_shot = new Sound("player_shot");
        game_over = new Sound("game_over");



    }

    public void initAliens(int numberOfEnemeies) {
        aliens = new ArrayList<>();
        populateRandomEnemies(numberOfEnemeies);
    }


    public void populateRandomEnemies(int numberOfEnemies){
        int counter = 0;
        int lastX = 0;
        int lastY = 0;

        while(counter <= numberOfEnemies) {
            Random rand = new Random();
            int randomX = rand.nextInt(800) + 400;
            int randomY = rand.nextInt(510) + 30;
            if(randomX - 30 > lastX - 30 && randomX + 30 < lastX + 30){
                randomX += 60;
            }
            if(randomY - 30 > lastY - 30 && randomY + 30 < lastY + 30){
                randomY += 60;
            }
            lastX = randomX;
            lastY = randomY;
            Alien alien = new Alien(randomX, randomY);
            aliens.add(alien);
            counter++;
        }
    }



    public void initExplosions(){
        explosions = new ArrayList<Explosion>();
    }

    public void initPlanets(){
        Planet planet1 = new Planet(700, 100, "darker", -5);
        Planet planet2 = new Planet(100, 200, "darker", 0);
        Planet planet3 = new Planet(400, 400, "darker", -1);
        planets = new ArrayList<Planet>();
        planets.add(planet1);
        planets.add(planet2);
        planets.add(planet3);
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
            for (Object p : planets) {
                Planet planet = (Planet) p;
                g2d.drawImage(planet.getImage(), planet.getX(),
                        planet.getY(), this);
            }
            if(playerShip.getLives() == 3) {
                shieldGreen.setVisible(true);
                shieldYellow.setVisible(false);
                shieldRed.setVisible(false);
                g2d.drawImage(shieldGreen.getImage(), playerShip.getX() - 20, playerShip.getY() - 35, this);
            }
            if(playerShip.getLives() == 2) {
                shieldGreen.setVisible(false);
                shieldYellow.setVisible(true);
                shieldRed.setVisible(false);
                g2d.drawImage(shieldYellow.getImage(), playerShip.getX() - 20, playerShip.getY() - 35, this);
            }
            if(playerShip.getLives() == 1) {
                shieldGreen.setVisible(false);
                shieldYellow.setVisible(false);
                shieldRed.setVisible(true);
                if(System.nanoTime() - lastCriticalDamage >= CRITICAL_DAMAGE_RATE) {
                    critical_damage.play();
                    lastCriticalDamage = System.nanoTime();
                }
                g2d.drawImage(shieldRed.getImage(), playerShip.getX() - 20, playerShip.getY() - 35, this);
            }
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

        for (Object a1 : aliens) {
            Alien a = (Alien) a1;
            ArrayList ams = a.getMissiles();
            for(Object m2: ams) {
                Missile2 ma = (Missile2) m2;
                g2d.drawImage(ma.getImage(), ma.getX(),
                        ma.getY(), this);
            }
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
        music.play();

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
            updatePlanets();
            updatePlayerShield();
            updatePlayerShip();
            updateAliens();
            updateMissiles();
            updateEnemyMissiles();
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

    private void updateEnemyMissiles() {
        for(int a = 0; a < aliens.size(); a++) {
            Alien alien = (Alien) aliens.get(a);
            ArrayList ams = alien.getMissiles();
            for (int m = 0; m < ams.size(); m++) {
                Missile2 bullet = (Missile2) ams.get(m);
                if (bullet.isVisible()) {
                    bullet.move();
                } else {
                    ams.remove(m);
                }
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

    private void updatePlayerShield(){
        shieldGreen.move(playerShip.getX(), playerShip.getY());
        shieldYellow.move(playerShip.getX(), playerShip.getY());
        shieldRed.move(playerShip.getX(), playerShip.getY());


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

    private void updatePlanets(){
        for (int i = 0; i < planets.size(); i++) {
            Planet planet = (Planet) planets.get(i);
            planet.update();
        }
    }

    public void checkCollisions() {


        Rectangle r3 = playerShip.getBounds();

        for (int i = 0; i < aliens.size(); i++) {
            Alien a = (Alien) aliens.get(i);
            Rectangle r2 = a.getBounds();
            ArrayList<Missile2> alienMissiles = a.getMissiles();

            if (r3.intersects(r2) && playerShip.getLives() >= 1) {
                playerShip.loseLife();
                 a.setVisible(false);
                explosions.add(new Explosion(a.getX(), a.getY()));
                explosion.play();
            }


            if (r3.intersects(r2) && playerShip.getLives() < 1) {
                playerShip.setVisible(false);
                a.setVisible(false);
                explosions.add(new Explosion(a.getX(), a.getY()));
                explosions.add(new Explosion(playerShip.getX(), playerShip.getY()));
                explosion.play();
                gameOver = true;
            }
            for (Missile2 m2: alienMissiles){
                Rectangle amr = m2.getBounds();
                if(amr.intersects(r3) && playerShip.getLives() >= 1){
                    playerShip.loseLife();
                    m2.setVisible(false);
                    explosions.add(new Explosion(a.getX(), a.getY()));

                }

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
                    explosion.play();
                }
            }
        }
    }



    protected void processInput() {

        // If moving down
        if (keyboard.keyDown(KeyEvent.VK_DOWN)) {
            playerShip.setDy(8);
        }
        if (!keyboard.keyDown(KeyEvent.VK_DOWN)){
            playerShip.setDy(0);
        }

        // If moving up
        if (keyboard.keyDown(KeyEvent.VK_UP)) {
            playerShip.setDy(-8);
        }

        // If moving left
        if (keyboard.keyDown(KeyEvent.VK_LEFT)) {
            playerShip.setDx(-8);
        }
        if (!keyboard.keyDown(KeyEvent.VK_LEFT)){
            playerShip.setDx(0);
        }

        // If moving right
        if (keyboard.keyDown(KeyEvent.VK_RIGHT)) {
            playerShip.setDx(8);
        }

        if (keyboard.keyDown(KeyEvent.VK_SPACE)){

            if(System.nanoTime() - lastShot >= FIRE_RATE) {
                playerShip.fire();
                player_shot.play();
                lastShot = System.nanoTime();
            }
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