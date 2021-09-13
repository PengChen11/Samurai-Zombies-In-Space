package com.engine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameEngineTest {


    @Before
    public void setUp() {
        GameEngine.getInstance().setCurrentLocation("Landing Dock");
    }


    @Test
    public void runGameLoop_shouldHaveZombieText_whenLookingInTheLandingDock() {
        StringBuilder testBuilder = GameEngine.getInstance().runGameLoop("look around");
        assertTrue(testBuilder.toString().contains("Zomburai"));
        // if the game engine gamebuilder (after meethod call) contains output from game screen
    }


    @Test
    public void runGameLoop_shouldNotHaveZombieText_whenLookingInNonZombieLocation() {
        GameEngine.getInstance().setCurrentLocation("Central Hub");
        StringBuilder testBuilder = GameEngine.getInstance().runGameLoop("look around");
        assertFalse(testBuilder.toString().contains("Zomburai") && !testBuilder.toString().contains("Central Hub"));

    }
    @Test
    public void runGameLoop_shouldGiveDescription_whenGivenLook() {
        StringBuilder testBuilder = GameEngine.getInstance().runGameLoop("look");
        assertTrue(testBuilder.toString().contains(GameEngine.getInstance().currentLocation));
    }
    @Test
    public void runGameLoop_shouldGiveErrorMessage_whenGivenInvalidLookCommand() {
        StringBuilder testBuilder = GameEngine.getInstance().runGameLoop("look GARBAGE_INPUT");
        assertTrue(testBuilder.toString().contains("don't see"));
    }
    @Test
    public void runGameLoop_shouldShowFight_whenGivenFight() {
        StringBuilder testBuilder = GameEngine.getInstance().runGameLoop("fight");
        assertTrue(testBuilder.toString().contains("attacking"));
    }
    

    @Test
    public void runGameLoop_shouldNotShowFight_whenGivenFightInABadArea() {
        GameEngine.getInstance().setCurrentLocation("Central Hub");
        StringBuilder testBuilder = GameEngine.getInstance().runGameLoop("fight");
        assertFalse(testBuilder.toString().contains("Zomburai"));
    }

    @Test
    public void runGameLoop_shouldShowGettingItem_whenGivenGetCommand() {
        GameEngine.getInstance().setCurrentLocation("Central Hub");
        StringBuilder testBuilder = GameEngine.getInstance().runGameLoop("get zombie armor");
        assertTrue(testBuilder.toString().contains("zombie armor"));
    }

    @Test
    public void runGameLoop_shouldNotLetYouPickUpItemsTwice_whenGivenGetCommandTwice() {
        GameEngine.getInstance().setCurrentLocation("Central Hub");
        StringBuilder testBuilder = GameEngine.getInstance().runGameLoop("get zombie armor");
        StringBuilder testBuilder2 = GameEngine.getInstance().runGameLoop("get zombie armor");
        assertNotEquals(testBuilder.toString(), testBuilder2.toString());
    }

    @Test
    public void runGameLoop_shouldShowDroppingItem_whenGivenDropCommand() {
        GameEngine.getInstance().setCurrentLocation("Central Hub");
        GameEngine.getInstance().runGameLoop("get zombie armor");
        StringBuilder testBuilder = GameEngine.getInstance().runGameLoop("drop zombie armor");
        assertTrue(testBuilder.toString().contains("drop"));
    }

    @Test
    public void runGameLoop_shouldNotShowDroppingItem_whenGivenInvalidDropCommand() {
        GameEngine.getInstance().setCurrentLocation("Central Hub");
        GameEngine.getInstance().runGameLoop("get zombie armor");
        StringBuilder testBuilder = GameEngine.getInstance().runGameLoop("drop zombie armor");
        StringBuilder testBuilder2 = GameEngine.getInstance().runGameLoop("drop zombie armor");
        assertNotEquals(testBuilder.toString(), testBuilder2.toString());
    }

    @Test
    public void runGameLoop_shouldShowHelpCommands_whenGivenHelpCommand() {
        StringBuilder testBuilder = GameEngine.getInstance().runGameLoop("help");
        assertTrue(testBuilder.toString().contains("look"));
    }@Test
    public void runGameLoop_shouldChangeLocation_whenGivenGoCommand() {
        StringBuilder testBuilder =  GameEngine.getInstance().runGameLoop("go north");
        assertTrue(testBuilder.toString().contains(GameEngine.getInstance().currentLocation));
    }

    @Test
    public void runGameLoop_shouldShowItems_whenInventoryIsGiven() {
        StringBuilder testBuilder = GameEngine.getInstance().runGameLoop("inventory");
        assertTrue(testBuilder.toString().contains("Items"));
    }

    @Test
    public void runGameLoop_shouldShowEveryItem_whenInventoryIsGiven() {
        StringBuilder testBuilder = GameEngine.getInstance().runGameLoop("catalog");
        assertTrue(testBuilder.toString().contains("Items : [wizzlewhat (in Repair Workshop) "));
    }


}