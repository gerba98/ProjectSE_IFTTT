package com.ccll.projectse_ifttt.Actions;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CopyFileActionTest {
    private Path tempDir;
    private final String sourceFileName = "testSourceFile.txt";
    private final String destinationFileName = "testDestinationFile.txt";

    static {
        // Inizializzazione del toolkit JavaFX
        new JFXPanel();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Crea una directory temporanea per i file di test
        tempDir = Files.createTempDirectory("testDir");
        Files.createFile(Paths.get(tempDir.toString(), sourceFileName));
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Cancella file e directory creati nei test
        Files.deleteIfExists(Paths.get(tempDir.toString(), sourceFileName));
        Files.deleteIfExists(Paths.get(tempDir.toString(), destinationFileName));
        Files.deleteIfExists(tempDir);
    }

    @Test
    @DisplayName("Copia file con successo")
    public void testCopyFileActionSuccess() {
        String sourcePath = Paths.get(tempDir.toString(), sourceFileName).toString();
        String destinationPath = tempDir.toString();

        ActionCreator creator = new CopyFileActionCreator();
        Action copyFileAction = creator.createAction(sourcePath + ";" + destinationPath);

        // Verifica che l'esecuzione sia riuscita e che il file di destinazione esista
        assertTrue(copyFileAction.execute(), "L'azione di copia deve essere eseguita correttamente");
        assertTrue(Files.exists(Paths.get(destinationPath, sourceFileName)), "Il file copiato deve esistere nel percorso di destinazione");
    }

    @Test
    @DisplayName("Fallimento della copia: file sorgente inesistente")
    public void testCopyFileActionSourceFileNotExist() {
        String sourcePath = Paths.get(tempDir.toString(), "nonExistentFile.txt").toString();
        String destinationPath = tempDir.toString();

        ActionCreator creator = new CopyFileActionCreator();
        Action copyFileAction = creator.createAction(sourcePath + ";" + destinationPath);

        // Verifica che l'azione fallisca se il file sorgente non esiste
        assertFalse(copyFileAction.execute(), "L'azione di copia deve fallire se il file sorgente non esiste");
    }

    @Test
    @DisplayName("Fallimento della copia: percorso di destinazione non valido")
    public void testCopyFileActionInvalidDestinationPath() {
        String sourcePath = Paths.get(tempDir.toString(), sourceFileName).toString();
        String invalidDestinationPath = Paths.get(tempDir.toString(), "nonExistentDir").toString();

        ActionCreator creator = new CopyFileActionCreator();
        Action copyFileAction = creator.createAction(sourcePath + ";" + invalidDestinationPath);

        // Verifica che l'azione fallisca se il percorso di destinazione non esiste
        assertFalse(copyFileAction.execute(), "L'azione di copia deve fallire se il percorso di destinazione non è valido");
    }
}
