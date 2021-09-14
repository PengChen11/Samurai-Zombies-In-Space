package com.gameEngine.commands;

import com.gameEngine.GameEngine;

import java.util.List;
import java.util.Map;

public class QCommand implements CommandInterface{

    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {
        gameBuilder.append("Exiting game");
        System.exit(0);
        //TODO: Exit game scene without closing whole game
    }
}
