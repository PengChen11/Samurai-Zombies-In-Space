package com.gameEngine.commands;

import com.character.Player;
import com.character.Zombie;
import com.client.Main;
import com.controller.FXMLDocumentController;
import com.controller.GameSceneControllerNew;
import com.gameEngine.GameEngine;
import com.item.Item;
import com.location.Locations;
import com.sound.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LOOKCommand implements CommandInterface {

    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {
        processLook(gameBuilder, command, instructs);
    }

    private void processLook(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {
        if (command[1] == null || command[1].isBlank() || "around".equalsIgnoreCase(command[1])) {
            GameEngine.GAME_ENGINE.goodCommand = true;
            gameBuilder.append(Player.PLAYER.getCurrentLocation().getDescription()).append("\n");
            if(Player.PLAYER.getCurrentLocation().equals(Locations.EscapeShuttle)){
                gameBuilder.append(Player.PLAYER.getCurrentLocation().getNpc().getDescription());
            }
            appendItemsToDescription(gameBuilder, instructs);
            if (Player.PLAYER.getCurrentLocation().getZombie() != null) {
                if (((Background)(GameSceneControllerNew.getBackground())) != null) {
                    ((Background)(GameSceneControllerNew.getBackground())).pauseMusic();
                    SoundFactory.createSound(SoundType.ROAR).startMusic(GameSceneControllerNew.currentVolume);
                    if(!((Roar)(SoundFactory.createSound(SoundType.ROAR))).getRoar().isPlaying())
                    {
                        ((Background)(GameSceneControllerNew.getBackground())).startMusic(GameSceneControllerNew.currentVolume);
                    }
                }
                processZombieAttack(gameBuilder, instructs);
            }
        } else {
            gameBuilder.append(getLookResult(command[1],instructs));
        }
        // the zombie should attack you if there is a zombie in the room

    }


    private void appendItemsToDescription(StringBuilder description, Map<String, Map<String, List<String>>> instructs) {
        List<Item> items = Player.PLAYER.getCurrentLocation().getItemList();

        if (items.size() > 0) {
            description.append(instructs.get("examine").get("instructions").get(0));
            for (int i = 0; i < items.size(); i++) {
                description.append(" ").append(items.get(i).getName()).append(
                        items.size() > 1 && i != items.size() - 1 ?
                                ", " : "");
            }
            description.append("]\n");
        } else {
            description.append(instructs.get("examine").get("instructions").get(1));
        }
    }

    private void processZombieAttack(StringBuilder gameBuilder, Map<String, Map<String, List<String>>> instructs) {
        Player.PLAYER.takeDamage(1);
        if(Player.PLAYER.getHealth() <= 0){
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/fxml/gameSceneLoss.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Scene scene = new Scene(root);
            Main.getPrimaryStage().setScene(scene);
            Main.getPrimaryStage().show();


        }
        gameBuilder.append(instructs.get("look").get("instructions").get(2)).append(Player.PLAYER.getHealth()).append(instructs.get("look").get("instructions").get(4));
        gameBuilder.append(instructs.get("look").get("instructions").get(3));
    }

    private String getLookResult(String objectToFind, Map<String, Map<String, List<String>>> instructs) {
        String response;
        Item item = Player.PLAYER.getCurrentLocation().containsItem(objectToFind);
        if (item != null) {
            response = item.getDescription();
        } else {
            response = instructs.get("look").get("instructions").get(1) + objectToFind + ".";
        }
        return response + "\n";
    }
}
