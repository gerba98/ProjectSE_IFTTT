package com.ccll.projectse_ifttt;

import com.ccll.projectse_ifttt.Rule.Rule;
import com.ccll.projectse_ifttt.Rule.RuleManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller per la gestione della vista principale dell'applicazione.
 */
public class IndexController {

    @FXML
    private Label errorLabel;

    @FXML
    private Button createRuleButton;

    @FXML
    private Button deleteRuleButton;

    @FXML
    private TableView<Rule> rulesTable;

    @FXML
    private TableColumn<Rule, String> nameColumn;
    @FXML
    private TableColumn<Rule, String> triggerColumn;
    @FXML
    private TableColumn<Rule, String> actionColumn;

    private final ObservableList<Rule> rulesList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        triggerColumn.setCellValueFactory(new PropertyValueFactory<>("trigger"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));

        // Imposta la larghezza delle colonne
        nameColumn.setPrefWidth(180);
        triggerColumn.setPrefWidth(180);
        actionColumn.setPrefWidth(180);

        rulesTable.setItems(rulesList);
    }


    @FXML
    public void OnCreateRuleClick() {
        errorLabel.setVisible(false);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("create-rule.fxml"));
            Parent root1 = fxmlLoader.load();

            CreateRuleController secondViewController = fxmlLoader.getController();
            secondViewController.setFXMLDocumentController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
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
        Rule selectedRule = rulesTable.getSelectionModel().getSelectedItem();
        if (selectedRule != null) {
            int selectedIndex = rulesTable.getSelectionModel().getSelectedIndex();
            ruleManager.removeRule(selectedIndex);
            rulesTable.getItems().remove(selectedIndex);
            errorLabel.setVisible(false);
        } else {
            errorLabel.setVisible(true);
        }
    }

    public void insertItems(Rule rule) {
        rulesList.add(rule);
        rulesTable.setItems(rulesList);
    }
}
