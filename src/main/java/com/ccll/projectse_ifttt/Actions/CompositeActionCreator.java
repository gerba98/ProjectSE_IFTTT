package com.ccll.projectse_ifttt.Actions;

import com.ccll.projectse_ifttt.Rule.RuleManager;

public class CompositeActionCreator extends ActionCreator {

    @Override
    public Action createAction(String actionsValue) {
        String[] parts = actionsValue.split(":");
        CompositeAction compositeAction = new CompositeAction();

        // Per ogni coppia tipo-valore, creiamo e aggiungiamo l'action
        for (int i = 0; i < parts.length; i += 2) {
            String actionType = parts[i];
            String actionValue = parts[i + 1];
            Action action = RuleManager.createAction(actionType, actionValue);
            compositeAction.addAction(action);
        }

        return compositeAction;
    }

    @Override
    public String getType() {
        return "composite";
    }

}
