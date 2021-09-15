package com.gameEngine.commands;

import com.character.NPC;
import com.character.Player;
import com.character.Zombie;
import com.controller.GameSceneControllerNew;
import com.sound.*;


import java.text.DecimalFormat;
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
            String voices=npc.getDialogue();
            gameBuilder.append(voices);

            //TranslateToSpeech.textToVoice(voices);
            if(true){
                //turn the npc into a zombie
                Player.PLAYER.getCurrentLocation().setZombie(new Zombie(5, Player.PLAYER.getCurrentLocation().getName()));
                gameBuilder.append(instructs.get("talk").get("instructions").get(0) + npc.getName());
                Player.PLAYER.getCurrentLocation().setNpc(null);
                //stop the background music
                ((Background)(GameSceneControllerNew.getBackground())).pauseMusic();
                SoundFactory.createSound(SoundType.BITE).startMusic(GameSceneControllerNew.currentVolume);
                SoundFactory.createSound(SoundType.TURN).startMusic(GameSceneControllerNew.currentVolume);
                if(!((Bite)(SoundFactory.createSound(SoundType.BITE))).getBiteClip().isPlaying() && !((Turn)(SoundFactory.createSound(SoundType.TURN))).getTurnClip().isPlaying()){
                    GameSceneControllerNew.getBackground().startMusic(GameSceneControllerNew.currentVolume);
                }

            }
        } else{
            gameBuilder.append("Sorry Dave, you can't talk to some one not in the room.");
        }
    }
}
