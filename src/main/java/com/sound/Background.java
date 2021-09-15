package com.sound;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.util.Duration;

import java.io.File;


public class Background extends SoundFX {
    private MediaPlayer mediaPlayer;
    private Media media;

    public Background() {
        media = new Media(new File("src/main/resources/sounds/Sunrust Zombie Survival Last Human Music.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    @Override
    public void startMusic(double volume) {
        mediaPlayer.setVolume(volume);
        //set the loop for the media player
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();
       // mediaPlayer.setAutoPlay(true);
    }
    public MediaPlayer getMediaPlayer(){
        return this.mediaPlayer;
    }


    public void controlVolume(double volume) {
      mediaPlayer.setVolume(volume);
    }

    public  void pauseMusic(){
        mediaPlayer.stop();
    }


}
