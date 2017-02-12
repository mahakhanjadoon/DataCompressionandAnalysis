package sample;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class DownloadController implements Initializable {

    public String[][] xy1 = new String[15][2];
    public String[][] xy2 = new String[15][2];
    public String[][] xy3 = new String[15][2];


    public String[][] xyA = new String[15][2];
    public String[][] xyB = new String[15][2];
    public String[][] xyC = new String[15][2];



    @FXML
    LineChart before, after;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for(int i=0; i<=9; i++) {
            Preferences xx1 = Preferences.userNodeForPackage(yourUploadController.class);
            Preferences yy1 = Preferences.userNodeForPackage(yourUploadController.class);
            String x = xx1.get(i + "_", null);
            String y = yy1.get(i + ".", null);

            xy1[i][0]= x;
            xy1[i][1]=y;

        }


        for(int k=0; k<=9; k++){
            Preferences xx2 = Preferences.userNodeForPackage(yourUploadController.class);
            Preferences yy2 = Preferences.userNodeForPackage(yourUploadController.class);
            String x = xx2.get(k+"-", null);
            String y = yy2.get(k+"=", null);

            xy2[k][0] = x;
            xy2[k][1] = y;

        }
        for(int l=0; l<=9; l++) {
            Preferences xx3 = Preferences.userNodeForPackage(yourUploadController.class);
            Preferences yy3 = Preferences.userNodeForPackage(yourUploadController.class);
            String x = xx3.get(l + "*", null);
            String y = yy3.get(l + "+", null);

            xy3[l][0] = x;
            xy3[l][1] = y;


        }
        loadChart1();
        try {
            loadChart2();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void loadChart1() {
        XYChart.Series<Number, Number> series1 = new XYChart.Series();
        XYChart.Series<Number, Number> series2 = new XYChart.Series();
        XYChart.Series<Number, Number> series3 = new XYChart.Series();

        int n=1;
        int m=1;
        int p=1;
        while(n>=0)
        {
            if (xy1[n][0] != null && xy1[n][0].length() > 0)
            {
                double x=Double.parseDouble(xy1[n][0]);
                double y=Double.parseDouble(xy1[n][1]);
                series1.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        while(m>=0)
        {
            if (xy2[m][0] != null && xy2[m][0].length() > 0)
            {
                double x=Double.parseDouble(xy2[m][0]);
                double y=Double.parseDouble(xy2[m][1]);
                series2.getData().add(new XYChart.Data<>(x, y));
                m++;
            }
            else {break;}

        }
        while(p>=0)
        {
            if (xy3[p][0] != null && xy3[p][0].length() > 0)
            {
                double x=Double.parseDouble(xy3[p][0]);
                double y=Double.parseDouble(xy3[p][1]);
                series3.getData().add(new XYChart.Data<>(x, y));
                p++;
            }
            else {break;}

        }



        series1.setName("Histo 1");
        series2.setName("Histo 2");
        series3.setName("Histo 3");
        before.setLegendVisible(false);
        before.getData().addAll(series1, series2, series3);


    }
    private void loadChart2() throws IOException {
        Preferences filePath = Preferences.userNodeForPackage(yourUploadController.class);
        String path = filePath.get("path", null);
       File file = new File(path);
        BufferedReader bufferedReader = null;


            bufferedReader = new BufferedReader(new FileReader(file));
            String text;


            int x = 0;
            int y = 0;
            int z = 0;

            for (int a = 0; a <= 6; a++) {
                bufferedReader.readLine();
            }

            while ((text = bufferedReader.readLine()) != null) {


                String rand = text.substring(1);
                if (rand.startsWith("#")) {
                    System.out.println("nope");

                    break;
                } else {
                    String text2 = text.substring(4);
                    String[] split = text2.split("\\s+");
                    Double ex1 = Double.parseDouble(split[0]);
                    Double ex2 = Double.parseDouble(split[1]);
                    Double rounded1 = round(ex1, 2);
                    Double rounded2 = round(ex2, 2);

                    split[0] = rounded1.toString();
                    split[1] = rounded2.toString();

                    if (x <= 10) {
                        xyA[x][0] = split[0];
                        xyA[x][1] = split[1];



                        System.out.println(xyA[x][0] + " " + xyA[x][1]);
                        x++;
                    }
                }
            }
            for (int a = 0; a <= 2; a++) {
                bufferedReader.readLine();
            }
            while ((text = bufferedReader.readLine()) != null) {


                String rand = text.substring(1);
                if (rand.startsWith("#")) {

                    System.out.println("nope");
                    break;
                } else {
                    String text2 = text.substring(4);
                    String[] split = text2.split("\\s+");
                    Double ex1 = Double.parseDouble(split[0]);
                    Double ex2 = Double.parseDouble(split[1]);
                    Double rounded1 = round(ex1, 2);
                    Double rounded2 = round(ex2, 2);

                    split[0] = rounded1.toString();
                    split[1] = rounded2.toString();

                    if (y <= 10) {
                        xyB[y][0] = split[0];
                        xyB[y][1] = split[1];


                        System.out.println(xyB[y][0] + " " + xyB[y][1]);
                        y++;
                    }
                }
            }
            for (int a = 0; a <= 2; a++) {
                bufferedReader.readLine();
            }

            while ((text = bufferedReader.readLine()) != null) {

                String text2 = text.substring(4);
                String[] split = text2.split("\\s+");
                Double ex1 = Double.parseDouble(split[0]);
                Double ex2 = Double.parseDouble(split[1]);
                Double rounded1 = round(ex1, 2);
                Double rounded2 = round(ex2, 2);

                split[0] = rounded1.toString();
                split[1] = rounded2.toString();

                if (z <= 10) {
                    xyC[z][0] = split[0];
                    xyC[z][1] = split[1];

                    System.out.println(xyC[z][0] + " " + xyC[z][1]);
                    z++;
                } else break;


            }
            load2();

          }

    private void load2() {
        XYChart.Series<Number, Number> series1 = new XYChart.Series();
        XYChart.Series<Number, Number> series2 = new XYChart.Series();
        XYChart.Series<Number, Number> series3 = new XYChart.Series();

        int n=1;
        int m=1;
        int p=1;
        while(n>=0)
        {
            if (xyA[n][0] != null && xyA[n][0].length() > 0)
            {
                double x=Double.parseDouble(xyA[n][0]);
                double y=Double.parseDouble(xyA[n][1]);
                series1.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        while(m>=0)
        {
            if (xyB[m][0] != null && xyB[m][0].length() > 0)
            {
                double x=Double.parseDouble(xyB[m][0]);
                double y=Double.parseDouble(xyB[m][1]);
                series2.getData().add(new XYChart.Data<>(x, y));
                m++;
            }
            else {break;}

        }
        while(p>=0)
        {
            if (xyC[p][0] != null && xyC[p][0].length() > 0)
            {
                double x=Double.parseDouble(xyC[p][0]);
                double y=Double.parseDouble(xyC[p][1]);
                series3.getData().add(new XYChart.Data<>(x, y));
                p++;
            }
            else {break;}

        }



        series1.setName("Histo 1");
        series2.setName("Histo 2");
        series3.setName("Histo 3");
        after.setLegendVisible(false);
        after.getData().addAll(series1, series2, series3);

    }

    public double round(double value, int places) {
            if (places < 0) throw new IllegalArgumentException();

            long factor = (long) Math.pow(10, places);
            value = value * factor;
            long tmp = Math.round(value);
            return (double) tmp / factor;
        }
}
