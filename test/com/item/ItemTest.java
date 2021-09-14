package com.item;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ItemTest {

    @Test
    public void whenOfferRightFileToGetItems_shouldReturnHashMapWithEntries() throws IOException {
        String validFilePath = "cfg/Items.json";
        HashMap<String, Item> itemsMap = Item.getItems(validFilePath);
        assertEquals(6, itemsMap.size());
    }

    @Test(expected = IOException.class)
    public void testGetItemsIOException() throws IOException {
        String invalidFilePath = "abc/Items.json";
        HashMap<String, Item> itemsMap = Item.getItems(invalidFilePath);
    }
}