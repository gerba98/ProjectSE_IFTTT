package com.ccll.projectse_ifttt;
import com.ccll.projectse_ifttt.utils.Utils;

import com.ccll.projectse_ifttt.Rule.Rule;
import com.ccll.projectse_ifttt.Rule.RuleManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.time.LocalTime;
import java.util.*;

/**
 * Controller per la creazione di nuove regole nel sistema IFTTT. Gestisce
 * l'interfaccia utente che consente agli utenti di definire e configurare
 * regole basate su trigger e azioni.
 */
public class CreateRuleController {

    private static final String TIME_OF_DAY = "Time of the Day";
    private static final String DAY_OF_WEEK = "Day of the week";
    private static final String DAY_OF_MONTH = "Day of the month";
    private static final String DATE = "Date";
    private static final String FILE_EXISTENCE = "File existence";
    private static final String FILE_DIMENSION = "File dimension";
    private static final String STATUS_PROGRAM = "Status program";

    private static final String DISPLAY_MESSAGE = "Display message";
    private static final String PLAY_AUDIO = "Play Audio";
    private static final String WRITE_STRING = "Write string";
    private static final String COPY_FILE = "Copy File";
    private static final String MOVE_FILE = "Move file";
    private static final String REMOVE_FILE = "Remove file";
    private static final String EXECUTE_PROGRAM = "Execute Program";

    private static final String COMPOSITE_FIRST = "composite first";
    private static final String COMPOSITE_SECOND = "composite second";
    private static final String NORMAL = "Normal";

    private static final String COMPOSITE_ACTION = "composite action";



    // FXML injected fields
    @FXML
    private Label CTLabelError;
    @FXML
    private Label CALabelError;
    @FXML
    private ListView<String> CAListView;
    @FXML
    private Pane CAPaneResult;
    @FXML
    private TextField compositeActionName;
    @FXML
    private ComboBox<String> CABox;
    @FXML
    private AnchorPane anchorPaneCompositeAction;
    @FXML
    private Button compositeActionButton;
    @FXML
    private Button clearCompositePageBtn;
    @FXML
    private TextField compositeTrigName;
    @FXML
    private CheckBox notOperator1;
    @FXML
    private CheckBox notOperator2;
    @FXML
    private ComboBox<String> CTBox1;
    @FXML
    private ComboBox<String> CTBox2;
    @FXML
    private ComboBox<String> operatorBox;
    @FXML
    private AnchorPane anchorPaneCompositeTrigger;
    @FXML
    private Button compositeTriggerButton;
    @FXML
    private Button backRuleButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private CheckBox periodicCheckBox;
    @FXML
    private CheckBox singleCheckBox;
    @FXML
    private Label labelError = new Label("Missing info");
    @FXML
    private Pane rulePane;
    @FXML
    private Label labelTriggerSelected;
    @FXML
    private Label labelCompositTrigSelected1;
    @FXML
    private Label labelCompositTrigSelected2;
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
    private IndexController indexController;

    // Observable Lists
    @FXML
    private final ObservableList<String> operators = FXCollections.observableArrayList("AND", "OR");

    @FXML
    private final ObservableList<String> triggersList = FXCollections.observableArrayList(
            TIME_OF_DAY, DAY_OF_WEEK, DAY_OF_MONTH, DATE, FILE_EXISTENCE, FILE_DIMENSION, STATUS_PROGRAM
    );

    @FXML
    private final ObservableList<String> actionsList = FXCollections.observableArrayList(
            DISPLAY_MESSAGE, PLAY_AUDIO, WRITE_STRING, COPY_FILE, MOVE_FILE, REMOVE_FILE, EXECUTE_PROGRAM
    );

    private ObservableList<String> compositeActionList = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Object> triggerPaneItems = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Object> actionPaneItems = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Object> periodicRuleItems = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Object> compositeTrigPaneItems1 = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Object> compositeTrigPaneItems2 = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Object> compositeActionPaneItems = FXCollections.observableArrayList();
    // Instance variables
    RuleManager ruleManager;
    private String filePath;
    private String selectedFilePath;
    private String selectedDirectoryPath;
    private String filePathToRemove;
    private String selectedProgramPath;
    private String programCommand;

    HashMap<String, String> compositeTriggerNames = new HashMap<>();
    HashMap<String, List<String>> compositeActionNamesHash = new HashMap<>();
    List<String> listForHash = new ArrayList<String>();

    @FXML
    public void initialize() {
        ruleManager = RuleManager.getInstance();
        setupComboBoxes();
        setupCheckBoxes();
    }

    @FXML
    public void setFXMLDocumentController(IndexController indexController) {
        this.indexController = indexController;
    }

    private void setupComboBoxes() {
        triggerBox.setItems(triggersList);
        actionBox.setItems(actionsList);

        CTBox1.setItems(triggersList);
        CTBox2.setItems(triggersList);

        operatorBox.setItems(operators);
        operatorBox.setPrefWidth(70);

        CABox.setItems(actionsList);

        CTBox1.setOnAction(e -> handleTriggerSelection(COMPOSITE_FIRST));
        CTBox2.setOnAction(e -> handleTriggerSelection(COMPOSITE_SECOND));
        triggerBox.setOnAction(e -> handleTriggerSelection(NORMAL));
        actionBox.setOnAction(e -> handleActionSelection(NORMAL));
        CABox.setOnAction(e -> handleActionSelection(COMPOSITE_ACTION));
    }

    private void setupCheckBoxes() {
        periodicCheckBox.setOnAction(e -> createPeriodicRule());

        // Mutual exclusion for checkboxes
        periodicCheckBox.selectedProperty().addListener((obs, old, newVal) ->
                singleCheckBox.setDisable(newVal));

        singleCheckBox.selectedProperty().addListener((obs, old, newVal) -> {
            periodicCheckBox.setDisable(newVal);
            if (newVal) createSingleRule();
        });
    }


    private void handleTriggerSelection(String value) {
        if(Objects.equals(value, NORMAL)){
            if (!triggerBox.getValue().isEmpty()) {
                compositeTriggerButton.setVisible(false);
            }
            clearPaneItems(triggerPaneItems, rulePane);
            createTriggerItem(labelTriggerSelected, triggerBox.getSelectionModel().getSelectedItem(), value);
        }else if (Objects.equals(value, COMPOSITE_FIRST)){
            clearPaneItems(compositeTrigPaneItems1, anchorPaneCompositeTrigger);
            createTriggerItem(labelTriggerSelected, CTBox1.getSelectionModel().getSelectedItem(), value);
        }else if (Objects.equals(value, COMPOSITE_SECOND)){
            clearPaneItems(compositeTrigPaneItems2, anchorPaneCompositeTrigger);
            createTriggerItem(labelTriggerSelected, CTBox2.getSelectionModel().getSelectedItem(), value);
        }

    }

    private void handleActionSelection(String value) {

        if(Objects.equals(value, NORMAL)){
            if (!actionBox.getValue().isEmpty()) {
                compositeActionButton.setVisible(false);
            }
            clearPaneItems(actionPaneItems, rulePane);
            createActionItem(labelActionSelected, actionBox.getSelectionModel().getSelectedItem(), value);
        }else if (Objects.equals(value, COMPOSITE_ACTION)){
            clearPaneItems(compositeActionPaneItems, anchorPaneCompositeAction);
            createActionItem(labelActionSelected, CABox.getSelectionModel().getSelectedItem(), value);
        }
    }

    private void clearPaneItems(ObservableList<Object> items, Pane pane) {

        Iterator<Object> iterator = items.iterator();
        while (iterator.hasNext()) {
            pane.getChildren().remove(iterator.next());
            iterator.remove();
        }
    }

    @FXML
    private void clearCompositeClick(){
        clearCompositePage();
    }

    @FXML
    private void CompositeTriggerManagement() {
        anchorPane.setVisible(false);
        anchorPaneCompositeTrigger.setVisible(true);
    }
    @FXML
    private void compositeActionManagement(){
        anchorPane.setVisible(false);
        anchorPaneCompositeAction.setVisible(true);
        compositeActionName.clear();
        compositeActionList.clear();
    }

    @FXML
    private void backButton() {
        anchorPane.setVisible(true);
        anchorPaneCompositeTrigger.setVisible(false);
        anchorPaneCompositeAction.setVisible(false);
        clearCompositePage();
        clearCompositeAction();
    }

    @FXML
    private void clearCompositePage(){
        compositeTrigName.clear();
        operatorBox.setValue("");
        notOperator1.setSelected(false);
        notOperator2.setSelected(false);
        CTLabelError.setVisible(false);
        CTBox1.setValue("");
        CTBox2.setValue("");
    }

    @FXML
    private void createPeriodicRule() {
        Spinner<Integer> hourSpinner = createSpinner(23, LocalTime.now().getHour());
        Spinner<Integer> minuteSpinner = createSpinner(59, LocalTime.now().getMinute());
        Spinner<Integer> daySpinner = createSpinner(1000, LocalTime.now().getSecond());

        setupPeriodicRuleLayout(daySpinner, hourSpinner, minuteSpinner);
    }
    private void setupPeriodicRuleLayout(Spinner<Integer> day, Spinner<Integer> hour, Spinner<Integer> minute) {

        Label hoursLabel = new Label("Hours");
        Label minutesLabel = new Label("Minutes");
        Label daysLabel = new Label("Days");

        hour.setEditable(true);
        hour.setPrefWidth(55);
        minute.setEditable(true);
        minute.setPrefWidth(55);
        day.setEditable(true);
        day.setPrefWidth(55);

        day.setLayoutX(150);
        day.setLayoutY(339);
        daysLabel.setLayoutX(150);
        daysLabel.setLayoutY(322);

        hour.setLayoutX(212.0);
        hour.setLayoutY(339);
        hoursLabel.setLayoutX(212);
        hoursLabel.setLayoutY(322);

        minute.setLayoutX(274.0);
        minute.setLayoutY(339);
        minutesLabel.setLayoutX(272);
        minutesLabel.setLayoutY(322);

        rulePane.getChildren().addAll(day, hour, minute, daysLabel, hoursLabel, minutesLabel);
        periodicRuleItems.addAll(day, hour, minute, daysLabel, hoursLabel, minutesLabel);
    }
    private Spinner<Integer> createSpinner(int max, int initial) {
        Spinner<Integer> spinner = new Spinner<>(0, max, initial);
        spinner.setEditable(true);
        spinner.setPrefWidth(55);
        return spinner;
    }


    @FXML
    private void createSingleRule(){}

    @FXML
    public void createCompositeTrigger(ActionEvent actionEvent){
        String operator = operatorBox.getValue();
        String triggerType1 = CTBox1.getValue();
        String triggerDetails1 = collectTriggerDetails(compositeTrigPaneItems1, CTBox1);
        String triggerType2 = CTBox2.getValue();
        String triggerDetails2 = collectTriggerDetails(compositeTrigPaneItems2, CTBox2);

        boolean not1 = notOperator1.isSelected();
        boolean not2 = notOperator2.isSelected();

        String compositeString = CompositeTriggerFormatter.formatCompositeTrigger(
                operator, not1, triggerType1, triggerDetails1,
                operator, not2, triggerType2, triggerDetails2,compositeTriggerNames
        );
        // Set the formatted string as the trigger
        compositeTriggerNames.put(compositeTrigName.getText(), "composite;"+compositeString);
        triggersList.add(compositeTrigName.getText());
        triggerBox.setItems(triggersList);

        clearCompositePage();
        CTLabelError.setVisible(false);


    }

    @FXML
    public void CACreateButton(){
        if(Objects.equals(compositeActionName.getText(), "") || compositeActionList.isEmpty()){
            CALabelError.setVisible(true);
        }else{
            actionsList.add(compositeActionName.getText());
            actionBox.setItems(actionsList);
            compositeActionNamesHash.put(compositeActionName.getText(), listForHash);
            CALabelError.setVisible(false);
            clearCompositeAction();
            System.out.println("Dizionarion dopo aver creato l'azione: " + compositeActionNamesHash.toString() + "\n" + compositeActionNamesHash);
        }
    }

    @FXML
    public void CAAddButton() {
        String actionType = CABox.getValue();
        String actionValue = collectActionDetails(compositeActionPaneItems, CABox);
        if(actionValue.equals("")){
            CALabelError.setVisible(true);
        }else{
            String result = actionType + "#" + actionValue;
            System.out.println("dizionarion quadno aggiungo una azione singola: " + compositeActionList.toString());
            if(compositeActionNamesHash.containsKey(actionType)){
                List<String> concatenateActions = compositeActionNamesHash.get(actionType);
                compositeActionList.addAll(concatenateActions);
                listForHash.addAll(concatenateActions);
            }else{
                compositeActionList.add(result);
                listForHash.add(result);
            }

            CAListView.setItems(compositeActionList);
            CALabelError.setVisible(false);
        }

    }

    @FXML
    public void CARemoveButton() {
        String selectedAction = CAListView.getSelectionModel().getSelectedItem();

        if (selectedAction != null) {
            compositeActionList.remove(selectedAction);

            CAListView.setItems(compositeActionList);

            String actionName = compositeActionName.getText();
            if (compositeActionNamesHash.containsKey(actionName)) {
                compositeActionNamesHash.get(actionName).remove(selectedAction);

                if (compositeActionNamesHash.get(actionName).isEmpty()) {
                    compositeActionNamesHash.remove(actionName);
                }
            }
        }
    }

    public static class CompositeTriggerFormatter {
        /**
         * Formats trigger components into a composite trigger string.
         * Example output: AND(AND(NOTTime of the day#19:45@Day of the week#SUNDAY)@Day of the Month#10-27)
         */
        public static String formatCompositeTrigger(String operator1, boolean not1, String triggerType1, String triggerDetails1,
                                                    String operator2, boolean not2, String triggerType2, String triggerDetails2, HashMap<String,String> dict) {
            String trigger1 = formatSingleTrigger(triggerType1, triggerDetails1, not1, dict);


            String trigger2 = formatSingleTrigger(triggerType2, triggerDetails2, not2, dict);


            // Combine using the operators
            return String.format("%s(%s@%s)", operator1, trigger1, trigger2);
        }

        private static String formatSingleTrigger(String triggerType, String details, boolean not,HashMap<String,String> dict) {
            String notPrefix = not ? "NOT" : "";
            if(dict.containsKey(triggerType)){
                triggerType = "";
            }
            if(details.startsWith("composite;")){
                details = details.replace("composite;", "");
            }
            String result = "";
            if(details.startsWith("AND") || details.startsWith("OR")){
                if(notPrefix=="NOT") {
                    result = String.format("%s(%s%s)", notPrefix, triggerType, details);
                }else{
                    result = String.format("%s%s%s", notPrefix, triggerType, details);
                }
            }else{
                if(notPrefix=="NOT"){
                    result = String.format("%s(%s#%s)", notPrefix, triggerType, details);
                }else{
                    result = String.format("%s%s#%s", notPrefix, triggerType, details);
                }
            }

            if(result.startsWith("#")){
                result = result.substring(1);
            }
            return result;
        }
    }

    @FXML
    public void onCreateButtonClick(ActionEvent actionEvent) {
        if (Objects.equals(triggerBox.getValue(), null) || Objects.equals(actionBox.getValue(), null)) {
            labelError.setVisible(true);
        }else{
            try {
                // Recupera gli input dalla UI
                String name = ruleNameTxtField.getText();
                String triggerType = triggerBox.getValue();
                if(compositeTriggerNames.containsKey(triggerType)){
                    triggerType = "composite";
                }

                String triggerDetails = collectTriggerDetails(triggerPaneItems, triggerBox);
                if(triggerDetails.startsWith("composite")){
                    triggerDetails = triggerDetails.split(";")[1];
                }

                String actionType = actionBox.getValue();
                if(compositeActionNamesHash.containsKey(actionType)){
                    actionType = "composite";
                }

                String actionDetails = collectActionDetails(actionPaneItems, actionBox);
                if(Objects.equals(actionType, "composite")){
                    List<String> list = compositeActionNamesHash.get(actionBox.getValue());
                    actionDetails = String.join(">>>", list);
                }


                RuleBuilder builder = new RuleBuilder()
                        .setName(name)
                        .setTriggerType(triggerType)
                        .setActionType(actionType)
                        .setTriggerDetails(triggerDetails)
                        .setActionDetails(actionDetails);

                if (periodicCheckBox.isSelected()) {

                    String period = collectPeriodDetails(); // Metodo per raccogliere i dettagli del periodo
                    builder.setPeriodic(period);
                } else if (singleCheckBox.isSelected()) {
                    builder.setSingle();
                }

                // Crea la regola
                Rule newRule = builder.build();

                // Aggiungi la nuova regola alla tabella nel IndexController
                indexController.insertItems(newRule);
                clearUI();

            } catch (IllegalArgumentException e) {
                labelError.setVisible(true);
            }
        }
    }

    private String collectTriggerDetails(ObservableList<Object> paneList, ComboBox<String> box) {
        String trigger = "";
        Iterator<Object> iterator = paneList.iterator();
        switch (box.getValue()){
            case "Time of the Day":
                while (iterator.hasNext()) {
                    Object item = iterator.next();
                    if(item instanceof Spinner){
                        if (((Spinner<?>) item).getValue().toString().length() == 1) {
                            trigger += "0" + ((Spinner<?>) item).getValue() + ":";
                        } else {
                            trigger += ((Spinner<?>) item).getValue() + ":";
                        }
                    }
                }
                break;
            case "Day of the week":
                while (iterator.hasNext()) {
                    Object item = iterator.next();
                    if(item instanceof ComboBox<?>){
                        if (!((ComboBox<?>) item).getValue().toString().isEmpty()) {
                            trigger += ((ComboBox<?>) item).getValue() + " ";
                        }
                    }
                }
                break;
            case "Date":
            case "Day of the month":
                while (iterator.hasNext()) {
                    Object item = iterator.next();
                    if (item instanceof DatePicker) {
                        if (!((DatePicker) item).getValue().toString().isEmpty()) {
                            trigger += ((DatePicker) item).getValue() + " ";
                        }
                    }
                }
                break;
            case "File existence":
                while (iterator.hasNext()) {
                    Object item = iterator.next();
                    if (item instanceof TextField) {
                        if (!((TextField) item).getText().isEmpty()) {
                            trigger += ((TextField) item).getText() + "-";
                        }
                    }
                }
                break;
            case "File dimension":
                while (iterator.hasNext()) {
                    Object item = iterator.next();
                    if (item instanceof TextField) {

                        if (!((TextField) item).getText().isEmpty()) {
                            trigger += ((TextField) item).getText() + "-";
                        }

                    }else if (item instanceof ComboBox<?>) {

                        if (!((ComboBox<?>) item).getValue().toString().isEmpty()) {
                            trigger += ((ComboBox<?>) item).getValue() + "-";
                        }

                    }else if (item instanceof Spinner<?>) {
                        if(!((Spinner<?>) item).getValue().toString().isEmpty()){
                            trigger += ((Spinner<?>) item).getValue() + "-";
                        }
                    }
                }
                break;
            case "Status program":
                while (iterator.hasNext()) {
                    Object item = iterator.next();
                    if(item instanceof TextField){
                        if(((TextField) item).getText().isEmpty() && paneList.indexOf(item) == 0){
                            trigger += " " + "-";
                        }else if(!((TextField) item).getText().isEmpty()){
                            trigger += ((TextField) item).getText() + "-";
                        }

                    }
                }
                break;
            default:
                trigger = compositeTriggerNames.get(box.getValue()) + " ";
        }
        trigger = trigger.substring(0, trigger.length() - 1);
        return trigger;
    }

    private String collectActionDetails(ObservableList<Object> paneList, ComboBox<String> box) {
        String action = "";
        Iterator<Object> iterator;
        iterator = paneList.iterator();
        switch (box.getValue()) {
            case "Remove file":
            case "Copy File":
            case "Write string":
            case "Move file":
            case "Play Audio":
            case"Display message":
                while (iterator.hasNext()) {
                    Object item = iterator.next();
                    if (item instanceof TextField) {

                        if (!((TextField) item).getText().isEmpty()) {
                            action += ((TextField) item).getText() + "-";
                        }
                    }
                }

                break;
            case "Execute Program":
                while (iterator.hasNext()) {
                    Object item = iterator.next();
                    if (item instanceof TextField) {
                        if (((TextField) item).getText().isEmpty() && paneList.indexOf(item) == 1) {
                        } else if (!((TextField) item).getText().isEmpty()) {
                            action += ((TextField) item).getText() + "-";
                        }
                    }
                }
                break;
            default:
                action = compositeActionNamesHash.get(box.getValue()) + " ";
                break;

        }
        if(!action.isEmpty()){
            action = action.substring(0, action.length() - 1);
        }
        return action;

    }

    private String collectPeriodDetails() {
        String stringToWrite = "";
        String ruleType = "";
        if(periodicCheckBox.isSelected()){
            for (Object item : periodicRuleItems) {
                if (item instanceof Spinner<?>) {
                    stringToWrite += ((Spinner<?>) item).getValue().toString() + ":"; // Recupera la stringa da scrivere
                }
            }
            stringToWrite = stringToWrite.substring(0, stringToWrite.length() - 1);
            ruleType = "periodicrule-" + stringToWrite;

        }
        return ruleType;
    }

    private void clearUI() {
        // Pulisce la UI dopo la creazione della regola
        ruleNameTxtField.clear();
        triggerBox.setValue("");
        actionBox.setValue("");
        labelTriggerSelected.setText("");
        labelActionSelected.setText("");
        singleCheckBox.setSelected(false);
        periodicCheckBox.setSelected(false);
        labelError.setVisible(false);
        compositeTriggerButton.setVisible(true);
        compositeActionButton.setVisible(true);
        CALabelError.setVisible(false
        );
        clearPeriodicRule();
    }

    private void clearPeriodicRule(){
        Iterator<Object> iterator = periodicRuleItems.iterator();

        while (iterator.hasNext()) {
            Object item = iterator.next();
            rulePane.getChildren().remove(item);
            iterator.remove();
        }
    }

    private void createTriggerItem(Label label, String text, String value) {

        switch (text) {
            case "Time of the Day":

                Spinner<Integer> hourSpinner = Utils.spinnerItem(0, 23, LocalTime.now().getHour());
                Spinner<Integer> minuteSpinner = Utils.spinnerItem(0, 59, LocalTime.now().getMinute());

                if(Objects.equals(value, NORMAL)){
                    label.setText("Time of the Day (hh:mm)");
                    Utils.layoutSpinner(hourSpinner, 285, 142, 55);
                    Utils.layoutSpinner(minuteSpinner, 350, 142, 55);
                    rulePane.getChildren().addAll(hourSpinner, minuteSpinner);
                    triggerPaneItems.addAll(hourSpinner, minuteSpinner);
                } else if (Objects.equals(value, COMPOSITE_FIRST)) {
                    Utils.layoutSpinner(hourSpinner, 350, 150, 55);
                    Utils.layoutSpinner(minuteSpinner, 420, 150, 55);
                    anchorPaneCompositeTrigger.getChildren().addAll(hourSpinner, minuteSpinner);
                    compositeTrigPaneItems1.addAll(hourSpinner, minuteSpinner);
                }else if(Objects.equals(value, COMPOSITE_SECOND)){
                    Utils.layoutSpinner(hourSpinner, 350, 276, 55);
                    Utils.layoutSpinner(minuteSpinner, 420, 276, 55);
                    anchorPaneCompositeTrigger.getChildren().addAll(hourSpinner, minuteSpinner);
                    compositeTrigPaneItems2.addAll(hourSpinner, minuteSpinner);
                }
                break;
            case "Day of the week":


                ComboBox<String> dayOfWeek = Utils.comboItem(FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

                if(Objects.equals(value, NORMAL)){
                    label.setText("Day of the week (Monday, etc..)");
                    Utils.layoutCombo(dayOfWeek, 285, 142);

                    rulePane.getChildren().add(dayOfWeek);
                    triggerPaneItems.add(dayOfWeek);
                } else if (Objects.equals(value, COMPOSITE_FIRST)) {
                    Utils.layoutCombo(dayOfWeek, 350, 153);


                    anchorPaneCompositeTrigger.getChildren().add(dayOfWeek);
                    compositeTrigPaneItems1.add(dayOfWeek);
                }else if(Objects.equals(value, COMPOSITE_SECOND)){
                    Utils.layoutCombo(dayOfWeek, 350, 276);

                    anchorPaneCompositeTrigger.getChildren().add(dayOfWeek);
                    compositeTrigPaneItems2.add(dayOfWeek);
                }
                break;
            case "Date":
            case "Day of the month":

                DatePicker dayMonthField = Utils.datePickerItem();

                if(Objects.equals(value, NORMAL)){
                    if (text.equals("Day of the month")){
                        label.setText("Choose a day of the month");
                    }else{
                        label.setText("Choose a date");
                    }
                    Utils.layoutDatePicker(dayMonthField, 285, 142);

                    rulePane.getChildren().add(dayMonthField);
                    triggerPaneItems.add(dayMonthField);
                } else if (Objects.equals(value, COMPOSITE_FIRST)) {
                    Utils.layoutDatePicker(dayMonthField, 360, 152);


                    anchorPaneCompositeTrigger.getChildren().add(dayMonthField);
                    compositeTrigPaneItems1.add(dayMonthField);
                }else if(Objects.equals(value, COMPOSITE_SECOND)){
                    Utils.layoutDatePicker(dayMonthField, 360, 274);

                    anchorPaneCompositeTrigger.getChildren().add(dayMonthField);
                    compositeTrigPaneItems2.add(dayMonthField);
                }

                break;
            case "File existence":
                TextField pathFolderField = new TextField();
                Label fileNameLab = new Label("Insert file name");
                Button browseDirButton = Utils.ButtonDirectoryItem(pathFolderField);
                TextField fileNameField = new TextField();
                if(Objects.equals(value, NORMAL)){
                    label.setText("Choose a directory");
                    Utils.layoutFiled(pathFolderField, 100, 358, 142, "directory");
                    Utils.layoutButton(browseDirButton, 285, 142);
                    Utils.layoutLabel(fileNameLab,460,125);
                    Utils.layoutFiled(fileNameField, 100, 460, 142, "file name");

                    rulePane.getChildren().addAll(pathFolderField,fileNameField,browseDirButton,fileNameLab);
                    triggerPaneItems.addAll(pathFolderField,fileNameField,browseDirButton,fileNameLab);

                } else if (Objects.equals(value, COMPOSITE_FIRST)) {
                    Utils.layoutFiled(pathFolderField, 100, 420, 153, "directory");
                    Utils.layoutButton(browseDirButton, 345, 153);
                    Utils.layoutLabel(fileNameLab,526,135);
                    Utils.layoutFiled(fileNameField, 100, 525, 153, "file name");

                    anchorPaneCompositeTrigger.getChildren().addAll(pathFolderField,fileNameField,browseDirButton,fileNameLab);
                    compositeTrigPaneItems1.addAll(pathFolderField,fileNameField,browseDirButton,fileNameLab);
                }else if(Objects.equals(value, COMPOSITE_SECOND)){
                    Utils.layoutFiled(pathFolderField, 100, 420, 276, "directory");
                    Utils.layoutButton(browseDirButton, 345, 276);
                    Utils.layoutLabel(fileNameLab,526,259);
                    Utils.layoutFiled(fileNameField, 100, 525, 276, "file name");

                    anchorPaneCompositeTrigger.getChildren().addAll(pathFolderField,fileNameField,browseDirButton,fileNameLab);
                    compositeTrigPaneItems2.addAll(pathFolderField,fileNameField,browseDirButton,fileNameLab);
                }


                break;
            case "File dimension":

                Spinner<Integer> fileDimensionSpinner = Utils.spinnerItem(1, 1024,1);
                ComboBox<String> unit = Utils.comboItem(FXCollections.observableArrayList("B", "KB", "MB", "GB"));
                Label labFile = new Label("File path");
                FileChooser.ExtensionFilter files = new FileChooser.ExtensionFilter("Files", "*.*");
                TextField pathFileField = new TextField();
                pathFileField.setPromptText("Chosen file");
                Button browseFileButton = Utils.buttonFileItem(pathFileField, files);

                unit.setValue("KB");
                fileDimensionSpinner.setPromptText("Insert dimension");
                if(Objects.equals(value, NORMAL)){
                    label.setText("Insert file dimension");
                    Utils.layoutSpinner(fileDimensionSpinner, 285, 142, 80);
                    Utils.layoutCombo(unit, 350, 146);
                    Utils.layoutLabel(labFile,510,125);
                    Utils.layoutButton(browseFileButton, 430, 146);
                    Utils.layoutFiled(pathFileField, 100, 510, 146, "file choose");

                    rulePane.getChildren().addAll(pathFileField,fileDimensionSpinner, unit,labFile,browseFileButton);
                    triggerPaneItems.addAll(pathFileField,fileDimensionSpinner, unit,labFile,browseFileButton);

                }else if(Objects.equals(value, COMPOSITE_FIRST)){
                    Utils.layoutSpinner(fileDimensionSpinner, 350, 152, 70);
                    Utils.layoutCombo(unit, 420, 155);
                    Utils.layoutLabel(labFile,570,135);
                    Utils.layoutButton(browseFileButton, 495, 155);
                    Utils.layoutFiled(pathFileField, 80, 570, 155, "file choose");

                    anchorPaneCompositeTrigger.getChildren().addAll(fileDimensionSpinner, unit,labFile,pathFileField,browseFileButton);
                    compositeTrigPaneItems1.addAll(fileDimensionSpinner, unit,labFile,pathFileField,browseFileButton);
                }else if(Objects.equals(value, COMPOSITE_SECOND)){
                    Utils.layoutSpinner(fileDimensionSpinner, 350, 276, 70);
                    Utils.layoutCombo(unit, 420, 278);
                    Utils.layoutLabel(labFile,570,259);
                    Utils.layoutButton(browseFileButton, 495, 278);
                    Utils.layoutFiled(pathFileField, 80, 570, 278, "file choose");

                    anchorPaneCompositeTrigger.getChildren().addAll(pathFileField,fileDimensionSpinner, unit,labFile,browseFileButton);
                    compositeTrigPaneItems2.addAll(pathFileField,fileDimensionSpinner, unit,labFile,browseFileButton);
                }
                break;
            case "Status program":
                label.setText("");
                Label commandLabel = new Label();
                commandLabel.setText("Command");
                TextField commandField = new TextField();
                TextField programField = new TextField();
                programField.setPromptText("Program path");
                Button programBrowse = Utils.buttonFileItem(programField, null);


                Label outputLabel = new Label();
                outputLabel.setText("Output");
                TextField outputField = new TextField();

                if(Objects.equals(value, NORMAL)){
                    Utils.layoutLabel(commandLabel, 285, 125);
                    Utils.layoutFiled(commandField, 50, 285, 142, "command");
                    Utils.layoutButton(programBrowse, 350, 112);
                    Utils.layoutFiled(programField,100,350, 142, "program");
                    Utils.layoutLabel(outputLabel, 500, 125);
                    Utils.layoutFiled(outputField, 30, 460, 142, "output");
                    Utils.layoutLabel(outputLabel,460,125);

                    rulePane.getChildren().addAll(commandLabel, commandField, programBrowse, programField, outputLabel, outputField);
                    triggerPaneItems.addAll(commandField, programField, outputField, commandLabel, outputLabel, programBrowse);
                }else if(Objects.equals(value, COMPOSITE_FIRST)){
                    Utils.layoutLabel(commandLabel, 347, 135);
                    Utils.layoutFiled(commandField, 50, 350, 153, "command");
                    Utils.layoutButton(programBrowse, 410, 123);
                    Utils.layoutFiled(programField, 150,410, 153, "command");
                    Utils.layoutLabel(outputLabel, 567, 135);
                    Utils.layoutFiled(outputField, 40, 567, 153, "command");

                    //rulePane.getChildren().add(statusProgramField);
                    anchorPaneCompositeTrigger.getChildren().addAll(commandLabel, commandField, programBrowse, programField, outputLabel, outputField);
                    compositeTrigPaneItems1.addAll(commandField, programField, outputField, commandLabel, outputLabel, programBrowse);
                }else if(Objects.equals(value, COMPOSITE_SECOND)){
                    Utils.layoutLabel(commandLabel, 347, 262);
                    Utils.layoutFiled(commandField, 50, 350, 276, "command");
                    Utils.layoutButton(programBrowse, 410, 246);
                    Utils.layoutFiled(programField,150, 410, 276, "command");
                    Utils.layoutLabel(outputLabel, 567, 262);
                    Utils.layoutFiled(outputField, 40, 567, 276, "command");

                    //rulePane.getChildren().add(statusProgramField);
                    anchorPaneCompositeTrigger.getChildren().addAll(commandLabel, commandField, programBrowse, programField, outputLabel, outputField);
                    compositeTrigPaneItems2.addAll(commandField, programField, outputField, commandLabel, outputLabel, programBrowse);
                }


        }
    }

    @FXML
    private void createActionItem(Label label, String text, String value) {
        System.out.println(value);
        label.setVisible(false);
        switch (text) {
            case "Display message":
                TextField msgField = new TextField();
                msgField.setPromptText("Message...");
                label.setText("Message to show");
                label.setVisible(true);
                if(Objects.equals(value, NORMAL)){
                    label.setText("Message to show");
                    Utils.layoutSimpleField(msgField, 285, 225);

                    rulePane.getChildren().add(msgField);
                    actionPaneItems.add(msgField);
                }else{
                    Utils.layoutSimpleField(msgField, 34, 245);
                    msgField.setPromptText("Message to display");

                    anchorPaneCompositeAction.getChildren().add(msgField);
                    compositeActionPaneItems.add(msgField);
                }

                break;
            case "Play Audio":

                TextField pathAudioField = new TextField();
                pathAudioField.setFocusTraversable(false);
                pathAudioField.setEditable(false);
                pathAudioField.setDisable(false);
                FileChooser.ExtensionFilter audio = new FileChooser.ExtensionFilter("Audio files", "*.mp3", "*.aac");

                Button browseAudioButton = Utils.buttonFileItem(pathAudioField, audio);

                if(Objects.equals(value, NORMAL)){
                    label.setText("Audio to reproduce");
                    label.setVisible(true);
                    Utils.layoutButton(browseAudioButton, 450, 225);
                    Utils.layoutSimpleField(pathAudioField, 285, 225);
                    pathAudioField.setPromptText("Chosen audio file");


                    rulePane.getChildren().addAll(browseAudioButton, pathAudioField);
                    actionPaneItems.addAll(browseAudioButton, pathAudioField);
                }else{

                    Utils.layoutButton(browseAudioButton, 200, 245);
                    Utils.layoutSimpleField(pathAudioField, 34, 245);
                    pathAudioField.setPromptText("Chosen audio file");

                    anchorPaneCompositeAction.getChildren().addAll(browseAudioButton, pathAudioField);
                    compositeActionPaneItems.addAll(browseAudioButton, pathAudioField);
                }
                break;
            case "Write string":
                TextField fileField = new TextField();
                Button browseButton = Utils.buttonFileAbsoluteItem(fileField, "Choose a file");
                TextField stringToWriteField = new TextField();
                stringToWriteField.setPromptText("String to write...");

                if(Objects.equals(value, NORMAL)){
                    label.setText("Choose a file to write a string");
                    label.setVisible(true);
                    Utils.layoutButton(browseButton, 285, 225);
                    Utils.layoutSimpleField(fileField, 360, 225);
                    Utils.layoutSimpleField(stringToWriteField, 285, 265);
                    fileField.setPromptText("Chosen File");

                    rulePane.getChildren().addAll(browseButton, fileField, stringToWriteField);
                    actionPaneItems.addAll(browseButton, fileField, stringToWriteField);
                }else{
                    Utils.layoutButton(browseButton, 200, 245);
                    Utils.layoutSimpleField(fileField, 34, 245);
                    fileField.setPromptText("Chosen File");
                    Utils.layoutSimpleField(stringToWriteField, 34, 275);
                    stringToWriteField.setPromptText("String to write...");

                    anchorPaneCompositeAction.getChildren().addAll(browseButton, fileField, stringToWriteField);
                    compositeActionPaneItems.addAll(browseButton, fileField, stringToWriteField);
                }

                break;

            case "Copy File":

                TextField fileSelectedField = new TextField();
                fileSelectedField.setPromptText("Select a file");
                Button fileSelectButton = Utils.buttonFileAbsoluteItem(fileSelectedField, "Select a file");
                TextField destSelectedField = new TextField();
                destSelectedField.setPromptText("Destination Path");
                Button destSelectButton = Utils.ButtonDirectoryItem(destSelectedField);
                if(Objects.equals(value, NORMAL)){
                    label.setText("Select file and destination directory");
                    label.setVisible(true);
                    Utils.layoutSimpleField(fileSelectedField, 360, 225);
                    Utils.layoutSimpleField(destSelectedField, 360, 265);
                    fileSelectedField.setPrefWidth(160.0);
                    destSelectedField.setPrefWidth(160.0);
                    Utils.layoutButton(fileSelectButton, 285, 225);
                    Utils.layoutButton(destSelectButton, 285, 265);

                    rulePane.getChildren().addAll(fileSelectButton, destSelectButton, fileSelectedField, destSelectedField);
                    actionPaneItems.addAll(fileSelectButton, destSelectButton, fileSelectedField, destSelectedField);
                }else{
                    Utils.layoutSimpleField(fileSelectedField, 34, 245);
                    fileSelectedField.setPromptText("Select a file");
                    destSelectedField.setPromptText("Destination Path");
                    Utils.layoutSimpleField(destSelectedField, 34, 275);
                    Utils.layoutButton(fileSelectButton, 200, 245);
                    Utils.layoutButton(destSelectButton, 200, 275);

                    anchorPaneCompositeAction.getChildren().addAll(fileSelectButton, destSelectButton, fileSelectedField, destSelectedField);
                    compositeActionPaneItems.addAll(fileSelectButton, destSelectButton, fileSelectedField, destSelectedField);
                }
                break;

            case "Move file":

                TextField fileSelectedField1 = new TextField();
                fileSelectedField1.setPromptText("Chosen File");
                fileSelectedField1.setPrefWidth(160.0);
                fileSelectedField1.setPromptText("Select a file");
                Button fileSelectButton1 = Utils.buttonFileAbsoluteItem(fileSelectedField1, "Select a file");

                TextField destSelectedField1 = new TextField();
                destSelectedField1.setPrefWidth(160.0);
                destSelectedField1.setPromptText("Destination Path");
                Button destSelectButton1 = Utils.ButtonDirectoryItem(destSelectedField1);

                if(Objects.equals(value, NORMAL)){
                    label.setText("Select file and destination directory");
                    label.setVisible(true);
                    Utils.layoutButton(fileSelectButton1, 285, 225);
                    Utils.layoutButton(destSelectButton1, 285, 265);
                    Utils.layoutSimpleField(fileSelectedField1, 360, 225);
                    Utils.layoutSimpleField(destSelectedField1, 360, 265);

                    rulePane.getChildren().addAll(fileSelectButton1, destSelectButton1, fileSelectedField1, destSelectedField1);
                    actionPaneItems.addAll(fileSelectButton1, destSelectButton1, fileSelectedField1, destSelectedField1);
                }else{
                    Utils.layoutSimpleField(fileSelectedField1, 34, 245);
                    Utils.layoutSimpleField(destSelectedField1, 34, 275);
                    Utils.layoutButton(fileSelectButton1, 200, 245);
                    fileSelectedField1.setPromptText("Select a file");
                    destSelectedField1.setPromptText("Destination Path");
                    Utils.layoutButton(destSelectButton1, 200, 275);

                    anchorPaneCompositeAction.getChildren().addAll(fileSelectedField1, destSelectedField1, fileSelectButton1, destSelectButton1);
                    compositeActionPaneItems.addAll(fileSelectedField1, destSelectedField1, fileSelectButton1, destSelectButton1);
                }
                break;
            case "Remove file":

                TextField fileRemovedField = new TextField();
                fileRemovedField.setPrefWidth(160.0);
                fileRemovedField.setPromptText("Remove File");
                Button browseRemButton = Utils.buttonFileAbsoluteItem(fileRemovedField, "Select a file to remove");

                if(Objects.equals(value, NORMAL)){
                    label.setText("Select the file to remove");
                    label.setVisible(true);
                    Utils.layoutSimpleField(fileRemovedField, 360, 225);
                    Utils.layoutButton(browseRemButton, 285, 225);

                    rulePane.getChildren().addAll(browseRemButton, fileRemovedField);
                    actionPaneItems.addAll(browseRemButton, fileRemovedField);
                }else{
                    Utils.layoutButton(browseRemButton, 200, 245);
                    fileRemovedField.setPromptText("Remove File");
                    Utils.layoutSimpleField(fileRemovedField, 34, 245);

                    anchorPaneCompositeAction.getChildren().addAll(browseRemButton, fileRemovedField);
                    compositeActionPaneItems.addAll(browseRemButton, fileRemovedField);
                }
                break;
            case "Execute Program":


                TextField programPathField = new TextField();
                programPathField.setPromptText("Insert the application file (.exe)");
                Button browseProgramButton = Utils.buttonFileAbsoluteItem(programPathField, "Select a program to execute");

                TextField commandField = new TextField();
                commandField.setPromptText("Insert command...");

                // Salva il comando inserito
                commandField.textProperty().addListener((observable, oldValue, newValue) -> {
                    programCommand = newValue;
                });

                if(Objects.equals(value, NORMAL)){
                    label.setText("Choose the program to execute");
                    label.setVisible(true);
                    Utils.layoutSimpleField(programPathField, 285, 225);
                    Utils.layoutButton(browseProgramButton, 450, 225);
                    Utils.layoutSimpleField(commandField, 285, 265);
                    rulePane.getChildren().addAll(programPathField, browseProgramButton, commandField);
                    actionPaneItems.addAll(programPathField, browseProgramButton, commandField);
                }else{
                    Utils.layoutButton(browseProgramButton, 200, 245);
                    Utils.layoutSimpleField(programPathField, 34, 245);
                    programPathField.setPromptText("Chosen program");
                    Utils.layoutSimpleField(commandField, 34, 275);

                    anchorPaneCompositeAction.getChildren().addAll(programPathField, browseProgramButton, commandField);
                    compositeActionPaneItems.addAll(programPathField, browseProgramButton, commandField);
                }
                break;
        }
    }

    @FXML
    public void onClearButtonClick(ActionEvent actionEvent) {
        labelTriggerSelected.setText("");
        labelActionSelected.setText("");
        ruleNameTxtField.clear();
        triggerBox.setValue("");
        actionBox.setValue("");
        singleCheckBox.setSelected(false);
        periodicCheckBox.setSelected(false);
        compositeTriggerButton.setVisible(true);
        compositeActionButton.setVisible(true);
        labelError.setVisible(false);
        CTLabelError.setVisible(false);
        clearPeriodicRule();

    }

    @FXML
    public void clearCompositeAction(){
        CALabelError.setVisible(false);
        compositeActionName.clear();
        compositeActionList.clear();
        CABox.setValue("");
    }

}
