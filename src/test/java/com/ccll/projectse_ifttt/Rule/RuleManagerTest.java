package com.ccll.projectse_ifttt.Rule;

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
        Rule rule1 = new Rule(null, null, "Regola 1");
        Rule rule2 = new Rule(null, null, "Regola 2");

        testRuleManager.addRule(rule1);
        testRuleManager.addRule(rule2);

        ObservableList<Rule> rules = testRuleManager.getRules();
        // verify getRules() method;

        assertEquals(2, rules.size(), "La lista delle regole dovrebbe contenere due regole");
        assertTrue(rules.contains(rule1) && rules.contains(rule2), "La lista delle regole dovrebbe contenere entrambe le regole aggiunte");
    }


//    @Test
//    @DisplayName("Verifica Immutabilità Lista Regole")
//    void testRulesListImmutability() {
//        Rule regolaMock = new Rule(null, null, "Regola di Test");
//        ruleManager.addRule(regolaMock);
//
//        ObservableList<Rule> regole = ruleManager.getRules();
//        assertThrows(UnsupportedOperationException.class, () -> regole.add(new Rule(null, null, "Altra Regola")),
//                "La lista delle regole dovrebbe essere non modificabile");
//    }
//
}