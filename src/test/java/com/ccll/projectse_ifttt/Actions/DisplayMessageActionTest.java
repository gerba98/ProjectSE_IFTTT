package com.ccll.projectse_ifttt.Actions;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import org.junit.Test;
import static org.junit.Assert.*;

public class DisplayMessageActionTest {

    private String message = "Messaggio di test";
    DisplayMessageAction displayMessage = new DisplayMessageAction(message);


    @Test
    public void testInitialization() {
        assertNotNull(displayMessage);
        assertEquals("Visualizza messaggio: " + message, displayMessage.toString());
    }

    @Test
    public void testCreator() {
        DisplayMessageActionCreator displayMessageActionCreator = new DisplayMessageActionCreator(message);
        Action displayMessageAction = displayMessageActionCreator.createAction();
        assertNotNull(displayMessageAction);
        assertTrue(displayMessageAction instanceof DisplayMessageAction);
        assertEquals("Visualizza messaggio: " + message, displayMessageAction.toString());
    }

    @Test
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