package com.ccll.projectse_ifttt.Triggers;

import com.ccll.projectse_ifttt.Rule.RuleManager;

import java.util.ArrayList;
import java.util.List;

public class CompositeTrigCreator extends TriggerCreator {

    private static final String TRIGGER_SEPARATOR = "@";

    @Override
    public Trigger createTrigger(String triggerString) {
        return parseTriggerExpression(triggerString);
    }

    private Trigger parseTriggerExpression(String expression) {
        // Remove any leading/trailing whitespace
        expression = expression.trim();

        // Find the operator
        LogicalOperator operator;
        String remaining;

        if (expression.startsWith("NOT")) {
            operator = LogicalOperator.NOT;
            remaining = expression.substring(3).trim();
        } else if (expression.startsWith("AND")) {
            operator = LogicalOperator.AND;
            remaining = expression.substring(3).trim();
        } else if (expression.startsWith("OR")) {
            operator = LogicalOperator.OR;
            remaining = expression.substring(2).trim();
        } else {
            throw new IllegalArgumentException("Invalid expression: " + expression);
        }

        // Remove outer parentheses if present
        if (remaining.startsWith("(") && remaining.endsWith(")")) {
            remaining = remaining.substring(1, remaining.length() - 1);
        }

        CompositeTrigger compositeTrigger = new CompositeTrigger(operator);

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
                triggers.add(currentTrigger.toString().trim());
                currentTrigger = new StringBuilder();
            } else {
                currentTrigger.append(c);
            }
        }

        // Add the last trigger
        if (!currentTrigger.isEmpty()) {
            triggers.add(currentTrigger.toString().trim());
        }

        return triggers;
    }

    @Override
    public String getType() {
        return "Composite";
    }
}