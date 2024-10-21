package com.ccll.projectse_ifttt.Actions;

import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayAudioActionTest {

    private Path tempAudioFile;  // Il file audio temporaneo
    private Action playAudioAction;  // L'azione da testare

    static {
        // Inizializzazione del toolkit JavaFX
        new JFXPanel();
    }

    @Before
    public void setUp() throws Exception {
        // Creazione di un file audio temporaneo per il test
        tempAudioFile = Files.createTempFile("testAudio", ".mp3");

        // Assicurati che ci sia un file fittizio che MediaPlayer possa riconoscere
        Files.write(tempAudioFile, "Test Audio File".getBytes());

        // Inizializza l'ActionCreator per creare l'azione di riproduzione audio
        ActionCreator creator = new PlayAudioActionCreator();
        playAudioAction = creator.createAction(tempAudioFile.toString());
    }

    @After
    public void tearDown() throws Exception {
        // Elimina il file audio temporaneo
        Files.deleteIfExists(tempAudioFile);
    }

    @Test
    @DisplayName("Test successo riproduzione file audio esistente")
    public void testPlayAudioActionSuccess() {
        // Verifica che il file audio esista
        assertTrue("Il file audio dovrebbe esistere", Files.exists(tempAudioFile));

        // Esegue l'azione di riproduzione
        boolean result = playAudioAction.execute();

        // Verifica che l'azione sia stata eseguita con successo
        assertTrue("L'azione dovrebbe essere eseguita con successo", result);
    }

    @Test
    @DisplayName("Test fallimento riproduzione file audio mancante")
    public void testPlayAudioActionFileDoesNotExist() throws Exception {
        // Elimina il file audio temporaneo per simulare un file mancante
        Files.deleteIfExists(tempAudioFile);

        // Esegue l'azione di riproduzione
        boolean result = playAudioAction.execute();

        // Verifica che l'azione fallisca poiché il file non esiste
        assertFalse("L'azione dovrebbe fallire perché il file non esiste", result);
    }
}
