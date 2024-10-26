package com.ccll.projectse_ifttt.Triggers;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Implementa un trigger che si attiva se l'ora corrente corrisponde all'ora specificata.
 */
public class TimeOfTheDayTrig extends AbstractTrigger {

    private LocalTime time;

    /**
     * Costruisce un TimeOfTheDayTrig con il LocalTime specificato.
     *
     * @param time l'orario specifico in cui questo trigger deve attivarsi.
     */
    public TimeOfTheDayTrig(LocalTime time) {
        this.time = time;
    }

    /**
     * Restituisce l'orario specificato per questo trigger
     *
     * @return l'orario per il quale il trigger deve attivarsi
     */
    public LocalTime getTime() {
        return this.time;
    }

    /**
     * Imposta un nuovo orario per questo trigger
     *
     * @param time il nuovo orario per il quale questo trigger deve attivarsi.
     */
    public void setTime(LocalTime time) {
        this.time = time.withSecond(0).withNano(0);
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta
     * La condizione è soddisfatta se l'ora corrente corrisponde all'ora specificata.
     *
     * @return true se la condizione è soddisfatta, false altrimenti
     */
    @Override
    public boolean getCurrentEvaluation() {
        return  time.equals(LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    /**
     * Restituisce una rappresentazione in stringa del trigger.
     *
     * @return una stringa in cui è specificato il trigger type e il trigger value separato da ";"
     */
    @Override
    public String toString() {
        return "Time of the day;" + time;
    }


}