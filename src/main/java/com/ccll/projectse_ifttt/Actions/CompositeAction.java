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
     * Ogni azione è rappresentata dalla sua stringa, separata da ">>>".
     *
     * @return una stringa che descrive la composizione di azioni
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Composite;");

        for (int i = 0; i < actions.size(); i++) {
            sb.append(getActionStr(i));
            if (i < actions.size() - 1) {
                sb.append(">>>");
            }
        }
        return sb.toString();
    }

    /**
     * Restituisce una rappresentazione in stringa dell'azione situata nella posizione specificata
     * nella lista dei trigger. La stringa risultante varia a seconda che il trigger sia un
     * CompositeAction o una action semplice.
     *
     * @param i L'indice della action nella lista delle action, la cui rappresentazione deve essere ottenuta.
     *          Deve essere un valore valido compreso nell'intervallo della lista dei trigger.
     *
     * @return La rappresentazione in stringa della action:
     *
     *  <ul>
     *      <li>Se la action è di tipo {@code CompositeAction}, la stringa restituita è priva della
     *      parte iniziale contenente "COMPOSITE;".</li>
     *      <li>Se la action è semplice, il delimitatore ";" viene sostituito con "#".</li>
     *  </ul>
     *
     */
    private String getActionStr(int i) {
        Action action = actions.get(i);

        String triggerStr = action.toString();
        if (action instanceof CompositeAction) {
            triggerStr = triggerStr.split(";")[1];
        } else {
            triggerStr = triggerStr.replace(";", "#");
        }
        return triggerStr;
    }
}
