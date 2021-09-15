package com.gameEngine.commands;

import com.character.Player;
import com.gameEngine.GameEngine;
import com.item.Item;
import com.location.Locations;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class GETCommandTest {
    private StringBuilder gameBuilder ;
    private String[] command ;
    private Map<String, Map<String, List<String>>> instructs;
    private CommandInterface getCommand;
    private Item item;
    private Player player;
    @Before
    public void setUp() throws IOException {
        gameBuilder = new StringBuilder();
        command = new String[2];
        command[0] = "get";
        command[1] = "test item";
        item = new Item("test item", "Landing Dock", "a test item");
        instructs= GameEngine.GAME_ENGINE.getInstructs();
        getCommand = new GETCommand();
        player = Player.PLAYER;
    }

    @Test
    public void processCommand_shouldAddItemToInventory_whenItemIsPresentAndPlayersCurrentLocationIsSet() {
        Locations.LandingDock.addItem(item);
        getCommand.processCommand(gameBuilder,command,instructs);
        assertTrue(Player.PLAYER.getInventory().get(0).getName().equals("test item"));
    }
}