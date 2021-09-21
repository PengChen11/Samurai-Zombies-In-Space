package com.gameEngine.commands;

import java.util.List;
import java.util.Map;

public interface CommandInterface{
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs);
}
