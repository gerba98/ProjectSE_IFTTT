package com.ccll.projectse_ifttt.Actions;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * Questa classe rappresenta un'azione per visualizzare un messaggio in una finestra di dialogo.
 * Implementa l'interfaccia {@link Action} e permette di eseguire l'azione ogni volta che viene chiamata.
 */
public class DisplayMessageAction implements Action {

    /**
     * Il messaggio da visualizzare nella finestra di dialogo.
     */
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
        Platform.runLater(() -> {
            // Crea un dialog di avviso per mostrare il messaggio
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notifica");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
        return true; // Restituisce true poiché l'azione è sempre eseguita
    }

    /**
     * Restituisce una rappresentazione in formato stringa di questa azione.
     *
     * @return Una stringa che descrive l'azione, inclusa il messaggio da visualizzare.
     */
    @Override
    public String toString() {
        return "Visualizza messaggio" + message;
    }
}
