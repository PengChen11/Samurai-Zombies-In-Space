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

public class DROPCommandTest {
    private StringBuilder gameBuilder ;
    private String[] command ;
    private Map<String, Map<String, List<String>>> instructs;
    private CommandInterface dropCommand;
    private Item item;
    private Player player;
    @Before
    public void setUp() throws Exception {
        Item.getItems("cfg/Items.json");
        Weapon.getWeapons("cfg/Weapons.json");
        Locations.initWithJsonFile("cfg/sampleLocations.json");
        gameBuilder = new StringBuilder();
        command = new String[2];
        command[0] = "drop";
        command[1] = "zombie armor";

        instructs= GameEngine.GAME_ENGINE.getInstructs();
        dropCommand = new DROPCommand();
        player = Player.PLAYER;
        player.setCurrentLocation(Locations.CentralHub);

    }

    @Test
    public void processCommand_shouldNotRemoveFromInventory_whenItemNotPresent() {

        dropCommand.processCommand(gameBuilder,command,instructs);
        assertTrue(gameBuilder.toString().contains(instructs.get("drop").get("instructions").get(0)));
    }

    @Test
    public void processCommand_shouldRemoveFromInventory_whenItemPresent() {
        player.addToInventory(Locations.CentralHub.getItemList().get(0));
        dropCommand.processCommand(gameBuilder,command,instructs);
        assertTrue(gameBuilder.toString().contains(instructs.get("drop").get("instructions").get(1)));
    }
}