package com.gameEngine.commands;

import com.character.Player;
import com.character.Zombie;
import com.controller.GameSceneControllerNew;
import com.location.Locations;
import com.sound.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

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
            GOCommand.randomlyGenerateZombie(nextLocation);
            Player.PLAYER.setCurrentLocation(nextLocation);
        }
    }

    private static void randomlyGenerateZombie(Locations location) {
        Random rand = new Random();
        int randomNumber = rand.nextInt();
        if (randomNumber % 3 == 0) {
            location.setZombie(new Zombie(5, location.getName()));
        }
    }
}
