package com.ccll.projectse_ifttt.Rule;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RulePersistence {
    private String CSV_FILE_PATH ="src/main/resources/com/ccll/projectse_ifttt/rules.csv";
    private final RuleManager ruleManager = RuleManager.getInstance();



    public void setPath(String path) {
        this.CSV_FILE_PATH = path;
    }

    public String getPath() {
        return CSV_FILE_PATH;
    }


    /**
     * Permette di salvare le regole in un file csv
     *
     *
     */
    public void saveRules() {
        ruleManager.getInstance();
        List<Rule> rules = ruleManager.getRules();

        try {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE_PATH))) {
                for (Rule rule : rules) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(rule.getName()).append(";")
                            .append(rule.getTrigger()).append(";")
                            .append(rule.getAction()).append(";")
                            .append(rule.isState()).append(";")
                            .append(rule.getType());

                    if (rule instanceof PeriodicRule) {
                        PeriodicRule newPeriodicRule = (PeriodicRule) rule;
                        sb.append("-");
                        sb.append(newPeriodicRule.getStrPeriod());
                        sb.append(";");
                        sb.append(newPeriodicRule.isReactivated());
                    }

                    writer.write(sb.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permette di caricare le regole sotto forma di stringa dal file csv
     * @return rules la lista di stringhe che identificano le regole
     */
    public void loadRules(){
        try {
            File csvFile = new File(CSV_FILE_PATH);
            if (!csvFile.exists()) {
                Files.createFile(csvFile.toPath());
                return;
            }


            // Carica le regole esistenti
            List<String> lines = Files.readAllLines(Paths.get(CSV_FILE_PATH));
                for (String line : lines) {
                    String[] parts = line.split(";");
                    // ruleName,triggerType,triggerValue,actionType,actionValue,ruleType

                    Rule newRule = ruleManager.createRule(
                            parts[0], // ruleName
                            parts[1], // triggerType
                            parts[2], // triggerValue
                            parts[3], // actionType
                            parts[4], // actionValue
                            parts[6]  // ruleType
                    );
                    newRule.setState(Boolean.parseBoolean(parts[5]));
                    if (Objects.equals(newRule.getType(), "PeriodicRule")) {
                        PeriodicRule newPeriodicRule = (PeriodicRule) newRule;
                        newPeriodicRule.setReactivated(Boolean.parseBoolean(parts[7]));

                        if (!newPeriodicRule.isState() && newPeriodicRule.isReactivated()) {
                            newPeriodicRule.initEndPeriod();
                        }
                    }
                }

            } catch(IOException e){
                e.printStackTrace();
            }

    }

        /**
     * Permette di cancellare le regole selezionate dall'interfaccia
     *
     * @param selectedIndex indica la riga da rimuovere
     */
//    public void deleteRules(int selectedIndex){
//        try {
//            List<String> lines = Files.readAllLines(Paths.get(path));
//
//            lines.remove(selectedIndex);
//
//            Files.write(Paths.get(path), lines);
//        }catch (IOException e){
//            System.out.println(e);
//        }
//    }
}
