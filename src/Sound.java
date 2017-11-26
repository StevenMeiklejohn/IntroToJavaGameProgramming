import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.*;
import java.net.URL;

/**
 * Created by user on 24/11/2017.
 */
public class Sound {

//    public static final AudioClip DARK_EXPLOSION = Applet.newAudioClip(Sound.class.getResource("Resources/dark_explosion.wav"));
//    public static final AudioClip MUSIC = Applet.newAudioClip(Sound.class.getResource("/Users/user/Desktop/CX3_4/projects/java/CodeClanGame3/Resources/music.mp3"));
//    public static final AudioClip DAMAGE = Applet.newAudioClip(Sound.class.getResource("Resources/damage.wav"));
//    public static final AudioClip GAMEOVER = Applet.newAudioClip(Sound.class.getResource("Resources/gameover.wav"));
//    public static final AudioClip ENEMYSHOT = Applet.newAudioClip(Sound.class.getResource("Resources/enemyshot.mp3"));
//    public static final AudioClip PLAYERSHOT = Applet.newAudioClip(Sound.class.getResource("Resources/playershot.mp3"));


    public void playMusic(){
        //** add this into your application code as appropriate
//      Open an input stream  to the audio file.

        File file = new File("./Resources/music.wav");
        InputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Create an AudioStream object from the input stream.
        AudioStream as = null;
        try {
            as = new AudioStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Use the static class member "player" from class AudioPlayer to play
        // clip.
        AudioPlayer.player.start(as);
        // Similarly, to stop the audio.
//        AudioPlayer.player.stop(as);
    }





}
