package com.gameEngine.commands;

import com.character.Player;
import com.gameEngine.GameEngine;
import com.item.Item;
import com.item.Weapon;
import com.location.Locations;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GETCommandTest {
    private StringBuilder gameBuilder ;
    private String[] command ;
    private Map<String, Map<String, List<String>>> instructs;
    private CommandInterface getCommand;
    private Item item;
    private Player player;
    @Before
    public void setUp() throws Exception {
        Item.getItems("cfg/Items.json");
        Weapon.getWeapons("cfg/Weapons.json");
        Locations.initWithJsonFile("cfg/sampleLocations.json");
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
        System.out.println(Locations.LandingDock.getItemList().get(0));
        player.setCurrentLocation(Locations.LandingDock);
        getCommand.processCommand(gameBuilder,command,instructs);
        assertEquals("test item", player.getInventory().get(0).getName());
    }

    @Test
    public void processCommand_shouldNotAddItemToInventory_whenItemIsNotPresentAtCurrentLocation() {
        player.getInventory().clear();
        player.setCurrentLocation(Locations.Bar);
        getCommand.processCommand(gameBuilder,command,instructs);
        assertEquals(0, player.getInventory().size());
    }

    @Test
    public void processCommand_shouldNotBeAbleToPickUpItemsInOtherRooms_whenCurrentLocationDoesNotMatch() {
        player.getInventory().clear();
        player.setCurrentLocation(Locations.Bar);
        getCommand.processCommand(gameBuilder,command,instructs);
        assertEquals(0,player.getInventory().size());
    }

    @Test
    public void processCommand_shouldUpdateGameBuilder_whenGivenInvalidCommand() {
        player.getInventory().clear();
        command[1] = "not a test item";
        getCommand.processCommand(gameBuilder,command,instructs);
        assertTrue(gameBuilder.toString().contains(instructs.get("pick").get("instructions").get(3)));
        command[1] = "test item";
    }

}