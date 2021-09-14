package com.gameEngine.commands;

import com.character.Player;

import java.util.List;
import java.util.Map;

public class USECommand implements CommandInterface{

    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {
        processUse(gameBuilder, command,instructs);
    }

    private void processUse(StringBuilder gameBuilder, String[] command,Map<String, Map<String, List<String>>> instructs) {
        if (command.length == 2) {
            processUseLogic(gameBuilder, command, instructs);
        }
    }

    private void processUseLogic(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {
        if ("health kit".equalsIgnoreCase(command[1]) && Player.PLAYER.checkInventoryName("health kit")) {
            gameBuilder.append(healPlayer(instructs));
        }
        else if ("lever".equalsIgnoreCase(command[1]) && Player.PLAYER.checkInventoryName(
                "lever")){
            gameBuilder.append(instructs.get("use").get("instructions").get(0));
        } else {
            gameBuilder.append(instructs.get("use").get("instructions").get(1));
        }
    }
    private String healPlayer(Map<String, Map<String, List<String>>> instructs) {
        String response = "";
        if (Player.PLAYER.getHealth() >= 30) {
            response = instructs.get("heal").get("instructions").get(0);
        } else {
            Player.PLAYER.setHealth(Player.PLAYER.getHealth() + 5);
            response =
                    instructs.get("heal").get("instructions").get(1) + Player.PLAYER.getHealth() + instructs.get("heal").get("instructions").get(2);
        }
        return response;
    }
}
