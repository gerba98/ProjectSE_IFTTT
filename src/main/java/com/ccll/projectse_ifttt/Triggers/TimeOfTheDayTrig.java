package com.ccll.projectse_ifttt.Triggers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Rappresenta un trigger che si attiva in un orario specifico del giorno.
 * Questo trigger valuta se l'ora corrente corrisponde all'ora specificata.
 */
public class TimeOfTheDayTrig implements Trigger {
    private LocalTime time;
    private boolean evaluation = false;  // Memorizza il risultato dell'ultima valutazione del trigger, true se il trigger è stato attivato
    private boolean changed = false;     // Tiene traccia se il risultato della valutazione è cambiato

    /**
     * Costruttore per inizializzare TimeOfTheDayTrig con il LocalTime specificato.
     * @param time l'orario specifico in cui questo trigger dovrebbe attivarsi.
     */
    public TimeOfTheDayTrig(LocalTime time) {
        this.time = time;
    }

    /**
     * Getter per ottenere l'orario specifico.
     * @return l'orario in cui si attiva questo trigger.
     */
    public LocalTime getTime() {
        return this.time;
    }

    /**
     * Setter per aggiornare l'orario specifico.
     * @param time il nuovo orario in cui questo trigger dovrebbe attivarsi.
     */
    public void setTime(LocalTime time) {
        this.time = time.withSecond(0).withNano(0);
    }

    /**
     * Valuta se la condizione del trigger è verificata.
     * Questo trigger si attiva se l'ora corrente corrisponde all'ora specificata.
     * @return true se lo stato della valutazione è cambiato, altrimenti false.
     */
    @Override
    public boolean evaluate() {
        // 1. Ottiene l'ora corrente e tronca i secondi e i nanosecondi, considerando solo ore e minuti.
        LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        // 2. Confronta l'orario troncato con l'orario specificato nel trigger.
        boolean newEvaluation = time.equals(now);

        // 3. Valuta se l'orario corrente è uguale all'orario specificato e se l'ultimo stato di valutazione era falso.
        if (newEvaluation && !evaluation) {
            evaluation = true;   // Aggiorna lo stato di valutazione a vero.
            changed = true;      // Imposta changed a vero, indicando che lo stato di valutazione è cambiato.
            return true;         // Ritorna vero, indicando che il trigger è attivato.
        } else if (!newEvaluation) {
            evaluation = false;  // Aggiorna lo stato di valutazione a falso se l'orario corrente non corrisponde.
        }

        // 4. Imposta changed a falso se l'orario corrente non corrisponde all'orario specificato.
        changed = false;
        return false;           // Ritorna falso, indicando che il trigger non è attivato.
    }

    /**
     * Reimposta lo stato della valutazione.
     */
    @Override
    public void reset() {
        evaluation = false;
        changed = false;
    }

    /**
     * Restituisce una rappresentazione in stringa del trigger.
     * @return una stringa che indica quando si attiva il trigger.
     */
    @Override
    public String toString() {
        return "Trigger attivato alle: " + time;
    }
}
