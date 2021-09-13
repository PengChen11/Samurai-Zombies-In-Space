package com.engine.commands;

import com.character.Player;
import com.character.Zombie;
import com.engine.commands.CommandInterface;
import com.item.Item;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HELPCommand implements CommandInterface {
    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, String currentLocation, List<Item> inventory, Zombie zombieNPC, Map<String, Map<String, List<String>>> instructs, Player player, HashMap<String, Item> catalog, JSONParser parser) {
        gameBuilder.append(showInstructions(instructs));
    }//provide instructions when the user type 'help'
    private StringBuilder showInstructions( Map<String, Map<String, List<String>>> instructs) {
        StringBuilder builder = new StringBuilder();
        //for each line, append it to the string builder object
        instructs.get("help").get("instructions").stream().forEach(eachLine->builder.append("\n"+eachLine));
        return builder;
    }

}
