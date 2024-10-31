package com.ccll.projectse_ifttt.Triggers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class FileExistenceTrigTest {
    private File testFile;
    private FileExistenceTrig trigger;

    @BeforeEach
    void setUp() throws IOException {
        // Crea un file di test temporaneo
        testFile = Files.createTempFile("testFile", ".txt").toFile();
        trigger = new FileExistenceTrig(testFile);
    }

    @AfterEach
    void tearDown() {
        // Elimina il file di test
        if (testFile != null && testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testGetCurrentEvaluation_FileExists() {
        // Verifica che il trigger riconosca che il file esiste
        assertTrue(trigger.getCurrentEvaluation(), "Il file dovrebbe esistere");
    }

    @Test
    void testToString() {
        // Verifica la rappresentazione stringa del trigger
        String expected = "File existence;" + testFile.getParent() + "-" + testFile.getName();
        assertEquals(expected, trigger.toString(), "La rappresentazione stringa del trigger non è corretta");
    }

    @Test
    void testSetFile() {
        // Modifica il file e verifica la nuova impostazione
        File newFile = new File("nonexistent.txt");
        trigger.setFile(newFile);
        assertEquals(newFile, trigger.getFile(), "Il file impostato non è corretto");
        assertFalse(trigger.getCurrentEvaluation(), "Il file non dovrebbe esistere");
    }
}