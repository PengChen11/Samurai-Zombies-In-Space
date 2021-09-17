package com.gameEngine.commands;

import com.character.Player;
import com.location.Locations;

import java.util.List;
import java.util.Map;

public class GOCommand implements CommandInterface{
    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs ) {

        Locations nextLocation = null;
        switch (command[1].toLowerCase()){
            case "north":
                nextLocation = Player.PLAYER.getCurrentLocation().getNorth();
                break;
            case "east":
                nextLocation = Player.PLAYER.getCurrentLocation().getEast();
                break;
            case "west":
                nextLocation = Player.PLAYER.getCurrentLocation().getWest();
                break;
            case "south":
                nextLocation = Player.PLAYER.getCurrentLocation().getSouth();
                break;
        }

        if (nextLocation == null){
            gameBuilder.append(instructs.get("head").get("instructions").get(2));
        } else {
            gameBuilder.append(instructs.get("head").get("instructions").get(0)).append(command[1]).append("\n");

            Player.PLAYER.setCurrentLocation(nextLocation);
        }
    }
}
