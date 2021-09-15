package com.gameEngine.commands;

import com.character.Player;
import com.character.Zombie;
import com.item.Item;
import com.sound.Roar;
import com.sound.SoundFactory;
import com.sound.SoundType;

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
            if (Player.PLAYER.getCurrentLocation().getZombie() != null) {
                SoundFactory.createSound(SoundType.ROAR).startMusic();
                processZombieAttack(gameBuilder, instructs);
            }
        } else {
            gameBuilder.append(getLookResult(command[1], instructs));
        }
        // the zombie should attack you if there is a zombie in the room

    }


    private void appendItemsToDescription(StringBuilder description, Map<String, Map<String, List<String>>> instructs) {
        List<Item> items = Player.PLAYER.getCurrentLocation().getItemList();

        if (items.size() > 0) {
            description.append(instructs.get("examine").get("instructions").get(0));
            for (int i = 0; i < items.size(); i++) {
                description.append(" ").append(items.get(i).getName()).append(
                        items.size() > 1 && i != items.size() - 1 ?
                                ", " : "");
            }
            description.append("]\n");
        } else {
            description.append(instructs.get("examine").get("instructions").get(1));
        }
    }

    private void processZombieAttack(StringBuilder gameBuilder, Map<String, Map<String, List<String>>> instructs) {
        Zombie zombie = Player.PLAYER.getCurrentLocation().getZombie();
        Player.PLAYER.takeDamage(zombie.attack());
        gameBuilder.append(instructs.get("look").get("instructions").get(2)).append(Player.PLAYER.getHealth()).append(instructs.get("look").get("instructions").get(4));
        gameBuilder.append(instructs.get("look").get("instructions").get(3));
    }

    private String getLookResult(String objectToFind, Map<String, Map<String, List<String>>> instructs) {
        String response;
        Item item = Player.PLAYER.getCurrentLocation().containsItem(objectToFind);
        if (item != null) {
            response = item.getDescription();
        } else {
            response = instructs.get("look").get("instructions").get(1) + objectToFind + ".";
        }
        return response + "\n";
    }
}
