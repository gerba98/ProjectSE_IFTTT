package com.ccll.projectse_ifttt.Actions;

/**
 * La classe astratta ActionCreator definisce un'interfaccia per la creazione di azioni.
 * Questa classe utilizza il pattern di design Factory Method, permettendo alle sottoclassi
 * di implementare il metodo createAction per creare specifiche istanze di Action.
 */
public abstract class ActionCreator {
    /**
     * Crea un'istanza di Action.
     *
     * @return un'istanza di Action
     */
    public abstract Action createAction();
}
