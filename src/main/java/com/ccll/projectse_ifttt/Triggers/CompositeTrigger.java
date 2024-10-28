package com.ccll.projectse_ifttt.Triggers;

import java.util.ArrayList;
import java.util.List;

import static com.ccll.projectse_ifttt.Triggers.TriggerType.COMPOSITE;

public class CompositeTrigger extends AbstractTrigger {
    private final LogicalOperator operator;
    private final List<Trigger> triggers;

    public CompositeTrigger(LogicalOperator operator) {
        this.operator = operator;
        this.triggers = new ArrayList<>();
    }

    public void addTrigger(Trigger trigger) {
        triggers.add(trigger);
    }

    public void removeTrigger(Trigger trigger) {
        triggers.remove(trigger);
    }

    @Override
    public boolean getCurrentEvaluation() {
        return switch (operator) {
            case AND -> evaluateAnd();
            case OR -> evaluateOr();
            case NOT -> evaluateNot();
        };
    }

    private boolean evaluateAnd() {
        return triggers.stream().allMatch(Trigger::getCurrentEvaluation);
    }

    private boolean evaluateOr() {
        return triggers.stream().anyMatch(Trigger::getCurrentEvaluation);
    }

    private boolean evaluateNot() {
        if (triggers.size() != 1) {
            throw new IllegalStateException("NOT operator requires exactly one trigger");
        }
        return !triggers.getFirst().getCurrentEvaluation();
    }

    @Override
    public void reset() {
        super.reset();
        triggers.forEach(Trigger::reset);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("COMPOSITE;");
        sb.append(formatTriggerExpression());
        return sb.toString();
    }

    private String formatTriggerExpression() {
        System.out.println(triggers.size());
        StringBuilder sb = new StringBuilder();

        // Add operator
        sb.append(operator);

        // Add opening parenthesis for complex expressions
        boolean needsParentheses = triggers.size() > 1 || triggers.get(0) instanceof CompositeTrigger;
        if (needsParentheses) {
            sb.append("(");
        }

        // Add triggers
        for (int i = 0; i < triggers.size(); i++) {
            Trigger trigger = triggers.get(i);

            // Handle the trigger string
            String triggerStr = trigger.toString();
            if (trigger instanceof CompositeTrigger) {
                // For composite triggers, only keep the expression part
                triggerStr = triggerStr.split(";")[1];
            } else {
                // For simple triggers, replace semicolons with #
                triggerStr = triggerStr.replace(";", "#");
            }

            sb.append(triggerStr);

            // Add separator between triggers
            if (i < triggers.size() - 1) {
                sb.append("@");
            }
        }

        // Add closing parenthesis for complex expressions
        if (needsParentheses) {
            sb.append(")");
        }

        return sb.toString();
    }


}

