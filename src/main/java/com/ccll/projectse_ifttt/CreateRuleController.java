package com.ccll.projectse_ifttt;

import com.ccll.projectse_ifttt.Rule.Rule;
import com.ccll.projectse_ifttt.Rule.RuleManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.security.cert.Extension;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Objects;

/**
 * Controller per la creazione di nuove regole nel sistema IFTTT. Gestisce
 * l'interfaccia utente che consente agli utenti di definire e configurare
 * regole basate su trigger e azioni.
 */
public class CreateRuleController {

    RuleManager ruleManager;

    @FXML
    private Label labelError;
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
    final ObservableList<String> triggersList = FXCollections.observableArrayList("Time of the Day", "Day of the week", "Day of the month", "Date");//,"Day of the week","Day of the month","Date","File existence","File dimension","Status program");
    @FXML
    final ObservableList<String> actionsList = FXCollections.observableArrayList("Display message","Play Audio");//,"Write string","Copy File","Move file","Remove file","Execute Program");
    @FXML
    private IndexController indexController;

    @FXML
    private ObservableList<Object> triggerPaneItems = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Object> actionPaneItems = FXCollections.observableArrayList();



    /**
     * Imposta l'istanza di IndexController per questo CreateRuleController.
     *
     * @param indexController l'istanza di IndexController da utilizzare in questo controller
     */
    @FXML
    public void setFXMLDocumentController(IndexController indexController) {
        this.indexController = indexController;
    }


    /**
     * Inizializza il CreateRuleController configurando l'istanza di RuleManager,
     * popolando triggerBox e actionBox con i relativi elementi, e configurando
     * i gestori di eventi per le azioni di selezione di triggerBox e actionBox.
     *
     * Al cambio di selezione in triggerBox o actionBox, questo metodo cancella gli
     * elementi correnti in triggerPaneItems o actionPaneItems rispettivamente,
     * li rimuove da rulePane e poi aggiunge un nuovo elemento in base alla selezione.
     */
    @FXML
    public void initialize(){
        ruleManager = RuleManager.getInstance();
        triggerBox.setItems(triggersList);
        actionBox.setItems(actionsList);

        triggerBox.setOnAction(e -> {
            String selectedItem = triggerBox.getSelectionModel().getSelectedItem();
            if(!triggerPaneItems.isEmpty()) {
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
    /**
     * Crea un elemento trigger all'interno del rule pane basato sul testo fornito.
     *
     * @param label l'etichetta da aggiornare con la descrizione specifica del trigger
     * @param text il tipo di trigger da creare; i valori validi sono "Ora del giorno",
     *             "Giorno della settimana", "Giorno del mese", "Data", "Esistenza file",
     *             "Dimensione file" e "Stato programma"
     */
    @FXML
    private void createTriggerItem(Label label, String text)
    {

        switch (text) {
            case "Time of the Day":
                label.setText("Time of the Day (hh:mm)");

                Spinner<Integer> hourSpinner = new Spinner<>(0, 23, LocalTime.now().getHour());
                Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, LocalTime.now().getMinute());

                hourSpinner.setEditable(true);
                hourSpinner.setPrefWidth(55);
                minuteSpinner.setEditable(true);
                minuteSpinner.setPrefWidth(55);

                hourSpinner.setLayoutX(285.0);
                hourSpinner.setLayoutY(142.0);

                minuteSpinner.setLayoutX(350.0);
                minuteSpinner.setLayoutY(142.0);


                rulePane.getChildren().addAll(hourSpinner,minuteSpinner);
                triggerPaneItems.addAll(hourSpinner,minuteSpinner);


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

    /**
     * Crea un elemento azione all'interno del rule pane basato sul testo fornito.
     *
     * A seconda del valore del parametro `text`, questo metodo aggiorna l'etichetta `label`
     * e crea i campi di input o i pulsanti appropriati richiesti per l'azione specificata.
     *
     * @param label l'etichetta da aggiornare con la descrizione specifica dell'azione
     * @param text il tipo di azione da creare; i valori validi sono "Visualizza messaggio",
     *             "Riproduci audio", "Scrivi stringa", "Copia file", "Sposta file",
     *             "Rimuovi file" e "Esegui programma"
     */
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

                FileChooser.ExtensionFilter audio = new FileChooser.ExtensionFilter("Audio files","*.mp3","*.aac");
                Button browseAudioButton = new Button("Browse...");

                TextField pathAudioField = new TextField();
                pathAudioField.setLayoutX(285);
                pathAudioField.setLayoutY(225);
                pathAudioField.setFocusTraversable(false);
                pathAudioField.setEditable(false);
                pathAudioField.setDisable(false);

                browseAudioButton.setLayoutX(450.0);
                browseAudioButton.setLayoutY(225);

                browseAudioButton.setOnAction(e ->{
                    FileChooser playFileChooser = new FileChooser();
                    playFileChooser.setTitle("File audio...");
                    playFileChooser.getExtensionFilters().add(audio);

                    Stage stage = (Stage) browseAudioButton.getScene().getWindow();
                    File selectedFile = playFileChooser.showOpenDialog(stage);
                    pathAudioField.setText(selectedFile.getPath());
                });

                rulePane.getChildren().addAll(browseAudioButton,pathAudioField);
                actionPaneItems.addAll(browseAudioButton,pathAudioField);

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
                Button browseRemButton = new Button("Browse...");
                TextField pathRemField = new TextField();
                pathRemField.setLayoutX(285);
                pathRemField.setLayoutY(225);
                pathRemField.setFocusTraversable(false);
                pathRemField.setEditable(false);
                pathRemField.setDisable(false);

                browseRemButton.setLayoutX(450.0);
                browseRemButton.setLayoutY(225);

                browseRemButton.setOnAction(e ->{
                    FileChooser removeFileChooser = new FileChooser();
                    removeFileChooser.setTitle("File to remove...");

                    Stage stage = (Stage) browseRemButton.getScene().getWindow();
                    File selectedFile = removeFileChooser.showOpenDialog(stage);
                    pathRemField.setText(selectedFile.getPath());
                });
                rulePane.getChildren().addAll(browseRemButton,pathRemField);
                actionPaneItems.addAll(browseRemButton,pathRemField);

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

    /**
     * Gestore dell'evento per l'azione di click del pulsante "Crea". Questo metodo cattura
     * l'input dell'utente da vari campi di testo e controlli nell'interfaccia grafica,
     * valida l'input, costruisce una nuova regola usando i dati raccolti e la aggiunge
     * all'istanza di RuleManager.
     *
     * Il metodo esegue i seguenti passaggi:
     * 1. Recupera il nome della regola dal `ruleNameTxtField`.
     * 2. Itera attraverso `triggerPaneItems` per costruire la stringa del trigger basata
     *    sull'input dell'utente dai diversi controlli (TextField, Spinner).
     * 3. Itera attraverso `actionPaneItems` per costruire la stringa dell'azione basata
     *    sull'input dell'utente da TextField.
     * 4. Controlla i valori selezionati per `triggerBox` e `actionBox` e valida gli input
     *    dell'utente.
     * 5. Se l'input è valido, crea una nuova regola e aggiorna l'interfaccia grafica
     *    di conseguenza.
     * 6. Mostra messaggi di errore se l'input non è valido.
     *
     * @param actionEvent l'evento scatenato quando il pulsante "Crea" viene cliccato
     */
    @FXML
    public void onCreateButtonClick(ActionEvent actionEvent) {
        RuleManager ruleManager = RuleManager.getInstance();

        String name = ruleNameTxtField.getText();
        boolean errorFlag = false;

        String trigger = "";
        String action = "";

        String triggerType = "";
        String actionType = "";



        Iterator<Object> iterator = triggerPaneItems.iterator();
        while (iterator.hasNext()) {
            Object item = iterator.next();
            if (item instanceof TextField) {
                if (!((TextField) item).getText().isEmpty()) {
                    errorFlag = false;
                    trigger += ((TextField) item).getText() + " ";
                } else {
                    errorFlag = true;
                }
            }
            if(item instanceof Spinner) {
                if (((Spinner) item).getValue().toString().length() == 1) {
                    trigger += "0" + ((Spinner) item).getValue() + ":";
                } else {
                    trigger += ((Spinner) item).getValue() + ":";

                }

            }
            if (item instanceof ComboBox<?>){
                if(!((ComboBox<?>) item).getValue().toString().isEmpty()) {
                    errorFlag = false;
                    trigger += ((ComboBox<?>) item).getValue() + " ";
                }else{
                    errorFlag = true;
                }
            }
            if(item instanceof DatePicker){
                if(!((DatePicker) item).getValue().toString().isEmpty()) {
                    errorFlag = false;
                    trigger += ((DatePicker) item).getValue() + " ";
                }else{
                    errorFlag = true;
                }
            }
        }

         //rimuove l'ultimo carattere e lo sostituisce con uno spazio

        iterator = actionPaneItems.iterator();
        while (iterator.hasNext()) {
            Object item = iterator.next();
            if (item instanceof TextField) {
                if (!((TextField) item).getText().isEmpty()) {
                    errorFlag = false;
                    action += ((TextField) item).getText();
                } else {
                    errorFlag = true;
                }
            }
        }
        if(Objects.equals(triggerBox.getValue(), null) || Objects.equals(actionBox.getValue(), null)) {
            errorFlag = true;
        }

        if(errorFlag || Objects.equals(name, "")) {
            labelError.setVisible(true);
        }else{
            try {
                labelError.setVisible(false);
                if (!trigger.isEmpty()){
                    trigger = trigger.substring(0,trigger.length()-1);
                }
                triggerType = triggerBox.getValue();
                actionType = actionBox.getValue();
                Rule newRule = ruleManager.createRule(triggerType, trigger, actionType, action, name);

                indexController.insertItems(newRule.toString());
                labelTriggerSelected.setText("");
                labelActionSelected.setText("");
                ruleNameTxtField.clear();
                triggerBox.setValue("");
                actionBox.setValue("");
            }catch (IllegalStateException e) {
                System.out.println(e);
                labelError.setVisible(true);
            }
        }



    }
    /**
     * Gestore dell'evento per l'azione di click del pulsante "Cancella". Questo metodo
     * cancella tutti gli input dell'utente e le selezioni nei campi del modulo,
     * incluso l'azzeramento dei testi di `labelTriggerSelected` e `labelActionSelected`,
     * la cancellazione di `ruleNameTxtField`, e il reset dei valori di `triggerBox`
     * e `actionBox`.
     *
     * @param actionEvent l'evento scatenato quando il pulsante "Cancella" viene cliccato
     */
    @FXML
    public void onClearButtonClick(ActionEvent actionEvent) {
        labelTriggerSelected.setText("");
        labelActionSelected.setText("");
        ruleNameTxtField.clear();
        triggerBox.setValue("");
        actionBox.setValue("");
    }

}

