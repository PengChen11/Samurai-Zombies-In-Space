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
        initGameData();

        //todo: update location items, zombie, npc

        //todo: update player current location and inventory
//        Player.PLAYER
    }

    private void loadGame(){

    }
}