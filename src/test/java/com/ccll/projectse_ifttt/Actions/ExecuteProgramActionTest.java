package com.ccll.projectse_ifttt.Actions;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class ExecuteProgramActionTest {

    private String validProgramPath;
    private String validCommand;

    @Before
    public void setUp() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac") || os.contains("linux")) {
            validProgramPath = "\"/bin/bash\", \"-c\"";  // Usa /bin/echo per test semplici
            validCommand = "echo Hello World";    // Comando semplice da eseguire
        } else if (os.contains("win")) {
            validProgramPath = "cmd.exe";
            validCommand = "/c echo Hello World";
        }
    }


    @Test
    @DisplayName("Test esecuzione programma valida")
    public void testExecuteProgramActionSuccess() {
        // Crea l'azione utilizzando il comando valido per il sistema operativo
        ActionCreator creator = new ExecuteProgramActionCreator();
        Action executeProgramAction = creator.createAction(validProgramPath + ";" + validCommand);

        // Verifica che l'azione venga eseguita correttamente
        assertTrue("The action should succeed", executeProgramAction.execute());
    }

    @Test
    @DisplayName("Test che il programma non si esegue una seconda volta")
    public void testExecuteProgramActionNotRepeated() {
        // Creazione di un'azione valida per il sistema operativo corrente
        ActionCreator creator = new ExecuteProgramActionCreator();
        Action executeProgramAction = creator.createAction(validProgramPath + ";" + validCommand);

        // Prima esecuzione deve avere successo
        assertTrue("First execution should succeed", executeProgramAction.execute());

        // Seconda esecuzione non deve riuscire (azione già eseguita)
        assertFalse("Second execution should fail", executeProgramAction.execute());
    }

    @Test
    @DisplayName("Test esecuzione fallita con comando non valido")
    public void testExecuteProgramActionFailure() {
        // Fornire un comando o un percorso di programma non valido
        ActionCreator creator = new ExecuteProgramActionCreator();
        Action executeProgramAction = creator.createAction("/path/to/nowhere;" + "invalidCommand");

        // Verifica che l'esecuzione fallisca
        assertFalse("Execution with invalid command should fail", executeProgramAction.execute());
    }
}
