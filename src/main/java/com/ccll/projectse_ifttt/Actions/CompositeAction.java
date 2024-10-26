package com.ccll.projectse_ifttt.Actions;

import com.ccll.projectse_ifttt.Rule.RuleManager;

import java.util.ArrayList;
import java.util.List;

public class CompositeAction implements Action{

    private final List<Action> actions;

    public CompositeAction() {
        this.actions = new ArrayList<Action>();
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public void removeAction(Action action) {
        actions.remove(action);
    }

    public List<Action> getChildren(){
        return this.actions;
    }

    @Override
    public boolean execute() {
        boolean success = true;
        for (Action action : actions) {
            success &= action.execute();
        }
        return success;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CompositeAction;");

        for (int i = 0; i < actions.size(); i++) {
            sb.append(actions.get(i).toString().replace(';', ':'));
            if (i < actions.size() - 1) {
                sb.append(":");
            }
        }

        return sb.toString();
    }

}
