package com.ccll.projectse_ifttt.Triggers;

import com.ccll.projectse_ifttt.TestUtilsClasses.TriggerTestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TriggerTest {
    @Test
    void testEdgeTriggeredBehavior() {

        TriggerTestUtils trigger1 = new TriggerTestUtils(true);

        // Prima valutazione
        assertTrue(trigger1.evaluate(), "La prima valutazione dovrebbe essere true quando entrambi i trigger sono true");

        // Seconda valutazione senza cambiamenti
        assertFalse(trigger1.evaluate(), "La seconda valutazione dovrebbe essere false (edge-triggered)");

        // Cambio stato e nuova valutazione
        trigger1.setShouldTrigger(false);

        trigger1.evaluate();
        assertFalse(trigger1.evaluate(), "La terza valutazione dovrebbe essere false in quanto un trigger è false");

        trigger1.setShouldTrigger(true);
        assertTrue(trigger1.evaluate(), "La valutazione dopo un cambiamento di stato dovrebbe essere true");
    }

}
