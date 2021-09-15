package com.gameEngine.commands;

import com.character.NPC;
import com.character.Player;
import com.gameEngine.GameEngine;
import com.item.Item;
import com.item.Weapon;
import com.location.Locations;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TALKCommandTest {
    private StringBuilder gameBuilder ;
    private String[] command ;
    private Map<String, Map<String, List<String>>> instructs;
    private TALKCommand talkCommand;
    private Player player;

    @Before
    public void setUp() throws Exception {
        Item.getItems("cfg/Items.json");
        Weapon.getWeapons("cfg/Weapons.json");
        Locations.initWithJsonFile("cfg/sampleLocations.json");
        player = Player.PLAYER;
        gameBuilder = new StringBuilder();
        command = new String[2];
        command[0] = "talk";
        instructs= GameEngine.GAME_ENGINE.getInstructs();
        talkCommand = new TALKCommand();
    }

    @Test
    public void processCommand_shouldUpdateGameBuilder_whenPlayerInRoomWithoutNPC() {
        talkCommand.processCommand(gameBuilder, command, instructs);
        assertTrue(gameBuilder.toString().contains("Sorry Dave, you can't talk to some one not in the room."));
    }

    @Test
    public void processCommand_shouldUpdateGameBuilder_whenPlayerInRoomWithNPC() {
        player.setCurrentLocation(Locations.Bar);
        command[1] = "bartender";
        talkCommand.processCommand(gameBuilder, command, instructs);
        assertTrue(gameBuilder.toString().contains("She takes a drag on her digi-cig and pretends like it does anything for her."));
    }

    @Test
    public void randomlyConvertToZombie_shouldAddZombieToCurrentLocations_whenExecuted() {
        player.setCurrentLocation(Locations.Bar);
        command[1] = "bartender";
        NPC npc = Player.PLAYER.getCurrentLocation().getNpc();
        talkCommand.randomlyConvertToZombie(gameBuilder, instructs, npc, 3);
        assertTrue(player.getCurrentLocation().getZombie() != null);
        assertTrue(player.getCurrentLocation().getNpc() == null);
    }

    // Background music will not be tested here.
}