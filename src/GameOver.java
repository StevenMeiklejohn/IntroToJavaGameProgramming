/**
 * Created by user on 05/11/2017.
 */
public class GameOver extends Sprite {



    public GameOver(int x, int y) {
        super(x, y);


        initGameOver();
    }

    private void initGameOver() {

        loadImage("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/pixelPlanetSplashGif2.gif");
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



