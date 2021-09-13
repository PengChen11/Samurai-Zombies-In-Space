package com.engine.commands;

import com.character.Player;
import com.character.Zombie;

import com.item.Item;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QCommand implements CommandInterface {


    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, String currentLocation, List<Item> inventory, Zombie zombieNPC, Map<String, Map<String, List<String>>> instructs, Player player, HashMap<String, Item> catalog, JSONParser parser) {
        processQuit(gameBuilder);
    }

    //This method should quite the application
    private void processQuit(StringBuilder gameBuilder) {
        gameBuilder.append("Exiting game");
        System.exit(0);
        //TODO: Exit game scene without closing whole game
    }
}
