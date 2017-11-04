import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by user on 01/11/2017.
 */
public class PlayerShip {

    private int dx;
    private int dy;
    private int x;
    private int y;
    private int iconWidth;
    private int iconHeight;
    private Image image;

    public PlayerShip() {

        initPlayerShip();
    }

    private void initPlayerShip() {

        ImageIcon ii = new ImageIcon("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/player1.png");
        iconWidth = ii.getIconWidth();
        iconHeight = ii.getIconHeight();
        image = ii.getImage();
        x = 100;
        y = 100;
    }


    public void move() {
        x += dx;
        y += dy;
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

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}
