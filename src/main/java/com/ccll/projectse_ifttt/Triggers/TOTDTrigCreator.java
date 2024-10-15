package com.ccll.projectse_ifttt.Triggers;

import java.time.LocalTime;

/**
 * Rappresenta un creatore di trigger per l'attivazione a un'ora specifica del giorno.
 * Questa classe estende la classe {TriggerCreator} e fornisce un metodo per creare
 * un trigger di tipo {TimeOfTheDayTrig}.
 */
public class TOTDTrigCreator extends TriggerCreator {
    private final LocalTime time;

    /**
     * Costruisce un nuovo creatore di trigger con l'ora specificata.
     *
     * @param time l'ora alla quale il trigger deve attivarsi.
     */
    public TOTDTrigCreator(String time) {
        this.time = LocalTime.parse(time);
    }

    /**
     * Crea un nuovo trigger di tipo {TimeOfTheDayTrig} con l'ora specificata.
     *
     * @return un nuovo oggetto {Trigger} che si attiva all'ora specificata.
     */
    @Override
    public Trigger createTrigger() {
        return new TimeOfTheDayTrig(this.time);
    }
}
