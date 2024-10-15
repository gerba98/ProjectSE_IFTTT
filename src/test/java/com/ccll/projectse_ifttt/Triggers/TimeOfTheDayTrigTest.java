package com.ccll.projectse_ifttt.Triggers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;

public class TimeOfTheDayTrigTest {

    @Test
    public void testEvaluation() {
        LocalTime time = LocalTime.now().withSecond(0).withNano(0);
        TimeOfTheDayTrig timeTrigger = new TimeOfTheDayTrig(time);

        assertTrue(timeTrigger.evaluate(), "Il trigger dovrebbe attivarsi quando l'ora corrente corrisponde all'ora specificata.");

        timeTrigger.setTime(time.minusMinutes(1));

        assertFalse(timeTrigger.evaluate(), "Il trigger non dovrebbe attivarsi quando l'ora corrente non corrisponde all'ora specificata.");
    }

    @Test
    public void testInitialization() {
        LocalTime time = LocalTime.of(12, 30);
        TimeOfTheDayTrig timeTrigger = new TimeOfTheDayTrig(time);

        assertEquals(time, timeTrigger.getTime());
    }

    @Test
    public void testCreator() {
        String time = "12:30";
        TriggerCreator TOTD = new TOTDTrigCreator();
        Trigger trigger = TOTD.createTrigger(time);
        assertInstanceOf(TimeOfTheDayTrig.class, trigger);
    }

}