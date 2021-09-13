package com.engine.commands;

import com.character.Player;
import com.character.Zombie;

import com.item.Item;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class INVENTORYCommand implements CommandInterface {
    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, String currentLocation, List<Item> inventory, Zombie zombieNPC, Map<String, Map<String, List<String>>> instructs, Player player, HashMap<String, Item> catalog, JSONParser parser) {
        gameBuilder.append(reviewInventory(player));
    }

    private String reviewInventory(Player player) {
        String returnVal = " Items : [";
        for (Item item : player.getInventory()) {
            returnVal += item.getName() + " (in " + item.getLocation() + ") ";
        }
        return returnVal;
    }

}
