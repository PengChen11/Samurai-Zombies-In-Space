package com.character;


import com.item.Item;
import com.location.Locations;

import java.util.ArrayList;
import java.util.List;

/**
 * A singleton class to represent the single player in the game.
 * property: ArrayList of inventory items
 * property: Integer representing level of health
 * property: String representing current location of the player
 */
public enum Player {
    PLAYER;

    List<Item> inventory;
    Integer health;
//    String location;  // until the locations are implemented
    boolean fightingZombie;
    Integer zombiesFollowing;
    List<String> areasVisited;
    Locations currentLocation;
    Integer strength;



    /**
     * Constructor
     */
    Player() {
        this.inventory = new ArrayList<>();
        this.health = 20;
//        this.location = "Landing Dock";
        this.fightingZombie = false;
        this.zombiesFollowing = 0;
        this.areasVisited = new ArrayList<>();
        this.currentLocation = Locations.LandingDock;
        this.strength = 5;
    }

    /**
     * Checks for presence of an item in inventory
     * @param item
     * @return item's presence as boolean
     */
    public boolean checkInventory(Item item) {
        return this.inventory.contains(item);
    }

    /**
     * Checks for any item with a String name in inventory
     * @param name
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

    public boolean checkAreasVisited(String location){
        boolean returnVal = false;
        for (String area : getAreasVisited()) {
            if (area.equals(location)) {
                returnVal = true;
                break;
            }
        }
        return returnVal;
    }

    public List<String> getAreasVisited() {
        return areasVisited;
    }

    public boolean addAreasVisited(String location) {
        if (!checkAreasVisited(location)) {
            this.areasVisited.add(location);
        }
        return false;
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

    // for testing, mostly
    public void clearInventory() {
        this.inventory = new ArrayList<>();
    }

    /**
     * Remove an item from inventory
     * @param item
     * @return String location of item
     */
    public String removeInventory(Item item) {
        this.inventory.remove(item);
        return item.getLocation();
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



    public void setFightingZombie(boolean fighting){
        this.fightingZombie = fighting;
    }

    public boolean getFightingZombie() {
        return fightingZombie;
    }

    public int attack(){
        return (int) (Math.random() * 5) + 1;
    }

    public Integer getZombiesFollowing() {
        return zombiesFollowing;
    }

    public void addZombiesFollowing() {
        this.zombiesFollowing = zombiesFollowing += 1;
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
}