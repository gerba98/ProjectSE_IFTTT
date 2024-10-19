package com.ccll.projectse_ifttt.Triggers;

import java.time.DayOfWeek;

/**
 * Rappresenta un creatore di trigger per l'attivazione a un giorno specificato della settimana.
 * Questa classe estende la classe {TriggerCreator} e fornisce un metodo per creare
 * un trigger di tipo {DayOfTheWeekTrig}.
 */
public class DOTWTrigCreator extends TriggerCreator{


    /**
     * Crea un nuovo trigger di tipo {DayOfTheWeekTrig} con il giorno specificato.
     *
     * @return un nuovo oggetto {Trigger} che si attiva al giorno della settimana specificato.
     */
    @Override
    public Trigger createTrigger(String triggerValue){
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(triggerValue.toUpperCase());
        return new DayOfTheWeekTrig(dayOfWeek);
    }

    @Override
    public String getType() {return "day of the week";}
}
