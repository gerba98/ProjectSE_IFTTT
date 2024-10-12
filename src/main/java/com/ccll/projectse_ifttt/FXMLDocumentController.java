package com.ccll.projectse_ifttt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class FXMLDocumentController {


    @FXML
    private Button CreateRuleButton;

    @FXML
    private Button DeleteRuleButton;

    @FXML
    private Button SaveRuleButton;

    @FXML
    private Button CancelCreationButton;




    @FXML
    public void initialize()
    {
        //RulePaneCreation.setVisible(false);
    }

    @FXML
    public void OnCreateRuleClick()
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("create-rule.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
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