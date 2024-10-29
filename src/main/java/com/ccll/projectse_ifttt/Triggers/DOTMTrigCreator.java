package com.ccll.projectse_ifttt.Triggers;

import java.time.MonthDay;

/**
 * Implementazione concreta di {@link TriggerCreator} per la creazione di trigger basati su un giorno specifico del mese.
 * Fornisce un metodo per creare istanze di {@link DayOfTheMonthTrig}, che si attivano in un giorno specifico del mese.
 */
public class DOTMTrigCreator extends TriggerCreator {

    /**
     * Crea un nuovo trigger di tipo {@link DayOfTheMonthTrig} che si attiva nel giorno specificato del mese.
     * Il valore del giorno deve essere fornito come stringa nel formato "MM-DD" o "YYYY-MM-DD".
     *
     * @param triggerValue una stringa rappresentante il giorno del mese nel formato "MM-DD" o "YYYY-MM-DD".
     * @return un nuovo oggetto {@link Trigger} che si attiva al giorno del mese specificato.
     */
    @Override
    public Trigger createTrigger(String triggerValue) {
        String[] list = triggerValue.split("-");
        MonthDay dayOfMonth;

        if (list.length == 2) {
            dayOfMonth = MonthDay.of(Integer.parseInt(list[0]), Integer.parseInt(list[1]));
        } else {
            dayOfMonth = MonthDay.of(Integer.parseInt(list[1]), Integer.parseInt(list[2]));
        }

        return new DayOfTheMonthTrig(dayOfMonth);
    }

    /**
     * Fornisce il tipo di trigger che questa factory è specializzata a creare.
     *
     * @return una stringa che descrive il tipo di trigger, in questo caso "day of the month".
     */
    @Override
    public String getType() {
        return "day of the month";
    }
}
