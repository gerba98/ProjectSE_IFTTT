package com.ccll.projectse_ifttt.Rule;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RulePersistenceTest {
    private RuleManager ruleManager;
    private static final String TEST_CSV_PATH = "src/main/resources/com/ccll/projectse_ifttt/rules.csv";
    private RulePersistence rulePersistence;


    private void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = RuleManager.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        resetSingleton();
        File testFile = new File(TEST_CSV_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
        ruleManager = RuleManager.getInstance();
        rulePersistence = new RulePersistence();
        rulePersistence.setPath(TEST_CSV_PATH);
    }


    @Test
    @DisplayName("Test salvataggio regola semplice")
    void testSaveSimpleRule() throws IOException {
        // Crea e aggiungi una regola semplice
        Rule rule = ruleManager.createRule(
                "Test Rule",
                "time of the day",
                "12:00",
                "display message",
                "Hello World",
                "Rule"
        );

        // Salva le regole
        rulePersistence.saveRules();

        // Verifica che il file esista e contenga i dati corretti
        assertTrue(Files.exists(Paths.get(TEST_CSV_PATH)), "Il file CSV dovrebbe essere stato creato");
        List<String> lines = Files.readAllLines(Paths.get(TEST_CSV_PATH));
        assertEquals(1, lines.size(), "Il file dovrebbe contenere una sola riga");
        assertTrue(lines.getFirst().contains("Test Rule"), "La riga dovrebbe contenere il nome della regola");
    }


    @Test
    @DisplayName("Test salvataggio regola periodica")
    void testSavePeriodicRule() throws IOException {
        // Crea e aggiungi una regola periodica
        Rule rule = ruleManager.createRule(
                "Periodic Test",
                "time of the day",
                "12:00",
                "display message",
                "Hello World",
                "PeriodicRule-01:00:00"
        );

        // Salva le regole
        rulePersistence.saveRules();

        // Verifica il contenuto del file
        List<String> lines = Files.readAllLines(Paths.get(TEST_CSV_PATH));
        assertTrue(lines.getFirst().contains("PeriodicRule"), "La riga dovrebbe indicare che è una PeriodicRule");
        assertTrue(lines.getFirst().contains("01:00:00"), "La riga dovrebbe contenere il periodo");
    }


    @Test
    @DisplayName("Test caricamento regole")
    void testLoadRules() throws IOException {
        // Crea alcune regole e salvale
        ruleManager.createRule(
                "Rule 1",
                "time of the day",
                "12:00",
                "display message",
                "Message 1",
                "Rule"
        );
        ruleManager.createRule(
                "Rule 2",
                "time of the day",
                "15:00",
                "display message",
                "Message 2",
                "Rule"
        );

        rulePersistence.saveRules();


        try {
            resetSingleton();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        ruleManager = RuleManager.getInstance();

        ObservableList<Rule> testRules = ruleManager.getRules();


        // Verifica che le regole siano state caricate correttamente
        assertEquals(2, testRules.size(), "Dovrebbero essere state caricate due regole");
        assertTrue(testRules.stream()
                .anyMatch(r -> r.getName().equals("Rule 1")), "La prima regola dovrebbe essere presente");
        assertTrue(testRules.stream()
                .anyMatch(r -> r.getName().equals("Rule 2")), "La seconda regola dovrebbe essere presente");
    }

    @Test
    @DisplayName("Test eliminazione regola")
    void testDeleteRule() throws IOException {
        // Crea e salva due regole
        ruleManager.createRule(
                "Rule to Keep",
                "time of the day",
                "12:00",
                "display message",
                "Stay",
                "Rule"
        );
        ruleManager.createRule(
                "Rule to Delete",
                "time of the day",
                "15:00",
                "display message",
                "Delete",
                "Rule"
        );

        rulePersistence.saveRules();

        // Elimina la seconda regola
        rulePersistence.deleteRules(1);

        // Verifica che il file contenga solo la prima regola
        List<String> lines = Files.readAllLines(Paths.get(TEST_CSV_PATH));
        assertEquals(1, lines.size(), "Dovrebbe rimanere una sola regola");
        assertTrue(lines.getFirst().contains("Rule to Keep"), "La regola rimanente dovrebbe essere quella corretta");
    }


    @Test
    @DisplayName("Test caricamento regola periodica con stato")
    void testLoadPeriodicRuleWithState() throws IOException {
        // Crea e salva una regola periodica con stato specifico
        Rule rule = ruleManager.createRule(
                "Periodic State Test",
                "time of the day",
                "12:00",
                "display message",
                "Test",
                "PeriodicRule-01:00:00"
        );
        ((PeriodicRule) rule).setReactivated(false);
        rule.setState(false);

        rulePersistence.saveRules();

        // Reset e ricarica

        try {
            resetSingleton();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        ruleManager = RuleManager.getInstance();

        // Verifica lo stato della regola caricata
        PeriodicRule loadedRule = (PeriodicRule) ruleManager.getRules().getFirst();

        assertFalse(loadedRule.isState(), "Lo stato della regola dovrebbe essere false");
        assertFalse(loadedRule.isReactivated(), "La riattivazione dovrebbe essere false");
    }


}
