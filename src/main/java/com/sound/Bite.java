package com.sound;

import javafx.scene.media.AudioClip;

import java.io.File;

class Bite extends SoundFX{
    private AudioClip biteClip;

    public Bite(){
        biteClip = new AudioClip(new File("src/main/resources/sounds/Bite.wav").toURI().toString());
    }
    @Override
    public void startMusic() {
        biteClip.play();
    }
}
