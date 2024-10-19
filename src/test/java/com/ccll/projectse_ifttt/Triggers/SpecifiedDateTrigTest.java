package com.ccll.projectse_ifttt.Triggers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SpecifiedDateTrigTest {
    @Test
    @DisplayName("Valutazione del trigger alla data specificata")
    public void testEvaluation() {
        LocalDate date = LocalDate.now();
        SpecifiedDateTrig dateTrigger = new SpecifiedDateTrig(date);

        assertTrue(dateTrigger.evaluate(), "Il trigger dovrebbe attivarsi quando la data corrente corrisponde a quella specificata.");

        date = date.plusDays(1);
        dateTrigger.setDate(date);
        assertFalse(dateTrigger.evaluate(), "Il trigger non dovrebbe attivarsi quando la data corrente non corrisponde a quella specificata.");
    }

    @Test
    @DisplayName("Inizializzazione di SpecifiedDateTrigger con la data specififcata")
    public void testInitialization() {
        LocalDate date = LocalDate.now();
        SpecifiedDateTrig dateTrigger = new SpecifiedDateTrig(date);

        assertEquals(date, dateTrigger.getDate());
    }

    @Test
    @DisplayName("Creazione di SpecifiedDateTrigger tramite SDTrigCreator")
    public void testCreator() {
        String date = "2024-10-19";
        TriggerCreator SD= new SDTrigCreator();
        Trigger trigger = SD.createTrigger(date);
        assertInstanceOf(SpecifiedDateTrig.class, trigger);
    }
}