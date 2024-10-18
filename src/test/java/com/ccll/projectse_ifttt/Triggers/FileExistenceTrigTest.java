package com.ccll.projectse_ifttt.Triggers;

import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.io.File;
import java.io.IOException;

public class FileExistenceTrigTest {

    private static final String EXISTING_FILE_PATH = "C:\\Users\\camil\\Desktop\\prova.txt";
    private static final String NON_EXISTING_FILE_PATH = "C:\\Users\\camil\\Desktop\\non_existent_file.txt";

    private FileExistenceTrig trigger;

    @BeforeEach
    public void setUp() {
        // Assicurati che il file di test esista prima di eseguire i test
        try {
            File existingFile = new File(EXISTING_FILE_PATH);
            if (!existingFile.exists()) {
                existingFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Inizializza il trigger con il percorso del file esistente
        trigger = new FileExistenceTrig(EXISTING_FILE_PATH);
    }

    @Test
    public void testConstructorAndGetFilePath() {
        // Verifica che il costruttore funzioni correttamente
        assertEquals(EXISTING_FILE_PATH, trigger.getFilePath());
    }

    @Test
    public void testSetFilePath() {
        // Modifica il percorso del file
        trigger.setFilePath(NON_EXISTING_FILE_PATH);
        // Verifica che il percorso del file sia stato aggiornato
        assertEquals(NON_EXISTING_FILE_PATH, trigger.getFilePath());
    }

    @Test
    public void testEvaluateFileExists() {
        // Verifica che il trigger ritorni true se il file esiste
        assertTrue(trigger.evaluate());
    }

    @Test
    public void testEvaluateFileDoesNotExist() {
        // Cambia il percorso al file che non esiste
        trigger.setFilePath(NON_EXISTING_FILE_PATH);
        // Verifica che il trigger ritorni false se il file non esiste
        assertFalse(trigger.evaluate());
    }

    @AfterAll
    public static void testCleanUp() {
        // Pulisci il file di test dopo i test
        File file = new File(EXISTING_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }
}
