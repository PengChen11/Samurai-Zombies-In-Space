package com.gameEngine.commands;

import com.character.NPC;
import com.character.Player;

import java.util.List;
import java.util.Map;

public class TALKCommand implements CommandInterface{

    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {
        NPC npc = Player.PLAYER.getCurrentLocation().getNpc();
        String targetNpc = command[1];

        if (npc != null && npc.getName().equalsIgnoreCase(targetNpc)){
            gameBuilder.append(npc.getDialogue());
        } else{
            gameBuilder.append("Sorry Dave, you can't talk to some one not in the room.");
        }
    }
}
