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
 * Implementa un'azione che consente di copiare un file da un percorso sorgente a una directory di destinazione.
 * Questa classe gestisce l'operazione di copia e mostra un messaggio di successo all'utente.
 */
public class CopyFileAction implements Action {
    private final String sourcePath;      // Percorso del file sorgente
    private final String destinationPath; // Percorso della directory di destinazione

    /**
     * Costruttore per creare un'istanza di CopyFileAction.
     *
     * @param sourcePath      il percorso del file sorgente
     * @param destinationPath il percorso della directory di destinazione
     */
    public CopyFileAction(String sourcePath, String destinationPath) {
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
    }

    /**
     * Esegue l'azione di copia del file dal percorso sorgente alla destinazione.
     * Se la copia è completata con successo, viene mostrato un messaggio di notifica.
     *
     * @return true se il file è stato copiato correttamente, false in caso di errore
     */
    @Override
    public boolean execute() {
        try {
            Files.copy(Paths.get(sourcePath),
                    Paths.get(destinationPath, new File(sourcePath).getName()),
                    StandardCopyOption.REPLACE_EXISTING);

            // Mostra l'alert di successo ogni volta che la copia è riuscita
            showSuccessAlert();

            return true; // Copia riuscita
        } catch (IOException e) {
            System.out.println("Errore nella copia del file: " + e.getMessage());
            return false; // Errore durante la copia
        }
    }

    /**
     * Mostra un messaggio di avviso per informare l'utente che l'operazione di copia
     * è stata completata con successo.
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

    /**
     * Restituisce una descrizione testuale dell'azione di copia del file.
     *
     * @return una stringa descrittiva dell'azione
     */
    @Override
    public String toString() {
        return "Copy File;";
    }
}
