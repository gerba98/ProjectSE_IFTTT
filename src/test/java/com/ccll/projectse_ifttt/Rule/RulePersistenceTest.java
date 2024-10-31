package com.ccll.projectse_ifttt.Rule;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

class RulePersistenceTest {
    private RulePersistence rulePersistence;
    private final String testCsvFilePath = "src/test/resources/com/ccll/projectse_ifttt/test_rules.csv";

    @BeforeEach
    void setUp() {
        rulePersistence = new RulePersistence();
        rulePersistence.setPath(testCsvFilePath);

        // Crea un file di test
        try {
            Files.createDirectories(Paths.get("src/test/resources/com/ccll/projectse_ifttt/"));
            Files.createFile(Paths.get(testCsvFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        // Elimina il file di test alla fine di ogni test
        try {
            Files.deleteIfExists(Paths.get(testCsvFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSetAndGetPath() {
        rulePersistence.setPath(testCsvFilePath);
        assertEquals(testCsvFilePath, rulePersistence.getPath(), "Il percorso del file CSV dovrebbe essere aggiornato correttamente.");
    }

//    @Test
//    void testSaveRules() {
//        RuleManager ruleManager = RuleManager.getInstance();
//        ruleManager.addRule(new Rule("TestRule", "TestTriggerType", "TestTriggerValue", "TestActionType", "TestActionValue", "StandardRule"));
//
//        rulePersistence.saveRules();
//
//        try {
//            List<String> lines = Files.readAllLines(Paths.get(testCsvFilePath));
//            assertFalse(lines.isEmpty(), "Il file CSV non dovrebbe essere vuoto dopo il salvataggio.");
//            assertTrue(lines.get(0).contains("TestRule"), "La riga salvata dovrebbe contenere il nome della regola salvata.");
//        } catch (IOException e) {
//            fail("Eccezione durante la lettura del file: " + e.getMessage());
//        }
//    }

//    @Test
//    void testLoadRules() {
//        // Scrivi manualmente una regola nel file CSV di test
//        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(testCsvFilePath))) {
//            writer.write("TestRule;TestTriggerType;TestTriggerValue;TestActionType;TestActionValue;true;StandardRule\n");
//        } catch (IOException e) {
//            fail("Eccezione durante la scrittura del file: " + e.getMessage());
//        }
//
//        rulePersistence.loadRules();
//
//        RuleManager ruleManager = RuleManager.getInstance();
//        List<Rule> loadedRules = ruleManager.getRules();
//        assertFalse(loadedRules.isEmpty(), "Dovrebbe caricare almeno una regola.");
//        assertEquals("TestRule", loadedRules.get(0).getName(), "La regola caricata dovrebbe avere il nome 'TestRule'.");
//    }

//    @Test
//    void testDeleteRules() {
//        RuleManager ruleManager = RuleManager.getInstance();
//        ruleManager.addRule(new Rule("TestRule1", "TestTriggerType", "TestTriggerValue", "TestActionType", "TestActionValue", "StandardRule"));
//        ruleManager.addRule(new Rule("TestRule2", "TestTriggerType", "TestTriggerValue", "TestActionType", "TestActionValue", "StandardRule"));
//
//        rulePersistence.saveRules();
//        rulePersistence.deleteRules(0); // Rimuove la prima regola
//
//        try {
//            List<String> lines = Files.readAllLines(Paths.get(testCsvFilePath));
//            assertEquals(1, lines.size(), "Il file CSV dovrebbe contenere una sola regola dopo la cancellazione.");
//            assertFalse(lines.get(0).contains("TestRule1"), "La riga cancellata non dovrebbe essere presente.");
//        } catch (IOException e) {
//            fail("Eccezione durante la lettura del file: " + e.getMessage());
//        }
//    }
}
