package com.sound;

import javafx.scene.media.AudioClip;

import java.io.File;


public class Roar extends SoundFX{

    public AudioClip getRoar() {
        return roar;
    }

    private AudioClip roar;
    public Roar()
    {
        roar=new AudioClip(new File("sounds/Male Zombie Roar Sound Effect No Copyright Free Sound Effects.mp3").toURI().toString());
    }
    @Override
    public void startMusic(double volume) {
        getRoar().setVolume(volume);
        roar.play();
    }


}
