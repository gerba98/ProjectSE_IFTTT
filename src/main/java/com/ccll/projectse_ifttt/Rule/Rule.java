package com.ccll.projectse_ifttt.Rule;

import com.ccll.projectse_ifttt.Actions.Action;
import com.ccll.projectse_ifttt.Triggers.Trigger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Questa classe rappresenta una regola IFTTT (If This Then That).
 * Una regola è composta da un oggetto trigger e un oggetto action, che definiscono rispettivamente
 * la condizione e l'azione da eseguire al verificarsi della condizione.
 */
public class Rule {
    private Trigger trigger;
    private Action action;
    private StringProperty name;
    private BooleanProperty state;
    private int numberOfExecutions;

    /**
     * Costruttore della classe Rule.
     * Crea una nuova istanza di Rule con il trigger, l'azione e il nome specificati.
     * Lo stato della regola è inizializzato a true, e il numero di esecuzioni è inizializzato a 0.
     *
     * @param name    Il nome della regola.
     * @param trigger Il trigger della regola.
     * @param action  L'azione della regola.
     */
    public Rule(String name, Trigger trigger, Action action) {
        this.trigger = trigger;
        this.action = action;
        this.name = new SimpleStringProperty(name);
        this.state = new SimpleBooleanProperty(true);
        this.numberOfExecutions = 0;
    }

    /**
     * Restituisce il trigger associato alla regola.
     *
     * @return Il trigger della regola.
     */
    public Trigger getTrigger() {
        return trigger;
    }

    /**
     * Imposta un nuovo trigger per la regola.
     *
     * @param trigger Il nuovo trigger da impostare.
     */
    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    /**
     * Restituisce l'azione associata alla regola.
     *
     * @return L'azione della regola.
     */
    public Action getAction() {
        return action;
    }

    /**
     * Imposta una nuova azione per la regola.
     *
     * @param action La nuova azione da impostare.
     */
    public void setAction(Action action) {
        this.action = action;
    }

    /**
     * Restituisce il nome della regola.
     *
     * @return Il nome della regola.
     */
    public String getName() {
        return name.get();
    }

    /**
     * Imposta un nuovo nome per la regola.
     *
     * @param name Il nuovo nome da impostare.
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Restituisce lo stato della regola.
     *
     * @return True se la regola è attiva, False altrimenti.
     */
    public boolean isState() {
        return state.get();
    }

    /**
     * Restituisce la proprietà di stato della regola.
     *
     * @return La proprietà BooleanProperty dello stato.
     */
    public BooleanProperty stateProperty() {
        return state;
    }

    /**
     * Imposta lo stato della regola. Se lo stato è impostato a true, il trigger viene resettato.
     *
     * @param state Il nuovo stato da impostare.
     */
    public void setState(boolean state) {
        if (state) {
            trigger.reset();
        }
        this.state.set(state);
    }

    /**
     * Restituisce il tipo della regola.
     *
     * @return Il tipo della regola.
     */
    public String getType() {
        return "Rule";
    }

    /**
     * Valuta il trigger associato alla regola se lo stato della regola è attivo.
     * Questo metodo è chiamato dal metodo run di {@link CheckRule}.
     *
     * @return True se il trigger è attivato, False altrimenti.
     */
    public boolean evaluateTrigger() {
        if (isState()) {
            return trigger.evaluate();
        }
        return false;
    }

    /**
     * Restituisce il numero di volte in cui l'azione associata alla regola è stata eseguita.
     *
     * @return Il numero di esecuzioni dell'azione.
     */
    public int getNumberOfExecutions() {
        return numberOfExecutions;
    }

    /**
     * Esegue l'azione associata alla regola e incrementa il numero di esecuzioni.
     * Questo metodo è chiamato dal metodo run di {@link CheckRule} quando il metodo evaluate del Trigger restituisce True.
     */
    public void executeAction() {
        action.execute();
        numberOfExecutions++;
    }

    /**
     * Restituisce una rappresentazione in stringa della regola, contenente il nome, il trigger, l'azione,
     * lo stato e il tipo della regola.
     *
     * @return Una stringa che descrive la regola.
     */
    @Override
    public String toString() {
        return getName() + ";" + trigger + ";" + action + ";" + isState() + ";" + getType();
    }
}
