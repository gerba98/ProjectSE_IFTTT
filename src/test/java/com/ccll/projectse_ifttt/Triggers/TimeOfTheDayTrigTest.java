package com.ccll.projectse_ifttt.Triggers;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class TimeOfTheDayTrigTest {

    private TimeOfTheDayTrig timeOfTheDayTrig;

    @Before
    public void setUp() {
        // Inizializza il trigger con l'orario corrente
        LocalTime targetTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        timeOfTheDayTrig = new TimeOfTheDayTrig(targetTime);
    }

    @Test
    public void testTimeOfTheDayTrigEvaluation() {
        // Verifica che il trigger si attivi esattamente all'orario corrente
        assertTrue("Il trigger dovrebbe attivarsi all'ora corrente.", timeOfTheDayTrig.evaluate());

        // Aspetta un minuto per garantire che l'ora sia cambiata
        timeOfTheDayTrig.setTime(LocalTime.now().minusMinutes(1));

        // Verifica che il trigger non si attivi ora che l'orario è cambiato
        assertFalse("Il trigger non dovrebbe attivarsi dopo che l'orario è cambiato.", timeOfTheDayTrig.evaluate());
    }

    @Test
    public void testTOTDTrigCreator() {
        // Creazione di un'istanza di TOTDTrigCreator con un orario specifico
        LocalTime time = LocalTime.of(12, 30);
        TOTDTrigCreator totdTrigCreator = new TOTDTrigCreator();

        // Creazione del trigger usando il creator
        Trigger trigger = totdTrigCreator.createTrigger(time.toString());

        // Verifica che il metodo "createTrigger()" di TOTDTrigCreator restituisca un TimeOfTheDayTrig
        assertTrue(trigger instanceof TimeOfTheDayTrig);
    }

    @Test
    public void testTimeOfTheDayTrigInitialization() {
        // Creazione del trigger con un orario specifico
        LocalTime time = LocalTime.of(12, 30);
        TimeOfTheDayTrig timeOfTheDayTrig = new TimeOfTheDayTrig(time);

        // Verifica che l'orario sia stato inizializzato correttamente
        assertEquals(time, timeOfTheDayTrig.getTime());
    }
}
