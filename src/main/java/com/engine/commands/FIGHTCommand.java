package com.engine.commands;

import com.character.Player;
import com.character.Zombie;
import com.engine.Parser;
import com.item.Item;

import com.sound.Punch;
import com.sound.SoundFX;
import com.sound.SoundFactory;
import com.sound.SoundType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FIGHTCommand implements CommandInterface {
    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, String currentLocation, List<Item> inventory, Zombie zombieNPC, Map<String, Map<String, List<String>>> instructs, Player player, HashMap<String, Item> catalog, JSONParser parser) {
        processFight(gameBuilder, command, player, zombieNPC,instructs,currentLocation, parser);
    }
    //This is the process for the 'fight' command
    private void processFight(StringBuilder gameBuilder, String[] command, Player player, Zombie zombieNPC,  Map<String, Map<String, List<String>>> instructs, String currentLocation, JSONParser parser) {
        if (checkForZombies(parser, currentLocation) && (command[1] == null || "zombie".equalsIgnoreCase(command[1]))) {
            player.setFightingZombie(true);
            while (player.getFightingZombie()) {
                gameBuilder.append(fightLoop(zombieNPC,instructs, currentLocation, player));
            }
        } else {
            gameBuilder.append(instructs.get("fight").get("instructions").get(8));
        }
    }
    //This is the process for a zombie attack.. we check for zombies then update the player,zombie, and gameBuilder
    private void processZombieAttack(StringBuilder gameBuilder, Zombie zombieNPC, Player player,String currentLocation, JSONParser parser) {
        if (checkForZombies(parser,currentLocation)) {
            if (zombieNPC.getHealth() > 0) {
                player.takeDamage(zombieNPC.attack());
                gameBuilder.append("\nThe Zomburai hits you. You have " + player.getHealth() + "HP.");
                gameBuilder.append("\nYou can fight the zombie or go to other locations");
            }
        }
    }
    //This method finds out if there are zombies in that current location using the JSON file
    private Boolean checkForZombies(JSONParser parser,String currentLocation) {
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

    //This method kicks off the fightLoop
    private StringBuilder fightLoop(Zombie zombie,  Map<String, Map<String, List<String>>> instructs,String currentLocation, Player player) {
        StringBuilder gameBuilder = new StringBuilder();
        Item zombieKatana = new Item("zombie katana", "Central Hub");
        gameBuilder.append("\n"+instructs.get("fight").get("instructions").get(0));

        processFightLogic(zombie, gameBuilder, zombieKatana, instructs, currentLocation, player);
        return gameBuilder;
    }
    //This method will handle the fight logic, ensuring we are able to fight and checking our players stats and weapons
    private void processFightLogic(Zombie zombie, StringBuilder gameBuilder, Item zombieKatana,  Map<String, Map<String, List<String>>> instructs, String currentLocation,Player player) {
        if (currentLocation.contains("Landing Dock")) {
            gameBuilder.append("\n"+instructs.get("fight").get("instructions").get(1).split(":")[0]+": " + player.getHealth()+instructs.get("fight").get("instructions").get(1).split(":")[1] + ": " + zombie.getHealth()
                    + instructs.get("fight").get("instructions").get(1).split(":")[2]);
            //call the punch sound effect
            SoundFactory.createSound(SoundType.PUNCH).startMusic();
            processArmedPlayer(zombie, gameBuilder, zombieKatana, player, instructs);
            processZombieAndPlayerHealth(zombie, gameBuilder, player, instructs);
        } else {
            gameBuilder.append("\n"+instructs.get("fight").get("instructions").get(7));
        }
    }
    //This is going to check the health of the player and zombie and update the gameBuilder accordingly
    private void processZombieAndPlayerHealth(Zombie zombie, StringBuilder gameBuilder,Player player,  Map<String, Map<String, List<String>>> instructs) {
        if (zombie.getHealth() > 0) {
            player.takeDamage(zombie.attack());
            gameBuilder.append("\n"+instructs.get("fight").get("instructions").get(4).split(":")[0] + player.getHealth() +instructs.get("fight").get("instructions").get(4).split(":")[1]);
        }
        if (player.getHealth() <= 0 || zombie.getHealth() <= 0) {
            processDeath(gameBuilder, player, instructs);
        }
    }

    //Checks for armed players and if they are it processes the attacks differently.
    private void processArmedPlayer(Zombie zombie, StringBuilder gameBuilder, Item zombieKatana, Player player,  Map<String, Map<String, List<String>>> instructs) {
        if (player.getHealth() > 0 && player.checkInventory(zombieKatana)) {
            SoundFactory.createSound(SoundType.WEAPON).startMusic();
            zombie.takeDamage(player.attack() * 2);
            gameBuilder.append("\n"+instructs.get("fight").get("instructions").get(2).split(":")[0] + zombie.getHealth() +instructs.get("fight").get("instructions").get(2).split(":")[1]);
        } else {
            zombie.takeDamage(player.attack());
            gameBuilder.append("\n"+instructs.get("fight").get("instructions").get(3).split(":")[0] + zombie.getHealth() + instructs.get("fight").get("instructions").get(3).split(":")[1]);

        }
    }
    //This should check if players health gets lower than 0 and update the gameBuilder
    private void processDeath(StringBuilder gameBuilder, Player player,  Map<String, Map<String, List<String>>> instructs) {
        if (player.getHealth() > 0) {
            gameBuilder.append("\n"+instructs.get("fight").get("instructions").get(5)+"\n");
            //zombie.setLocation(); <<<<<<< this should be a random location... gotta find out how to get that form the json
        } else {
            gameBuilder.append("\n"+instructs.get("fight").get("instructions").get(6));
        }
        player.setFightingZombie(false);
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

}
