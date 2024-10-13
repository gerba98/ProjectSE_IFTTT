package com.ccll.projectse_ifttt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Month;
import java.util.Iterator;

public class CreateRuleController {

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
    private ObservableList<String> actionsList = FXCollections.observableArrayList("Display message","Play Audio","Write string","Copy File","Move file","Remove file","Execute Program");
    @FXML
    private FXMLDocumentController fxmlDocumentController;

    @FXML
    private ObservableList<Object> triggerPaneItems = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Object> actionPaneItems = FXCollections.observableArrayList();

    @FXML
    public void setFXMLDocumentController(FXMLDocumentController fxmlDocumentController) {
        this.fxmlDocumentController = fxmlDocumentController;
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

                TextField timeField = new TextField();
                timeField.setPromptText("hh:mm");
                timeField.setLayoutX(285.0);
                timeField.setLayoutY(142.0);


                rulePane.getChildren().add(timeField);
                triggerPaneItems.add(timeField);

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

                TextField pathAudio = new TextField();
                pathAudio.setPromptText("Audio path...");  //modifichiamolo in un file browser

                pathAudio.setLayoutX(285.0);
                pathAudio.setLayoutY(225);

                rulePane.getChildren().add(pathAudio);
                actionPaneItems.add(pathAudio);

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
                TextField removeFileField = new TextField();        //modifichiamolo in un file browser
                removeFileField.setPromptText("File path...");

                removeFileField.setLayoutX(285.0);
                removeFileField.setLayoutY(225);

                rulePane.getChildren().addAll(removeFileField);
                actionPaneItems.addAll(removeFileField);

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
        String trigger = triggerBox.getValue();
        String action = actionBox.getValue();

        //new Rule();


        fxmlDocumentController.insertItems(name);
        Stage stage = (Stage) createButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void onClearButtonClick(ActionEvent actionEvent) {
        ruleNameTxtField.clear();
        triggerBox.setValue(null);
        actionBox.setValue(null);
    }
}

