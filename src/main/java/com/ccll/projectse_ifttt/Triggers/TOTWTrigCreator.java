package com.ccll.projectse_ifttt.Triggers;

import java.time.DayOfWeek;

public class TOTWTrigCreator extends TriggerCreator{

    @Override
    public Trigger createTrigger(String triggerValue){
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(triggerValue.toUpperCase());
        return new TimeOfTheWeekTrig(dayOfWeek);
    }

    @Override
    public String getType() {return "day of the week";}
}
