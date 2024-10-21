package com.ccll.projectse_ifttt.Actions;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.util.logging.Logger;

/**
 * Questa classe rappresenta l'azione di rimuovere un file.
 * Implementa l'interfaccia {@link Action} e fornisce la funzionalità per eseguire
 * la rimozione di un file specificato.
 */
public class RemoveFileAction implements Action {
    private static final Logger logger = Logger.getLogger(RemoveFileAction.class.getName());
    private final String filePath;

    /**
     * Costruttore della classe RemoveFileAction.
     *
     * @param filePath Il percorso del file da rimuovere.
     */
    public RemoveFileAction(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Esegue l'azione di rimozione del file.
     *
     * @return true se il file è stato rimosso con successo, false altrimenti.
     */
    @Override
    public boolean execute() {
        File file = new File(filePath);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                logger.info("File rimosso con successo: " + filePath);
                showAlert("Successo", "File rimosso con successo: " + filePath);
                return true;
            } else {
                logger.warning("Impossibile rimuovere il file: " + filePath);
                showAlert("Errore", "Impossibile rimuovere il file: " + filePath);
                return false;
            }
        } else {
            logger.warning("Il file non esiste: " + filePath);
            showAlert("Errore", "Il file non esiste: " + filePath);
            return false;
        }
    }

    /**
     * Mostra un alert con un titolo e un messaggio specificati.
     *
     * @param title   Il titolo dell'alert.
     * @param message Il messaggio da visualizzare nell'alert.
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
     * @return Una stringa che descrive l'azione.
     */
    @Override
    public String toString() {
        return "Rimozione file";
    }
}