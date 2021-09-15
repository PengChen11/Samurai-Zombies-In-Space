package com.gameEngine.commands;

import com.character.Player;
import com.item.Item;
import com.location.Locations;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SAVECommand implements CommandInterface{
    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {

        JSONObject gameData = new JSONObject();

        gameData.put("player", getPlayerData());
        gameData.put("locations", getLocationsData());

        //Write JSON file
        try (FileWriter file = new FileWriter("cfg/save/gameData.json")) {
            file.write(gameData.toJSONString());
            file.flush();
            gameBuilder.append("You successfully saved your Game Data!! ");
        } catch (IOException e) {
            e.printStackTrace();
            gameBuilder.append("Something went wrong, game can NOT be saved right now!");
        }
    }

    private JSONObject getPlayerData(){

        JSONObject playerDetails = new JSONObject();
        playerDetails.put("health", Player.PLAYER.getHealth());
        playerDetails.put("currentLocation", Player.PLAYER.getCurrentLocation().getName());

        JSONArray inventoryList = new JSONArray();
        for (Item item : Player.PLAYER.getInventory()){
            inventoryList.add(item.getName());
        }
        playerDetails.put("inventory", inventoryList);

        return playerDetails;
    }

    private JSONObject getLocationsData(){
        JSONObject locationsObj = new JSONObject();

        for (Locations location : Locations.values()){
            JSONObject locationDetails = new JSONObject();

            String npcName = location.getNpc() != null ? location.getNpc().getName() : null;
            locationDetails.put("npc", npcName);

            String zombieInfo = location.getZombie() != null ? "yes" : null;
            locationDetails.put("zombie", zombieInfo);

            JSONArray itemList = new JSONArray();
            for (Item item : location.getItemList()){
                itemList.add(item.getName());
            }
            locationDetails.put("itemList", itemList);

            locationsObj.put(location.getName(), locationDetails);
        }
        return locationsObj;
    }
}
