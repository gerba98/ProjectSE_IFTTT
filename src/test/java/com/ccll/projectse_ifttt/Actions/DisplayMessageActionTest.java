package com.ccll.projectse_ifttt.Actions;

import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DisplayMessageActionTest {

    private Action displayMessageAction; // L'azione da testare

    static {
        // Inizializzazione del toolkit JavaFX
        new JFXPanel();
    }

    @Before
    public void setUp() {
        // Inizializza l'ActionCreator per creare l'azione di visualizzazione del messaggio
        ActionCreator creator = new DisplayMessageActionCreator();
        displayMessageAction = creator.createAction("Questo è un messaggio di test.");
    }

    @Test
    public void testDisplayMessageActionSuccess() {
        // Esegue l'azione di visualizzazione del messaggio
        boolean result = displayMessageAction.execute();

        // Verifica che l'azione sia stata eseguita con successo
        assertTrue("L'azione dovrebbe essere eseguita con successo", result);
    }

    @Test
    public void testDisplayMessageActionAlreadyExecuted() {
        // Esegue l'azione di visualizzazione una prima volta
        boolean firstRun = displayMessageAction.execute();
        assertTrue("La prima esecuzione dovrebbe avere successo", firstRun);

        // Esegue nuovamente l'azione, che non dovrebbe essere eseguita poiché è già stata eseguita
        boolean secondRun = displayMessageAction.execute();
        assertFalse("La seconda esecuzione dovrebbe fallire poiché è già stata eseguita", secondRun);
    }

}
