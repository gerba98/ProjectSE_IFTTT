package com.ccll.projectse_ifttt.Rule;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class RulePersistence {
    private String CSV_FILE_PATH ="src/main/resources/com/ccll/projectse_ifttt/rules.csv";
    private final RuleManager ruleManager = RuleManager.getInstance();


    /**
     * Imposta il percorso del file CSV dove vengono salvate le regole.
     *
     * @param path il nuovo percorso del file CSV.
     */
    public void setPath(String path) {
        this.CSV_FILE_PATH = path;
    }

    /**
     * Restituisce il percorso del file CSV attualmente impostato.
     *
     * @return il percorso del file CSV.
     */
    public String getPath() {
        return CSV_FILE_PATH;
    }


    /**
     * Salva le regole attualmente gestite da RuleManager in un file CSV.
     * <p>
     * Le regole vengono scritte nel formato stringa e nel caso di
     * PeriodicRule, vengono inclusi anche il periodo e lo stato di
     * riattivazione.
     */
    public void saveRules() {
        List<Rule> rules = ruleManager.getRules();

        try {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE_PATH))) {
                for (Rule rule : rules) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(rule.toString());

                    writer.write(sb.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carica le regole da un file CSV e le aggiunge a RuleManager.
     * <p>
     * Le regole vengono lette come stringhe e, nel caso di
     * PeriodicRule, viene impostato lo stato di riattivazione.
     * <p>
     * Se il file CSV non esiste, viene creato automaticamente.
     */
    public void loadRules(){
        try {
            File csvFile = new File(CSV_FILE_PATH);
            if (!csvFile.exists()) {
                Files.createFile(csvFile.toPath());
            } else if (csvFile.length()>0) {


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
    public void deleteRules(int selectedIndex){
        try {
            List<String> lines = Files.readAllLines(Paths.get(CSV_FILE_PATH));
            if (!lines.isEmpty()) {
                lines.remove(selectedIndex);
            }

            Files.write(Paths.get(CSV_FILE_PATH), lines);
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
