package com.ccll.projectse_ifttt.Triggers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.MonthDay;

import static org.junit.jupiter.api.Assertions.*;
class ExecutionProgramTrigTest {
    @Test
    @DisplayName("Valutazione del trigger all'output del programma specificato")
    public void testEvaluation() {
        String userInfo = "py-C:\\Users\\cuozz\\OneDrive\\Desktop\\exe\\file.py-Azione eseguita";
        ExecutionProgramTrig executionProgramTrig = new ExecutionProgramTrig(userInfo);

        assertTrue(executionProgramTrig.evaluate(), "Il trigger dovrebbe attivarsi quando l'output del programma specificato è uguale a quello desiderato");

        executionProgramTrig.setUserInfo("py-C:\\Users\\cuozz\\OneDrive\\Desktop\\exe\\file.py-new output");
        assertFalse(executionProgramTrig.evaluate(), "Il trigger dovrebbe attivarsi quando l'output del programma specificato è uguale a quello desiderato");
    }

    @Test
    @DisplayName("Inizializzazione di ExecutionProgramTrig con comando, programma e output specificati")
    public void testInitialization() {
        String userInfo = "py-C:\\Users\\cuozz\\OneDrive\\Desktop\\exe\\file.py-Azione eseguita";
        ExecutionProgramTrig executionProgramTrig = new ExecutionProgramTrig(userInfo);

        assertEquals(userInfo, executionProgramTrig.getUserInfo());
    }

    @Test
    @DisplayName("Creazione di DayOfTheMonthTrig tramite DOTMTrigCreator")
    public void testCreator() {
        String userInfo = "py-C:\\Users\\cuozz\\OneDrive\\Desktop\\exe\\file.py-Azione eseguita";
        TriggerCreator EP = new EPTrigCreator();
        Trigger trigger = EP.createTrigger(userInfo);
        assertInstanceOf(ExecutionProgramTrig.class, trigger);
    }
}