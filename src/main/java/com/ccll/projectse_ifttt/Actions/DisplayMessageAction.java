package com.ccll.projectse_ifttt.Actions;

import javafx.scene.control.Alert;

/**
 * Classe che rappresenta un'azione per visualizzare un messaggio in una finestra di dialogo.
 * Questa classe implementa l'interfaccia {Action} e fornisce la funzionalità
 * per mostrare un messaggio all'utente quando l'azione viene eseguita.
 */
public class DisplayMessageAction implements Action {
    private final String message;

    /**
     * Costruttore della classe {@code DisplayMessageAction}.
     *
     * @param message Il messaggio da visualizzare nella finestra di dialogo.
     */
    public DisplayMessageAction(String message) {
        this.message = message;
    }

    /**
     * Esegue l'azione di visualizzazione del messaggio in una finestra di dialogo.
     *
     * @return {@code true} se l'azione è stata eseguita con successo, {@code false} altrimenti.
     */
    @Override
    public boolean execute() {
        // Crea un dialog di avviso per mostrare il messaggio
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notifica");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        return true;
    }

    /**
     * Restituisce una rappresentazione testuale dell'azione.
     *
     * @return Una stringa che descrive l'azione.
     */
    @Override
    public String toString() {
        return "Visualizza messaggio: " + message;
    }
}
