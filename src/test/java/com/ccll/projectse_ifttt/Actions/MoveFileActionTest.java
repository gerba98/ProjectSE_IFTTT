package com.ccll.projectse_ifttt.Actions;

import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MoveFileActionTest {
    private Path tempDirSource;
    private Path tempDirDestination;
    private final String fileName = "testFile.txt";

    static {
        // Inizializza JavaFX Toolkit per gestire gli alert
        new JFXPanel();
    }

    @Before
    public void setUp() throws Exception {
        tempDirSource = Files.createTempDirectory("testDirSource");
        tempDirDestination = Files.createTempDirectory("testDirDestination");
        Files.createFile(Paths.get(tempDirSource.toString(), fileName));
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
        assertTrue(Files.exists(Paths.get(sourcePath)));

        ActionCreator creator = new MoveFileActionCreator();
        Action moveFileAction = creator.createAction(sourcePath + ";" + destinationPath);

        // Esegue l'azione di spostamento
        assertTrue(moveFileAction.execute());

        // Verifica che il file sia stato spostato correttamente
        assertFalse(Files.exists(Paths.get(sourcePath)));
        assertTrue(Files.exists(Paths.get(destinationPath, fileName)));
    }

    @Test
    @DisplayName("Test esecuzione non ripetibile dell'azione di spostamento")
    public void testMoveFileActionAlreadyExecuted() throws Exception {
        String sourcePath = Paths.get(tempDirSource.toString(), fileName).toString();
        String destinationPath = tempDirDestination.toString();

        ActionCreator creator = new MoveFileActionCreator();
        Action moveFileAction = creator.createAction(sourcePath + ";" + destinationPath);

        // Prima esecuzione dovrebbe avere successo
        assertTrue(moveFileAction.execute());

        // Verifica che il file sia stato spostato correttamente
        assertFalse(Files.exists(Paths.get(sourcePath)));
        assertTrue(Files.exists(Paths.get(destinationPath, fileName)));

        // Seconda esecuzione dovrebbe fallire perché il file è già stato spostato
        assertFalse(moveFileAction.execute());
    }

}
