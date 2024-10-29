package com.ccll.projectse_ifttt.Triggers;

import com.ccll.projectse_ifttt.Rule.RuleManager;
import com.ccll.projectse_ifttt.TestUtilsClasses.TriggerTestUtils;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
class CompositeTriggerTest {

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
    void testNotTriggerCreation() {

        CompositeTrigger notTrigger = new CompositeTrigger(LogicalOperator.NOT);
        TriggerTestUtils testTrigger = new TriggerTestUtils(true);
        notTrigger.addTrigger(testTrigger);

        assertFalse(notTrigger.getCurrentEvaluation(), "Il trigger ha invertito correttamente il valore del trigger interno");
    }

    @Test
    void testAndTriggerEvaluation() {
        CompositeTrigger andTrigger = new CompositeTrigger(LogicalOperator.AND);
        TriggerTestUtils trigger1 = new TriggerTestUtils(true);
        TriggerTestUtils trigger2 = new TriggerTestUtils(true);

        andTrigger.addTrigger(trigger1);
        andTrigger.addTrigger(trigger2);

        assertTrue(andTrigger.getCurrentEvaluation(),
                "Il trigger AND dovrebbe restituire true quando entrambi i trigger sono true");

        trigger2.setShouldTrigger(false);
        assertFalse(andTrigger.getCurrentEvaluation(),
                "Il trigger AND dovrebbe restituire false quando almeno un trigger è false");
    }


    @Test
    void testOrTriggerEvaluation() {

        CompositeTrigger orTrigger = new CompositeTrigger(LogicalOperator.OR);
        TriggerTestUtils trigger1 = new TriggerTestUtils(false);
        TriggerTestUtils trigger2 = new TriggerTestUtils(true);

        orTrigger.addTrigger(trigger1);
        orTrigger.addTrigger(trigger2);

        assertTrue(orTrigger.getCurrentEvaluation(),
                "Il trigger OR dovrebbe restituire true quando almeno un trigger è true");

        trigger2.setShouldTrigger(false);
        assertFalse(orTrigger.getCurrentEvaluation(),
                "Il trigger OR dovrebbe restituire false quando tutti i trigger sono false");
    }

    @Test
    void testNestedTriggerEvaluation() {

        // NOT(trigger1)
        CompositeTrigger notTrigger = new CompositeTrigger(LogicalOperator.NOT);
        TriggerTestUtils trigger1 = new TriggerTestUtils(true);
        notTrigger.addTrigger(trigger1);

        // AND(trigger2, NOT(trigger1))
        CompositeTrigger andTrigger = new CompositeTrigger(LogicalOperator.AND);
        TriggerTestUtils trigger2 = new TriggerTestUtils(true);
        andTrigger.addTrigger(trigger2);
        andTrigger.addTrigger(notTrigger);

        assertFalse(andTrigger.getCurrentEvaluation(), "Il trigger AND(trigger2, NOT(trigger1)) dovrebbe restituire false");

        trigger1.setShouldTrigger(false);
        assertTrue(andTrigger.getCurrentEvaluation(), "Il trigger AND(trigger2, NOT(trigger1)) dovrebbe restituire true");
    }

    @Test
    void testTriggerCreation() {

        Trigger t1 = RuleManager.createTrigger("time of the day", "11:30");

        Trigger t2 = RuleManager.createTrigger("day of the week", "Monday");

        Trigger t3 = RuleManager.createTrigger("day of the week", "Monday");

        CompositeTrigger notT1 = new CompositeTrigger(LogicalOperator.NOT); //false
        notT1.addTrigger(t1);

        CompositeTrigger t4 = new CompositeTrigger(LogicalOperator.AND);
        t4.addTrigger(t2);
        t4.addTrigger(notT1);


        CompositeTrigger notT4 = new CompositeTrigger(LogicalOperator.NOT); //false
        notT4.addTrigger(t4);

        CompositeTrigger t5 = new CompositeTrigger(LogicalOperator.OR);
        t5.addTrigger(t3);
        t5.addTrigger(notT4);


        CompositeTrigger notT5 = new CompositeTrigger(LogicalOperator.NOT);
        notT5.addTrigger(t5);

        Trigger t = RuleManager.createTrigger("composite", notT5.toString().split(";")[1]);
        assertEquals(notT5.toString(), t.toString(), "il risultato del toString dei due trigger dovrebbe corrispondere");
    }

    @Test
    void testTriggerReset() {

        CompositeTrigger compositeTrigger = new CompositeTrigger(LogicalOperator.AND);
        TriggerTestUtils trigger1 = new TriggerTestUtils(true);
        TriggerTestUtils trigger2 = new TriggerTestUtils(true);

        compositeTrigger.addTrigger(trigger1);
        compositeTrigger.addTrigger(trigger2);

        // Prima valutazione
        boolean firstEvaluation = compositeTrigger.evaluate();
        assertTrue(firstEvaluation, "La prima valutazione dovrebbe essere true");

        // Seconda valutazione senza modifiche
        boolean secondEvaluation = compositeTrigger.evaluate();
        assertFalse(secondEvaluation, "La seconda valutazione dovrebbe essere false");

        // Reset e nuova valutazione
        compositeTrigger.reset();
        boolean evaluationAfterReset = compositeTrigger.evaluate();
        assertTrue(evaluationAfterReset, "La valutazione dopo il reset dovrebbe essere true");
    }
}