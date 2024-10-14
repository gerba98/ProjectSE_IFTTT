package com.ccll.projectse_ifttt.Actions;

import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.Assert.*;
import org.junit.Test;

public class PlayAudioActionTest {

    @Test
    public void initAction() {
        Path filePath = Paths.get("path/to/audio/file.mp3");
        PlayAudioAction action = new PlayAudioAction(filePath);
        assertNotNull(action);
        assertEquals(filePath, action.getAudioFilePath());
    }

    @Test
    public void createAction() {
        PlayAudioActionCreator creator = new PlayAudioActionCreator(Paths.get("/Users/camillamurati/Desktop/cat.mp3"));
        Action action = creator.createAction();
        assertNotNull(action);
        assertTrue(action instanceof PlayAudioAction);
    }

    @Test
    public void createWithValidPath() {
        Path filePath = Paths.get("/Users/camillamurati/Desktop/cat.mp3");
        PlayAudioActionCreator creator = new PlayAudioActionCreator(filePath);
        Action action = creator.createAction();
        assertNotNull(action);
        assertTrue(action instanceof PlayAudioAction);
        assertEquals(filePath, ((PlayAudioAction) action).getAudioFilePath());
    }

    @Test
    public void createWithNullPath() {
        PlayAudioActionCreator creator = new PlayAudioActionCreator(null);
        Action action = creator.createAction();
        assertNotNull(action);
        assertTrue(action instanceof PlayAudioAction);
    }

    @Test
    public void createWithInvalidPath() {
        PlayAudioActionCreator creator = new PlayAudioActionCreator(Paths.get("invalid/path/file.mp3"));
        Action action = creator.createAction();
        assertNotNull(action);
        assertTrue(action instanceof PlayAudioAction);
    }
}
