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
 * La classe {@code WriteStringAction} implementa l'interfaccia {@link Action}
 * e rappresenta un'azione per scrivere una stringa alla fine di un file di testo.
 * Questa azione verifica se il file specificato esiste prima di tentare di
 * scrivere nel file. Se il file non esiste, l'azione restituisce {@code false}.
 */
public class WriteStringAction implements Action {

    private final String filePath;
    private final String contentToWrite;

    /**
     * Crea un'istanza di {@code WriteStringAction} con il percorso del file e
     * il contenuto da scrivere.
     *
     * @param filePath       il percorso del file in cui scrivere
     * @param contentToWrite il contenuto da scrivere nel file
     */
    public WriteStringAction(String filePath, String contentToWrite) {
        this.filePath = filePath;
        this.contentToWrite = contentToWrite;
    }

    /**
     * Esegue l'azione di scrittura nel file.
     * Se il file specificato non esiste, l'azione non verrà eseguita e
     * verrà restituito {@code false}. In caso di errore durante la scrittura,
     * viene stampato un messaggio di errore.
     *
     * @return {@code true} se la scrittura è avvenuta con successo,
     *         {@code false} altrimenti
     */
    @Override
    public boolean execute() {
        Path path = Paths.get(filePath);

        // Verifica se il file esiste
        if (!Files.exists(path)) {
            return false; // Se il file non esiste, restituisci false
        }

        // Scrivi nel file
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            writer.write(contentToWrite);
            writer.newLine();

            // Mostra l'alert di successo
            showSuccessAlert();
            return true;

        } catch (IOException e) {
            System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
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

    /**
     * Restituisce una rappresentazione in formato stringa di questa azione.
     *
     * @return una stringa che descrive l'azione come "Scrittura su file"
     */
    @Override
    public String toString() {
        return "write string;"+filePath+"-"+contentToWrite;
    }
}
