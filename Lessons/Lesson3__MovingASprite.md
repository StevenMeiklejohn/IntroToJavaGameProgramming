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
    private int iconWidth;
    private int iconHeight;
    private Image image;

    public PlayerShip() {

        initPlayerShip();
    }

    private void initPlayerShip() {

        ImageIcon ii = new ImageIcon("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/player1.png");
        iconWidth = ii.getIconWidth();
        iconHeight = ii.getIconHeight();
        image = ii.getImage();
        x = 100;
        y = 100;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public int getImageWidth(){
        return iconWidth;
    }

    public int getImageHeight(){
        return iconHeight;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
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
icon width and height will the width and height of the image (this will come in handy for defining limits of movement for the player).
```
        private int dx;
        private int dy;
        private int x;
        private int y;
        private int iconWidth;
        private int iconHeight;
        private Image image;
```


When we initialise the player, we obtain the image and assign it to type imageIcon.
Not only is this the best format for our purposes, it will also allow us to get the image height and width.
Then we apply the imageIcon to Image variable image.

```
    private void initPlayer() {

        ImageIcon ii = new ImageIcon("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/player1.png");
        image = ii.getImage();
        iconWidth = ii.getIconWidth();
        iconHeight = ii.getIconHeight();
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


Ok, lets go through our new Window class.

```
public class WindowThread extends JPanel implements Runnable {

    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int DELAY = 50;

    private PlayerShip playerShip;
    private Thread animator;
    KeyboardInput keyboard = new KeyboardInput();

    public WindowThread() {

        initWindow();
    }


    private void initWindow() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);
        addKeyListener(keyboard);
        setFocusable(true);
        playerShip = new PlayerShip();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        animator = new Thread(this);

        animator.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPlayer(g);
    }

    private void drawPlayer(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(playerShip.getImage(), playerShip.getX(), playerShip.getY(), this);
        Toolkit.getDefaultToolkit().sync();
    }




    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {
            playerShip.move();
            keyboard.poll();
            processInput();
            repaint();

            // Should we exit?
            if (keyboard.keyDownOnce(KeyEvent.VK_ESCAPE))
                break;

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }

            beforeTime = System.currentTimeMillis();
        }
    }

    protected void processInput() {
        // If moving down
        if (keyboard.keyDown(KeyEvent.VK_DOWN)) {
            playerShip.setDy(5);
        }
        if (!keyboard.keyDown(KeyEvent.VK_DOWN)){
            playerShip.setDy(0);
        }

        // If moving up
        if (keyboard.keyDown(KeyEvent.VK_UP)) {
            playerShip.setDy(-5);
        }

        // If moving left
        if (keyboard.keyDown(KeyEvent.VK_LEFT)) {
            playerShip.setDx(-5);
        }
        if (!keyboard.keyDown(KeyEvent.VK_LEFT)){
            playerShip.setDx(0);
        }

        // If moving right
        if (keyboard.keyDown(KeyEvent.VK_RIGHT)) {
            playerShip.setDx(5);
        }



        if( playerShip.getX() < 0 )
            playerShip.setX(0);

        if( playerShip.getX() >= (B_WIDTH - playerShip.getImageWidth()))
            playerShip.setX(B_WIDTH - playerShip.getImageWidth());

        if( playerShip.getY() < 0 )
            playerShip.setY(0);

        if( playerShip.getY() >= (B_HEIGHT - playerShip.getImageHeight()))
            playerShip.setY(B_HEIGHT - playerShip.getImageHeight());

    }
```


In our new init window we add a KeyListener and pass in a keyboard object.
We also new up an instance of our new playerShip class.

```
    KeyboardInput keyboard = new KeyboardInput();
    private void initWindow() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);
        addKeyListener(keyboard);
        setFocusable(true);
        playerShip = new PlayerShip();
    }
```

Our paint override method calls drawPlayer().

```
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPlayer(g);
    }
```

Our drawImage method has been updated to reflect the use of our new playerShip class.
As we are overriding the paintComponent method, we need to explicitly call Toolkit.sync() at the end of our paint cycle.
(as the application has no idea when you want your changes to be flushed through to the display.)


```
    private void drawPlayer(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(playerShip.getImage(), playerShip.getX(), playerShip.getY(), this);
        Toolkit.getDefaultToolkit().sync();
    }
```

Our run method has a number of updates.
As before, run is run once so we have a while loop inside it.
First, we move our playerShip.
Then we (poll) the keyboard (i.e. listen for key presses/releases)
In the event a keyboard action has been identified, it is dealt with by processInput.
Finally, we repaint the canvas.

We can add in an if which will stop the program is the user presses escape.

Finally, we do some simple maths to ensure the time between each loop is the same.

```
    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {
            playerShip.move();
            keyboard.poll();
            processInput();
            repaint();

            // Should we exit?
            if (keyboard.keyDownOnce(KeyEvent.VK_ESCAPE))
                break;

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }

            beforeTime = System.currentTimeMillis();
        }
    }
```


ProcessInput() handles key events. So, if the down arrow is pressed, the playerShip dy is set to 5.
I changed this to show how the game play much faster.
We check check for the opposite condition. So, if down is not pressed, the player dy is reset to 0.
Without this, the player would continue moving down until another direction was pressed.

```
        // If moving down
        if (keyboard.keyDown(KeyEvent.VK_DOWN)) {
            playerShip.setDy(5);
        }
        if (!keyboard.keyDown(KeyEvent.VK_DOWN)){
            playerShip.setDy(0);
        }
```


Ok, looking good. The last thing we need to is create a KeyBoardInput class.
This does the keyboard leg work for us (i.e) the logic for the poll method etc).

```
public class KeyboardInput implements KeyListener {

    private static final int KEY_COUNT = 256;

    private enum KeyState {
        RELEASED, // Not down
        PRESSED,  // Down, but not the first time
        ONCE      // Down for the first time
    }

    // Current state of the keyboard
    private boolean[] currentKeys = null;

    // Polled keyboard state
    private KeyState[] keys = null;

    public KeyboardInput() {
        currentKeys = new boolean[ KEY_COUNT ];
        keys = new KeyState[ KEY_COUNT ];
        for( int i = 0; i < KEY_COUNT; ++i ) {
            keys[ i ] = KeyState.RELEASED;
        }
    }

    public synchronized void poll() {
        for( int i = 0; i < KEY_COUNT; ++i ) {
            // Set the key state
            if( currentKeys[ i ] ) {
                // If the key is down now, but was not
                // down last frame, set it to ONCE,
                // otherwise, set it to PRESSED
                if( keys[ i ] == KeyState.RELEASED )
                    keys[ i ] = KeyState.ONCE;
                else
                    keys[ i ] = KeyState.PRESSED;
            } else {
                keys[ i ] = KeyState.RELEASED;
            }
        }
    }

    public boolean keyDown( int keyCode ) {
        return keys[ keyCode ] == KeyState.ONCE ||
                keys[ keyCode ] == KeyState.PRESSED;
    }

    public boolean keyDownOnce( int keyCode ) {
        return keys[ keyCode ] == KeyState.ONCE;
    }

    public synchronized void keyPressed( KeyEvent e ) {
        int keyCode = e.getKeyCode();
        if( keyCode >= 0 && keyCode < KEY_COUNT ) {
            currentKeys[ keyCode ] = true;
        }
    }

    public synchronized void keyReleased( KeyEvent e ) {
        int keyCode = e.getKeyCode();
        if( keyCode >= 0 && keyCode < KEY_COUNT ) {
            currentKeys[ keyCode ] = false;
        }
    }

    public void keyTyped( KeyEvent e ) {
        // Not needed
    }
}
```


Right.....All being well, if you run your app, you should now be able to control the player ship on screeen and be prevented from going outside the frame.
Nifty right?
