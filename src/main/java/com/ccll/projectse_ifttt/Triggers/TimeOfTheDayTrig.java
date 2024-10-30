package com.ccll.projectse_ifttt.Triggers;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Classe che implementa un trigger basato sull'orario specifico del giorno.
 * Questo trigger si attiva quando l'ora corrente corrisponde all'orario impostato durante la costruzione dell'istanza.
 */
public class TimeOfTheDayTrig extends AbstractTrigger {

    private LocalTime time;

    /**
     * Costruisce un trigger che si attiva all'orario specificato.
     *
     * @param time l'orario specifico in cui questo trigger deve attivarsi, senza secondi né frazioni di secondo.
     */
    public TimeOfTheDayTrig(LocalTime time) {
        this.time = time.withSecond(0).withNano(0);  // Assicura che l'orario sia impostato senza secondi né nanosecondi.
    }

    /**
     * Restituisce l'orario impostato per l'attivazione di questo trigger.
     *
     * @return l'orario per il quale il trigger è configurato ad attivarsi.
     */
    public LocalTime getTime() {
        return this.time;
    }

    /**
     * Imposta un nuovo orario per l'attivazione di questo trigger.
     *
     * @param time il nuovo orario specificato per l'attivazione del trigger.
     */
    public void setTime(LocalTime time) {
        this.time = time.withSecond(0).withNano(0);  // Assicura che l'orario sia impostato senza secondi né nanosecondi.
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * La condizione è considerata soddisfatta se l'ora corrente, troncata ai minuti, corrisponde all'ora impostata.
     *
     * @return true se l'ora corrente corrisponde all'ora impostata, false altrimenti.
     */
    @Override
    public boolean getCurrentEvaluation() {
        return time.equals(LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    /**
     * Fornisce una rappresentazione in stringa di questo trigger.
     *
     * @return una stringa che descrive il tipo di trigger e l'orario impostato, separati da un punto e virgola.
     */
    @Override
    public String toString() {
        return "Time of the day;" + time;
    }
}
