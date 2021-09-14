package com.gameEngine.commands;

import java.util.List;
import java.util.Map;

public class INVENTORYCommand implements CommandInterface{
    // todo: I don't think we need this one. We already have an inventory section in the UI. -- Peng
    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {

    }
}
