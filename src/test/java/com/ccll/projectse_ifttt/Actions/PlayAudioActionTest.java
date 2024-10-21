package com.ccll.projectse_ifttt.Actions;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayAudioActionTest {

    private Path audioFilePath;  // Il percorso del file audio
    private Action playAudioAction;  // L'azione da testare

    static {
        // Inizializzazione del toolkit JavaFX
        new JFXPanel(); // Questo serve per avviare il toolkit JavaFX
    }

    @Before
    public void setUp() throws Exception {
        // Carica il file audio dalle risorse
        URL audioFileUrl = getClass().getResource("/com/ccll/projectse_ifttt/cat.mp3");
        assertNotNull("Il file audio non è stato trovato nelle risorse", audioFileUrl);

        // Ottieni il percorso del file audio
        audioFilePath = Paths.get(audioFileUrl.toURI());

        // Inizializza l'ActionCreator per creare l'azione di riproduzione audio
        ActionCreator creator = new PlayAudioActionCreator();
        playAudioAction = creator.createAction(audioFilePath.toString());
    }


    @After
    public void tearDown() throws Exception {
        // Non è necessario eliminare il file audio poiché si trova nelle risorse del progetto
    }

    @Test
    @DisplayName("Test successo riproduzione file audio esistente")
    public void testPlayAudioActionSuccess() {
        // Verifica che il file audio esista
        assertTrue("Il file audio dovrebbe esistere", Files.exists(audioFilePath));

        // Esegui l'azione nel thread JavaFX
        Platform.runLater(() -> {
            // Esegue l'azione di riproduzione
            boolean result = playAudioAction.execute();

            // Verifica che l'azione sia stata eseguita con successo
            assertTrue("L'azione dovrebbe essere eseguita con successo", result);
        });
    }

    @Test
    @DisplayName("Test fallimento riproduzione file audio mancante")
    public void testPlayAudioActionFileDoesNotExist() throws Exception {
        // Simula un file audio mancante impostando un percorso non valido
        Path missingAudioFile = Paths.get("src/main/resources/com/ccll/projectse_ifttt/missing_audio.mp3");

        // Inizializza una nuova azione con un file inesistente
        ActionCreator creator = new PlayAudioActionCreator();
        Action playAudioActionMissing = creator.createAction(missingAudioFile.toString());

        // Esegui l'azione nel thread JavaFX
        Platform.runLater(() -> {
            // Esegue l'azione di riproduzione
            boolean result = playAudioActionMissing.execute();

            // Verifica che l'azione fallisca poiché il file non esiste
            assertFalse("L'azione dovrebbe fallire perché il file non esiste", result);
        });
    }
}
