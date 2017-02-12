package sample;


import com.mongodb.Mongo;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class homePageController  {

    private double xOffset = 0;
    private double yOffset = 0;

    Stage stage;

    private BorderPane rootLayout;
    private AnchorPane loginPage;

    @FXML
    Button upload, home, yourUpload;

    @FXML
    BorderPane borderPane;


    @FXML
    public  Label userName;

    public String usernameText;

    public Boolean selected;
    public String password;

    public BorderPane getRootLayout()
    {
        return borderPane;
    }


    public void handleUpload(ActionEvent event) throws IOException {



            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/upload.fxml"));

            AnchorPane center = (AnchorPane) loader.load();

        UploadController homeController = loader.getController();
        homeController.setName(userName.getText());



            // Set person overview into the center of root layout.
            borderPane.setCenter(center);

    }
    public void handleHome(ActionEvent event) throws IOException {
        //loadHomePage();

        // Load person overview.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/innerHomePage.fxml"));
        AnchorPane center = (AnchorPane) loader.load();

        // Set person overview into the center of root layout.
        borderPane.setCenter(center);

    }
    public void handleSignout(ActionEvent event) throws IOException {
        //loadHomePage();

        stage = (Stage) upload.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/sample.fxml"));
        loginPage = (AnchorPane) loader.load();
        Controller controller =  loader.getController();



        // Show the scene containing the root layout
        Scene scene = new Scene(loginPage, 750, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void handleCross(ActionEvent event){
        Stage window = (Stage)upload.getScene().getWindow();
        window.close();
    }
    public void handleMinimize(ActionEvent event){
        Stage window = (Stage)upload.getScene().getWindow();
        window.setIconified(true);
    }

    public void handleExperiments(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/yourUpload.fxml"));

        AnchorPane center = (AnchorPane) loader.load();


        // Set person overview into the center of root layout.
        borderPane.setCenter(center);

    }
    public void handleSettings(ActionEvent event) throws IOException {
        Stage window = new Stage();
        window.setTitle("Settings");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/settings.fxml"));
        AnchorPane layout = (AnchorPane)loader.load();

        Scene scene = new Scene(layout);



        window.setScene(scene);

        window.showAndWait();


    }


    public void setName(String name) {
        userName.setText(name);
        yourUploadController.username = name;


    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRememberMe(boolean rememberMe) {
        this.selected = rememberMe;
    }
}
