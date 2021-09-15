package com.gameEngine.commands;

import com.gameEngine.GameEngine;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class QCommandTest {
    private StringBuilder gameBuilder ;
    private String[] command ;
    private Map<String, Map<String, List<String>>> instructs;
    private CommandInterface qCommand;

    @Before
    public void setUp() {
        gameBuilder = new StringBuilder();
        command = new String[2];
        command[0] = "quit";
        instructs= GameEngine.GAME_ENGINE.getInstructs();
        qCommand = new QCommand();
    }
//    @Test
//    public void processCommand_shouldExitGame_afterProcessed() {
//        qCommand.processCommand(gameBuilder, command, instructs);
//        assertTrue(gameBuilder.toString().contains("Exiting game"));
//    }

}