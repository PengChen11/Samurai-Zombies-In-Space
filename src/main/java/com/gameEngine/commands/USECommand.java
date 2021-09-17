package com.gameEngine.commands;

import com.character.Player;

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
                    gameBuilder.append(instructs.get("use").get("instructions").get(0));
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
