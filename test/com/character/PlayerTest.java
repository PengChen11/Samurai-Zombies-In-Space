package com.character;

import com.item.Item;
import com.item.Weapon;
import com.location.Locations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {
    Item item1, item2, item3;
    Player player;

    @Before
    public void setup() {
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
    public void checkInventoryPositive() {
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
    public void checkInventoryNegativeNullSetItem() {
        item1 = null;
        player.addToInventory(item1);
        item2 = new Item("spork", "Loading Dock");
        assertFalse(player.checkInventory(item2));
    }

    @Test
    public void checkInventoryNegativeDifferentItemNameSameLocation() {
        item1 = new Item("finger", "Loading Dock");
        item2 = new Item("spork", "Loading Dock");
        player.addToInventory(item1);
        assertFalse(player.checkInventory(item2));
    }

    @Test
    public void checkInventoryPositiveDifferentItemDifferentLocation() {
        item1 = new Item("finger", "Loading Dock");
        item2 = new Item("spork", "med bay");
        player.addToInventory(item1);
        assertFalse(player.checkInventory(item2));
    }

    @Test
    public void getInventoryPositiveTwoItems() {
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
    public void removeInventoryPositive() {
        item1 = new Item("finger", "Loading Dock");
        item2 = new Item("spork", "med bay");
        player.addToInventory(item1);
        player.addToInventory(item2);
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(item1);
        player.removeInventory(item2);

        assertEquals(inventory, player.getInventory());
    }

    @Test
    public void removeInventoryNegative() {
        item1 = new Item("finger", "Loading Dock");
        item2 = new Item("spork", "med bay");
        player.addToInventory(item1);
        player.removeInventory(item1);
        assertEquals(new ArrayList<>(), player.getInventory());
    }

    @Test
    public void getInventorySizeCorrectForExistingItems() {
        item1 = new Item("finger", "Loading Dock");
        item2 = new Item("spork", "med bay");
        player.setCurrentLocation(Locations.LandingDock);
        player.addToInventory(item1);
        player.setCurrentLocation(Locations.MedicalBay);
        player.addToInventory(item2);
        assertEquals((Integer) 2, player.getInventorySize());
    }

    @Test
    public void getInventorySizeCorrectAfterItemRemoval() {
        item1 = new Item("finger", "Loading Dock");
        item2 = new Item("spork", "med bay");
        player.addToInventory(item1);
        player.addToInventory(item2);
        player.removeInventory(item2);
        assertEquals((Integer) 1, player.getInventorySize());
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


}