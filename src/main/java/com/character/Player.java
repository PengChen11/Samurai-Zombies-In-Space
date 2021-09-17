package com.character;


import com.item.Item;
import com.item.Weapon;
import com.location.Locations;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public enum Player {
    PLAYER;

    List<Item> inventory;
    Integer health;
    Integer maxHealth = 20;
    boolean fightingZombie;
    Locations currentLocation;
    Integer strength;

    Player() {
        initPlayer();
    }

    public Integer getMaxHealth(){
        return maxHealth;
    }

    /**
     * Checks for presence of an item in inventory
     * @param item-
     * @return item's presence as boolean
     */
    public boolean checkInventory(Item item) {
        return this.inventory.contains(item);
    }

    /**
     * Checks for any item with a String name in inventory
     * @param name-
     * @return boolean
     */
    public boolean checkInventoryName(String name) {
        boolean returnVal = false;
        for (Item item : getInventory()) {
            if (name.equals(item.getName())) {
                returnVal = true;
                break;
            }
        }
        return returnVal;
    }

    /**
     * Provides a List of items in inventory
     * @return List<Item>
     */
    public List<Item> getInventory() {
        return inventory;
    }

    /**
     * Checks that player is in the same room as the item's location, adds to inventory, and returns success status
     * @param item
     * @return boolean
     */
    public boolean addToInventory(Item item) {
        if (item == null) {
            return false;
        }
        this.inventory.add(item);
        return true;
    }

    public void loadInventoryFromSavedGameData(List<Item> itemList){
        this.inventory = itemList;
    }

    // for testing, mostly
    public void clearInventory() {
        this.inventory = new ArrayList<>();
    }


    public boolean removeInventory(String itemName) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equals(itemName)) {
                inventory.remove(i);
                return true;
            }
        }
        return false;
    }

    public void dropToCurrentLocation(String itemName){
        Item item = null;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equals(itemName)) {
                item = inventory.get(i);
                inventory.remove(i);
                break;
            }
        }
        currentLocation.addItem(item);
    }

    /**
     * Get size of inventory
     * @return size of inventory
     */
    public Integer getInventorySize() {
        return this.inventory.size();
    }

    /**
     * Return current health level
     * @return health level
     */
    public Integer getHealth() {
        return health;
    }

    /**
     * Assigns new number to player health level
     * @param health
     */
    public void setHealth(Integer health) {
        this.health = health;
    }

    public void addToHealth(Integer addition){
        this.health = health + addition > maxHealth?
                20:health + addition;
    }

    public int attack(){
        return (int) (Math.random() * 5) + 1;
    }

    public void takeDamage(int damageTaken){
        int currentHp = getHealth() - damageTaken;
        setHealth(currentHp);
    }

    public Locations getCurrentLocation(){
        return this.currentLocation;
    }

    public void setCurrentLocation(Locations currentLocation){
        this.currentLocation = currentLocation;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public void updatePlayerFromSavedGameData(JSONObject playerData){
        Map<String, Locations> locationsMap = Locations.getEnumMap();
        HashMap<String, Weapon> weaponsMap = Weapon.weaponsMap;
        HashMap<String, Item> itemsMap = Item.itemsMap;

        this.setHealth((int)(long) playerData.get("health"));
        this.setCurrentLocation(locationsMap.get((String) playerData.get("currentLocation")));

        List<Item> newItemList = new ArrayList<>();
        JSONArray savedInventory = (JSONArray) playerData.get("inventory");

        for (Object itemObj : savedInventory ){
            String itemName = (String) itemObj;
            Weapon weaponInPlayerInventory = weaponsMap.get(itemName);
            if (weaponInPlayerInventory != null) {
                newItemList.add(weaponInPlayerInventory);
                continue;
            }
            Item itemInPlayerInventory = itemsMap.get(itemName);
            if (itemInPlayerInventory != null){
                newItemList.add(itemInPlayerInventory);
            }
        }

        this.loadInventoryFromSavedGameData(newItemList);
    }

    public void initPlayer(){
        this.inventory = new ArrayList<>();
        this.health = 20;
        this.fightingZombie = false;
        this.currentLocation = Locations.LandingDock;
        this.strength = 5;
    }
}