/**
 * Created by user on 05/11/2017.
 */
public class Explosion extends Sprite {

    public long timeInitialised;
    public Explosion(int x, int y) {
            super(x, y);

            initExplosion();
        }

    private void initExplosion() {

        loadImage("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/explosionGif.gif");
        getImageDimensions();
        timeInitialised = System.currentTimeMillis();
    }

    public boolean checkForRemoval(){
        long newTime, timeDiff, sleep;

        newTime = System.currentTimeMillis();
        timeDiff = newTime - timeInitialised;


        if (timeDiff > 500) {
            return true;
        } else {
            return false;
        }
    }

}
