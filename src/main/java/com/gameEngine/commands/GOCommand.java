package com.gameEngine.commands;

import com.character.Player;
import com.location.Locations;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.List;
import java.util.Map;

public class GOCommand implements CommandInterface{
    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs ) {
        processGo(gameBuilder, command[1],instructs);

    }

    private void processGo(StringBuilder gameBuilder, String direction, Map<String, Map<String, List<String>>> instructs) {
        gameBuilder.append(headToNextRoom(direction, instructs));
    }


    private String headToNextRoom(String direction,Map<String, Map<String, List<String>>> instructs) {

        Locations nextLocation = null;
        switch (direction.toLowerCase()){
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
            return instructs.get("head").get("instructions").get(2);
        }
        Player.PLAYER.setCurrentLocation(nextLocation);
        return instructs.get("head").get("instructions").get(0) + direction + "\n";
    }

}
