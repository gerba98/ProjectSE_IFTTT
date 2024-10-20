package com.ccll.projectse_ifttt.Rule;
import com.ccll.projectse_ifttt.Actions.Action;

import com.ccll.projectse_ifttt.Triggers.Trigger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

class CheckRuleTest {

    private RuleManager testRuleManager;
    private CheckRule checkRule;

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
    @DisplayName("Verifica esecuzione regola quando il trigger è true")
    void testRuleExecutionWhenTriggerIsTrue() {
        TestTrigger testTrigger = new TestTrigger(true);
        TestAction testAction = new TestAction();
        Rule testRule = new Rule(testTrigger, testAction, "Regola di Test 1");

        testRuleManager.addRule(testRule);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(testAction.wasExecuted(), "L'azione dovrebbe essere stata eseguita");
    }

    @Test
    @DisplayName("Verifica non esecuzione regola quando il trigger è false")
    void testRuleNonExecutionWhenTriggerIsFalse() {
        TestTrigger testTrigger = new TestTrigger(false);
        TestAction testAction = new TestAction();
        Rule testRule = new Rule(testTrigger, testAction, "Regola di Test 2");

        testRuleManager.addRule(testRule);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertFalse(testAction.wasExecuted(), "L'azione non dovrebbe essere stata eseguita");
    }

    @Test
    @DisplayName("Verifica esecuzione di regole multiple")
    void testMultipleRulesExecution() {
        TestTrigger trigger1 = new TestTrigger(true);
        TestAction action1 = new TestAction();
        Rule rule1 = new Rule(trigger1, action1, "Regola 1");

        TestTrigger trigger2 = new TestTrigger(false);
        TestAction action2 = new TestAction();
        Rule rule2 = new Rule(trigger2, action2, "Regola 2");

        testRuleManager.addRule(rule1);
        testRuleManager.addRule(rule2);

        try {
            Thread.sleep(2000); // Attendi che CheckRule elabori
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(action1.wasExecuted(), "L'azione 1 dovrebbe essere stata eseguita");
        Assertions.assertFalse(action2.wasExecuted(), "L'azione 2 non dovrebbe essere stata eseguita");
    }

    @Test
    @DisplayName("Verifica esecuzione regola dopo cambio del trigger")
    void testRuleExecutionAfterTriggerChange() {
        TestTrigger testTrigger = new TestTrigger(false);
        TestAction testAction = new TestAction();
        Rule testRule = new Rule(testTrigger, testAction, "Regola di Test 3");

        testRuleManager.addRule(testRule);

        try {
            Thread.sleep(2000); // Attendi che CheckRule elabori
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertFalse(testAction.wasExecuted(), "L'azione non dovrebbe essere stata eseguita inizialmente");

        testTrigger.setShouldTrigger(true);

        try {
            Thread.sleep(2000); // Attendi che CheckRule elabori di nuovo
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(testAction.wasExecuted(), "L'azione dovrebbe essere stata eseguita dopo il cambiamento del trigger");
    }
    @Test
    @DisplayName("Verifica esecuzione di una regola aggiunta dopo che sono state rimosse altre regole")
    void testRulesExecutionAfterRestart() {
        TestTrigger trigger1 = new TestTrigger(true);
        TestAction action1 = new TestAction();
        Rule rule1 = new Rule(trigger1, action1, "Regola 1");

        testRuleManager.addRule(rule1);
        try {
            Thread.sleep(2000); // Attendi che CheckRule elabori
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(action1.wasExecuted(), "L'azione 1 dovrebbe essere stata eseguita");
        testRuleManager.removeRule(0);
        Assertions.assertTrue(testRuleManager.getRules().isEmpty(), "Non dovrebbero esserci più regole dopo la rimozione dell'ultima");
        try {
            Thread.sleep(2000); // Attendi che CheckRule elabori
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        TestTrigger trigger2 = new TestTrigger(true);
        TestAction action2 = new TestAction();
        Rule rule2 = new Rule(trigger2, action2, "Regola 2");
        testRuleManager.addRule(rule2);
        try {
            Thread.sleep(2000); // Attendi che CheckRule elabori
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(action2.wasExecuted(), "L'azione 2 dovrebbe essere stata eseguita");
    }
    // Implementazione di test per Trigger
    // Implementazione di test per Trigger
    static class TestTrigger implements Trigger {
        private boolean shouldTrigger;
        private boolean lastEvaluation = false; // Questo campo potrebbe servire per memorizzare il risultato dell'ultima valutazione

        public TestTrigger(boolean shouldTrigger) {
            this.shouldTrigger = shouldTrigger;
        }

        @Override
        public boolean evaluate() {
            lastEvaluation = shouldTrigger; // Memorizza il risultato dell'ultima valutazione
            return shouldTrigger;
        }

        @Override
        public void reset() {
            // Implementa qui la logica di reset del trigger, se necessaria
            shouldTrigger = false;  // Reimposta il trigger a un valore di default
            lastEvaluation = false; // Puoi anche resettare l'ultimo risultato di valutazione
        }

        @Override
        public boolean returnEvaluation() {
            // Ritorna il risultato dell'ultima valutazione
            return lastEvaluation;
        }

        public void setShouldTrigger(boolean shouldTrigger) {
            this.shouldTrigger = shouldTrigger;
        }

        @Override
        public String toString() {
            return "TestTrigger{shouldTrigger=" + shouldTrigger + '}';
        }
    }


    // Implementazione di test per Action
    // Implementa l'interfaccia Action
    static class TestAction implements Action {
        private boolean executed = false;

        @Override
        public boolean execute() {
            executed = true;
            return true;
        }

        public boolean wasExecuted() {
            return executed;
        }

        public void reset() {
            executed = false;
        }

        @Override
        public String toString() {
            return "TestAction{executed=" + executed + '}';
        }
    }

}