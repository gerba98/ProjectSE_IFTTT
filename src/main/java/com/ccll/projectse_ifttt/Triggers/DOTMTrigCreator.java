package com.ccll.projectse_ifttt.Triggers;

import java.time.MonthDay;


/**
 * Rappresenta un creatore di trigger per l'attivazione a un giorno specificato del mese.
 * Questa classe estende la classe {TriggerCreator} e fornisce un metodo per creare
 * un trigger di tipo {DayOfTheMonthTrig}.
 */
public class DOTMTrigCreator extends TriggerCreator {

    /**
     * Crea un nuovo trigger di tipo {DayOfTheMonthTrig} con il giorno specificato.
     *
     * @return un nuovo oggetto {Trigger} che si attiva al giorno del mese specificato.
     */
    @Override
    public Trigger createTrigger(String triggerValue){
        String[] list = triggerValue.split("-");
        MonthDay dayOfMonth = MonthDay.of(Integer.parseInt(list[1]), Integer.parseInt(list[2]));
        return new DayOfTheMonthTrig(dayOfMonth);
    }

    @Override
    public String getType(){return "day of the month";}

}
