
package com.ccll.projectse_ifttt;

import com.ccll.projectse_ifttt.Rule.PeriodicRule;
import com.ccll.projectse_ifttt.Rule.Rule;
import com.ccll.projectse_ifttt.Rule.RuleManager;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
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
import java.util.Objects;

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
    private Button activateButton;

    @FXML
    private Button deactivateButton;

    @FXML
    private TableView<Rule> rulesTable;

    @FXML
    private TableColumn<Rule, String> nameColumn;
    @FXML
    private TableColumn<Rule, String> triggerColumn;
    @FXML
    private TableColumn<Rule, String> actionColumn;
    @FXML
    private TableColumn<Rule, String> stateColumn;
    @FXML
    private TableColumn<Rule, String> typeColumn;  // Colonna per la tipologia della regola
    @FXML
    private Stage stage;

    private final ObservableList<Rule> rulesList = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        triggerColumn.setCellValueFactory(new PropertyValueFactory<>("trigger"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        stateColumn.setCellValueFactory(cellData -> statePropertyFunction(cellData));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));  // Aggiunto per la colonna "Tipologia"

        RuleManager ruleManager = RuleManager.getInstance();
        Platform.runLater(() ->{
            Stage stage = (Stage) rulesTable.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                ruleManager.saveRules();
            });
        });
        rulesList.addAll(ruleManager.getRules());
        rulesTable.setItems(ruleManager.getRules());
    }

    private ObservableStringValue statePropertyFunction(TableColumn.CellDataFeatures<Rule, String> cellData) {
        Rule rule = cellData.getValue();
        if (Objects.equals(cellData.getValue().getType(), "PeriodicRule")) {
            PeriodicRule pr = (PeriodicRule) rule;
            return Bindings.createStringBinding(() -> {
                if (pr.stateProperty().get()) {
                    return "Active";
                }else{
                    return pr.reactivatedProperty().get() ? "Temporally deactivated" : "Inactive";
                }
            }, pr.stateProperty(), pr.reactivatedProperty());
        } else {
            return Bindings.createStringBinding(() -> rule.stateProperty().get() ? "Active" : "Inactive", rule.stateProperty());
        }
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

    @FXML
    public void onActivateButtonClick(ActionEvent actionEvent) {
        Rule selectedRule = rulesTable.getSelectionModel().getSelectedItem();
        if(Objects.equals(selectedRule.getType(), "PeriodicRule") && selectedRule != null){

            PeriodicRule rule = (PeriodicRule) selectedRule;
            rule.setReactivated(true);
            selectedRule.setState(true);

        }else if (selectedRule != null) {
            selectedRule.setState(true);  // Imposta lo stato su true (Active)
            rulesTable.refresh();  // Aggiorna la tabella per riflettere il cambiamento
            errorLabel.setVisible(false);
        } else {
            errorLabel.setVisible(true);
        }
    }

    @FXML
    public void onDeactivateButtonClick(ActionEvent actionEvent) {
        Rule selectedRule = rulesTable.getSelectionModel().getSelectedItem();
        if(Objects.equals(selectedRule.getType(), "PeriodicRule") && selectedRule != null){

            PeriodicRule rule = (PeriodicRule) selectedRule;
            rule.setReactivated(false);
            selectedRule.setState(false);

        }else if (selectedRule != null) {
            selectedRule.setState(false);  // Imposta lo stato su false (Inactive)
            rulesTable.refresh();  // Aggiorna la tabella per riflettere il cambiamento
            errorLabel.setVisible(false);
        } else {
            errorLabel.setVisible(true);
        }

    }


    public void insertItems(Rule rule) {
        rulesList.add(rule);
        rulesTable.setItems(rulesList);
    }

    public void setStage(Stage stage){
        this.stage=stage;
    }
}
