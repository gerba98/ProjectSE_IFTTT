package com.ccll.projectse_ifttt.Rule;

import com.ccll.projectse_ifttt.TestUtilsClasses.ActionTestUtils;
import com.ccll.projectse_ifttt.TestUtilsClasses.TriggerTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static com.ccll.projectse_ifttt.TestUtilsClasses.TestUtils.waitCheckRule;

class PeriodicRuleTest {

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
    @DisplayName("CASO 1: Regola attiva -> trigger si verifica -> azione eseguita -> regola disattivata -> dopo il periodo, regola riattivata")
    void testCase1() {
        TriggerTestUtils trigger = new TriggerTestUtils(false);
        ActionTestUtils action = new ActionTestUtils();
        Rule periodicRule = new PeriodicRule("test", trigger, action, "0:0:1");

        testRuleManager.addRule(periodicRule);
        waitCheckRule();
        Assertions.assertTrue(periodicRule.isState(), "La regola dovrebbe essere inizialmente attiva");

        trigger.setShouldTrigger(true);
        waitCheckRule();
        Assertions.assertEquals(1, periodicRule.getNumberOfExecutions(), "l'azione dovrebbe essere stata eseguita");
        Assertions.assertFalse(periodicRule.isState(), "La regola dovrebbe essere disattivata dopo l'esecuzione");

        trigger.setShouldTrigger(false);
        waitCheckRule(61000);
        Assertions.assertTrue(periodicRule.isState(), "La regola dovrebbe essere nuovamente attiva dopo il periodo");

        trigger.setShouldTrigger(true);
        waitCheckRule();
        Assertions.assertEquals(2, periodicRule.getNumberOfExecutions(), "l'azione dovrebbe essere stata eseguita");
        Assertions.assertFalse(periodicRule.isState(), "La regola dovrebbe essere disattivata nuovamente dopo la seconda esecuzione");

        trigger.setShouldTrigger(false);
        waitCheckRule(61000);
        Assertions.assertTrue(periodicRule.isState(), "La regola dovrebbe essere nuovamente attiva dopo il periodo");
    }

    @Test
    @DisplayName("CASO 2: Regola attiva -> utente disattiva la regola -> regola rimane inattiva")
    void testCase2() {
        TriggerTestUtils trigger = new TriggerTestUtils(false);
        ActionTestUtils action = new ActionTestUtils();
        Rule periodicRule = new PeriodicRule("test", trigger, action, "0:0:1");

        testRuleManager.addRule(periodicRule);
        waitCheckRule();
        Assertions.assertTrue(periodicRule.isState(), "La regola dovrebbe essere inizialmente attiva");

        periodicRule.setState(false); // L'utente disattiva la regola
        trigger.setShouldTrigger(true);
        waitCheckRule();
        Assertions.assertFalse(periodicRule.isState(), "La regola dovrebbe essere inattiva dopo la disattivazione dell'utente");
        Assertions.assertFalse(action.wasExecuted(), "L'azione non dovrebbe essere eseguita quando la regola è inattiva");

        trigger.setShouldTrigger(false);
        waitCheckRule(61000);
        Assertions.assertFalse(periodicRule.isState(), "La regola dovrebbe rimanere inattiva dopo il periodo");

        periodicRule.setState(true); //l'utente riattiva la regola
        waitCheckRule();
        Assertions.assertTrue(periodicRule.isState(), "La regola dovrebbe essere attiva");

        //funzionamento base
        trigger.setShouldTrigger(true);
        waitCheckRule();
        Assertions.assertEquals(1, periodicRule.getNumberOfExecutions(), "l'azione dovrebbe essere stata eseguita");
        Assertions.assertFalse(periodicRule.isState(), "La regola dovrebbe essere disattivata dopo l'esecuzione");

        trigger.setShouldTrigger(false);
        waitCheckRule(61000);
        Assertions.assertTrue(periodicRule.isState(), "La regola dovrebbe essere nuovamente attiva dopo il periodo");
    }


    @Test
    @DisplayName("CASO 3: Regola attiva -> trigger si verifica -> azione eseguita -> regola disattivata -> utente riattiva la regola")
    void testCase3() {
        TriggerTestUtils trigger = new TriggerTestUtils(false);
        ActionTestUtils action = new ActionTestUtils();
        Rule periodicRule = new PeriodicRule("test", trigger, action, "0:0:1");

        testRuleManager.addRule(periodicRule);
        waitCheckRule();
        Assertions.assertTrue(periodicRule.isState(), "La regola dovrebbe essere inizialmente attiva");

        trigger.setShouldTrigger(true);
        waitCheckRule();
        Assertions.assertEquals(1, periodicRule.getNumberOfExecutions(), "l'azione dovrebbe essere stata eseguita");
        Assertions.assertFalse(periodicRule.isState(), "La regola dovrebbe essere disattivata dopo l'esecuzione");

        trigger.setShouldTrigger(false);
        periodicRule.setState(true); // L'utente riattiva la regola
        waitCheckRule();
        Assertions.assertTrue(periodicRule.isState(), "La regola dovrebbe essere attiva dopo la riattivazione dell'utente");

        waitCheckRule(61000);
        Assertions.assertTrue(periodicRule.isState(), "La regola dovrebbe essere attiva fino a quando non viene eseguita");

        //funzionamento base
        trigger.setShouldTrigger(true);
        waitCheckRule();
        Assertions.assertEquals(2, periodicRule.getNumberOfExecutions(), "l'azione dovrebbe essere stata eseguita");
        Assertions.assertFalse(periodicRule.isState(), "La regola dovrebbe essere disattivata dopo l'esecuzione");

        trigger.setShouldTrigger(false);
        waitCheckRule(61000);
        Assertions.assertTrue(periodicRule.isState(), "La regola dovrebbe essere nuovamente attiva dopo il periodo");
    }
}