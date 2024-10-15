package com.ccll.projectse_ifttt.Actions;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class PlayAudioActionTest {

    @Test
    @DisplayName("Creazione di PlayAudioAction tramite PlayAudioActionCreator")
    public void createAction() {
        ActionCreator creator = new PlayAudioActionCreator();
        Action action = creator.createAction("/Users/camillamurati/Desktop/cat.mp3");
        assertNotNull(action);
        assertTrue(action instanceof PlayAudioAction);
    }

    @Test
    @DisplayName("Creazione di PlayAudioAction con percorso valido")
    public void createWithValidPath() {
        String stringFilePath = "/Users/camillamurati/Desktop/cat.mp3";
        ActionCreator creator = new PlayAudioActionCreator();
        Action action = creator.createAction(stringFilePath);
        assertNotNull(action);
        assertTrue(action instanceof PlayAudioAction);
        Path filePath = Paths.get(stringFilePath);
        assertEquals(filePath, ((PlayAudioAction) action).getAudioFilePath());
    }

    @Test
    @DisplayName("Creazione di PlayAudioAction con percorso non valido")
    public void createWithInvalidPath() {
        ActionCreator creator = new PlayAudioActionCreator();
        Action action = creator.createAction("invalid/path/file.mp3");
        assertNotNull(action);
        assertTrue(action instanceof PlayAudioAction);
    }
}
