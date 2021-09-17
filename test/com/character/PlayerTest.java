package com.character;

import com.item.Item;
import com.item.Weapon;
import com.location.Locations;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {
    Item item1, item2, item3;
    Player player;

    @Before
    public void setup() throws Exception {
        Item.getItems("cfg/Items.json");
        Weapon.getWeapons("cfg/Weapons.json");
        Locations.initWithJsonFile("cfg/sampleLocations.json");
        player = Player.PLAYER;
    }

    @After
    public void teardown() {
        // Reset defaults on enum
        player.setCurrentLocation(Locations.LandingDock);
        player.clearInventory();
        player.setHealth(20);
    }

    @Test
    public void getHealthDefault() {
        assertEquals((Integer) 20, player.getHealth());
    }

    @Test
    public void getLocationDefault() {
        assertEquals(Locations.LandingDock, player.getCurrentLocation());
    }

    @Test
    public void checkInventoryPositive() throws IOException {
        item1 = new Item("spork", "Loading Dock");
        item2 = new Item("shovel", "med bay");

        player.setCurrentLocation(Locations.LandingDock);
        assertTrue(player.addToInventory(item1));
        // github action tests
//        assertFalse(player.addToInventory(item1));

        player.setCurrentLocation(Locations.MedicalBay);
        assertTrue(player.addToInventory(item2));

        assertTrue(player.checkInventory(item1));
        assertTrue(player.checkInventory(item2));
    }

    @Test
    public void checkInventoryPositiveNullItem() {
        assertFalse(player.addToInventory(null));
    }

    @Test
    public void checkInventoryNegativeNullSetItem() throws IOException {
        item1 = null;
        player.addToInventory(item1);
        item2 = new Item("spork", "Loading Dock");
        assertFalse(player.checkInventory(item2));
    }

    @Test
    public void checkInventoryNegativeDifferentItemNameSameLocation() throws IOException {
        item1 = new Item("finger", "Loading Dock");
        item2 = new Item("spork", "Loading Dock");
        player.addToInventory(item1);
        assertFalse(player.checkInventory(item2));
    }

    @Test
    public void checkInventoryPositiveDifferentItemDifferentLocation() throws IOException {
        item1 = new Item("finger", "Loading Dock");
        item2 = new Item("spork", "med bay");
        player.addToInventory(item1);
        assertFalse(player.checkInventory(item2));
    }

    @Test
    public void getInventoryPositiveTwoItems() throws IOException {
        item1 = new Item("finger", "Loading Dock");
        item2 = new Item("spork", "med bay");
        player.setCurrentLocation(Locations.LandingDock);
        player.addToInventory(item1);
        player.setCurrentLocation(Locations.MedicalBay);
        player.addToInventory(item2);
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(item1);
        inventory.add(item2);

        assertEquals(inventory, player.getInventory());

    }

    @Test
    public void getInventorySizeCorrectForExistingItems() throws IOException {
        item1 = new Item("finger", "Loading Dock");
        item2 = new Item("spork", "med bay");
        player.setCurrentLocation(Locations.LandingDock);
        player.addToInventory(item1);
        player.setCurrentLocation(Locations.MedicalBay);
        player.addToInventory(item2);
        assertEquals((Integer) 2, player.getInventorySize());
    }

    @Test
    public void getHealth() {
        player.setHealth(2);
        assertEquals((Integer) 2, player.getHealth());
    }

    @Test
    public void getHealthChanged() {
        player.setHealth(2);
        player.setHealth(10);
        assertEquals((Integer) 10, player.getHealth());
    }

    @Test
    public void getHealthZero() {
        player.setHealth(0);
        assertEquals((Integer) 0, player.getHealth());
    }

    @Test
    public void getLocationChanged() {
        player.setCurrentLocation(Locations.MedicalBay);
        assertEquals(Locations.MedicalBay, player.getCurrentLocation());
    }

    @Test
    public void updatePlayerFromSavedGameDataShouldWork() throws Exception {
        Object gameData = new JSONParser().parse(new FileReader("cfg/tests/sampleSavedGameDataForTest(Do_NOT_Modify)" +
                ".json"));
        JSONObject gameDataObj = (JSONObject) gameData;
        JSONObject playerDataObj = (JSONObject) gameDataObj.get("player");
        Player.PLAYER.updatePlayerFromSavedGameData(playerDataObj);

        // Player location should change
        assertEquals(Locations.Bar, Player.PLAYER.getCurrentLocation());
        // Player HP should be updated
        assertEquals(Integer.valueOf(10), Player.PLAYER.getHealth());
        // Player inventory should be updated
        assertEquals("zombie armor", Player.PLAYER.getInventory().get(0).getName());
    }


}