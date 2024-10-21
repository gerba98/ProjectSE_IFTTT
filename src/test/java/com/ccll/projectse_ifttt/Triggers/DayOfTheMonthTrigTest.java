package com.ccll.projectse_ifttt.Triggers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.MonthDay;

import static org.junit.jupiter.api.Assertions.*;

class DayOfTheMonthTrigTest {

    @Test
    @DisplayName("Valutazione del trigger al giorno del mese specificato")
    public void testEvaluation() {
        LocalDate dayLocal = LocalDate.now();
        MonthDay dayMonth = MonthDay.from(dayLocal);
        DayOfTheMonthTrig dayMonthTrigger = new DayOfTheMonthTrig(dayMonth);

        assertTrue(dayMonthTrigger.evaluate(), "Il trigger dovrebbe attivarsi quando il giorno del mese corrente corrisponde a quello specificato.");

        dayLocal = dayLocal.plusDays(1);
        MonthDay newDayMonth = MonthDay.from(dayLocal);
        dayMonthTrigger.setDayOfMonth(newDayMonth);
        assertFalse(dayMonthTrigger.evaluate(), "Il trigger non dovrebbe attivarsi quando il giorno del mese corrente non corrisponde a quello specificato.");

    }

    @Test
    @DisplayName("Inizializzazione di DayOfTheMonthTrig con il giorno del mese specificato")
    public void testInitialization() {
        LocalDate dayLocal = LocalDate.now();
        MonthDay dayMonth = MonthDay.from(dayLocal);
        DayOfTheMonthTrig dayMonthTrigger = new DayOfTheMonthTrig(dayMonth);

        assertEquals(dayMonth, dayMonthTrigger.getDayOfMonth());
    }

    @Test
    @DisplayName("Creazione di DayOfTheMonthTrig tramite DOTMTrigCreator")
    public void testCreator() {

        String day = "2024-10-19";
        TriggerCreator DOTM = new DOTMTrigCreator();
        Trigger trigger = DOTM.createTrigger(day);
        assertInstanceOf(DayOfTheMonthTrig.class, trigger);
    }
}