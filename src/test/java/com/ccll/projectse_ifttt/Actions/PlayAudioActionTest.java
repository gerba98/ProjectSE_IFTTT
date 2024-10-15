package com.ccll.projectse_ifttt.Actions;

import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class PlayAudioActionTest {

    @Test
    @DisplayName("Creazione di PlayAudioAction tramite PlayAudioActionCreator")
    public void createAction() {
        PlayAudioActionCreator creator = new PlayAudioActionCreator(Paths.get("src/main/resources/com/ccll/projectse_ifttt/cat.mp3"));
        Action action = creator.createAction();
        assertNotNull(action);
        assertTrue(action instanceof PlayAudioAction);
    }

    @Test
    @DisplayName("Creazione di PlayAudioAction con percorso valido")
    public void createWithValidPath() {
        Path filePath = Paths.get("src/main/resources/com/ccll/projectse_ifttt/cat.mp3");
        PlayAudioActionCreator creator = new PlayAudioActionCreator(filePath);
        Action action = creator.createAction();
        assertNotNull(action);
        assertTrue(action instanceof PlayAudioAction);
        assertEquals(filePath, ((PlayAudioAction) action).getAudioFilePath());
    }

    @Test
    @DisplayName("Creazione di PlayAudioAction con percorso non valido")
    public void createWithInvalidPath() {
        PlayAudioActionCreator creator = new PlayAudioActionCreator(Paths.get("invalid/path/file.mp3"));
        Action action = creator.createAction();
        assertNotNull(action);
        assertTrue(action instanceof PlayAudioAction);
    }
}
