package com.ccll.projectse_ifttt;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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
    private Pane RulePaneCreation;


    @FXML
    public void initialize()
    {
        RulePaneCreation.setVisible(false);
    }

    @FXML
    public void OnCreateRuleClick()
    {
        RulePaneCreation.setVisible(!RulePaneCreation.isVisible());
    }

}