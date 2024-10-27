package com.ccll.projectse_ifttt.Triggers;

import com.ccll.projectse_ifttt.Rule.RuleManager;

public class CompositeTrigCreator extends TriggerCreator {


    @Override
    public Trigger createTrigger(String triggers) {
        // Il formato del triggerValue sarà: "OPERATOR:trigger1Type:(opera))"
        String[] parts = triggers.split(":");

        LogicalOperator operator = LogicalOperator.valueOf(parts[0]);
        CompositeTrigger compositeTrigger = new CompositeTrigger(operator);

        // Per ogni coppia tipo-valore, creiamo e aggiungiamo il trigger
        for (int i = 1; i < parts.length; i += 2) {
            String triggerType = parts[i];
            String triggerValue = parts[i + 1];
            Trigger trigger = RuleManager.createTrigger(triggerType, triggerValue);
            compositeTrigger.addTrigger(trigger);
        }

        return compositeTrigger;
    }

    @Override
    public String getType() {
        return "Composite";
    }
}