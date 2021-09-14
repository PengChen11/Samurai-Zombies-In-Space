package com.sound;

import javafx.scene.media.AudioClip;

import java.io.File;

public class Turn extends  SoundFX{
    private AudioClip turnClip;
    public Turn(){
        turnClip = new AudioClip(new File("src/main/resources/sounds/Turn.wav").toURI().toString());
    }
    @Override
    public void startMusic() {
        turnClip.play();
    }
}
