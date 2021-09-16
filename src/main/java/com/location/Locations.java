package com.location;

import com.character.NPC;
import com.character.Player;
import com.character.Zombie;
import com.item.Item;
import com.item.Weapon;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.*;

public enum Locations {
    CentralHub,
    LandingDock,
    Bar,
    RepairWorkshop,
    MedicalBay,
    EscapeShuttle,
    Ship;

    private String name;
    private Locations north;
    private Locations west;
    private Locations east;
    private Locations south;
    private String description;
    private NPC npc;
    private List<Item> itemList = new ArrayList<>();
    private Zombie zombie;
//    private boolean isCurrentLocation = false;

    // I don't need any constructors. the default constructors set everything to null which is needed.

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void setNorth(Locations north){
        this.north = north;
    }

    public Locations getNorth(){
        return this.north;
    }

    public Locations getWest() {
        return west;
    }

    void setWest(Locations west) {
        this.west = west;
    }

    public Locations getEast() {
        return east;
    }

    void setEast(Locations east) {
        this.east = east;
    }

    public Locations getSouth() {
        return south;
    }

    void setSouth(Locations south) {
        this.south = south;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public NPC getNpc() {
        return npc;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public boolean addItem(Item newItem) {
        boolean alreadyExist = false;

        for (Item thing : itemList){
            if (thing.getName().equalsIgnoreCase(newItem.getName())){
                alreadyExist = true;
                break;
            }
        }

        if (alreadyExist){
            return false;
        }else {
            this.itemList.add(newItem);
            return true;
        }
    }

    public void removeItem(Item item) {
        this.itemList.remove(item);
    }

    public Zombie getZombie() {
        return zombie;
    }

    public void setZombie(Zombie zombie) {
        this.zombie = zombie;
    }

//    public boolean isCurrentLocation() {
//        return isCurrentLocation;
//    }
//
//    public void setCurrentLocation(boolean currentLocation) {
//        isCurrentLocation = currentLocation;
//    }

    public static void initWithJsonFile(String path) throws Exception{
        Map<String, Locations> enumMap = getEnumMap();

        Object locationsArrayObj = new JSONParser().parse(new FileReader(path));
        JSONArray locationsObjArray = (JSONArray) locationsArrayObj;

        for (Object locationObj : locationsObjArray){
            JSONObject locationJO = (JSONObject) locationObj;
            String name = (String) locationJO.get("name");

            Locations target = enumMap.get(name);

            // do NOT remove the String down cast
            target.setName((String) locationJO.get("name"));
            target.setNorth(enumMap.get((String) locationJO.get("north")));
            target.setWest(enumMap.get((String) locationJO.get("west")));
            target.setEast(enumMap.get((String) locationJO.get("east")));
            target.setSouth(enumMap.get((String) locationJO.get("south")));
            target.setDescription((String) locationJO.get("description"));
//            // todo: do I really need to track current location here?
//            target.setCurrentLocation((boolean) locationJO.get("isCurrent"));

            // setting up NPC

            if (locationJO.get("NPC") != null){
                String npcName = (String) locationJO.get("NPC");
                target.setNpc(new NPC(npcName));
            }

            // setting up items
            if (locationJO.get("items") != null){
                HashMap<String, Weapon> weaponsMap = Weapon.weaponsMap;
                HashMap<String, Item> itemsMap = Item.itemsMap;
                JSONArray itemsObjArray = (JSONArray) locationJO.get("items");
                for (Object itemObj : itemsObjArray){
                    String itemName = (String) itemObj;
                    Weapon weaponInLocation = weaponsMap.get(itemName);
                    if (weaponInLocation != null) {
                        target.addItem(weaponInLocation);
                        continue;
                    }
                    Item itemInLocation = itemsMap.get(itemName);
                    if (itemInLocation != null){
                        target.addItem(itemInLocation);
                    }
                }
            }

            // setting up zombie
            if (locationJO.get("enemy") != null){
                target.setZombie(Zombie.getInstance());
            }

        }
    }


    public static Map<String, Locations> getEnumMap(){
        Map<String, Locations> locationsMap = new HashMap<>();
        locationsMap.put("Central Hub", Locations.CentralHub);
        locationsMap.put("Landing Dock", Locations.LandingDock);
        locationsMap.put("Repair Workshop", Locations.RepairWorkshop);
        locationsMap.put("Bar", Locations.Bar);
        locationsMap.put("Medical Bay", Locations.MedicalBay);
        locationsMap.put("Escape Shuttle", Locations.EscapeShuttle);
        locationsMap.put("Ship", Locations.Ship);
        return locationsMap;
    }

    public static void turnNpcToZombie(Locations location){
        if (location.getNpc() != null && location.getZombie() == null){
            location.setNpc(null);
            location.setZombie(Zombie.getInstance());
        }
    }

    public Item containsItem(String itemName){
        if (this.getItemList().size() == 0){
            return null;
        }

        for (Item item : this.getItemList()){
            if (itemName.equalsIgnoreCase(item.getName())){
                return item;
            }
        }

        return null;
    }

    public static void updateLocationsFromSavedGameData(JSONObject locationsData){
        HashMap<String, Weapon> weaponsMap = Weapon.weaponsMap;
        HashMap<String, Item> itemsMap = Item.itemsMap;

        for (Locations location : Locations.values()){

            JSONObject locationObj = (JSONObject) locationsData.get(location.getName());

            if (locationObj != null){
                // update zombie
                Zombie zombie = "yes".equalsIgnoreCase((String) locationObj.get("zombie"))?
                        Zombie.getInstance() : null;
                location.setZombie(zombie);

                // update NPC
                if (locationObj.get("npc") != null){
                    String npcName = (String) locationObj.get("npc");
                    location.setNpc(new NPC(npcName));
                } else {
                    location.setNpc(null);
                }

                // update itemList
                List<Item> newItemList = new ArrayList<>();
                JSONArray savedItemList = (JSONArray) locationObj.get("itemList");

                for (Object itemObj : savedItemList ){
                    String itemName = (String) itemObj;
                    Weapon weaponInLocation = weaponsMap.get(itemName);
                    if (weaponInLocation != null) {
                        newItemList.add(weaponInLocation);
                        continue;
                    }
                    Item itemInLocation = itemsMap.get(itemName);
                    if (itemInLocation != null){
                        newItemList.add(itemInLocation);
                    }
                }
                location.loadItemListFromSavedGameData(newItemList);
            }
        }
    }

    private void loadItemListFromSavedGameData(List<Item> items){
        this.itemList = items;
    }
}
