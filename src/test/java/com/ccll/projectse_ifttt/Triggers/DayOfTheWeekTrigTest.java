package com.ccll.projectse_ifttt.Triggers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DayOfTheWeekTrigTest {


    @Test
    @DisplayName("Valutazione del trigger al giorno della settimana specificato")
    public void testEvaluation() {

        DayOfWeek day = LocalDate.now().getDayOfWeek();
        DayOfTheWeekTrig dayTrigger = new DayOfTheWeekTrig(day);

        assertTrue(dayTrigger.evaluate(), "Il trigger dovrebbe attivarsi quando il giorno corrente corrisponde a quello specificato.");

        DayOfWeek newDay = DayOfWeek.of(new Random().nextInt(7) + 1);
        if(day.equals(newDay)) {
            while (day.equals(newDay)) {
                newDay = DayOfWeek.of(new Random().nextInt(7) + 1);
                dayTrigger.setDayOfWeek(newDay);
            }
        }
        dayTrigger.setDayOfWeek(newDay);
        assertFalse(dayTrigger.evaluate(), "..Il trigger non dovrebbe attivarsi quando il giorno corrente non corrisponde a quello specificato.");

    }

    @Test
    @DisplayName("Inizializzazione di TimeOfTheWeekTrig con il giorno della settimana specificato")
    public void testInitialization() {

        DayOfWeek day = DayOfWeek.of(1);
        DayOfTheWeekTrig dayTrigger = new DayOfTheWeekTrig(day);

        assertEquals(day, dayTrigger.getDayOfWeek());
    }

    @Test
    @DisplayName("Creazione di TimeOfTheWeekTrig tramite TOTWTrigCreator")
    public void testCreator() {

        String day = "Monday";
        TriggerCreator DOTW = new DOTWTrigCreator();
        Trigger trigger = DOTW.createTrigger(day);
        assertInstanceOf(DayOfTheWeekTrig.class, trigger);
    }

}