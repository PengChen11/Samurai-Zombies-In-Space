package com.engine.commands;

import com.character.Player;
import com.character.Zombie;

import com.item.Item;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DROPCommand implements CommandInterface {

    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, String currentLocation, List<Item> inventory, Zombie zombieNPC, Map<String, Map<String, List<String>>> instructs, Player player, HashMap<String, Item> catalog, JSONParser parser) {
        gameBuilder.append(dropItem(command[1] , player,instructs,currentLocation,catalog));
    }

    private String dropItem(String playerItem, Player player, Map<String, Map<String, List<String>>> instructs, String currentLocation, HashMap<String, Item> catalog) {
        if (!player.checkInventoryName(playerItem)) {
            return instructs.get("drop").get("instructions").get(0);
        }
        //inventory.removeIf(item -> item.getName().equals(playerItem));
        catalog.replace(playerItem, new Item(playerItem, currentLocation)); //relocate the item
        player.removeInventory(playerItem);

        List<Item> inventory = player.getInventory(); // refresh inventory

        // need to replace last item in list with empty item. removal of last item results in item being displayed in inventory container.
        if (inventory.size() <= 1) {
            Item emptyItem = new Item("", currentLocation);
            inventory.add(emptyItem);
        }

        return instructs.get("drop").get("instructions").get(1) + playerItem + instructs.get("drop").get("instructions").get(2) + currentLocation;
    }
}
