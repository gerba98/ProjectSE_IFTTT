package com.ccll.projectse_ifttt;

import com.ccll.projectse_ifttt.Rule.RuleManager;
import com.ccll.projectse_ifttt.Rule.RulePersistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Controller per la gestione della vista principale dell'applicazione.
 * <p>
 * Questa classe gestisce le interazioni con vari elementi dell'interfaccia utente,
 * inclusi pulsanti e un ListView per mostrare elementi di una lista di regole.
 */
public class IndexController {

    @FXML
    private Label errorLabel;
    @FXML
    private ListView<String> listView;

    @FXML
    private Button createRuleButton;

    @FXML
    private Button deleteRuleButton;

    @FXML
    private Button saveRuleButton;

    @FXML
    private Button cancelCreationButton;

    @FXML
    private ObservableList<String> rulesList = FXCollections.observableArrayList();

    private RulePersistence rulePers;


    /**
     * Inizializza il ListView con gli elementi dalla lista osservabile.
     * <p>
     * Questo metodo viene chiamato automaticamente quando il file FXML viene caricato.
     * Imposta gli elementi del ListView come rulesList, permettendo al ListView
     * di visualizzare qualsiasi elemento presente nella lista osservabile.
     */
    @FXML
    public void initialize() {
        rulePers = new RulePersistence();
        initRules();
        listView.setItems(rulesList);
    }

    /**
     * Inserisce un elemento nella lista osservabile e aggiorna il ListView.
     *
     * @param rule L'elemento da inserire nella lista.
     */
    @FXML
    public void insertItems(String rule) {
        rulesList.add(rule);
        rulePers.saveRules(rulesList);
        listView.setItems(rulesList);
    }
    /**
     * Inizializza la lista delle regole caricando da file e crea gli oggetti regola.
     *
     * il formato del csv è -> 0 Rule, 1 Name, 2 trigger, 3 triggerType, 4 trigger value,5 action, 6 action type, 7 action value
     */
    public void initRules(){
        rulesList = rulePers.loadRules();
        RuleManager ruleManager = RuleManager.getInstance();
        for (String string:rulesList){
            String[] parts = string.split(";");
            ruleManager.createRule(parts[1],parts[3],parts[4],parts[6],parts[7]);
        }
    }


    /**
     * Metodo gestore dell'evento che viene attivato quando il pulsante "Crea Regola" viene cliccato.
     * Carica il layout "create-rule.fxml" e lo visualizza in una nuova finestra modale.
     * <p>
     * Questo metodo esegue i seguenti passaggi:
     * 1. Carica la risorsa FXML "create-rule.fxml".
     * 2. Recupera il controller associato con il FXML caricato.
     * 3. Imposta l'istanza del controller corrente al controller FXML.
     * 4. Inizializza e mostra un nuovo stage (finestra) con il titolo "Crea Regola".
     * <p>
     * In caso di IOException durante il processo di caricamento del FXML, un messaggio di errore
     * viene stampato sull'output standard.
     */
    @FXML
    public void OnCreateRuleClick() {

        errorLabel.setVisible(false);
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
        } catch (IOException ex) {
            System.out.println("I/O error");
        }
    }

    @FXML
    public void onDeleteRuleButton(ActionEvent actionEvent) {
        RuleManager ruleManager = RuleManager.getInstance();
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            ruleManager.removeRule(selectedIndex);
            rulePers.deleteRules(selectedIndex);
            listView.getItems().remove(selectedIndex);
            errorLabel.setVisible(false);
        } else {
            errorLabel.setVisible(true);
        }
    }

}