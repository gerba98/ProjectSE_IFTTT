package com.ccll.projectse_ifttt.Actions;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.File;

/**
 * Questa classe rappresenta l'azione di rimuovere un file.
 * Implementa l'interfaccia {@link Action} e fornisce la funzionalità per eseguire
 * la rimozione di un file specificato.
 */
public class RemoveFileAction implements Action {
    private final String filePath;

    /**
     * Costruttore della classe {@code RemoveFileAction}.
     *
     * @param filePath il percorso del file da rimuovere
     */
    public RemoveFileAction(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Esegue l'azione di rimozione del file. Se il file esiste e viene rimosso con successo,
     * restituisce {@code true}. Se il file non esiste o non può essere rimosso, restituisce {@code false}
     * e mostra un messaggio di errore.
     *
     * @return {@code true} se il file è stato rimosso con successo, {@code false} altrimenti
     */
    @Override
    public boolean execute() {
        File file = new File(filePath);

        // Verifica se il file esiste
        if (file.exists()) {
            boolean deleted = file.delete();

            // Se il file è stato cancellato con successo, restituisci true
            if (deleted) {
                return true;
            } else {
                // Il file non è stato cancellato per qualche motivo
                showAlert("Errore", "Impossibile eliminare il file: " + filePath);
                return false;
            }
        } else {
            // Il file non esiste
            showAlert("Errore", "Il file non esiste: " + filePath);
            return false;
        }
    }

    /**
     * Mostra un alert con un titolo e un messaggio specificati.
     *
     * @param title   il titolo dell'alert
     * @param message il messaggio da visualizzare nell'alert
     */
    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    /**
     * Restituisce una rappresentazione in formato stringa di questa azione.
     *
     * @return una stringa che descrive l'azione come "Rimozione file"
     */
    @Override
    public String toString() {
        return "Rimozione file";
    }
}
