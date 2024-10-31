package com.ccll.projectse_ifttt.Triggers;

import java.time.DayOfWeek;

/**
 * Implementazione concreta di {@link TriggerCreator} per la creazione di trigger basati su un giorno specifico della settimana.
 * Fornisce un metodo per creare istanze di {@link DayOfTheWeekTrig}, che si attivano in un giorno specificato.
 */
public class DOTWTrigCreator extends TriggerCreator {

    /**
     * Crea un nuovo trigger di tipo {@link DayOfTheWeekTrig} che si attiva nel giorno della settimana specificato.
     * Il valore del giorno deve essere fornito come stringa in inglese (ad esempio, "MONDAY").
     *
     * @param triggerValue una stringa rappresentante il giorno della settimana (ad esempio, "MONDAY").
     * @return un nuovo oggetto {@link Trigger} che si attiva al giorno specificato della settimana.
     */
    @Override
    public Trigger createTrigger(String triggerValue) {
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(triggerValue.toUpperCase());
        return new DayOfTheWeekTrig(dayOfWeek);
    }

    /**
     * Fornisce il tipo di trigger che questa factory è specializzata a creare.
     *
     * @return una stringa che descrive il tipo di trigger, in questo caso "day of the week".
     */
    @Override
    public String getType() {
        return "day of the week";
    }
}
