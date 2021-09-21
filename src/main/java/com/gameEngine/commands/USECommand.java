package com.gameEngine.commands;

import com.character.Player;
import com.client.Main;
import com.controller.GameSceneControllerNew;
import com.item.Item;
import com.location.Locations;
import com.sound.Background;
import com.sound.SoundFactory;
import com.sound.SoundType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class USECommand implements CommandInterface{

    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {
        processUse(gameBuilder, command,instructs);
    }

    private void processUse(StringBuilder gameBuilder, String[] command,Map<String, Map<String, List<String>>> instructs) {
        if (command.length == 2) {
            processUseLogic(gameBuilder, command, instructs);
        }
    }

    private void processUseLogic(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {

        if (Player.PLAYER.checkInventoryName(command[1].toLowerCase())){

            boolean shouldRemoveItem = true;

            switch (command[1].toLowerCase()){

                case "health kit":
                    if (Player.PLAYER.getHealth().equals(Player.PLAYER.getMaxHealth())){
                        shouldRemoveItem = false;
                    }
                    gameBuilder.append(healPlayer(instructs));
                    break;
                case "lever":
                    shouldRemoveItem = isShouldRemoveItemShip(gameBuilder, instructs, shouldRemoveItem, command[1].toLowerCase());

                    break;
                case "battery":
                    shouldRemoveItem = isShouldRemoveItem(gameBuilder, instructs, shouldRemoveItem, command[1].toLowerCase());
                    break;
                case "key":
                    shouldRemoveItem = isShouldRemoveItemKey(gameBuilder, instructs, shouldRemoveItem, command[1].toLowerCase());
                    break;
                case "space wrench":
                    shouldRemoveItem = false;
                    gameBuilder.append(instructs.get("use").get("instructions").get(4) + command[1].toLowerCase() + instructs.get("use").get("instructions").get(5) + Player.PLAYER.getCurrentLocation().getName());
                    break;
            }
            if (shouldRemoveItem){
                boolean removedFromInventory = Player.PLAYER.removeInventory(command[1].toLowerCase());
                if (!removedFromInventory){
                    gameBuilder.append("Sorry Dave, something went wrong and you can not use it right now.");
                }
            }
        } else {
            gameBuilder.append(instructs.get("use").get("instructions").get(1));
        }
    }

    private boolean isShouldRemoveItemKey(StringBuilder gameBuilder, Map<String, Map<String, List<String>>> instructs, boolean shouldRemoveItem, String item) {
        if(Player.PLAYER.getCurrentLocation().equals(Locations.RepairWorkshop) && Player.PLAYER.checkInventory(Item.itemsMap.get("key"))){
            Player.PLAYER.addToInventory(Item.itemsMap.get("space wrench"));
            gameBuilder.append(instructs.get("use").get("instructions").get(3));
        }else{
            shouldRemoveItem = false;
            gameBuilder.append(instructs.get("use").get("instructions").get(4) + item + instructs.get("use").get("instructions").get(5) + Player.PLAYER.getCurrentLocation().getName());
        }
        return shouldRemoveItem;
    }

    private boolean isShouldRemoveItemShip(StringBuilder gameBuilder, Map<String, Map<String, List<String>>> instructs, boolean shouldRemoveItem, String item) {
        if(Player.PLAYER.getCurrentLocation().equals(Locations.Ship) && Player.PLAYER.checkInventory(Item.itemsMap.get("space wrench")) && Player.PLAYER.checkInventory(Item.itemsMap.get("lever"))){
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/fxml/gameSceneWin.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Scene scene = new Scene(root);
            Main.getPrimaryStage().setScene(scene);

            Main.getPrimaryStage().show();
            if (((Background)(GameSceneControllerNew.getBackground())) != null) {
                ((Background) (GameSceneControllerNew.getBackground())).getMediaPlayer().stop();
                SoundFactory.createSound(SoundType.WIN).startMusic(0.7);
            }//win game
         }
        else if(Player.PLAYER.getCurrentLocation().equals(Locations.Ship) && !Player.PLAYER.checkInventory(Item.itemsMap.get("space wrench")) && Player.PLAYER.checkInventory(Item.itemsMap.get("lever"))){
            gameBuilder.append(instructs.get("use").get("instructions").get(6));
            shouldRemoveItem = false;
        }
        else{
            shouldRemoveItem = false;
            gameBuilder.append(instructs.get("use").get("instructions").get(4) + item + instructs.get("use").get("instructions").get(5) + Player.PLAYER.getCurrentLocation().getName());
        }
        return shouldRemoveItem;
    }

    private boolean isShouldRemoveItem(StringBuilder gameBuilder, Map<String, Map<String, List<String>>> instructs, boolean shouldRemoveItem, String item) {
        if(Player.PLAYER.getCurrentLocation().equals(Locations.EscapeShuttle) && Player.PLAYER.checkInventoryName("battery")) {
            Player.PLAYER.addToInventory(Item.itemsMap.get("lever"));
            gameBuilder.append(instructs.get("use").get("instructions").get(2));
        }else{
            shouldRemoveItem = false;
            gameBuilder.append(instructs.get("use").get("instructions").get(4) + item + instructs.get("use").get("instructions").get(5) + Player.PLAYER.getCurrentLocation().getName());
        }
        return shouldRemoveItem;
    }


    private String healPlayer(Map<String, Map<String, List<String>>> instructs) {
        String response = "";
        if (Player.PLAYER.getHealth().equals(Player.PLAYER.getMaxHealth())) {
            response = instructs.get("heal").get("instructions").get(0);
        } else {
            Player.PLAYER.addToHealth(5);
            response =
                    instructs.get("heal").get("instructions").get(1) + Player.PLAYER.getHealth() + instructs.get("heal").get("instructions").get(2);
        }
        return response;
    }
}
