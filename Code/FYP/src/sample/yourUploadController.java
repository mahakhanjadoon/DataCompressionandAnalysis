package sample;


import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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

public class yourUploadController implements Initializable {

    @FXML
    VBox vbox;

    private double xOffset = 0;
    private double yOffset = 0;

    public static String username;
    public String[][] xy1 = new String[15][2];
    public String[][] xy2 = new String[15][2];
    public String[][] xy3 = new String[15][2];

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Preferences xx1 = Preferences.userNodeForPackage(yourUploadController.class);
        Preferences xx2 = Preferences.userNodeForPackage(yourUploadController.class);
        Preferences xx3 = Preferences.userNodeForPackage(yourUploadController.class);
        Preferences yy1 = Preferences.userNodeForPackage(yourUploadController.class);
        Preferences yy2 = Preferences.userNodeForPackage(yourUploadController.class);
        Preferences yy3 = Preferences.userNodeForPackage(yourUploadController.class);

        double width = vbox.getWidth();
        MongoClientURI uri  = new MongoClientURI("mongodb://admin:1234@ds056789.mlab.com:56789/cern");
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());

        MongoCollection<Document> collection = db.getCollection("experiments");
        Document query = new Document("user_name", username);
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
                Hyperlink analyze = new Hyperlink("Analyze");

                analyze.setOnAction(event -> {
                    HBox hBox1=(HBox)analyze.getParent();
                   Label experiment= (Label)hBox1.getChildren().get(1);
                    Document query1 = new Document("experiment_name", experiment.getText());
                    MongoCursor<Document> cursor1 = collection.find(query1).limit(1).iterator();


                        Document obj1 = (Document) cursor1.next();
                    Document x1 = (Document)obj1.get("x1");
                    Document y1 = (Document)obj1.get("y1");
                    Document x2 = (Document)obj1.get("x2");
                    Document y2 = (Document)obj1.get("y2");
                    Document x3 = (Document)obj1.get("x3");
                    Document y3 = (Document)obj1.get("y3");
                        for(int i=0; i<=9; i++)
                        {
                            String val1 = x1.getString("x"+i);
                            String val2 = y1.getString("y"+i);

                            String val3 = x2.getString("x"+i);
                            String val4 = y2.getString("y"+i);

                            String val5 = x3.getString("x"+i);
                            String val6 = y3.getString("y"+i);

                            xy1[i][0]=val1;
                            xy1[i][1]=val2;


                            xy2[i][0]=val3;
                            xy2[i][1]=val4;



                            xy3[i][0]=val5;
                            xy3[i][1]=val6;

                        }
                    Preferences preference1 = Preferences.userNodeForPackage(UploadController.class);
                    preference1.put("experiment", experiment.getText());
                    Stage stage = (Stage) vbox.getScene().getWindow();

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("View/homePage.fxml"));

                    Preferences preference = Preferences.userNodeForPackage(Controller.class);
                    String user = preference.get("username", null);

                    BorderPane borderPane = null;
                    try {
                        borderPane = (BorderPane) loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    homePageController homeController = loader.getController();
                    homeController.setName(user);

                    FXMLLoader loader2 = new FXMLLoader();
                    loader2.setLocation(Main.class.getResource("View/uploadGraphs.fxml"));

                    AnchorPane innerHome = null;
                    try {
                        innerHome = (AnchorPane) loader2.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    GraphController controller = loader2.getController();
                    controller.setValues1(xy1);
                    controller.setValues2(xy2);
                    controller.setValues3(xy3);
                    controller.setName(experiment.getText());
                    System.out.println(experiment.getText());



                    // Set person overview into the center of root layout.
                    borderPane.setCenter(innerHome);

                    borderPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            xOffset = event.getSceneX();
                            yOffset = event.getSceneY();
                        }
                    });
                    borderPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            stage.setX(event.getScreenX() - xOffset);
                            stage.setY(event.getScreenY() - yOffset);
                        }
                    });
                    Scene scene = new Scene(borderPane, 980, 720);


                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();

                });

                download.setOnAction(e -> {
                    try {
                    HBox hBox1=(HBox)download.getParent();
                    Label experiment= (Label)hBox1.getChildren().get(1);
//                    BasicDBObject query1 = new BasicDBObject("experiment_name", experiment.getText());
//                    DBCursor cursor1 = collection.find(query1);
//                    System.out.println(cursor1.count());
//
//                    BasicDBObject obj1 = (BasicDBObject) cursor1.one();
//                    BasicDBObject x1 = (BasicDBObject)obj1.get("x1");
//                    BasicDBObject y1 = (BasicDBObject)obj1.get("y1");
//                    BasicDBObject x2 = (BasicDBObject)obj1.get("x2");
//                    BasicDBObject y2 = (BasicDBObject)obj1.get("y2");
//                    BasicDBObject x3 = (BasicDBObject)obj1.get("x3");
//                    BasicDBObject y3 = (BasicDBObject)obj1.get("y3");
//                    for(int i=0; i<=9; i++)
//                    {
//                        String val1 = x1.getString("x"+i);
//                        String val2 = y1.getString("y"+i);
//
//                        String val3 = x2.getString("x"+i);
//                        String val4 = y2.getString("y"+i);
//
//                        String val5 = x3.getString("x"+i);
//                        String val6 = y3.getString("y"+i);
//
//                        xy1[i][0]=val1;
//                        xy1[i][1]=val2;
//                        xx1.put(i+"_", xy1[i][0]);
//                        yy1.put(i+".", xy1[i][1]);
//                         //System.out.println(xy1[i][0] + " " + xy1[i][1]);
//
//                        xy2[i][0]=val3;
//                        xy2[i][1]=val4;
//                        xx2.put(i+"-", xy2[i][0]);
//                        yy2.put(i+"=", xy2[i][1]);
//                        //System.out.println(xy2[i][0] + " " + xy2[i][1]);
//
//                        xy3[i][0]=val5;
//                        xy3[i][1]=val6;
//                        xx3.put(i+"*", xy3[i][0]);
//                        yy3.put(i+"+", xy3[i][1]);
//
//                    }

                        decompress(experimentName);

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });



                hBox.getChildren().addAll(checkbox, label, analyze, download);

                hBox.getStyleClass().add("hbox-border");

                HBox.setMargin(label, new Insets(1, 230, 1, 1));
                HBox.setMargin(download, new Insets(1, 2, 2, 1));
                HBox.setMargin(analyze, new Insets(1, 2, 2, 1));
                HBox.setMargin(checkbox, new Insets(1, 1, 1, 5));

                VBox.setMargin(hBox, new Insets(1, 1, 5, 1));
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
                Preferences filePath = Preferences.userNodeForPackage(yourUploadController.class);
                String pathFile = file.getAbsolutePath().toString();
                filePath.put("path", pathFile);
                System.out.println(file.getAbsoluteFile());
            }
            Preferences filePath = Preferences.userNodeForPackage(yourUploadController.class);
            String pathFile = file.getAbsolutePath().toString();
            filePath.put("path", pathFile);
            //System.out.println(file.getAbsoluteFile());

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

            Stage stage1 = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/download.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);
            stage1.setScene(scene);
            stage1.setResizable(false);
            stage1.show();

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
