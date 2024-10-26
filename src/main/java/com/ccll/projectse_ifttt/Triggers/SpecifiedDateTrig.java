package com.ccll.projectse_ifttt.Triggers;

import java.time.LocalDate;

/**
 * Implementa un trigger che si attiva alla data specificata se la data corrente corrisponde a quella specificato.
 */
public class SpecifiedDateTrig extends AbstractTrigger {
    LocalDate date;

    /**
     * Costruisce un SpecifiedDataTrig con la data specificata.
     *
     * @param date la data specificata per la quale questo trigger deve attivarsi.
     */
    public SpecifiedDateTrig(LocalDate date) {
        this.date = date;
    }

    /**
     * Restituisce la data specificata per questo trigger.
     *
     * @return la data per la quale il trigger deve attivarsi
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Imposta una nuova data per questo trigger.
     *
     * @param date la nuova data per la quale questo trigger deve attivarsi.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * La condizione è soddisfatta se la data corrente è uguale a quella specificato.
     *
     * @return true se la condizione è soddisfatta, false altrimenti
     */
    @Override
    public boolean getCurrentEvaluation() {
        return date.equals(LocalDate.now());
    }

    /**
     * Restituisce una rappresentazione in stringa del trigger.
     *
     * @return una stringa in cui è specificato il trigger type e il trigger value separato da ";"
     */
    @Override
    public String toString() {
        return "date;" + date;
    }

}
