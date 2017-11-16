### Lesson 8.

### Adding A Scrolling Background.


Lets add a very simple example of a scrolling background.
To achieve this we are going to draw our background image twice.
Let's assume out image is 800 x 600 (the current size of our frame). The first image will be
drawn at (0,0) and the second image drawn at (800, 0).
Our update method will move our image to the left at a speed we specify, then when the first image reaches -800, we change its position to 800.
So,
