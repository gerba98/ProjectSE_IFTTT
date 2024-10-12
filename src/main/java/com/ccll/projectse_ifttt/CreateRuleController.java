package com.ccll.projectse_ifttt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
            if(paneItems.size() > 0)
            {
                rulePane.getChildren().remove(paneItems.getFirst());
                paneItems.remove(0);
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
                label.setText("Day of the month (1, 2, etc..)");
                break;
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

