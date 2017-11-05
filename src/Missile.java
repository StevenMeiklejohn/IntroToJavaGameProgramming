/**
 * Created by user on 04/11/2017.
 */
public class Missile extends Sprite {

    private final int BOARD_WIDTH = 800;
    private final int MISSILE_SPEED = 10;

    public Missile(int x, int y) {
        super(x, y);

        initMissile();
    }

    private void initMissile() {

        loadImage("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/projectile.png");
        getImageDimensions();
    }


    public void move() {

        x += MISSILE_SPEED;

        if (x > BOARD_WIDTH) {
            vis = false;
        }
    }
}
