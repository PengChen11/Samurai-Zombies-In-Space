package com.location;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationInitFromJsonTest {

    @Before
    public void setup() throws Exception {
        // DO NOT parse from actual game location json file, use this tester instead
        Locations.initWithJsonFile("cfg/sampleLocationTests(do_not_modify).json");
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
}