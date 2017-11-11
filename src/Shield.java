import java.awt.*;

/**
 * Created by user on 11/11/2017.
 */
public class Shield extends Sprite {
    private Image green;
    private Image yellow;
    private Image red;
    private String colour;

    public Shield(int x, int y, String type) {
        super(x, y);
        this.colour = type;
        initShield();
    }


    private void initShield() {
        if(colour.equals("green")) {
            loadImage("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/greenShieldGif.gif");
            getImageDimensions();
        }
        if(colour.equals("yellow")) {
            loadImage("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/yellowShieldGif.gif");
            getImageDimensions();
        }
        if(colour.equals("red")) {
            loadImage("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/redShieldGif.gif");
            getImageDimensions();
        }
    }

    public void move(int playerX, int playerY) {
        x = playerX;
        y = playerY;
    }
}
