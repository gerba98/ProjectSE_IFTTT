package com.ccll.projectse_ifttt.Triggers;

import java.time.LocalDate;
import java.time.MonthDay;

/**
 * Implementa un trigger che si attiva in un giorno specifico del mese.
 * Questo trigger valuta se il giorno e il mese corrente corrispondono al giorno specificato.
 */
public class DayOfTheMonthTrig extends AbstractTrigger {
    private MonthDay dayOfMonth;

    /**
     * Costruisce un trigger basato su un giorno specifico del mese.
     *
     * @param dayOfMonth il giorno del mese per cui questo trigger deve attivarsi.
     */
    public DayOfTheMonthTrig(MonthDay dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    /**
     * Restituisce il giorno del mese per cui questo trigger è configurato ad attivarsi.
     *
     * @return il giorno del mese associato al trigger.
     */
    public MonthDay getDayOfMonth() {
        return dayOfMonth;
    }

    /**
     * Imposta un nuovo giorno del mese per cui questo trigger deve attivarsi.
     *
     * @param dayOfMonth il nuovo giorno del mese per l'attivazione del trigger.
     */
    public void setDayOfMonth(MonthDay dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * Il trigger si attiva se il giorno e il mese corrente corrispondono a quelli specificati.
     *
     * @return true se il giorno e il mese corrente corrispondono al giorno specificato, false altrimenti.
     */
    @Override
    public boolean getCurrentEvaluation() {
        MonthDay today = MonthDay.from(LocalDate.now());
        return dayOfMonth.equals(today);
    }

    /**
     * Fornisce una rappresentazione in stringa di questo trigger.
     *
     * @return una stringa che indica il giorno del mese per l'attivazione del trigger.
     */
    @Override
    public String toString() {
        return "Day of the Month; " + dayOfMonth.getMonthValue() + "-" + dayOfMonth.getDayOfMonth();
    }
}
