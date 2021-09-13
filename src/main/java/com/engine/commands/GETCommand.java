package com.engine.commands;

import com.character.NPC;
import com.character.Player;
import com.character.Zombie;

import com.item.Item;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//REWIRTE
public class GETCommand implements CommandInterface {
    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, String currentLocation, List<Item> inventory, Zombie zombieNPC, Map<String, Map<String, List<String>>> instructs, Player player, HashMap<String, Item> catalog, JSONParser parser) {
        processGet(gameBuilder, command,currentLocation, instructs,player,catalog);
    }
        //This method should process the "get" command
    private void processGet(StringBuilder gameBuilder, String[] command,String currentLocation,  Map<String, Map<String, List<String>>> instructs, Player player, HashMap<String, Item> catalog ) {
        if (command.length > 1) {
            gameBuilder.append(pickUpItem(command[1], currentLocation, instructs,player,catalog));
        } else {
            gameBuilder.append(instructs.get("get").get("instructions").get(0));
        }
    }
        private String pickUpItem(String thing,String currentLocation, Map<String, Map<String, List<String>>> instructs, Player player,  HashMap<String, Item> catalog ) {
        Item item = catalog.get(thing);
        if (item != null && item.getLocation().equals(currentLocation)) {
            item.setLocation("player");
            player.addToInventory(item);
            catalog.replace(thing, item);
            return instructs.get("pick").get("instructions").get(0) + thing + instructs.get("pick").get("instructions").get(1);
        } else if (item != null && item.getLocation().equals("player")) {
            return instructs.get("pick").get("instructions").get(2) + thing;
        }
        return thing + instructs.get("pick").get("instructions").get(3);
    }
}
