package com.ccll.projectse_ifttt.Triggers;

/**
 * Rappresenta un trigger generico nell'applicazione.
 * Un trigger è un meccanismo che attiva azioni in base a determinate condizioni.
 */
public interface Trigger {

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     *
     * @return true se la condizione del trigger è soddisfatta, false altrimenti.
     */
    boolean evaluate();
}
