package com.sound;

import javafx.scene.media.AudioClip;

import java.io.File;


public class Punch extends SoundFX{

    private AudioClip punchClip;

    public Punch(){
        punchClip = new AudioClip(new File("src/main/resources/sounds/Punch - Sound Effect.wav").toURI().toString());
    }
    @Override
    public void startMusic() {
       punchClip.play();
    }


}
