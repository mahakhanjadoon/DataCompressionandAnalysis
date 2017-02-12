package sample;


import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.*;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class experimentsController implements Initializable {
    @FXML
    VBox vbox;


    public static String username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        double width = vbox.getWidth();
        MongoClientURI uri  = new MongoClientURI("mongodb://admin:1234@ds056789.mlab.com:56789/cern");
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());

        Preferences preference = Preferences.userNodeForPackage(Controller.class);
       String name= preference.get("username", null);
        MongoCollection<Document> collection = db.getCollection("experiments");
       Document query = new Document("user_name", name);
        MongoCursor<Document> cursor = collection.find(query).limit(100).iterator();

        try {
            while (cursor.hasNext()) {
               Document obj = (Document) cursor.next();
                String experimentName = obj.getString("experiment_name");

                CheckBox checkbox = new CheckBox();

                HBox hBox = new HBox(5);


                hBox.setMinWidth(width);

                Label label = new Label(experimentName);
                label.setMinWidth(150);
                label.setFont(Font.font("Ariel", FontWeight.BOLD, 12));


                Hyperlink download = new Hyperlink("Download");
                download.setFont(Font.font("Ariel", 12));
                download.setOnAction(e -> {
                    try {
                        decompress(experimentName);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });

                download.getStylesheets().add("/sample/experimentss.css");

                hBox.getChildren().addAll(label, download);
                hBox.getStyleClass().add("hbox-border");
                hBox.getStylesheets().add("/sample/experimentss.css");
                HBox.setMargin(label, new Insets(1, 250, 1, 1));
                HBox.setMargin(download, new Insets(1, 2, 12, 1));
                HBox.setMargin(checkbox, new Insets(1, 1, 1, 5));

                VBox.setMargin(hBox, new Insets(1, 1, 10, 1));
                vbox.getChildren().add(hBox);
            }
        } finally {
            cursor.close();
        }
    }

    public void decompress(String path) throws IOException {

        Stage stage = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Experiment");
// Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
// Show save file dialog
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
// Make sure it has the correct extension
            if (!file.getPath().endsWith(".txt")) {
                file = new File(file.getPath() + ".txt");
            }

            Preferences folderLocation  = Preferences.userNodeForPackage(SettingsController.class);
            String savePath=  (folderLocation.get("output", null)).toString();

            File compressed = new File(savePath + "\\"+ path + ".7z");
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(compressed));
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));

            int propertiesSize = 5;
            byte[] properties = new byte[propertiesSize];
            if (in.read(properties, 0, propertiesSize) != propertiesSize) {
                throw new IOException("input .lzma file is too short");
            }
            lzma.sdk.lzma.Decoder decoder = new lzma.sdk.lzma.Decoder();
            if (!decoder.setDecoderProperties(properties)) {
                throw new IOException("Incorrect stream properties");
            }
            long outSize = 0;
            for (int i = 0; i < 8; i++) {
                int v = in.read();
                if (v < 0) {
                    throw new IOException("Can't read stream size");
                }
                outSize |= ((long) v) << (8 * i);
            }
            if (!decoder.code(in, out, outSize)) {
                System.out.println("Done");
                throw new IOException("Error in data stream");
            }
            out.flush();
            out.close();
            in.close();

            Preferences stats  = Preferences.userNodeForPackage(UploadController.class);
            if(stats.get("downloads", null)==null)
            {
                stats.put("downloads", "0");

            }
            else
            {
                String exp = stats.get("downloads", null);
                long dns = Integer.parseInt(exp);
                dns++;
                String finalExp = String.valueOf(dns);
                stats.put("downloads",finalExp);
            }

        }

    }
}
