package com.ccll.projectse_ifttt.Actions;

import java.io.File;
import java.nio.file.Path;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Rappresenta un'azione per riprodurre un file audio.
 * Questa classe implementa l'interfaccia Action e fornisce funzionalità
 * per riprodurre un file audio, ripeterlo e consentire all'utente di fermare la riproduzione.
 */
public class PlayAudioAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(PlayAudioAction.class.getName());
    private final Path audioFilePath;
    private MediaPlayer mediaPlayer;

    /**
     * Costruttore per PlayAudioAction.
     * @param audioFilePath Il percorso del file audio da riprodurre.
     */
    public PlayAudioAction(Path audioFilePath) {
        this.audioFilePath = audioFilePath;
    }

    public Path getAudioFilePath() {
        return audioFilePath;
    }

    /**
     * Esegue l'azione per riprodurre il file audio utilizzando MediaPlayer di JavaFX.
     * @return true se il file audio è stato riprodotto correttamente, false altrimenti.
     */
    @Override
    public boolean execute() {
        File file = new File(this.audioFilePath.toString());
        if (!file.exists()) {
            LOGGER.log(Level.WARNING, "Il file audio non esiste.");
            showAlert("Azione Fallita", "Il file audio non esiste.");
            return false;
        }

        try {
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(mediaPlayer.getStartTime()));

            mediaPlayer.play();

            showStopMusicAlert();
            return true;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante la riproduzione del file audio", e);
            showAlert("Errore", "Si è verificato un errore imprevisto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mostra una semplice finestra di avviso.
     * @param title Il titolo dell'avviso.
     * @param message Il messaggio da visualizzare nell'avviso.
     */
    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(message);
            alert.showAndWait();
        });
    }

    /**
     * Mostra un avviso per fermare la musica.
     */
    private void showStopMusicAlert() {
        Alert alert = new Alert(AlertType.NONE);
        alert.setTitle("Stop Sveglia");
        alert.setHeaderText("Vuoi stoppare la sveglia?");
        ButtonType stopButton = new ButtonType("Stop", ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(stopButton);

        alert.setOnHidden(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop(); //
            }
        });

        Platform.runLater(alert::showAndWait);
    }

    /**
     * Restituisce una rappresentazione in stringa di questa PlayAudioAction.
     *
     * @return Una stringa che descrive l'azione e il nome del file audio.
     */
    @Override
    public String toString() {
        return "Riproduci audio: " + audioFilePath.getFileName();
    }
}