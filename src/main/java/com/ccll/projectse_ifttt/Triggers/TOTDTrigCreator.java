package com.ccll.projectse_ifttt.Triggers;

import java.time.LocalTime;

/**
 * Rappresenta un creatore di trigger per l'attivazione a un'ora specifica del giorno.
 * Questa classe estende la classe {TriggerCreator} e fornisce un metodo per creare
 * un trigger di tipo {TimeOfTheDayTrig}.
 */
public class TOTDTrigCreator extends TriggerCreator {

    /**
     * Crea un nuovo trigger di tipo {TimeOfTheDayTrig} con l'ora specificata.
     *
     * @return un nuovo oggetto {Trigger} che si attiva all'ora specificata.
     */
    @Override
    public Trigger createTrigger(String triggerValue) {
        LocalTime time = LocalTime.parse(triggerValue);
        return new TimeOfTheDayTrig(time);
    }

    @Override
    public String getType() {
        return "Time of the day";
    }
}
