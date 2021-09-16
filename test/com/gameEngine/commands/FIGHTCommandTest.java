package com.gameEngine.commands;

import com.character.Player;
import com.character.Zombie;
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

public class FIGHTCommandTest {

    private StringBuilder gameBuilder ;
    private String[] command ;
    private Map<String, Map<String, List<String>>> instructs;
    private CommandInterface fightCommand;
    private Player player;
    @Before
    public void setUp() throws Exception {
        Item.getItems("cfg/Items.json");
        Weapon.getWeapons("cfg/Weapons.json");
        Locations.initWithJsonFile("cfg/sampleLocations.json");
        gameBuilder = new StringBuilder();
        command = new String[2];
        command[0] = "fight";
        instructs= GameEngine.GAME_ENGINE.getInstructs();
        fightCommand = new FIGHTCommand();
        player = Player.PLAYER;
        Locations.LandingDock.setZombie(Zombie.getInstance());
        player.setCurrentLocation(Locations.LandingDock);
        player.setHealth(10000);
    }

    @Test
    public void processFight_shouldChangeHealthOfZombieAndPlayer_whenCalledAndZombiePresent() throws Exception {
        int oldPlayerHealth = player.getHealth();
        fightCommand.processCommand(gameBuilder,command,instructs);
        assertTrue(player.getHealth() < oldPlayerHealth);
    }
    @Test
    public void processFight_shouldChangeHealthOfZombieAndPlayer_whenCalledAndZombiePresentPlusCommandOneIsZombie() throws Exception {
        command[1] = "zombie";
        int oldPlayerHealth = Player.PLAYER.getHealth();
        fightCommand.processCommand(gameBuilder,command,instructs);
        assertTrue(Player.PLAYER.getHealth() < oldPlayerHealth);


    }
    @Test
    public void processFight_shouldNotChangeHealthOfZombieAndPlayer_whenCalledAndZombieNotPresent() throws Exception {
        int oldPlayerHealth = Player.PLAYER.getHealth();
        int oldZombieHealth = Player.PLAYER.getCurrentLocation().getZombie().getHealth();
        Player.PLAYER.setCurrentLocation(Locations.Bar);
        fightCommand.processCommand(gameBuilder,command,instructs);
        assertEquals((int) Player.PLAYER.getHealth(), oldPlayerHealth);

    }

    @Test
    public void processFight_shouldGiveFeedBackThroughGameBuilder_whenItDoesNotPerformAttack() throws Exception {
        Locations.LandingDock.setName("Central Hub");

        fightCommand.processCommand(gameBuilder,command,instructs);
        assertTrue(gameBuilder.toString().contains(instructs.get("fight").get("instructions").get(9)));
    }

    @Test
    public void processFight_shouldNullifyZombieAtLocation_whenZombieKilled() throws Exception {
        Locations.LandingDock.setName("Landing Dock");
        fightCommand.processCommand(gameBuilder,command,instructs);
        assertNull(Player.PLAYER.getCurrentLocation().getZombie());
    }

}