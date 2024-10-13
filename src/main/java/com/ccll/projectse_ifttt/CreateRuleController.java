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

public class CreateRuleController {

    @FXML
    private Pane rulePane;
    @FXML
    private Label labelTriggerSelected;
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
    private ObservableList<String> triggersList = FXCollections.observableArrayList("Time of the Day","Day of the week","Day of the month","Date","File existence","File dimension","Status program");
    @FXML
    private ObservableList<String> actionsList = FXCollections.observableArrayList("Display message","Play Audio","Write string","Copy File","Move file","Remove file","Execute Program");
    @FXML
    private FXMLDocumentController fxmlDocumentController;

    private ObservableList<Object> paneItems = FXCollections.observableArrayList();


    public void setFXMLDocumentController(FXMLDocumentController fxmlDocumentController) {
        this.fxmlDocumentController = fxmlDocumentController;
    }

    public void initialize(){
        triggerBox.setItems(triggersList);
        actionBox.setItems(actionsList);

        triggerBox.setOnAction(e -> {
            String selectedItem = triggerBox.getSelectionModel().getSelectedItem();
            if(!paneItems.isEmpty())
            {
                for(Object item : paneItems){
                    rulePane.getChildren().remove(item);
                    paneItems.remove(item);
                }
            }
            createItem(labelTriggerSelected, selectedItem);
        });

    }

    private void createItem(Label label, String text)
    {
        switch (text) {
            case "Time of the Day":
                label.setText("Time of the Day (hh:mm)");

                TextField timeField = new TextField();
                timeField.setPromptText("hh:mm");
                timeField.setLayoutX(285.0);
                timeField.setLayoutY(142.0);

                rulePane.getChildren().add(timeField);
                paneItems.add(timeField);

                break;
            case "Day of the week":
                label.setText("Day of the week (Monday, etc..)");

                ComboBox<String> dayOfWeek = new ComboBox<String>();
                ObservableList<String> week = FXCollections.observableArrayList("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday");
                dayOfWeek.setItems(week);

                dayOfWeek.setLayoutX(285.0);
                dayOfWeek.setLayoutY(142.0);

                rulePane.getChildren().add(dayOfWeek);
                paneItems.add(dayOfWeek);

                break;
            case "Day of the month":
                label.setText("Choose a day of the month");
                DatePicker dayMonthField = new DatePicker();

                dayMonthField.setLayoutX(285.0);
                dayMonthField.setLayoutY(142.0);

                rulePane.getChildren().add(dayMonthField);
                paneItems.add(dayMonthField);

                break;
            case "Date":
                label.setText("Choose a date");
                DatePicker dateFiled = new DatePicker();

                dateFiled.setLayoutX(285.0);
                dateFiled.setLayoutY(142.0);

                rulePane.getChildren().add(dateFiled);
                paneItems.add(dateFiled);

                break;
            case "File existence":
                label.setText("Insert a file path");
                TextField fileExistenceField = new TextField();

                fileExistenceField.setLayoutX(285.0);
                fileExistenceField.setLayoutY(142.0);

                fileExistenceField.setPrefWidth(250);

                rulePane.getChildren().add(fileExistenceField);
                paneItems.add(fileExistenceField);

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
                paneItems.addAll(fileDimensionField,unit);

                break;
            case "Status program":
                label.setText("Insert the status program");

                TextField statusProgramField = new TextField();
                statusProgramField.setPromptText("Insert the status program");

                statusProgramField.setLayoutX(285.0);
                statusProgramField.setLayoutY(142.0);
                statusProgramField.setPrefWidth(250);

                rulePane.getChildren().add(statusProgramField);
                paneItems.add(statusProgramField);
        }
    }

    public void onCreateButtonClick(ActionEvent actionEvent) {
        String name = ruleNameTxtField.getText();
        String trigger = triggerBox.getValue();
        String action = actionBox.getValue();

        //new Rule();

        fxmlDocumentController.insertItems(name);
        Stage stage = (Stage) createButton.getScene().getWindow();
        stage.close();
    }

    public void onClearButtonClick(ActionEvent actionEvent) {
        ruleNameTxtField.clear();
        triggerBox.setValue(null);
        actionBox.setValue(null);
    }
}

