package com.ccll.projectse_ifttt.Triggers;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Rappresenta un trigger che si attiva al giorno specificato della settimana.
 * Questo trigger valuta se il giorno della settimana corrente corrisponde o supera il giorno specificato.
 */
public class DayOfTheWeekTrig implements Trigger {
    boolean lastEvaluation = false;
    DayOfWeek dayOfWeek;

    /**
     * Costruisce un DayOfTheWeekTrig con l'ora specificata.
     *
     * @param dayOfWeek il giorno della settimana specificato per cui questo trigger deve attivarsi.
     */
    public DayOfTheWeekTrig(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Restituisce il giorno della settimana specificato per questo trigger.
     *
     * @return il giorno della settimana alla quale questo trigger si attiva.
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Imposta un nuovo giorno della settimana specificato per questo trigger.
     *
     * @param dayOfWeek il nuovo giorno della settimana alla quale questo trigger deve attivarsi.
     */
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * Questo trigger si attiva se il giorno della settimana  corrente è uguale a quello specificato.
     *
     * @return true se il trigger è attivo, false altrimenti.
     */
    @Override
    public boolean evaluate() {
        boolean evaluation = false;
        DayOfWeek today = LocalDate.now().getDayOfWeek();

        boolean newEvaluation = today.equals(dayOfWeek);

        if (!lastEvaluation && newEvaluation) {
            evaluation = true;
        }

        lastEvaluation = newEvaluation;
        return evaluation;

    }

    /**
     * Restituisce una rappresentazione stringa del trigger.
     *
     * @return una stringa che indica il giorno della settimana alla quale il trigger si attiva.
     */
    @Override
    public String toString() {
        return "Trigger attivato a: " + dayOfWeek;
    }

    @Override
    public void reset() {
        lastEvaluation = false;
    }
}
