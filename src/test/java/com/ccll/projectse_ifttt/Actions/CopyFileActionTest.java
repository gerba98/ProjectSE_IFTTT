package com.ccll.projectse_ifttt.Actions;

import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.*;

import static org.junit.Assert.assertTrue;

public class CopyFileActionTest {
    private Path tempDir;
    private String sourceFileName = "testSourceFile.txt";
    private String destinationFileName = "testDestinationFile.txt";

    static {
        // Inizializzazione del toolkit JavaFX
        new JFXPanel();
    }

    @Before
    public void setUp() throws Exception {
        tempDir = Files.createTempDirectory("testDir");
        Files.createFile(Paths.get(tempDir.toString(), sourceFileName));
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get(tempDir.toString(), sourceFileName));
        Files.deleteIfExists(Paths.get(tempDir.toString(), destinationFileName));
        Files.deleteIfExists(tempDir);
    }

    @Test
    public void testCopyFileAction() {
        String sourcePath = Paths.get(tempDir.toString(), sourceFileName).toString();
        String destinationPath = tempDir.toString();

        ActionCreator creator = new CopyFileActionCreator();
        Action copyFileAction = creator.createAction(sourcePath + ";" + destinationPath);

        assertTrue("File should be copied successfully", copyFileAction.execute());
        assertTrue("Destination file should exist after copy", Files.exists(Paths.get(destinationPath, sourceFileName)));
    }
}
