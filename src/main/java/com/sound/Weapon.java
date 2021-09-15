package com.sound;

import javafx.scene.media.AudioClip;

import java.io.File;

public class Weapon extends SoundFX{
    public AudioClip getWeaponClip() {
        return weaponClip;
    }

    private AudioClip weaponClip;
    Weapon(){
        weaponClip=new AudioClip(new File("src/main/resources/sounds/punch sound effect 1.mp3").toURI().toString());
    }
    @Override
    public void startMusic(double volume) {
        weaponClip.setVolume(volume);
        weaponClip.play();
    }

}
