package com.sound;

import javafx.scene.media.AudioClip;

import java.io.File;

class Win extends SoundFX{
    public AudioClip getWinClip() {
        return winClip;
    }

    private AudioClip winClip;

    Win(){
        winClip=new AudioClip(new File("sounds/win.mp3").toURI().toString());
    }
    @Override
    public void startMusic(double volume) {
        winClip.setVolume(volume);
        winClip.play();
    }

}
