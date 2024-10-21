package com.ccll.projectse_ifttt.TestUtilsClasses;

import com.ccll.projectse_ifttt.Actions.Action;

public class ActionTestUtils implements Action {
    private boolean executed = false;

    @Override
    public boolean execute() {
        executed = true;
        return true;
    }

    public boolean wasExecuted() {
        return executed;
    }

    public void reset() {
        executed = false;
    }

    @Override
    public String toString() {
        return "TestAction{executed=" + executed + '}';
    }
}
