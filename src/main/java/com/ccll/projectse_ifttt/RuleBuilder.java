package com.ccll.projectse_ifttt;

import com.ccll.projectse_ifttt.Rule.Rule;
import com.ccll.projectse_ifttt.Rule.RuleManager;

public class RuleBuilder {
    private String name;
    private String triggerType;
    private String actionType;
    private String triggerDetails;
    private String actionDetails;
    private boolean isPeriodic = false;
    private boolean isSingle = false;
    private String period;

    public RuleBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public RuleBuilder setTriggerType(String triggerType) {
        this.triggerType = triggerType;
        return this;
    }

    public RuleBuilder setActionType(String actionType) {
        this.actionType = actionType;
        return this;
    }

    public RuleBuilder setTriggerDetails(String triggerDetails) {
        this.triggerDetails = triggerDetails;
        return this;
    }

    public RuleBuilder setActionDetails(String actionDetails) {
        this.actionDetails = actionDetails;
        return this;
    }

    public RuleBuilder setPeriodic(String period) {
        this.isPeriodic = true;
        this.isSingle = false;
        this.period = period;
        return this;
    }

    public RuleBuilder setSingle() {
        this.isSingle = true;
        this.isPeriodic = false;
        return this;
    }

    public Rule build() {
        // Valida gli input
        validate();

        RuleManager ruleManager = RuleManager.getInstance();
        if (isPeriodic) {
            return ruleManager.createRule(name, triggerType, triggerDetails, actionType, actionDetails, "periodicrule-" + period);
        } else if(isSingle) {
            return ruleManager.createRule(name, triggerType, triggerDetails, actionType, actionDetails, "singlerule");
        }else{
            return ruleManager.createRule(name, triggerType, triggerDetails, actionType, actionDetails);
        }
    }

    private void validate() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (triggerType == null || triggerType.isEmpty()) {
            throw new IllegalArgumentException("TriggerType cannot be null or empty");
        }
        if (actionType == null || actionType.isEmpty()) {
            throw new IllegalArgumentException("ActionType cannot be null or empty");
        }
        if(triggerDetails == null || triggerDetails.isEmpty()) {
            throw new IllegalArgumentException("TriggerDetails cannot be null or empty");
        }
        if(actionDetails == null || actionDetails.isEmpty()) {
            throw new IllegalArgumentException("ActionDetails cannot be null or empty");
        }
        // Aggiungere altre validazioni specifiche se necessario
    }
}