package com.location;


import com.item.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LocationsTest {
    Locations current;
    Item testItem = new Item("test");

    @Before
    public void setup() {
        current = Locations.CentralHub;
    }

    @After
    public void resetItemList(){
        if (current.getItemList().size() != 0){
            current.getItemList().clear();
        }
    }

    @Test
    public void locationCanSetDirection(){
        current.setNorth(Locations.LandingDock);
        assertEquals(Locations.LandingDock, current.getNorth());

    }

    @Test
    public void LocationCanAddItem(){
        current.addItem(testItem);
        assertEquals(testItem, current.getItemList().get(0));
    }

    @Test
    public void locationCanRemoveItem(){
        current.addItem(testItem);
        assertEquals(1, current.getItemList().size());
        current.removeItem(testItem);
        assertEquals(0, current.getItemList().size());
    }

}