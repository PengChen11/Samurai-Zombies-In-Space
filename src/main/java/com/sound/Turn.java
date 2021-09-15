package com.sound;

import javafx.scene.media.AudioClip;

import java.io.File;

public class Turn extends SoundFX {
    public AudioClip getTurnClip() {
        return turnClip;
    }

    private AudioClip turnClip;

    public Turn() {
        turnClip = new AudioClip(new File("src/main/resources/sounds/Turn.wav").toURI().toString());
    }

    @Override
    public void startMusic(double volume) {

        getTurnClip().setVolume(volume);
        turnClip.play();
    }
}
