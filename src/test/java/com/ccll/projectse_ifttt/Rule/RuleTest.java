package com.ccll.projectse_ifttt.Rule;

import com.ccll.projectse_ifttt.Actions.Action;
import com.ccll.projectse_ifttt.Triggers.Trigger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class RuleTest {
    private TestTrigger testTrigger;
    private TestAction testAction;
    private Rule rule;

    @BeforeEach
    void setUp() {
        testTrigger = new TestTrigger(false);
        testAction = new TestAction();
        rule = new Rule(testTrigger, testAction, "Regola di Test");
    }

    @Test
    void testGetterSetter() {
        assertEquals("Regola di Test", rule.getName(), "Il nome della regola dovrebbe corrispondere");
        assertSame(testTrigger, rule.getTrigger(), "Il trigger dovrebbe essere lo stesso oggetto");
        assertSame(testAction, rule.getAction(), "L'azione dovrebbe essere lo stesso oggetto");

        TestTrigger testTrigger1 = new TestTrigger(true);
        TestAction testAction1 = new TestAction();
        rule.setTrigger(testTrigger1);
        rule.setAction(testAction1);
        rule.setName("Nuova Regola di Test");

        assertEquals("Nuova Regola di Test", rule.getName(), "Il nuovo nome della regola dovrebbe corrispondere");
        assertSame(testTrigger1, rule.getTrigger(), "Il nuovo trigger dovrebbe essere lo stesso oggetto");
        assertSame(testAction1, rule.getAction(), "La nuova azione dovrebbe essere lo stesso oggetto");
    }

    @Test
    void testExecuteAction() {
        rule.executeAction();
        assertTrue(testAction.wasExecuted(), "L'azione dovrebbe essere stata eseguita");
    }

    @Test
    void testToString() {
        String ruleString = rule.toString();
        assertNotNull(ruleString, "toString non dovrebbe restituire null");
        assertFalse(ruleString.isEmpty(), "toString non dovrebbe restituire una stringa vuota");
    }

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
    private class TestAction implements Action {
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
    }
}