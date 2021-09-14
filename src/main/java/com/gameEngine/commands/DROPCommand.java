package com.gameEngine.commands;

import com.character.Player;
import java.util.List;
import java.util.Map;

public class DROPCommand implements CommandInterface {

    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {
        gameBuilder.append(dropItem(command[1], instructs));
    }

    private String dropItem(String playerItem, Map<String, Map<String, List<String>>> instructs) {
        if (!Player.PLAYER.checkInventoryName(playerItem)) {
            return instructs.get("drop").get("instructions").get(0);
        }

        Player.PLAYER.dropToCurrentLocation(playerItem);

        return instructs.get("drop").get("instructions").get(1) + playerItem + instructs.get("drop").get(
                "instructions").get(2) + Player.PLAYER.getCurrentLocation().getName();
    }
}