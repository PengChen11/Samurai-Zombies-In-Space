package com.sound;

import javafx.scene.media.AudioClip;

import java.io.File;


public class Punch extends SoundFX{

    public AudioClip getPunchClip() {
        return punchClip;
    }

    private AudioClip punchClip;

    public Punch(){
        punchClip = new AudioClip(new File("sounds/Punch - Sound Effect.mp3").toURI().toString());
    }
    @Override
    public void startMusic(double volume) {
        getPunchClip().setVolume(volume);
                punchClip.play();
    }


}
