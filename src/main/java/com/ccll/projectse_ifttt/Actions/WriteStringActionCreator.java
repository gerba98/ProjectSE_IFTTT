package com.ccll.projectse_ifttt.Actions;

/**
 * La classe {@code WriteStringActionCreator} estende {@link ActionCreator}
 * e fornisce un modo per creare un'istanza di {@link WriteStringAction}.
 * Questa classe implementa il metodo {@code createAction} per accettare
 * un valore di azione che deve contenere un percorso di file e una stringa
 * da scrivere, separati da un trattino ('-').
 */
public class WriteStringActionCreator extends ActionCreator {

    /**
     * Crea un'azione {@link WriteStringAction} a partire dal valore dell'azione
     * fornito.
     *
     * @param actionValue il valore dell'azione che contiene il percorso del file
     *                    e la stringa da scrivere, separati da '-'
     * @return una nuova istanza di {@link WriteStringAction} con il percorso del file
     *         e la stringa da scrivere
     * @throws IllegalArgumentException se {@code actionValue} non contiene
     *                                  un separatore '-' oppure se il percorso del file
     *                                  o la stringa da scrivere non sono forniti
     */
    @Override
    public Action createAction(String actionValue) {
        // Verifica se actionValue contiene il separatore "-"
        if (actionValue == null || !actionValue.contains("-")) {
            throw new IllegalArgumentException("The action value must contain a file path and a string, separated by '-'");
        }

        // Divide actionValue in due parti, il percorso del file e la stringa da scrivere
        String[] parts = actionValue.split("-", 2); // Assume che il percorso del file e la stringa da scrivere siano separati da "-"

        // Verifica che entrambe le parti (percorso del file e stringa) siano presenti
        if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new IllegalArgumentException("Both the file path and the string to write must be provided.");
        }

        // Crea e ritorna una nuova WriteStringAction con il percorso del file e il testo
        return new WriteStringAction(parts[0], parts[1]);
    }

    /**
     * Restituisce il tipo di azione rappresentato da questo creatore di azioni.
     *
     * @return una stringa che rappresenta il tipo di azione, in questo caso "write string"
     */
    @Override
    public String getType() {
        return "write string";
    }
}
