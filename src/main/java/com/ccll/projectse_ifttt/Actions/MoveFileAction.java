package com.ccll.projectse_ifttt.Actions;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Questa classe rappresenta un'azione per spostare un file da una directory di origine a una directory di destinazione.
 * Implementa l'interfaccia {@link Action} e fornisce la funzionalità per eseguire lo spostamento di un file.
 */
public class MoveFileAction implements Action {
    private final String sourceFilePath;
    private final String destinationDirectoryPath;

    /**
     * Costruttore della classe MoveFileAction.
     *
     * @param sourceFilePath           Il percorso del file sorgente da spostare.
     * @param destinationDirectoryPath Il percorso della directory di destinazione.
     */
    public MoveFileAction(String sourceFilePath, String destinationDirectoryPath) {
        this.sourceFilePath = sourceFilePath;
        this.destinationDirectoryPath = destinationDirectoryPath;
    }

    /**
     * Esegue l'azione di spostamento del file.
     *
     * @return true se il file è stato spostato con successo, false altrimenti.
     */
    @Override
    public boolean execute() {
        File sourceFile = new File(sourceFilePath);
        File destinationFile = new File(destinationDirectoryPath, sourceFile.getName());

        if (!sourceFile.exists()) {
            return false; // Il file sorgente non esiste
        }

        try {
            Files.move(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Mostra l'alert di successo
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Operazione Completata");
                alert.setHeaderText("Spostamento completato");
                alert.setContentText("Il file è stato spostato con successo.");
                alert.showAndWait();
            });
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public String toString() {
        return "Move file;"+sourceFilePath+"-"+destinationDirectoryPath;
    }
}
