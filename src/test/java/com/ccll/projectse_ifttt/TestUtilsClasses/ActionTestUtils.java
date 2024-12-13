package com.ccll.projectse_ifttt.TestUtilsClasses;

import com.ccll.projectse_ifttt.Actions.Action;

public class ActionTestUtils implements Action {
    private boolean executed = false;
    private boolean result = true; // Impostazione predefinita

    @Override
    public boolean execute() {
        executed = true;
        return result;
    }

    public boolean wasExecuted() {
        return executed;
    }

    public void reset() {
        executed = false;
    }

    // Configura il risultato dell'esecuzione (true o false)
    public void setExecutionResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "TestAction{executed=" + executed + '}';
    }
}
