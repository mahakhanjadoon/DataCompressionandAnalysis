package sample;


import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;


public class Controller implements Initializable {
    Stage stage, newStage;

    private double xOffset = 0;
    private double yOffset = 0;

    private double xOffset1 = 0;
    private double yOffset1 = 0;
    private BorderPane rootLayout;

    @FXML
    Button loginButton;

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    Label incorrectLabel;

    @FXML
    CheckBox rememberMe;

    @FXML
    Hyperlink signUp;

    @FXML
    AnchorPane loginBg;

    String passwordText;

    public String usernameText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Preferences prefs = Preferences.userNodeForPackage(Controller.class);
        String user = prefs.get("userName", null);
        String pass = prefs.get("password", null);
        String select = prefs.get("selected", null);


        if (select != null){
            rememberMe.setSelected(true);
            username.setText(user);
            password.setText(pass);
        }


    }

    public void handleBgPressed( MouseEvent event){

        xOffset1 = event.getSceneX();
        yOffset1 = event.getSceneY();
    }
    public void handleBgDragged(MouseEvent event){
         newStage = (Stage)username.getScene().getWindow();
        newStage.setX(event.getScreenX() - xOffset1);
        newStage.setY(event.getScreenY() - yOffset1);

    }

    public void handleSignIn(ActionEvent event) throws IOException {

        usernameText = username.getText();
        passwordText = password.getText();

        Preferences preference = Preferences.userNodeForPackage(Controller.class);
        preference.put("username", usernameText);

        Preferences prefs = Preferences.userNodeForPackage(Controller.class);
        if(rememberMe.isSelected()==true)
        {

            prefs.put("userName", usernameText);
            prefs.put("password", passwordText);
            prefs.put("selected", "true");

        }
        else
        {
            prefs.remove("userName");
            prefs.remove("password");
            prefs.remove("selected");

        }


        MongoClientURI uri  = new MongoClientURI("mongodb://admin:1234@ds056789.mlab.com:56789/cern");
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());
        MongoCollection<Document> collection = db.getCollection("users");
        Document query = new Document("name", usernameText);

        MongoCursor<Document> cursor = collection.find(query).limit(1).iterator();

        if(!cursor.hasNext())
        {
            incorrectLabel.setText("Incorrect Username/Password");
        }

        while (cursor.hasNext()) {
            String passwordDb = String.valueOf(cursor.next().get("password"));



            if (passwordDb.equalsIgnoreCase(passwordText)) {


                stage = (Stage) loginButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("View/homePage.fxml"));
                rootLayout = (BorderPane) loader.load();
                homePageController homeController = loader.getController();
                homeController.setName(usernameText);
                homeController.setPassword(passwordText);
                homeController.setRememberMe(rememberMe.isSelected());


                innerHomePage();

                rootLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset = event.getSceneX();
                        yOffset = event.getSceneY();
                    }
                });
                rootLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        stage.setX(event.getScreenX() - xOffset);
                        stage.setY(event.getScreenY() - yOffset);
                    }
                });

                // Show the scene containing the root layout
                Scene scene = new Scene(rootLayout,980, 720);


                stage.setScene(scene);
                stage.setResizable(false);

                stage.show();



            } else {
                incorrectLabel.setText("Incorrect Username/Password.");
            }
        }


    }


    public void handleSignUp(ActionEvent event) throws IOException{
        SignUpBox.display();
    }
    public void handleCross(ActionEvent event){
        Stage window = (Stage)loginButton.getScene().getWindow();
        window.close();
    }
    public void handleMinimize(ActionEvent event){
        Stage window = (Stage)loginButton.getScene().getWindow();
        window.setIconified(true);
    }

    public void innerHomePage() {
        try {

            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/innerHomePage.fxml"));
            AnchorPane innerHome = (AnchorPane) loader.load();



            // Set person overview into the center of root layout.
            rootLayout.setCenter(innerHome);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }









}


