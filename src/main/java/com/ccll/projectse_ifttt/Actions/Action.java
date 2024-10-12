package com.ccll.projectse_ifttt.Actions;

/**
 * Rappresenta un'azione nell'applicazione.
 * Un'azione è un'operazione che può essere eseguita quando un trigger è attivato.
 */
public interface Action {

    /**
     * Esegue l'azione.
     *
     * @return true se l'azione è stata eseguita con successo, false altrimenti.
     */
    boolean execute();
}

