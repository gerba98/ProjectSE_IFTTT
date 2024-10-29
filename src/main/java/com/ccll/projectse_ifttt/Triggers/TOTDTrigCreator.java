package com.ccll.projectse_ifttt.Triggers;

import java.time.LocalTime;

/**
 * Implementazione concreta di {@link TriggerCreator} per la creazione di trigger basati sull'ora del giorno.
 * Fornisce un'implementazione specifica per creare istanze di {@link TimeOfTheDayTrig}, che attivano azioni
 * a un'ora specifica del giorno.
 */
public class TOTDTrigCreator extends TriggerCreator {

    /**
     * Crea un nuovo trigger di tipo {@link TimeOfTheDayTrig} che si attiva all'ora del giorno specificata.
     * L'ora del giorno è fornita come stringa nel formato ISO-8601 (HH:MM), che viene convertita in {@link LocalTime}.
     *
     * @param triggerValue l'ora del giorno in formato stringa (HH:MM) a cui il trigger dovrebbe attivarsi.
     * @return un nuovo oggetto {@link Trigger} che si attiva all'ora specificata.
     */
    @Override
    public Trigger createTrigger(String triggerValue) {
        LocalTime time = LocalTime.parse(triggerValue); // Convert the string to a LocalTime object
        return new TimeOfTheDayTrig(time);
    }

    /**
     * Fornisce il tipo di trigger che questa factory è specializzata a creare.
     *
     * @return una stringa che descrive il tipo di trigger, in questo caso "Time of the day".
     */
    @Override
    public String getType() {
        return "Time of the day";
    }
}
