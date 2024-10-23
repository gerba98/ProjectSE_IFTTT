package com.ccll.projectse_ifttt.Triggers;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Rappresenta un trigger che si attiva in un orario specifico del giorno.
 * Questo trigger valuta se l'ora corrente corrisponde all'ora specificata.
 */
public class TimeOfTheDayTrig implements Trigger {

    private LocalTime time;
    boolean LastEvaluation = false;//

    /**
     * Costruttore per inizializzare TimeOfTheDayTrig con il LocalTime specificato.
     *
     * @param time l'orario specifico in cui questo trigger dovrebbe attivarsi.
     */
    public TimeOfTheDayTrig(LocalTime time) {
        this.time = time;
    }

    /**
     * Getter per ottenere l'orario specifico.
     *
     * @return l'orario in cui si attiva questo trigger.
     */
    public LocalTime getTime() {
        return this.time;
    }

    /**
     * Setter per aggiornare l'orario specifico.
     *
     * @param time il nuovo orario in cui questo trigger dovrebbe attivarsi.
     */
    public void setTime(LocalTime time) {
        this.time = time.withSecond(0).withNano(0);
    }

    /**
     * Valuta se la condizione del trigger è verificata.
     * Questo trigger si attiva se l'ora corrente corrisponde all'ora specificata.
     *
     * @return true se lo stato della valutazione è cambiato, altrimenti false.
     */

    @Override
    public boolean evaluate() {
        boolean evaluation = false;
        LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        boolean newEvaluation = time.equals(now);

        if (newEvaluation && !LastEvaluation) {
            evaluation = true;
        }
        LastEvaluation = newEvaluation;
        return evaluation;
    }

    /**
     * Reimposta lo stato della valutazione.
     */
    @Override
    public void reset() {
        LastEvaluation = false;
    }

    /**
     * Restituisce una rappresentazione in stringa del trigger.
     *
     * @return una stringa che indica quando si attiva il trigger.
     */
    @Override
    public String toString() {
        return "Time of the day;" + time;
    }


}
