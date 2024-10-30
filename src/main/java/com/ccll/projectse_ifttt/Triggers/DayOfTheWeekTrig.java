package com.ccll.projectse_ifttt.Triggers;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Implementa un trigger che si attiva in un giorno specificato della settimana.
 * Questo trigger valuta se il giorno della settimana corrente corrisponde al giorno specificato.
 */
public class DayOfTheWeekTrig extends AbstractTrigger {
    private DayOfWeek dayOfWeek;

    /**
     * Costruisce un trigger basato su un giorno specifico della settimana.
     *
     * @param dayOfWeek il giorno della settimana per cui questo trigger deve attivarsi.
     */
    public DayOfTheWeekTrig(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Restituisce il giorno della settimana per cui questo trigger è configurato ad attivarsi.
     *
     * @return il giorno della settimana associato al trigger.
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Imposta un nuovo giorno della settimana per cui questo trigger deve attivarsi.
     *
     * @param dayOfWeek il nuovo giorno della settimana per l'attivazione del trigger.
     */
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * Il trigger si attiva se il giorno della settimana corrente corrisponde a quello specificato.
     *
     * @return true se il giorno corrente corrisponde al giorno specificato, false altrimenti.
     */
    @Override
    public boolean getCurrentEvaluation() {
        return LocalDate.now().getDayOfWeek().equals(dayOfWeek);
    }

    /**
     * Fornisce una rappresentazione in stringa di questo trigger.
     *
     * @return una stringa che indica il giorno della settimana per l'attivazione del trigger.
     */
    @Override
    public String toString() {
        return "Day of the week;" + dayOfWeek;
    }
}
