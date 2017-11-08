### Lesson 5.

### Enemies

Given we have just created projectiles in our game, you already have guessed how to add enemies.
The process is very similar, in that, we create an enemy class which inherits from Sprite (thus giving us much of the required
functionality), then make some updates to our main class (drawing, etc)
So, lets make an enemy class, I have gone with 'Alien' for this example.

```

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

        x -= 2;
    }
}
```

Next, we must update the main class to utilise our new alien class, in the following ways;

>>MainActivity(WindowThread)

We need to add a new arrayList which will contain our instances of 'Alien'
I've also added a hash of co-ordinates that will act as the starting points for our first 'wave'
While this is not very dynamic, it would be better to create instances of alien semi-randomly to get the player on his/her toes
But for demonstration purposes, this will suffice.



```
    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int DELAY = 50;

    private PlayerShip playerShip;
    private Thread animator;
    private ArrayList aliens;
    private ArrayList explosions;
    private boolean inGame;
    private boolean inTitle;
    private boolean gameOver;
    KeyboardInput keyboard = new KeyboardInput();
    private final int[][] pos = {
            {2380, 29}, {2500, 59}, {1380, 89},
            {780, 109}, {580, 139}, {680, 239},
            {790, 259}, {760, 50}, {790, 150},
            {980, 209}, {560, 45}, {510, 70},
            {930, 159}, {590, 80}, {530, 60},
            {940, 59}, {990, 30}, {920, 200},
            {900, 259}, {660, 50}, {540, 90},
            {810, 220}, {860, 20}, {740, 180},
            {820, 128}, {490, 170}, {700, 30}
    };
```


Initialise the aliens arraylist and populate it utilising the hash above.

```
   private void initWindow() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);
        addKeyListener(keyboard);
        setFocusable(true);
        inTitle = true;
        inGame = false;
        initAliens();
        initExplosions();
        playerShip = new PlayerShip(100, 300);
    }

    public void initAliens() {
        aliens = new ArrayList<>();

        for (int[] p : pos) {
            aliens.add(new Alien(p[0], p[1]));
        }
    }
```

Our doDrawing method now contains instructions to loop through the aliens arrayList and draw each one.
```
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        if(playerShip.isVisible()) {
            g2d.drawImage(playerShip.getImage(), playerShip.getX(), playerShip.getY(), this);
        }

        ArrayList ms = playerShip.getMissiles();

        for (Object m1 : ms) {
            Missile m = (Missile) m1;
            g2d.drawImage(m.getImage(), m.getX(),
                    m.getY(), this);
        }

        for (Object a1 : aliens) {
            Alien a = (Alien) a1;
            g2d.drawImage(a.getImage(), a.getX(),
                    a.getY(), this);
        }

        for (Object ex : explosions) {
            Explosion explosion = (Explosion) ex;
            g2d.drawImage(explosion.getImage(), explosion.getX(),
                    explosion.getY(), this);
        }

        g.setColor(Color.WHITE);
        g.drawString("Aliens left: " + aliens.size(), 5, 15);
        Toolkit.getDefaultToolkit().sync();
    }
```

The while loop in our run method is updated to include aliens.

```
        while (inGame) {
            updatePlayerShip();
            updateMissiles();
            updateAliens();
            updateExplosions();
            keyboard.poll();
            processInput();
            repaint();
```

Next, we add an update method for the aliens.

```
    private void updateAliens() {

        if (aliens.isEmpty()) {

            inGame = false;
            gameOver = true;
            return;
        }

        for (int i = 0; i < aliens.size(); i++) {
            Alien a = (Alien) aliens.get(i);

            if (a.isVisible()) {
                a.move();
            } else {
                aliens.remove(i);
            }
        }
    }
```


In the next lesson, we will implement explosions which will be displayed when an enemy is destroyed.
To prepare for this, make a new explosion class.


```
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

        loadImage("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/explosion3.png");
        getImageDimensions();
        timeInitialised = System.currentTimeMillis();
    }

    public boolean checkForRemoval(){
        long newTime, timeDiff, sleep;

        newTime = System.currentTimeMillis();
        timeDiff = newTime - timeInitialised;


        if (timeDiff > 200) {
            return true;
        } else {
            return false;
        }
    }

}
```

