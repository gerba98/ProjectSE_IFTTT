package com.ccll.projectse_ifttt.Actions;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DisplayMessageActionTest {

    private Action displayMessageAction; // L'azione da testare

    static {
        // Inizializzazione del toolkit JavaFX
        new JFXPanel();
    }

    @BeforeEach
    public void setUp() {
        // Inizializza l'ActionCreator per creare l'azione di visualizzazione del messaggio
        ActionCreator creator = new DisplayMessageActionCreator();
        displayMessageAction = creator.createAction("Questo è un messaggio di test.");
    }

    @Test
    @DisplayName("Test azione di visualizzazione messaggio: esecuzione iniziale corretta")
    public void testDisplayMessageActionSuccess() {
        // Esegue l'azione di visualizzazione del messaggio
        boolean result = displayMessageAction.execute();

        // Verifica che l'azione sia stata eseguita con successo
        assertTrue(result);
    }
}
