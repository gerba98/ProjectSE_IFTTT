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
    @DisplayName("Verifica il funzionamento base della regola periodica: esecuzione dell'azione e riattivazione dopo il periodo")
    void testBasicPeriodicRuleExecution() {
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
    @DisplayName("Verifica che la regola rimane inattiva quando l'utente la disattiva manualmente")
    void testRuleRemainsInactiveWhenReactivatedIsFalse() {
        TriggerTestUtils trigger = new TriggerTestUtils(false);
        ActionTestUtils action = new ActionTestUtils();
        PeriodicRule periodicRule = new PeriodicRule("test", trigger, action, "0:0:1");

        testRuleManager.addRule(periodicRule);
        waitCheckRule();
        Assertions.assertTrue(periodicRule.isState(), "La regola dovrebbe essere inizialmente attiva");


        // L'utente disattiva la regola mentre la regola è attiva
        periodicRule.setState(false);
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

        // l'utente disattiva la regola mentre la regola è disattivata temporaneamente
        trigger.setShouldTrigger(true);
        waitCheckRule();
        Assertions.assertEquals(1, periodicRule.getNumberOfExecutions(), "l'azione dovrebbe essere stata eseguita");
        Assertions.assertFalse(periodicRule.isState(), "La regola dovrebbe rimanere inattiva dopo il periodo");

        trigger.setShouldTrigger(false);
        periodicRule.setReactivated(false);
        waitCheckRule(61000);
        Assertions.assertFalse(periodicRule.isState(), "La regola dovrebbe essere disattivata dopo l'esecuzione");
        Assertions.assertFalse(periodicRule.isReactivated(), "La variabile Reactivated dovrebbe essere settata a true");

        //funzionamento base
        periodicRule.setState(true);
        trigger.setShouldTrigger(true);
        waitCheckRule();
        Assertions.assertEquals(2, periodicRule.getNumberOfExecutions(), "l'azione dovrebbe essere stata eseguita");
        Assertions.assertFalse(periodicRule.isState(), "La regola dovrebbe essere disattivata dopo l'esecuzione");

        trigger.setShouldTrigger(false);
        waitCheckRule(61000);
        Assertions.assertTrue(periodicRule.isState(), "La regola dovrebbe essere nuovamente attiva dopo il periodo");
    }


    @Test
    @DisplayName("Verifica il comportamento della regola quando viene riattivata  durante il periodo in cui è disattivata temporaneamente")
    void testManualReactivationDuringPeriod() {
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

//    @Test
//    @DisplayName("Verifica che il periodo specificato viene rispettato prima della riattivazione")
//    void testPeriodDurationBeforeReactivation() {
//        TriggerTestUtils trigger = new TriggerTestUtils(false);
//        ActionTestUtils action = new ActionTestUtils();
//        Rule periodicRule = new PeriodicRule("test", trigger, action, "0:0:1");  // periodo di 2 minuti
//
//        testRuleManager.addRule(periodicRule);
//        waitCheckRule();
//        Assertions.assertTrue(periodicRule.isState(), "La regola dovrebbe essere inizialmente attiva");
//
//        trigger.setShouldTrigger(true);
//        waitCheckRule(1000);
//        Assertions.assertEquals(1, periodicRule.getNumberOfExecutions(), "L'azione dovrebbe essere stata eseguita");
//        Assertions.assertFalse(periodicRule.isState(), "La regola dovrebbe essere inattiva dopo l'esecuzione");
//
//        waitCheckRule(59000); // Attende 1 minuto
//        Assertions.assertFalse(periodicRule.isState(), "La regola dovrebbe rimanere inattiva prima del completamento del periodo");
//
//        waitCheckRule(1000); // Attende un altro minuto (totale 2 minuti)
//        Assertions.assertTrue(periodicRule.isState(), "La regola dovrebbe essere attiva dopo il periodo completo");
//    }
}