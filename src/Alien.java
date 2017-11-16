import java.util.ArrayList;
import java.util.Random;

/**
 * Created by user on 05/11/2017.
 */
public class Alien extends Sprite {

    private final int INITIAL_X = 800;
    private ArrayList missiles;
    private long deltaTime;
    private long lastFired;
    public static final long MAX_FIRE_RATE = 8000000000L;
    public static final long MIN_FIRE_RATE = 3000000000L;
    public long fireInterval;





    public Alien(int x, int y) {
        super(x, y);

        initAlien();
    }

    private void initAlien() {
        missiles = new ArrayList();
        loadImage("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/enemyTriangleGif.gif");
        getImageDimensions();
        long interval = getRandomFireInterval();
        setFireInterval(interval);
    }

    public void move() {
        x -= 2;
        fire();
    }

    private void setFireInterval(long interval){
        fireInterval = interval;
    }

    private long getRandomFireInterval(){
        Random r = new Random();
//        long number = x+((long)(r.nextDouble()*(y-x)));
        long number = MIN_FIRE_RATE+((long)(r.nextDouble()*(MAX_FIRE_RATE - MIN_FIRE_RATE)));
        return number;
    }




    public boolean isInAttackRange(int playerX, int playerY){
        if(x - playerX < 10 || y - playerY < 10){
            return true;
        }
        return false;
    }

    public ArrayList getMissiles() {
        return missiles;
    }

    public void fire() {
        long currentTime = System.nanoTime();
        if(currentTime - lastFired > fireInterval){
            lastFired = System.nanoTime();
            missiles.add(new Missile2(x, y + getImageHeight()/2));
        }
    }
}
