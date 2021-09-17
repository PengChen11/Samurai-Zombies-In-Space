package com.gameEngine.commands;

import com.character.Player;
import com.gameEngine.GameEngine;
import com.item.Item;
import com.item.Weapon;
import com.location.Locations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class USECommandTest {
    private StringBuilder gameBuilder ;
    private String[] command ;
    private Map<String, Map<String, List<String>>> instructs;
    private USECommand useCommand;
    private Player player;
    @Before
    public void setUp() throws Exception {
//        Item.getItems("cfg/Items.json");
//        Weapon.getWeapons("cfg/Weapons.json");
//        Locations.initWithJsonFile("cfg/sampleLocations.json");
        player = Player.PLAYER;
        gameBuilder = new StringBuilder();
        command = new String[2];
        command[0] = "use";
        instructs= GameEngine.GAME_ENGINE.getInstructs();
        useCommand = new USECommand();
        player.getInventory().clear();
    }

    @Test
    public void processCommand_shouldUpdateGameBuilder_whenPassValidCommandWithoutItemInInventory() {
        command[1] = "health kit";
        useCommand.processCommand(gameBuilder, command, instructs);
        assertTrue(gameBuilder.toString().contains("You can't do that Dave."));
    }

    @Test
    public void processCommand_shouldUpdateGameBuilder_whenPassValidCommandWithItemInInventory() {
        command[1] = "health kit";
        player.addToInventory(new Item("health kit"));
        player.setHealth(15);
        useCommand.processCommand(gameBuilder, command, instructs);
        assertTrue(gameBuilder.toString().contains("Your current health is:"));
        assertTrue(player.getInventory().isEmpty());
    }

    @Test
    public void healPlayer_shouldNotAddHealth_whenPlayerHealthIsMax() {
        command[1] = "health kit";
        player.addToInventory(new Item("health kit"));
        player.setHealth(20);
        useCommand.processCommand(gameBuilder, command, instructs);
        assertTrue(gameBuilder.toString().contains("You're at max health."));
        assertFalse(player.getInventory().isEmpty());
    }

    @After
    public void tearDown() throws Exception {
        player.setHealth(20);
    }
}