package com.ccll.projectse_ifttt.Actions;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExecuteProgramActionTest {
    private String validProgramPath;
    private String validCommand;

    @Before
    public void setUp() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac") || os.contains("linux")) {
            validProgramPath = "/bin/bash";
            validCommand = "echo 'Hello World'";
        } else if (os.contains("win")) {
            validProgramPath = "cmd.exe";
            validCommand = "echo Hello World";
        }
    }

    @Test
    @DisplayName("Test esecuzione programma valida")
    public void testExecuteProgramActionSuccess() {
        // Crea l'azione utilizzando il comando valido per il sistema operativo
        ActionCreator creator = new ExecuteProgramActionCreator();
        // Costruisce il comando nel formato richiesto: programPath-command
        String fullCommand = validProgramPath + "-" + validCommand;
        Action executeProgramAction = creator.createAction(fullCommand);

        // Verifica che l'azione venga eseguita correttamente
        assertTrue(executeProgramAction.execute());
    }

    @Test
    @DisplayName("Test esecuzione fallita con comando non valido")
    public void testExecuteProgramActionFailure() {
        // Fornire un comando o un percorso di programma non valido
        ActionCreator creator = new ExecuteProgramActionCreator();
        Action executeProgramAction = creator.createAction("path/to/nowhere-invalidCommand");

        // Verifica che l'esecuzione fallisca
        assertFalse(executeProgramAction.execute());
    }
}