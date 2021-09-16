package com.gameEngine;

import com.character.Player;
import com.controller.FXMLDocumentController;
import com.engine.Instruction;
import com.engine.Parser;
import com.gameEngine.commands.*;
import com.item.Item;
import com.location.Locations;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum GameEngine {

    GAME_ENGINE; // how to refer to instance of game engine

    private Map<String, Map<String, List<String>>> instructs = new Instruction().getInstruction();

    HashMap<String, Item> catalog = Item.readAll();



    public StringBuilder runGameLoop(String input)  {

        StringBuilder gameBuilder = new StringBuilder();

        String[] command;
        command = Parser.parseInput(input.toLowerCase());

        ArrayList<CommandInterface> commandList = getCommandInterfaces();

        for(CommandInterface commandFromList : commandList){
            if(commandFromList.getClass().getSimpleName().contains(command[0].toUpperCase())){
                commandFromList.processCommand(gameBuilder,command, instructs);
            }
        }
        //update win/lose status
        checkPlayerHealth();
        checkPuzzleComplete();
        return gameBuilder.append(showStatus(Player.PLAYER.getCurrentLocation().getName()));
    }


    public StringBuilder showStatus(String location) {
        StringBuilder builder = new StringBuilder();
        builder.append(instructs.get("status").get("instructions").get(1))
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



    private ArrayList<CommandInterface> getCommandInterfaces() {
        CommandInterface qCommand = new QCommand();
        CommandInterface talkCommand = new TALKCommand();
        CommandInterface lookCommand = new LOOKCommand();
        CommandInterface fightCommand =  new FIGHTCommand();
        CommandInterface useCommand = new USECommand();
        CommandInterface getCommand = new GETCommand();
        CommandInterface dropCommand = new DROPCommand();
        CommandInterface goCommand = new GOCommand();

        CommandInterface helpCommand = new HELPCommand();
        CommandInterface saveCommand = new SAVECommand();
        ArrayList<CommandInterface> commandList = new ArrayList();
        commandList.add(qCommand);
        commandList.add(talkCommand);
        commandList.add(lookCommand);
        commandList.add(fightCommand);
        commandList.add(useCommand);
        commandList.add(getCommand);
        commandList.add(dropCommand);
        commandList.add(goCommand);
        commandList.add(helpCommand);
        commandList.add(saveCommand);
        return commandList;
    }


    public Map<String, Map<String, List<String>>> getInstructs() {
        return instructs;
    }

    public HashMap<String, Item> getCatalog() {
        return catalog;
    }

}
