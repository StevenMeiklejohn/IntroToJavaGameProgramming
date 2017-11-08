### Lesson 6

### Collision Detection.


There are many diverse ways of implementing collision detection.
Our strategy will be pretty simple, in that we will use the dimensions of the sprite image as our bounds.
The advantage of this is ease of implementation.
The disadvantage is a drop in accuracy. Our sprites do not cover the entire size of the image, which means that close inspection
would show that a collision occurs slightly further away that would be ideal. We can fix this, but for now lets stick with the basics.
Implementing this strategy is fairly simple.

Basically, all we need is the following method and a call to run it in our game loop.

```
    public void checkCollisions() {


        Rectangle r3 = playerShip.getBounds();

        for (int i = 0; i < aliens.size(); i++) {
            Alien a = (Alien) aliens.get(i);
            Rectangle r2 = a.getBounds();

            if (r3.intersects(r2)) {
                playerShip.setVisible(false);
                a.setVisible(false);
                explosions.add(new Explosion(a.getX(), a.getY()));
                explosions.add(new Explosion(playerShip.getX(), playerShip.getY()));
                gameOver = true;
            }
        }


        ArrayList<Missile> ms = playerShip.getMissiles();

        for (Missile m : ms) {

            Rectangle r1 = m.getBounds();

            for (int i = 0; i < aliens.size(); i++) {
                Alien a = (Alien) aliens.get(i);
                Rectangle r2 = a.getBounds();
                if (r1.intersects(r2)) {
                    m.setVisible(false);
                    a.setVisible(false);
                    explosions.add(new Explosion(a.getX(), a.getY()));
                }
            }
        }
    }
```

First, we check for collisions between the player and the aliens.
We obtain the boundary of the sprite like so;

```
            Alien a = (Alien) aliens.get(i);
            Rectangle r2 = a.getBounds();
```

Next we have a simple if statement that checks if the rectangle object applied to player
'intersects' with the rectangle object applied to the alien.
If they do, we remove the player and alien (using setVisible) and add an instance of explosion in the relevant positions.
Our updateExplosions method checks toBeRemoved() on every game loop, so our explosion sprite dissapears after the defined time.


