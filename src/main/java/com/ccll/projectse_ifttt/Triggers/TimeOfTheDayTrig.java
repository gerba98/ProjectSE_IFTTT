package com.ccll.projectse_ifttt.Triggers;

import java.time.LocalTime;

/**
 * Rappresenta un trigger che si attiva a un'ora specifica del giorno.
 * Questo trigger valuta se l'ora corrente corrisponde o supera l'ora specificata.
 */
public class TimeOfTheDayTrig implements Trigger {
    private LocalTime time;

    /**
     * Costruisce un TimeOfTheDayTrig con l'ora specificata.
     *
     * @param time l'ora specifica per cui questo trigger deve attivarsi.
     */
    public TimeOfTheDayTrig(LocalTime time) {
        this.time = time;
    }

    /**
     * Restituisce l'ora specifica per questo trigger.
     *
     * @return l'ora alla quale questo trigger si attiva.
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Imposta una nuova ora specifica per questo trigger.
     *
     * @param time la nuova ora alla quale questo trigger deve attivarsi.
     */
    public void setTime(LocalTime time) {
        this.time = time.withSecond(0).withNano(0);
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * Questo trigger si attiva se l'ora corrente è uguale all'ora specificata.
     *
     * @return true se il trigger è attivo, false altrimenti.
     */
    @Override
    public boolean evaluate() {
        LocalTime currentTime = LocalTime.now().withSecond(0).withNano(0);
        return currentTime.equals(time);  //
    }

    /**
     * Restituisce una rappresentazione stringa del trigger.
     *
     * @return una stringa che indica l'ora alla quale il trigger si attiva.
     */
    @Override
    public String toString() {
        return "Trigger attivato a: " + time;
    }
}
