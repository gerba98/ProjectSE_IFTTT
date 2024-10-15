package com.ccll.projectse_ifttt.Actions;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class DisplayMessageAction implements Action {
    private String message;
    private boolean hasExecuted; // Flag per tracciare se l'azione è stata già eseguita

    /**
     * Costruttore della classe {@code DisplayMessageAction}.
     *
     * @param message Il messaggio da visualizzare nella finestra di dialogo.
     */
    public DisplayMessageAction(String message) {
        this.message = message;
        this.hasExecuted = false; // All'inizio l'azione non è stata eseguita
    }

    /**
     * Esegue l'azione di visualizzazione del messaggio in una finestra di dialogo.
     * L'azione viene eseguita solo una volta finché non viene reimpostata.
     *
     * @return {@code true} se l'azione è stata eseguita con successo, {@code false} altrimenti.
     */
    @Override
    public boolean execute() {
        // Se l'azione è già stata eseguita, non la eseguiamo di nuovo
        if (!hasExecuted) {
            Platform.runLater(() -> {
                // Crea un dialog di avviso per mostrare il messaggio
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notifica");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
            });
            hasExecuted = true; // Imposta il flag per indicare che l'azione è stata eseguita
            return true;
        }
        return false; // Restituisce false se l'azione è già stata eseguita
    }

    /**
     * Reimposta lo stato dell'azione permettendole di essere eseguita nuovamente.
     */
    public void reset() {
        hasExecuted = false;
    }

    @Override
    public String toString() {
        return "Visualizza messaggio: " + message;
    }
}