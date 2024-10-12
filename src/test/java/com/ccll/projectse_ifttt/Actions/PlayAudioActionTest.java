package com.ccll.projectse_ifttt.Actions;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class PlayAudioActionTest {
    private PlayAudioAction playAudioAction;
    private Path validAudioPath;
    private Path invalidAudioPath;

    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        validAudioPath = Paths.get("/Users/camillamurati/Desktop/cat.mp3");
        invalidAudioPath = Paths.get("/Users/camillamurati/Desktop/non-existent-audio.mp3");
    }

    @Test
    void testExecuteWithValidAudio() {
        playAudioAction = new PlayAudioAction(validAudioPath);
        assertTrue(playAudioAction.execute(), "L'esecuzione dovrebbe avere successo con un file audio valido.");
    }

    @Test
    void testExecuteWithInvalidAudio() {
        playAudioAction = new PlayAudioAction(invalidAudioPath);
        assertFalse(playAudioAction.execute(), "L'esecuzione dovrebbe fallire con un file audio non valido.");
    }

    @Test
    void testGetAudioFilePath() {
        playAudioAction = new PlayAudioAction(validAudioPath);
        assertEquals(validAudioPath, playAudioAction.getAudioFilePath(), "Il percorso del file audio dovrebbe corrispondere a quello fornito nel costruttore.");
    }

    @Test
    void testToString() {
        playAudioAction = new PlayAudioAction(validAudioPath);
        String expectedString = "PlayAudioAction: " + validAudioPath;
        assertEquals(expectedString, playAudioAction.toString(), "La rappresentazione in forma di stringa dovrebbe essere corretta.");
    }
}
