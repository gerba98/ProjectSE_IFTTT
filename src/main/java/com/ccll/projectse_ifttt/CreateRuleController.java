package com.ccll.projectse_ifttt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.security.cert.Extension;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Objects;

public class CreateRuleController {

    @FXML
    private Label labelError;
    @FXML
    private Pane rulePane;
    @FXML
    private Label labelTriggerSelected;
    @FXML
    private Label labelActionSelected;
    @FXML
    private Button createButton;
    @FXML
    private Button clearButton;
    @FXML
    private TextField ruleNameTxtField;
    @FXML
    private ComboBox<String> triggerBox;
    @FXML
    private ComboBox<String> actionBox;
    @FXML
    private ObservableList<String> triggersList = FXCollections.observableArrayList("Time of the Day");//,"Day of the week","Day of the month","Date","File existence","File dimension","Status program");
    @FXML
    private ObservableList<String> actionsList = FXCollections.observableArrayList("Display message","Play Audio");//,"Write string","Copy File","Move file","Remove file","Execute Program");
    @FXML
    private IndexController indexController;

    @FXML
    private ObservableList<Object> triggerPaneItems = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Object> actionPaneItems = FXCollections.observableArrayList();



    @FXML
    public void setFXMLDocumentController(IndexController indexController) {
        this.indexController = indexController;
    }
    @FXML
    public void initialize(){
        triggerBox.setItems(triggersList);
        actionBox.setItems(actionsList);

        triggerBox.setOnAction(e -> {
            String selectedItem = triggerBox.getSelectionModel().getSelectedItem();
            if(!triggerPaneItems.isEmpty())
            {
                Iterator<Object> iterator = triggerPaneItems.iterator();
                while (iterator.hasNext()) {
                    Object item = iterator.next();
                    rulePane.getChildren().remove(item);
                    iterator.remove(); // Rimozione sicura dell'elemento
                }
            }
            createTriggerItem(labelTriggerSelected, selectedItem);
        });

        actionBox.setOnAction(e -> {
            String selectedItem = actionBox.getSelectionModel().getSelectedItem();
            if(!actionPaneItems.isEmpty())
            {
                Iterator<Object> iterator = actionPaneItems.iterator();
                while (iterator.hasNext()) {
                    Object item = iterator.next();
                    rulePane.getChildren().remove(item);
                    iterator.remove(); // Rimozione sicura dell'elemento
                }
            }
            createActionItem(labelActionSelected, selectedItem);
        });

    }
    @FXML
    private void createTriggerItem(Label label, String text)
    {

        switch (text) {
            case "Time of the Day":
                label.setText("Time of the Day (hh:mm)");

                Spinner<Integer> hourSpinner = new Spinner<>(0, 23, LocalTime.now().getHour());
                Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, LocalTime.now().getMinute());

                hourSpinner.setEditable(true);
                hourSpinner.setPrefWidth(55);
                minuteSpinner.setEditable(true);
                minuteSpinner.setPrefWidth(55);

                hourSpinner.setLayoutX(285.0);
                hourSpinner.setLayoutY(142.0);

                minuteSpinner.setLayoutX(350.0);
                minuteSpinner.setLayoutY(142.0);


                rulePane.getChildren().addAll(hourSpinner,minuteSpinner);
                triggerPaneItems.addAll(hourSpinner,minuteSpinner);

                break;
            case "Day of the week":
                label.setText("Day of the week (Monday, etc..)");

                ComboBox<String> dayOfWeek = new ComboBox<String>();
                ObservableList<String> week = FXCollections.observableArrayList("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday");
                dayOfWeek.setItems(week);

                dayOfWeek.setLayoutX(285.0);
                dayOfWeek.setLayoutY(142.0);

                rulePane.getChildren().add(dayOfWeek);
                triggerPaneItems.add(dayOfWeek);

                break;
            case "Day of the month":
                label.setText("Choose a day of the month");
                DatePicker dayMonthField = new DatePicker();

                dayMonthField.setLayoutX(285.0);
                dayMonthField.setLayoutY(142.0);

                rulePane.getChildren().add(dayMonthField);
                triggerPaneItems.add(dayMonthField);

                break;
            case "Date":
                label.setText("Choose a date");
                DatePicker dateFiled = new DatePicker();

                dateFiled.setLayoutX(285.0);
                dateFiled.setLayoutY(142.0);

                rulePane.getChildren().add(dateFiled);
                triggerPaneItems.add(dateFiled);

                break;
            case "File existence":
                label.setText("Insert a file path");
                TextField fileExistenceField = new TextField();

                fileExistenceField.setLayoutX(285.0);
                fileExistenceField.setLayoutY(142.0);

                fileExistenceField.setPrefWidth(250);

                rulePane.getChildren().add(fileExistenceField);
                triggerPaneItems.add(fileExistenceField);

                break;
            case "File dimension":
                label.setText("Insert a file dimension and specify the unit");
                TextField fileDimensionField = new TextField();
                ComboBox<String> unit = new ComboBox<String>();

                ObservableList<String> unitList = FXCollections.observableArrayList("B","KB","MB","GB");
                unit.setItems(unitList);

                fileDimensionField.setPromptText("Insert a file dimension");
                fileDimensionField.setLayoutX(285.0);
                fileDimensionField.setLayoutY(142.0);
                fileDimensionField.setPrefWidth(100);

                unit.setLayoutX(400.0);
                unit.setLayoutY(142.0);

                rulePane.getChildren().addAll(fileDimensionField,unit);
                triggerPaneItems.addAll(fileDimensionField,unit);

                break;
            case "Status program":
                label.setText("Insert the status program");

                TextField statusProgramField = new TextField();
                statusProgramField.setPromptText("Insert the status program");
                
                statusProgramField.setLayoutX(285.0);
                statusProgramField.setLayoutY(142.0);
                statusProgramField.setPrefWidth(250);

                rulePane.getChildren().add(statusProgramField);
                triggerPaneItems.add(statusProgramField);
        }
    }

    @FXML
    private void createActionItem(Label label, String text)
    {

        switch (text) {
            case "Display message":
                label.setText("Message to show");

                TextField msgField = new TextField();
                msgField.setPromptText("Message...");
                msgField.setLayoutX(285.0);
                msgField.setLayoutY(225.0);


                rulePane.getChildren().add(msgField);
                actionPaneItems.add(msgField);

                break;
            case "Play Audio":
                label.setText("Audio to reproduce");

                FileChooser.ExtensionFilter audio = new FileChooser.ExtensionFilter("Audio files","*.mp3","*.aac","*.ogg");
                Button browseAudioButton = new Button("Browse...");

                TextField pathAudioField = new TextField();
                pathAudioField.setLayoutX(285);
                pathAudioField.setLayoutY(225);
                pathAudioField.setFocusTraversable(false);
                pathAudioField.setEditable(false);
                pathAudioField.setDisable(false);

                browseAudioButton.setLayoutX(450.0);
                browseAudioButton.setLayoutY(225);

                browseAudioButton.setOnAction(e ->{
                    FileChooser playFileChooser = new FileChooser();
                    playFileChooser.setTitle("File audio...");
                    playFileChooser.getExtensionFilters().add(audio);

                    Stage stage = (Stage) browseAudioButton.getScene().getWindow();
                    File selectedFile = playFileChooser.showOpenDialog(stage);
                    pathAudioField.setText(selectedFile.getPath());
                });

                rulePane.getChildren().addAll(browseAudioButton,pathAudioField);
                actionPaneItems.addAll(browseAudioButton,pathAudioField);

                break;
            case "Write string":
                label.setText("String to add");

                TextField stringField = new TextField();
                stringField.setPromptText("String...");

                stringField.setLayoutX(285.0);
                stringField.setLayoutY(225);

                rulePane.getChildren().add(stringField);
                actionPaneItems.add(stringField);

                break;
            case "Copy File":
                label.setText("File to copy");
                TextField fileCopyField = new TextField();
                fileCopyField.setPromptText("File path...");

                fileCopyField.setLayoutX(285.0);
                fileCopyField.setLayoutY(225);

                TextField destinationCopyField = new TextField();
                destinationCopyField.setPromptText("Folder path...");

                destinationCopyField.setLayoutX(450);
                destinationCopyField.setLayoutY(225);

                rulePane.getChildren().add(fileCopyField);
                actionPaneItems.add(fileCopyField);

                rulePane.getChildren().add(destinationCopyField);
                actionPaneItems.add(destinationCopyField);

                break;
            case "Move file":
                label.setText("File and folder path");
                TextField fileMoveField = new TextField();
                fileMoveField.setPromptText("File path...");

                fileMoveField.setLayoutX(285.0);
                fileMoveField.setLayoutY(225);

                TextField destinationMoveField = new TextField();
                destinationMoveField.setPromptText("Folder path...");

                destinationMoveField.setLayoutX(450);
                destinationMoveField.setLayoutY(225);

                rulePane.getChildren().add(fileMoveField);
                actionPaneItems.add(fileMoveField);

                rulePane.getChildren().add(destinationMoveField);
                actionPaneItems.add(destinationMoveField);

                break;
            case "Remove file":
                label.setText("Specify which file to remove");
                Button browseRemButton = new Button("Browse...");
                TextField pathRemField = new TextField();
                pathRemField.setLayoutX(285);
                pathRemField.setLayoutY(225);
                pathRemField.setFocusTraversable(false);
                pathRemField.setEditable(false);
                pathRemField.setDisable(false);

                browseRemButton.setLayoutX(450.0);
                browseRemButton.setLayoutY(225);

                browseRemButton.setOnAction(e ->{
                    FileChooser removeFileChooser = new FileChooser();
                    removeFileChooser.setTitle("File to remove...");

                    Stage stage = (Stage) browseRemButton.getScene().getWindow();
                    File selectedFile = removeFileChooser.showOpenDialog(stage);
                    pathRemField.setText(selectedFile.getPath());
                });
                rulePane.getChildren().addAll(browseRemButton,pathRemField);
                actionPaneItems.addAll(browseRemButton,pathRemField);

                break;
            case "Execute Program":
                label.setText("Choose the program to execute");

                TextField executeProgramField = new TextField();
                executeProgramField.setPromptText("Insert the application file (.exe)");

                executeProgramField.setLayoutX(285.0);
                executeProgramField.setLayoutY(225);

                rulePane.getChildren().add(executeProgramField);
                actionPaneItems.add(executeProgramField);
        }
    }

    @FXML
    public void onCreateButtonClick(ActionEvent actionEvent) {
        String name = ruleNameTxtField.getText();
        boolean errorFlag = false;

        String trigger = "";
        String action = "";

        Iterator<Object> iterator = triggerPaneItems.iterator();
        while (iterator.hasNext()) {
            Object item = iterator.next();
            if (item instanceof TextField) {
                if (!((TextField) item).getText().isEmpty()) {
                    errorFlag = false;
                    trigger += ((TextField) item).getText() + " ";
                } else {
                    errorFlag = true;
                }
            }
            if(item instanceof Spinner) {
                if (((Spinner) item).getValue().toString().length() == 1) {
                    trigger += "0" + ((Spinner) item).getValue() + ":";
                } else {
                    trigger += ((Spinner) item).getValue() + ":";

                }

            }
        }
        trigger = trigger.substring(0,trigger.length()-1)+" "; //rimuove l'ultimo carattere e lo sostituisce con uno spazio

        iterator = actionPaneItems.iterator();
        while (iterator.hasNext()) {
            Object item = iterator.next();
            if (item instanceof TextField) {
                if (!((TextField) item).getText().isEmpty()) {
                    errorFlag = false;
                    action += ((TextField) item).getText() + " ";
                } else {
                    errorFlag = true;
                }
            }

        }
        if(errorFlag || Objects.equals(name, "")) {
            labelError.setVisible(true);
        }else{
            //new Rule();
            labelError.setVisible(false);
            String rule = name + " " + trigger + " " + action;

            indexController.insertItems(rule);
            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();
        }
    }
    @FXML
    public void onClearButtonClick(ActionEvent actionEvent) {
        ruleNameTxtField.clear();
        triggerBox.setValue(null);
        actionBox.setValue(null);
    }
}

