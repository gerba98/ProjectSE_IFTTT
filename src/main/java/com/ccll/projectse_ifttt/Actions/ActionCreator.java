package com.ccll.projectse_ifttt.Actions;

/**
 * La classe astratta ActionCreator definisce un'interfaccia per la creazione di oggetti Action.
 * Utilizza il pattern di design Factory Method, permettendo alle sottoclassi di implementare
 * il metodo {@code createAction} per restituire istanze specifiche di Action.
 */
public abstract class ActionCreator {

    /**
     * Crea un'istanza di Action in base ai parametri forniti.
     * Questo metodo è implementato dalle sottoclassi per generare tipi specifici di azioni.
     *
     * @param actionValue un valore utilizzato per configurare l'istanza di Action.
     * @return un'istanza di Action configurata in base al parametro {@code actionValue}.
     */
    public abstract Action createAction(String actionValue);

    /**
     * Restituisce il tipo di azione creato da questa classe.
     * Questo metodo consente di identificare la tipologia di azione che ogni sottoclasse crea.
     *
     * @return una stringa che rappresenta il tipo di azione.
     */
    public abstract String getType();
}
