import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 09/11/2017.
 */
public class Background {
    private int bgX = 0;
    private int bgY = 0;
    private int speedX;


    public Background(int x , int y){
        bgX = x;
        bgY = y;
        speedX = -5;
    }

    public void update() {
        bgX += speedX;
        if (bgX <= -800){
            bgX += 1600;
        }
    }

    public Image getBackgroundImage(){
        ImageIcon ic = new ImageIcon("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/spaceBG1.png");
        return ic.getImage();
    }

    public int getX(){
        return bgX;
    }

    public int getY(){
        return bgY;
    }

    public void setX(int x){
        this.bgX = x;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }
}
