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
//    public static final AudioClip MUSIC = Applet.newAudioClip(Sound.class.getResource("/Resources/music.mp3"));
//    public static final AudioClip CRITICAL_DAMAGE = Applet.newAudioClip(Sound.class.getResource("Resources/damage.wav"));
//    public static final AudioClip GAMEOVER = Applet.newAudioClip(Sound.class.getResource("Resources/gameover.wav"));
//    public static final AudioClip PLAYERSHOT = Applet.newAudioClip(Sound.class.getResource("Resources/laser_rifle.wav"));
    public String audio;
    public File file;
    public AudioStream as;

    public Sound(String audio){
        this.audio = audio;
        if(audio == "music"){
            this.file = new File("./Resources/music.wav");
        }
        if(audio == "explosion"){
            this.file = new File("./Resources/dark_explosion.wav");
        }
        if(audio == "critical_damage"){
            this.file = new File("./Resources/damage.wav");
        }
        if(audio == "player_shot"){
            this.file = new File("./Resources/laser_weapon.wav");
        }
        if(audio == "game_over"){
            this.file = new File("./Resources/gameover.wav");
        }
    }

    public void play(){
        InputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Create an AudioStream object from the input stream.
        this.as = null;
        try {
            as = new AudioStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AudioPlayer.player.start(as);
    }


//    public void setup(){
//
////        File file = new File("./Resources/music.wav");
//        InputStream in = null;
//        try {
//            in = new FileInputStream(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        // Create an AudioStream object from the input stream.
//        this.as = null;
//        try {
//            as = new AudioStream(in);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public void start(){
        AudioPlayer.player.start(as);
    }

    public void stop(){
        AudioPlayer.player.stop(as);
    }





}
