package com.sound;

import javafx.scene.media.AudioClip;

import java.io.File;

public class Punch implements SoundFX{
    private AudioClip punchClip;

    public Punch(){
        punchClip = new AudioClip(new File("src/main/resources/sounds/Punch Sound Effect - Gaming SFX.wav").toURI().toString());
    }
    @Override
    public void startMusic() {
       punchClip.play();
    }

    @Override
    public void controlVolume(double volume) {
       punchClip.setVolume(volume);
    }
}
