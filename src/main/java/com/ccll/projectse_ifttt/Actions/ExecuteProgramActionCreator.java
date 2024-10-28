package com.ccll.projectse_ifttt.Actions;

/**
 * La classe ExecuteProgramActionCreator è responsabile della creazione di
 * istanze di {@link ExecuteProgramAction}. Estende {@link ActionCreator}
 * e utilizza il pattern di design Factory Method per generare azioni
 * basate su valori forniti.
 */
public class ExecuteProgramActionCreator extends ActionCreator {

    /**
     * Crea un'istanza di {@link ExecuteProgramAction} basata sul valore dell'azione fornito.
     * Il valore dell'azione deve contenere il percorso del programma e i comandi, separati da un punto e virgola,
     * nel formato "programPath;command".
     *
     * @param actionValue una stringa contenente il percorso del programma e i comandi
     *                    separati da un punto e virgola, nel formato "programPath;command"
     * @return un'istanza di {@link ExecuteProgramAction} configurata con il percorso e i comandi forniti
     * @throws IllegalArgumentException se il valore dell'azione non è valido
     *                                  (non contiene esattamente due parti)
     */
    @Override
    public Action createAction(String actionValue) {
        String[] parts = actionValue.split(";", 2); // Dividi il valore in due parti
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid action value. Expected format: programPath;command");
        }
        String programPath = parts[0];
        String command = parts[1];
        return new ExecuteProgramAction(programPath, command);
    }

    /**
     * Restituisce il tipo di azione supportata da questo creatore.
     *
     * @return una stringa che rappresenta il tipo di azione, in questo caso "Execute Program"
     */
    @Override
    public String getType() {
        return "Execute Program";
    }
}
