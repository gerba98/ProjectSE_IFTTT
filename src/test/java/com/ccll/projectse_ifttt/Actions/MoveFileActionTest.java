package com.ccll.projectse_ifttt.Actions;

import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.nio.file.*;
import static org.junit.Assert.*;

public class MoveFileActionTest {
    private Path tempDirSource;
    private Path tempDirDestination;
    private String fileName = "testFile.txt";

    static {
        // Inizializza JavaFX Toolkit per gestire gli alert
        new JFXPanel();
    }

    @Before
    public void setUp() throws Exception {
        tempDirSource = Files.createTempDirectory("testDirSource");
        tempDirDestination = Files.createTempDirectory("testDirDestination");
        Files.createFile(Paths.get(tempDirSource.toString(), fileName));
        System.out.println("File creato nel percorso: " + tempDirSource.toString());
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get(tempDirDestination.toString(), fileName));
        Files.deleteIfExists(tempDirSource);
        Files.deleteIfExists(tempDirDestination);
    }

    @Test
    @DisplayName("Test spostamento file con successo")
    public void testMoveFileActionSuccess() throws Exception {
        String sourcePath = Paths.get(tempDirSource.toString(), fileName).toString();
        String destinationPath = tempDirDestination.toString();

        // Verifica che il file sorgente esista prima dell'azione
        assertTrue("Il file sorgente dovrebbe esistere prima dello spostamento", Files.exists(Paths.get(sourcePath)));

        ActionCreator creator = new MoveFileActionCreator();
        Action moveFileAction = creator.createAction(sourcePath + ";" + destinationPath);

        // Esegue l'azione di spostamento
        assertTrue("The action should succeed", moveFileAction.execute());

        // Verifica che il file sia stato spostato correttamente
        assertFalse("Source file should not exist after move", Files.exists(Paths.get(sourcePath)));
        assertTrue("Destination file should exist after move", Files.exists(Paths.get(destinationPath, fileName)));
    }

    @Test
    @DisplayName("Test esecuzione non ripetibile dell'azione di spostamento")
    public void testMoveFileActionAlreadyExecuted() throws Exception {
        String sourcePath = Paths.get(tempDirSource.toString(), fileName).toString();
        String destinationPath = tempDirDestination.toString();

        ActionCreator creator = new MoveFileActionCreator();
        Action moveFileAction = creator.createAction(sourcePath + ";" + destinationPath);

        // Prima esecuzione dovrebbe avere successo
        assertTrue("First execution should succeed", moveFileAction.execute());

        // Verifica che il file sia stato spostato correttamente
        assertFalse("Source file should not exist after move", Files.exists(Paths.get(sourcePath)));
        assertTrue("Destination file should exist after move", Files.exists(Paths.get(destinationPath, fileName)));

        // Seconda esecuzione dovrebbe fallire perché il file è già stato spostato
        assertFalse("Action should not execute more than once", moveFileAction.execute());
    }

}
