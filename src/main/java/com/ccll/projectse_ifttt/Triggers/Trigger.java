package com.ccll.projectse_ifttt.Triggers;

/**
 * Interfaccia che rappresenta un trigger generico nell'applicazione.
 * Un trigger è un elemento che attiva specifiche azioni in base al verificarsi di determinate condizioni.
 */
public interface Trigger {

    /**
     * Valuta se la condizione del trigger è soddisfatta, cioè se il trigger passa da uno stato disattivato a uno attivato.
     *
     * @return true se la condizione del trigger è soddisfatta, ossia se passa da "false" a "true", false altrimenti.
     */
    boolean evaluate();

    /**
     * Fornisce la valutazione corrente del trigger senza modificare il suo stato.
     *
     * @return true se la condizione del trigger è attualmente soddisfatta, false altrimenti.
     */
    boolean getCurrentEvaluation();

    /**
     * Reimposta lo stato del trigger alle condizioni iniziali.
     */
    void reset();
}
