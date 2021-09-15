package com.item;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Weapon extends Item {
    // name, location, and description fields inherited from Item class
    private int attackPower;

    public static HashMap<String, Weapon> weaponsMap;

    // constructor
    public Weapon(String name, String location, String description, int attackPower) throws IOException {
        super(name, location, description);
        this.attackPower = attackPower;
    }

    // read Weapons.json file and create Hashmap for weapons. key is name, value is weapon object.
    public static void getWeapons(String filePath) throws IOException {
        HashMap<String, Weapon> weapons = new HashMap<>();
        JSONParser parser = new JSONParser();
        if (weaponsMap != null) {
            return;
        }
        try {
            JSONArray weaponsSet = (JSONArray) parser.parse(new FileReader(filePath));
            for (int i = 0; i < weaponsSet.size(); i++) {
                JSONObject weapon = (JSONObject) weaponsSet.get(i);
                String name = weapon.get("name").toString();
                String location = weapon.get("location").toString();
                String description = weapon.get("description").toString();
                int attackPower = (int) ((long) weapon.get("attack power"));
                weapons.put(name, new Weapon(name, location, description, attackPower));
            }
        } catch (FileNotFoundException | ParseException e) {
            throw new IOException();
        }
        System.out.println(weapons);
        if (!weapons.isEmpty()) {
            weaponsMap = weapons;
        }
    }

    //Getter and setter inherited from Item class

    @Override
    public String toString() {
        return "Weapon{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", attackPower=" + attackPower +
                '}';
    }

    static void resetWeaponMap(){
        weaponsMap = null;
    }
}