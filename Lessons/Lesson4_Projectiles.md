### Lesson 4.

### Projectiles.

We have a moveable player on the screen. Now, lets try and make the player ship fire a projectile.
It should:
- Appear in the correct position at the front of the player ship.
- Shoot across the screen.
- Be disposed of once it leaves the screen.

Before we continue in earnest, lets consider the direction our game is heading.
If we can fire bullets, it stands to reason we will need something to fire them at.
Our 'enemies' will have much in common with our player ship, (generate an image, paint it, move it, etc)
So, at this point it might be a good idea to put some of this basic functionality into a Sprite superclass, that playerShip and Enemy can inherit from.

```
public class Sprite {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean vis;
    protected Image image;

    public Sprite(int x, int y) {

        this.x = x;
        this.y = y;
        vis = true;
    }

    protected void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
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

    public boolean isVisible() {
        return vis;
    }

    public void setVisible(Boolean visible) {
        vis = visible;
    }
}
```

Given this change, our PlayerShip class now looks like this;
(explanations below);

```
    public PlayerShip(int x, int y) {
        super(x, y);
        initPlayerShip();
    }

    private void initPlayerShip() {
        missiles = new ArrayList();
        ImageIcon ii = new ImageIcon("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/player1.png");
        iconWidth = ii.getIconWidth();
        iconHeight = ii.getIconHeight();
        image = ii.getImage();

    }


    public void move() {
        x += dx;
        y += dy;


    }

    public ArrayList getMissiles() {
        return missiles;
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

    public void fire() {
        missiles.add(new Missile(x + getImageWidth(), y + getImageHeight() / 3));
    }

}
```


Here we have added an arrayList for our projectile objects.
```
    public ArrayList getMissiles() {
        return missiles;
    }
```

And a method our playerShip can call to generate a projectile;

```
   public void fire() {
        missiles.add(new Missile(x + getImageWidth(), y + getImageHeight() / 3));
    }
```


Next up, lets make our new Missile class.

```
public class Missile extends Sprite {

    private final int BOARD_WIDTH = 590;
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
```

This class will give us a missile constructor, get the image and move the missile.
Notice that in the move method, we are setting the visibility of the missile to false if it goes off screen.


Last but not least, we need to make some changes to our Window class.

```
public class WindowThread extends JPanel implements Runnable {

    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int DELAY = 50;

    private PlayerShip playerShip;
    private Image background;
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
        playerShip = new PlayerShip(100, 300);
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
        doDrawing(g);
    }



    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(playerShip.getImage(), playerShip.getX(), playerShip.getY(), this);

        ArrayList ms = playerShip.getMissiles();

        for (Object m1 : ms) {
            Missile m = (Missile) m1;
            g2d.drawImage(m.getImage(), m.getX(),
                    m.getY(), this);
        }
        Toolkit.getDefaultToolkit().sync();
    }




    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {
            playerShip.move();
            updateMissiles();
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

    private void updateMissiles() {

        ArrayList ms = playerShip.getMissiles();

        for (int i = 0; i < ms.size(); i++) {

            Missile m = (Missile) ms.get(i);

            if (m.isVisible()) {

                m.move();
            } else {

                ms.remove(i);
            }
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

        if (keyboard.keyDown(KeyEvent.VK_SPACE)){
            playerShip.fire();
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
}
```


Our doDrawing() methos has been updated to draw each missile in the arrayList.

```
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(playerShip.getImage(), playerShip.getX(), playerShip.getY(), this);

        ArrayList ms = playerShip.getMissiles();

        for (Object m1 : ms) {
            Missile m = (Missile) m1;
            g2d.drawImage(m.getImage(), m.getX(),
                    m.getY(), this);
        }
        Toolkit.getDefaultToolkit().sync();
    }
```

We update or run method to run updateMissiles().

```
    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {
            playerShip.move();
            updateMissiles();
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


The updateMissiles() method moves our missiles across the screen and removes them from the arrayList when they go off screen.

```
    private void updateMissiles() {

        ArrayList ms = playerShip.getMissiles();

        for (int i = 0; i < ms.size(); i++) {

            Missile m = (Missile) ms.get(i);

            if (m.isVisible()) {

                m.move();
            } else {

                ms.remove(i);
            }
        }
    }
```


Note that we are keeping the keyboard inputs which control the player in our window class. These controls would be
better suited residing in the playerClass, but for the sake of simplicity, we'll leave them where they are for now.

Ok, launch your app and blaze away.
