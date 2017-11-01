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

        add(new Board());

        setResizable(false); //UPDATED
        pack(); //UPDATED

        setTitle("Star");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

```



The setResizable() sets whether the frame can be resized. The pack() method causes this window to be sized to fit the preferred size and layouts of its children.
Note that the order in which these two methods are called is important. (The setResizable() changes the insets of the frame on some platforms; calling this method after the pack() method might lead to incorrect results










