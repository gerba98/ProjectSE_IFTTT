package com.ccll.projectse_ifttt.Actions;

/**
 * Classe responsabile della creazione di azioni di tipo {@code DisplayMessageAction}.
 * Estende {@link ActionCreator} e consente di configurare un'azione per visualizzare un messaggio
 * in una finestra di dialogo al momento dell'esecuzione.
 */
public class DisplayMessageActionCreator extends ActionCreator {

    /**
     * Crea un'istanza di {@link DisplayMessageAction} con il messaggio fornito.
     * Se il messaggio è nullo o vuoto, restituisce un'azione che fallisce immediatamente.
     *
     * @param message il messaggio da visualizzare quando l'azione viene eseguita
     * @return un'istanza di {@link DisplayMessageAction} configurata con il messaggio fornito,
     *         o un'azione che fallisce se il messaggio è nullo o vuoto
     */
    @Override
    public Action createAction(String message) {
        if (message == null || message.trim().isEmpty()) {
            return () -> false; // Ritorna un'azione che fallisce immediatamente
        }
        return new DisplayMessageAction(message);
    }

    /**
     * Restituisce il tipo di azione supportata da questa factory.
     *
     * @return una stringa che rappresenta il tipo di azione, in questo caso "display message"
     */
    @Override
    public String getType() {
        return "display message";
    }
}
