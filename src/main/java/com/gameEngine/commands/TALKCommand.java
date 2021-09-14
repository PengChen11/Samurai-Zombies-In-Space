package com.gameEngine.commands;

import com.character.NPC;
import com.character.Player;
import com.character.Zombie;
import com.gameEngine.GameEngine;
import com.location.Locations;
import com.sound.SoundFX;
import com.sound.SoundFactory;
import com.sound.SoundType;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class TALKCommand implements CommandInterface{

    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {
        NPC npc = Player.PLAYER.getCurrentLocation().getNpc();
        String targetNpc = command[1];
        Random rand = new Random();
        if (npc != null && npc.getName().equalsIgnoreCase(targetNpc)){
            gameBuilder.append(npc.getDialogue());
            if(rand.nextInt()%2 == 1){
                //turn the npc into a zombie
                Player.PLAYER.getCurrentLocation().setZombie(new Zombie(5, Player.PLAYER.getCurrentLocation().getName()));
                gameBuilder.append(instructs.get("talk").get("instructions").get(0) + npc.getName());
                Player.PLAYER.getCurrentLocation().setNpc(null);
                SoundFactory.createSound(SoundType.BITE).startMusic();
                SoundFactory.createSound(SoundType.TURN).startMusic();

            }
        } else{
            gameBuilder.append("Sorry Dave, you can't talk to some one not in the room.");
        }
    }
}
