import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by user on 04/11/2017.
 */
public class Sprite {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private int iconWidth;
    private int iconHeight;
    protected boolean vis;
    protected Image image;
    private ArrayList<Image> animationFrames;

    public Sprite(int x, int y) {

        this.x = x;
        this.y = y;
        vis = true;
    }

    protected void loadImage(String imageName) {
        ImageIcon ii = new ImageIcon(imageName);
        iconWidth = ii.getIconWidth();
        iconHeight = ii.getIconHeight();
        image = ii.getImage();
    }

    public void loadAnimationFrames(Image image1, Image image2, Image image3, Image image4){
        animationFrames.add(image1);
        animationFrames.add(image2);
        animationFrames.add(image3);
        animationFrames.add(image4);
    }

    public ArrayList<Image> getAnimationFrames(){
        return this.animationFrames;
    }

    protected void getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getImageWidth(){
        return iconWidth;
    }

    public int getImageHeight(){
        return iconHeight;
    }

    public boolean isVisible() {
        return vis;
    }

    public void setVisible(Boolean visible) {
        vis = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, getImageWidth(), getImageHeight());
    }
}
