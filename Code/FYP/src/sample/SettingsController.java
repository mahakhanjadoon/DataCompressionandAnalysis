package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class SettingsController implements Initializable{
    Stage stage;
    Preferences folderLocation  = Preferences.userNodeForPackage(SettingsController.class);
    String inputLocation, outputLocation;

    @FXML
    TextField inputFolder, outputFolder;

    @FXML
    Label label;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputFolder.setText(folderLocation.get("input", null));
        outputFolder.setText(folderLocation.get("output", null));
        label.setVisible(false);
    }

    public void chooseInputFolder(ActionEvent e)
    {
        stage = new Stage();
        stage.setTitle("Choose Folder");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory =
                directoryChooser.showDialog(stage);

        if(selectedDirectory == null){
            inputFolder.setText("No Directory selected");
        }else{
            inputFolder.setText(selectedDirectory.getAbsolutePath());
             inputLocation = selectedDirectory.getAbsolutePath();
            outputLocation = (folderLocation.get("output", null)).toString();
        }
    }

    public void chooseOutputFolder(ActionEvent e)
    {
        stage = new Stage();
        stage.setTitle("Choose Folder");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory =
                directoryChooser.showDialog(stage);

        if(selectedDirectory == null){
            outputFolder.setText("No Directory selected");
        }else{
            outputFolder.setText(selectedDirectory.getAbsolutePath());
            outputLocation = selectedDirectory.getAbsolutePath();
            inputLocation = (folderLocation.get("input", null)).toString();
        }
    }

    public void onSave(ActionEvent e)
    {
        if(outputFolder.getText().equals("No Directory selected"))
        {
            label.setVisible(true);
            label.setText("Please choose a directory to store experiments");
        }
        if(inputFolder.getText().equals("No Directory selected"))
        {
            label.setVisible(true);
            label.setText("Please choose a directory for input files");
        }

        if((inputLocation != null) && (outputLocation !=null) ) {
            folderLocation.put("input", inputLocation);
            folderLocation.put("output", outputLocation);
        }
        Stage stage = (Stage)inputFolder.getScene().getWindow();
        stage.close();
    }

    public void onCancel(ActionEvent e){


        Stage stage = (Stage)inputFolder.getScene().getWindow();
        stage.close();
    }
}
