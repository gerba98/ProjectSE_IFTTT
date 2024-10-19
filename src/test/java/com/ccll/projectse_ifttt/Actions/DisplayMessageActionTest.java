package com.ccll.projectse_ifttt.Actions;

import javafx.application.Platform;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class DisplayMessageActionTest {

    private String message = "Messaggio di test";
    DisplayMessageAction displayMessage = new DisplayMessageAction(message);

    @Test
    @DisplayName("Inizializzazione di DisplayMessageAction")
    public void testInitialization() {
        assertNotNull(displayMessage);
        assertEquals("Display message;" + message, displayMessage.toString());
    }

    @Test
    @DisplayName("Creazione di DisplayMessageAction tramite DisplayMessageActionCreator")
    public void testCreator() {
        ActionCreator creator = new DisplayMessageActionCreator();
        Action displayMessageAction = creator.createAction(message);
        assertNotNull(displayMessageAction);
        assertTrue(displayMessageAction instanceof DisplayMessageAction);
        assertEquals("Display message;" + message, displayMessageAction.toString());
    }

    @Test
    @DisplayName("Esecuzione dell'azione DisplayMessageAction")
    public void testExecute() {
        try {
            CompletableFuture<Void> future = new CompletableFuture<>();

            Platform.runLater(() -> {
                displayMessage.execute();
                future.complete(null);
            });

            future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("Errore durante il test testExecute " + e.getStackTrace());
        }
    }
}
