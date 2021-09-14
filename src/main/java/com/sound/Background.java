package com.sound;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

import java.io.File;


public class Background extends SoundFX {
    private MediaPlayer mediaPlayer;
    private Media media;

    public Background() {
        media = new Media(new File("src/main/resources/sounds/Sunrust Zombie Survival Last Human Music.wav").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    @Override
    public void startMusic() {
        mediaPlayer.setVolume(0.2);
        mediaPlayer.play();
    }
    public MediaPlayer getMediaPlayer(){
        return this.mediaPlayer;
    }


    public void controlVolume(double volume) {
      mediaPlayer.setVolume(volume);
    }

}
