package com.ccll.projectse_ifttt.Actions;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Implementa l'azione di copia di un file.
 * Questa classe consente di copiare un file da un percorso sorgente a una directory di destinazione.
 */
public class CopyFileAction implements Action {
    private String sourcePath;      // Percorso del file sorgente
    private String destinationPath; // Percorso della directory di destinazione
    private boolean alertShown;     // Flag per controllare se l'alert è già stato mostrato

    /**
     * Costruttore per creare un'istanza di CopyFileAction.
     *
     * @param sourcePath      Il percorso del file sorgente.
     * @param destinationPath Il percorso della directory di destinazione.
     */
    public CopyFileAction(String sourcePath, String destinationPath) {
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
        this.alertShown = false; // Inizializza il flag a false
    }

    /**
     * Esegue l'azione di copia del file.
     *
     * @return true se il file è stato copiato con successo, false altrimenti.
     */
    @Override
    public boolean execute() {
        try {
            Files.copy(Paths.get(sourcePath),
                    Paths.get(destinationPath, new File(sourcePath).getName()),
                    StandardCopyOption.REPLACE_EXISTING);

            // Mostra l'alert di successo solo se non è già stato mostrato
            if (!alertShown) {
                showSuccessAlert();
                alertShown = true; // Imposta il flag a true dopo aver mostrato l'alert
            }

            return true; // Copia riuscita
        } catch (IOException e) {
            System.out.println("Error copying file: " + e.getMessage());
            return false; // Errore durante la copia
        }
    }

    /**
     * Mostra un alert per informare l'utente che l'operazione è avvenuta con successo.
     */
    private void showSuccessAlert() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Operazione completata");
            alert.setHeaderText(null);
            alert.setContentText("Il file è stato copiato con successo!");
            alert.showAndWait();
        });
    }
    public String toString() {
        return "Copia di un file";
    }
}
