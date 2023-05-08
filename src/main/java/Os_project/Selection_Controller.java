package Os_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Selection_Controller implements Initializable {

    FXMLLoader loader = null;

    private Stage primaryStage;

    private Parent root;

    private String algoFlag = null;

    @FXML // fx:id="MySpinner"
    private Spinner<Integer> MySpinner; // Value injected by FXMLLoader

    SpinnerValueFactory<Integer> valueFactory;

    @FXML // fx:id="btn"
    private Button btn; // Value injected by FXMLLoader

    @FXML // fx:id="RB1"
    private RadioButton RB1; // Value injected by FXMLLoader

    @FXML // fx:id="RB2"
    private RadioButton RB2; // Value injected by FXMLLoader

    @FXML // fx:id="RB3"
    private RadioButton RB3; // Value injected by FXMLLoader

    @FXML // fx:id="RB4"
    private RadioButton RB4; // Value injected by FXMLLoader

    @FXML // fx:id="RB5"
    private RadioButton RB5; // Value injected by FXMLLoader

    @FXML // fx:id="RB6"
    private RadioButton RB6; // Value injected by FXMLLoader

    @FXML // fx:id="imageView"
    private ImageView imageView; // Value injected by FXMLLoader

    public Selection_Controller() throws IOException {
    }

    public void initialize(URL location, ResourceBundle resources)
    {
        Image image = new Image("E:\\Senior1\\Second Semester\\OS\\OS_Project\\Assets\\icon.png");
        //Image image = new Image("file:Assets/icon.png");
        imageView.setImage(image);

        /* Spinner */
        createEditableSpinner();
    }

    void createEditableSpinner()
    {
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        MySpinner.setValueFactory(valueFactory);
        MySpinner.setStyle("-fx-font-size: 15px;");
        MySpinner.setEditable(true);
    }

    @FXML
    void OnFCFSButton(ActionEvent event) {
        // TODO: Handle FCFS button click
        algoFlag = "FCFS";
    }

    @FXML
    void OnPriorityNP_Button(ActionEvent event) {
        // TODO: Handle Priority (Non Preemptive) button click
        algoFlag = "Priority_NP";
    }

    @FXML
    void OnPriorityP_Button(ActionEvent event) {
        // TODO: Handle Priority (Preemptive) button click
        algoFlag = "Priority_P";
    }

    @FXML
    void OnRoundRobinButton(ActionEvent event) {
        // TODO: Handle Round Robin button click
        algoFlag = "RR";
    }

    @FXML
    void OnSJFNP_Button(ActionEvent event) {
        // TODO: Handle SJF (Non-Preemptive) button click
        algoFlag = "SJF_NP";
    }

    @FXML
    void OnSJFP_Button(ActionEvent event) {
        // TODO: Handle SJF (Preemptive) button click
        algoFlag = "SJF_P";
    }

    @FXML
    void onNextbutton(ActionEvent event) throws IOException {
        // TODO: Handle Next button click

        if (algoFlag == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Algorithm Not Detected");
            alert.setHeaderText("You must choose an algorithm first");
            alert.showAndWait();
            return;
        }

        if ((algoFlag.equals("FCFS") || algoFlag.equals("SJF_NP") || algoFlag.equals("SJF_P")))
        {
            loader = new FXMLLoader(getClass().getResource("FCFS_SJF.fxml"));
            loader.setControllerFactory(param -> new FCFS_SJF_Controller(MySpinner.getValue(),algoFlag));
        }
        else if((algoFlag.equals("Priority_P") || algoFlag.equals("Priority_NP")))
        {
            loader = new FXMLLoader(getClass().getResource("Priority.fxml"));
            loader.setControllerFactory(param -> new Priority_Controller(MySpinner.getValue(),algoFlag));
        }
        else
        {
            loader = new FXMLLoader(getClass().getResource("RR.fxml"));
            loader.setControllerFactory(param -> new RR_Controller(MySpinner.getValue()));
        }

        /* Common Code Between all Loaders */
        root = loader.load();
        primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setTitle("CPU Scheduler");
        primaryStage.resizableProperty();
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}


