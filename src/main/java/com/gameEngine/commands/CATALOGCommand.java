package com.gameEngine.commands;

import com.item.Item;
import com.location.Locations;

import java.util.List;
import java.util.Map;

public class CATALOGCommand implements CommandInterface{

    //todo: Do we really need to give player this info?? --Peng
    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {
        generateCatalog(gameBuilder);
    }

    private void generateCatalog(StringBuilder builder){
        builder.append("Here're the items locations: \n");
        for (Locations location : Locations.values()){
            List<Item> itemList = location.getItemList();
            builder.append("\n").append(location.getName()).append(" : ");

            for (int i = 0; i<location.getItemList().size(); i++) {
                builder.append(itemList.get(i).getName()).append(
                        itemList.size()>1 && i != itemList.size()-1  ?
                                ", " : "" );
            }
        }
    }
}
