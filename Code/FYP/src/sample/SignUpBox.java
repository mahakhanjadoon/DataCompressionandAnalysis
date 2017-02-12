package sample;


import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.bson.Document;

import java.io.IOException;

public class SignUpBox {

    @FXML
    TextField userName;

    @FXML
    TextField password;

    @FXML
    Button signUp;

    @FXML
    Label completed;

    @FXML
    Button cross, minimize;

    @FXML
    AnchorPane bg;

    private double xOffset = 0;
    private double yOffset = 0;

    public static void display() throws IOException {
        Stage window = new Stage();
        window.setTitle("Sign Up");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/signUp.fxml"));
        AnchorPane layout = (AnchorPane)loader.load();

        Scene scene = new Scene(layout);


        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(scene);

        window.showAndWait();


    }
    public void handleSignUp(ActionEvent event)throws IOException {


        String userNameText = userName.getText();
        String passwordText = password.getText();

        int lengthUser = userNameText.length();
        int lengthPass = passwordText.length();

        if((lengthUser <=25) && (lengthPass<=8)){

            MongoClientURI uri  = new MongoClientURI("mongodb://admin:1234@ds056789.mlab.com:56789/cern");
            MongoClient client = new MongoClient(uri);
            MongoDatabase db = client.getDatabase(uri.getDatabase());
            MongoCollection<Document> collection = db.getCollection("users");
            Document query = new Document();


            query.put("name", userNameText);
            query.put("password", passwordText);
            collection.insertOne(query);

            completed.setTextFill(Color.BLUEVIOLET);
            completed.setText("Sign Up Complete!");


        }
        else
        {
            if(lengthUser > 25)
            {
                completed.setTextFill(Color.RED);
                completed.setText("Max 25 characters of username allowed.");
            }
            if(lengthPass >8)
            {
                completed.setTextFill(Color.RED);
                completed.setText("Max 8 characters of password allowed.");
            }
        }


    }
    public void handleCross(ActionEvent event){
        Stage window = (Stage)userName.getScene().getWindow();
        window.close();
    }
    public void handleMinimize(ActionEvent event){
        Stage window = (Stage)userName.getScene().getWindow();
        window.setIconified(true);
    }
    public void handleBgPressed( MouseEvent event){

        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }
    public void handleBgDragged(MouseEvent event){
       Stage newStage = (Stage)userName.getScene().getWindow();
        newStage.setX(event.getScreenX() - xOffset);
        newStage.setY(event.getScreenY() - yOffset);

    }
}
