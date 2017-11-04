import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by user on 01/11/2017.
 */
public class PlayerShip extends Sprite {

    private int dx;
    private int dy;
    private int iconWidth;
    private int iconHeight;
    private Image image;
    private ArrayList missiles;;

    public PlayerShip(int x, int y) {
        super(x, y);
        initPlayerShip();
    }

    private void initPlayerShip() {
        missiles = new ArrayList();
        ImageIcon ii = new ImageIcon("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/player1.png");
        iconWidth = ii.getIconWidth();
        iconHeight = ii.getIconHeight();
        image = ii.getImage();

    }


    public void move() {
        x += dx;
        y += dy;


    }

    public ArrayList getMissiles() {
        return missiles;
    }



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public int getImageWidth(){
        return iconWidth;
    }

    public int getImageHeight(){
        return iconHeight;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void fire() {
        missiles.add(new Missile(x + getImageWidth(), y + getImageHeight() / 3));
    }

//    public void keyPressed(KeyboardInput keyboard, KeyEvent e) {
//
////        int key = e.getKeyCode();
//
////        if (key == KeyEvent.VK_LEFT) {
////            dx = -1;
////        }
//
//        if (keyboard.keyDown(KeyEvent.VK_LEFT)) {
//            setDx(-5);
//        }
//        if (!keyboard.keyDown(KeyEvent.VK_LEFT)){
//            setDx(0);
//        }
////
////        if (key == KeyEvent.VK_RIGHT) {
////            dx = 1;
////        }
//        if (keyboard.keyDown(KeyEvent.VK_RIGHT)) {
//            setDx(5);
//        }
//
////        if (key == KeyEvent.VK_UP) {
////            dy = -1;
////        }
//        if (keyboard.keyDown(KeyEvent.VK_UP)) {
//            setDy(-5);
//        }
//
////        if (key == KeyEvent.VK_DOWN) {
////            dy = 1;
////        }
//        if (keyboard.keyDown(KeyEvent.VK_DOWN)) {
//            setDy(5);
//        }
//        if (!keyboard.keyDown(KeyEvent.VK_DOWN)){
//            setDy(0);
//        }
//
//        if (keyboard.keyDown(KeyEvent.VK_SPACE)){
//            fire();
//        }
//
//        if (!keyboard.keyDown(KeyEvent.VK_SPACE)){
//            stopFire();
//        }
//
////        if (key == KeyEvent.VK_SPACE) {
////            fire();
////        }
//    }
//
//    public void keyReleased(KeyEvent e) {
//
//        int key = e.getKeyCode();
//
//        if (key == KeyEvent.VK_LEFT) {
//            dx = 0;
//        }
//
//        if (key == KeyEvent.VK_RIGHT) {
//            dx = 0;
//        }
//
//        if (key == KeyEvent.VK_UP) {
//            dy = 0;
//        }
//
//        if (key == KeyEvent.VK_DOWN) {
//            dy = 0;
//        }
//    }
}
