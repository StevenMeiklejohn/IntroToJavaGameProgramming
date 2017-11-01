### Lesson 2

### Animation.

In this lesson we'll be discussing animation in the sense moving things around on our screen.
We'll get to animating our sprites a bit later.

There are many ways in which we could achieve this sense of movement in our game. Some, like using the swing timer are quite easy ti implement but not very effective or efficient.
So, we are going to start off on the right foot and implement ours by way of threading. This is the most common strategy for games construction (so far as I can tell), owing to its efficacy and accuracy.

### Thread.

First of all, update this method in the Application class.

```
    private void initUI() {

        add(new Window());

        setResizable(false); //UPDATED
        pack(); //UPDATED

        setTitle("Star");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

```



The setResizable() sets whether the frame can be resized. The pack() method causes this window to be sized to fit the preferred size and layouts of its children.
Note that the order in which these two methods are called is important. (The setResizable() changes the insets of the frame on some platforms; calling this method after the pack() method might lead to incorrect results
Next up, lets's go through the Window class.
Now, this will need some fairly extensive surgery so the finished class is printed below with explanations below that.

```
import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 01/11/2017.
 */
public class Window extends JPanel implements Runnable {


    private final int B_WIDTH = 350;
    private final int B_HEIGHT = 350;
    private final int INITIAL_X = -40;
    private final int INITIAL_Y = -40;
    private final int DELAY = 25;

    private Image star;
    private Thread animator;
    private int x, y;

    public Window() {

        initWindow();
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/player1.png");
        sprite = ii.getImage();
    }

    private void initWindow() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);

        loadImage();

        x = INITIAL_X;
        y = INITIAL_Y;
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

        drawSprite(g);
    }

    private void drawSprite(Graphics g) {

        g.drawImage(star, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    private void cycle() {

        x += 1;
        y += 1;

        if (y > B_HEIGHT) {

            y = INITIAL_Y;
            x = INITIAL_X;
        }
    }

    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {

            cycle();
            repaint();

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
}
```

What on earth does all that do? Nil desperandum, let's take each change in turn.


```
private final int B_WIDTH = 350;
private final int B_HEIGHT = 350;
private final int INITIAL_X = -40;
private final int INITIAL_Y = -40;
private final int DELAY = 25;
```

Five constants are defined. The first two constants are the board width and height.
The third and fourth are the initial coordinates of the sprite.
The last one determines the speed of the animation.

```
private void loadImage() {

    ImageIcon ii = new ImageIcon("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/player1.png");
    star = ii.getImage();
}
```

In the loadImage() method we create an instance of the ImageIcon class.
The getImage() method will return the the Image object from this class.
This object will be drawn on the board.

```
setDoubleBuffered(true);
```

The JPanel component will use a buffer to paint. This means that all drawing will be done in memory first.
Later the off-screen buffer will be copied to the screen. In this simple example, we might not notice any differences.

```
@Override
public void paintComponent(Graphics g) {
    super.paintComponent(g);

    drawSprite(g);
}
```

Custom painting is done in the paintComponent() method. Note that we also call the paintComponent() method of its parent.
The actual painting is delegated to the drawSprite() method.

```
private void drawSprite(Graphics g) {
    g.drawImage(sprite, x, y, this);
    Toolkit.getDefaultToolkit().sync();
}
```

In the drawSprite() method, we draw the image on the window with the usage of the drawImage() method.
The Toolkit.getDefaultToolkit().sync() synchronises the painting on systems that buffer graphics events.
Without this line, the animation might not be jittery.

```
@Override
public void addNotify() {
    super.addNotify();

    animator = new Thread(this);
    animator.start();
}
```

The addNotify() method is called after our JPanel has been added to the JFrame component.
This method is often used for various initialisation tasks.

```
timeDiff = System.currentTimeMillis() - beforeTime;
sleep = DELAY - timeDiff;
```

We want our game to run smoothly, at constant speed. Therefore we compute the system time.
The cycle() and the repaint() methods might take different time at various while cycles.
We calculate the time both methods run and subtract it from the DELAY constant.
This way we want to ensure that each while cycle runs at constant time.
In our case, it is DELAY ms each cycle.



In summary,
- The animation will take place inside a thread.
- The run() method is called only once. This is why why we have a while loop in the method.
- The cycle() and the repaint() methods might take different time at various while cycles. We calculate the time both methods run and subtract it from the DELAY constant. This way we want to ensure that each while cycle runs at constant time.
In our case, it is DELAY ms each cycle.From this method, we call the cycle() and the repaint() methods.












