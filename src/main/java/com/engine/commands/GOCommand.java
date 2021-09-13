package com.engine.commands;

import com.character.Player;
import com.character.Zombie;
import com.engine.GameEngine;
import com.item.Item;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GOCommand implements CommandInterface {
    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, String currentLocation, List<Item> inventory, Zombie zombieNPC, Map<String, Map<String, List<String>>> instructs, Player player, HashMap<String, Item> catalog, JSONParser parser) {

        processGo(gameBuilder, command[1], player, currentLocation,parser,instructs);
    }
    //This method should change the player location and update the gameBuilder to reflect the change in location
    private void processGo(StringBuilder gameBuilder, String direction,Player player, String currentLocation, JSONParser parser, Map<String, Map<String, List<String>>> instructs) {
        gameBuilder.append(headToNextRoom(direction, instructs, player, currentLocation, parser));
        //check that this room is accessible from current room
    }
    //this method will check available routes and if the input is one of them it will update the player to that location, and return the string
    private String headToNextRoom(String direction,Map<String, Map<String, List<String>>> instructs, Player player, String currentLocation, JSONParser parser) {
        try {
            JSONObject current = getJsonObject(parser,currentLocation);
            String next = (String) current.get(direction);
            String instructs1 = navigationLogic(direction, current, next,instructs,player,currentLocation);
            if (instructs1 != null) return instructs1;
        } catch (NullPointerException e) {
            System.out.println(instructs.get("head").get("instructions").get(1));
        }
        return instructs.get("head").get("instructions").get(2);
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
    private String navigationLogic(String direction, JSONObject current, String next,Map<String, Map<String, List<String>>> instructs,Player player, String currentLocation) {
        if (current.containsKey("enemy") && !player.checkAreasVisited(currentLocation)) {
            player.addZombiesFollowing();
            player.addAreasVisited(currentLocation);
        }
        if (current.containsKey(direction)) {
            GameEngine.getInstance().setCurrentLocation(next);
            return instructs.get("head").get("instructions").get(0) + direction + "\n";
        }
        return null;
    }
}
