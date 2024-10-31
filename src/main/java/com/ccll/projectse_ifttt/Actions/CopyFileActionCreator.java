package com.ccll.projectse_ifttt.Actions;

/**
 * Factory per la creazione di azioni di copia file.
 * Questa classe estende {@link ActionCreator} e fornisce un'implementazione
 * per creare azioni di copia file basate su stringhe di input.
 */
public class CopyFileActionCreator extends ActionCreator {

    /**
     * Crea un'istanza di {@link CopyFileAction} utilizzando una stringa contenente i percorsi
     * del file sorgente e della directory di destinazione, separati da un trattino.
     *
     * @param actionValue la stringa contenente il percorso del file sorgente e quello della
     *                    directory di destinazione, separati da un trattino
     * @return un'istanza di {@link CopyFileAction} configurata con i percorsi forniti
     * @throws ArrayIndexOutOfBoundsException se la stringa {@code actionValue} non contiene
     *                                        entrambi i percorsi richiesti
     */
    @Override
    public Action createAction(String actionValue) {
        String[] parts = actionValue.split("-"); // Divide la stringa in base al separatore "-"
        return new CopyFileAction(parts[0], parts[1]); // Crea e restituisce un'istanza di CopyFileAction
    }

    /**
     * Restituisce il tipo di azione supportata da questa factory.
     *
     * @return una stringa che rappresenta il tipo di azione, in questo caso "Copy File"
     */
    @Override
    public String getType() {
        return "Copy File"; // Restituisce il tipo di azione
    }
}
