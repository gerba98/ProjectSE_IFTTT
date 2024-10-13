package com.ccll.projectse_ifttt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;

public class FXMLDocumentController {

    @FXML
    private ListView<String> listView;

    @FXML
    private Button CreateRuleButton;

    @FXML
    private Button DeleteRuleButton;

    @FXML
    private Button SaveRuleButton;

    @FXML
    private Button CancelCreationButton;

    @FXML
    private ObservableList<String> rulesList = FXCollections.observableArrayList();


    @FXML
    public void initialize()
    {
        listView.setItems(rulesList);
    }

    @FXML
    public void insertItems(String name){
        rulesList.add(name);
        listView.setItems(rulesList);
    }

    @FXML
    public void OnCreateRuleClick()
    {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("create-rule.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            CreateRuleController secondViewController = fxmlLoader.getController();
            secondViewController.setFXMLDocumentController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Create Rule");
            stage.setScene(new Scene(root1));
            stage.show();
        }catch(IOException ex){
            System.out.println("I/O error");
        }
    }

}