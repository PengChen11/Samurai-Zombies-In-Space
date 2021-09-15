package com.gameEngine.commands;

import com.character.NPC;
import com.character.Player;
import com.character.Zombie;
import com.controller.GameSceneControllerNew;
import com.sound.*;


import java.util.List;
import java.util.Map;
import java.util.Random;

public class TALKCommand implements CommandInterface{

    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {
        NPC npc = Player.PLAYER.getCurrentLocation().getNpc();
        String targetNpc = command[1];
        Random rand = new Random();
        int randomNumber = rand.nextInt();
        if (npc != null && npc.getName().equalsIgnoreCase(targetNpc)){
            String voices=npc.getDialogue();
            gameBuilder.append(voices);

            //TranslateToSpeech.textToVoice(voices);
            randomlyConvertToZombie(gameBuilder, instructs, npc, randomNumber);
        } else{
            gameBuilder.append("Sorry Dave, you can't talk to some one not in the room.");
        }
    }

    public void randomlyConvertToZombie(StringBuilder gameBuilder, Map<String, Map<String, List<String>>> instructs, NPC npc, int randomNumber) {
        if(randomNumber%2 == 1){
            //turn the npc into a zombie
            Player.PLAYER.getCurrentLocation().setZombie(new Zombie(5, Player.PLAYER.getCurrentLocation().getName()));
            gameBuilder.append(instructs.get("talk").get("instructions").get(0) + npc.getName());
            Player.PLAYER.getCurrentLocation().setNpc(null);
            //stop the background music
            if(((Background)(GameSceneControllerNew.getBackground())) != null) {
                ((Background)(GameSceneControllerNew.getBackground())).pauseMusic();
                SoundFactory.createSound(SoundType.BITE).startMusic();
                SoundFactory.createSound(SoundType.TURN).startMusic();
                if(!((Bite)(SoundFactory.createSound(SoundType.BITE))).getBiteClip().isPlaying() && !((Turn)(SoundFactory.createSound(SoundType.TURN))).getTurnClip().isPlaying()){
                    GameSceneControllerNew.getBackground().startMusic();
                }
            }
        }
    }
}
