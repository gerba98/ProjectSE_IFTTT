package com.ccll.projectse_ifttt.Rule;

import com.ccll.projectse_ifttt.Actions.Action;
import com.ccll.projectse_ifttt.Triggers.Trigger;


/**
 * Questa classe rappresenta una regola IFTTT (If This Then That).
 * Una regola è composta da un gggetto trigger e un oggetto action
 */
public class Rule {
    private Trigger trigger;
    private Action action;
    private String name;
    private boolean state;
    private int numberOfExecutions;

    /**
     * Costruttore della classe Rule.
     * Crea una nuova istanza di Rule dato il trigger, l'azione e il nome specificati.
     * lo stato della regola è inizializzato a true
     * Il numero di esecuzioni della regola è inizializzato a 0
     *
     * @param name    Il nome della regola
     * @param trigger Il trigger della regola
     * @param action  L'azione della regola
     */
    public Rule(String name, Trigger trigger, Action action) {
        this.trigger = trigger;
        this.action = action;
        this.name = name;
        this.state = true;
        this.numberOfExecutions = 0;
    }

    /**
     * Restituisce il trigger associato alla regola
     *
     * @return Il trigger della regola
     */
    public Trigger getTrigger() {
        return trigger;
    }

    /**
     * Imposta il trigger della regola.
     *
     * @param trigger Il nuovo trigger da impostare
     */
    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    /**
     * Restituisce l'azione associata alla regola.
     *
     * @return L'azione della regola
     */

    public Action getAction() {
        return action;
    }

    /**
     * Imposta l'azione della regola.
     *
     * @param action La nuova azione da impostare
     */
    public void setAction(Action action) {
        this.action = action;
    }

    /**
     * Restituisce il nome della regola.
     *
     * @return Il nome della regola
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome della regola.
     *
     * @param name Il nuovo nome da impostare
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Restituisce lo stato della regola.
     *
     * @return True se la regola è attiva, False altrimenti
     */
    public boolean isState() {
        return state;
    }

    /**
     * Imposta lo stato della regola.
     *
     * @param state Il nuovo stato da impostare
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * Restituisce il tipo della regola.
     *
     * @return Il tipo della regola
     */
    public String getType() {
        return "Rule";
    }

    /**
     * Valuta il trigger associato alla regola se lo stato della regola è attivo.
     * Questo metodo viene chiamato nel metodo run della classe CheckRule.
     *
     * @return True se il trigger è attivato, False altrimenti
     */
    public boolean evaluateTrigger() {
        if (state) {
            return trigger.evaluate();
        }
        return false;
    }


    /**
     * Restituisce il numero di volte che l'azione associata alla regola è stata eseguita.
     *
     * @return Il numero di esecuzioni dell'azione
     */
    public int getNumberOfExecutions() {
        return numberOfExecutions;
    }


    /**
     * Esegue l'azione associata alla regola.
     * Questo metodo viene chiamato nel metodo run della classe CheckRule quando il metodo evaluate del Trigger restituisce True.
     */
    public void executeAction() {
        action.execute();
        numberOfExecutions++;
    }

    /**
     * Restituisce una rappresentazione stringa della regola.
     * La stringa contiene informazioni sul trigger e sull'azione.
     *
     * @return Una stringa che descrive la regola
     */
    @Override
    public String toString() {
        return "Rule{" +
                "trigger=" + trigger +
                ", action=" + action +
                '}';
    }
}