package com.ccll.projectse_ifttt.TestUtilsClasses;

import com.ccll.projectse_ifttt.Triggers.Trigger;

public class TriggerTestUtils implements Trigger {
    private boolean shouldTrigger;
    private boolean lastEvaluation;


    public TriggerTestUtils(boolean shouldTrigger) {
        this.shouldTrigger = shouldTrigger;
        this.lastEvaluation = false;
    }

    @Override
    public boolean evaluate() {
        boolean evaluation = false;
        if(!lastEvaluation && shouldTrigger) {
            evaluation = true;
        }
        lastEvaluation = shouldTrigger;
        return evaluation;
    }

    @Override
    public void reset() {
        lastEvaluation = false;
    }

    public void setShouldTrigger(boolean shouldTrigger) {
        this.shouldTrigger = shouldTrigger;
    }

    @Override
    public String toString() {
        return "TestTrigger{shouldTrigger=" + shouldTrigger + '}';
    }
}
