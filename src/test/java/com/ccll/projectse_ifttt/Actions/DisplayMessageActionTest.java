package com.ccll.projectse_ifttt.Actions;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
    @DisplayName("Esecuzione corretta dell'azione di visualizzazione del messaggio")
    public void testDisplayMessageActionSuccess() {
        // Esegue l'azione di visualizzazione del messaggio
        boolean result = displayMessageAction.execute();

        // Verifica che l'azione sia stata eseguita con successo
        assertTrue(result, "L'azione di visualizzazione del messaggio deve essere eseguita correttamente");
    }

    @Test
    @DisplayName("Esecuzione dell'azione di visualizzazione con messaggio vuoto")
    public void testDisplayMessageActionEmptyMessage() {
        // Crea un'azione con un messaggio vuoto
        ActionCreator creator = new DisplayMessageActionCreator();
        displayMessageAction = creator.createAction("");

        // Verifica che l'azione fallisca con un messaggio vuoto
        assertFalse(displayMessageAction.execute(), "L'azione di visualizzazione del messaggio non dovrebbe essere eseguita con un messaggio vuoto");
    }

    @Test
    @DisplayName("Esecuzione dell'azione di visualizzazione con messaggio nullo")
    public void testDisplayMessageActionNullMessage() {
        // Crea un'azione con un messaggio nullo
        ActionCreator creator = new DisplayMessageActionCreator();
        displayMessageAction = creator.createAction(null);

        // Verifica che l'azione fallisca con un messaggio nullo
        assertFalse(displayMessageAction.execute(), "L'azione di visualizzazione del messaggio non dovrebbe essere eseguita con un messaggio nullo");
    }
}
