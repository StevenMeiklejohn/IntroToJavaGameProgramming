### Lesson 7

### Game Over Screen


To make our game look a bit more like a game we could add a game over message and a splash screen for when the app starts.
We could do this by drawing text to the screen, but I thought it might be cool to go one further.
We will make 'press play' and 'gameover' screens which features a full screen gif and text.

To begin, lets make a Game Over class which will inherit from Sprite.

```
public class GameOver extends Sprite {



    public GameOver(int x, int y) {
        super(x, y);


        initGameOver();
    }

    private void initGameOver() {

        loadImage("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/gameover2.gif");
    }

   @Override
    public int getImageWidth(){
        return 800;
   }

    @Override
    public int getImageHeight(){
        return 600;
    }
    @Override
    protected void getImageDimensions() {

        this.width = 800;
        this.height = 600;
    }

    }
```


All pretty self explanatory, our splash screen is basically the same (this could be refactored)

```
public class SplashScreen extends Sprite {



    public SplashScreen(int x, int y) {
        super(x, y);


        initSplashScreen();
    }

    private void initSplashScreen() {

        loadImage("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/spacebastards.gif");
    }

    @Override
    public int getImageWidth(){
        return 800;
    }

    @Override
    public int getImageHeight(){
        return 600;
    }
    @Override
    protected void getImageDimensions() {

        this.width = 800;
        this.height = 600;
    }
}
```

So, in our main class, we need to add a few booleans to track the status of the game


```
    private boolean inGame;
    private boolean inTitle;
    private boolean gameOver;
```

When we initialise the game, we set our inTitle boolean to true (we are on the welcome screen) and inGame to false (we are waiting to begin the game).

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
```

Next we're going to override the paintComponent method which is run automatically.
So, if we are in the game loop, our draw method is doDrawing() (as before)
We will make another two draw methods to handle inTitle.


```
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            doDrawing(g);
        }
        if(inTitle){
            drawGameTitle(g);
        }
        if(gameOver){
            drawGameOver(g);
        }
//        if(!inGame && !inTitle){
//            drawGameOver(g);
//        }

    }
```


Our new draw methods create an instance of the relevant class and draws it to the screen in the usual way.
So when our state changes (ingame/gameover) the corresponding draw method is called, thus creating different 'screens'.

```
    private void drawGameOver(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        GameOver gameOver = new GameOver(0, 0);
        g2d.drawImage(gameOver.getImage(), 0, 0, this);
    }

    private void drawGameTitle(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        SplashScreen title = new SplashScreen(0, 0);
        g2d.drawImage(title.getImage(), 0, 0, this);
    }
```

Finally, we update our game loop to check state. Doing this means we can apply the keyboard listener to whichever state is currently active.
This will allow us to ask the user to press a key (in this case 'P') to start/re-start the game.

```
        while(inTitle){
            keyboard.poll();
            processInput();
        }

        while(!inGame && !inTitle){
            keyboard.poll();
            processInput();
        }

        while (inGame) {
            updatePlayerShip();
            updateMissiles();
            updateAliens();
            updateExplosions();

            checkCollisions();
            keyboard.poll();
            processInput();
            repaint();
```

We are checking P is pressed all the time, but we are unlikely to press it during the game, it will suffice for the moment.

Ok, starting to look a bit like a game now right?

