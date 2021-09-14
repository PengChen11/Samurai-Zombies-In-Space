package com.engine.commands;

import com.character.NPC;
import com.character.Player;
import com.character.Zombie;

import com.item.Item;
import com.sound.Roar;
import com.sound.SoundFX;
import com.sound.SoundFactory;
import com.sound.SoundType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LOOKCommand implements CommandInterface {
    private SoundFX lookSound;
    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, String currentLocation, List<Item> inventory, Zombie zombieNPC, Map<String, Map<String, List<String>>> instructs, Player player, HashMap<String, Item> catalog, JSONParser parser) {
        processLook(gameBuilder, command,zombieNPC,player,currentLocation,parser, instructs, catalog);
    } //This method should "look" in the same room that the player is currently in and append the gameBuilder
    private void processLook(StringBuilder gameBuilder, String[] command,Zombie zombieNPC, Player player,String currentLocation,JSONParser parser, Map<String, Map<String, List<String>>> instructs, HashMap<String, Item> catalog) {
        if (command[1] == null || command[1].isBlank() || "around".equalsIgnoreCase(command[1])) {
            gameBuilder.append(examineRoom(parser,currentLocation,instructs,catalog));
            // the zombie should attack you if there is a zombie in the room
            //call the look sound
            SoundFactory.createSound(SoundType.ROAR).startMusic();
            processZombieAttack(gameBuilder,zombieNPC, player,currentLocation,parser);

        } else {
            gameBuilder.append(getLookResult(command[1],instructs,catalog));
        }
    } //This is the process for a zombie attack.. we check for zombies then update the player,zombie, and gameBuilder
    private void processZombieAttack(StringBuilder gameBuilder,Zombie zombieNPC,Player player, String currentLocation,JSONParser parser) {
        if (checkForZombies(player,currentLocation, parser)) {
            if (zombieNPC.getHealth() > 0) {
                player.takeDamage(zombieNPC.attack());
                gameBuilder.append("\nThe Zomburai hits you. You have " + player.getHealth() + "HP.");
                gameBuilder.append("\nYou can fight the zombie or go to other locations");
            }
        }
    }//This method finds out if there are zombies in that current location using the JSON file
    private Boolean checkForZombies(Player player,String currentLocation,JSONParser parser) {
        try {
            JSONObject current = getJsonObject(parser,currentLocation);
            String zombie = (String) current.get("enemy");
            if ("Zombie".equals(zombie) ) {
                return true;
            }
        } catch (NullPointerException e) {
            System.out.println("No zombies in " + currentLocation);
        }
        return false;
    }
    private JSONObject getJsonObject(JSONParser parser, String currentLocation) {
        JSONObject locations = new JSONObject();
        try {
            locations = (JSONObject) parser.parse(new FileReader("cfg/Locations.json"));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return (JSONObject) locations.get(currentLocation);
    }
    private String examineRoom(JSONParser parser, String currentLocation, Map<String, Map<String, List<String>>> instructs, HashMap<String, Item> catalog) {
        StringBuilder description = new StringBuilder();
        try {
            return getDescriptionFromJson(description,parser,currentLocation,instructs,catalog);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return description + "]\n";
    }
    //Going to need the description of the current location and all the items there aswell
    private String getDescriptionFromJson(StringBuilder description,JSONParser parser, String currentLocation,Map<String, Map<String, List<String>>> instructs, HashMap<String, Item> catalog) {
        JSONObject current = getJsonObject(parser, currentLocation);
        description = new StringBuilder((String) current.get("Description"));
        List<Item> items = new ArrayList<>();
        findItemsInLocation(items,currentLocation,catalog);
        appendItemsToDescription(description, items,instructs);
        return description + "\n";
    }
    //helper method for the getDescriptionFromJson, this will append the items to the return string
    private void appendItemsToDescription(StringBuilder description, List<Item> items, Map<String, Map<String, List<String>>> instructs) {
        if (items.size() > 0) {
            description.append(instructs.get("examine").get("instructions").get(0));
            for (Item item : items) {
                description.append(" ").append(item.getName()).append(" ");
            }
            description.append("]\n");
        } else {
            description.append(instructs.get("examine").get("instructions").get(1));
        }
    }
    //This is just to validate the items are in their current location and add them to the items list
    private void findItemsInLocation(List<Item> items, String currentLocation, HashMap<String, Item> catalog) {
        for (Map.Entry<String, Item> item : catalog.entrySet()) {
            if (item.getValue().getLocation().equals(currentLocation)) {
                items.add(item.getValue());
            }
        }
    }
    private String getLookResult(String objectToFind, Map<String, Map<String, List<String>>> instructs, HashMap<String, Item> catalog) {
        String object;
        if (objectToFind == null || objectToFind.equals("")) {
            object = "around";
        } else {
            object = objectToFind.strip().toLowerCase();
        }
        String response;
        if (object.equals("around")) {
            response = instructs.get("look").get("instructions").get(0);
        } else if (catalog.containsKey(object)) {
            response = catalog.get(object).getDescription();
        }  else if (NPC.checkCast(object)) {
            NPC character = new NPC(objectToFind);
            response = character.getDescription();
        } else {
            response = instructs.get("look").get("instructions").get(1) + object + ".";
        }
        return response + "\n";
    }


}
