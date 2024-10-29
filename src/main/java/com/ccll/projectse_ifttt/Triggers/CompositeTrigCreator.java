package com.ccll.projectse_ifttt.Triggers;

import com.ccll.projectse_ifttt.Rule.RuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe CompositeTrigCreator per creare trigger compositi complessi che combinano trigger di base
 * e\o altri trigger compositi utilizzando gli operatori logici AND, OR e NOT.
 * Estende la classe astratta TriggerCreator e implementa la logica per interpretare espressioni complesse di trigger.
 */
public class CompositeTrigCreator extends TriggerCreator {

    private static final String TRIGGER_SEPARATOR = "@";

    @Override
    public Trigger createTrigger(String triggerString) {
        return parseTriggerExpression(triggerString);
    }

    private Trigger parseTriggerExpression(String expression) {

        LogicalOperator operator;
        String remaining;

        // individuo l'operatore
        if (expression.startsWith("NOT")) {
            operator = LogicalOperator.NOT;
            remaining = expression.substring(3);
        } else if (expression.startsWith("AND")) {
            operator = LogicalOperator.AND;
            remaining = expression.substring(3);
        } else if (expression.startsWith("OR")) {
            operator = LogicalOperator.OR;
            remaining = expression.substring(2);
        } else {
            throw new IllegalArgumentException("Invalid expression: " + expression);
        }

        // creo il trigger composto
        CompositeTrigger compositeTrigger = new CompositeTrigger(operator);

        // rimuovo le parentesi
        remaining = remaining.substring(1, remaining.length() - 1);


        // Parse the triggers inside
        List<String> triggerStrings = splitTriggers(remaining);

        for (String triggerStr : triggerStrings) {
            if (triggerStr.startsWith("AND") || triggerStr.startsWith("OR") || triggerStr.startsWith("NOT")) {
                // Recursive case - this is another composite trigger
                compositeTrigger.addTrigger(parseTriggerExpression(triggerStr));
            } else {
                // Base case - this is a simple trigger
                String[] parts = triggerStr.split("#", 2);
                compositeTrigger.addTrigger(RuleManager.createTrigger(parts[0], parts[1]));
            }
        }

        return compositeTrigger;
    }

    private List<String> splitTriggers(String expression) {
        List<String> triggers = new ArrayList<>();
        int parenthesesCount = 0;
        StringBuilder currentTrigger = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == '(') {
                parenthesesCount++;
            } else if (c == ')') {
                parenthesesCount--;
            }

            if (c == '@' && parenthesesCount == 0) {
                triggers.add(currentTrigger.toString());
                currentTrigger = new StringBuilder();
            } else {
                currentTrigger.append(c);
            }
        }

        if (!currentTrigger.isEmpty()) {
            triggers.add(currentTrigger.toString());
        }

        return triggers;
    }

    @Override
    public String getType() {
        return "Composite";
    }
}