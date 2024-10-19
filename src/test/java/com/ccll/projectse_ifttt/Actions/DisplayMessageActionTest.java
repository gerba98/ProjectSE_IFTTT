package com.ccll.projectse_ifttt.Actions;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(result, "L'azione dovrebbe essere eseguita con successo");
    }

    @Test
    @DisplayName("Test azione di visualizzazione messaggio: non deve eseguire una seconda volta")
    public void testDisplayMessageActionAlreadyExecuted() {
        // Esegue l'azione di visualizzazione una prima volta
        boolean firstRun = displayMessageAction.execute();
        assertTrue(firstRun, "La prima esecuzione dovrebbe avere successo");

        // Esegue nuovamente l'azione, che non dovrebbe essere eseguita poiché è già stata eseguita
        boolean secondRun = displayMessageAction.execute();
        assertFalse(secondRun, "La seconda esecuzione dovrebbe fallire poiché è già stata eseguita");
    }

}
