package com.sound;

import javafx.scene.media.AudioClip;

import java.io.File;
import java.io.FileReader;

public class Nice extends SoundFX{
    private AudioClip niceClip;

    public Nice(){
        niceClip = new AudioClip(new File("src/main/resources/sounds/nice.wav").toURI().toString());
    }
    @Override
    public void startMusic() {
        niceClip.play();
    }
}
