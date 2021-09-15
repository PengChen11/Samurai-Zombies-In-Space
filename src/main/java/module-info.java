module Samurai.Zombies.In.Space {

    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires gson;
    requires java.sql;
    requires javafx.media;
    requires cmu.time.awb;
    requires freetts;

    opens com.controller to javafx.fxml;

    exports com.client;
}