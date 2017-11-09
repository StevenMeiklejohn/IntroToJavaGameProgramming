import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 09/11/2017.
 */
public class Background {
    private int x = 0;
    private int y = 0;


    public Background(int x , int y){
        this.x = x;
        this.y = y;
    }

    public void update(){
        x -= 1;
    }

    public void drawModel(Graphics g){
        g.drawImage(getBackgroundImage(),x,y,null);
    }

    public Image getBackgroundImage(){
        ImageIcon ic = new ImageIcon("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/spaceBackgroundReSized.png");
        return ic.getImage();
    }
    public void displayX(){
        System.out.println(x);
    }
    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }
}
