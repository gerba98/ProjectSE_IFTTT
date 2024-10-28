package com.ccll.projectse_ifttt.Actions;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe rappresenta un'azione per riprodurre un file audio. Implementa l'interfaccia {@link Action}
 * ed è responsabile della riproduzione del file audio specificato e della gestione delle operazioni correlate,
 * come l'interruzione della riproduzione tramite un prompt per l'utente.
 */
public class PlayAudioAction implements Action {

    /**
     * Logger utilizzato per registrare errori e avvisi durante la riproduzione audio.
     */
    private static final Logger LOGGER = Logger.getLogger(PlayAudioAction.class.getName());

    /**
     * Il percorso del file audio che verrà riprodotto.
     */
    private final Path audioFilePath;

    /**
     * Costruttore che inizializza l'azione con il percorso del file audio da riprodurre.
     *
     * @param audioFilePath il percorso del file audio
     */
    public PlayAudioAction(Path audioFilePath) {
        this.audioFilePath = audioFilePath;
    }

    /**
     * Esegue l'azione di riproduzione del file audio. Se il file non esiste, mostra un avviso
     * all'utente. Se il file esiste, avvia la riproduzione e permette all'utente di interromperla tramite un prompt.
     *
     * @return {@code true} se l'audio viene riprodotto correttamente, {@code false} in caso di errore
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
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(mediaPlayer.getStartTime()));

            mediaPlayer.play();
            showStopMusicAlert(mediaPlayer);  // Mostra l'alert per fermare la musica
            return true;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante la riproduzione del file audio", e);
            showAlert("Errore", "Si è verificato un errore imprevisto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mostra un alert che chiede all'utente se desidera fermare la riproduzione della musica.
     * Se l'utente sceglie di fermare la musica, la riproduzione viene interrotta.
     *
     * @param mediaPlayer il {@link MediaPlayer} da fermare se richiesto
     */
    private void showStopMusicAlert(MediaPlayer mediaPlayer) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.NONE);
            alert.setTitle("Stop Sveglia");
            alert.setHeaderText("Vuoi stoppare la sveglia?");
            ButtonType stopButton = new ButtonType("Stop", ButtonType.OK.getButtonData());
            alert.getButtonTypes().setAll(stopButton);

            alert.showAndWait().ifPresent(response -> {
                if (response == stopButton && mediaPlayer != null) {
                    mediaPlayer.stop();
                }
            });
        });
    }

    /**
     * Mostra un alert di avviso all'utente con un titolo e un messaggio specifici.
     *
     * @param title   il titolo dell'alert
     * @param message il messaggio dell'alert
     */
    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    /**
     * Restituisce una rappresentazione in formato stringa di questa azione.
     *
     * @return una stringa che descrive l'azione, inclusa il nome del file audio
     */
    @Override
    public String toString() {
        return "Riproduci audio: " + audioFilePath.getFileName();
    }
}
