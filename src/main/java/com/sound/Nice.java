package com.sound;

import javafx.scene.media.AudioClip;

import java.io.File;
import java.io.FileReader;

public class Nice extends SoundFX{
    public AudioClip getNiceClip() {
        return niceClip;
    }

    private AudioClip niceClip;

    public Nice(){
        niceClip = new AudioClip(new File("src/main/resources/sounds/Nice Sound Effect (download).mp3").toURI().toString());
    }
    @Override
    public void startMusic() {
        niceClip.play();
    }
}
