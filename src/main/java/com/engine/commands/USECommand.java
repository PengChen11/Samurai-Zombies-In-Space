package com.engine.commands;

import com.character.Player;
import com.character.Zombie;

import com.item.Item;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class USECommand implements CommandInterface {
    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, String currentLocation, List<Item> inventory, Zombie zombieNPC, Map<String, Map<String, List<String>>> instructs, Player player, HashMap<String, Item> catalog, JSONParser parser) {
        processUse(gameBuilder, command,player,instructs);
    } //This method should process the 'use' command with valid items
    private void processUse(StringBuilder gameBuilder, String[] command,Player player, Map<String, Map<String, List<String>>> instructs) {
        if (command.length == 2) {
            processUseLogic(gameBuilder, command,player,instructs);
        }
    }    //This is the logic for the 'use' command
    private void processUseLogic(StringBuilder gameBuilder, String[] command, Player player, Map<String, Map<String, List<String>>> instructs) {
        if ("health kit".equalsIgnoreCase(command[1]) && player.checkInventoryName("health " +
                "kit")) {
            gameBuilder.append(healPlayer(player,instructs));
        }
        else if ("lever".equalsIgnoreCase(command[1]) && player.checkInventoryName(
                "lever")){
            gameBuilder.append(instructs.get("use").get("instructions").get(0));
        } else {
            gameBuilder.append(instructs.get("use").get("instructions").get(1));
        }
    }
    private String healPlayer(Player player, Map<String, Map<String, List<String>>> instructs) {
        String response = "";
        StringBuilder builder = new StringBuilder();
        if (player.getHealth() >= 30) {
            response = instructs.get("heal").get("instructions").get(0);
        } else {
            player.setHealth(player.getHealth() + 5);
            response = instructs.get("heal").get("instructions").get(1) + player.getHealth() + instructs.get("heal").get("instructions").get(2);
        }
        return response;
    }
}
