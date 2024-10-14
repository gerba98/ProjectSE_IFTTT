package com.ccll.projectse_ifttt.Rule;

import com.ccll.projectse_ifttt.Actions.Action;
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
        CheckRuleTest.TestTrigger trigger1 = new CheckRuleTest.TestTrigger(true);
        CheckRuleTest.TestAction action1 = new CheckRuleTest.TestAction();
        Rule rule1 = new Rule(trigger1, action1, "Regola 1");

        CheckRuleTest.TestTrigger trigger2 = new CheckRuleTest.TestTrigger(false);
        CheckRuleTest.TestAction action2 = new CheckRuleTest.TestAction();
        Rule rule2 = new Rule(trigger2, action2, "Regola 2");

        testRuleManager.addRule(rule1);
        testRuleManager.addRule(rule2);

        ObservableList<Rule> rules = testRuleManager.getRules();
        // verify getRules() method;

        assertEquals(2, rules.size(), "La lista delle regole dovrebbe contenere due regole");
        assertTrue(rules.contains(rule1) && rules.contains(rule2), "La lista delle regole dovrebbe contenere entrambe le regole aggiunte");
    }

    // Implementazione di test per Trigger
    private static class TestTrigger implements Trigger {
        private boolean shouldTrigger;

        public TestTrigger(boolean shouldTrigger) {
            this.shouldTrigger = shouldTrigger;
        }

        @Override
        public boolean evaluate() {
            return shouldTrigger;
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
    private static class TestAction implements Action {
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