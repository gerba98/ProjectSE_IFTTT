package com.ccll.projectse_ifttt.Triggers;

/**
 * Classe astratta che rappresenta un creatore di trigger.
 * Le sottoclassi devono fornire un'implementazione per il metodo {createTrigger()}
 * per creare istanze di trigger specifici.
 */
public abstract class TriggerCreator {

    /**
     * Crea un nuovo trigger.
     *
     * @return un'istanza di {Trigger} creata.
     */
    public abstract Trigger createTrigger(String triggerValue);
    public abstract String getType();
}