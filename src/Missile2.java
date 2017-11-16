/**
 * Created by user on 16/11/2017.
 */
public class Missile2 extends Sprite {

    private final int BOARD_WIDTH = 800;
    private final int MISSILE_SPEED = 10;

    public Missile2(int x, int y) {
        super(x, y);

        initMissile();
    }

    private void initMissile() {

        loadImage("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/enemyBullet.gif");
        getImageDimensions();
    }


    public void move() {

        x -= MISSILE_SPEED;

        if (x < 0) {
            vis = false;
        }
    }
}
