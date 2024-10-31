package com.ccll.projectse_ifttt.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

/**
 * Classe di utilità che fornisce metodi statici per la creazione e la gestione di componenti grafici comuni
 * nell'interfaccia utente, come spinner, combo box, date picker, text field e pulsanti per la selezione di file e directory.
 */
public class Utils {

    /**
     * Crea e configura uno spinner con un intervallo di valori.
     *
     * @param min     Il valore minimo dello spinner.
     * @param max     Il valore massimo dello spinner.
     * @param initial Il valore iniziale dello spinner.
     * @return Un oggetto {@link Spinner} configurato.
     */
    private static Spinner<Integer> createSpinner(int min, int max, int initial) {
        Spinner<Integer> spinner = new Spinner<>(min, max, initial);
        spinner.setEditable(true);
        spinner.setPrefWidth(55);
        return spinner;
    }

    /**
     * Fornisce uno spinner con valori compresi tra min e max, inizializzato a un valore specifico.
     *
     * @param min     Il valore minimo.
     * @param max     Il valore massimo.
     * @param initial Il valore iniziale.
     * @return Un oggetto {@link Spinner} configurato.
     */
    @FXML
    public static Spinner<Integer> spinnerItem(int min, int max, int initial) {
        return createSpinner(min, max, initial);
    }

    /**
     * Configura la posizione e la larghezza di uno spinner.
     *
     * @param spinner   Lo spinner da configurare.
     * @param x         La posizione orizzontale dello spinner.
     * @param y         La posizione verticale dello spinner.
     * @param prefWidth La larghezza preferita dello spinner.
     */
    public static void layoutSpinner(Spinner<Integer> spinner, int x, int y, int prefWidth) {
        spinner.setEditable(true);
        spinner.setPrefWidth(prefWidth);
        spinner.setLayoutX(x);
        spinner.setLayoutY(y);
    }

    /**
     * Crea un {@link ComboBox} con una lista di elementi predefiniti.
     *
     * @param list La lista di stringhe da visualizzare nel combo box.
     * @return Un oggetto {@link ComboBox} configurato con gli elementi forniti.
     */
    public static ComboBox<String> comboItem(ObservableList<String> list) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(list);
        return comboBox;
    }

    /**
     * Configura la posizione di un combo box.
     *
     * @param comboBox Il combo box da configurare.
     * @param x        La posizione orizzontale del combo box.
     * @param y        La posizione verticale del combo box.
     */
    public static void layoutCombo(ComboBox<String> comboBox, int x, int y) {
        comboBox.setLayoutX(x);
        comboBox.setLayoutY(y);
    }

    /**
     * Crea un oggetto {@link DatePicker}.
     *
     * @return Un nuovo oggetto {@link DatePicker}.
     */
    public static DatePicker datePickerItem() {
        return new DatePicker();
    }

    /**
     * Configura la posizione di un date picker.
     *
     * @param datePicker Il date picker da configurare.
     * @param x          La posizione orizzontale del date picker.
     * @param y          La posizione verticale del date picker.
     */
    public static void layoutDatePicker(DatePicker datePicker, int x, int y) {
        datePicker.setLayoutX(x);
        datePicker.setLayoutY(y);
    }

    /**
     * Configura un campo di testo con un tipo specifico (directory, file, semplice).
     *
     * @param textField Il campo di testo da configurare.
     * @param prefWidth La larghezza preferita del campo.
     * @param x         La posizione orizzontale del campo.
     * @param y         La posizione verticale del campo.
     * @param type      Il tipo di campo ("directory", "file name", "file choose").
     */
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

    /**
     * Configura la posizione di un semplice campo di testo.
     *
     * @param textField Il campo di testo da configurare.
     * @param x         La posizione orizzontale del campo di testo.
     * @param y         La posizione verticale del campo di testo.
     */
    public static void layoutSimpleField(TextField textField, int x, int y) {
        textField.setLayoutX(x);
        textField.setLayoutY(y);
    }

    /**
     * Configura la posizione di un'etichetta.
     *
     * @param label L'etichetta da configurare.
     * @param x     La posizione orizzontale dell'etichetta.
     * @param y     La posizione verticale dell'etichetta.
     */
    public static void layoutLabel(Label label, int x, int y) {
        label.setLayoutX(x);
        label.setLayoutY(y);
    }

    /**
     * Crea un pulsante per selezionare una directory.
     * Al click, apre una finestra di dialogo per la scelta di una directory e aggiorna il campo di testo con il percorso selezionato.
     *
     * @param textField Il campo di testo da aggiornare con il percorso della directory.
     * @return Un pulsante configurato per selezionare una directory.
     */
    public static Button ButtonDirectoryItem(TextField textField) {
        Button browseDirButton = new Button("Browse...");
        browseDirButton.setOnAction(e -> {
            DirectoryChooser checkDirChooser = new DirectoryChooser();
            checkDirChooser.setTitle("Choose directory...");
            Stage stage = (Stage) browseDirButton.getScene().getWindow();
            File selectedFile = checkDirChooser.showDialog(stage);
            if (selectedFile!=null) {
                textField.setText(selectedFile.getPath());
            }
        });
        return browseDirButton;
    }

    public static Button buttonFileAbsoluteItem(TextField textField, String value) {

        Button browseFileButton = new Button("Browse...");

        browseFileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(value);
            File selectedFile = fileChooser.showOpenDialog(browseFileButton.getScene().getWindow());
            if (selectedFile != null) { // Salva il percorso nella variabile di istanza
                // Mostra un messaggio di conferma
                textField.setText(selectedFile.getAbsolutePath());
            }
        });

        return browseFileButton;

    }


    /**
     * Crea un pulsante per selezionare un file.
     * Al click, apre una finestra di dialogo per la scelta di un file e aggiorna il campo di testo con il percorso selezionato.
     *
     * @param textField Il campo di testo da aggiornare con il percorso del file.
     * @param files     Il filtro di estensione del file da selezionare (opzionale).
     * @return Un pulsante configurato per selezionare un file.
     */
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
            if (selectedFile != null){
                textField.setText(selectedFile.getPath());
            }

        });
        return browseFileButton;
    }

    /**
     * Configura la posizione di un pulsante.
     *
     * @param button Il pulsante da configurare.
     * @param x      La posizione orizzontale del pulsante.
     * @param y      La posizione verticale del pulsante.
     */
    public static void layoutButton(Button button, int x, int y) {
        button.setLayoutX(x);
        button.setLayoutY(y);
    }
}
