package com.gameEngine.commands;

import com.character.Player;
import com.controller.GameSceneControllerNew;
import com.item.Item;
import com.sound.*;
import javafx.scene.media.MediaPlayer;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class GETCommand implements CommandInterface{

    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {
        processGet(gameBuilder, command, instructs);
    }

    private void processGet(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {
        if (command.length > 1) {
            gameBuilder.append(pickUpItem(command[1], instructs));


        } else {
            gameBuilder.append(instructs.get("get").get("instructions").get(0));
        }
    }

    private String pickUpItem(String thing,Map<String, Map<String, List<String>>> instructs ) {
        Item item = Player.PLAYER.getCurrentLocation().containsItem(thing);
        if (item != null) {
            Player.PLAYER.addToInventory(item);
            Player.PLAYER.getCurrentLocation().removeItem(item);
            ((Background)(GameSceneControllerNew.getBackground())).pauseMusic();
            SoundFactory.createSound(SoundType.NICE).startMusic(GameSceneControllerNew.currentVolume);
            if(!((Nice)(SoundFactory.createSound(SoundType.NICE))).getNiceClip().isPlaying()){
                ((Background)(GameSceneControllerNew.getBackground())).startMusic(GameSceneControllerNew.currentVolume);
            }

            return instructs.get("pick").get("instructions").get(0) + thing + instructs.get("pick").get("instructions").get(1);
        }

        return thing + instructs.get("pick").get("instructions").get(3);
    }
}
