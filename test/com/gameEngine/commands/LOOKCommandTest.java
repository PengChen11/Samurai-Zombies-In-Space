package com.gameEngine.commands;

import com.character.Player;
import com.gameEngine.GameEngine;
import com.item.Item;
import com.location.Locations;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class LOOKCommandTest {
    private StringBuilder gameBuilder ;
    private String[] command ;
    private Map<String, Map<String, List<String>>> instructs;
    private CommandInterface lookCommand;
    private Player player;

    @Before
    public void setUp() throws Exception {
        Locations.initWithJsonFile("cfg/sampleLocations.json");
        player = Player.PLAYER;
        gameBuilder = new StringBuilder();
        command = new String[2];
        command[0] = "look";
        instructs= GameEngine.GAME_ENGINE.getInstructs();
        lookCommand = new LOOKCommand();
    }
    @Test
    public void processCommand_shouldUpdateGameBuilder_whenInputValidCommand_1() {
        command[1] = "";
        lookCommand.processCommand(gameBuilder, command, instructs);
        assertTrue(gameBuilder.toString().contains("Landing Dock"));
        assertTrue(gameBuilder.toString().contains("Ruh roh. No items here!"));
        assertTrue(gameBuilder.toString().contains("The Zombie hits you."));
    }

    @Test
    public void processCommand_shouldUpdateGameBuilder_whenInputValidCommand_2() {
        command[1] = "around";
        lookCommand.processCommand(gameBuilder, command, instructs);
        assertTrue(gameBuilder.toString().contains("Landing Dock"));
        assertTrue(gameBuilder.toString().contains("Ruh roh. No items here!"));
        assertTrue(gameBuilder.toString().contains("The Zombie hits you."));
    }

    @Test
    public void processCommand_shouldUpdateGameBuilder_whenInputValidCommand_3() {
        command[1] = null;
        lookCommand.processCommand(gameBuilder, command, instructs);
        assertTrue(gameBuilder.toString().contains("Landing Dock"));
        assertTrue(gameBuilder.toString().contains("Ruh roh. No items here!"));
        assertTrue(gameBuilder.toString().contains("The Zombie hits you."));
    }

    @Test
    public void processCommand_shouldUpdateGameBuilder_whenInputInvalidCommand() {
        command[1] = "abc";
        lookCommand.processCommand(gameBuilder, command, instructs);
        assertTrue(gameBuilder.toString().contains("You don't see a " + command[1]));
    }

    @Test
    public void processCommand_shouldAddItemsToGameBuilder_whenPlayerInRoomWithItems() {
        command[1] = "around";
        player.setCurrentLocation(Locations.CentralHub);
        lookCommand.processCommand(gameBuilder, command, instructs);
        assertTrue(gameBuilder.toString().contains("zombie armor"));
        assertTrue(gameBuilder.toString().contains("zombie katana"));
    }

    //TODO: figure out how the item initialized in the location. and update the test method.
    @Test
    public void processCommand_shouldUpdateGameBuilder_whenLookExistItem() {
        command[1] = "zombie armor";
        Locations.CentralHub.addItem(new Item("zombie armor", ""));
        player.setCurrentLocation(Locations.CentralHub);
        lookCommand.processCommand(gameBuilder, command, instructs);
        assertTrue(gameBuilder.toString().contains("Looks to be in good shape, don't mind the blood."));
    }
}