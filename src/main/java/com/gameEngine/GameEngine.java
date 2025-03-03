package com.gameEngine;

import com.character.Player;
import com.controller.GameSceneControllerNew;
import com.gameEngine.commands.*;
import com.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum GameEngine {

    GAME_ENGINE; // how to refer to instance of game engine

    private Map<String, Map<String, List<String>>> instructs = new Instruction().getInstruction();



    public  boolean goodCommand;
   // HashMap<String, Item> catalog = Item.readAll();



    public StringBuilder runGameLoop(String input)  {
        goodCommand = false;
        StringBuilder gameBuilder = new StringBuilder();

        String[] command;
        command = Parser.parseInput(input.toLowerCase());

        ArrayList<CommandInterface> commandList = getCommandInterfaces();

        boolean validCommand = false;
        for(CommandInterface commandFromList : commandList){
            if(commandFromList.getClass().getSimpleName().contains(command[0].toUpperCase()) && !command[0].equals("")){
                commandFromList.processCommand(gameBuilder,command, instructs);
                validCommand = true;
                break;
            }
        }

        if (!validCommand){
            gameBuilder.append(instructs.get("error"));
        }
        //update win/lose status
        checkPlayerHealth();
        checkPuzzleComplete();
        return gameBuilder;
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

   /* public HashMap<String, Item> getCatalog() {
        return catalog;
    }*/

}
