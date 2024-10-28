package com.ccll.projectse_ifttt.Actions;

import com.ccll.projectse_ifttt.Rule.RuleManager;

/**
 * La classe CompositeActionCreator è una fabbrica per la creazione di oggetti {@link CompositeAction}.
 * Implementa il metodo {@code createAction} per istanziare e configurare un'azione composta
 * utilizzando una stringa formattata con tipo e valore delle azioni individuali.
 */
public class CompositeActionCreator extends ActionCreator {

    /**
     * Crea un'istanza di {@link CompositeAction} utilizzando una stringa di valori per configurare
     * ogni azione componente. La stringa deve essere formattata come "tipo:valore", con coppie
     * di tipo e valore per ogni azione da aggiungere.
     *
     * @param actionsValue una stringa formattata contenente coppie di tipo e valore per ogni azione
     * @return un'istanza di CompositeAction contenente tutte le azioni specificate
     * @throws ArrayIndexOutOfBoundsException se la stringa {@code actionsValue} non è formattata correttamente
     */
    @Override
    public Action createAction(String actionsValue) {
        String[] parts = actionsValue.split(";");
        CompositeAction compositeAction = new CompositeAction();

        // Per ogni coppia tipo-valore, creiamo e aggiungiamo l'action
        for (int i = 0; i < parts.length; i += 2) {
            String actionType = parts[i];
            String actionValue = parts[i + 1];
            Action action = RuleManager.createAction(actionType, actionValue);
            compositeAction.addAction(action);
        }

        return compositeAction;
    }

    /**
     * Restituisce il tipo di azione che questa classe crea, ovvero "composite".
     *
     * @return una stringa che rappresenta il tipo di azione ("composite")
     */
    @Override
    public String getType() {
        return "composite";
    }
}
