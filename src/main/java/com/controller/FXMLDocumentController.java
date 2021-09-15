package com.controller;

import com.character.Player;
import com.item.Item;
import com.item.Weapon;
import com.location.Locations;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;

public class FXMLDocumentController {

    private Parent root;

//    public void switchFromIntroToGame(javafx.event.ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/gameScene.fxml"));
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }

    public void switchFromIntroToGameNew(javafx.event.ActionEvent event) throws Exception {

        initGameData();

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/gameSceneNew.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void switchFromIntroToGameLoad(javafx.event.ActionEvent event) throws Exception {
        initGameData();
        loadFromSavedGameData();

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/gameSceneNew.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void initGameData() throws Exception {
        Item.getItems("cfg/Items.json");
        Weapon.getWeapons("cfg/Weapons.json");
        Locations.initWithJsonFile("cfg/sampleLocations.json");
    }

    private void loadFromSavedGameData() throws Exception {

        Object gameData = new JSONParser().parse(new FileReader("cfg/save/gameData.json"));
        JSONObject gameDataObj = (JSONObject) gameData;

        JSONObject playerDataObj = (JSONObject) gameDataObj.get("player");
        JSONObject locationsDataObj = (JSONObject) gameDataObj.get("locations");

        Player.PLAYER.updatePlayerFromSavedGameData(playerDataObj);
        Locations.updateLocationsFromSavedGameData(locationsDataObj);
    }

}