package com.ccll.projectse_ifttt.Actions;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import org.junit.jupiter.api.Test;

public class DisplayMessageActionTest {

    @Test
    public void testConstructor() {
        String expectedMessage = "Hello, World!";
        DisplayMessageAction action = new DisplayMessageAction(expectedMessage);

        // Verifica che il messaggio sia stato inizializzato correttamente
        assertEquals("Visualizza messaggio: Hello, World!", action.toString());
    }

    @Test
    public void testExecute() {
        Platform.startup(() -> { }); // Inizializza il toolkit JavaFX se non lo è già
        DisplayMessageAction action = new DisplayMessageAction("Test Message");

        Platform.runLater(() -> {
            // Verifica che il metodo execute ritorni true
            boolean result = action.execute();
            assertTrue(result, "L'azione dovrebbe restituire true");
        });
    }
    @Test
    public void testToString() {
        String expectedMessage = "Test message";
        DisplayMessageAction action = new DisplayMessageAction(expectedMessage);

        // Verifica che il toString restituisca il formato corretto
        assertEquals("Visualizza messaggio: Test message", action.toString());
    }
}
