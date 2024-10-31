package com.ccll.projectse_ifttt.Triggers;

import java.time.LocalDate;

/**
 * Classe che implementa un trigger basato su una data specifica.
 * Questo trigger si attiva quando la data corrente corrisponde alla data impostata durante la costruzione dell'istanza.
 */
public class SpecifiedDateTrig extends AbstractTrigger {
    private LocalDate date;

    /**
     * Costruisce un trigger che si attiva alla data specificata.
     *
     * @param date la data specificata per la quale questo trigger deve attivarsi.
     */
    public SpecifiedDateTrig(LocalDate date) {
        this.date = date;
    }

    /**
     * Restituisce la data impostata per l'attivazione di questo trigger.
     *
     * @return la data per la quale il trigger è configurato ad attivarsi.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Imposta una nuova data per l'attivazione di questo trigger.
     *
     * @param date la nuova data specificata per l'attivazione del trigger.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * La condizione è considerata soddisfatta se la data corrente è uguale alla data impostata.
     *
     * @return true se la data corrente corrisponde alla data impostata, false altrimenti.
     */
    @Override
    public boolean getCurrentEvaluation() {
        return date.equals(LocalDate.now());
    }

    /**
     * Fornisce una rappresentazione in stringa di questo trigger.
     *
     * @return una stringa che descrive il tipo di trigger e la data impostata, separati da un punto e virgola.
     */
    @Override
    public String toString() {
        return "Date;" + date;
    }
}
