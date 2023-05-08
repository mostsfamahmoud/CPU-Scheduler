package Os_project;

import Scheduling_Algorithms.Scheduler;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import java.util.ResourceBundle;

public class FCFS_SJF_RR_NonLive_Controller implements Initializable {

    @FXML
    private TreeTableColumn<RowRecord, Number> AT;    // Arrival Time Column

    @FXML
    private TreeTableColumn<RowRecord, Number> BT;     // Burst Time Column

    @FXML
    private TreeTableColumn<RowRecord, String> PN;     // Process Name Column

    @FXML
    private TextField turnAround;

    @FXML
    private TextField waitingTime;

    private Scheduler scheduler;

    FXMLLoader loader;

    Parent root;

    Stage primaryStage;

    @FXML
    private Canvas myCanvas;

    @FXML
    private TreeTableView<RowRecord> TableView;

    private TreeItem<RowRecord>[] Row = null;

    private TreeItem<RowRecord> tableRoot = new TreeItem<>(new RowRecord("Process[i]", 0, 0));

    @FXML
    private Button btn1;

    @FXML
    private ImageView imageView;

    public FCFS_SJF_RR_NonLive_Controller(TreeItem<RowRecord>[] Row, Scheduler scheduler) {
        this.Row = Row;
        this.scheduler = scheduler;
    }

    @FXML
    void onBackbutton(ActionEvent event) throws IOException {

        loader = new FXMLLoader(getClass().getResource("Selection.fxml"));
        root = loader.load();
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setTitle("CPU Scheduler");
        primaryStage.resizableProperty();
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image image = new Image("E:\\Senior1\\Second Semester\\OS\\OS_Project\\Assets\\icon.png");
        //Image image = new Image("file:Assets/icon.png");
        imageView.setImage(image);

        createTable();

        scheduler.schedule();
        turnAround.setText(scheduler.getAvgTurnaroundTime() + "");
        waitingTime.setText(scheduler.getAvgWaitingTime() + "");

        createGanttChart();
    }

    void createTable() {
        tableRoot.getChildren().setAll(Row);

        PN.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<RowRecord, String> param) -> param.getValue().getValue().getProcessNameProperty()
        );

        AT.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RowRecord, Number>, ObservableValue<Number>>() {
                                   @Override
                                   public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<RowRecord, Number> param) {
                                       return param.getValue().getValue().getArrivalTimeProperty();
                                   }
                               }
        );

        BT.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RowRecord, Number>, ObservableValue<Number>>() {
                                   @Override
                                   public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<RowRecord, Number> param) {
                                       return param.getValue().getValue().getBurstTimeProperty();
                                   }
                               }
        );

        TableView.setEditable(false);
        TableView.setRoot(tableRoot);
        TableView.setShowRoot(false);
    }


    void setProcessColor(String processName, GraphicsContext gc) {
        if (!processName.equals("N")) {
            String str = processName.substring(1);
            int processNo = Integer.parseInt(str);

            switch (processNo % 10) {
                case 0 -> gc.setFill(Color.PINK);
                case 1 -> gc.setFill(Color.BLUE);
                case 2 -> gc.setFill(Color.YELLOW);
                case 3 -> gc.setFill(Color.GREEN);
                case 4 -> gc.setFill(Color.RED);
                case 5 -> gc.setFill(Color.ORANGE);
                case 6 -> gc.setFill(Color.PURPLE);
                case 7 -> gc.setFill(Color.DARKCYAN);
                case 8 -> gc.setFill(Color.LIGHTBLUE);
                default -> gc.setFill(Color.VIOLET);
            }
        } else
            gc.setFill(Color.WHITE);
    }

    void createGanttChart() {
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        double rectangleWidth = 22;
        double rectangleHeight = 100;
        ArrayList<String> chart = new ArrayList<>();
        chart = scheduler.getOutput();
        int i = 0;
        for (String element : chart) {
            double x = i * (rectangleWidth + 3);
            double y = 0;
            setProcessColor(element, gc);
            gc.fillRect(x, y, rectangleWidth, rectangleHeight);
            gc.setFill(Color.BLACK);
            // Set the font and size of the text
            gc.setFont(new Font("Arial", 12));
            // Create a Text object to measure the width and height of the text
            Text text = new Text(element);
            text.setFont(gc.getFont());
            double textWidth = text.getBoundsInLocal().getWidth();
            double textHeight = text.getBoundsInLocal().getHeight();
            // Center the text horizontally and vertically
            gc.fillText(element, x + (rectangleWidth - textWidth) / 2, y + (rectangleHeight + textHeight) / 2);
            i++;
        }

    }
}
