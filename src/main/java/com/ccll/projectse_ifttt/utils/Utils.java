package com.ccll.projectse_ifttt.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalTime;
import java.util.Objects;

public class Utils {


    private static Spinner<Integer> createSpinner(int min, int max, int initial) {
        Spinner<Integer> spinner = new Spinner<>(min, max, initial);
        spinner.setEditable(true);
        spinner.setPrefWidth(55);
        return spinner;
    }

    @FXML
    public static Spinner<Integer> spinnerItem(int min, int max, int initial) {

        return createSpinner(min, max, initial);

    }

    public static void layoutSpinner(Spinner<Integer> spinner, int x, int y, int prefWidth) {
        spinner.setEditable(true);
        spinner.setPrefWidth(prefWidth);
        spinner.setLayoutX(x);
        spinner.setLayoutY(y);
    }

    public static ComboBox<String> comboItem(ObservableList<String> list) {
        ComboBox<String> comboBox = new ComboBox<String>();
        comboBox.setItems(list);
        return comboBox;
    }

    public static void layoutCombo(ComboBox<String> comboBox, int x, int y) {
        comboBox.setLayoutX(x);
        comboBox.setLayoutY(y);
    }

    public static DatePicker datePickerItem() {
        return new DatePicker();
    }

    public static void layoutDatePicker(DatePicker datePicker, int x, int y) {
        datePicker.setLayoutX(x);
        datePicker.setLayoutY(y);
    }

    public static void layoutFiled(TextField textField, int prefWidth, int x, int y, String type) {
        if (Objects.equals(type, "directory")) {
            textField.setPromptText("Directory path");
            textField.setPrefWidth(prefWidth);
            textField.setLayoutX(x);
            textField.setLayoutY(y);
            textField.setFocusTraversable(false);
            textField.setEditable(false);
            textField.setDisable(false);
        } else if (Objects.equals(type, "file name")) {
            textField.setPromptText("Write file name");
            textField.setPrefWidth(prefWidth);
            textField.setLayoutX(x);
            textField.setLayoutY(y);
        } else if (Objects.equals(type, "file choose")) {
            textField.setPrefWidth(prefWidth);
            textField.setLayoutX(x);
            textField.setLayoutY(y);
            textField.setFocusTraversable(false);
            textField.setEditable(false);
            textField.setDisable(false);
        } else {
            textField.setLayoutX(x);
            textField.setLayoutY(y);
            textField.setPrefWidth(prefWidth);
        }


    }

    public static void layoutSimpleField(TextField textField, int x, int y) {
        textField.setLayoutX(x);
        textField.setLayoutY(y);
    }

    public static void layoutLabel(Label label, int x, int y) {
        label.setLayoutX(x);
        label.setLayoutY(y);
    }

    public static Button ButtonDirectoryItem(TextField textField) {

        Button browseDirButton = new Button("Browse...");
        browseDirButton.setOnAction(e -> {
            DirectoryChooser checkDirChooser = new DirectoryChooser();
            checkDirChooser.setTitle("Choose directory...");

            Stage stage = (Stage) browseDirButton.getScene().getWindow();
            File selectedFile = checkDirChooser.showDialog(stage);
            textField.setText(selectedFile.getPath());
        });
        return browseDirButton;
    }

    public static Button buttonFileItem(TextField textField, FileChooser.ExtensionFilter files) {

        Button browseFileButton = new Button("Browse...");

        browseFileButton.setOnAction(e -> {
            FileChooser checkFileDimChooser = new FileChooser();
            checkFileDimChooser.setTitle("File...");
            if (files != null) {
                checkFileDimChooser.getExtensionFilters().add(files);
            }


            Stage stage = (Stage) browseFileButton.getScene().getWindow();
            File selectedFile = checkFileDimChooser.showOpenDialog(stage);
            textField.setText(selectedFile.getPath());
        });

        return browseFileButton;

    }

    public static void layoutButton(Button button, int x, int y) {
        button.setLayoutX(x);
        button.setLayoutY(y);
    }
}