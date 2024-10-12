package com.ccll.projectse_ifttt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateRuleController {

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

    public void setFXMLDocumentController(FXMLDocumentController fxmlDocumentController) {
        this.fxmlDocumentController = fxmlDocumentController;
    }

    public void initialize(){
        triggerBox.setItems(triggersList);
        actionBox.setItems(actionsList);
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

