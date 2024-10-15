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
 * Questa classe rappresenta un'azione per riprodurre un file audio. Implementa l'interfaccia {@link Action}
 * ed è responsabile della riproduzione del file audio specificato e della gestione delle operazioni correlate,
 * come l'interruzione della riproduzione tramite un prompt per l'utente.
 */
public class PlayAudioAction implements Action {

    /** Logger utilizzato per registrare errori e avvisi durante la riproduzione audio. */
    private static final Logger LOGGER = Logger.getLogger(PlayAudioAction.class.getName());

    /** Il percorso del file audio che verrà riprodotto */
    private final Path audioFilePath;

    /** Il MediaPlayer utilizzato per riprodurre l'audio */
    private MediaPlayer mediaPlayer;

    /** Indica se l'audio è attualmente in riproduzione */
    private boolean isPlaying = false;

    /** Indica se la riproduzione è stata interrotta */
    private boolean isStopped = false;

    /**
     * Costruttore che inizializza l'azione con il percorso del file audio da riprodurre.
     *
     * @param audioFilePath Il percorso del file audio.
     */
    public PlayAudioAction(Path audioFilePath) {
        this.audioFilePath = audioFilePath;
    }

    /**
     * Restituisce il percorso del file audio associato a questa azione.
     *
     * @return Il percorso del file audio.
     */
    public Path getAudioFilePath() {
        return audioFilePath;
    }

    /**
     * Esegue l'azione di riproduzione del file audio. Se la riproduzione è già in corso o se l'audio
     * è stato fermato in precedenza, l'azione non verrà eseguita nuovamente.
     *
     * @return true se l'audio viene riprodotto correttamente, false in caso di errore.
     */
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

    /**
     * Mostra un alert che chiede all'utente se desidera fermare la riproduzione della musica.
     * Se l'utente sceglie di fermare la musica, la riproduzione viene interrotta e lo stato viene aggiornato.
     */
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

    /**
     * Mostra un alert di avviso all'utente con un titolo e un messaggio specifici.
     *
     * @param title Il titolo dell'alert.
     * @param message Il messaggio dell'alert.
     */
    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(message);
            alert.showAndWait();
        });
    }

    /**
     * Restituisce una rappresentazione in formato stringa di questa azione.
     *
     * @return Una stringa che descrive l'azione, inclusa il nome del file audio.
     */
    @Override
    public String toString() {
        return "Riproduci audio: " + audioFilePath.getFileName();
    }
}