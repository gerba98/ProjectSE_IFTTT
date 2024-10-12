package com.ccll.projectse_ifttt.Actions;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaException;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe PlayAudioAction rappresenta un'azione che riproduce un file audio.
 * Implementa l'interfaccia Action per eseguire un'azione di riproduzione audio
 * quando viene attivata.
 */
public class PlayAudioAction implements Action {
    private static final Logger logger = Logger.getLogger(PlayAudioAction.class.getName());
    private Path audioFilePath;

    /**
     * Costruttore della classe PlayAudioAction.
     *
     * @param audioFilePath il percorso del file audio da riprodurre
     */
    public PlayAudioAction(Path audioFilePath) {
        this.audioFilePath = audioFilePath;
    }

    public Path getAudioFilePath() {
        return audioFilePath;
    }

    /**
     * Esegue l'azione di riproduzione audio.
     *
     * @return true se l'azione è stata eseguita con successo, false altrimenti
     */
    @Override
    public boolean execute() {
        try {
            // Usa audioFilePath.toUri().toString() per ottenere l'URI corretto
            Media sound = new Media(audioFilePath.toUri().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
            mediaPlayer.setOnEndOfMedia(mediaPlayer::stop); // Ferma il suono alla fine
            return true; // L'azione è stata eseguita con successo
        } catch (MediaException | IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Errore durante la riproduzione del file audio: " + audioFilePath, e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore imprevisto durante l'esecuzione di PlayAudioAction", e);
        }
        return false; // C'è stato un errore nell'esecuzione
    }

    /**
     * Restituisce una rappresentazione in forma di stringa dell'oggetto PlayAudioAction.
     *
     * @return una stringa che descrive l'azione di riproduzione audio
     */
    @Override
    public String toString() {
        return "PlayAudioAction: " + audioFilePath;
    }
}
