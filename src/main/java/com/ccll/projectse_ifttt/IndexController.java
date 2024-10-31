
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
import java.util.*;

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
        triggerColumn.setCellValueFactory(cellData -> {
                    String trigger = cellData.getValue().getTrigger().toString();
                    if(trigger.contains("COMPOSITE")){
                        String concatTrigs="";
                        String val=trigger.split(";")[1];
//                        concatTrigs=stringTriggerFormatter(val);
                        return new SimpleStringProperty(val);
                    }else{
                        return new SimpleStringProperty(trigger.split(";")[0]+ " " +trigger.split(";")[1]);
                    }
                });
        actionColumn.setCellValueFactory(cellData -> {
            String action = cellData.getValue().getAction().toString();
            if(action.contains("Composite")){
                String concatActions="";
                String val=action.split(";")[1];
                String[] trigs = val.split(">>>");
                for(String s:trigs){
                    concatActions = concatActions + s.split("#")[1]+" + ";
                }
                concatActions=concatActions.substring(0,concatActions.length()-3);
                return new SimpleStringProperty(concatActions);
            }else{
                return new SimpleStringProperty(action.split(";")[0]+" "+action.split(";")[1]);
            }
        });
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

//    private String stringTriggerFormatter(String input){
//        String output="";
//        List<String> operators= new ArrayList<>();
//        List<String> triggersValues= new ArrayList<>();
//        String[] blocks = input.split("\\(");
//        for(int j=0;j<=blocks.length-1;j++){
//            int i=j;
//            if (!blocks[i].contains("@")) {
//                if(i++ <= blocks.length-1){
//                    if(!blocks[i].contains("@")){
//                        operators.add(blocks[i] + " ");
//                    }else{
//                        String operatorTemp=blocks[i--];
//                        if(Objects.equals(operatorTemp, "OR") || Objects.equals(operatorTemp, "AND") || Objects.equals(operatorTemp, "NOT")) {
//                            operators.add(blocks[i].split("@")[1]);
//                        }
//                    }
//                }
//                operators.add(blocks[j] + " ");
//
//            }else{
//                String operator2 = blocks[i].split("@")[1];
//                if(Objects.equals(operator2, "OR") || Objects.equals(operator2, "AND") || Objects.equals(operator2, "NOT")) {
//                    operators.add(operator2);
//                }
//            }
//        }
//        System.out.println("operatori:  "+operators);
//
//        String[] triggers = input.split("@");
//        for(int i=0;i<=triggers.length-1;i++){
//            String value = triggers[i].split("#")[1];
//            if (value.endsWith("))")) {
//                value = value.substring(0, value.length() - 2);
//            }else if (value.endsWith(")")){
//                value = value.substring(0, value.length() - 1);
//            }
//            triggersValues.add(value);
//        }
//        System.out.println("valori trigger:  "+triggersValues);
//
//        for(int count=0;count<triggersValues.size()-1;count++){
//            output = output + " " +triggersValues.get(count) + " " + operators.get(count);
//        }
//        output = output + " " + triggersValues.getLast();
//        System.out.println("Output:  "+output);
//        return output;
//    }

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

            stage.setWidth(660);
            stage.setHeight(410);

            stage.show();
        } catch (IOException ex) {
            System.out.println("I/O error");
        }
    }


    /*
    Cancella la regola tramite il rule manager, l'interfaccia è sincronizzata su quella lista

     */
    @FXML
    public void onDeleteRuleButton(ActionEvent actionEvent) {
        RuleManager ruleManager = RuleManager.getInstance();
        Rule selectedRule = rulesTable.getSelectionModel().getSelectedItem();
        if (selectedRule != null) {
            int selectedIndex = rulesTable.getSelectionModel().getSelectedIndex();
            ruleManager.removeRule(selectedIndex);
            updateRulesList(ruleManager);
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

    public void updateRulesList(RuleManager rm) {
        rulesList.clear();
        rulesList.addAll(rm.getRules());
        rulesTable.setItems(rulesList);
    }

    public void setStage(Stage stage){
        this.stage=stage;
    }
}
