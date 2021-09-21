package com.item;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class Item {
    String name;
    String description;
    public static HashMap<String, Item> itemsMap;

    public Item(String name) {
        this.name = name;
    }


    public Item(String name, String description) throws IOException {
        this.name = name;
        this.description = description;
    }

    // read Items.json file and create Hashmap for items. key is name, value is item object.
    public static void getItems(String filePath) throws IOException {
        HashMap<String, Item> items = new HashMap<>();
        JSONParser parser = new JSONParser();
        if (itemsMap != null) {
            return;
        }
        try {
            JSONArray itemSet = (JSONArray) parser.parse(new FileReader(filePath));
            for (int i = 0; i < itemSet.size(); i++) {
                JSONObject item = (JSONObject) itemSet.get(i);
                String name = item.get("name").toString();
                String description = item.get("description").toString();
                items.put(name, new Item(name, description));
            }
        } catch (FileNotFoundException | ParseException e) {
            throw new IOException();
        }
        if (!items.isEmpty()) {
            itemsMap = items;
        }
    }

    /**
     *
     * @return catalog hashmap
     */
   /* public static HashMap<String, Item> readAll() {
        JSONParser parser = new JSONParser();

        HashMap<String, Item> catalog = new HashMap<>();
        try {
            JSONObject characterSet = (JSONObject) parser.parse(new FileReader("cfg/Locations.json"));
            JSONObject itemSet = (JSONObject) parser.parse(new FileReader("cfg/OldItems.json"));
            for (Object room : characterSet.keySet()) {
                JSONObject roomObj = (JSONObject) characterSet.get(room);
                JSONArray itemArr = (JSONArray) roomObj.get("Item");
                if (itemArr != null) {
                    for (Object item : itemArr) {
                        String description =  itemSet.get(item.toString()).toString();
                        catalog.put(item.toString(), new Item(item.toString(), room.toString()));
                        catalog.get(item.toString()).setDescription(description);
                    }
                }
            }

        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(catalog.keySet());
        return catalog;
    }*/

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    static void resetItemMap(){
        itemsMap = null;
    }
}