package com.ccll.projectse_ifttt.Actions;

import java.io.File;
import java.nio.file.Path;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
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
    private boolean isPlaying = false;
    private boolean isStopped = false; // Aggiungi il flag per indicare che la riproduzione è stata fermata

    public PlayAudioAction(Path audioFilePath) {
        this.audioFilePath = audioFilePath;
    }

    public Path getAudioFilePath() {
        return audioFilePath;
    }

    @Override
    public boolean execute() {
        if (isStopped) {
            // Se l'audio è stato fermato, non eseguire l'azione
            return false;
        }

        if (isPlaying) {
            // Se la musica è già in riproduzione, non riprodurre di nuovo e non mostrare un altro alert
            return true;
        }

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
            isPlaying = true;  // Imposta lo stato come 'in riproduzione'

            showStopMusicAlert();  // Mostra l'alert per fermare la musica
            return true;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante la riproduzione del file audio", e);
            showAlert("Errore", "Si è verificato un errore imprevisto: " + e.getMessage());
            return false;
        }
    }

    private void showStopMusicAlert() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Stop Sveglia");
            alert.setHeaderText("Vuoi stoppare la sveglia?");
            ButtonType stopButton = new ButtonType("Stop", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(stopButton);

            alert.setOnHidden(event -> {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    isPlaying = false;  // Imposta lo stato come 'non in riproduzione'
                    isStopped = true;   // Segna che la riproduzione è stata fermata definitivamente
                }
            });

            alert.showAndWait();
        });
    }

    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(message);
            alert.showAndWait();
        });
    }

    @Override
    public String toString() {
        return "Riproduci audio: " + audioFilePath.getFileName();
    }
}