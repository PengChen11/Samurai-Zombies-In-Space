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

    //public StringBuilder status = showStatus(currentLocation);
    public List<Item> inventory;

    //NPC zombies; ?? later for tracking how many are alive and where?
    HashMap<String, Item> catalog = Item.readAll();
    static JSONParser parser = new JSONParser();

    //Create a bar room
    HashMap<String, String> bar = new HashMap<>();

    public StringBuilder runGameLoop(String input) {
        StringBuilder gameBuilder = new StringBuilder();
        inventory = player.getInventory();

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
                    gameBuilder.append("\n \"Sorry, Dave. I can't get that.\n");
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
                        gameBuilder.append("What should I use this on?");
                    } else {
                        gameBuilder.append("You can't do that Dave.");
                    }
                }
                break;
            case "fight":
                if (checkForZombies() && (command.length == 1 || "zombie".equalsIgnoreCase(command[1]))) {
                    Zombie zombie = new Zombie(6, currentLocation);
                    player.setFightingZombie(true);
                    while (player.getFightingZombie()) {
                        gameBuilder.append(fightLoop(zombie));
                    }
                } else {
                    gameBuilder.append("\nDave, stay focused. You can pick a fight later.");
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
            if (zombie.equals("Zombie")) {
                return true;
            }
        } catch (NullPointerException e) {
            System.out.println("No zombies in " + currentLocation);
        }
        return false;
    }

    private StringBuilder fightLoop(Zombie zombie) {
        StringBuilder gameBuilder = new StringBuilder();
        List<String> attacks = null;
        try
        {
            attacks=Files.readAllLines(Path.of("cfg/zombieAttack.txt"));
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        Item zombieKatana = new Item("zombie katana", "Central Hub");
        gameBuilder.append("\n"+attacks.get(0));

        if (currentLocation.contains("Landing Dock")) {
            gameBuilder.append("\n"+attacks.get(1).split(":")[0]+": " + player.getHealth()+attacks.get(1).split(":")[1] + ": " + zombie.getHealth()
                    + attacks.get(1).split(":")[2]);

            if (player.getHealth() > 0 && player.checkInventory(zombieKatana)) {
                zombie.takeDamage(zombie.attack() * 2);
                gameBuilder.append("\n"+attacks.get(2).split(":")[0] + zombie.getHealth() +attacks.get(2).split(":")[1]);
            } else {
                zombie.takeDamage(zombie.attack());
                gameBuilder.append("\n"+attacks.get(3).split(":")[0] + zombie.getHealth() + attacks.get(3).split(":")[1]);
            }

//                        Thread.sleep(500); // Only works with sout. gameBuilder does a delay and then prints
            if (zombie.getHealth() > 0) {
                player.takeDamage(player.attack());
                gameBuilder.append("\n"+attacks.get(4).split(":")[0] + player.getHealth() +attacks.get(4).split(":")[1]);
            }


            if (player.getHealth() <= 0 || zombie.getHealth() <= 0) {
                if (player.getHealth() > 0) {
                    gameBuilder.append("\n"+attacks.get(5)+"\n");
                    player.setFightingZombie(false);
                } else {
                    gameBuilder.append("\n"+attacks.get(6));
                    player.setFightingZombie(false);
                }
            }
        } else {
            gameBuilder.append("\n"+attacks.get(7));
        }
        return gameBuilder;
    }
//provide instructions when the user type 'help'
    private StringBuilder showInstructions() {
        StringBuilder builder = new StringBuilder();
        List<String> instructions =null;
        try
        {
            //read all the lines from the file into a list of string
            instructions = Files.readAllLines(Path.of("cfg/instructions.txt"));
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        //for each line, append it to the string builder object
        instructions.stream().forEach(eachLine->builder.append("\n"+eachLine));
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
            response = "Don't look too hard now.";
        } else if (catalog.containsKey(object)) {
            response = catalog.get(object).getDescription();
        }  else if (NPC.checkCast(object)) {
            NPC character = new NPC(objectToFind);
            response = character.getDescription();
        } else {
            response = "You don't see a " + object + ".";
        }
        return response + "\n";
    }


    public StringBuilder showStatus(String location) {
        StringBuilder builder = new StringBuilder();
        System.out.println(player.getAreasVisited());
        System.out.println("Zombies currently following you: " + player.getZombiesFollowing());
        builder.append("\n You are currently in the ")
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
                description.append("\nItems in room: [");
                for (Item item : items) {
                    description.append(" ").append(item.getName()).append(" ");
                }
                description.append("]\n");
            } else {
                description.append("\nRuh roh. No items here!");
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
                return "You moved " + direction + "\n";
            }
        } catch (NullPointerException e) {
            System.out.println("Can't go that way\n");
        }
        return "Can't go that way\n";
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
            return "You can't drop what you don't have, Dave.";
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

        return "You dropped the " + playerItem + " in the " + currentLocation;
    }

    private String healPlayer() {
        String response = "";
        StringBuilder builder = new StringBuilder();
        if (player.getHealth() >= 30) {
            response = "\nYou're at max health.";
        } else {
            player.setHealth(player.getHealth() + 5);
            response = "\nYour current health is: " + player.getHealth() + "HP. ";
        }
        return response;
    }

    private String pickUpItem(String thing) {
        Item item = catalog.get(thing);
        if (item != null && item.getLocation().equals(currentLocation)) {
            item.setLocation("player");
            player.addToInventory(item);
            catalog.replace(thing, item);
            return "Placed " + thing + " in your inventory\n";
        } else if (item != null && item.getLocation().equals("player")) {
            return "You already have the " + thing;
        }
        return thing + " doesn't exist\n";
    }
}