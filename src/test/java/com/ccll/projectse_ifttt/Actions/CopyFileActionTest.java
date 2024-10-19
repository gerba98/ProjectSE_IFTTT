package com.ccll.projectse_ifttt.Actions;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CopyFileActionTest {
    private Path tempDir;
    private String sourceFileName = "testSourceFile.txt";
    private String destinationFileName = "testDestinationFile.txt";

    static {
        // Inizializzazione del toolkit JavaFX
        new JFXPanel();
    }

    @BeforeEach
    public void setUp() throws Exception {
        tempDir = Files.createTempDirectory("testDir");
        Files.createFile(Paths.get(tempDir.toString(), sourceFileName));
    }

    @AfterEach
    public void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get(tempDir.toString(), sourceFileName));
        Files.deleteIfExists(Paths.get(tempDir.toString(), destinationFileName));
        Files.deleteIfExists(tempDir);
    }

    @Test
    @DisplayName("Test copia file: l'azione di copia deve essere eseguita correttamente")
    public void testCopyFileAction() {
        String sourcePath = Paths.get(tempDir.toString(), sourceFileName).toString();
        String destinationPath = tempDir.toString();

        ActionCreator creator = new CopyFileActionCreator();
        Action copyFileAction = creator.createAction(sourcePath + ";" + destinationPath);

        assertTrue(copyFileAction.execute(), "Il file dovrebbe essere copiato con successo");
        assertTrue(Files.exists(Paths.get(destinationPath, sourceFileName)), "Il file di destinazione dovrebbe esistere dopo la copia");
    }
}
