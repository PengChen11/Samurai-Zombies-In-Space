package com.gameEngine.commands;

import com.client.Main;
import com.gameEngine.GameEngine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class QCommand implements CommandInterface{

    @Override
    public void processCommand(StringBuilder gameBuilder, String[] command, Map<String, Map<String, List<String>>> instructs) {
        gameBuilder.append("Exiting game");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/gameSceneLoss.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        Main.getPrimaryStage().setScene(scene);
        Main.getPrimaryStage().show();
        //TODO: Exit game scene without closing whole game
    }
}
