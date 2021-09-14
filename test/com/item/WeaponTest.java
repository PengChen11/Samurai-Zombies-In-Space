package com.item;

import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;
import static org.junit.Assert.*;

public class WeaponTest {

    @Test
    public void whenOfferValidFileToGetWeapons_shouldReturnHashMapWithEntries() throws IOException {
        String validFilePath = "cfg/Weapons.json";
        Weapon.getWeapons(validFilePath);
        HashMap<String, Weapon> weaponsMap = Weapon.weaponsMap;
        assertEquals(3, weaponsMap.size());
    }

    @Test(expected = IOException.class)
    public void testGetWeaponsIOException() throws IOException {
        String invalidFilePath = "abc/Weapons.json";
        Weapon.getWeapons(invalidFilePath);
        HashMap<String, Weapon> weaponsMap = Weapon.weaponsMap;
    }
}