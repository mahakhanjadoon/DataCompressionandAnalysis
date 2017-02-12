package sample;





import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lzma.sdk.lzma.Encoder;
import org.bson.Document;

import java.io.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;


public class UploadController {
    @FXML
    Button browse, viewGraph;

    @FXML
    TextField fileName, experimentName;

    @FXML
    Label wrongUsername, txtState, before, after, beforeLbl, afterLbl;

    @FXML
    AnchorPane downPane;
    @FXML
    LineChart<Number,Number> lineChart;

    String beforeText, afterText;


    @FXML
    ProgressIndicator pbar;


    Stage stage;
    DB db;
    DBCollection collection;
    public String name;
    private double xOffset = 0;
    private double yOffset = 0;


    public String[][] xy1 = new String[15][2];
    public String[][] xy2 = new String[15][2];
    public String[][] xy3 = new String[15][2];

    public void setName(String name) {
        this.name = name;
    }


    final FileChooser fileChooser = new FileChooser();
    File file;

    public void uploadFile(ActionEvent event) throws IOException {


        pbar.setVisible(false);
        txtState.setVisible(false);

          pbar.progressProperty().addListener(new ChangeListener<Number>() {


            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {

                if(t1.doubleValue()==1){
                    txtState.setVisible(true);
                    txtState.setTextAlignment(TextAlignment.CENTER);
                    txtState.setText("File Compressed and Uploaded!");
                    txtState.setTextFill(Color.GREEN);

                }
            }

        });


        wrongUsername.setVisible(false);
        stage = new Stage();
        stage.setTitle("Choose File");
        configureFileChooser(fileChooser);
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            fileName.setText(String.valueOf(file.getAbsolutePath()));


        }
    }

    public void uploadButton(ActionEvent event) throws IOException, BackingStoreException {
        Preferences stats  = Preferences.userNodeForPackage(UploadController.class);

        if(experimentName.getLength() <=0)
        {
            wrongUsername.setTextFill(Color.RED);
            wrongUsername.setVisible(true);
            wrongUsername.setText("Please Choose a Name for the Experiment");

        }
        else if(experimentName.getLength() >25)
        {
            wrongUsername.setTextFill(Color.RED);
            wrongUsername.setVisible(true);
            wrongUsername.setText("Max 25 Characters Allowed");

        }
        else if(fileName.getLength() <=0)
        {
            wrongUsername.setTextFill(Color.RED);
            wrongUsername.setVisible(true);
            wrongUsername.setText("No File Chosen");
        }
        else if(!fileName.getText().endsWith(".txt"))
        {
            wrongUsername.setTextFill(Color.RED);
            wrongUsername.setVisible(true);
            wrongUsername.setText("Please choose a valid text file");
        }
        else {
            Preferences folderLocation  = Preferences.userNodeForPackage(SettingsController.class);
            String path=  (folderLocation.get("input", null)).toString();
            int length = path.length();
            String userPath =(fileName.getText()).toString();
            String checkPath;
            if(userPath.length() < length)
            {
                checkPath = "llalala";
            }
            else {
                checkPath = userPath.substring(0, length);
            }

            if(path.equals(checkPath))
            {
                Task task = taskCreator(3);
                pbar.progressProperty().unbind();
                pbar.progressProperty().bind(task.progressProperty());
                viewGraph.setVisible(false);
                new Thread(task).start();


                txtState.setVisible(true);
                pbar.setVisible(true);
                txtState.setAlignment(Pos.CENTER);
                txtState.setTextAlignment(TextAlignment.CENTER);
                txtState.setText("Compressing and Uploading");
                txtState.setTextFill(Color.BLUE);


                inferenceEngine(file);
                compress(file, experimentName.getText());


                task.setOnSucceeded(event1 -> {
                            viewGraph.setVisible(true);
                            before.setVisible(true);
                            before.setText(beforeText);
                            after.setVisible(true);
                            after.setText(afterText);
                            beforeLbl.setVisible(true);
                            afterLbl.setVisible(true);
                        }
                );

                if(stats.get("experiments", null)==null)
                {
                   stats.put("experiments", "0");

                }
                else
                {
                    String exp = stats.get("experiments", null);
                    int experiments = Integer.parseInt(exp);
                    experiments++;
                    String finalExp = String.valueOf(experiments);
                    stats.put("experiments", finalExp);
                }
            }
            else
            {
                wrongUsername.setTextFill(Color.RED);
                wrongUsername.setVisible(true);
                wrongUsername.setText("Please choose file from specified folder or change folder location in Setttings");
            }
        }
    }

    private Task taskCreator(int seconds){
        return new Task() {

            @Override
            protected Object call() throws Exception {
                for(int i=0; i<seconds;i++){
                    Thread.sleep(1000);
                    updateProgress(i+1, seconds);

                }
                return true;
            }
        };
    }
    private static void configureFileChooser(final FileChooser fileChooser) {
        Preferences folderLocation  = Preferences.userNodeForPackage(SettingsController.class);
        String path = folderLocation.get("input", null);
        fileChooser.setTitle("Choose a Text File");
        fileChooser.setInitialDirectory(
                new File(path)
        );
    }


    public void inferenceEngine(File file) {

        Preferences x1 = Preferences.userNodeForPackage(UploadController.class);
        Preferences x2 = Preferences.userNodeForPackage(UploadController.class);
        Preferences x3 = Preferences.userNodeForPackage(UploadController.class);
        Preferences y1 = Preferences.userNodeForPackage(UploadController.class);
        Preferences y2 = Preferences.userNodeForPackage(UploadController.class);
        Preferences y3 = Preferences.userNodeForPackage(UploadController.class);


        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String text;

            MongoClientURI uri  = new MongoClientURI("mongodb://admin:1234@ds056789.mlab.com:56789/cern");
            MongoClient client = new MongoClient(uri);
            MongoDatabase db = client.getDatabase(uri.getDatabase());
            MongoCollection<Document> collection = db.getCollection("experiments");
            Document document1 = new Document();

            document1.put("experiment_name", experimentName.getText());
            document1.put("user_name", name);
            document1.put("path", "F:\\Fyp_Zipped_Files\\" + experimentName.getText() + ".7z");


            int x =0;
            int y=0;
            int z=0;

            for(int a=0; a<=6; a++) {
                bufferedReader.readLine();
            }

            while ((text = bufferedReader.readLine()) != null) {



               String rand=text.substring(1);
                if(rand.startsWith("#"))
                {
                    System.out.println("nope");

                    break;
                }
                else {
                    String text2 = text.substring(4);
                    String[] split = text2.split("\\s+");
                    Double ex1 = Double.parseDouble(split[0]);
                    Double ex2 = Double.parseDouble(split[1]);
                    Double rounded1 = round(ex1, 2);
                    Double rounded2 = round(ex2, 2);

                    split[0]=rounded1.toString();
                    split[1]=rounded2.toString();

                    if (x <= 10) {
                        xy1[x][0] = split[0];
                        xy1[x][1] = split[1];
                        x1.put(x+"_", xy1[x][0]);
                        y1.put(x+".", xy1[x][1]);


                        System.out.println(xy1[x][0] + " " + xy1[x][1]);
                        x++;
                    }
                }
            }
            for(int a=0; a<=2; a++) {
                bufferedReader.readLine();
            }
            while ((text = bufferedReader.readLine()) != null) {



                String rand=text.substring(1);
                if(rand.startsWith("#"))
                {

                    System.out.println("nope");
                    break;
                }
                else {
                    String text2 = text.substring(4);
                    String[] split = text2.split("\\s+");
                    Double ex1 = Double.parseDouble(split[0]);
                    Double ex2 = Double.parseDouble(split[1]);
                    Double rounded1 = round(ex1, 2);
                    Double rounded2 = round(ex2, 2);

                    split[0]=rounded1.toString();
                    split[1]=rounded2.toString();

                    if (y <= 10) {
                        xy2[y][0] = split[0];
                        xy2[y][1] = split[1];
                        x2.put(y+"-", xy2[y][0]);
                        y2.put(y+"=", xy2[y][1]);

                        System.out.println(xy2[y][0] + " " + xy2[y][1]);
                        y++;
                    }
                }
            }
            for(int a=0; a<=2; a++) {
                bufferedReader.readLine();
            }

            while ((text = bufferedReader.readLine()) != null) {

                String text2 = text.substring(4);
                String[] split = text2.split("\\s+");
                Double ex1 = Double.parseDouble(split[0]);
                Double ex2 = Double.parseDouble(split[1]);
                Double rounded1 = round(ex1, 2);
                Double rounded2 = round(ex2, 2);

                split[0]=rounded1.toString();
                split[1]=rounded2.toString();

                if(z<=10) {
                    xy3[z][0] = split[0];
                    xy3[z][1] = split[1];
                    x3.put(z+"*", xy3[z][0]);
                    y3.put(z+"+", xy3[z][1]);
                    System.out.println(xy3[z][0] + " " + xy3[z][1]);
                    z++;
                }

                else break;


            }
            wrongUsername.setTextFill(Color.BLUEVIOLET);
            wrongUsername.setText("Upload Successful!");
            wrongUsername.setVisible(true);

            downPane.setVisible(true);

            Document xA = new Document();
            Document yA = new Document();
            Document xB = new Document();
            Document yB = new Document();
            Document xC = new Document();
            Document yC = new Document();
            for(int i=0; i<=9; i++) {
                xA.put("x"+i, xy1[i][0]);
                yA.put("y"+i, xy1[i][1]);
                xB.put("x"+i, xy2[i][0]);
                yB.put("y"+i, xy2[i][1]);
                xC.put("x"+i, xy3[i][0]);
                yC.put("y"+i, xy3[i][1]);
            }
            document1.put("x1", xA);
            document1.put("y1", yA);
            document1.put("x2", xB);
            document1.put("y2", yB);
            document1.put("x3", xC);
            document1.put("y3", yC);


            collection.insertOne(document1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void viewGraphs(ActionEvent e) throws Exception {

        Preferences preference1 = Preferences.userNodeForPackage(UploadController.class);
        preference1.put("experiment", experimentName.getText());
        Stage stage = (Stage) browse.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/homePage.fxml"));

        Preferences preference = Preferences.userNodeForPackage(Controller.class);
        String user = preference.get("username", null);

        BorderPane borderPane = (BorderPane) loader.load();
        homePageController homeController = loader.getController();
        homeController.setName(user);

        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(Main.class.getResource("View/uploadGraphs.fxml"));

        AnchorPane innerHome = (AnchorPane) loader2.load();
        GraphController controller = loader2.getController();
        controller.setValues1(xy1);
        controller.setValues2(xy2);
        controller.setValues3(xy3);
        controller.setName(experimentName.getText());
        System.out.println(experimentName.getText());



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


    }
    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    public void compress(File file, String name) throws IOException {
         /* Read the input file to be compressed */
// compressing
        StringBuilder sb = new StringBuilder(name);
        StringBuilder sb2 = new StringBuilder(".7z");
        Preferences folderLocation  = Preferences.userNodeForPackage(SettingsController.class);
        String savePath=  (folderLocation.get("output", null)).toString();
        String s2 = savePath+"\\"+sb+sb2;
        File compressedFile = new File(s2);
       BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(compressedFile));

        final Encoder encoder = new Encoder();

        encoder.setDictionarySize(1 << 23);
        encoder.setEndMarkerMode(true);
        encoder.setMatchFinder(Encoder.EMatchFinderTypeBT4);
        encoder.setNumFastBytes(0x20);

        encoder.writeCoderProperties(out);
        long fileSize = file.length();
        for (int i = 0; i < 8; i++)
        {
            out.write((int) (fileSize >>> (8 * i)) & 0xFF);
        }
        encoder.code(in, out, -1, -1, null);
        out.flush();
        out.close();
        in.close();

        long fileSize2 = compressedFile.length();



        if((fileSize/1000) <1000) {
            beforeText = ((fileSize/1000) + " KB");
        }
        else
        {
            beforeText = ((fileSize/1000000) + " MB");
        }


        if((fileSize2/1000) <1000) {
            afterText = ((fileSize2/1000) + " KB");
        }
        else
        {
            afterText = ((fileSize2/1000000) + " MB");
        }
        Preferences stats  = Preferences.userNodeForPackage(UploadController.class);
        if(stats.get("space", null)==null)
        {
            stats.put("space", "0");

        }
        else
        {
            String exp = stats.get("space", null);
            long space = Integer.parseInt(exp);
            space = space + (fileSize/1000000);
            String finalExp = String.valueOf(space);
            stats.put("space", finalExp);
        }
        //decompress();
        if(stats.get("saved", null)==null)
        {
            stats.put("saved", "0");

        }
        else
        {
            String exp = stats.get("saved", null);
            long space = Integer.parseInt(exp);
            long saved = (fileSize - fileSize2)/1000000;
            space = space + saved;
            String finalExp = String.valueOf(space);
            stats.put("saved", finalExp);
        }


    }
    public void decompress() throws IOException {

        File decompressedFile = new File("F:\\Fyp_Zipped_Files\\yayy2.txt");
        File compressed = new File("F:\\Fyp_Zipped_Files\\please.7z");
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(compressed));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(decompressedFile));

        int propertiesSize = 5;
        byte[] properties = new byte[propertiesSize];
        if (in.read(properties, 0, propertiesSize) != propertiesSize)
        {
            throw new IOException("input .lzma file is too short");
        }
        lzma.sdk.lzma.Decoder decoder = new lzma.sdk.lzma.Decoder();
        if (!decoder.setDecoderProperties(properties))
        {
            throw new IOException("Incorrect stream properties");
        }
        long outSize = 0;
        for (int i = 0; i < 8; i++)
        {
            int v = in.read();
            if (v < 0)
            {
                throw new IOException("Can't read stream size");
            }
            outSize |= ((long) v) << (8 * i);
        }
        if (!decoder.code(in, out, outSize))
        {
            throw new IOException("Error in data stream");
        }
        out.flush();
        out.close();
        in.close();
    }

}
