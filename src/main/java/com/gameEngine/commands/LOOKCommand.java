package com.gameEngine.commands;

import com.character.NPC;
import com.character.Player;
import com.character.Zombie;
import com.item.Item;
import com.sound.Roar;
import java.util.List;
import java.util.Map;

public class LOOKCommand implements CommandInterface {

    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {
        processLook(gameBuilder, command, instructs);
    }

    private void processLook(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {
        if (command[1] == null || command[1].isBlank() || "around".equalsIgnoreCase(command[1])) {
            gameBuilder.append(Player.PLAYER.getCurrentLocation().getDescription()).append("\n");
            appendItemsToDescription(gameBuilder, instructs);
        } else {
            gameBuilder.append(getLookResult(command[1],instructs));
        }
        // the zombie should attack you if there is a zombie in the room
        processZombieAttack(gameBuilder);
    }


    private void appendItemsToDescription(StringBuilder description, Map<String, Map<String, List<String>>> instructs) {
        List<Item> items = Player.PLAYER.getCurrentLocation().getItemList();

        if (items.size() > 0) {
            description.append(instructs.get("examine").get("instructions").get(0));
            for (Item item : items) {
                description.append(" ").append(item.getName()).append(", ");
            }
            description.append("]\n");
        } else {
            description.append(instructs.get("examine").get("instructions").get(1));
        }
    }

    private void processZombieAttack(StringBuilder gameBuilder) {
        Zombie zombie = Player.PLAYER.getCurrentLocation().getZombie();
        if ( zombie != null && zombie.getHealth() > 0) {
            new Roar().startMusic();
            Player.PLAYER.takeDamage(zombie.attack());
            gameBuilder.append("\nThe Zomburai hits you. You have ").append(Player.PLAYER.getHealth()).append("HP.");
            gameBuilder.append("\nYou can fight the zombie or go to other locations");
        }
    }

    private String getLookResult(String objectToFind, Map<String, Map<String, List<String>>> instructs) {
        String response;
        Item item = containsItem(objectToFind);
        if (item != null) {
            response = item.getDescription();
        } else {
            response = instructs.get("look").get("instructions").get(1) + objectToFind + ".";
        }
        return response + "\n";
    }

    private Item containsItem(String key){
        List<Item> items = Player.PLAYER.getCurrentLocation().getItemList();
        for (Item item : items){
            if (key.equalsIgnoreCase(item.getName())){
                return item;
            }
        }
        return null;
    }
}
