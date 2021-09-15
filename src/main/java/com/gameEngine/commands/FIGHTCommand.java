package com.gameEngine.commands;

import com.character.Player;
import com.character.Zombie;
import com.gameEngine.GameEngine;
import com.location.Locations;
import com.sound.SoundFX;
import com.sound.SoundFactory;
import com.sound.SoundType;

import java.util.List;
import java.util.Map;

public class FIGHTCommand implements CommandInterface{

    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs)  {
        String currentLocKey = Player.PLAYER.getCurrentLocation().getName();
        if(Locations.getEnumMap().get(currentLocKey).getZombie() != null && (command[1] == null|| command[1].equalsIgnoreCase("zombie"))&& Locations.getEnumMap().get(currentLocKey).getZombie().getHealth() > 0){
            gameBuilder.append(instructs.get("fight").get("instructions").get(0) + "\n");
            while(Player.PLAYER.getHealth() > 0 && Locations.getEnumMap().get(currentLocKey).getZombie().getHealth() > 0 ){
                Locations.getEnumMap().get(currentLocKey).getZombie().takeDamage(Player.PLAYER.attack());
                Player.PLAYER.takeDamage(Locations.getEnumMap().get(currentLocKey).getZombie().attack());
                SoundFactory.createSound(SoundType.PUNCH).startMusic();

                gameBuilder.append(instructs.get("fight").get("instructions").get(4) + Player.PLAYER.getHealth()+ "\n");
                gameBuilder.append(instructs.get("fight").get("instructions").get(5) + Locations.getEnumMap().get(currentLocKey).getZombie().getHealth()+ "\n");
            }
            if(Player.PLAYER.getHealth() <= 0){
                gameBuilder.append(instructs.get("fight").get("instructions").get(7) );
                System.exit(0);
            }else{
                Player.PLAYER.getCurrentLocation().setZombie(null);
                gameBuilder.append(instructs.get("fight").get("instructions").get(6) );
            }
        }else{
           gameBuilder.append(instructs.get("fight").get("instructions").get(9));
        }




    }
}
