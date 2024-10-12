package com.ccll.projectse_ifttt.Actions;

/**
 * Classe responsabile della creazione di azioni di tipo {@code DisplayMessageAction}.
 * Questa classe estende {ActionCreator} e consente di configurare un messaggio
 * da visualizzare in un'azione. Il messaggio sarà visualizzato in una finestra di dialogo
 * quando l'azione viene eseguita.
 */
public class DisplayMessageActionCreator extends ActionCreator {
    private final String message;

    /**
     * Costruttore per inizializzare DisplayMessageActionCreator con un messaggio specifico.
     *
     * @param message Il messaggio da visualizzare.
     */
    public DisplayMessageActionCreator(String message) {
        this.message = message;
    }

    /**
     * Crea e restituisce una nuova istanza di DisplayMessageAction con il messaggio configurato.
     *
     * @return Un'istanza di {@code DisplayMessageAction}.
     */
    @Override
    public Action createAction() {
        return new DisplayMessageAction(this.message);
    }
}