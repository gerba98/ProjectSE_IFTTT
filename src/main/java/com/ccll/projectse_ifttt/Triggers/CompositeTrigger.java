package com.ccll.projectse_ifttt.Triggers;

import java.util.ArrayList;
import java.util.List;

public class CompositeTrigger extends AbstractTrigger {
    private static final int MAX_TRIGGERS = 2;
    private final LogicalOperator operator;
    private final List<Trigger> triggers;

    public CompositeTrigger(LogicalOperator operator) {
        this.operator = operator;
        this.triggers = new ArrayList<>();
    }

    public void addTrigger(Trigger trigger) {
        if (triggers.size() >= MAX_TRIGGERS) {
            throw new IllegalStateException("Cannot add more than " + MAX_TRIGGERS + " triggers to a composite trigger");
        }
        triggers.add(trigger);
    }

    public void removeTrigger(Trigger trigger) {
        triggers.remove(trigger);
    }

    public List<Trigger> getChildren() {
        return this.triggers;
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
        if (triggers.size() != 2) {
            throw new IllegalStateException("AND operator requires exactly two triggers");
        }
        return triggers.stream().allMatch(Trigger::getCurrentEvaluation);
    }

    private boolean evaluateOr() {
        if (triggers.size() != 2) {
            throw new IllegalStateException("OR operator requires exactly two triggers");
        }
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
        return "COMPOSITE;" + getTriggerExpression();
    }

    private String getTriggerExpression() {
        StringBuilder sb = new StringBuilder();
        sb.append(operator);
        sb.append("(");

        for (int i = 0; i < triggers.size(); i++) {
            String triggerStr = getTriggerStr(i);
            sb.append(triggerStr);
            if (i < triggers.size() - 1) {
                sb.append("@");
            }
        }

        sb.append(")");
        return sb.toString();
    }

    private String getTriggerStr(int i) {
        Trigger trigger = triggers.get(i);

        String triggerStr = trigger.toString();
        if (trigger instanceof CompositeTrigger) {
            triggerStr = triggerStr.split(";")[1];
        } else {
            triggerStr = triggerStr.replace(";", "#");
        }
        return triggerStr;
    }
}