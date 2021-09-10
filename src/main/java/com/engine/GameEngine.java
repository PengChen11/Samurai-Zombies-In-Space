package com.engine;

import com.character.NPC;
import com.character.Player;
import com.character.Zombie;
import com.item.Item;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.HashMap;
import java.util.List;

public class GameEngine {

    public String currentLocation = "Landing Dock";
    private final Player player = Player.PLAYER;
    //private Instruction instructs;
    private Map<String,Map<String,List<String>>> instructs=new Instruction().instruct;

    //public StringBuilder status = showStatus(currentLocation);
    public List<Item> inventory;

    //NPC zombies; ?? later for tracking how many are alive and where?
    HashMap<String, Item> catalog = Item.readAll();
    static JSONParser parser = new JSONParser();

    //Create a bar room
    HashMap<String, String> bar = new HashMap<>();
    private Zombie zombieNPC;
    public StringBuilder runGameLoop(String input) {
        StringBuilder gameBuilder = new StringBuilder();
        inventory = player.getInventory();

        // initialize a zombie if there is a zombie in current location

        if (checkForZombies() && zombieNPC.getLocation().equals(currentLocation)) {
            zombieNPC = new Zombie(6, currentLocation);

        }
        String[] command;
        command = Parser.parseInput(input.toLowerCase());

        // perform actions
        switch (command[0]) {
            case "q":
                gameBuilder.append("Exiting game");
                System.exit(0);
                //TODO: Exit game scene without closing whole game
                break;
            case "look":
                if (command[1] == null || command[1].isBlank() || "around".equalsIgnoreCase(command[1])) {
                    gameBuilder.append(examineRoom());
                    // the zombie should attack you if there is a zombie in the room
                        if (checkForZombies()) {
                            if (zombieNPC.getHealth() > 0) {
                                player.takeDamage(zombieNPC.attack());
                                gameBuilder.append("\nThe Zomburai hits you. You have " + player.getHealth() + "HP.");
                                gameBuilder.append("\nYou can fight the zombie or go to other locations");
                            }
                        }

                } else {
                    gameBuilder.append(getLookResult(command[1]));
                }
                break;
            case "go":
                gameBuilder.append(headToNextRoom(command[1]));
                //check that this room is accessible from current room
                player.setLocation(currentLocation);
                break;
            case "get":
                if (command.length > 1) {
                    gameBuilder.append(pickUpItem((command[1])));
                } else {
                    gameBuilder.append(instructs.get("get").get("instructions").get(0));
                }
                break;
            case "drop":
               gameBuilder.append(dropItem(command[1]));
               break;
            case "talk":
                NPC character = new NPC(command[1]);
                gameBuilder.append(character.getDialogue());
                break;
            case "use":
                System.out.println(Arrays.toString(command));
                if (command.length == 2) {
                    if ("health kit".equalsIgnoreCase(command[1]) && player.checkInventoryName("health " +
                            "kit")) {
                        gameBuilder.append(healPlayer());
                    }
                    else if ("lever".equalsIgnoreCase(command[1]) && player.checkInventoryName(
                            "lever")){
                        gameBuilder.append(instructs.get("use").get("instructions").get(0));
                    } else {
                        gameBuilder.append(instructs.get("use").get("instructions").get(1));
                    }
                }
                break;
            case "fight":
                if (checkForZombies() && (command[1] == null || "zombie".equalsIgnoreCase(command[1]))) {
                    player.setFightingZombie(true);
                    while (player.getFightingZombie()) {
                        gameBuilder.append(fightLoop(zombieNPC));
                    }


                } else {
                    gameBuilder.append(instructs.get("fight").get("instructions").get(8));
                }
                break;
            case "help":
                gameBuilder.append(showInstructions());
                break;
            case "catalog":
                gameBuilder.append(reviewCatalog());
                break;
            case "inventory":
                gameBuilder.append(reviewInventory());
                break;
            default:
                gameBuilder.append("Sorry, Dave. I can\'t do that.");

        }

        //update win/lose status
        checkPlayerHealth();
        checkPuzzleComplete();

        return gameBuilder.append(showStatus(currentLocation));
    }

    private Boolean checkForZombies() {

        try {
            JSONObject current = getJsonObject();
            String zombie = (String) current.get("enemy");
            if ("Zombie".equals(zombie) ) {
                return true;
            }
        } catch (NullPointerException e) {
            System.out.println("No zombies in " + currentLocation);
        }
        return false;
    }



    //TODO:probably needs separation of responsibilities on this one.... we shouldnt make swords in a fight loop.. it should only be responsible for fighitng
    private StringBuilder fightLoop(Zombie zombie) {
        StringBuilder gameBuilder = new StringBuilder();
        Item zombieKatana = new Item("zombie katana", "Central Hub");
        gameBuilder.append("\n"+instructs.get("fight").get("instructions").get(0));

        if (currentLocation.contains("Landing Dock")) {
            gameBuilder.append("\n"+instructs.get("fight").get("instructions").get(1).split(":")[0]+": " + player.getHealth()+instructs.get("fight").get("instructions").get(1).split(":")[1] + ": " + zombie.getHealth()
                    + instructs.get("fight").get("instructions").get(1).split(":")[2]);

            if (player.getHealth() > 0 && player.checkInventory(zombieKatana)) {
                zombie.takeDamage(player.attack() * 2);
                gameBuilder.append("\n"+instructs.get("fight").get("instructions").get(2).split(":")[0] + zombie.getHealth() +instructs.get("fight").get("instructions").get(2).split(":")[1]);
            } else {
                zombie.takeDamage(player.attack());
                gameBuilder.append("\n"+instructs.get("fight").get("instructions").get(3).split(":")[0] + zombie.getHealth() + instructs.get("fight").get("instructions").get(3).split(":")[1]);

            }
            if (zombie.getHealth() > 0) {
                player.takeDamage(zombie.attack());
                gameBuilder.append("\n"+instructs.get("fight").get("instructions").get(4).split(":")[0] + player.getHealth() +instructs.get("fight").get("instructions").get(4).split(":")[1]);

            }


            if (player.getHealth() <= 0 || zombie.getHealth() <= 0) {
                if (player.getHealth() > 0) {
                    gameBuilder.append("\n"+instructs.get("fight").get("instructions").get(5)+"\n");
                    player.setFightingZombie(false);
                    //zombie.setLocation(); <<<<<<< this should be a random location... gotta find out how to get that form the json
                } else {
                    gameBuilder.append("\n"+instructs.get("fight").get("instructions").get(6));
                    player.setFightingZombie(false);
                }
            }
        } else {
            gameBuilder.append("\n"+instructs.get("fight").get("instructions").get(7));
        }
        return gameBuilder;
    }
    //provide instructions when the user type 'help'
    private StringBuilder showInstructions() {
        StringBuilder builder = new StringBuilder();
           //for each line, append it to the string builder object
        instructs.get("help").get("instructions").stream().forEach(eachLine->builder.append("\n"+eachLine));
        return builder;
    }

    private String getLookResult(String objectToFind) {
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


    public StringBuilder showStatus(String location) {
        StringBuilder builder = new StringBuilder();
        System.out.println(player.getAreasVisited());
        System.out.println(instructs.get("status").get("instructions").get(0) + player.getZombiesFollowing());
        builder.append(instructs.get("status").get("instructions").get(1))
                .append(location).append("\n\n");
        return builder;
    }

    private void checkPlayerHealth() {
        //if Player.getHealth() < 1 { loseStatus = true; }
    }

    private void checkPuzzleComplete() {
        //if wizzlewhat and space wrench are in player inventory, winStatus = true
        //later, account for fueling time as well
    }

    private String[] parser(String input) {
        return input.toLowerCase().split("[\\s]+");
    }

    private String reviewCatalog() {
        String returnVal = " Items : [";
        for (Map.Entry<String, Item> item : catalog.entrySet()) {
            returnVal += item.getValue().getName() + " (in " + item.getValue().getLocation() + ") ";
        }
        return returnVal;
    }

    private String reviewInventory() {
        String returnVal = " Items : [";
        for (Item item : player.getInventory()) {
            returnVal += item.getName() + " (in " + item.getLocation() + ") ";
        }
        return returnVal;
    }

    private String examineRoom() {
        StringBuilder description = new StringBuilder();
        try {
            JSONObject current = getJsonObject();
            description = new StringBuilder((String) current.get("Description"));
            List<Item> items = new ArrayList<>();
            for (Map.Entry<String, Item> item : catalog.entrySet()) {
                if (item.getValue().getLocation().equals(currentLocation)) {
                    items.add(item.getValue());
                }
            }
            if (items.size() > 0) {
                description.append(instructs.get("examine").get("instructions").get(0));
                for (Item item : items) {
                    description.append(" ").append(item.getName()).append(" ");
                }
                description.append("]\n");
            } else {
                description.append(instructs.get("examine").get("instructions").get(1));
            }
            return description + "\n";
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return description + "]\n";
    }

    private String headToNextRoom(String direction) {
        try {
            JSONObject current = getJsonObject();
            String next = (String) current.get(direction);
            if (current.containsKey("enemy") && !player.checkAreasVisited(currentLocation)) {
                player.addZombiesFollowing();
                player.addAreasVisited(currentLocation);
            }
            if (current.containsKey(direction)) {
                currentLocation = next;
                return instructs.get("head").get("instructions").get(0) + direction + "\n";
            }
        } catch (NullPointerException e) {
            System.out.println(instructs.get("head").get("instructions").get(1));
        }
        return instructs.get("head").get("instructions").get(2);
    }

    private JSONObject getJsonObject() {
        JSONObject locations = new JSONObject();
        try {
            locations = (JSONObject) parser.parse(new FileReader("cfg/Locations.json"));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return (JSONObject) locations.get(currentLocation);
    }

    private String dropItem(String playerItem) {
        if (!player.checkInventoryName(playerItem)) {
            return instructs.get("drop").get("instructions").get(0);
        }

        //inventory.removeIf(item -> item.getName().equals(playerItem));
        catalog.replace(playerItem, new Item(playerItem, currentLocation)); //relocate the item
        player.removeInventory(playerItem);

        inventory = player.getInventory(); // refresh inventory

        // need to replace last item in list with empty item. removal of last item results in item being displayed in inventory container.
        if (inventory.size() <= 1) {
            Item emptyItem = new Item("", currentLocation);
            inventory.add(emptyItem);
        }

        return instructs.get("drop").get("instructions").get(1) + playerItem + instructs.get("drop").get("instructions").get(2) + currentLocation;
    }

    private String healPlayer() {
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

    private String pickUpItem(String thing) {
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