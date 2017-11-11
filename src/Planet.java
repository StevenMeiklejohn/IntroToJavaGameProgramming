import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 10/11/2017.
 */
public class Planet {
    private int bgX = 0;
    private int bgY = 0;
    private int speedX;
    private String planetType;



    public Planet(int x , int y, String type, int speed){
        bgX = x;
        bgY = y;
        speedX = speed;
        planetType = type;
    }

    public void update() {
        bgX += speedX;
        if (bgX <= -30){
            bgX += 800;
        }
    }

    public Image getImage(){
//        if (planetType.equals("red")){
//            ImageIcon ic = new ImageIcon("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/planetGif.gif");
//            return ic.getImage();
//        }
//        else {
            ImageIcon ic = new ImageIcon("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/planetGifSmall.gif");
            return ic.getImage();
//        }
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
