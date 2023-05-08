package Os_project;

import Scheduling_Algorithms.Process;
import Scheduling_Algorithms.Scheduler;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

public class Priority_Controller implements Initializable {

    private int ProcessCount;

    private Stage primaryStage;

    private FXMLLoader loader;

    private Parent root;

    private String algoFlag;

    @FXML // fx:id="btn1"
    private Button btn1; // Value injected by FXMLLoader

    @FXML // fx:id="btn2"
    private Button btn2; // Value injected by FXMLLoader

    @FXML // fx:id="btn3"
    private Button btn3; // Value injected by FXMLLoader

    @FXML // fx:id="imageView"
    private ImageView imageView; // Value injected by FXMLLoader

    @FXML
    private TreeTableColumn<RowRecord,Number> AT;    // Arrival Time Column

    @FXML
    private TreeTableColumn<RowRecord,Number> BT;     // Burst Time Column

    @FXML
    private TreeTableColumn<RowRecord,String> PN;     // Process Name Column

    @FXML
    private TreeTableColumn<RowRecord,Number> PR;     // Process Name Column

    @FXML
    private TreeTableView<RowRecord> TableView;

    private TreeItem<RowRecord>[] Row = null;

    private TreeItem<RowRecord> tableRoot = new TreeItem<>(new RowRecord("Process[i]", 0, 0,0));

    public Priority_Controller(int ProcessCount, String algoFlag)
    {
        this.ProcessCount = ProcessCount;
        this.algoFlag = algoFlag;
        Row = new TreeItem[ProcessCount];
    }

    public void initialize(URL location, ResourceBundle resources)
    {
        Image image = new Image("E:\\Senior1\\Second Semester\\OS\\OS_Project\\Assets\\icon.png");
        //Image image = new Image("file:Assets/icon.png");
        imageView.setImage(image);

        createEditableTable();
    }

    void createEditableTable()
    {
        for(int i = 0; i < ProcessCount; i++)
        {
            Row[i] = new TreeItem<>(new RowRecord("P" + (i + 1), 0, 0,0));
        }

        tableRoot.getChildren().setAll(Row);

        PN.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<RowRecord,String> param) -> param.getValue().getValue().getProcessNameProperty()
        );

        AT.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RowRecord, Number>, ObservableValue<Number>>() {
                                   @Override
                                   public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<RowRecord, Number> param) {
                                       return param.getValue().getValue().getArrivalTimeProperty();
                                   }
                               }
        );

        AT.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new NumberStringConverter()));

        AT.setOnEditCommit(event -> {
            TreeTablePosition<RowRecord, Number> pos = event.getTreeTablePosition();
            Number newAT = event.getNewValue();
            int row = pos.getRow();
            RowRecord rowRecord = pos.getTreeItem().getValue();
            rowRecord.setArrivalTimeProperty(newAT.intValue());
        });


        BT.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RowRecord, Number>, ObservableValue<Number>>() {
                                   @Override
                                   public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<RowRecord, Number> param) {
                                       return param.getValue().getValue().getBurstTimeProperty();
                                   }
                               }
        );

        BT.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new NumberStringConverter()));

        BT.setOnEditCommit(event -> {
            TreeTablePosition<RowRecord, Number> pos = event.getTreeTablePosition();
            Number newAT = event.getNewValue();
            int row = pos.getRow();
            RowRecord rowRecord = pos.getTreeItem().getValue();
            rowRecord.setBurstTimeProperty(newAT.intValue());
        });

        PR.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RowRecord, Number>, ObservableValue<Number>>() {
                                   @Override
                                   public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<RowRecord, Number> param) {
                                       return param.getValue().getValue().getPriorityProperty();
                                   }
                               }
        );

        PR.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new NumberStringConverter()));

        PR.setOnEditCommit(event -> {
            TreeTablePosition<RowRecord, Number> pos = event.getTreeTablePosition();
            Number newAT = event.getNewValue();
            int row = pos.getRow();
            RowRecord rowRecord = pos.getTreeItem().getValue();
            rowRecord.setPriorityProperty(newAT.intValue());
        });

        TableView.setEditable(true);
        TableView.setRoot(tableRoot);
        TableView.setShowRoot(false);
    }

    @FXML
    void OnLiveSchedulingButton(ActionEvent event) throws IOException {

        if (isBurstTimeInvalid())
        {
            if (isBurstTimeZero())
            {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Invalid Burst Time");
                alert1.setHeaderText("The burst time of one process or more is ZERO");
                alert1.setContentText("Burst time must be a POSITIVE value");
                alert1.show();
            }

            if (isBurstTimeNegative())
            {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Invalid Burst Time");
                alert2.setHeaderText("The burst time of one process or more is NEGATIVE");
                alert2.setContentText("Burst time must be a POSITIVE value");
                alert2.show();
            }

            return;
        }

        Queue<Process> readyQueue =  new LinkedList<>();
        for(int i = 0; i < ProcessCount; i++)
        {
            Process p = new Process(Row[i].getValue().getProcessName(),Row[i].getValue().getBurstTime(),
                    Row[i].getValue().getArrivalTime(),Row[i].getValue().getPriority());

            Row[i].getValue().setRemainingTimeProperty(Row[i].getValue().getBurstTime());
            readyQueue.add(p);
        }

        Scheduler sch = new Scheduler(algoFlag,readyQueue);

        loader = new FXMLLoader(getClass().getResource("Priority_Live.fxml"));
        loader.setControllerFactory(param -> new Priority_Live_Controller(Row,sch));
        root = loader.load();
        primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setTitle("CPU Scheduler");
        primaryStage.resizableProperty();
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    @FXML
    void onBackbutton(ActionEvent event) throws IOException
    {
        loader = new FXMLLoader(getClass().getResource("Selection.fxml"));
        root = loader.load();
        primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setTitle("CPU Scheduler");
        primaryStage.resizableProperty();
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    @FXML
    void onNonLiveSchedulingbutton(ActionEvent event) throws IOException {

        if (isBurstTimeInvalid())
        {
            if (isBurstTimeZero())
            {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Invalid Burst Time");
                alert1.setHeaderText("The burst time of one process or more is ZERO");
                alert1.setContentText("Burst time must be a POSITIVE value");
                alert1.show();
            }

            if (isBurstTimeNegative())
            {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Invalid Burst Time");
                alert2.setHeaderText("The burst time of one process or more is NEGATIVE");
                alert2.setContentText("Burst time must be a POSITIVE value");
                alert2.show();
            }

            return;
        }

        Queue<Process> readyQueue =  new LinkedList<>();
        for(int i = 0; i < ProcessCount; i++)
        {
            Process p = new Process(Row[i].getValue().getProcessName(),Row[i].getValue().getBurstTime(),
                    Row[i].getValue().getArrivalTime(),Row[i].getValue().getPriority());

            readyQueue.add(p);
        }

        Scheduler sch = new Scheduler(algoFlag,readyQueue);

        loader = new FXMLLoader(getClass().getResource("Priority_NonLive.fxml"));
        loader.setControllerFactory(param -> new Priority_NonLive_Controller(Row,sch));
        root = loader.load();
        primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setTitle("CPU Scheduler");
        primaryStage.resizableProperty();
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    private boolean isBurstTimeZero()
    {
        int burstTime;
        boolean zeroFlag = false;

        for(int i = 0; i < ProcessCount; i++)
        {
            burstTime = Row[i].getValue().getBurstTime();

            if (burstTime == 0)
            {
                zeroFlag = true;
                break;
            }
        }
        return zeroFlag;
    }


    private boolean isBurstTimeNegative()
    {
        int burstTime;
        boolean negativeFlag = false;

        for(int i = 0; i < ProcessCount; i++)
        {
            burstTime = Row[i].getValue().getBurstTime();

            if (burstTime < 0)
            {
                negativeFlag = true;
                break;
            }
        }

        return negativeFlag;
    }

    private boolean isBurstTimeInvalid()
    {
        return (isBurstTimeZero() || isBurstTimeNegative());
    }
}
