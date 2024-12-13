package com.ccll.projectse_ifttt.Actions;

/**
 * Questa classe crea istanze di {@link RemoveFileAction}.
 * Estende {@link ActionCreator} e fornisce la logica necessaria per
 * creare un'istanza di {@link RemoveFileAction} utilizzando il percorso del file da rimuovere.
 */
public class RemoveFileActionCreator extends ActionCreator {

    /**
     * Crea un'istanza di {@link RemoveFileAction} a partire da un valore che rappresenta
     * il percorso del file da rimuovere.
     *
     * @param actionValue una stringa che rappresenta il percorso del file da rimuovere
     * @return un'istanza di {@link RemoveFileAction} creata con il percorso fornito
     */
    @Override
    public Action createAction(String actionValue) {
        // Il valore dell'azione deve contenere solo il percorso del file da rimuovere
        return new RemoveFileAction(actionValue);
    }

    /**
     * Restituisce il tipo di azione supportata da questo creatore.
     *
     * @return una stringa che rappresenta il tipo di azione, in questo caso "remove file"
     */
    @Override
    public String getType() {
        return "remove file";
    }
}
