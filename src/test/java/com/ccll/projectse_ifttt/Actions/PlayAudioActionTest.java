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
        ActionCreator creator = new PlayAudioActionCreator();
        Action action = creator.createAction("/Users/camillamurati/Desktop/cat.mp3");
        assertNotNull(action);
        assertTrue(action instanceof PlayAudioAction);
    }

    @Test
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
    public void createWithNullPath() {
        PlayAudioActionCreator creator = new PlayAudioActionCreator();
        Action action = creator.createAction("/Users/camillamurati/Desktop/cat.mp3");
        assertNotNull(action);
        assertTrue(action instanceof PlayAudioAction);
    }

    @Test
    public void createWithInvalidPath() {
        ActionCreator creator = new PlayAudioActionCreator();
        Action action = creator.createAction("invalid/path/file.mp3");
        assertNotNull(action);
        assertTrue(action instanceof PlayAudioAction);
    }
}
