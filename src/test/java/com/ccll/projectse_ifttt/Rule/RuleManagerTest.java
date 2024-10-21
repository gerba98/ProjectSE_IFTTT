package com.ccll.projectse_ifttt.Rule;

import com.ccll.projectse_ifttt.Actions.Action;
import com.ccll.projectse_ifttt.TestUtilsClasses.ActionTestUtils;
import com.ccll.projectse_ifttt.TestUtilsClasses.TriggerTestUtils;
import com.ccll.projectse_ifttt.Triggers.Trigger;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class RuleManagerTest {

    private RuleManager testRuleManager;

    private void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = RuleManager.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        resetSingleton();
        testRuleManager = RuleManager.getInstance();
    }

    @Test
    @DisplayName("Verifica Pattern Singleton")
    void testSingletonPattern() {
        RuleManager ruleManager2 = RuleManager.getInstance();
        assertSame(testRuleManager, ruleManager2, "RuleManager dovrebbe essere un singleton");
    }


    @Test
    @DisplayName("Verifica Aggiunta Regole")
    void testMultipleRuleAdditions() {
        TriggerTestUtils trigger1 = new TriggerTestUtils(true);
        ActionTestUtils action1 = new ActionTestUtils();
        Rule rule1 = new Rule("Regola 1", trigger1, action1);

        TriggerTestUtils trigger2 = new TriggerTestUtils(false);
        ActionTestUtils action2 = new ActionTestUtils();
        Rule rule2 = new Rule("Regola 2", trigger2, action2);

        testRuleManager.addRule(rule1);
        testRuleManager.addRule(rule2);

        ObservableList<Rule> rules = testRuleManager.getRules();

        assertEquals(2, rules.size(), "La lista delle regole dovrebbe contenere due regole");
        assertTrue(rules.contains(rule1) && rules.contains(rule2), "La lista delle regole dovrebbe contenere entrambe le regole aggiunte");
    }

    @Test
    @DisplayName("Verifica creazione regola")
    void testCreateRule() {
        Rule resultRule1 = testRuleManager.createRule("Regola Test 1", "time of the day", "12:00", "display message", "Hello World");

        assertNotNull(resultRule1, "La regola creata non dovrebbe essere null");
        assertEquals("Regola Test 1", resultRule1.getName(), "Il nome della regola dovrebbe corrispondere");
        assertInstanceOf(Trigger.class, resultRule1.getTrigger(), "Il trigger dovrebbe essere un'istanza di Trigger");
        assertInstanceOf(Action.class, resultRule1.getAction(), "L'azione dovrebbe essere un'istanza di Action");

        Rule resultRule2 = testRuleManager.createRule("Regola Test 2", "time of the day", "15:30", "play audio", "/Users/Desktop/cat.mp3");

        assertNotNull(resultRule2, "La regola creata non dovrebbe essere null");
        assertEquals("Regola Test 2", resultRule2.getName(), "Il nome della regola dovrebbe corrispondere");
        assertInstanceOf(Action.class, resultRule2.getAction(), "L'azione dovrebbe essere un'istanza di Action");
    }

    @Test
    @DisplayName("Verifica Rimozione Regola")
    void testRemoveRule() {
        // Creiamo e aggiungiamo due regole
        TriggerTestUtils trigger1 = new TriggerTestUtils(true);
        ActionTestUtils action1 = new ActionTestUtils();
        Rule rule1 = new Rule("Regola 1", trigger1, action1);

        TriggerTestUtils trigger2 = new TriggerTestUtils(false);
        ActionTestUtils action2 = new ActionTestUtils();
        Rule rule2 = new Rule("Regola 2", trigger2, action2);

        testRuleManager.addRule(rule1);
        testRuleManager.addRule(rule2);

        // Verifichiamo che ci siano due regole
        assertEquals(2, testRuleManager.getRules().size(), "Dovrebbero esserci due regole prima della rimozione");

        // Rimuoviamo la prima regola
        testRuleManager.removeRule(0);

        // Verifichiamo che sia rimasta solo una regola
        assertEquals(1, testRuleManager.getRules().size(), "Dovrebbe rimanere una sola regola dopo la rimozione");
        assertFalse(testRuleManager.getRules().contains(rule1), "La regola rimossa non dovrebbe essere più presente");
        assertTrue(testRuleManager.getRules().contains(rule2), "La seconda regola dovrebbe essere ancora presente");

        // Rimuoviamo l'ultima regola
        testRuleManager.removeRule(0);

        // Verifichiamo che non ci siano più regole
        assertTrue(testRuleManager.getRules().isEmpty(), "Non dovrebbero esserci più regole dopo la rimozione dell'ultima");
    }
}