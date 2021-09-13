package com.location;


import com.item.Item;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LocationsTest {
    Locations current;

    @Before
    public void setup() {
        current = Locations.CentralHub;
    }

    @Test
    public void locationCanSetDirection(){
        current.setNorth(Locations.LandingDock);
        assertEquals(Locations.LandingDock, current.getNorth());

    }

    @Test
    public void LocationCanAddItem(){
        Item testItem = new Item("test");
        current.addItem(testItem);
        assertEquals(testItem, current.getItemList().get(0));
    }

    @Test
    public void locationCanRemoveItem(){
        Item testItem = new Item("test");
        current.addItem(testItem);
        assertEquals(1, current.getItemList().size());
        current.removeItem(testItem);
        assertEquals(0, current.getItemList().size());
    }

    @Test
    public void locationShouldBeAbleToInitFromJSON() throws Exception {
        Locations.initWithJsonFile("cfg/sampleLocationTests(do_not_modify).json");
    }

}