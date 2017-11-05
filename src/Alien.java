/**
 * Created by user on 05/11/2017.
 */
public class Alien extends Sprite {

    private final int INITIAL_X = 800;

    public Alien(int x, int y) {
        super(x, y);

        initAlien();
    }

    private void initAlien() {

        loadImage("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/enemy1.png");
        getImageDimensions();
    }

    public void move() {

        if (x < 0) {
            x = INITIAL_X;
        }

        x -= 1;
    }
}
