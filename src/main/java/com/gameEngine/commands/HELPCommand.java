package com.gameEngine.commands;

import java.util.List;
import java.util.Map;

public class HELPCommand implements CommandInterface{


    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {
        gameBuilder.append(showInstructions(instructs));
    }

    private StringBuilder showInstructions( Map<String, Map<String, List<String>>> instructs) {
        StringBuilder builder = new StringBuilder();
        //for each line, append it to the string builder object
        instructs.get("help").get("instructions").forEach(eachLine-> builder.append("\n").append(eachLine));
        return builder;
    }
}
