package com.ccll.projectse_ifttt.Actions;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import com.ccll.projectse_ifttt.TestUtilsClasses.ActionTestUtils;

public class CompositeActionTest {

    private CompositeAction compositeAction;
    private ActionTestUtils action1;
    private ActionTestUtils action2;

    @Before
    public void setUp() {
        compositeAction = new CompositeAction();
        action1 = new ActionTestUtils();
        action2 = new ActionTestUtils();
    }

    @Test
    @DisplayName("Esecuzione con tutte le azioni che hanno successo")
    public void testExecuteAllActionsSuccess() {
        compositeAction.addAction(action1);
        compositeAction.addAction(action2);

        assertTrue("L'esecuzione della CompositeAction deve riuscire solo se tutte le azioni interne riescono", compositeAction.execute());
        assertTrue("La prima azione deve risultare eseguita", action1.wasExecuted());
        assertTrue("La seconda azione deve risultare eseguita", action2.wasExecuted());
    }

    @Test
    @DisplayName("Interruzione dell'esecuzione in caso di fallimento di una delle azioni")
    public void testExecuteStopsOnFailure() {
        // Configura action1 per fallire e action2 per avere successo
        action1.setExecutionResult(false);
        action2.setExecutionResult(true);

        compositeAction.addAction(action1);
        compositeAction.addAction(action2);

        assertFalse("L'esecuzione della CompositeAction deve fallire se una delle azioni interne fallisce", compositeAction.execute());
        assertTrue("La prima azione deve risultare eseguita", action1.wasExecuted());
        assertFalse("La seconda azione non deve essere eseguita, poiché l'azione precedente ha fallito", action2.wasExecuted());
    }

    @Test
    @DisplayName("Aggiunta e rimozione di un'azione")
    public void testAddAndRemoveAction() {
        compositeAction.addAction(action1);
        assertEquals("Dovrebbe esserci 1 azione dopo l'aggiunta", 1, compositeAction.getChildren().size());

        compositeAction.removeAction(action1);
        assertEquals("Dovrebbe esserci 0 azioni dopo la rimozione", 0, compositeAction.getChildren().size());
    }

    @Test
    @DisplayName("Esecuzione senza azioni aggiunte")
    public void testExecuteNoActions() {
        assertTrue("L'esecuzione deve avere successo anche quando non ci sono azioni, poiché non c'è nulla che possa fallire", compositeAction.execute());
    }
}
