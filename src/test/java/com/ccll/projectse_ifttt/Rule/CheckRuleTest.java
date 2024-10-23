package com.ccll.projectse_ifttt.Rule;

import com.ccll.projectse_ifttt.TestUtilsClasses.ActionTestUtils;
import com.ccll.projectse_ifttt.TestUtilsClasses.TriggerTestUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static com.ccll.projectse_ifttt.TestUtilsClasses.TestUtils.waitCheckRule;

class CheckRuleTest {

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
    @DisplayName("Verifica esecuzione regola quando il trigger è true")
    void testRuleExecutionWhenTriggerIsTrue() {
        TriggerTestUtils trigger1 = new TriggerTestUtils(true);
        ActionTestUtils action1 = new ActionTestUtils();
        Rule testRule = new Rule("Regola di Test 1", trigger1, action1);

        testRuleManager.addRule(testRule);
        waitCheckRule();

        Assertions.assertTrue(action1.wasExecuted(), "L'azione dovrebbe essere stata eseguita");
    }

    @Test
    @DisplayName("Verifica non esecuzione regola quando il trigger è false")
    void testRuleNonExecutionWhenTriggerIsFalse() {
        TriggerTestUtils triggerTestUtils = new TriggerTestUtils(false);
        ActionTestUtils actionTestUtils = new ActionTestUtils();
        Rule testRule = new Rule("Regola di Test 2", triggerTestUtils, actionTestUtils);

        testRuleManager.addRule(testRule);
        waitCheckRule();

        Assertions.assertFalse(actionTestUtils.wasExecuted(), "L'azione non dovrebbe essere stata eseguita");
    }

    @Test
    @DisplayName("Verifica esecuzione di regole multiple")
    void testMultipleRulesExecution() {
        TriggerTestUtils trigger1 = new TriggerTestUtils(true);
        ActionTestUtils action1 = new ActionTestUtils();
        Rule rule1 = new Rule("Regola 1", trigger1, action1);

        TriggerTestUtils trigger2 = new TriggerTestUtils(false);
        ActionTestUtils action2 = new ActionTestUtils();
        Rule rule2 = new Rule("Regola 2", trigger2, action2);

        testRuleManager.addRule(rule1);
        testRuleManager.addRule(rule2);

        waitCheckRule();

        Assertions.assertTrue(action1.wasExecuted(), "L'azione 1 dovrebbe essere stata eseguita");
        Assertions.assertFalse(action2.wasExecuted(), "L'azione 2 non dovrebbe essere stata eseguita");
    }

    @Test
    @DisplayName("Verifica esecuzione regola dopo cambio del trigger")
    void testRuleExecutionAfterTriggerChange() {
        TriggerTestUtils trigger1 = new TriggerTestUtils(false);
        ActionTestUtils action1 = new ActionTestUtils();
        Rule testRule = new Rule("Regola di Test 3", trigger1, action1);

        testRuleManager.addRule(testRule);

        waitCheckRule();

        Assertions.assertFalse(action1.wasExecuted(), "L'azione non dovrebbe essere stata eseguita inizialmente");

        trigger1.setShouldTrigger(true);

        waitCheckRule();

        Assertions.assertTrue(action1.wasExecuted(), "L'azione dovrebbe essere stata eseguita dopo il cambiamento del trigger");
    }


}
