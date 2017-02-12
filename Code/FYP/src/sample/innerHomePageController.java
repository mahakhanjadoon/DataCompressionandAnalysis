package sample;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

public class innerHomePageController {

@FXML AnchorPane anchorPane;

    public void loadPane(ActionEvent event) throws IOException {

        BorderPane pane=(BorderPane)anchorPane.getParent();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/upload.fxml"));

        AnchorPane center = (AnchorPane) loader.load();


        // Set person overview into the center of root layout.
        pane.setCenter(center);

    }
    public void download(ActionEvent event) throws IOException {

        BorderPane pane=(BorderPane)anchorPane.getParent();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/experiments.fxml"));

        AnchorPane center = (AnchorPane) loader.load();


        // Set person overview into the center of root layout.
        pane.setCenter(center);

    }
    public void recentAnalysis(ActionEvent event) throws IOException {

        BorderPane pane=(BorderPane)anchorPane.getParent();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/recentGraph.fxml"));

        AnchorPane center = (AnchorPane) loader.load();


        // Set person overview into the center of root layout.
        pane.setCenter(center);

    }
    public void recent(ActionEvent event) throws IOException {

        BorderPane pane=(BorderPane)anchorPane.getParent();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/stats.fxml"));

        AnchorPane center = (AnchorPane) loader.load();


        // Set person overview into the center of root layout.
        pane.setCenter(center);

    }

}
