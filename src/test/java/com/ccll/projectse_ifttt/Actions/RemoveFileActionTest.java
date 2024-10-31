package com.ccll.projectse_ifttt.Actions;

import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RemoveFileActionTest {
    private String tempFile;

    static {
        // Inizializza JavaFX Toolkit per gestire gli alert
        new JFXPanel();
    }

    @Before
    public void setUp() throws Exception {
        // Crea un file temporaneo
        tempFile = System.getProperty("java.io.tmpdir") + "prova.txt";
        // Crea il file nel percorso specificato
        Files.createFile(Paths.get(tempFile));
    }

    @After
    public void tearDown() throws Exception {
        // Rimuove il file temporaneo se esiste ancora dopo i test
        Files.deleteIfExists(Paths.get(tempFile));
    }

    @Test
    @DisplayName("Test successo rimozione file esistente")
    public void testRemoveFileActionSuccess() throws Exception {
        ActionCreator creator = new RemoveFileActionCreator();
        Action removeFileAction = creator.createAction(tempFile);

        // Esegue l'azione di rimozione
        assertTrue(removeFileAction.execute());

        // Verifica che il file sia stato rimosso correttamente
        assertFalse(Files.exists(Paths.get(tempFile)));
    }

    @Test
    @DisplayName("Test fallimento rimozione file inesistente")
    public void testRemoveFileActionFileDoesNotExist() throws Exception {
        // Elimina il file prima del test
        Files.deleteIfExists(Paths.get(tempFile));

        ActionCreator creator = new RemoveFileActionCreator();
        Action removeFileAction = creator.createAction(tempFile);

        // Esegue l'azione di rimozione
        assertFalse(removeFileAction.execute());
    }

    @Test
    @DisplayName("Test rimozione file eseguita una sola volta")
    public void testRemoveFileActionAlreadyExecuted() throws Exception {
        ActionCreator creator = new RemoveFileActionCreator();
        Action removeFileAction = creator.createAction(tempFile);

        // Prima esecuzione dovrebbe riuscire
        assertTrue(removeFileAction.execute());

        // Seconda esecuzione dovrebbe fallire
        assertFalse(removeFileAction.execute());
    }
}
