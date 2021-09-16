package com.gameEngine.commands;

import com.character.Player;
import com.character.Zombie;
import com.gameEngine.GameEngine;
import com.item.Item;
import com.item.Weapon;
import com.location.Locations;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class LOOKCommandTest {
    private StringBuilder gameBuilder ;
    private String[] command ;
    private Map<String, Map<String, List<String>>> instructs;
    private CommandInterface lookCommand;
    private Player player;
    @BeforeClass
    public static void setupClass() throws Exception {
        Item.getItems("cfg/Items.json");
        Weapon.getWeapons("cfg/Weapons.json");
        Locations.initWithJsonFile("cfg/sampleLocations.json");

    }

    @Before
    public void setUp() throws Exception {
        player = Player.PLAYER;
        instructs= GameEngine.GAME_ENGINE.getInstructs();
        player.setCurrentLocation(Locations.LandingDock);
        Locations.LandingDock.getItemList().clear();
        Locations.LandingDock.setZombie(Zombie.getInstance());
        gameBuilder = new StringBuilder();
        command = new String[2];
        command[0] = "look";
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
        player.setCurrentLocation(Locations.LandingDock);
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

    @Test
    public void processCommand_shouldUpdateGameBuilder_whenLookExistItem() {
        command[1] = "zombie armor";
        player.setCurrentLocation(Locations.CentralHub);
        lookCommand.processCommand(gameBuilder, command, instructs);
        assertTrue(gameBuilder.toString().contains("Looks to be in good shape, don't mind the blood."));
    }

    @After
    public void tearDown() throws Exception {
        player.setHealth(20);
    }

    // Background music will not be tested here.
}