package com.ccll.projectse_ifttt.Triggers;

import com.ccll.projectse_ifttt.Rule.Rule;
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
    void testComplexNestedTriggerEvaluation() {

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
    void testTriggerCreation(){

        String targetTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES).toString();
        Trigger t1 = RuleManager.createTrigger("time of the day", targetTime);

        Trigger t2 = RuleManager.createTrigger("day of the week", "Monday");

        Trigger t3 = RuleManager.createTrigger("day of the week", "Monday");



        CompositeTrigger t4 = new CompositeTrigger(LogicalOperator.AND); //true
        t4.addTrigger(t1);
        t4.addTrigger(t2);

        CompositeTrigger notT4 = new CompositeTrigger(LogicalOperator.NOT); //false
        notT4.addTrigger(t4);

        CompositeTrigger t5 = new CompositeTrigger(LogicalOperator.OR);
        t5.addTrigger(notT4);
        t5.addTrigger(t3);



        System.out.println(t5);
        //System.out.println(t5.toTreeString(0));

        Trigger t = RuleManager.createTrigger("composite", t5.toString().split(";")[1]);
        System.out.println(t.evaluate());
        //CompositeTrigger tc = (CompositeTrigger) t;
        //System.out.println(t5.toTreeString(0));

//        CompositeTrigger t5 = new CompositeTrigger(LogicalOperator.OR);
//        Trigger t4 = RuleManager.createTrigger("day of the month", "2024-10-29");
//        t5.addTrigger(t4);
//        t5.addTrigger(t3);
//
//
//        Trigger t = RuleManager.createTrigger("composite", t5.toString().split(";")[1]);
//        CompositeTrigger tc = (CompositeTrigger) t;

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

    @Test
    void testEdgeTriggeredBehavior() {

        TriggerTestUtils trigger1 = new TriggerTestUtils(true);

        // Prima valutazione
        assertTrue(trigger1.evaluate(), "La prima valutazione dovrebbe essere true quando entrambi i trigger sono true");

        // Seconda valutazione senza cambiamenti
        assertFalse(trigger1.evaluate(), "La seconda valutazione dovrebbe essere false (edge-triggered)");

        // Cambio stato e nuova valutazione
        trigger1.setShouldTrigger(false);

        trigger1.evaluate();
        assertFalse(trigger1.evaluate(), "La terza valutazione dovrebbe essere false in quanto un trigger è false");

        trigger1.setShouldTrigger(true);
        assertTrue(trigger1.evaluate(), "La valutazione dopo un cambiamento di stato dovrebbe essere true");
    }



}