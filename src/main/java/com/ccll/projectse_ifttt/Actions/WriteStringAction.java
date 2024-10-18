package com.ccll.projectse_ifttt.Actions;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * La classe {@code WriteStringAction} implementa l'interfaccia {@code Action}
 * e rappresenta un'azione per scrivere una stringa alla fine di un file di testo.
 * Questa azione controlla se il file specificato esiste prima di tentare di
 * scrivere nel file. Se il file non esiste, l'azione restituisce {@code false}.
 * Una volta eseguita con successo, l'azione non può essere eseguita nuovamente.
 */
public class WriteStringAction implements Action {

    private final String filePath;
    private final String contentToWrite;
    private boolean executed = false; // Flag per tracciare se l'azione è già stata eseguita

    /**
     * Crea un'istanza di {@code WriteStringAction} con il percorso del file e
     * il contenuto da scrivere.
     *
     * @param filePath      il percorso del file in cui scrivere
     * @param contentToWrite il contenuto da scrivere nel file
     */
    public WriteStringAction(String filePath, String contentToWrite) {
        this.filePath = filePath;
        this.contentToWrite = contentToWrite;
    }

    /**
     * Esegue l'azione di scrittura nel file. Se l'azione è stata già eseguita,
     * restituisce {@code false}.
     *
     * Se il file specificato non esiste, l'azione non verrà eseguita e
     * verrà restituito {@code false}. In caso di errore durante la scrittura,
     * viene stampato un messaggio di errore.
     *
     * @return {@code true} se la scrittura è avvenuta con successo,
     *         {@code false} altrimenti
     */
    @Override
    public boolean execute() {
        if (executed) {
            return false;
        }

        try {
            Path path = Paths.get(filePath);

            // Verifica se il file esiste
            if (!Files.exists(path)) {
                System.err.println("File does not exist: " + path.toString());
                return false; // Se il file non esiste, restituisci false
            }

            // Scrivi nel file
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.write(contentToWrite);
                writer.newLine();
                executed = true;

                // Mostra l'alert di successo
                showSuccessAlert();

                return true;
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
            return false;
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
            alert.setContentText("L'operazione è avvenuta con successo!");
            alert.showAndWait();
        });
    }

}
