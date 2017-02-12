package sample;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class StatsController implements Initializable{


    @FXML
    Label space, saved, downloads, experiments;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Preferences stats  = Preferences.userNodeForPackage(UploadController.class);
        downloads.setText(stats.get("downloads", null));
        experiments.setText(stats.get("experiments", null));

        String spaceTemp = stats.get("space", null);
        int countSpace = Integer.parseInt(spaceTemp);
        if(countSpace<=1000)
        {
            space.setText(spaceTemp + " MB");
        }
        else {
            countSpace=countSpace/1000;
            space.setText(countSpace + " GB");
        }

        String savedTemp = stats.get("saved", null);
        int countSaved = Integer.parseInt(savedTemp);
        if(countSaved<=1000)
        {
            saved.setText(savedTemp + " MB");
        }
        else {
            countSaved=countSaved/1000;
            saved.setText(countSaved + " GB");
        }

    }
}
