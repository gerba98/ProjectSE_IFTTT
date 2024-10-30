package com.ccll.projectse_ifttt.Actions;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe CompositeAction rappresenta un'azione composta, che consente di combinare più azioni
 * in una singola esecuzione. Implementa il pattern Composite, permettendo di eseguire una
 * sequenza di azioni in cui ogni azione deve essere eseguita correttamente affinché
 * l'intera esecuzione sia considerata riuscita.
 */
public class CompositeAction implements Action {

    private final List<Action> actions;

    /**
     * Costruttore per creare un'istanza di CompositeAction senza azioni iniziali.
     */
    public CompositeAction() {
        this.actions = new ArrayList<Action>();
    }

    /**
     * Aggiunge un'azione alla lista di azioni da eseguire.
     *
     * @param action l'azione da aggiungere alla composizione
     */
    public void addAction(Action action) {
        actions.add(action);
    }

    /**
     * Rimuove un'azione dalla lista di azioni da eseguire.
     *
     * @param action l'azione da rimuovere dalla composizione
     */
    public void removeAction(Action action) {
        actions.remove(action);
    }

    /**
     * Restituisce la lista di azioni figlie attualmente presenti nella composizione.
     *
     * @return una lista delle azioni figlie
     */
    public List<Action> getChildren() {
        return this.actions;
    }

    /**
     * Esegue tutte le azioni nella composizione. Se una qualsiasi azione non viene eseguita
     * correttamente, l'esecuzione dell'intera composizione fallisce.
     *
     * @return true se tutte le azioni sono state eseguite con successo, false altrimenti
     */
    @Override
    public boolean execute() {
        for (Action action : actions) {
            if (!action.execute()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Restituisce una rappresentazione testuale della composizione di azioni.
     * Ogni azione è rappresentata dalla sua stringa, separata da ":".
     *
     * @return una stringa che descrive la composizione di azioni
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Composite;");

        for (int i = 0; i < actions.size(); i++) {
            sb.append(actions.get(i).toString().replace(';', '#'));
            if (i < actions.size() - 1) {
                sb.append(">>>");
            }
        }
        System.out.println("composite action: " + sb.toString());
        return sb.toString();
    }
}
