package com.ccll.projectse_ifttt.Actions;

/**
 * Classe creatore per l'azione di spostamento del file.
 * Questa classe estende {@link ActionCreator} e fornisce la logica per creare
 * un'istanza di {@link MoveFileAction} utilizzando il formato dell'azione fornito.
 */
public class MoveFileActionCreator extends ActionCreator {

    /**
     * Crea un'istanza di MoveFileAction a partire da una stringa che contiene i percorsi del file sorgente
     * e della directory di destinazione separati da un punto e virgola.
     *
     * @param actionValue Una stringa contenente il percorso del file sorgente e della directory di destinazione
     *                    nel formato "sourceFilePath;destinationDirectoryPath".
     * @return Un'istanza di MoveFileAction creata con i parametri forniti.
     * @throws IllegalArgumentException Se l'actionValue non è nel formato corretto.
     */
    @Override
    public Action createAction(String actionValue) {
        String[] values = actionValue.split(";");
        if (values.length != 2) {
            throw new IllegalArgumentException("Expected format: sourceFilePath;destinationDirectoryPath");
        }
        return new MoveFileAction(values[0], values[1]);
    }

    /**
     * Restituisce il tipo di azione supportata da questo creatore.
     *
     * @return Una stringa che rappresenta il tipo di azione, in questo caso "Move file".
     */
    @Override
    public String getType() {
        return "Move file";
    }
}
