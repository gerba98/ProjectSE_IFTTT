package com.ccll.projectse_ifttt.Actions;

/**
 * Factory per la creazione di azioni di copia file.
 * Questa classe estende ActionCreator e fornisce un'implementazione per
 * creare azioni di copia file.
 */
public class CopyFileActionCreator extends ActionCreator {

    /**
     * Crea un'azione di copia file basata su una stringa di valori.
     *
     * @param actionValue La stringa contenente i percorsi del file sorgente
     *                    e della directory di destinazione, separati da un punto e virgola.
     * @return Un'istanza di CopyFileAction creata con i percorsi forniti.
     */
    @Override
    public Action createAction(String actionValue) {
        String[] parts = actionValue.split(";"); // Divide la stringa in base al separatore ";"
        return new CopyFileAction(parts[0], parts[1]); // Crea e restituisce un'istanza di CopyFileAction
    }

    /**
     * Restituisce il tipo di azione supportata da questo creator.
     *
     * @return Una stringa che rappresenta il tipo di azione, in questo caso "Copy File".
     */
    @Override
    public String getType() {
        return "Copy File"; // Restituisce il tipo di azione
    }
}
