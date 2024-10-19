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

    /**
     * Costruttore della classe Rule.
     * Crea una nuova istanza di Rule dato il trigger, l'azione e il nome specificati.
     *
     * @param trigger Il trigger della regola
     * @param action  L'azione della regola
     * @param name    Il nome della regola
     */
    public Rule(Trigger trigger, Action action, String name) {
        this.trigger = trigger;
        this.action = action;
        this.name = name;
    }

    /**
     * Restituisce il trigger associato alla regola.
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
     * Esegue l'azione associata alla regola.
     * Questo metodo viene chiamato nel metodo run della classe CheckRule quando il metodo evaluate del Trigger restituisce True.
     */
    // this method execute action
    public void executeAction() {
        action.execute();

    }

    /**
     * Restituisce una rappresentazione stringa della regola.
     * La stringa contiene informazioni sul trigger e sull'azione.
     *
     * @return Una stringa che descrive la regola
     */
    // to string composed of trigger and action tostring
    @Override
    public String toString() {
        return "Rule;" + name +
                ";trigger;" + trigger +
                ";action;" + action;
    }
}
