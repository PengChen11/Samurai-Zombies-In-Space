package com.sound;

public class SoundFactory {
    private SoundFactory() {

    }

    public static SoundFX createSound(SoundType soundType) {
        SoundFX soundFX = null;
        switch (soundType) {
            case BACKGROUND:
                soundFX = new Background();
                break;
            case ROAR:
                soundFX = new Roar();
                break;
            case PUNCH:
                soundFX = new Punch();
                break;
            case WEAPON:
                soundFX = new Weapon();
                break;
            case TURN:
                soundFX = new Turn();
                break;
            case NICE:
                soundFX = new Nice();
                break;
            case BITE:
                soundFX = new Bite();
        }
        return soundFX;
    }
}
