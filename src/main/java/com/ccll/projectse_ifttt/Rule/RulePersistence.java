package com.ccll.projectse_ifttt.Rule;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RulePersistence {
    private String path ="src/main/resources/com/ccll/projectse_ifttt/rules.csv";

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }


    /**
     * Permette di salvare le regole in un file csv
     *
     * @param rules lista di strighe che identificano le regole
     */
    public void saveRules(ObservableList<String> rules){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            // Iterate through each string in the list
            for (String line : rules) {
                // Split the string by commas to simulate columns
                String[] columns = line.split(";");

                // Write the columns to the CSV, each value separated by a comma
                writer.write(String.join(";", columns));

                // Add a newline after each row
                writer.newLine();
            }
            System.out.println("CSV file scritto con successo");
        } catch (IOException e) {
            System.out.println("Un errore durante la scrittura del file");
            e.printStackTrace();
        }
    }

    /**
     * Permette di caricare le regole sotto forma di stringa dal file csv
     * @return rules la lista di stringhe che identificano le regole
     */
    public ObservableList<String> loadRules(){
        ObservableList<String> rules = FXCollections.observableArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                // Add the line to the list of strings
                rules.add(line);
            }
            System.out.println("CSV file letto con successo");
        } catch (IOException e) {
            System.out.println("Un errore durante la lettura del file");
            e.printStackTrace();
        }
        return rules;
    }

    /**
     * Permette di cancellare le regole selezionate dall'interfaccia
     *
     * @param selectedIndex indica la riga da rimuovere
     */
    public void deleteRules(int selectedIndex){
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));

            lines.remove(selectedIndex);

            Files.write(Paths.get(path), lines);
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
