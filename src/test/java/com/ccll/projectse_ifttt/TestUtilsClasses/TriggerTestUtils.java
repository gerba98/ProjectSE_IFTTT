package com.ccll.projectse_ifttt.TestUtilsClasses;

import com.ccll.projectse_ifttt.Triggers.AbstractTrigger;

public class TriggerTestUtils extends AbstractTrigger {
    private boolean shouldTrigger;

    public TriggerTestUtils(boolean shouldTrigger) {
        this.shouldTrigger = shouldTrigger;
    }

    @Override
    public boolean getCurrentEvaluation() {
        return shouldTrigger;
    }


    public void setShouldTrigger(boolean shouldTrigger) {
        this.shouldTrigger = shouldTrigger;
    }

    @Override
    public String toString() {
        return "TestTrigger{shouldTrigger=" + shouldTrigger + '}';
    }
}
