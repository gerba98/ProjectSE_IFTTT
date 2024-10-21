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

public class RemoveFileActionTest {
    private Path tempFile;

    static {
        // Inizializza JavaFX Toolkit per gestire gli alert
        new JFXPanel();
    }

    @Before
    public void setUp() throws Exception {
        // Crea un file temporaneo
        tempFile = Files.createTempFile("testFileToRemove", ".txt");
    }

    @After
    public void tearDown() throws Exception {
        // Rimuove il file temporaneo se esiste ancora dopo i test
        Files.deleteIfExists(tempFile);
    }

    @Test
    @DisplayName("Test successo rimozione file esistente")
    public void testRemoveFileActionSuccess() throws Exception {
        ActionCreator creator = new RemoveFileActionCreator();
        Action removeFileAction = creator.createAction(tempFile.toString());

        // Esegue l'azione di rimozione
        assertTrue("The action should succeed", removeFileAction.execute());

        // Verifica che il file sia stato rimosso correttamente
        assertFalse("The file should not exist after removal", Files.exists(tempFile));
    }

    @Test
    @DisplayName("Test fallimento rimozione file inesistente")
    public void testRemoveFileActionFileDoesNotExist() throws Exception {
        // Elimina il file prima del test
        Files.deleteIfExists(tempFile);

        ActionCreator creator = new RemoveFileActionCreator();
        Action removeFileAction = creator.createAction(tempFile.toString());

        // Esegue l'azione di rimozione
        assertFalse("The action should fail if the file does not exist", removeFileAction.execute());
    }

    @Test
    @DisplayName("Test rimozione file eseguita una sola volta")
    public void testRemoveFileActionAlreadyExecuted() throws Exception {
        ActionCreator creator = new RemoveFileActionCreator();
        Action removeFileAction = creator.createAction(tempFile.toString());

        // Prima esecuzione dovrebbe riuscire
        assertTrue("First execution should succeed", removeFileAction.execute());

        // Seconda esecuzione dovrebbe fallire
        assertFalse("Action should not execute more than once", removeFileAction.execute());
    }
}
