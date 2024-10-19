package com.ccll.projectse_ifttt.Actions;

import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.nio.file.*;

import static org.junit.Assert.*;

public class WriteStringActionTest {
    private Path tempFile;
    private String contentToWrite = "Test content";

    static {
        // Inizializza JavaFX Toolkit per gestire gli alert
        new JFXPanel();
    }

    @Before
    public void setUp() throws Exception {
        // Crea un file temporaneo
        tempFile = Files.createTempFile("testFile", ".txt");
    }

    @After
    public void tearDown() throws Exception {
        // Rimuove il file temporaneo
        Files.deleteIfExists(tempFile);
    }

    @Test
    @DisplayName("Test scrittura stringa in file esistente con successo")
    public void testWriteStringActionSuccess() throws Exception {
        ActionCreator creator = new WriteStringActionCreator();
        Action writeStringAction = creator.createAction(tempFile.toString() + ";" + contentToWrite);

        // Esegue l'azione di scrittura
        assertTrue("The action should succeed", writeStringAction.execute());

        // Verifica che il contenuto sia stato scritto correttamente
        String writtenContent = Files.readString(tempFile);
        assertTrue("The content should match", writtenContent.contains(contentToWrite));
    }

    @Test
    @DisplayName("Test fallimento scrittura stringa in file inesistente")
    public void testWriteStringActionFileDoesNotExist() throws Exception {
        // Elimina il file prima del test
        Files.deleteIfExists(tempFile);

        ActionCreator creator = new WriteStringActionCreator();
        Action writeStringAction = creator.createAction(tempFile.toString() + ";" + contentToWrite);

        // Esegue l'azione di scrittura
        assertFalse("The action should fail if the file does not exist", writeStringAction.execute());
    }

    @Test
    @DisplayName("Test scrittura stringa non ripetuta dopo esecuzione iniziale")
    public void testWriteStringActionAlreadyExecuted() throws Exception {
        ActionCreator creator = new WriteStringActionCreator();
        Action writeStringAction = creator.createAction(tempFile.toString() + ";" + contentToWrite);

        // Prima esecuzione dovrebbe riuscire
        assertTrue("First execution should succeed", writeStringAction.execute());

        // Seconda esecuzione dovrebbe fallire
        assertFalse("Action should not execute more than once", writeStringAction.execute());
    }
}
