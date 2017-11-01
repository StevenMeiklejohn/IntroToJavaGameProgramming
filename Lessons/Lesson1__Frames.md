### Intro To Game Programming.



If you enjoy video games (who doesn't?), more specifically of the retro 8/16 bit variety, attempting to build your own can be a great (and fun) way of obtaining a deeper understanding of Java. Given Java's similarity to C it makes it our best option for delving into this world.
At the end of the following lesson we will have a playable game. It won't melt anyone's mind with astonishing graphics and sound, but as any gamer worth his/her salt knows - gameplay/mechanics is everything.
This basic framework can then be used to build other things. You can take it as far as you want to go.
*- Warning. This can be addictive. -*

### Framework

There are a number of crucial components which every game requires.
Specifics vary, but generally speak we will need the following (though not necessarily in that order);

1. Frame.
2. Set frames Per second
3. Game Loop.
 - Get input.
 - Update.
 - Draw graphics.


### Frame.
 1. For this tutorial we will be using a java library called 'swing'. This will allow us to create an instance of a JFrame and its accompanying drawable surface (canvas). This tutorial was designed to be underthe android environment. This was a conscious decision, as developing for Android adds another layer of complexity when drawing graphics to the screen (we'll look at that later).
 Trying to create and access a drawable frame without the swing library in Java is quite difficult and would detract from the purpose of this lesson, which is game programming. Consequently we will allow swing to abstract much of the processes involved in making the frame away from us.
 So, lets start by making our game class and constructing a frame.

 Let's begin by creating a Window class to represent our game screen.

 ```
 public class Window extends JPanel {


     public Window() {
         initWindow();
     }

     private void initWindow() {
         setPreferredSize(new Dimension(w, h));
     }
 ```

 All we have done here is stipulate that our class inherits from Jpanel, (which is a bit a surface onto which we draw or add images) and set the preffered size of our screen.
 The screen size can be modified by the user too( we'll do that in a bit):


 ### Main Class

 Ok, we have a surface on which our game will play out.
 Let's create the entry point for our game (i.e. a class which contains the main() method).

 Make a new class called 'Application'

 ```
 public class Application extends JFrame {

     public Application() {

         initUI();
     }

     private void initUI() {

         add(new Window());

         setSize(800, 500);

         setTitle("Codeclan Game3");
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setLocationRelativeTo(null);
     }

     public static void main(String[] args) {

         EventQueue.invokeLater(new Runnable() {
             @Override
             public void run() {
                 Application ex = new Application();
                 ex.setVisible(true);
             }
         });
     }
 }
 ```


We we run our game, the main method will be called.
The main method creates a new thread with the neccessary run method. The EventQueue part is concerned with the sycronisation of multiple threads.
We will look at this in more detail later.
The run method creates an instance of our application and makes the resulting window visible.
As you can see from the Application constructor, the first thing it does when created is run initUI.

initUI adds a new instance of our window class,
- sets the size of the new window,
- defines what the title on the window will be,
- specifies an action to take when the user closes the window,
- then positions it.


### Adding an image

Its possible to draw shapes and lines directly to our window, but since we are building a game, we are much more likely to be using sprites.
Our sprites will consist of an imported image. To that end, lets create a sprite class and display in our game window.

Before we make a sprite class, we must make a couple of additions to the Window class first.


```
public class Window extends JPanel {

    private Image playerImage; //UPDATED

    public Window() {
        initWindow();
    }

    private void initWindow() {
        loadImage(); // UPDATED
        int w = playerImage.getWidth(this); //UPDATED
        int h =  playerImage.getHeight(this); //UPDATED
        setPreferredSize(new Dimension(w, h));
    }

    private void loadImage() { //UPDATED
        ImageIcon ii = new ImageIcon("Resources/player1.png");
        playerImage = ii.getImage();
    }

    @Override
    public void paintComponent(Graphics g) { //UPDATED
        g.drawImage(playerImage, 0, 0, null);
    }
}
```

What just happened? Well...
- We declared an instance of java class Image.
- In the initWindow method we call loadImage and set the size of the image to match the size of the .png/.jpg/etc file that we are importing.
- The loadImage method imports our picture as type ImageIcon, then assigns it to variable playerImage.
- Finally, we override the standard JPanel paint method with our own, in which we draw our playerImage to the screen.

Righto, all we need now is our Image class.
Go ahead and make a new class called ImageExample or something similar.

```
public class ImageExample extends JFrame {

    public ImageExample() {

        initUI();
    }

    private void initUI() {
        add(new Window());
        pack();
        setTitle("playerSprite");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                ImageExample ex = new ImageExample();
                ex.setVisible(true);
            }
        });
    }
}
```


This class works in a broadly similar way to the game class, in that;
- It has a main method which creates a thread with an instance of itself.
- Has a constructor which calls initUI().
- InitUI creates another instance of the window class and adds it to the application context (i.e adds to it our main window)
- uses pack() to bundle both windows together (the sprite will be on top since we are adding it to the main window and not vice versa.
- Sets new window title etc.



If you now run your application, you would a window with a white background and a small spaceship sprite (assuming you are using the assets I generated)
Pretty cool right? Think of the possibilities......






















