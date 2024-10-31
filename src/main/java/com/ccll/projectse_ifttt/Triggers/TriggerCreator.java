package com.ccll.projectse_ifttt.Triggers;

/**
 * La classe astratta TriggerCreator definisce un'interfaccia per la creazione di oggetti Trigger.
 * Utilizza il pattern di design Factory Method, permettendo alle sottoclassi di implementare
 * il metodo {@code createTrigger} per restituire istanze specifiche di Trigger.
 */
public abstract class TriggerCreator {

    /**
     * Crea un'istanza di Trigger in base ai parametri forniti.
     * Questo metodo è implementato dalle sottoclassi per generare tipi specifici di trigger.
     *
     * @param triggerValue un valore utilizzato per configurare l'istanza di Trigger.
     * @return un'istanza di Trigger configurata in base al parametro {@code triggerValue}.
     */
    public abstract Trigger createTrigger(String triggerValue);

    /**
     * Restituisce il tipo di trigger creato da questa classe.
     * Questo metodo consente di identificare la tipologia di trigger che ogni sottoclasse crea.
     *
     * @return una stringa che rappresenta il tipo di trigger.
     */
    public abstract String getType();
}
