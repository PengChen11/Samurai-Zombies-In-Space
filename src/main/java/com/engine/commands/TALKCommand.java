package com.engine.commands;

import com.character.NPC;
import com.character.Player;
import com.character.Zombie;
import com.engine.commands.CommandInterface;
import com.item.Item;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TALKCommand implements CommandInterface {
    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, String currentLocation, List<Item> inventory, Zombie zombieNPC, Map<String, Map<String, List<String>>> instructs, Player player, HashMap<String, Item> catalog, JSONParser parser) {
        processTalk(gameBuilder,command[1]);
    }

    //This method should process the "talk" command by generating a new character and updating the player
    private void processTalk(StringBuilder gameBuilder, String character1) {
        NPC character = new NPC(character1);
        gameBuilder.append(character.getDialogue());
    }
}
