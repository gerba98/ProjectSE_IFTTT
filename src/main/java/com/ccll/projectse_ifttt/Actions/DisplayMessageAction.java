package com.ccll.projectse_ifttt.Actions;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * Questa classe rappresenta un'azione per visualizzare un messaggio in una finestra di dialogo.
 * Implementa l'interfaccia {@link Action} e consente di eseguire l'azione ogni volta che viene chiamata,
 * mostrando il messaggio sia in console sia in una finestra di dialogo JavaFX.
 */
public class DisplayMessageAction implements Action {

    /**
     * Il messaggio da visualizzare nella finestra di dialogo.
     */
    private final String message;

    /**
     * Costruttore della classe {@code DisplayMessageAction}.
     *
     * @param message il messaggio da visualizzare nella finestra di dialogo
     */
    public DisplayMessageAction(String message) {
        this.message = message;
    }

    /**
     * Esegue l'azione di visualizzazione del messaggio in una finestra di dialogo.
     * Se il messaggio è nullo o vuoto, l'azione fallisce restituendo {@code false}.
     * Altrimenti, il messaggio viene stampato in console e visualizzato in un alert di tipo informativo.
     *
     * @return {@code true} se l'azione è stata eseguita con successo, {@code false} se il messaggio è nullo o vuoto
     */
    @Override
    public boolean execute() {
        if (message == null || message.trim().isEmpty()) {
            // Fallisce se il messaggio è nullo o vuoto
            return false;
        }

        // Visualizzazione del messaggio in una finestra di dialogo informativa con JavaFX
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notifica");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });

        return true; // L'azione è eseguita con successo
    }

    /**
     * Restituisce una rappresentazione testuale di questa azione, inclusa il messaggio da visualizzare.
     *
     * @return una stringa che descrive l'azione, inclusa il messaggio da visualizzare
     */
    @Override
    public String toString() {
        return "Display Message;" + message;
    }
}
