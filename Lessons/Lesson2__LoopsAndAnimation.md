### Lesson 2

### Animation.

In this lesson we'll be discussing animation in the sense of moving things around on our screen.
We'll get to animating our sprites a bit later.

There are many ways in which we could achieve this sense of movement in our game.
Each option varies in ease of implementation and efficiency. We will start with the most basic example and work our way up.


### Utility Timer Example

Our main class stays basically the same, but let's create a new up and update our application configuration to use this class
as the entry point into the game.

```
   public SwingTimerExample() {

        initUI();
    }

    private void initUI() {

        add(new Window());

        setResizable(false);
        pack();

        setTitle("Swing Timer Example");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame ex = new SwingTimerExample();
                ex.setVisible(true);
            }
        });
    }
}
```

The one small update is this;

```
setResizable(false);
pack();

```

The setResizable() sets whether the frame can be resized.
The pack() method causes this window to be sized to fit the preferred size and layouts of its children.
Note that the order in which these two methods are called is important.
(The setResizable() changes the insets of the frame on some platforms; calling this method after the pack() method might lead to incorrect results


Our window class will contain most of the changes.
Here's the file, we'll go through each addition below.

```
public class Window extends JPanel {

    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int INITIAL_X = 40;
    private final int INITIAL_Y = 40;
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 25;

    private Image player;
    private Timer timer;
    private int x, y;

    public Window() {
        initWindow();
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/player1.png");
        player = ii.getImage();
    }

    private void initWindow() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);

        loadImage();

        x = INITIAL_X;
        y = INITIAL_Y;

        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawPlayer(g);
    }

    private void drawPlayer(Graphics g) {

        g.drawImage(player, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
            x += 1;
            y += 1;

            if (y > B_HEIGHT) {
                y = INITIAL_Y;
                x = INITIAL_X;
            }
            repaint();
        }
    }


}
```

Ok, in the Window class we are going to move our player from the top left to the bottom right.
So we create 6 constants.
The size of the board, player starting position and player end position.
The initial delay and the period interval

```
    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int INITIAL_X = 40;
    private final int INITIAL_Y = 40;
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 25;
```

In the loadImage() method we create an instance of the ImageIcon class. The image is located in the project directory.
The getImage() method will return the the Image object from this class. This object will be drawn on the window.

```
private void loadImage() {

    ImageIcon ii = new ImageIcon("player1.png");
    star = ii.getImage();
}
```

The JPanel component will use a buffer to paint. This means that all drawing will be done in memory first.
Later the off-screen buffer will be copied to the screen. In this simple example, we might not notice any differences.

```
setDoubleBuffered(true);
```

Next we create an instance of the utility timer.
We can use the (fairly self explanatory) scheduleAtFixedIntervals method.
The arguements are;
Thing we want to do at regular intervals.
The initial delay.
Length of time bewteen doing what we want it to do.

```
timer = new Timer();
timer.scheduleAtFixedRate(new ScheduleTask(),
       INITIAL_DELAY, PERIOD_INTERVAL);
```


This is our Schedule Task method, I.E. the instructions to execute at periodic intervals.
We are overriding the run method associated with the Jframe to increment oue sprites position, check that it's position does not exceed the limits of the frame.
Repaint the frame. Repaint() is a Jframe method.

```
private class ScheduleTask extends TimerTask {
   @Override
   public void run() {
      x += 1;
      y += 1;

      if (y > B_HEIGHT) {
          y = INITIAL_Y;
          x = INITIAL_X;
          }
      repaint();
   }
 ```

When the Jframe calls repaint() it actually calls a number of methods.
We are going to override paintComponent(). We call the super to deal with our graphics object, then we add our own call (drawPlayer())

```
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawPlayer(g);
    }
```


In the drawPlayer() method, we draw the image on the window with the usage of the drawImage() method.
The Toolkit.getDefaultToolkit().sync() synchronises the painting on systems that buffer graphics events.
Without this line, the animation might not be smooth on Linux.

```
    private void drawPlayer(Graphics g) {

        g.drawImage(player, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }
```


If you run your app, you should see a ship decending across the screen.
Nice eh?


In summary....
- Timer - Calls actionPerformed (frequency of call is in DELAY)
- actionPerformed - moves player position.
  Calls repaint()
- Redraws the player in new position.



