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
        sb.append(COMPOSITE).append(";").append(operator).append(":");
        for (int i = 0; i < triggers.size(); i++) {
            sb.append(triggers.get(i).toString().replace(";",":"));
            if (i < triggers.size() - 1) {
                sb.append(":");
            }
        }
        return sb.toString();
    }
}

