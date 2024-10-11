package com.ccll.projectse_ifttt;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FXMLDocumentController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}