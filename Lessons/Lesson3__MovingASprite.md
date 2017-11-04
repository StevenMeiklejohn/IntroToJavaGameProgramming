### Lesson 3

### Moving a Sprite.

Before we look at ways to control out player ship, lets do a bit of housekeeping and create a playerShip class.
This class will handle getting the relevant image, paint and move methods.

```
public class PlayerShip {

    private int dx;
    private int dy;
    private int x;
    private int y;
    private Image image;

    public PlayerShip() {

        initPlayer();
    }

    private void initPlayer() {

        ImageIcon ii = new ImageIcon("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/player1.png");
        image = ii.getImage();
        x = 40;
        y = 60;
    }


    public void move() {
        x += dx;
        y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}
```


This class will represent out player's spaceship.
dx and dy will track the move direction, with x and y tracking the current position of the player.
```
    private int dx;
    private int dy;
    private int x;
    private int y;
    private Image image;
```


When we initialise the player, we obtain the image and set its size.

```
    private void initPlayer() {

        ImageIcon ii = new ImageIcon("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/player1.png");
        image = ii.getImage();
        x = 40;
        y = 60;
    }
```

As you will see in the keyListener, when the user presses left, dx is assigned the value -1.
When pressing right, its assigned +1. Our move method is called every cycle, which has the effect of moving our ship
1px per cycle as long as the corresponding key is pressed.

```
    public void move() {
        x += dx;
        y += dy;
    }
 ```

Handliing key presses by updating the dx, dy.

```
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
```


Next, Let's update our window class to reflect the use of a playerShip class and implement the key:istener.

First of all, we can remove the loadIMage() method as this now being handled by the playerShip class.

We can also remove this for the same reason.

```
        x = INITIAL_X;
        y = INITIAL_Y;
```

