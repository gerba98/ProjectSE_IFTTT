package com.ccll.projectse_ifttt.Rule;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RulePersistenceTest {

    private RulePersistence rulePersistence;
    private static Path tempFilePath;

    @BeforeEach
    public void setUp() throws IOException {
        // Creiamo un file temporaneo per testare le operazioni
        tempFilePath = Files.createTempFile("rules", ".csv");
        rulePersistence = new RulePersistence();

        // Modifica il path nel test per usare il file temporaneo
        rulePersistence.setPath(tempFilePath.toString());
    }

    @Test
    public void testSaveRules() throws IOException {
        // Creiamo un esempio di ObservableList con il formato richiesto
        ObservableList<String> rules = FXCollections.observableArrayList(
                "Rule1;TurnOnLight;TimeTrigger;Time;08:00;TurnOn;Switch;Light",
                "Rule2;TurnOffFan;MotionTrigger;Motion;Detected;TurnOff;Switch;Fan"
        );

        // Salva le regole nel file temporaneo
        rulePersistence.saveRules(rules);

        // Leggi il contenuto del file per verificarne il contenuto
        List<String> lines = Files.readAllLines(tempFilePath);
        assertEquals(2, lines.size(), "Il numero di righe nel file non corrisponde");

        // Verifica che ogni riga contenga il pattern corretto
        assertTrue(lines.get(0).contains("Rule1;TurnOnLight;TimeTrigger;Time;08:00;TurnOn;Switch;Light"), "La prima riga non contiene i dati corretti");
        assertTrue(lines.get(1).contains("Rule2;TurnOffFan;MotionTrigger;Motion;Detected;TurnOff;Switch;Fan"), "La seconda riga non contiene i dati corretti");
    }

    @Test
    public void testLoadRules() throws IOException {
        // Scriviamo delle regole nel file temporaneo con il formato specificato
        Files.write(tempFilePath, "Rule1;TurnOnLight;TimeTrigger;Time;08:00;TurnOn;Switch;Light\nRule2;TurnOffFan;MotionTrigger;Motion;Detected;TurnOff;Switch;Fan".getBytes());

        // Carica le regole
        ObservableList<String> loadedRules = rulePersistence.loadRules();

        // Verifica che le regole siano caricate correttamente
        assertEquals(2, loadedRules.size(), "Il numero di regole caricate non è corretto");
        assertEquals("Rule1;TurnOnLight;TimeTrigger;Time;08:00;TurnOn;Switch;Light", loadedRules.get(0), "La prima regola non è corretta");
        assertEquals("Rule2;TurnOffFan;MotionTrigger;Motion;Detected;TurnOff;Switch;Fan", loadedRules.get(1), "La seconda regola non è corretta");
    }

    @Test
    public void testDeleteRules() throws IOException {
        // Scriviamo delle regole nel file temporaneo con il formato specificato
        Files.write(tempFilePath, "Rule1;TurnOnLight;TimeTrigger;Time;08:00;TurnOn;Switch;Light\nRule2;TurnOffFan;MotionTrigger;Motion;Detected;TurnOff;Switch;Fan\nRule3;ActivateAlarm;ButtonTrigger;Button;Pressed;Activate;Alarm;Siren".getBytes());

        // Elimina la seconda riga (index 1)
        rulePersistence.deleteRules(1);

        // Rileggiamo il file per verificare che la seconda riga sia stata eliminata
        List<String> lines = Files.readAllLines(tempFilePath);
        assertEquals(2, lines.size(), "Il numero di righe nel file non è corretto dopo la cancellazione");

        // Verifica che le righe contengano i dati corretti dopo la cancellazione
        assertTrue(lines.contains("Rule1;TurnOnLight;TimeTrigger;Time;08:00;TurnOn;Switch;Light"), "La prima regola è stata erroneamente rimossa");
        assertTrue(lines.contains("Rule3;ActivateAlarm;ButtonTrigger;Button;Pressed;Activate;Alarm;Siren"), "La terza regola non è corretta");
        assertFalse(lines.contains("Rule2;TurnOffFan;MotionTrigger;Motion;Detected;TurnOff;Switch;Fan"), "La seconda regola non è stata eliminata correttamente");
    }

    @Test
    public void testSaveEmptyRules() throws IOException {
        ObservableList<String> rules = FXCollections.observableArrayList();

        // Salva una lista vuota
        rulePersistence.saveRules(rules);

        // Verifica che il file sia vuoto
        List<String> lines = Files.readAllLines(tempFilePath);
        assertTrue(lines.isEmpty(), "Il file non dovrebbe contenere righe");
    }

    @AfterAll
    public static void deleteTempFiles() throws IOException {
        File file = new File(tempFilePath+"");
        if (file.exists()) {
            file.delete();  // Se il file esiste già, lo rimuoviamo.
        }
    }
}
