package com.gameEngine.commands;

import com.character.Player;

import com.client.Main;
import com.controller.FXMLDocumentController;
import com.controller.GameSceneControllerNew;

import com.item.Item;
import com.location.Locations;
import com.sound.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FIGHTCommand implements CommandInterface{

    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs){

        String currentLocKey = Player.PLAYER.getCurrentLocation().getName();
        if(currentLocKey != null &&
                Locations.getEnumMap().get(currentLocKey).getZombie() != null &&
                (command[1] == null|| command[1].equalsIgnoreCase("zombie"))&&
                Locations.getEnumMap().get(currentLocKey).getZombie().getHealth() > 0){

            gameBuilder.append(instructs.get("fight").get("instructions").get(0) + "\n");
            while(Player.PLAYER.getHealth() > 0 && Locations.getEnumMap().get(currentLocKey).getZombie().getHealth() > 0 ){
                Locations.getEnumMap().get(currentLocKey).getZombie().takeDamage(Player.PLAYER.attack());
                Player.PLAYER.takeDamage(Locations.getEnumMap().get(currentLocKey).getZombie().attack());
                if(((Background)(GameSceneControllerNew.getBackground())) != null){
                    ((Background)(GameSceneControllerNew.getBackground())).pauseMusic();
                    SoundFactory.createSound(SoundType.PUNCH).startMusic(GameSceneControllerNew.currentVolume);
                    if(!((Punch)(SoundFactory.createSound(SoundType.PUNCH))).getPunchClip().isPlaying())
                    {
                        ((Background)(GameSceneControllerNew.getBackground())).startMusic(GameSceneControllerNew.currentVolume);
                    }
                }

                gameBuilder.append(instructs.get("fight").get("instructions").get(4) + Player.PLAYER.getHealth()+ "\n");
                gameBuilder.append(instructs.get("fight").get("instructions").get(5) + Locations.getEnumMap().get(currentLocKey).getZombie().getHealth()+ "\n");
            }
            if(Player.PLAYER.getHealth() <= 0){
                gameBuilder.append(instructs.get("fight").get("instructions").get(7) );
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/fxml/gameSceneLoss.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Scene scene = new Scene(root);
                Main.getPrimaryStage().setScene(scene);
                Main.getPrimaryStage().show();

            }else{
                Player.PLAYER.getCurrentLocation().setZombie(null);
                gameBuilder.append(instructs.get("fight").get("instructions").get(6));
                if(Player.PLAYER.getCurrentLocation().equals(Locations.LandingDock) && !Player.PLAYER.checkInventory(Item.itemsMap.get("key"))){
                    Player.PLAYER.addToInventory(Item.itemsMap.get("key"));
                    gameBuilder.append(instructs.get("fight").get("instructions").get(10));
                }
            }
        }else{
           gameBuilder.append(instructs.get("fight").get("instructions").get(9));
        }
    }
}
