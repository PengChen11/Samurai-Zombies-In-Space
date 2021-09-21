package com.sound;

import javafx.scene.media.AudioClip;

import java.io.File;

public class Bite extends SoundFX {
    public AudioClip getBiteClip() {
        return biteClip;
    }

    private AudioClip biteClip;

    public Bite() {
        biteClip = new AudioClip(new File("sounds/Zombie Flesh Bite (Dawn of The Dead Inspired) - Sound Effect [HD].mp3").toURI().toString());
    }

    @Override
    public void startMusic(double volume) {
        getBiteClip().setVolume(volume);
        biteClip.play();
    }
}
