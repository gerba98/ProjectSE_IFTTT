package com.ccll.projectse_ifttt.Triggers;

import java.time.LocalDate;

/**
 * Implementazione concreta di {@link TriggerCreator} per la creazione di trigger basati su una data specifica.
 * Fornisce un metodo per creare istanze di {@link SpecifiedDateTrig}, che si attivano quando la data corrente
 * corrisponde alla data specificata.
 */
public class SDTrigCreator extends TriggerCreator {

    /**
     * Crea un nuovo trigger di tipo {@link SpecifiedDateTrig} che si attiva alla data specificata.
     * La data deve essere fornita come stringa nel formato ISO-8601 (YYYY-MM-DD), che viene convertita in {@link LocalDate}.
     *
     * @param triggerValue la data in formato stringa (YYYY-MM-DD) a cui il trigger dovrebbe attivarsi.
     * @return un nuovo oggetto {@link Trigger} che si attiva alla data specificata.
     */
    @Override
    public Trigger createTrigger(String triggerValue) {
        LocalDate date = LocalDate.parse(triggerValue);
        return new SpecifiedDateTrig(date);
    }

    /**
     * Fornisce il tipo di trigger che questa factory è specializzata a creare.
     *
     * @return una stringa che descrive il tipo di trigger, in questo caso "date".
     */
    @Override
    public String getType() {
        return "date";
    }
}
