package com.sound;

import javafx.scene.media.AudioClip;

import java.io.File;

public class Roar implements SoundFX{
    private AudioClip roar;
    public Roar()
    {
        roar=new AudioClip(new File("src/main/resources/sounds/Male Zombie Roar Sound Effect No Copyright Free Sound Effects.wav").toURI().toString());
    }
    @Override
    public void startMusic() {
        roar.play();
    }

    @Override
    public void controlVolume(double volume) {

    }
}
