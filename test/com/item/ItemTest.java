package com.item;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ItemTest {

    @Before
    public void resetItemMap (){
        Item.resetItemMap();
    }

    @Test
    public void whenOfferRightFileToGetItems_shouldReturnHashMapWithEntries() throws IOException {
        String validFilePath = "cfg/Items.json";
        Item.getItems(validFilePath);
        HashMap<String, Item> itemsMap = Item.itemsMap;
        assertEquals(7, itemsMap.size());
    }

    @Test(expected = IOException.class)
    public void testGetItemsIOException() throws IOException {
        String invalidFilePath = "abc/Items.json";
        Item.getItems(invalidFilePath);
        HashMap<String, Item> itemsMap = Item.itemsMap;
    }
}