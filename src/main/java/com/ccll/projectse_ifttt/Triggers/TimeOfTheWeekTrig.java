package com.ccll.projectse_ifttt.Triggers;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class TimeOfTheWeekTrig implements Trigger{
    DayOfWeek dayOfWeek;

    public TimeOfTheWeekTrig(DayOfWeek dayOfWeek){this.dayOfWeek = dayOfWeek;}

    public DayOfWeek getDayOfWeek(){return dayOfWeek;}
    public void setDayOfWeek(DayOfWeek dayOfWeek){this.dayOfWeek = dayOfWeek;}

    @Override
    public boolean evaluate(){
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        return today.equals(dayOfWeek);
    }

    @Override
    public String toString(){return "Trigger attivato a: " + dayOfWeek;}

}
