package com.ccll.projectse_ifttt.Triggers;

import java.time.LocalDate;

/**
 * Rappresenta un trigger che si attiva alla data specificata.
 * Questo trigger valuta se la data corrente corrisponde a quella specificato.
 */
public class SpecifiedDateTrig implements Trigger {

    boolean lastEvaluation = false;
    LocalDate date;

    /**
     * Costruisce un SpecifiedDataTrig con con la data specificata.
     *
     * @param date la data specificata per cui questo trigger deve attivarsi.
     */
    public SpecifiedDateTrig(LocalDate date) {
        this.date = date;
    }

    /**
     * Restituisce la data specificata per questo trigger.
     *
     * @return la data specificata alla quale questo trigger si attiva.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Imposta una nuova data specificata per questo trigger.
     *
     * @param date la nuova data specifica alla quale questo trigger deve attivarsi.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * Questo trigger si attiva se la data corrente è uguale a quella specificato.
     *
     * @return true se il trigger è attivo, false altrimenti.
     */
    @Override
    public boolean evaluate() {
        boolean evaluation = false;
        LocalDate today = LocalDate.now();

        boolean newEvaluation = date.equals(today);

        if (!lastEvaluation && newEvaluation) {
            evaluation = true;
        }

        lastEvaluation = newEvaluation;
        return evaluation;
    }

    /**
     * Restituisce una rappresentazione stringa del trigger.
     *
     * @return una stringa che indica la data specifica  alla quale il trigger si attiva.
     */
    @Override
    public String toString() {
        return "Trigger attivato a: " + date;
    }

    @Override
    public void reset() {
        lastEvaluation = false;
    }
}
