package com.ccll.projectse_ifttt.Triggers;

/**
 * Rappresenta un trigger generico nell'applicazione.
 * Un trigger è un meccanismo che attiva azioni in base a determinate condizioni.
 */
public interface Trigger {

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * Questo metodo controlla se il trigger passa da uno stato disattivato a uno attivato.
     *
     * @return true se la condizione del trigger è soddisfatta (passando da "false" a "true"),
     *         false altrimenti.
     */
    boolean evaluate();
    /**
     * Valuta se la condizione del trigger è soddisfatta.
     *
     * @return true se la condizione del trigger è soddisfatta, false altrimenti.
     */
    boolean getCurrentEvaluation();
    void reset();
}
