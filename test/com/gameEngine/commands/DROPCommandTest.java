package com.gameEngine.commands;

import com.gameEngine.GameEngine;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DROPCommandTest {
    private StringBuilder gameBuilder ;
    private String[] command ;
    private Map<String, Map<String, List<String>>> instructs;
    private CommandInterface catalogCommand;

    @Before
    public void setUp() {
        gameBuilder = new StringBuilder();
        command = new String[2];
        command[0] = "catalog";
        instructs= GameEngine.GAME_ENGINE.getInstructs();
        catalogCommand = new CATALOGCommand();
    }

    @Test
    public void processCommand_shouldRemoveFromInventory_whenItemPresent() {

    }
}