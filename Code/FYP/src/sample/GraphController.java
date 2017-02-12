package sample;



import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.stage.StageStyle;


public class GraphController implements Initializable{

     NumberAxis xAxis = new NumberAxis();
     NumberAxis yAxis = new NumberAxis();
    ToggleGroup group = new ToggleGroup();

    @FXML
    AnchorPane graphPane;

    @FXML
    HBox separateGraphPane;

    @FXML
    RadioButton lineChartBtn, barChartBtn, scatterChartBtn, bubbleChartBtn, areaChartBtn, stackedAreaBtn, stackedBarBtn;

    @FXML LineChart<Number,Number> lineChart;

    @FXML
    Label experimentName;


    LineChart<Number, Number> lineChart1;
    BarChart<String,Number> barChart;
    StackedBarChart<Number,Number> stackedBarChart;
    ScatterChart<Number,Number> scatterChart;
    StackedAreaChart<Number,Number> stackedAreaChart;
    AreaChart<Number,Number> areaChart;
    BubbleChart<Number,Number> bubbleChart;

    public void setName(String eName) {
        this.eName= eName;
    }

    String eName;
    XYChart.Series<Number, Number> series1;
    XYChart.Series<Number, Number> series2;
    XYChart.Series<Number, Number> series3;

Stage stage;


    public String[][] axis1 = new String[15][2];
    public String[][] axis2 = new String[15][2];
    public String[][] axis3 = new String[15][2];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Preferences preference1 = Preferences.userNodeForPackage(UploadController.class);
        String user = preference1.get("experiment", null);
        experimentName.setText(user);

        lineChartBtn.setToggleGroup(group);
        lineChartBtn.setSelected(true);
        barChartBtn.setToggleGroup(group);
        areaChartBtn.setToggleGroup(group);
        scatterChartBtn.setToggleGroup(group);
        bubbleChartBtn.setToggleGroup(group);
        stackedAreaBtn.setToggleGroup(group);
        stackedBarBtn.setToggleGroup(group);

        xAxis.setLabel("Pseudorapidty");
        yAxis.setLabel("Cross-Section");

        setInitialChart();



    }

    private void setInitialChart() {
        graphPane.getChildren().remove(0);
        lineChart1 = new LineChart<Number,Number>(xAxis,yAxis);
        graphPane.getChildren().add(lineChart1);

        series1 = new XYChart.Series();
        series2 = new XYChart.Series();
        series3 = new XYChart.Series();
        lineChart1.setPrefWidth(388);
        lineChart1.setPrefHeight(252);



        //graphPane.getChildren().add(lineChart);

        int n=1;
        int m=1;
        int p=1;
        while(n>=0)
        {
            if (axis1[n][0] != null && axis1[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis1[n][0]);
                double y=Double.parseDouble(axis1[n][1]);
                series1.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        while(m>=0)
        {
            if (axis2[m][0] != null && axis2[m][0].length() > 0)
            {
                double x=Double.parseDouble(axis2[m][0]);
                double y=Double.parseDouble(axis2[m][1]);
                series2.getData().add(new XYChart.Data<>(x, y));
                m++;
            }
            else {break;}

        }
        while(p>=0)
        {
            if (axis3[p][0] != null && axis3[p][0].length() > 0)
            {
                double x=Double.parseDouble(axis3[p][0]);
                double y=Double.parseDouble(axis3[p][1]);
                series3.getData().add(new XYChart.Data<>(x, y));
                p++;
            }
            else {break;}

        }



        series1.setName("Histo 1");
        series2.setName("Histo 2");
        series3.setName("Histo 3");
        lineChart1.getData().addAll(series1, series2, series3);
        lineChart1.setLegendSide(Side.RIGHT);

        separateLineChart1();
        separateLineChart2();
        separateLineChart3();






    }

    public void setValues1(String[][] xy)
    {
        this.axis1 = xy;
        //System.out.println(axis[0][0]);
    }
    public void setValues2(String[][] xy)
    {
        this.axis2 = xy;
        //System.out.println(axis[0][0]);
    }
    public void setValues3(String[][] xy)
    {
        this.axis3 = xy;
        //System.out.println(axis[0][0]);
    }
    public void lineChart(ActionEvent e)
    {

        graphPane.getChildren().remove(0);
        lineChart1 = new LineChart<Number,Number>(xAxis,yAxis);
        graphPane.getChildren().add(lineChart1);

        series1 = new XYChart.Series();
        series2 = new XYChart.Series();
        series3 = new XYChart.Series();
        lineChart1.setPrefWidth(388);
        lineChart1.setPrefHeight(252);



        //graphPane.getChildren().add(lineChart);

        int n=1;
        int m=1;
        int p=1;
        while(n>=0)
        {
            if (axis1[n][0] != null && axis1[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis1[n][0]);
                double y=Double.parseDouble(axis1[n][1]);
                series1.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        while(m>=0)
        {
            if (axis2[m][0] != null && axis2[m][0].length() > 0)
            {
                double x=Double.parseDouble(axis2[m][0]);
                double y=Double.parseDouble(axis2[m][1]);
                series2.getData().add(new XYChart.Data<>(x, y));
               m++;
            }
            else {break;}

        }
        while(p>=0)
        {
            if (axis3[p][0] != null && axis3[p][0].length() > 0)
            {
                double x=Double.parseDouble(axis3[p][0]);
                double y=Double.parseDouble(axis3[p][1]);
                series3.getData().add(new XYChart.Data<>(x, y));
                p++;
            }
            else {break;}

        }



        series1.setName("Histo 1");
        series2.setName("Histo 2");
        series3.setName("Histo 3");
        lineChart1.getData().addAll(series1, series2, series3);
        lineChart1.setLegendSide(Side.RIGHT);

        separateLineChart1();
        separateLineChart2();
        separateLineChart3();

        for (XYChart.Data<Number, Number> data : series1.getData()) {
            Node node = data.getNode() ;
            node.setCursor(Cursor.HAND);
            node.setOnMouseDragged(event -> {
                Point2D pointInScene = new Point2D(event.getSceneX(), event.getSceneY());
                double xAxisLoc = xAxis.sceneToLocal(pointInScene).getX();
                double yAxisLoc = yAxis.sceneToLocal(pointInScene).getY();
                Number x = xAxis.getValueForDisplay(xAxisLoc);
                Number y = yAxis.getValueForDisplay(yAxisLoc);
                data.setXValue(x);
                data.setYValue(y);
            });
        }




    }
    public void scatterChart(ActionEvent e)
    {
        scatterChart = new ScatterChart<Number,Number>(xAxis,yAxis);
        series1 = new XYChart.Series();
        series2 = new XYChart.Series();
        series3 = new XYChart.Series();
        scatterChart.setPrefWidth(388);
        scatterChart.setPrefHeight(252);
        graphPane.getChildren().remove(0,1);

        graphPane.getChildren().add(scatterChart);

        int n=0;
        int m=0;
        int p=0;
        while(n>=0)
        {
            if (axis1[n][0] != null && axis1[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis1[n][0]);
                double y=Double.parseDouble(axis1[n][1]);
                series1.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        while(m>=0)
        {
            if (axis2[m][0] != null && axis2[m][0].length() > 0)
            {
                double x=Double.parseDouble(axis2[m][0]);
                double y=Double.parseDouble(axis2[m][1]);
                series2.getData().add(new XYChart.Data<>(x, y));
                m++;
            }
            else {break;}

        }
        while(p>=0)
        {
            if (axis3[p][0] != null && axis3[p][0].length() > 0)
            {
                double x=Double.parseDouble(axis3[p][0]);
                double y=Double.parseDouble(axis3[p][1]);
                series3.getData().add(new XYChart.Data<>(x, y));
                p++;
            }
            else {break;}

        }

        series1.setName("Histo 1");
        series2.setName("Histo 2");
        series3.setName("Histo 3");

        scatterChart.getData().addAll(series1, series2,series3);
        scatterChart.setLegendSide(Side.RIGHT);

        separateScatterChart1();
        separateScatterChart2();
        separateScatterChart3();

    }

    public void barChart(ActionEvent e)
    {
        CategoryAxis xAxi = new CategoryAxis();
        xAxi.setLabel("Pseudorapidity");
        NumberAxis yAxi = new NumberAxis();
        yAxi.setLabel("Cross-Section");
        barChart = new BarChart<String,Number>(xAxi,yAxi);
        XYChart.Series<String,Number> series1 = new XYChart.Series();
        barChart.setPrefWidth(388);
        barChart.setPrefHeight(252);
        graphPane.getChildren().remove(0);

        graphPane.getChildren().add(barChart);

        int n=0;
        while(n>=0)
        {
            if (axis1[n][0] != null && axis1[n][0].length() > 0)
            {
                String x=(axis1[n][0]).toString();
                double y=Double.parseDouble(axis1[n][1]);
                series1.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        series1.setName("Histo");

        barChart.getData().addAll(series1);
        barChart.setLegendSide(Side.RIGHT);

        separateBarChart1();
        separateBarChart2();
        separateBarChart3();
    }

    public void areaChart(ActionEvent e)
    {
        areaChart = new AreaChart<Number,Number>(xAxis,yAxis);
        series1 = new XYChart.Series();
        series2 = new XYChart.Series();
        series3 = new XYChart.Series();
        areaChart.setPrefWidth(388);
        areaChart.setPrefHeight(252);
        graphPane.getChildren().remove(0);

        graphPane.getChildren().add(areaChart);

        int n=0;
        int m=0;
        int p=0;
        while(n>=0)
        {
            if (axis1[n][0] != null && axis1[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis1[n][0]);
                double y=Double.parseDouble(axis1[n][1]);
                series1.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        while(m>=0)
        {
            if (axis2[m][0] != null && axis2[m][0].length() > 0)
            {
                double x=Double.parseDouble(axis2[m][0]);
                double y=Double.parseDouble(axis2[m][1]);
                series2.getData().add(new XYChart.Data<>(x, y));
                m++;
            }
            else {break;}

        }
        while(p>=0)
        {
            if (axis3[p][0] != null && axis3[p][0].length() > 0)
            {
                double x=Double.parseDouble(axis3[p][0]);
                double y=Double.parseDouble(axis3[p][1]);
                series3.getData().add(new XYChart.Data<>(x, y));
                p++;
            }
            else {break;}

        }
        series1.setName("Histo 1");
        series2.setName("Histo 2");
        series3.setName("Histo 3");
        areaChart.getData().addAll(series1, series2, series3);
        areaChart.setLegendSide(Side.RIGHT);
        separateAreaChart1();
        separateAreaChart2();
        separateAreaChart3();
    }

    public void bubbleChart(ActionEvent e)
    {
        bubbleChart = new BubbleChart<Number,Number>(xAxis,yAxis);
        series1 = new XYChart.Series();
        series2 = new XYChart.Series();
        series3 = new XYChart.Series();
        bubbleChart.setPrefWidth(388);
        bubbleChart.setPrefHeight(252);
        graphPane.getChildren().remove(0);

        graphPane.getChildren().add(bubbleChart);

        int n=0;
        int m=0;
        int p=0;
        while(n>=0)
        {
            if (axis1[n][0] != null && axis1[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis1[n][0]);
                double y=Double.parseDouble(axis1[n][1]);
                series1.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        while(m>=0)
        {
            if (axis2[m][0] != null && axis2[m][0].length() > 0)
            {
                double x=Double.parseDouble(axis2[m][0]);
                double y=Double.parseDouble(axis2[m][1]);
                series2.getData().add(new XYChart.Data<>(x, y));
                m++;
            }
            else {break;}

        }
        while(p>=0)
        {
            if (axis3[p][0] != null && axis3[p][0].length() > 0)
            {
                double x=Double.parseDouble(axis3[p][0]);
                double y=Double.parseDouble(axis3[p][1]);
                series3.getData().add(new XYChart.Data<>(x, y));
                p++;
            }
            else {break;}

        }
        series1.setName("Histo 1");
        series2.setName("Histo 2");
        series3.setName("Histo 3");
        bubbleChart.getData().addAll(series1, series2, series3);
        bubbleChart.setLegendSide(Side.RIGHT);
        separateBubbleChart1();
        separateBubbleChart2();
        separateBubbleChart3();
    }

    public void stackedArea(ActionEvent e)
    {

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Pseudorapidity");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Cross-Section");

        StackedAreaChart stackedAreaChart = new StackedAreaChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        XYChart.Series dataSeries2 = new XYChart.Series();
        XYChart.Series dataSeries3 = new XYChart.Series();
        dataSeries1.setName("Histo 1");
        dataSeries2.setName("Histo 2");
        dataSeries3.setName("Histo 3");

        stackedAreaChart.setLegendSide(Side.RIGHT);


        int n=0;
        int m=0;
        int p=0;
        while(n>=0)
        {
            if (axis1[n][0] != null && axis1[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis1[n][0]);
                double y=Double.parseDouble(axis1[n][1]);
                dataSeries1.getData().add(new XYChart.Data<>(n+1, y));
                n++;
            }
            else {break;}

        }
        while(m>=0)
        {
            if (axis2[m][0] != null && axis2[m][0].length() > 0)
            {
                double x=Double.parseDouble(axis2[m][0]);
                double y=Double.parseDouble(axis2[m][1]);
                dataSeries2.getData().add(new XYChart.Data<>(m+1, y));
                m++;
            }
            else {break;}

        }
        while(p>=0)
        {
            if (axis3[p][0] != null && axis3[p][0].length() > 0)
            {
                double x=Double.parseDouble(axis3[p][0]);
                double y=Double.parseDouble(axis3[p][1]);
                dataSeries3.getData().add(new XYChart.Data<>(p+1, y));
                p++;
            }
            else {break;}

        }

        stackedAreaChart.setPrefWidth(388);
        stackedAreaChart.setPrefHeight(252);
        graphPane.getChildren().remove(0);

        graphPane.getChildren().add(stackedAreaChart);
        stackedAreaChart.getData().addAll(dataSeries1, dataSeries2, dataSeries3);

        separateAreaChart1();
        separateAreaChart2();
        separateAreaChart3();
    }

    public void stackedBar(ActionEvent e)
    {

        CategoryAxis xAxis    = new CategoryAxis();

        xAxis.setLabel("Pseudorapidity");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Cross-Section");


        StackedBarChart     stackedBarChart = new StackedBarChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Histo 1");
        stackedBarChart.setLegendSide(Side.RIGHT);

        int n=0;
        int m=0;
        int p=0;
        while(n>=0 && n<=2)
        {
            if (axis1[n][0] != null && axis1[n][0].length() > 0)
            {
                String x=(axis1[n][0]).toString();
                double y=Double.parseDouble(axis1[n][1]);
                dataSeries1.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }



        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.setName("Histo 2");

        while(m>=0 && m<=2)
        {
            if (axis2[m][0] != null && axis2[m][0].length() > 0)
            {
                String x=(axis2[n][0]).toString();
                double y=Double.parseDouble(axis2[m][1]);
                dataSeries2.getData().add(new XYChart.Data<>(x, y));
                m++;
            }
            else {break;}

        }


        XYChart.Series dataSeries3 = new XYChart.Series();
        dataSeries3.setName("Histo 3");

        while(p>=0 && p<=2)
        {
            if (axis3[p][0] != null && axis3[p][0].length() > 0)
            {
                String x=(axis3[n][0]).toString();
                double y=Double.parseDouble(axis3[p][1]);
                dataSeries3.getData().add(new XYChart.Data<>(x, y));
                p++;
            }
            else {break;}

        }


        stackedBarChart.setPrefWidth(388);
        stackedBarChart.setPrefHeight(252);
        graphPane.getChildren().remove(0);

        graphPane.getChildren().add(stackedBarChart);
        stackedBarChart.getData().addAll(dataSeries1, dataSeries2, dataSeries3);

        separateBarChart1();
        separateBarChart2();
        separateBarChart3();
    }
    public void separateLineChart1(){


        separateGraphPane.getChildren().clear();


        final NumberAxis xAxis = new NumberAxis();

        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Pseudorapidity");
        yAxis.setLabel("Cross-section");

        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);



        XYChart.Series series = new XYChart.Series();
        lineChart.setStyle("CHART_COLOR_1:#ff531a;");

        int n = 0;


        while(n>=0)
        {
            if (axis1[n][0] != null && axis1[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis1[n][0]);
                double y=Double.parseDouble(axis1[n][1]);
                series.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        series.setName("Histo 1");
        separateGraphPane.getChildren().add(lineChart);
        lineChart.getData().add(series);


    }
    public void separateLineChart2(){

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Pseudorapidity");
        yAxis.setLabel("Cross-section");
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setStyle("CHART_COLOR_1: orange;");

        //defining a series
        XYChart.Series series = new XYChart.Series();

        int n = 0;


        while(n>=0)
        {
            if (axis2[n][0] != null && axis2[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis2[n][0]);
                double y=Double.parseDouble(axis2[n][1]);
                series.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        series.setName("Histo 2");
        separateGraphPane.getChildren().add(lineChart);
        lineChart.getData().add(series);


    }
    public void separateLineChart3(){



        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Pseudorapidity");
        yAxis.setLabel("Cross-section");

        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);
        lineChart.setStyle("CHART_COLOR_1: #2ec63b;");


        //defining a series
        XYChart.Series<Number,Number> series = new XYChart.Series();

        int n = 0;


        while(n>=0)
        {
            if (axis3[n][0] != null && axis3[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis3[n][0]);
                double y=Double.parseDouble(axis3[n][1]);
                series.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        series.setName("Histo 3");
        separateGraphPane.getChildren().add(lineChart);
        lineChart.getData().add(series);



    }
    public void separateBarChart1(){

        separateGraphPane.getChildren().clear();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);

        xAxis.setLabel("Pseudorapidity");
        yAxis.setLabel("Cross-section");
        XYChart.Series series = new XYChart.Series();
        bc.setStyle("CHART_COLOR_1:#ff531a;");



        int n=0;
        while(n>=0)
        {
            if (axis1[n][0] != null && axis1[n][0].length() > 0)
            {
                String x=(axis1[n][0]).toString();
                double y=Double.parseDouble(axis1[n][1]);
                series.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        series.setName("Histo 1");
         separateGraphPane.getChildren().add(bc);
        bc.getData().addAll(series);
    }
    public void separateBarChart2(){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);
        bc.setStyle("CHART_COLOR_1: orange;");

        XYChart.Series series = new XYChart.Series();

        xAxis.setLabel("Pseudorapidity");
        yAxis.setLabel("Cross-section");

        int n=0;
        while(n>=0)
        {
            if (axis2[n][0] != null && axis2[n][0].length() > 0)
            {
                String x=(axis2[n][0]).toString();
                double y=Double.parseDouble(axis2[n][1]);
                series.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        series.setName("Histo 2");
        separateGraphPane.getChildren().add(bc);
        bc.getData().addAll(series);
    }
    public void separateBarChart3(){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);

        bc.setStyle("CHART_COLOR_1: #2ec63b;");
        xAxis.setLabel("Pseudorapidity");
        yAxis.setLabel("Cross-section");

        XYChart.Series series = new XYChart.Series();


        int n=0;
        while(n>=0)
        {
            if (axis3[n][0] != null && axis3[n][0].length() > 0)
            {
                String x=(axis3[n][0]).toString();
                double y=Double.parseDouble(axis3[n][1]);
                series.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        series.setName("Histo 3");
        separateGraphPane.getChildren().add(bc);
        bc.getData().addAll(series);
    }

    public void separateScatterChart1(){

        separateGraphPane.getChildren().clear();
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final ScatterChart<Number,Number> sc = new
                ScatterChart<Number,Number>(xAxis,yAxis);
        sc.setStyle("CHART_COLOR_1:#ff531a;");


        xAxis.setLabel("Pseudorapidity");
        yAxis.setLabel("Cross-section");
        XYChart.Series series = new XYChart.Series();

        int n=0;
        while(n>=0)
        {
            if (axis1[n][0] != null && axis1[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis1[n][0]);
                double y=Double.parseDouble(axis1[n][1]);
                series.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        series.setName("Histo 1");
        sc.getData().add(series);
        separateGraphPane.getChildren().add(sc);
    }
    public void separateScatterChart2(){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final ScatterChart<Number,Number> sc = new
                ScatterChart<Number,Number>(xAxis,yAxis);

        sc.setStyle("CHART_COLOR_1: orange;");
        xAxis.setLabel("Pseudorapidity");
        yAxis.setLabel("Cross-section");
        XYChart.Series series = new XYChart.Series();

        int n=0;
        while(n>=0)
        {
            if (axis2[n][0] != null && axis2[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis2[n][0]);
                double y=Double.parseDouble(axis2[n][1]);
                series.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        series.setName("Histo 2");
        sc.getData().add(series);
        separateGraphPane.getChildren().add(sc);
    }
    public void separateScatterChart3(){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final ScatterChart<Number,Number> sc = new
                ScatterChart<Number,Number>(xAxis,yAxis);

        sc.setStyle("CHART_COLOR_1: #2ec63b;");
        xAxis.setLabel("Pseudorapidity");
        yAxis.setLabel("Cross-section");
        XYChart.Series series = new XYChart.Series();

        int n=0;
        while(n>=0)
        {
            if (axis3[n][0] != null && axis3[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis3[n][0]);
                double y=Double.parseDouble(axis3[n][1]);
                series.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        series.setName("Histo 3");
        sc.getData().add(series);
        separateGraphPane.getChildren().add(sc);
    }
    public void separateAreaChart1(){

        separateGraphPane.getChildren().clear();
        final NumberAxis xAxis = new NumberAxis(1, 31, 1);
        final NumberAxis yAxis = new NumberAxis();
        final AreaChart<Number,Number> ac =
                new AreaChart<Number,Number>(xAxis,yAxis);
        ac.setStyle("CHART_COLOR_1:#ff531a;");


        xAxis.setLabel("Pseudorapidity");
        yAxis.setLabel("Cross-section");
        XYChart.Series series= new XYChart.Series();
        int n=0;
        while(n>=0)
        {
            if (axis1[n][0] != null && axis1[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis1[n][0]);
                double y=Double.parseDouble(axis1[n][1]);
                series.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        series.setName("Histo 1");
        separateGraphPane.getChildren().add(ac);
        ac.getData().add(series);
    }
    public void separateAreaChart2(){
        final NumberAxis xAxis = new NumberAxis(1, 31, 1);
        final NumberAxis yAxis = new NumberAxis();
        final AreaChart<Number,Number> ac = new AreaChart<Number,Number>(xAxis,yAxis);
        ac.setStyle("CHART_COLOR_1: orange;");

        xAxis.setLabel("Pseudorapidity");
        yAxis.setLabel("Cross-section");

        XYChart.Series series= new XYChart.Series();
        int n=0;
        while(n>=0)
        {
            if (axis2[n][0] != null && axis2[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis2[n][0]);
                double y=Double.parseDouble(axis2[n][1]);
                series.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        series.setName("Histo 2");
        separateGraphPane.getChildren().add(ac);
        ac.getData().add(series);
    }
    public void separateAreaChart3(){
        final NumberAxis xAxis = new NumberAxis(1, 31, 1);
        final NumberAxis yAxis = new NumberAxis();
        final AreaChart<Number,Number> ac =
                new AreaChart<Number,Number>(xAxis,yAxis);

        ac.setStyle("CHART_COLOR_1: #2ec63b;");

        xAxis.setLabel("Pseudorapidity");
        yAxis.setLabel("Cross-section");

        XYChart.Series series= new XYChart.Series();
        int n=0;
        while(n>=0)
        {
            if (axis3[n][0] != null && axis3[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis3[n][0]);
                double y=Double.parseDouble(axis3[n][1]);
                series.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        series.setName("Histo 3");
        separateGraphPane.getChildren().add(ac);
        ac.getData().add(series);
    }

    public void separateBubbleChart1(){
        separateGraphPane.getChildren().clear();
        final NumberAxis xAxis = new NumberAxis(1, 53, 4);
        final NumberAxis yAxis = new NumberAxis(0, 80, 10);

        xAxis.setLabel("Pseudorapidity");
        yAxis.setLabel("Cross-section");
        final BubbleChart<Number,Number> blc = new
                BubbleChart<Number,Number>(xAxis,yAxis);
        blc.setStyle("CHART_COLOR_1:#ff531a;");


        XYChart.Series series = new XYChart.Series();
        int n=0;
        while(n>=0)
        {
            if (axis1[n][0] != null && axis1[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis1[n][0]);
                double y=Double.parseDouble(axis1[n][1]);
                series.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        series.setName("Histo 1");
        separateGraphPane.getChildren().add(blc);
        blc.getData().add(series);


    }
    public void separateBubbleChart2(){
        final NumberAxis xAxis = new NumberAxis(1, 53, 4);
        final NumberAxis yAxis = new NumberAxis(0, 80, 10);

        xAxis.setLabel("Pseudorapidity");
        yAxis.setLabel("Cross-section");
        final BubbleChart<Number,Number> blc = new
                BubbleChart<Number,Number>(xAxis,yAxis);
        blc.setStyle("CHART_COLOR_1: orange;");

        XYChart.Series series = new XYChart.Series();
        int n=0;
        while(n>=0)
        {
            if (axis2[n][0] != null && axis2[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis2[n][0]);
                double y=Double.parseDouble(axis2[n][1]);
                series.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        series.setName("Histo 2");
        separateGraphPane.getChildren().add(blc);
        blc.getData().add(series);

    }
    public void separateBubbleChart3(){
        final NumberAxis xAxis = new NumberAxis(1, 53, 4);
        final NumberAxis yAxis = new NumberAxis(0, 80, 10);

        xAxis.setLabel("Pseudorapidity");
        yAxis.setLabel("Cross-section");
        final BubbleChart<Number,Number> blc = new
                BubbleChart<Number,Number>(xAxis,yAxis);
        blc.setStyle("CHART_COLOR_1: #2ec63b;");

        XYChart.Series series = new XYChart.Series();
        int n=0;
        while(n>=0)
        {
            if (axis3[n][0] != null && axis3[n][0].length() > 0)
            {
                double x=Double.parseDouble(axis3[n][0]);
                double y=Double.parseDouble(axis3[n][1]);
                series.getData().add(new XYChart.Data<>(x, y));
                n++;
            }
            else {break;}

        }
        series.setName("Histo 3");
        separateGraphPane.getChildren().add(blc);
        blc.getData().add(series);



    }
    public void downloadMain(ActionEvent e) throws IOException {
        WritableImage image = graphPane.getChildren().get(0).snapshot(new SnapshotParameters(), null);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
// Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
// Show save file dialog
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
// Make sure it has the correct extension
            if (!file.getPath().endsWith(".png")) {
                file = new File(file.getPath() + ".png");
            }
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        }
    }
    public void download1(ActionEvent e) throws IOException {
        WritableImage image = separateGraphPane.getChildren().get(0).snapshot(new SnapshotParameters(), null);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
// Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
// Show save file dialog
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
// Make sure it has the correct extension
            if (!file.getPath().endsWith(".png")) {
                file = new File(file.getPath() + ".png");
            }
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        }
    }
    public void download2(ActionEvent e) throws IOException {
        WritableImage image = separateGraphPane.getChildren().get(1).snapshot(new SnapshotParameters(), null);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
// Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
// Show save file dialog
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
// Make sure it has the correct extension
            if (!file.getPath().endsWith(".png")) {
                file = new File(file.getPath() + ".png");
            }
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        }
    }
    public void download3(ActionEvent e) throws IOException {
        WritableImage image = separateGraphPane.getChildren().get(2).snapshot(new SnapshotParameters(), null);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
// Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
// Show save file dialog
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
// Make sure it has the correct extension
            if (!file.getPath().endsWith(".png")) {
                file = new File(file.getPath() + ".png");
            }
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        }
    }
    public void zoomMain(ActionEvent e) throws IOException {
        WritableImage image = graphPane.getChildren().get(0).snapshot(new SnapshotParameters(), null);
display(image);
    }
    public void zoom1(ActionEvent e) throws IOException {
        WritableImage image = separateGraphPane.getChildren().get(0).snapshot(new SnapshotParameters(), null);
        display(image);
    }
    public void zoom2(ActionEvent e) throws IOException {
        WritableImage image = separateGraphPane.getChildren().get(1).snapshot(new SnapshotParameters(), null);
        display(image);
    }
    public void zoom3(ActionEvent e) throws IOException {
        WritableImage image = separateGraphPane.getChildren().get(2).snapshot(new SnapshotParameters(), null);
        display(image);
    }

    public void display(WritableImage image) throws IOException {

        Stage window = new Stage();
        window.setTitle("Zoomed Chart");

       AnchorPane anchorPane = new AnchorPane();
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(470);
        imageView.setFitWidth(550);
        anchorPane.getChildren().add(imageView);

        Scene scene = new Scene(anchorPane, 550, 470);

        window.setScene(scene);
        window.setResizable(false);
        window.initStyle(StageStyle.UTILITY);

        window.show();


    }


}









