package com.location;

import com.item.Item;
import com.item.Weapon;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

public class LocationInitFromJsonTest {

    @Before
    public void setup() throws Exception {
        // DO NOT parse from actual game location json file, use this tester instead
        Item.getItems("cfg/Items.json");
        Weapon.getWeapons("cfg/Weapons.json");
        Locations.initWithJsonFile("cfg/tests/sampleLocationTests(do_not_modify).json");
        Locations.Bar.setZombie(null);
    }

    @Test
    public void directionsShouldWork(){
        assertEquals(Locations.LandingDock.getNorth(), Locations.CentralHub);
        assertEquals(Locations.LandingDock.getWest(), Locations.EscapeShuttle);
        assertEquals(Locations.LandingDock.getEast(), Locations.RepairWorkshop);
        assertEquals(Locations.LandingDock.getSouth(), Locations.Ship);
        assertEquals(Locations.CentralHub.getEast(), Locations.Bar);
        assertEquals(Locations.CentralHub.getWest(), Locations.MedicalBay);
        assertEquals(Locations.CentralHub.getSouth(), Locations.LandingDock);
    }

    @Test
    public void nullDirectionShouldWork(){
        assertNull(Locations.CentralHub.getNorth());
        assertNull(Locations.RepairWorkshop.getNorth());
        assertNull(Locations.Bar.getSouth());
    }

    @Test
    public void npcShouldWork(){
        assertEquals("guard", Locations.LandingDock.getNpc().getName());
        assertEquals("engineer", Locations.RepairWorkshop.getNpc().getName());
    }

    @Test
    public void itemsArrayShouldWork(){
        assertEquals(2, Locations.RepairWorkshop.getItemList().size());
        assertEquals(2, Locations.CentralHub.getItemList().size());
        System.out.println(Locations.CentralHub.getItemList().size());
    }

    @Test
    public void zombieShouldWork(){
        assertNotNull(Locations.LandingDock.getZombie());
    }

    @Test
    public void shouldBeAbleToTurnNPCToZombie(){
        assertNotNull(Locations.Bar.getNpc());
        assertNull(Locations.Bar.getZombie());
        Locations.turnNpcToZombie(Locations.Bar);
        assertNull(Locations.Bar.getNpc());
        assertNotNull(Locations.Bar.getZombie());
    }

    @Test
    public void shouldBeAbleToLoadFromSavedGameData() throws Exception {
        Object gameData = new JSONParser().parse(new FileReader("cfg/tests/sampleSavedLocationsData.json"));
        JSONObject gameDataObj = (JSONObject) gameData;
        JSONObject locationsDataObj = (JSONObject) gameDataObj.get("locations");
        Locations.updateLocationsFromSavedGameData(locationsDataObj);
        //After load the game data, landing Dock state updated.
        // Landing Dock had a zombie before, now it's gone.
        assertNull(Locations.LandingDock.getZombie());
        // landing Dock should have a list of items
        assertEquals(Locations.LandingDock.getItemList().get(0).getName(), "lever");
        //Bar should have a zombie now
        assertNotNull(Locations.Bar.getZombie());
    }
}