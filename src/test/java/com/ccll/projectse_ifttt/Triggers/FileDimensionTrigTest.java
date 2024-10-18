package com.ccll.projectse_ifttt.Triggers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class FileDimensionTrigTest {

    private static final String TEST_FILE_PATH = "C:/Users/camil/Desktop/prova.txt"; // Percorso di test per il file

    @BeforeEach
    void setUp() {
        // Creiamo un file di test prima di ogni esecuzione dei test.
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();  // Se il file esiste già, lo rimuoviamo.
        }
    }

    @Test
    void testEvaluateWithByteUnit() {
        // Creiamo un file di test con una dimensione di 500 byte
        File file = new File(TEST_FILE_PATH);
        try {
            boolean fileCreated = file.createNewFile();
            assertTrue(fileCreated);
            // Scriviamo 500 byte nel file
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(file)) {
                fos.write(new byte[500]);
            }

            // Creiamo il trigger con una dimensione di 500 byte
            FileDimensionTrig trigger = new FileDimensionTrig(500, "B", TEST_FILE_PATH);
            assertTrue(trigger.evaluate(), "Il trigger dovrebbe attivarsi con la dimensione corretta di 500 byte.");
        } catch (Exception e) {
            fail("Errore durante la creazione o la scrittura del file di test.");
        }
    }

    @Test
    void testEvaluateWithKilobyteUnit() {
        // Creiamo un file di test con una dimensione di 1 KB (1024 byte)
        File file = new File(TEST_FILE_PATH);
        try {
            boolean fileCreated = file.createNewFile();
            assertTrue(fileCreated);
            // Scriviamo 1024 byte nel file
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(file)) {
                fos.write(new byte[1024]);
            }

            // Creiamo il trigger con una dimensione di 1 KB
            FileDimensionTrig trigger = new FileDimensionTrig(1, "KB", TEST_FILE_PATH);
            assertTrue(trigger.evaluate(), "Il trigger dovrebbe attivarsi con la dimensione corretta di 1 KB.");
        } catch (Exception e) {
            fail("Errore durante la creazione o la scrittura del file di test.");
        }
    }

    @Test
    void testEvaluateWithMegabyteUnit() {
        // Creiamo un file di test con una dimensione di 1 MB (1048576 byte)
        File file = new File(TEST_FILE_PATH);
        try {
            boolean fileCreated = file.createNewFile();
            assertTrue(fileCreated);
            // Scriviamo 1 MB nel file
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(file)) {
                fos.write(new byte[1048576]);
            }

            // Creiamo il trigger con una dimensione di 1 MB
            FileDimensionTrig trigger = new FileDimensionTrig(1, "MB", TEST_FILE_PATH);
            assertTrue(trigger.evaluate(), "Il trigger dovrebbe attivarsi con la dimensione corretta di 1 MB.");
        } catch (Exception e) {
            fail("Errore durante la creazione o la scrittura del file di test.");
        }
    }

    @Test
    void testEvaluateWithGigabyteUnit() {
        // Creiamo un file di test con una dimensione di 1 GB (1073741824 byte)
        File file = new File(TEST_FILE_PATH);
        try {
            boolean fileCreated = file.createNewFile();
            assertTrue(fileCreated);
            // Scriviamo 1 GB nel file
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(file)) {
                fos.write(new byte[1073741824]);
            }

            // Creiamo il trigger con una dimensione di 1 GB
            FileDimensionTrig trigger = new FileDimensionTrig(1, "GB", TEST_FILE_PATH);
            assertTrue(trigger.evaluate(), "Il trigger dovrebbe attivarsi con la dimensione corretta di 1 GB.");
        } catch (Exception e) {
            fail("Errore durante la creazione o la scrittura del file di test.");
        }
    }

    @Test
    void testEvaluateWithNonExistentFile() {
        // Creiamo un trigger con un file che non esiste
        FileDimensionTrig trigger = new FileDimensionTrig(500, "B", "nonexistentfile.txt");
        assertFalse(trigger.evaluate(), "Il trigger non dovrebbe attivarsi se il file non esiste.");
    }


    @AfterAll
    static void closeTest() {
        // Creiamo un file di test prima di ogni esecuzione dei test.
        File file = new File(TEST_FILE_PATH);
        file.delete();  // Rimuoviamo il file.

    }
}
