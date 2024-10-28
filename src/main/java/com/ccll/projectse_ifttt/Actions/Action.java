package com.ccll.projectse_ifttt.Actions;

/**
 * Rappresenta un'azione che può essere eseguita nell'applicazione come parte di una regola IFTTT.
 * Un'azione definisce un'operazione specifica che viene attivata in risposta a un evento o a un trigger.
 * Le implementazioni di questa interfaccia devono definire la logica specifica per eseguire l'azione.
 */
public interface Action {

    /**
     * Esegue l'azione definita dall'implementazione specifica.
     * Questo metodo dovrebbe contenere la logica necessaria per completare l'azione.
     *
     * @return true se l'azione è stata eseguita correttamente, false in caso di errore o di fallimento.
     */
    boolean execute();
}
