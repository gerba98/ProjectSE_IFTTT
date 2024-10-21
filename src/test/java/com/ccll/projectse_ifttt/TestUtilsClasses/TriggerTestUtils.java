package com.ccll.projectse_ifttt.TestUtilsClasses;

import com.ccll.projectse_ifttt.Triggers.Trigger;

public class TriggerTestUtils implements Trigger {
    private boolean shouldTrigger;

    public TriggerTestUtils(boolean shouldTrigger) {
        this.shouldTrigger = shouldTrigger;
    }

    @Override
    public boolean evaluate() {
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
