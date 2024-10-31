package com.ccll.projectse_ifttt.Rule;

import com.ccll.projectse_ifttt.TestUtilsClasses.ActionTestUtils;
import com.ccll.projectse_ifttt.TestUtilsClasses.TriggerTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class RuleTest {
    private TriggerTestUtils testTrigger;
    private ActionTestUtils testAction;
    private Rule rule;

    @BeforeEach
    void setUp() {
        testTrigger = new TriggerTestUtils(false);
        testAction = new ActionTestUtils();
        rule = new Rule("Regola di Test", testTrigger, testAction);
    }

    @Test
    void testGetterSetter() {
        assertEquals("Regola di Test", rule.getName(), "Il nome della regola dovrebbe corrispondere");
        assertSame(testTrigger, rule.getTrigger(), "Il trigger dovrebbe essere lo stesso oggetto");
        assertSame(testAction, rule.getAction(), "L'azione dovrebbe essere lo stesso oggetto");

        TriggerTestUtils testTrigger1 = new TriggerTestUtils(true);
        ActionTestUtils testAction1 = new ActionTestUtils();
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
}