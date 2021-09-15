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

import static org.junit.Assert.*;

public class GOCommandTest
{
    private StringBuilder gameBuilder ;
    private String[] command ;
    private Map<String, Map<String, List<String>>> instructs;
    private CommandInterface goCommand;
    private Item item;
    private Player player;
    @Before
    public void setUp() throws Exception {
        Item.getItems("cfg/Items.json");
        Weapon.getWeapons("cfg/Weapons.json");
        Locations.initWithJsonFile("cfg/sampleLocations.json");
        gameBuilder = new StringBuilder();
        command = new String[2];
        command[0] = "go";
        command[1] = "north";
        instructs= GameEngine.GAME_ENGINE.getInstructs();
        goCommand = new GOCommand();
        player = Player.PLAYER;
        player.setCurrentLocation(Locations.LandingDock);
    }

    @Test
    public void processCommand_shouldChangeLocations_whenValidLocationGiven() {
        String oldLocation = player.getCurrentLocation().getName();
        goCommand.processCommand(gameBuilder,command,instructs);
        assertFalse(oldLocation.equalsIgnoreCase(player.getCurrentLocation().getName()));
    }

    @Test
    public void processCommand_shouldNotChangeLocations_whenInvalidLocationGiven() {
       command[1] = "invalid direction";
        String oldLocation = player.getCurrentLocation().getName();
        goCommand.processCommand(gameBuilder,command,instructs);
        assertEquals(oldLocation, player.getCurrentLocation().getName());
    }

    @Test
    public void processCommand_shouldNotChangeLocations_whenValidLocationSyntaxGiven_butDirectionIsDeadEnd() {
        command[1] = "north";
        player.setCurrentLocation(Locations.Bar);
        goCommand.processCommand(gameBuilder,command,instructs);
        assertEquals(player.getCurrentLocation(), Locations.Bar);
    }

    @Test
    public void processCommand_shouldChangeEast_whenGivenEastAndValidOnMap() {
        command[1] = "east";
        goCommand.processCommand(gameBuilder,command,instructs);
        assertEquals("Repair Workshop", player.getCurrentLocation().getName());
    }
    @Test
    public void processCommand_shouldChangeWest_whenGivenWestAndValidOnMap() {
        command[1] = "west";
        goCommand.processCommand(gameBuilder,command,instructs);
        assertEquals("Escape Shuttle", player.getCurrentLocation().getName());
    }

    @Test
    public void processCommand_shouldChangeSouth_whenGivenSouthAndValidOnMap() {
        command[1] = "south";
        goCommand.processCommand(gameBuilder,command,instructs);
        assertEquals("Ship", player.getCurrentLocation().getName());
    }


}