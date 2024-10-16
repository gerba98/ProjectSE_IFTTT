package com.ccll.projectse_ifttt;

import com.ccll.projectse_ifttt.Rule.RuleManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Controller per la gestione della vista principale dell'applicazione.
 *
 * Questa classe gestisce le interazioni con vari elementi dell'interfaccia utente,
 * inclusi pulsanti e un ListView per mostrare elementi di una lista di regole.
 */
public class IndexController {

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


    /**
     * Inizializza il ListView con gli elementi dalla lista osservabile.
     *
     * Questo metodo viene chiamato automaticamente quando il file FXML viene caricato.
     * Imposta gli elementi del ListView come rulesList, permettendo al ListView
     * di visualizzare qualsiasi elemento presente nella lista osservabile.
     */
    @FXML
    public void initialize()
    {
        listView.setItems(rulesList);
    }

    /**
     * Inserisce un elemento nella lista osservabile e aggiorna il ListView.
     *
     * @param name Il nome dell'elemento da inserire nella lista.
     */
    @FXML
    public void insertItems(String name){
        rulesList.add(name);
        listView.setItems(rulesList);
    }

    /**
     * Disabilita il pulsante "Crea Regola".
     *
     * Questo metodo imposta la proprietà disable del pulsante "Crea Regola" su true,
     * impedendo all'utente di interagire con esso.
     */
    @FXML
    public void disableButton(){
        CreateRuleButton.setDisable(true);
    }

    /**
     * Metodo gestore dell'evento che viene attivato quando il pulsante "Crea Regola" viene cliccato.
     * Carica il layout "create-rule.fxml" e lo visualizza in una nuova finestra modale.
     *
     * Questo metodo esegue i seguenti passaggi:
     * 1. Carica la risorsa FXML "create-rule.fxml".
     * 2. Recupera il controller associato con il FXML caricato.
     * 3. Imposta l'istanza del controller corrente al controller FXML.
     * 4. Inizializza e mostra un nuovo stage (finestra) con il titolo "Crea Regola".
     *
     * In caso di IOException durante il processo di caricamento del FXML, un messaggio di errore
     * viene stampato sull'output standard.
     */
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

    public void onDeleteRuleButton(ActionEvent actionEvent) {
        RuleManager ruleManager = RuleManager.getInstance();
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            ruleManager.removeRule(selectedIndex);
            listView.getItems().remove(selectedIndex);
        } else {
            System.out.println("Nessuna regola selezionata per la cancellazione.");
        }
    }

}